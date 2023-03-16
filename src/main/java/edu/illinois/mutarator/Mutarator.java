package edu.illinois.mutarator;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.utils.CodeGenerationUtils;
import com.github.javaparser.utils.SourceRoot;
import edu.illinois.mutarator.binaryexpr.NegateConditional;
import edu.illinois.mutarator.example.BinarySearcher;

public class Mutarator {
    // Current Problem: Unable to use generated CompilationUnit to run the tests
    public static void main(String[] args) {
        SourceRoot srt = new SourceRoot(
                CodeGenerationUtils.mavenModuleRoot(BinarySearcher.class)
                        .resolve("target/classes")
        );

        CompilationUnit cu = srt.parse("example", "BinarySearcher.java");

        NegateConditional nc = new NegateConditional();
        System.out.println(cu);
        nc.visit(cu, null);
        System.out.println(cu);
    }
}
