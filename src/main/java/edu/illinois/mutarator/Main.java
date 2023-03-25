package edu.illinois.mutarator;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.utils.SourceRoot;
import edu.illinois.mutarator.binaryexpr.ConditionalBoundary;
import edu.illinois.mutarator.binaryexpr.NegateConditional;
import edu.illinois.mutarator.utils.FileTraverser;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
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

        NegateConditional nc = new NegateConditional();
        for (CompilationUnit cu : codes) {
            nc.visit(cu, null);
        }
        ft.saveChanges();

        try {
            runProcess("mvn test");
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("Mutation is done, roll back!=========================");

        ft.resumeFiles(codes);
        try {
            runProcess("mvn test");
        } catch (Exception e) {
            System.out.println(e);
        }

//        File dir = new File("./src/main/resources/temp");
//        if (!dir.exists()) {
//            dir.mkdir();
//        }
//        Path sourcePath = Paths.get("./src/main/java");
//        Path backupPath = Paths.get("./src/main/resources/temp");
//
//        SourceRoot srt = new SourceRoot(sourcePath);
//        SourceRoot backupRt = new SourceRoot(backupPath);
//
//        CompilationUnit cu = srt.parse("example", "BinarySearcher.java");
//        ConditionalBoundary cb = new ConditionalBoundary();
//
//        srt.saveAll(backupPath);
//
//        cb.visit(cu, null);
//        srt.saveAll();
//        try {
//            runProcess("mvn test");
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//
//        System.out.println("Mutation is done, roll back!=========================");
//
//        backupRt.parse("example", "BinarySearcher.java");
//
//        backupRt.saveAll(sourcePath);
//
//
//
//        try {
//            runProcess("mvn test");
//        } catch (Exception e) {
//            System.out.println(e);
//        }


    }

}
