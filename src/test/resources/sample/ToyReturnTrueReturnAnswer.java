public class ToyReturnProgram {
    public int a = 0;
    public int b = 0;
    float c = 1.0;

    public boolean m1() {
        return true;
    }

    public void m2() {
        return;
    }

    public boolean m3(int i) {
        if (i == 4) {
            return true;
        }

        return true;
    }

    public Boolean m4() {
        return true;
    }
}