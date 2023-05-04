package edu.illinois.mutarator.variables;

import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.type.Type;
import edu.illinois.mutarator.Mutarator;

import java.util.Optional;
import java.util.Random;

public class ConstantReplacementMutator extends Mutarator {
    @Override
    public void visit(AssignExpr ae, Object obj) {
        super.visit(ae, obj);

        Expression assignedValue = ae.getValue();
        boolean valid = assignedValue.isBooleanLiteralExpr() || assignedValue.isIntegerLiteralExpr() ||
                assignedValue.isLongLiteralExpr() || assignedValue.isDoubleLiteralExpr();

        if (!valid) {return;}

        if (mutateMode) {
            if (mutantsId == callingCount) {
//                System.out.println(ae);
                mutate(assignedValue);
            }

            callingCount++;
        } else {
            pointCount++;
        }
    }

    @Override
    public void visit(VariableDeclarationExpr n, Object obj) {
        super.visit(n, obj);

        NodeList<VariableDeclarator> vars = n.getVariables();
        if (vars.size() == 0) {
            return;
        }

        // check type
        Type t = vars.get(0).getType();
        String name = t.asString();
        boolean valid = name.equals("boolean") || name.equals("short") || name.equals("int") ||
                name.equals("double") || name.equals("float") || name.equals("long") || name.equals("byte");

        // not final
        valid = valid && !n.getModifiers().contains(Modifier.finalModifier());
        if (!valid) {
            return;
        }

        for (VariableDeclarator dec : vars) {
            Optional<Expression> temp = dec.getInitializer();
            Expression value = temp.orElse(null);
            if (value == null) {
                continue;
            }

            valid = value.isBooleanLiteralExpr() || value.isIntegerLiteralExpr() ||
                    value.isLongLiteralExpr() || value.isDoubleLiteralExpr();
            if (!valid) {
                continue;
            }

            if (mutateMode) {
                if (mutantsId == callingCount) {
//                    System.out.println(n);
                    mutate(value);
                }
                callingCount ++;
            } else {
                pointCount ++;
            }
        }

    }

    private void mutate(Expression assignedValue) {
        if (assignedValue.isBooleanLiteralExpr()) {
            BooleanLiteralExpr repr = assignedValue.asBooleanLiteralExpr();
            boolean originalValue = repr.getValue();
            repr.setValue(!originalValue);
        } else if (assignedValue.isIntegerLiteralExpr()) {
            IntegerLiteralExpr repr = assignedValue.asIntegerLiteralExpr();
            int originalValue = repr.asInt();
            originalValue = generateMutation(originalValue);
            repr.setInt(originalValue);
        } else if (assignedValue.isLongLiteralExpr()) {
            LongLiteralExpr repr = assignedValue.asLongLiteralExpr();
            long originalValue = repr.asLong();
            originalValue = generateMutation(originalValue);
            repr.setLong(originalValue);
        } else {
            // remain double expression
            DoubleLiteralExpr repr = assignedValue.asDoubleLiteralExpr();
            double originalValue = repr.asDouble();
            originalValue = generateMutation(originalValue);
            repr.setDouble(originalValue);
        }
    }

    private long generateMutation(long originalValue) {
        if (!debugFlag) {
            Random rand = new Random();
            long oldVal = originalValue;

            while (oldVal == originalValue) {
                int selection = rand.nextInt(6);
                switch (selection) {
                    case 0 -> originalValue = 1;
                    case 1 -> originalValue = 0;
                    case 2 -> originalValue = -1;
                    case 3 -> originalValue = -originalValue;
                    case 4 -> originalValue += 1;
                    case 5 -> originalValue -= 1;
                }
            }
        } else {
            // mutate to 1 for testing
            originalValue = 1;
        }

        return originalValue;
    }

    private int generateMutation(int originalValue) {
        return (int) generateMutation((long) originalValue);
    }

    private double generateMutation(double originalValue) {
        if (!debugFlag) {
            Random rand = new Random();
            double oldVal = originalValue;

            while (oldVal == originalValue) {
                int selection = rand.nextInt(6);
                switch (selection) {
                    case 0 -> originalValue = 1.0;
                    case 1 -> originalValue = 0.0;
                    case 2 -> originalValue = -1.0;
                    case 3 -> originalValue = -originalValue;
                    case 4 -> originalValue += 1.0;
                    case 5 -> originalValue -= 1.0;
                }
            }
        } else {
            // mutate to 1 for testing
            originalValue = 1.0;
        }

        return originalValue;
    }

    // used to remove randomize
    boolean debugFlag = false;
}
