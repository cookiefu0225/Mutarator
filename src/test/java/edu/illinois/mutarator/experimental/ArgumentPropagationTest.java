package edu.illinois.mutarator.experimental;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.utils.CodeGenerationUtils;
import com.github.javaparser.utils.SourceRoot;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ArgumentPropagationTest {
    @Test
    public void testPlayground() {
        SourceRoot srt = new SourceRoot(
                CodeGenerationUtils.mavenModuleRoot(ArgumentPropagation.class)
                        .resolve("target/test-classes"));

        CompilationUnit cu = srt.parse("sample", "ToyArgumentPropagation.java");

        ArgumentPropagation ap = new ArgumentPropagation();
        ap.visit(cu, null);
        int totalMutants = ap.getMutantsNumber();
        assertEquals(1, totalMutants);
        ap.switchToMutation();
        ap.setMutantId(0);
        ap.visit(cu, null);
        ap.resetCallingCount();

        CompilationUnit expected = srt.parse("sample", "ToyArgumentPropagation-Modified.java");
        assertEquals(expected, cu);
    }
}
