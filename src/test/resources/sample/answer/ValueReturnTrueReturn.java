import java.util.HashSet;
import java.util.Set;

public class ToyValueReturn {
    public int f1 = 0;
    String f2 = "";
    private Set<Integer> f3 = new HashSet<>();

    public ToyValueReturn() {
        f1 = 19;
        f2 = "foo";
        f3.add(18);
    }

    public int m1(String p1, int p2) {
        return p2;
    }

    short m2() {
        short a = 98;
        return a + 19;
    }

    private void m3() {f1 ++;}

    public String m4() {
        return f2;
    }

    Set<Integer> m5() {
        return f3;
    }

    boolean m6() {
        return true;
    }

    Boolean m7() {
        return true;
    }

}