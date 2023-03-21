package edu.illinois.mutarator.testrunner;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.util.LinkedList;
import java.util.List;

/**
 * todo: make TestRunner be able to fetch all test classes in a given project
 */
public class TestRunner {
    List<Class<?>> testPrograms = new LinkedList<>();
    public void runTest(Class<?> testClass) {
        Result result = JUnitCore.runClasses(testClass);

        for (Failure f : result.getFailures()) {
            System.out.println(f.toString());
        }

        System.out.println(result.wasSuccessful());
    }
}
