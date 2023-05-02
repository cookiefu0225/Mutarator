public class ToyProgramMath {
    void method(int a) {
        switch (a) {
            case 1:
                foo1();
                break;
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
            default:
                throw new IllegalArgumentException("illegal");
            case 6:
                break;

        }
    }

}