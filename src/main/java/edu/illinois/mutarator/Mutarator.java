package edu.illinois.mutarator;

import edu.illinois.mutarator.example.BinarySearcherTest;
import edu.illinois.mutarator.testrunner.TestRunner;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * after creating the jar, put jar into the directory of a project
 * the main program do mvn compile and compile the project
 */
public class Mutarator {
    public static void main(String[] packagePath) {
        if (packagePath.length == 0) {
            System.out.println("Please specify the package path");
            System.exit(1);
        }

        try {
            runProcess("mvn compile");
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    private static void printLines(InputStream ins) throws Exception {
        String line = null;
        BufferedReader in = new BufferedReader(new InputStreamReader(ins));
        while ((line = in.readLine()) != null) {
            System.out.println(line);
        }
    }

    private static void runProcess(String command) throws Exception {
        Process pro = Runtime.getRuntime().exec(command);
        System.out.println("command: " + command);
        printLines(pro.getInputStream());
        pro.waitFor();
    }
}
