package edu.illinois.mutarator.returns;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.utils.CodeGenerationUtils;
import com.github.javaparser.utils.SourceRoot;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConstructorCallTest {
    @Test
    public void test1() {
        SourceRoot srt = new SourceRoot(
                CodeGenerationUtils.mavenModuleRoot(ConstructorCall.class)
                        .resolve("target/test-classes"));

        CompilationUnit cu = srt.parse("sample", "ToyConstructorCall.java");

        ConstructorCall cc = new ConstructorCall();
        cc.visit(cu, null);
        int totalMutants = cc.getMutantsNumber();
        assertEquals(2, totalMutants);

        cc.switchToMutation();
        cc.setMutantId(0);
        cc.visit(cu, null);
        cc.resetCallingCount();

        CompilationUnit expected = srt.parse("sample", "ToyConstructorCall-Modified.java");
        assertEquals(expected, cu);
    }
}
