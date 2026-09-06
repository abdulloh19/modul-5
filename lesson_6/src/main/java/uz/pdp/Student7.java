package uz.pdp;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Student7 {
    private String name;
    private String country;
    private String group;
    private int age;
    private double score;
}

class StudentMain7{
    public static void main(String[] args) {
        List<Student3> students = List.of(
                new Student3("Ali", "UZB", "Java", 15, 87.5),
                new Student3("Vali", "UZB", "Java", 22, 91.0),
                new Student3("Hasan", "UZB", "Spring", 21, 76.5),
                new Student3("John", "US", "Java", 23, 88.0),
                new Student3("Mike", "US", "Spring", 20, 95.5),
                new Student3("Anna", "US", "Java", 21, 82.0),
                new Student3("Hans", "GER", "Java", 25, 79.5),
                new Student3("Emma", "GER", "Spring", 22, 93.0),
                new Student3("Yuki", "JAP", "Java", 19, 89.5),
                new Student3("Ken", "JAP", "Spring", 24, 72.0),
                new Student3("Sardor", "UZB", "Java", 20, 95.0),
                new Student3("Bobur", "UZB", "Spring", 19, 68.5),
                new Student3("Sardor", "UZB", "Java", 23, 84.0)
        );
        Map<String, String> collect = students.stream().map(Student3::getName)
                .map(String::toUpperCase)
                .collect(Collectors.toMap(name -> name,
                        name -> name,
                        (existing, replacement) -> existing,
                        TreeMap::new
                        ));
        collect.forEach((k, v) -> System.out.println(k + " => " + v));
    }
}
