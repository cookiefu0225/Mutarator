package edu.illinois.mutarator.experimental;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.ReturnStmt;
import edu.illinois.mutarator.Mutarator;

public class ArgumentPropagation extends Mutarator {
    @Override
    public void visit(MethodDeclaration md, Object obj){
        super.visit(md, obj);
        if (mutateMode) {

            md.walk(ReturnStmt.class, returnStmt -> {
                returnStmt.walk(MethodCallExpr.class, mc ->{

                    NodeList<Expression> arguments = mc.getArguments();
                    if(arguments.size() != 0){
                        if (mutantsId == callingCount) {
                            returnStmt.setExpression(arguments.get(0));
                        }
                        callingCount ++;
                    }

                });

            });
        } else {
            md.walk(ReturnStmt.class, returnStmt -> {
                returnStmt.walk(MethodCallExpr.class, mc ->{

                    NodeList<Expression> arguments = mc.getArguments();
                    if(arguments.size() != 0){
                        pointCount++;
                    }

                });

            });
        }
    }
}
