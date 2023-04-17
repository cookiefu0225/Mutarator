package edu.illinois.mutarator;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.utils.SourceRoot;
import edu.illinois.mutarator.binaryexpr.ConditionalBoundary;
import edu.illinois.mutarator.binaryexpr.NegateConditional;
import edu.illinois.mutarator.testrunner.TestRunner;
import edu.illinois.mutarator.utils.CustomerTestLoader;
import edu.illinois.mutarator.utils.FileTraverser;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.List;
import java.lang.ClassLoader;

import static edu.illinois.mutarator.utils.Executor.runProcess;

/**
 * after creating the jar, put jar into the directory of a project
 * the main program do mvn compile and compile the project
 * todo: Set up replacer to replace a java file with mutated program
 */
public class Main {
    public static void main(String[] args) {
        // based on the given args, select mutarator with variable vva

//        main program reference
//        FileTraverser ft = new FileTraverser();
//        List<CompilationUnit> codes = ft.getAllSrcCodes();
//
//        // Mutation
//        System.out.println("First");
//        NegateConditional nc = new NegateConditional();
//        for (CompilationUnit cu : codes) {
//            nc.visit(cu, null);
//        }
//
//        ft.saveChanges();
//        try {
//            runProcess("mvn test");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("================");
//        System.out.println("Mutation done");
//        System.out.println("================");
//        codes = ft.resumeFiles(codes);
//        try {
//            runProcess("mvn test");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        System.out.println(System.getProperty("java.class.path"));
//        try {
//            runProcess("ls");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        CustomerTestLoader ctl = new CustomerTestLoader("./target/test-classes/");
        TestRunner tr = new TestRunner();
        try {
            Class<?> c = ctl.findClass("./target/test-classes/edu/illinois/mutarator/CodeParserTest.class");
            tr.runTest(c);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        File inputfile = new File("./target/test-classes/edu/illinois/mutarator/CodeParserTest.class");
//        File outputfile = new File("./test.class");
//        byte[] bytes = new byte[(int) inputfile.length()];
//        try {
//            FileInputStream fis = new FileInputStream(inputfile);
//            OutputStream os = new FileOutputStream(outputfile);
//            fis.read(bytes);
//            os.write(bytes);
//            fis.close();
//            os.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        TestRunner tr = new TestRunner();
//        tr.getTests();
//        tr.showTests();

    }

}
