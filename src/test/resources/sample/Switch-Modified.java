public class ToyProgramMath {
    void method(int a) {
        switch (a) {
            default:
                throw new IllegalArgumentException("illegal");
            case 2:
                foo2();
                break;
            case 3:
                foo3();
                break;
            case 4:
                foo4();
                break;
            case 5:
            case 6:
                break;
        }
    }

}