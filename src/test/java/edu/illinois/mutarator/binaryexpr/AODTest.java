package edu.illinois.mutarator.binaryexpr;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.utils.CodeGenerationUtils;
import com.github.javaparser.utils.SourceRoot;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AODTest {
    @Test
    public void test1() {
        SourceRoot srt = new SourceRoot(
                CodeGenerationUtils.mavenModuleRoot(ConditionalBoundaryTest.class)
                        .resolve("target/test-classes"));

        CompilationUnit cu = srt.parse("sample", "AOR.java");

        ArithmeticOperatorDeletion ad = new ArithmeticOperatorDeletion();
        ad.debugMode = true;
        ad.visit(cu, null);
        assertEquals(7, ad.getMutantsNumber());

        ad.switchToMutation();
        ad.setMutantId(2);
        ad.visit(cu, null);
        ad.resetCallingCount();

        ad.resetPointCount();
        assertEquals(0, ad.getMutantsNumber());

        CompilationUnit expected = srt.parse("sample.answer", "AODAnswer.java");
        assertEquals(expected, cu);

    }
}
