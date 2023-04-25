package edu.illinois.mutarator.returns;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.utils.CodeGenerationUtils;
import com.github.javaparser.utils.SourceRoot;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PrimitiveReturnTest {

    @Test
    public void test1() {
        SourceRoot srt = new SourceRoot(
                CodeGenerationUtils.mavenModuleRoot(FalseReturnTest.class)
                        .resolve("target/test-classes"));

        CompilationUnit cu = srt.parse("sample", "ToyValueReturn.java");

        PrimitiveReturn pr = new PrimitiveReturn();
        pr.visit(cu, null);
        int totalMutants = pr.getMutantsNumber();
        assertEquals(2, totalMutants);

        pr.switchToMutation();
        pr.setMutantId(1);
        pr.visit(cu, null);
        pr.resetCallingCount();

        int calls = pr.getCallingCount();
        assertEquals(0, calls);

        CompilationUnit expected = srt.parse("sample.answer", "ValueReturnPrimitiveMutate2.java");
        assertEquals(expected, cu);
    }
}
