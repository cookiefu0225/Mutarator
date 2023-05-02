package edu.illinois.mutarator.returns;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import edu.illinois.mutarator.Mutarator;

import java.util.HashSet;
import java.util.Set;

public class PrimitiveReturn extends Mutarator {
    Set<String> primitiveTypes = new HashSet<>();

    public PrimitiveReturn() {
        for (PrimitiveType.Primitive c : PrimitiveType.Primitive.values()) {
            primitiveTypes.add(c.asString());
        }
    }

    @Override
    public void visit(MethodDeclaration md, Object obj) {
        super.visit(md, obj);

        final String returnType = md.getType().toString();
        boolean valid = primitiveTypes.contains(returnType) &&
                !returnType.equals("byte") && !returnType.equals("boolean");

        if (valid) {
            if (mutateMode) {
                md.walk(ReturnStmt.class, returnStmt -> {
                    if (mutantsId == callingCount) {
                        returnStmt.setExpression(new IntegerLiteralExpr(0));
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

    public int getCallingCount() {
        return callingCount;
    }
}
