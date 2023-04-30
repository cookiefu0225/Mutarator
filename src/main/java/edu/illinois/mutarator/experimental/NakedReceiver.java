package edu.illinois.mutarator.experimental;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.stmt.ReturnStmt;
import edu.illinois.mutarator.Mutarator;
import edu.illinois.mutarator.returns.FalseReturn;

import java.util.List;

public class NakedReceiver extends Mutarator {
//    public boolean containsNameExpr = false;
//    public boolean containsSimpleName = false;
    @Override
    public void visit(MethodDeclaration md, Object obj){
        super.visit(md, obj);
        if (mutateMode) {

            md.walk(ReturnStmt.class, returnStmt -> {
                returnStmt.walk(MethodCallExpr.class, mc ->{

                    List<Node> childNodes = mc.getChildNodes();
                    boolean isSimpleName = false;
                    boolean isNameExpr = false;
                    NameExpr nameExpr = new NameExpr();
                    for(Node childNode : childNodes){
                        if (childNode instanceof SimpleName){
                            isSimpleName = true;
                        }
                        if(childNode instanceof NameExpr){
                            isNameExpr = true;
                            nameExpr = (NameExpr) childNode;
                        }

                    }
                    if(isNameExpr && isSimpleName) {
                        if (mutantsId == callingCount) {
                            returnStmt.setExpression(nameExpr);
                        }
                        callingCount++;
                    }

                });

            });
        } else {
            md.walk(ReturnStmt.class, returnStmt -> {
                returnStmt.walk(MethodCallExpr.class, mc ->{

                    List<Node> childNodes = mc.getChildNodes();
                    boolean isSimpleName = false;
                    boolean isNameExpr = false;
                    for(Node childNode : childNodes){
                        if (childNode instanceof SimpleName){
                            isSimpleName = true;
                        }
                        if(childNode instanceof NameExpr){
                            isNameExpr = true;

                        }

                    }
//                    containsNameExpr = isNameExpr;
//                    containsSimpleName = isSimpleName;
                    if(isNameExpr && isSimpleName) {
                        pointCount++;
                    }

                });

            });
        }
    }
}
