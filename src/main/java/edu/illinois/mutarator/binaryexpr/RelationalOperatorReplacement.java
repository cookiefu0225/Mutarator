package edu.illinois.mutarator.binaryexpr;

import com.github.javaparser.ast.expr.BinaryExpr;
import edu.illinois.mutarator.Mutarator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RelationalOperatorReplacement extends Mutarator {

    List<BinaryExpr.Operator> supportedMutator = new ArrayList<>();

    public RelationalOperatorReplacement() {
        supportedMutator.add(BinaryExpr.Operator.GREATER);
        supportedMutator.add(BinaryExpr.Operator.GREATER_EQUALS);
        supportedMutator.add(BinaryExpr.Operator.LESS);
        supportedMutator.add(BinaryExpr.Operator.LESS_EQUALS);
        supportedMutator.add(BinaryExpr.Operator.EQUALS);
        supportedMutator.add(BinaryExpr.Operator.NOT_EQUALS);
    }

    @Override
    public void visit(BinaryExpr expr, Object obj) {
        super.visit(expr, obj);

        BinaryExpr.Operator op = expr.getOperator();
        int upperbound = supportedMutator.size();
        Random rand = new Random();

        if (mutateMode) {
            switch (op) {
                case GREATER, LESS, LESS_EQUALS, GREATER_EQUALS, EQUALS, NOT_EQUALS -> {
                    if (mutantsId == callingCount) {
                        int number = rand.nextInt(upperbound);

                        while (supportedMutator.get(number) == op) {
                            number = rand.nextInt();
                        }
                        expr.setOperator(supportedMutator.get(number));
                    }
                    callingCount ++;
                }
            }
        } else {
            switch (op) {
                case GREATER, LESS, LESS_EQUALS, GREATER_EQUALS, EQUALS, NOT_EQUALS -> pointCount++;
            }
        }
    }
}
