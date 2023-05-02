package edu.illinois.mutarator.returns;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import edu.illinois.mutarator.Mutarator;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class EmptyReturn extends Mutarator {
    private final Set<String> supported = new HashSet<>();

    public EmptyReturn() {
        for (EmptySupport e : EmptySupport.values()) {
            supported.add(e.toString());
        }
    }

    @Override
    public void visit(MethodDeclaration md, Object obj) {
        super.visit(md, obj);

        final String returnType = md.getType().toString();
        String trimmedType = genericClassChecker(returnType);
        boolean valid = supported.contains(trimmedType);

        if (valid) {
            if (mutateMode) {
                md.walk(ReturnStmt.class, returnStmt -> {
                    if (mutantsId == callingCount) {
                        switch (trimmedType) {
                            case "Integer", "Float", "Character", "Short", "Long", "Double" -> {
                                returnStmt.setExpression(new IntegerLiteralExpr(0));
                            }
                            case "String" -> {
                                returnStmt.setExpression(new StringLiteralExpr(""));
                            }
                            case "Optional" -> {
                                if (addImport(md, Object.class)) {
                                    returnStmt.setExpression(new MethodCallExpr(
                                            new NameExpr("Optional"), "empty"
                                    ));
                                } else { System.out.println("Import Failure"); }
                            }
                            case "List", "Collection" -> {
                                if (addImport(md, Collections.class)) {
                                    returnStmt.setExpression(new MethodCallExpr(
                                            new NameExpr("Collections"), "emptyList"
                                    ));
                                } else {
                                    System.out.println("Import Failure");
                                }
                            }
                            case "Set" -> {
                                if (addImport(md, Collections.class)) {
                                    returnStmt.setExpression(new MethodCallExpr(
                                            new NameExpr("Collections"), "emptySet"
                                    ));
                                } else {
                                    System.out.println("ImportFailure");
                                }
                            }
                        }
                    }
                    callingCount ++;
                });
            } else {
                md.walk(ReturnStmt.class, returnStmt -> {
                    pointCount ++;
                });
            }
        }
    }

    private boolean addImport(MethodDeclaration md, Class<?> clazz) {
        Optional<CompilationUnit> temp = md.findCompilationUnit();
        if (temp.isPresent()) {
            CompilationUnit cu = temp.get();
            cu.addImport(clazz);
            return true;
        } else {
            return false;
        }
    }

    private String genericClassChecker(String target) {
        int temp = target.indexOf('<');
        return temp != -1 ? target.substring(0, temp) : target;
    }


    public enum EmptySupport {
        Integer,
        Float,
        Double,
        Character,
        Short,
        Long,
        String,
        Optional,
        List,
        Collection,
        Set
    }
}
