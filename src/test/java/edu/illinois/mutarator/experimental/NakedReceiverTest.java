package edu.illinois.mutarator.experimental;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.utils.CodeGenerationUtils;
import com.github.javaparser.utils.SourceRoot;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NakedReceiverTest {
    @Test
    public void testPlayground() {
        SourceRoot srt = new SourceRoot(
                CodeGenerationUtils.mavenModuleRoot(ArgumentPropagation.class)
                        .resolve("target/test-classes"));

        CompilationUnit cu = srt.parse("sample", "ToyNakedReceiver.java");

        NakedReceiver ne = new NakedReceiver();
        ne.visit(cu, null);
        int totalMutants = ne.getMutantsNumber();
        assertEquals(1, totalMutants);

        ne.switchToMutation();
        ne.setMutantId(0);
        ne.visit(cu, null);
        ne.resetCallingCount();

        CompilationUnit expected = srt.parse("sample", "ToyNakedReceiver-Modified.java");
        assertEquals(expected, cu);
    }
}
