package edu.illinois.mutarator.unaryexpr;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.utils.CodeGenerationUtils;
import com.github.javaparser.utils.SourceRoot;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IncrementTest {
    @Test
    public void testToyProgram() {
        // Identify source root
        SourceRoot srt = new SourceRoot(
                CodeGenerationUtils.mavenModuleRoot(Increment.class)
                        .resolve("target/test-classes"));

        // Parse target file
        CompilationUnit cu = srt.parse("sample", "ToyProgramIncrement.java");

        Increment nc = new Increment();
        nc.visit(cu, null);

        CompilationUnit expected = srt.parse("sample", "ToyProgramIncrement-Modified.java");
        assertEquals(expected, cu);
    }
}
