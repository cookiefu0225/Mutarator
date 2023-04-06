package edu.illinois.mutarator.unaryexpr;

import com.github.javaparser.ast.expr.UnaryExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class Increment extends VoidVisitorAdapter {
    /**
     * Negate Conditional Mutatants only negate simple binary expressions
     * p && q -> !p || !q is not supported now
     * @param n
     * @param org
     */
    @Override
    public void visit(UnaryExpr n, Object org) {
        super.visit(n, org);

        UnaryExpr.Operator op = n.getOperator();

        switch (op) {
            case PREFIX_DECREMENT -> n.setOperator(UnaryExpr.Operator.PREFIX_INCREMENT);
            case PREFIX_INCREMENT -> n.setOperator(UnaryExpr.Operator.PREFIX_DECREMENT);
            case POSTFIX_INCREMENT -> n.setOperator(UnaryExpr.Operator.POSTFIX_DECREMENT);
            case POSTFIX_DECREMENT -> n.setOperator(UnaryExpr.Operator.POSTFIX_INCREMENT);

        }

//        System.out.println(n);
    }


}
