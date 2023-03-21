package edu.illinois.mutarator;

import edu.illinois.mutarator.example.BinarySearcherTest;
import edu.illinois.mutarator.testrunner.TestRunner;

public class Mutarator {
    public static void main(String[] args) {
        // use TestRunner can run test classes within the package
        TestRunner tr = new TestRunner();
        tr.runTest(BinarySearcherTest.class);


    }
}
