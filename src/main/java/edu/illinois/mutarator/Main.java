package edu.illinois.mutarator;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.utils.SourceRoot;
import edu.illinois.mutarator.binaryexpr.ConditionalBoundary;
import edu.illinois.mutarator.binaryexpr.NegateConditional;
import edu.illinois.mutarator.utils.FileTraverser;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.List;

import static edu.illinois.mutarator.utils.Executor.runProcess;

/**
 * after creating the jar, put jar into the directory of a project
 * the main program do mvn compile and compile the project
 * todo: Set up replacer to replace a java file with mutated program
 */
public class Main {
    public static void main(String[] args) {
        // based on the given args, select mutarator with variable vva

        FileTraverser ft = new FileTraverser();
        List<CompilationUnit> codes = ft.getAllSrcCodes();

        // Mutation
        System.out.println("First");
        NegateConditional nc = new NegateConditional();
        for (CompilationUnit cu : codes) {
            nc.visit(cu, null);
        }

        ft.saveChanges();
        try {
            runProcess("mvn test");
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("================");
        System.out.println("Mutation done");
        System.out.println("================");
        codes = ft.resumeFiles(codes);
        try {
            runProcess("mvn test");
        } catch (Exception e) {
            e.printStackTrace();
        }


        System.out.println("Second");
        ConditionalBoundary cb = new ConditionalBoundary();
        for (CompilationUnit cu: codes) {
            cb.visit(cu, null);
        }
        ft.saveChanges();

        try {
            runProcess("mvn test");
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("================");
        System.out.println("Mutation done");
        System.out.println("================");
        ft.resumeFiles(codes);
        try {
            runProcess("mvn test");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
