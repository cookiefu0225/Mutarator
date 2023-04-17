package edu.illinois.mutarator.returns;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.BooleanLiteralExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.Optional;

public class FalseReturn extends VoidVisitorAdapter {
    @Override
    public void visit(MethodDeclaration md, Object obj) {
//        System.out.println(md.toString());
        super.visit(md, obj);

        String returnType = md.getType().toString();

        if (returnType.equals("boolean") || returnType.equals("Boolean")) {
            md.walk(ReturnStmt.class, returnStmt -> {
                BooleanLiteralExpr ble = new BooleanLiteralExpr(false);
                returnStmt.setExpression(ble);
            });
        }


//        System.out.println("======");
    }
}
