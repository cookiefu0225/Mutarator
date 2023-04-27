package edu.illinois.mutarator;

import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class Mutarator extends VoidVisitorAdapter {
    // Number of valid mutating point
    protected int pointCount = 0;
    // Number of passed point
    protected int callingCount = 0;
    // Target mutant, means <mutantsId>th point
    protected int mutantsId = 0;
    // Mutation or counting phase
    protected boolean mutateMode = false;

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

    public void resetPointCount() {pointCount = 0;}

    // Add new override functions supported by VoidVisitAdapter if needed
    @Override
    public void visit(BinaryExpr be, Object obj) {
        super.visit(be, obj);
    }
}
