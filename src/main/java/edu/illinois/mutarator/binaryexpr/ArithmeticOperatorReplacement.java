package edu.illinois.mutarator.binaryexpr;

import com.github.javaparser.ast.expr.BinaryExpr;
import edu.illinois.mutarator.Mutarator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ArithmeticOperatorReplacement extends Mutarator {
    List<BinaryExpr.Operator> supporedMutator = new ArrayList<>();

    public ArithmeticOperatorReplacement() {
        supporedMutator.add(BinaryExpr.Operator.PLUS);
        supporedMutator.add(BinaryExpr.Operator.MINUS);
        supporedMutator.add(BinaryExpr.Operator.MULTIPLY);
        supporedMutator.add(BinaryExpr.Operator.DIVIDE);
        supporedMutator.add(BinaryExpr.Operator.REMAINDER);
    }

    @Override
    public void visit(BinaryExpr n, Object obj) {
        BinaryExpr.Operator op = n.getOperator();
        Random rand = new Random();

        if (mutateMode) {
            switch (op) {
                case PLUS, MINUS, MULTIPLY, DIVIDE, REMAINDER -> {
                    if (mutantsId == callingCount) {
                        BinaryExpr.Operator oldOper = op;
                        while (op == oldOper) {
                            int number = debugMode ? 4 : rand.nextInt(supporedMutator.size());
                            op = supporedMutator.get(number);
                        }

                        n.setOperator(op);
                    }
                    callingCount ++;
                }
            }
        } else {
            switch (op) {
                case PLUS, MINUS, MULTIPLY, DIVIDE, REMAINDER -> pointCount ++;
            }
        }
    }

    boolean debugMode = false;
}
