package edu.illinois.mutarator.binaryexpr;

import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class NegateConditional extends VoidVisitorAdapter {
    /**
     * Negate Conditional Mutatants only negate simple binary expressions
     * p && q -> !p || !q is not supported now
     * @param n
     * @param org
     */
    @Override
    public void visit(BinaryExpr n, Object org) {
        super.visit(n, org);

        BinaryExpr.Operator op = n.getOperator();

        switch (op) {
            case EQUALS -> n.setOperator(BinaryExpr.Operator.NOT_EQUALS);
            case NOT_EQUALS -> n.setOperator(BinaryExpr.Operator.EQUALS);
            case GREATER -> n.setOperator(BinaryExpr.Operator.LESS_EQUALS);
            case GREATER_EQUALS -> n.setOperator(BinaryExpr.Operator.LESS);
            case LESS -> n.setOperator(BinaryExpr.Operator.GREATER_EQUALS);
            case LESS_EQUALS -> n.setOperator(BinaryExpr.Operator.GREATER);
        }

        System.out.println(n);
    }
}
