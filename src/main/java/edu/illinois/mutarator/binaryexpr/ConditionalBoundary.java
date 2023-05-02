package edu.illinois.mutarator.binaryexpr;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import edu.illinois.mutarator.Mutarator;

import java.util.HashSet;
import java.util.Set;

public class ConditionalBoundary extends Mutarator {

    /**
     * Override visit function w.r.t. what we're going to do
     * Here I'm mutating the boundary conditions, such as > to >= and < to <=, vice versa
     * @param n
     * @param org
     */
    @Override
    public void visit(BinaryExpr n, Object org) {
        super.visit(n, org);

        BinaryExpr.Operator op = n.getOperator();

        if (mutateMode) {
            // do mutation
            switch (op) {
                case GREATER -> {
                    if (mutantsId == callingCount) {
                        n.setOperator(BinaryExpr.Operator.GREATER_EQUALS);
                    }
                    callingCount ++;
                }
                case GREATER_EQUALS -> {
                    if (mutantsId == callingCount) {
                        n.setOperator(BinaryExpr.Operator.GREATER);
                    }
                    callingCount ++;
                }
                case LESS -> {
                    if (mutantsId == callingCount) {
                        n.setOperator(BinaryExpr.Operator.LESS_EQUALS);
                    }
                    callingCount ++;
                }
                case LESS_EQUALS -> {
                    if (mutantsId == callingCount) {
                        n.setOperator(BinaryExpr.Operator.LESS);
                    }
                    callingCount ++;
                }
            }
        } else {
            // count phase
            switch (op) {
                case GREATER, GREATER_EQUALS, LESS, LESS_EQUALS -> pointCount ++;
            }
        }
    }

}
