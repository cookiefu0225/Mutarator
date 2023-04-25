package edu.illinois.mutarator.returns;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.HashSet;
import java.util.Set;

public class PrimitiveReturn extends VoidVisitorAdapter {
    Set<String> primitiveTypes = new HashSet<>();
    private int pointCount = 0;
    private int callingCount = 0;
    private int mutantsId = 0;
    private boolean mutateMode = false;

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

    public int getMutantsNumber() {
        return pointCount;
    }

    /**
     * Switch mutator mode to mutation mode
     */
    public void switchToMutation() {
        mutateMode = true;
    }

    public void switchToCount() {
        mutateMode = false;
    }

    public void resetCallingCount() {
        callingCount = 0;
    }

    public void setMutantId(int id) {
        mutantsId = id;
    }

    public int getCallingCount() {
        return callingCount;
    }
}
