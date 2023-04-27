package edu.illinois.mutarator.unaryexpr;

import com.github.javaparser.ast.expr.UnaryExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class Increment extends VoidVisitorAdapter {

    private int pointCount = 0;
    private int callingCount = 0;
    private int mutantsId = 0;
    private boolean mutateMode = false;
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

        if(mutateMode){
            switch (op) {
                case PREFIX_DECREMENT -> {
                    if(mutantsId == callingCount) {
                        n.setOperator(UnaryExpr.Operator.PREFIX_INCREMENT);
                    }
                    callingCount ++;
                }
                case PREFIX_INCREMENT -> {
                    if(mutantsId == callingCount) {
                        n.setOperator(UnaryExpr.Operator.PREFIX_DECREMENT);
                    }
                    callingCount ++;
                }
                case POSTFIX_INCREMENT -> {
                    if(mutantsId == callingCount) {
                        n.setOperator(UnaryExpr.Operator.POSTFIX_DECREMENT);
                    }
                    callingCount ++;
                }
                case POSTFIX_DECREMENT -> {
                    if(mutantsId == callingCount) {
                        n.setOperator(UnaryExpr.Operator.POSTFIX_INCREMENT);
                    }
                    callingCount ++;
                }

            }
        }else{
            switch (op){
                case PREFIX_DECREMENT, PREFIX_INCREMENT, POSTFIX_INCREMENT, POSTFIX_DECREMENT -> pointCount++;
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


}
