package edu.illinois.mutarator.binaryexpr;

import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import edu.illinois.mutarator.Mutarator;

public class NegateConditional extends Mutarator {

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

        if (mutateMode) {
            switch (op) {
                case EQUALS -> {
                    if (mutantsId == callingCount) {
                        n.setOperator(BinaryExpr.Operator.NOT_EQUALS);
                    }
                    callingCount++;
                }
                case NOT_EQUALS -> {
                    if (mutantsId == callingCount) {
                        n.setOperator(BinaryExpr.Operator.EQUALS);
                    }
                    callingCount++;
                }
                case GREATER -> {
                    if (mutantsId == callingCount) {
                        n.setOperator(BinaryExpr.Operator.LESS_EQUALS);
                    }
                    callingCount++;
                }
                case GREATER_EQUALS -> {
                    if (mutantsId == callingCount) {
                        n.setOperator(BinaryExpr.Operator.LESS);
                    }
                    callingCount++;
                }
                case LESS -> {
                    if (mutantsId == callingCount) {
                        n.setOperator(BinaryExpr.Operator.GREATER_EQUALS);
                    }
                    callingCount++;
                }
                case LESS_EQUALS -> {
                    if (mutantsId == callingCount) {
                        n.setOperator(BinaryExpr.Operator.GREATER);
                    }
                    callingCount++;
                }
            }
        } else {
            switch (op) {
                case EQUALS, NOT_EQUALS, GREATER, GREATER_EQUALS, LESS, LESS_EQUALS -> pointCount ++;
            }
        }

//        System.out.println(n);
    }
}
