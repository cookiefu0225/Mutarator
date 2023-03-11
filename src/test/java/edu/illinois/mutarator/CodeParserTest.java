package edu.illinois.mutarator;

import static org.junit.Assert.assertEquals;

import com.github.javaparser.utils.CodeGenerationUtils;
import com.github.javaparser.utils.SourceRoot;
import org.junit.Test;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;

public class CodeParserTest {
    @Test
    public void testPlayground() {
        // Toy program
        CompilationUnit cu = StaticJavaParser.parse(
                "abstract class X {int num = 1; public void m1(){System.out.println(\"hello\");} public abstract int m2(int i); public int m3(int i){return i;} static int m4(int i){return i;}}");

        CodeParser cp = new CodeParser();
        cp.visit(cu, null);

        assertEquals(4, cp.methods.size());
        assertEquals("public abstract int m2(int i);", cp.getMethod(1));
    }

    @Test
    // Use the example from MP2 to get sample codes in jsoup
    public void testSampleFile() {
        // Initialize the source root as the "target/test-classes" dir, which
        // includes the test resource information (i.e., the source code info
        // for Jsoup for this assignment) copied from src/test/resources
        // during test execution
        SourceRoot sourceRoot = new SourceRoot(
                CodeGenerationUtils.mavenModuleRoot(CodeParserTest.class)
                        .resolve("target/test-classes"));

        // Get the code representation for the specific java file using its
        // package and name info
        CompilationUnit cu = sourceRoot.parse("org.jsoup",
                "Jsoup.java");

        CodeParser cp = new CodeParser();
        cp.visit(cu, null);

        assertEquals(15, cp.methods.size());

    }
}
