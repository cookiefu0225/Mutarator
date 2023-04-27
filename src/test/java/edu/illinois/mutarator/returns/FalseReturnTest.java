package edu.illinois.mutarator.returns;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.utils.CodeGenerationUtils;
import com.github.javaparser.utils.SourceRoot;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FalseReturnTest {
    @Test
    public void testPlayground() {
        SourceRoot srt = new SourceRoot(
                CodeGenerationUtils.mavenModuleRoot(FalseReturnTest.class)
                        .resolve("target/test-classes"));

        CompilationUnit cu = srt.parse("sample", "ToyReturnProgram.java");

        FalseReturn fr = new FalseReturn();
        fr.visit(cu, null);
        int totalMutants = fr.getMutantsNumber();
        assertEquals(4, totalMutants);
        fr.switchToMutation();
        fr.setMutantId(3);
        fr.visit(cu, null);
        fr.resetCallingCount();

        CompilationUnit expected = srt.parse("sample", "ToyReturnFalseReturnAnswer.java");
        assertEquals(expected, cu);
    }

    @Test
    public void testJsoup() {
        SourceRoot srt = new SourceRoot(
                CodeGenerationUtils.mavenModuleRoot(FalseReturnTest.class)
                        .resolve("target/test-classes"));

        CompilationUnit cu = srt.parse("org.jsoup.nodes", "TextNode.java");
        FalseReturn fr = new FalseReturn();
        fr.visit(cu, null);
        int totalMutants = fr.getMutantsNumber();
        assertEquals(2, totalMutants);
        fr.switchToMutation();
        fr.setMutantId(0);
        fr.visit(cu, null);
        fr.resetCallingCount();

        CompilationUnit expected = srt.parse("sample.answer", "TextNodeFalseReturn.java");
        assertEquals(expected, cu);
    }
}
