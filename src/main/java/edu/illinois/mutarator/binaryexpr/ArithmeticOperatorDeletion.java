package edu.illinois.mutarator.binaryexpr;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.Expression;
import edu.illinois.mutarator.Mutarator;

import java.util.Random;

public class ArithmeticOperatorDeletion extends Mutarator {

    @Override
    public void visit(BinaryExpr n, Object obj) {
        super.visit(n, obj);

        BinaryExpr.Operator op = n.getOperator();

        Expression left = n.getLeft();
        Expression right = n.getRight();

        boolean valid = op == BinaryExpr.Operator.MINUS || op == BinaryExpr.Operator.PLUS ||
                op == BinaryExpr.Operator.MULTIPLY || op == BinaryExpr.Operator.DIVIDE ||
                op == BinaryExpr.Operator.REMAINDER;

        if (!valid) {
            return;
        }

        if (mutateMode) {
            if (mutantsId == callingCount) {
                if (!debugMode) {
                    Random rand = new Random();
                    int number = rand.nextInt(2);
                    if (number == 0) {
                        n.replace(left);
                    } else {
                        n.replace(right);
                    }
                } else {
//                    System.out.println(n.replace(left));
                    n.replace(left);
                }
            }
            callingCount ++;
        } else {
            pointCount ++;
        }
    }

    boolean debugMode = false;
}
