package sample;

public class ToyProgramNegateConditional {
    public boolean m1() {
        int a = 10;
        int b = 3;
        return a >= b;
    }

    private int m2(float c, int d) {
        if (c < d) {d++;}

        int f = c + 1;
        if (f != d) {
            f--;
        }

        if (f == c) {
            return f;
        } else {
            return d;
        }
    }

    String m3(String s1, String s2) {
        if (s1.length() <= s2.length()) {
            return s2;
        }
    }
}