package edu.illinois.mutarator.returns;

import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.UnaryExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class VoidMethodCall extends VoidVisitorAdapter {
    @Override
    public void visit(MethodCallExpr n, Object org) {
        super.visit(n, org);

        if(n.calculateResolvedType().toString().equals("voidType")){
            n.remove();
        }


    }
}
