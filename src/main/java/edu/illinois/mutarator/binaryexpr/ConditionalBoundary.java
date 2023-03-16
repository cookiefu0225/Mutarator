package edu.illinois.mutarator.binaryexpr;

import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class ConditionalBoundary extends VoidVisitorAdapter {

    /**
     * Override visit function w.r.t. what we're going to do
     * Here I'm mutating the boundary conditions, such as > to >= and < to <=, vice versa
     * @param n
     * @param org
     */
    @Override
    public void visit(BinaryExpr n, Object org) {
        super.visit(n, org);

        BinaryExpr.Operator op = n.getOperator();

        switch (op) {
            case GREATER -> n.setOperator(BinaryExpr.Operator.GREATER_EQUALS);
            case GREATER_EQUALS -> n.setOperator(BinaryExpr.Operator.GREATER);
            case LESS -> n.setOperator(BinaryExpr.Operator.LESS_EQUALS);
            case LESS_EQUALS -> n.setOperator(BinaryExpr.Operator.LESS);
        }
    }
}
