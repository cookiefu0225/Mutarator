package edu.illinois.mutarator.returns;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.UnaryExpr;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class VoidMethodCall extends VoidVisitorAdapter {
    @Override
    public void visit(MethodDeclaration md, Object org) {
        super.visit(md, org);
        if(md.getType().toString().equals("void")){
//            System.out.println(md.getName());
            md.accept(new MethodCallVisitor(), null);
        }



    }
}

class MethodCallVisitor extends ModifierVisitor<Void> {
    @Override
    public Visitable visit(MethodCallExpr n, Void arg) {
        // Found a method call
//        n.remove();
//        System.out.println(n.getScope() + " - " + n.getName());
//        System.out.println(n.getScope() + " - " + n.getName());
//        System.out.println(n.getScope() + " - " + n.getName());
        Node parentNode = n.getParentNode().get();
        Node grandParentNode = parentNode.getParentNode().get();
        System.out.println(grandParentNode.remove(parentNode));

        // Don't forget to call super, it may find more method calls inside the arguments of this method call, for example.
//        super.visit(n, arg);
        return super.visit(n, arg);
    }
}
