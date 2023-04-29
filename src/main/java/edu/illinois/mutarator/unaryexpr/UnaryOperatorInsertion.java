package edu.illinois.mutarator.unaryexpr;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.*;
import edu.illinois.mutarator.Mutarator;

import java.util.Random;


public class UnaryOperatorInsertion extends Mutarator {
    @Override
    public void visit(BinaryExpr n, Object obj) {
        super.visit(n, obj);

        Expression left = n.getLeft();
        Expression right = n.getRight();
        boolean leftValid = left.isNameExpr();
        boolean rightValid = right.isNameExpr();

        if (leftValid) {
//            System.out.println(n);
//            System.out.println(left);
            if (mutateMode) {
                if (mutantsId == callingCount) {
                    left = generateMutation(left);
                    n.setLeft(left);
                }
                callingCount ++;
            } else {
                pointCount ++;
            }
        }

        if (rightValid) {
//            System.out.println(n);
//            System.out.println(right);
            if (mutateMode) {
                if (mutantsId == callingCount) {
                    right = generateMutation(right);
                    n.setRight(right);
                }
                callingCount ++;
            } else {
                pointCount ++;
            }
        }

    }

    @Override
    public void visit(MethodCallExpr mce, Object obj) {
        super.visit(mce, obj);

        NodeList<Expression> args = mce.getArguments();

        for (int i = 0; i < args.size(); i++) {
            Expression e = args.get(i);
            if (e.isNameExpr()) {
                if (mutateMode) {
                    if (mutantsId == callingCount) {
                        e = generateMutation(e);
                        args.set(i, e);
                        mce.setArguments(args);
                    }
                    callingCount ++;
                } else {
                    pointCount ++;
                }
            }
        }
    }

    private Expression generateMutation(Expression e) {
        NameExpr ne = e.asNameExpr();

        if (!debugFlag) {
            Random rand = new Random();
            int selection = rand.nextInt(4);

            switch (selection) {
                case 0 -> {
                    return new UnaryExpr(ne, UnaryExpr.Operator.PREFIX_INCREMENT);
                }
                case 1 -> {
                    return new UnaryExpr(ne, UnaryExpr.Operator.PREFIX_DECREMENT);
                }
                case 2 -> {
                    return new UnaryExpr(ne, UnaryExpr.Operator.POSTFIX_INCREMENT);
                }
                default -> {
                    return new UnaryExpr(ne, UnaryExpr.Operator.POSTFIX_DECREMENT);
                }
            }
        } else {
            return new UnaryExpr(ne, UnaryExpr.Operator.PREFIX_INCREMENT);
        }
    }

    boolean debugFlag = false;
}
