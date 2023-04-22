package edu.illinois.mutarator.returns;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import edu.illinois.mutarator.Mutator;
import java.util.*;

public class ValueReturn extends VoidVisitorAdapter {
    Set<String> primitiveTypes = new HashSet<>();
    FalseReturn falseReturnTool = new FalseReturn();
    TrueReturn trueReturnTool = new TrueReturn();
    Mutator task;

    private ValueReturn() {
        for (PrimitiveType.Primitive c : PrimitiveType.Primitive.values()) {
            primitiveTypes.add(c.asString());
        }
    }

    // flag means want true return or false return
    public ValueReturn(Mutator flag) {
        this();
        task = flag;
    }

    @Override
    public void visit(MethodDeclaration md, Object obj) {
//        System.out.println(md.toString());
        super.visit(md, obj);

        final String returnType = md.getType().toString();

        md.walk(ReturnStmt.class, returnStmt -> {
            // Use task to filter
            switch (task) {
                case TRUE -> trueReturnTool.visit(md, obj);
                case FALSE -> falseReturnTool.visit(md, obj);
                case PRIMITIVE -> {
                    if (primitiveTypes.contains(returnType) && !returnType.equals("byte") && !returnType.equals("boolean")) {
                        returnStmt.setExpression(new IntegerLiteralExpr(0));
                    }
                }
                default -> {
                    // handling cases such as Set<Integer>
                    // ex: Set<Integer> -> Set
                    String trimmedType = genericClassChecker(returnType);
                    switch (trimmedType) {
                        // cases handled by EMPTY task
                        case "Integer", "Float", "Character", "Short", "Long", "Double" -> {
                            if (task == Mutator.EMPTY) {
                                returnStmt.setExpression(new IntegerLiteralExpr(0));
                            }
                        }
                        case "String" -> {
                            if (task == Mutator.EMPTY)
                                returnStmt.setExpression(new StringLiteralExpr(""));
                        }
                        case "Optional" -> {
                            if (task == Mutator.EMPTY) {
                                if (addImport(md, Optional.class)) {
                                    returnStmt.setExpression(new MethodCallExpr(
                                            new NameExpr("Optional"), "empty"
                                    ));
                                } else {System.out.println("Import Failure");}
                            }
                        }
                        case "List", "Collection" -> {
                            if (task == Mutator.EMPTY) {
                                if (addImport(md, Collections.class)) {
                                    returnStmt.setExpression(new MethodCallExpr(
                                            new NameExpr("Collections"), "emptyList"
                                    ));
                                } else {System.out.println("Import Failure");}
                            }
                        }
                        case "Set" -> {
                            if (task == Mutator.EMPTY) {
                                if (addImport(md, Collections.class)) {
                                    returnStmt.setExpression(new MethodCallExpr(
                                            new NameExpr("Collections"), "emptySet"
                                    ));
                                } else { System.out.println("Import Failure"); }
                            }
                        }
                        default -> {
                            // other tasks handled by NULL task
                            if (task == Mutator.NULL && !primitiveTypes.contains(trimmedType)) {
                                returnStmt.setExpression(new NullLiteralExpr());
                            }
                        }
                    }
                }
            }
        });
    }

    /**
     * Add import for MethodCallExpr, such as Collections.emptyList().
     * Collections might have not been imported
     * @param md MethodDeclaration
     * @param clazz Class
     * @return true if import successfully
     */
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

    /**
     * Remove generic part of input type
     * @param target type name from get type
     * @return trimmed Set
     */
    private String genericClassChecker(String target) {
        int temp = target.indexOf('<');
        return temp != -1 ? target.substring(0, temp) : target;
    }
}
