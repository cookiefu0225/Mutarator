package edu.illinois.mutarator.experimental;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.utils.CodeGenerationUtils;
import com.github.javaparser.utils.SourceRoot;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SwitchTest {
    @Test
    public void testPlayground() {
        SourceRoot srt = new SourceRoot(
                CodeGenerationUtils.mavenModuleRoot(Switch.class)
                        .resolve("target/test-classes"));

        CompilationUnit cu = srt.parse("sample", "Switch.java");

        Switch sw = new Switch();
        sw.visit(cu, null);
        int totalMutants = sw.getMutantsNumber();
//        NodeList<Expression> labels = sw.getLabels();
//        assertEquals("default", labels);
        assertEquals(1, totalMutants);
        sw.switchToMutation();
        sw.setMutantId(0);
        sw.visit(cu, null);
        sw.resetCallingCount();

        CompilationUnit expected = srt.parse("sample", "Switch-Modified.java");
        assertEquals(expected, cu);
    }
}
