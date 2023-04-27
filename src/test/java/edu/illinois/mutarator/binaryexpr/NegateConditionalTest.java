package edu.illinois.mutarator.binaryexpr;

import static org.junit.Assert.assertEquals;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.utils.CodeGenerationUtils;
import com.github.javaparser.utils.SourceRoot;
import org.junit.Test;

public class NegateConditionalTest {
    @Test
    public void testToyProgram() {
        // Identify source root
        SourceRoot srt = new SourceRoot(
                CodeGenerationUtils.mavenModuleRoot(NegateConditional.class)
                        .resolve("target/test-classes"));

        // Parse target file
        CompilationUnit cu = srt.parse("sample", "ToyProgramNegateConditional.java");

        NegateConditional nc = new NegateConditional();
        nc.visit(cu, null);
        int totalMutant = nc.getMutantsNumber();
        int mutateTarget = 3;
        nc.setMutantId(mutateTarget);
        nc.switchToMutation();
        nc.visit(cu, null);
        nc.resetCallingCount();

        CompilationUnit expected = srt.parse("sample", "ToyProgramNegateConditional-Modified.java");
        assertEquals(expected, cu);
    }

}
