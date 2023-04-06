package edu.illinois.mutarator.returns;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.utils.CodeGenerationUtils;
import com.github.javaparser.utils.SourceRoot;
import edu.illinois.mutarator.binaryexpr.Math;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class VoidMethodCallTest {
    @Test
    public void testToyProgram() {
        // Identify source root
        SourceRoot srt = new SourceRoot(
                CodeGenerationUtils.mavenModuleRoot(VoidMethodCall.class)
                        .resolve("target/test-classes"));

        // Parse target file
        CompilationUnit cu = srt.parse("sample", "ToyProgramVoidMethodCall.java");

        VoidMethodCall nc = new VoidMethodCall();
        nc.visit(cu, null);

        CompilationUnit expected = srt.parse("sample", "ToyProgramVoidMethodCall-Modified.java");
        assertEquals(expected, cu);
    }
}
