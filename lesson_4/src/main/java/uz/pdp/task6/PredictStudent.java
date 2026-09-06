package uz.pdp.task6;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;
import java.util.function.Predicate;

public class PredictStudent {

    @Data
    @AllArgsConstructor
    static class Student {
        private String name;
        private int age;
        private double grade;
    }

    public static void main(String[] args) {
        List<Student> students = Arrays.asList(
                new Student("Alice", 20, 4.7),
                new Student("Alex", 17, 4.8),
                new Student("Bob", 19, 3.9),
                new Student("Anna", 22, 4.4),
                new Student("Charlie", 18, 4.6),
                new Student("Diana", 16, 4.2),
                new Student("Aaron", 21, 4.9)
        );

        Predicate<Student> adult = s -> s.getAge() >= 18;
        Predicate<Student> excellent = s -> s.getGrade() >= 4.5;
        Predicate<Student> nameStartsWithA = s -> s.getName().startsWith("A");

        print("Adults (age >= 18):", filter(students, adult));
        print("Excellent (grade >= 4.5):", filter(students, excellent));
        print("Name starts with A:", filter(students, nameStartsWithA));

        print("Adult AND Excellent:", filter(students, adult.and(excellent)));
        print("Adult OR Excellent:", filter(students, adult.or(excellent)));
        print("Adult AND Name starts with A:", filter(students, adult.and(nameStartsWithA)));
        print("Excellent AND Name starts with A:", filter(students, excellent.and(nameStartsWithA)));
        print("Not Adult (under 18):", filter(students, adult.negate()));
    }

    private static List<Student> filter(List<Student> list, Predicate<Student> predicate) {
        List<Student> result = new ArrayList<>();
        for (Student s : list) if (predicate.test(s)) result.add(s);
        return result;
    }

    private static void print(String title, List<Student> list) {
        System.out.println("\n" + title);
        list.forEach(s -> System.out.println(" - " + s));
    }
}
