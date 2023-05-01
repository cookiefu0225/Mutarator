package edu.illinois.mutarator.experimental;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.utils.CodeGenerationUtils;
import com.github.javaparser.utils.SourceRoot;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MemberVariableTest {
    @Test
    public void testPlayground() {
        SourceRoot srt = new SourceRoot(
                CodeGenerationUtils.mavenModuleRoot(MemberVariable.class)
                        .resolve("target/test-classes"));

        CompilationUnit cu = srt.parse("sample", "MemberVariable.java");

        MemberVariable mv = new MemberVariable();
        mv.visit(cu, null);
        int totalMutants = mv.getMutantsNumber();
        assertEquals(3, totalMutants);

        mv.switchToMutation();
        mv.setMutantId(2);
        mv.visit(cu, null);
        mv.resetCallingCount();

        CompilationUnit expected = srt.parse("sample", "MemberVariable-Modified.java");
        assertEquals(expected, cu);
    }
}
