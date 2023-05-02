package edu.illinois.mutarator.returns;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.BooleanLiteralExpr;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import edu.illinois.mutarator.Mutarator;

public class FalseReturn extends Mutarator {

    @Override
    public void visit(MethodDeclaration md, Object obj) {
//        System.out.println(md.toString());
        super.visit(md, obj);

        String returnType = md.getType().toString();

        if (returnType.equals("boolean") || returnType.equals("Boolean")) {
            if (mutateMode) {

                md.walk(ReturnStmt.class, returnStmt -> {
                    if (mutantsId == callingCount) {
                        BooleanLiteralExpr ble = new BooleanLiteralExpr(false);
                        returnStmt.setExpression(ble);
                    }
                    callingCount ++;
                });
            } else {
                md.walk(ReturnStmt.class, returnStmt -> {
                    pointCount ++;
                });
            }
        }


//        System.out.println("======");
    }
}
