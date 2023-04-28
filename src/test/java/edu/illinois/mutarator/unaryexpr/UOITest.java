package edu.illinois.mutarator.unaryexpr;

import static org.junit.Assert.*;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.utils.CodeGenerationUtils;
import com.github.javaparser.utils.SourceRoot;
import edu.illinois.mutarator.binaryexpr.ConditionalBoundaryTest;
import org.junit.Test;

public class UOITest {
    @Test
    public void test1() {
        SourceRoot srt = new SourceRoot(
                CodeGenerationUtils.mavenModuleRoot(ConditionalBoundaryTest.class)
                        .resolve("target/test-classes"));

        CompilationUnit cu = srt.parse("sample", "ToyValueReturn.java");

        UnaryOperatorInsertion uoi = new UnaryOperatorInsertion();
        uoi.debugFlag = true;
        uoi.visit(cu, null);
        int totalMutant = uoi.getMutantsNumber();
        assertEquals(2, totalMutant);
        uoi.setMutantId(0);
        uoi.switchToMutation();
        uoi.visit(cu, null);
        uoi.resetCallingCount();

        CompilationUnit expected = srt.parse("sample.answer", "UOIAnswerBinary.java");
        assertEquals(expected, cu);

    }

    @Test
    public void test2() {
        SourceRoot srt = new SourceRoot(
                CodeGenerationUtils.mavenModuleRoot(ConditionalBoundaryTest.class)
                        .resolve("target/test-classes"));

        CompilationUnit cu = srt.parse("sample", "UOI.java");

        UnaryOperatorInsertion uoi = new UnaryOperatorInsertion();
        uoi.debugFlag = true;

        uoi.visit(cu, null);
        assertEquals(4, uoi.getMutantsNumber());
        uoi.switchToMutation();
        uoi.setMutantId(0);
        uoi.visit(cu, null);
        uoi.resetCallingCount();

        CompilationUnit expected1 = srt.parse("sample.answer", "UOIAnswerMix.java");
        assertEquals(expected1, cu);

        uoi.resetPointCount();
        uoi.switchToCount();
        uoi.visit(cu, null);
        assertEquals(3, uoi.getMutantsNumber());
        uoi.switchToMutation();
        uoi.setMutantId(1);
        uoi.visit(cu, null);
        uoi.resetCallingCount();

        CompilationUnit expected2 = srt.parse("sample.answer", "UOIAnswerMix2.java");
        assertEquals(expected2, cu);
    }
}
