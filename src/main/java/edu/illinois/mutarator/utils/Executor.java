package edu.illinois.mutarator.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * todo: make TestRunner be able to fetch all test classes in a given project
 */
public class Executor {
    private static void printLines(InputStream ins) throws Exception {
        String line = null;
        BufferedReader in = new BufferedReader(new InputStreamReader(ins));
        while ((line = in.readLine()) != null) {
            System.out.println(line);
        }
    }

    public static void runProcess(String command) throws Exception {
        Process pro = Runtime.getRuntime().exec(command);
        System.out.println("command: " + command);
        // print the console output
        printLines(pro.getInputStream());
        pro.waitFor();
    }
}
