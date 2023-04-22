package edu.illinois.mutarator.returns;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.utils.CodeGenerationUtils;
import com.github.javaparser.utils.SourceRoot;
import edu.illinois.mutarator.Mutator;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ValueReturnTest {
    @Test
    public void testEmpty() {
        SourceRoot srt = new SourceRoot(
                CodeGenerationUtils.mavenModuleRoot(ValueReturnTest.class)
                        .resolve("target/test-classes"));

        CompilationUnit cu = srt.parse("sample", "ToyValueReturn.java");

        ValueReturn vr = new ValueReturn(Mutator.EMPTY);
        vr.visit(cu, null);

        CompilationUnit expected = srt.parse("sample.answer", "ValueReturnEmpty.java");
        assertEquals(expected, cu);
    }

    @Test
    public void testPrimitive() {
        SourceRoot srt = new SourceRoot(
                CodeGenerationUtils.mavenModuleRoot(ValueReturnTest.class)
                        .resolve("target/test-classes"));

        CompilationUnit cu = srt.parse("sample", "ToyValueReturn.java");

        ValueReturn vr = new ValueReturn(Mutator.PRIMITIVE);
        vr.visit(cu, null);

        CompilationUnit expected = srt.parse("sample.answer", "ValueReturnPrimitive.java");
        assertEquals(expected, cu);
    }
}
