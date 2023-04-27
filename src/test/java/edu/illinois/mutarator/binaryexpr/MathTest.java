package edu.illinois.mutarator.binaryexpr;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.utils.CodeGenerationUtils;
import com.github.javaparser.utils.SourceRoot;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MathTest {

    @Test
    public void testToyProgram() {
        // Identify source root
        SourceRoot srt = new SourceRoot(
                CodeGenerationUtils.mavenModuleRoot(Math.class)
                        .resolve("target/test-classes"));

        // Parse target file
        CompilationUnit cu = srt.parse("sample", "ToyProgramMath.java");

        Math math = new Math();
        math.visit(cu, null);
        int totalMutant = math.getMutantsNumber();
        math.switchToMutation();
        math.setMutantId(0);
        math.visit(cu, null);

        CompilationUnit expected = srt.parse("sample", "ToyProgramMath-Modified.java");
        assertEquals(expected, cu);
    }
}