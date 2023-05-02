package edu.illinois.mutarator.unaryexpr;

import com.github.javaparser.ast.expr.UnaryExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import edu.illinois.mutarator.Mutarator;

public class Increment extends Mutarator {
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


}
