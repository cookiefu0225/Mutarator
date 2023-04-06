package edu.illinois.mutarator.binaryexpr;

import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.UnaryExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class Math extends VoidVisitorAdapter {
    /**
     * @param n
     * @param org
     */
    public void visit(BinaryExpr n, Object org) {

        super.visit(n, org);

        BinaryExpr.Operator op = n.getOperator();

        switch (op) {
            case PLUS-> n.setOperator(BinaryExpr.Operator.MINUS);
            case MINUS -> n.setOperator(BinaryExpr.Operator.PLUS);
            case MULTIPLY -> n.setOperator(BinaryExpr.Operator.DIVIDE);
            case DIVIDE -> n.setOperator(BinaryExpr.Operator.MULTIPLY);
            case REMAINDER-> n.setOperator(BinaryExpr.Operator.MULTIPLY);
            case BINARY_AND -> n.setOperator(BinaryExpr.Operator.BINARY_OR);
            case BINARY_OR -> n.setOperator(BinaryExpr.Operator.BINARY_AND);
            case XOR -> n.setOperator(BinaryExpr.Operator.BINARY_AND);
            case LEFT_SHIFT -> n.setOperator(BinaryExpr.Operator.SIGNED_RIGHT_SHIFT);
            case SIGNED_RIGHT_SHIFT -> n.setOperator(BinaryExpr.Operator.LEFT_SHIFT);
            case UNSIGNED_RIGHT_SHIFT -> n.setOperator(BinaryExpr.Operator.LEFT_SHIFT);
        }


    }
}
