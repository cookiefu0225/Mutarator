package sample;

import java.util.ArrayList;
import java.util.List;

public class UOI {
    List<Integer> f1 = new ArrayList<>();
    public int arg = 0;

    public void m1() {
        return f1.get(++arg);
    }

    public void m3() {
        return f1.get(0);
    }

    void m2() {
        int a = 2;
        int c = 3;
        int d = a + ++c + arg;
    }
}