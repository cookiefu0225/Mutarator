package edu.illinois.mutarator.unaryexpr;

import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.UnaryExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class InvertNegatives extends VoidVisitorAdapter {
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
            case MINUS -> n.setOperator(UnaryExpr.Operator.PLUS);
        }

//        System.out.println(n);
    }
}
