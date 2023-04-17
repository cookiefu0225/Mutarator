package edu.illinois.mutarator.testrunner;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * currently broken
 */
public class TestRunner {
    int passed = 0;
    String root = null;
    List<Class<?>> testPrograms = new LinkedList<>();

    public TestRunner() {
        this("./target/test-classes/");
    }
    public TestRunner(String path) {
        root = path;
    }

    public void runTest(Class<?> testClass) {
        Result result = JUnitCore.runClasses(testClass);

        for (Failure f : result.getFailures()) {
            System.out.println(f.toString());
        }

        if (result.wasSuccessful()) {
            passed ++;
        }
        System.out.println(result.wasSuccessful());
    }

    /**
     * run all tests
     * @return number of passing tests
     */
    public int runAllTest() {
        int allTest = testPrograms.size();
        int res = 0;

        // run all tests
        for (Class<?> t : testPrograms) {
            runTest(t);
        }

        // report testing result
        System.out.printf("Pass: %d, Fail: %d, Passing Rate: %f%n",
                passed,
                allTest - passed,
                (float) passed/(float) allTest);

        // reset results back to initial state
        res = passed;
        passed = 0;

        return res;
    }

    public List<Class<?>> getTests() {
        try {
            Files.walkFileTree(Paths.get(root), new FileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    if (Files.isHidden(dir)) {
                        return FileVisitResult.SKIP_SUBTREE;
                    }

//                    System.out.println("Enter: " + dir);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    // find out files with <name>.java
                    String filename = file.toString();

                    if (!filename.substring(filename.length() - 6).equals(".class")) {
                        // Unrelated files
                        return FileVisitResult.CONTINUE;
                    }

                    System.out.println(filename);
                    // convert ex/example/example.class -> ex.example.example.class
                    filename = filename.substring(root.length())
                            .replaceAll("/", ".");

                    try {
                        testPrograms.add(Class.forName(filename.substring(0, filename.lastIndexOf('.'))));
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                        return FileVisitResult.CONTINUE;
                    }

                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
//                    System.out.println("Unable to visit: " + file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
//                    System.out.println("Exit: " + dir);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return testPrograms;
    }

    public void showTests() {
        for (Class<?> t : testPrograms) {
            System.out.println(t.getName());
        }
    }
}
