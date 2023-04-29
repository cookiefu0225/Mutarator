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
        assertEquals(13, ad.getMutantsNumber());

        ad.switchToMutation();
        ad.setMutantId(6);
        ad.visit(cu, null);
        ad.resetCallingCount();

        CompilationUnit expected = srt.parse("sample.answer", "AODAnswer.java");
        assertEquals(expected, cu);

    }
}
