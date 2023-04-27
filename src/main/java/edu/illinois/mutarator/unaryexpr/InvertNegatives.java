package edu.illinois.mutarator.unaryexpr;

import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.UnaryExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class InvertNegatives extends VoidVisitorAdapter {

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
        Expression expr = n.getExpression();

        if(!expr.isIntegerLiteralExpr()){
            if(mutateMode){
                switch (op) {
                    case MINUS -> {
                        if(mutantsId == callingCount) {
                            n.setOperator(UnaryExpr.Operator.PLUS);
                        }
                        callingCount ++;
                    }
                }
            }else {
                switch (op){
                    case  MINUS -> pointCount++;
                }
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
