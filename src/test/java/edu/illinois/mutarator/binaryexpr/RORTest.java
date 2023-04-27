package edu.illinois.mutarator.binaryexpr;

import static com.github.javaparser.StaticJavaParser.parse;
import static org.junit.Assert.*;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.utils.CodeGenerationUtils;
import com.github.javaparser.utils.SourceRoot;
import org.junit.Test;

public class RORTest {
    @Test
    public void test1() {
        SourceRoot srt = new SourceRoot(
                CodeGenerationUtils.mavenModuleRoot(ConditionalBoundaryTest.class)
                        .resolve("target/test-classes"));

        CompilationUnit cu = srt.parse("sample", "ToyROR.java");

        RelationalOperatorReplacement ror = new RelationalOperatorReplacement();
        ror.visit(cu, null);
        assertEquals(1, ror.getMutantsNumber());
        ror.switchToMutation();
        ror.setMutantId(0);
        ror.visit(cu, null);
        ror.resetCallingCount();

        ror.resetPointCount();
        assertEquals(0, ror.getMutantsNumber());

        cu.walk(BinaryExpr.class, expr -> {
            BinaryExpr.Operator op = expr.getOperator();
            boolean valid = op == BinaryExpr.Operator.EQUALS || op == BinaryExpr.Operator.NOT_EQUALS ||
                    op == BinaryExpr.Operator.GREATER || op == BinaryExpr.Operator.GREATER_EQUALS ||
                    op == BinaryExpr.Operator.LESS_EQUALS;
            assertTrue(valid);
        });

    }
}
