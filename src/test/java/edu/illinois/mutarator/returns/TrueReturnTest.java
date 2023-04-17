package edu.illinois.mutarator.returns;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.utils.CodeGenerationUtils;
import com.github.javaparser.utils.SourceRoot;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TrueReturnTest {
    @Test
    public void testPlayground() {
        SourceRoot srt = new SourceRoot(
                CodeGenerationUtils.mavenModuleRoot(FalseReturnTest.class)
                        .resolve("target/test-classes"));

        CompilationUnit cu = srt.parse("sample", "ToyReturnProgram.java");

        TrueReturn fr = new TrueReturn();
        fr.visit(cu, null);

        CompilationUnit expected = srt.parse("sample", "ToyReturnTrueReturnAnswer.java");
        assertEquals(expected, cu);
    }
}
