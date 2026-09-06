package uz.pdp.task3;

public class App {
    static void main(String[] args) {

        Calculator add = (a, b) -> a + b;
        Calculator subtract = (a, b) -> a - b;
        Calculator multiply = (a, b) -> a * b;
        Calculator divide = (a, b) -> a / b;

        add.printResult(10, 5);
        subtract.printResult(10, 5);
        multiply.printResult(10, 5);
        divide.printResult(10, 5);
    }
}
