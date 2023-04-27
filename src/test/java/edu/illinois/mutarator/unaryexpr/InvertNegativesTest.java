package edu.illinois.mutarator.unaryexpr;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.utils.CodeGenerationUtils;
import com.github.javaparser.utils.SourceRoot;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class InvertNegativesTest {
    @Test
    public void testToyProgram() {
        // Identify source root
        SourceRoot srt = new SourceRoot(
                CodeGenerationUtils.mavenModuleRoot(InvertNegatives.class)
                        .resolve("target/test-classes"));

        // Parse target file
        CompilationUnit cu = srt.parse("sample", "ToyProgramInvertNegatives.java");

        InvertNegatives in = new InvertNegatives();
        in.visit(cu, null);
        int totalMutant = in.getMutantsNumber();
        in.switchToMutation();
        in.setMutantId(0);
        in.visit(cu, null);

        CompilationUnit expected = srt.parse("sample", "ToyProgramInvertNegatives-Modified.java");
        assertEquals(expected, cu);
    }
}
