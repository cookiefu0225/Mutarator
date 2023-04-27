public class ToyReturnProgram {
    public int a = 0;
    public int b = 0;
    float c = 1.0;

    public boolean m1() {
        return c == 1.0;
    }

    public void m2() {
        return;
    }

    public boolean m3(int i) {
        if (i == 4) {
            return false;
        }

        return true;
    }

    public Boolean m4() {
        return false;
    }
}