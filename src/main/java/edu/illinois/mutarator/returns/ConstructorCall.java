package edu.illinois.mutarator.returns;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.NullLiteralExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import edu.illinois.mutarator.Mutarator;

public class ConstructorCall extends Mutarator {

    @Override
    public void visit(ObjectCreationExpr oc, Object obj){
        if(mutateMode){
            if(mutantsId == callingCount){
                if(oc.getParentNode().isPresent()){
                    Node parentNode = oc.getParentNode().get();
                    for(Node childNode: parentNode.getChildNodes()){
                        if (childNode instanceof Expression) {
                            Expression child = (Expression) childNode;

                            if (child.isObjectCreationExpr()) {
                                Node replacementNode = new NullLiteralExpr();
                                parentNode.replace(childNode, replacementNode);
                            }
                        }
                    }
                }

                callingCount++;
            }
        }else {
            pointCount ++;
        }

    }
}
