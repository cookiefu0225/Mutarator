package edu.illinois.mutarator.variables;

import static org.junit.Assert.assertEquals;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.utils.CodeGenerationUtils;
import com.github.javaparser.utils.SourceRoot;
import edu.illinois.mutarator.binaryexpr.ConditionalBoundaryTest;
import org.junit.Test;

public class CRMTest {

    @Test
    public void test1() {
        SourceRoot srt = new SourceRoot(
                CodeGenerationUtils.mavenModuleRoot(ConditionalBoundaryTest.class)
                        .resolve("target/test-classes"));

        CompilationUnit cu = srt.parse("sample", "ConstantVariable.java");

        ConstantReplacementMutator crm = new ConstantReplacementMutator();
        crm.debugFlag = true;
        crm.visit(cu, null);
        assertEquals(4, crm.getMutantsNumber());

        crm.switchToMutation();
        crm.setMutantId(2);
        crm.visit(cu, null);
        crm.resetCallingCount();

        CompilationUnit expected = srt.parse("sample.answer", "CRMAnswer.java");
        assertEquals(expected, cu);

    }


    @Test
    public void test2() {
        SourceRoot srt = new SourceRoot(
                CodeGenerationUtils.mavenModuleRoot(ConditionalBoundaryTest.class)
                        .resolve("target/test-classes"));

        CompilationUnit cu = srt.parse("sample", "ConstantVariable.java");

        ConstantReplacementMutator crm = new ConstantReplacementMutator();
        crm.debugFlag = true;
        crm.visit(cu, null);

        crm.switchToMutation();
        crm.setMutantId(3);
        crm.visit(cu, null);
        crm.resetCallingCount();

        CompilationUnit expected = srt.parse("sample.answer", "CRMAnswer2.java");
        assertEquals(expected, cu);

    }
}
