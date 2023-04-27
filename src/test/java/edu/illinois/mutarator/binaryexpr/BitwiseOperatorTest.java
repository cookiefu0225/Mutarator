package edu.illinois.mutarator.binaryexpr;

import static org.junit.Assert.*;
import static com.github.javaparser.StaticJavaParser.parse;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.BooleanLiteralExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.utils.CodeGenerationUtils;
import com.github.javaparser.utils.SourceRoot;
import org.junit.Test;

public class BitwiseOperatorTest {
    @Test
    public void test1() {
        CompilationUnit cu = parse("""
                package sample;
                
                public class Example {
                    public void m1() {
                        int a = 0;
                        int b = 1;
                        int c = 3;
                        boolean var = (a == b) & (b == c);
                    }
                }
                """);

        BitwiseOperator bo = new BitwiseOperator();
        bo.visit(cu, null);
        assertEquals(1, bo.getMutantsNumber());
        bo.switchToMutation();
        bo.setMutantId(0);
        bo.visit(cu, null);
        bo.resetCallingCount();

        cu.walk(BinaryExpr.class, expr -> {
            BinaryExpr.Operator op = expr.getOperator();

            if (op == BinaryExpr.Operator.BINARY_AND) {
                Expression left = expr.getLeft();
                Expression right = expr.getRight();

                if (left.isBooleanLiteralExpr()) {
                    assertEquals(new BooleanLiteralExpr(true), left.asBooleanLiteralExpr());
                }
                if (right.isBooleanLiteralExpr()) {
                    assertEquals(new BooleanLiteralExpr(true), right.asBooleanLiteralExpr());
                }

                assertEquals(false, left.isBooleanLiteralExpr() && right.isBooleanLiteralExpr());
            } else if (op == BinaryExpr.Operator.BINARY_OR) {
                Expression left = expr.getLeft();
                Expression right = expr.getRight();
                assertEquals(false, left.isBooleanLiteralExpr() && right.isBooleanLiteralExpr());
            }
        });
    }
}
