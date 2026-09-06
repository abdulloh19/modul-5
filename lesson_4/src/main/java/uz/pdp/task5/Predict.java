package uz.pdp.task5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class Predict {
    public static void main(String[] args) {
        int[] numbers = {3, 8, -5, 12, 20, 7, -14, 25, 4, 11};

        Predicate<Integer> even = n -> n % 2 == 0;
        Predicate<Integer> positive = n -> n > 0;
        Predicate<Integer> greaterThan10 = n -> n > 10;

        System.out.println("even.and(positive): " + Arrays.toString(filter(numbers, even.and(positive))));
        System.out.println("even.or(greaterThan10): " + Arrays.toString(filter(numbers, even.or(greaterThan10))));
        System.out.println("positive.and(greaterThan10): " + Arrays.toString(filter(numbers, positive.and(greaterThan10))));
    }

    static int[] filter(int[] numbers, Predicate<Integer> predicate) {
        List<Integer> result = new ArrayList<>();
        for (int number : numbers) {
            if (predicate.test(number)) {
                result.add(number);
            }
        }

        int[] filtered = new int[result.size()];
        for (int i = 0; i < result.size(); i++) {
            filtered[i] = result.get(i);
        }
        System.out.println("salom");
        return filtered;
    }
}
