package edu.illinois.mutarator;

import org.junit.Test;
import static  org.junit.Assert.assertEquals;

public class SampleTest {

    @Test
    public void test01() {
        int a = 10, b = 20;
        assertEquals(30, Sample.testSum(a, b));
    }
}
