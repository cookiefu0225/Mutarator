package edu.illinois.mutarator.unaryexpr;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.utils.CodeGenerationUtils;
import com.github.javaparser.utils.SourceRoot;
import edu.illinois.mutarator.binaryexpr.ConditionalBoundaryTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NegationTest {
    @Test
    public void test1() {
        SourceRoot srt = new SourceRoot(
                CodeGenerationUtils.mavenModuleRoot(ConditionalBoundaryTest.class)
                        .resolve("target/test-classes"));

        CompilationUnit cu = srt.parse("sample", "ToyNegation.java");

        Negation ne = new Negation();
        ne.visit(cu, null);
        int totalMutant = ne.getMutantsNumber();
        assertEquals(1, totalMutant);
        ne.setMutantId(0);
        ne.switchToMutation();
        ne.visit(cu, null);
        ne.resetCallingCount();

        CompilationUnit expected = srt.parse("sample", "ToyNegation-Modified.java");
        assertEquals(expected, cu);

    }
}
