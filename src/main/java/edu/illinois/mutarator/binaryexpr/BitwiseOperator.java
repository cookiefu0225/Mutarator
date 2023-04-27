package edu.illinois.mutarator.binaryexpr;

import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.BooleanLiteralExpr;
import edu.illinois.mutarator.Mutarator;

import java.util.Random;

public class BitwiseOperator extends Mutarator {

    @Override
    public void visit(BinaryExpr n, Object obj) {
        super.visit(n, obj);

        BinaryExpr.Operator op = n.getOperator();
        Random rand = new Random();
        int upperbound = 3;

        if (mutateMode) {
            if (op == BinaryExpr.Operator.BINARY_AND) {
                if (mutantsId == callingCount) {
                    int decision = rand.nextInt(upperbound);

                    switch (decision) {
                        case 0 -> n.setOperator(BinaryExpr.Operator.BINARY_OR);
                        // p & true -> p
                        case 1 -> n.setRight(new BooleanLiteralExpr(true));
                        // true & q -> q
                        case 2 -> n.setLeft(new BooleanLiteralExpr(true));
                    }
                }
                callingCount++;

            } else if (op == BinaryExpr.Operator.BINARY_OR) {
                if (mutantsId == callingCount) {
                    int decision = rand.nextInt(upperbound);

                    switch (decision) {
                        case 0 -> n.setOperator(BinaryExpr.Operator.BINARY_AND);
                        case 1 -> n.setRight(new BooleanLiteralExpr(false));
                        case 2 -> n.setLeft(new BooleanLiteralExpr(false));
                    }
                }
                callingCount++;
            }
        } else {
            if (op == BinaryExpr.Operator.BINARY_AND ||
            op == BinaryExpr.Operator.BINARY_OR) {
                pointCount ++;
            }
        }
    }
}
