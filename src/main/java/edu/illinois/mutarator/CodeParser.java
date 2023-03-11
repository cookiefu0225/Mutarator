package edu.illinois.mutarator;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.ast.body.MethodDeclaration;

import java.util.ArrayList;
import java.util.List;

public class CodeParser extends VoidVisitorAdapter{
    List<MethodDeclaration> methods;

    public CodeParser() {
        methods = new ArrayList<>();
    }

    @Override
    public void visit(MethodDeclaration d, Object obj) {
        super.visit(d, obj);

        this.methods.add(d);
    }

    public String getMethod(int idx) {
        return this.methods.get(idx).toString();
    }

}
