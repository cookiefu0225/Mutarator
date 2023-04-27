package edu.illinois.mutarator.binaryexpr;

import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class Math extends VoidVisitorAdapter {

    private int pointCount = 0;
    private int callingCount = 0;
    private int mutantsId = 0;
    private boolean mutateMode = false;

    /**
     * @param n
     * @param org
     */
    public void visit(BinaryExpr n, Object org) {

        super.visit(n, org);

        BinaryExpr.Operator op = n.getOperator();
        Expression left = n.getLeft();
        Expression right = n.getRight();
        if (!left.isStringLiteralExpr() && !right.isStringLiteralExpr()){
            if(mutateMode){
                switch (op) {
                    case PLUS -> {
                        if (mutantsId == callingCount) {
                            n.setOperator(BinaryExpr.Operator.MINUS);
                        }
                        callingCount++;
                    }
                    case MINUS -> {
                        if (mutantsId == callingCount) {
                            n.setOperator(BinaryExpr.Operator.PLUS);
                        }
                        callingCount++;
                    }
                    case MULTIPLY -> {
                        if (mutantsId == callingCount) {
                            n.setOperator(BinaryExpr.Operator.DIVIDE);
                        }
                        callingCount++;
                    }
                    case DIVIDE,REMAINDER -> {
                        if (mutantsId == callingCount) {
                            n.setOperator(BinaryExpr.Operator.MULTIPLY);
                        }
                        callingCount++;
                    }
                    case BINARY_AND -> {
                        if (mutantsId == callingCount) {
                            n.setOperator(BinaryExpr.Operator.BINARY_OR);
                        }
                        callingCount++;
                    }
                    case BINARY_OR, XOR -> {
                        if (mutantsId == callingCount) {
                            n.setOperator(BinaryExpr.Operator.BINARY_AND);
                        }
                        callingCount++;
                    }
                    case LEFT_SHIFT -> {
                        if (mutantsId == callingCount) {
                            n.setOperator(BinaryExpr.Operator.SIGNED_RIGHT_SHIFT);
                        }
                        callingCount++;
                    }
                    case SIGNED_RIGHT_SHIFT, UNSIGNED_RIGHT_SHIFT -> {
                        if (mutantsId == callingCount) {
                            n.setOperator(BinaryExpr.Operator.LEFT_SHIFT);
                        }
                        callingCount++;
                    }
                }
            }else{
                switch (op){
                    case PLUS, MINUS, MULTIPLY,DIVIDE,REMAINDER,BINARY_AND,BINARY_OR,XOR,LEFT_SHIFT,SIGNED_RIGHT_SHIFT,UNSIGNED_RIGHT_SHIFT ->
                            pointCount ++;
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
