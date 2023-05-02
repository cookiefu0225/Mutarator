package edu.illinois.mutarator.returns;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.NullLiteralExpr;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import edu.illinois.mutarator.Mutarator;

import java.util.HashSet;
import java.util.Set;

public class NullReturn extends Mutarator {
    private Set<String> excludeSet = new HashSet<>();

    public NullReturn() {
        for (PrimitiveType.Primitive c : PrimitiveType.Primitive.values()) {
            excludeSet.add(c.asString());
        }
        for (EmptyReturn.EmptySupport e : EmptyReturn.EmptySupport.values()) {
            excludeSet.add(e.toString());
        }
        excludeSet.add("void");
    }

    @Override
    public void visit(MethodDeclaration md, Object obj) {
        super.visit(md, obj);

        final String returnType = md.getType().toString();
        String trimmedType = genericClassChecker(returnType);
        boolean valid = !excludeSet.contains(trimmedType);

        if (valid) {
            if (mutateMode) {
                md.walk(ReturnStmt.class, returnStmt -> {
                    if (mutantsId == callingCount) {
                        returnStmt.setExpression(new NullLiteralExpr());
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

    private String genericClassChecker(String target) {
        int temp = target.indexOf('<');
        return temp != -1 ? target.substring(0, temp) : target;
    }

}
