public class ToyProgramMath {
    public int originalMethod() {
        int someInt = 3;
        return someOtherMethod(someInt);
    }

    private int someOtherMethod(int parameter) {
        return parameter + 1;
    }

}