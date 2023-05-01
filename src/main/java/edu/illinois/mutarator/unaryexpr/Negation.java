package edu.illinois.mutarator.unaryexpr;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.ReturnStmt;
import edu.illinois.mutarator.Mutarator;

import java.util.List;
import java.util.Random;

public class Negation extends Mutarator {
    @Override
    public void visit(BinaryExpr n, Object obj) {
        super.visit(n, obj);

        Expression left = n.getLeft();
        Expression right = n.getRight();
        boolean leftValid = left.isNameExpr();
        boolean rightValid = right.isNameExpr();

        if (leftValid) {
//            System.out.println(n);
//            System.out.println(left);
            if (mutateMode) {
                if (mutantsId == callingCount) {
                    left = generateMutation(left);
                    n.setLeft(left);
                }
                callingCount ++;
            } else {
                pointCount ++;
            }
        }

        if (rightValid) {
//            System.out.println(n);
//            System.out.println(right);
            if (mutateMode) {
                if (mutantsId == callingCount) {
                    right = generateMutation(right);
                    n.setRight(right);
                }
                callingCount ++;
            } else {
                pointCount ++;
            }
        }

    }

    @Override
    public void visit(MethodCallExpr mce, Object obj) {
        super.visit(mce, obj);

        NodeList<Expression> args = mce.getArguments();

        for (int i = 0; i < args.size(); i++) {
            Expression e = args.get(i);
            if (e.isNameExpr()) {
                if (mutateMode) {
                    if (mutantsId == callingCount) {
                        e = generateMutation(e);
                        args.set(i, e);
                        mce.setArguments(args);
                    }
                    callingCount ++;
                } else {
                    pointCount ++;
                }
            }
        }
    }

    @Override
    public void visit(MethodDeclaration md, Object obj) {
        super.visit(md, obj);

        md.walk(ReturnStmt.class, returnStmt -> {
            returnStmt.walk(NameExpr.class, ne ->{
                if(mutateMode){
                    Expression result = generateMutation(ne);

                    if (mutantsId == callingCount) {
                        returnStmt.setExpression(result);
                    }
                    callingCount++;
                }else {
                    pointCount++;
                }



            });

        });
    }

    private Expression generateMutation(Expression e) {
        NameExpr ne = e.asNameExpr();

        return new UnaryExpr(ne, UnaryExpr.Operator.MINUS);
    }


}
