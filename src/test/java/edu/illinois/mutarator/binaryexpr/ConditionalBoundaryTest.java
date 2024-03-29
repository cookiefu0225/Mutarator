package edu.illinois.mutarator.binaryexpr;

import static org.junit.Assert.assertEquals;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.utils.CodeGenerationUtils;
import com.github.javaparser.utils.SourceRoot;
import org.junit.Test;

public class ConditionalBoundaryTest {
    @Test
    public void testPlayground() {
        // Identify source root
        SourceRoot srt = new SourceRoot(
                CodeGenerationUtils.mavenModuleRoot(ConditionalBoundaryTest.class)
                        .resolve("target/test-classes"));

        // Parse target file
        CompilationUnit cu = srt.parse("sample", "ToyProgramConditionalBoundary.java");

        ConditionalBoundary cdb = new ConditionalBoundary();
        cdb.visit(cu, null);    // count total mutant
        int totalMutant = cdb.getMutantsNumber();
        assertEquals(8, totalMutant);

        cdb.switchToMutation();
        // mutate n th mutant -> set mutant id = n -1
        cdb.setMutantId(2);
        cdb.visit(cu, null);

        CompilationUnit expected = srt.parse("sample", "ToyProgramConditionalBoundary-Modified.java");
        assertEquals(expected, cu);
    }

    @Test
    public void testJsoup() {
        SourceRoot srt = new SourceRoot(
                CodeGenerationUtils.mavenModuleRoot(ConditionalBoundaryTest.class)
                        .resolve("target/test-classes"));

        CompilationUnit cu = srt.parse("org.jsoup.nodes", "Node.java");
        ConditionalBoundary cdb = new ConditionalBoundary();
        cdb.visit(cu, null);
        int totalMutant = cdb.getMutantsNumber();
        assertEquals(9, totalMutant);
        cdb.switchToMutation();
        cdb.setMutantId(0);
        cdb.visit(cu, null);
        cdb.resetCallingCount();
        cdb.resetPointCount();
        assertEquals(0, cdb.getMutantsNumber());

        CompilationUnit expected = srt.parse("sample.answer", "NodeConditionalBoundary.java");
        assertEquals(expected, cu);
    }

    @Test
    public void test3() {
        SourceRoot srt = new SourceRoot(
                CodeGenerationUtils.mavenModuleRoot(ConditionalBoundaryTest.class)
                        .resolve("target/test-classes"));

        CompilationUnit cu = srt.parse("org.jsoup.nodes", "DataNode.java");

        ConditionalBoundary cdb = new ConditionalBoundary();
        cdb.visit(cu, null);
        assertEquals(0, cdb.getMutantsNumber());
    }
}
