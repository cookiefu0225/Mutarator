package edu.illinois.mutarator.binaryexpr;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.utils.CodeGenerationUtils;
import com.github.javaparser.utils.SourceRoot;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AORTest {
    @Test
    public void test1() {
        SourceRoot srt = new SourceRoot(
                CodeGenerationUtils.mavenModuleRoot(ConditionalBoundaryTest.class)
                        .resolve("target/test-classes"));

        CompilationUnit cu = srt.parse("sample", "AOR.java");

        ArithmeticOperatorReplacement aor = new ArithmeticOperatorReplacement();
        aor.debugMode = true;
        aor.visit(cu, null);
        assertEquals(7, aor.getMutantsNumber());

        aor.switchToMutation();
        aor.setMutantId(4);
        aor.visit(cu, null);
        aor.resetCallingCount();

        CompilationUnit expected = srt.parse("sample.answer", "AORAnswer.java");
        assertEquals(expected, cu);

    }
}
