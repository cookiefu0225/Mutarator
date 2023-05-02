package edu.illinois.mutarator.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Executor {

    /**
     *
     * @param ins maven test logs
     * @return true if there's error in test, means the mutant is killed
     * @throws Exception
     */
    private static boolean printLines(InputStream ins) throws Exception {
        // match [ERROR], every error means the test is not passed
        Pattern pattern = Pattern.compile("ERROR");
        String line = null;
        BufferedReader in = new BufferedReader(new InputStreamReader(ins));
        while ((line = in.readLine()) != null) {

            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                // means there's error
                return true;
            }
        }

        return false;
    }

    /**
     *
     * @param command Currently is always mvn test
     * @return Return true if the mutants is killed
     * @throws Exception
     */
    public static boolean runProcess(String command) throws Exception {
        Process pro = Runtime.getRuntime().exec(command);
        boolean killed = false;

        // timeout should be as small as possible, it will reduce the total runtime
        if(!pro.waitFor(5, TimeUnit.SECONDS)) {
            // means the process runs too long, the mutant is killed
            // a successful run should end in 5 seconds
            pro.destroyForcibly();
            killed = true;
        }
        killed = killed || printLines(pro.getInputStream());

        return killed;
    }

    public static void cleanup(String command) throws Exception {
        Process pro = Runtime.getRuntime().exec(command);
        pro.waitFor();
    }
}
