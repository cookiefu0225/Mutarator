public class ToyProgramVoidMethodCall {
    public void someVoidMethod(int i) {
        System.out.println(1);
    }

    public int foo() {
        int i = 5;
        someVoidMethod(i);
        return i;
    }
}