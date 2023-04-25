package edu.illinois.mutarator.returns;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.utils.CodeGenerationUtils;
import com.github.javaparser.utils.SourceRoot;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NullReturnTest {
    @Test
    public void test1() {
        SourceRoot srt = new SourceRoot(
                CodeGenerationUtils.mavenModuleRoot(FalseReturnTest.class)
                        .resolve("target/test-classes"));

        CompilationUnit cu = srt.parse("org.jsoup.select", "Collector.java");

        NullReturn nr = new NullReturn();
        nr.visit(cu, null);
        int totalMutants = nr.getMutantsNumber();
        assertEquals(5, totalMutants);

        nr.switchToMutation();
        nr.setMutantId(0);
        nr.visit(cu, null);
        nr.resetCallingCount();

        CompilationUnit expected = srt.parse("sample.answer", "NullReturnJsoup.java");
        assertEquals(expected, cu);
    }
}
