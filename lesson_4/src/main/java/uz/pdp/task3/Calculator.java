package uz.pdp.task3;

@FunctionalInterface
public interface Calculator {
    int calculate(int a, int b);

    default void printResult(int a, int b) {
        System.out.println("Result: " + calculate(a, b));
    }
}
