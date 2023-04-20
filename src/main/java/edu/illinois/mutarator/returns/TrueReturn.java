package edu.illinois.mutarator.returns;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.BooleanLiteralExpr;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class TrueReturn extends VoidVisitorAdapter {
    @Override
    public void visit(MethodDeclaration md, Object obj) {
//        System.out.println(md.toString());
        super.visit(md, obj);

        String returnType = md.getType().toString();

        if (returnType.equals("boolean") || returnType.equals("Boolean")) {
            md.walk(ReturnStmt.class, returnStmt -> {
                BooleanLiteralExpr ble = new BooleanLiteralExpr(true);
                returnStmt.setExpression(ble);
            });
        }

    }
}
