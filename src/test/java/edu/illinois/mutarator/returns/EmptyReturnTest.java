package edu.illinois.mutarator.returns;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.utils.CodeGenerationUtils;
import com.github.javaparser.utils.SourceRoot;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EmptyReturnTest {
    @Test
    public void test1() {
        SourceRoot srt = new SourceRoot(
                CodeGenerationUtils.mavenModuleRoot(FalseReturnTest.class)
                        .resolve("target/test-classes"));

        CompilationUnit cu = srt.parse("sample", "ToyValueReturn.java");

        EmptyReturn er = new EmptyReturn();
        er.visit(cu, null);
        int totalMutants = er.getMutantsNumber();
        assertEquals(2, totalMutants);

        er.switchToMutation();
        er.setMutantId(1);
        er.visit(cu, null);
        er.resetCallingCount();
        er.resetPointCount();
        assertEquals(0, er.getMutantsNumber());

        CompilationUnit expected = srt.parse("sample.answer", "ValueReturnEmpty2.java");
        assertEquals(expected, cu);
    }
}
