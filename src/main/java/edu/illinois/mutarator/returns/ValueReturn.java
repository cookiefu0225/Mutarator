package edu.illinois.mutarator.returns;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.HashSet;
import java.util.Set;

public class ValueReturn extends VoidVisitorAdapter {
    Set<String> primitiveTypes = new HashSet<>();
    FalseReturn falseReturnTool = new FalseReturn();
    TrueReturn trueReturnTool = new TrueReturn();
    boolean falseRtFlag = false;
    boolean trueRtFlag = false;

    public ValueReturn() {
        for (PrimitiveType.Primitive c : PrimitiveType.Primitive.values()) {
            primitiveTypes.add(c.asString());
        }
    }

    // flag means want true return or false return
    public ValueReturn(boolean flag) {
        this();
        if (flag) {
            trueRtFlag = true;
        } else {
            falseRtFlag = true;
        }
    }

    @Override
    public void visit(MethodDeclaration md, Object obj) {
//        System.out.println(md.toString());
        super.visit(md, obj);

        String returnType = md.getType().toString();

        md.walk(ReturnStmt.class, returnStmt -> {
            switch (returnType) {
                case "String" -> {
                    StringLiteralExpr sle = new StringLiteralExpr("");
                    returnStmt.setExpression(sle);
                }
                case "Optional" -> returnStmt.setExpression(new MethodCallExpr(new NameExpr("Optional"), "empty"));
                case "List", "Collection" ->
                        returnStmt.setExpression(new MethodCallExpr(new NameExpr("Collections"), "emptyList"));
                case "Set" -> returnStmt.setExpression(new MethodCallExpr(new NameExpr("Collections"), "emptySet"));
                case "Integer", "Float", "Character", "Short", "Long", "Double" -> {
                    IntegerLiteralExpr ile = new IntegerLiteralExpr(0);
                    returnStmt.setExpression(ile);
                }
                case "Boolean", "boolean" -> {
                    if (trueRtFlag) {
                        trueReturnTool.visit(md, obj);
                    }
                    if (falseRtFlag) {
                        falseReturnTool.visit(md, obj);
                    }
                }
                default -> {
                    // NullReturn expression
                    if (!primitiveTypes.contains(returnType)) {
                        returnStmt.setExpression(new NullLiteralExpr());
                    } else if (!returnType.equals("byte")){
                        // int short long char float double
                        IntegerLiteralExpr ile = new IntegerLiteralExpr(0);
                        returnStmt.setExpression(ile);
                    }
                }
            }
        });
    }
}
