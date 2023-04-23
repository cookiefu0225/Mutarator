public class ToyProgramVoidMethodCall {
    public void someVoidMethod(int i) {
    }

    public int foo() {
        int i = 5;
        someVoidMethod(i);
        return i;
    }
}