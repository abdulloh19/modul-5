package uz.pdp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Student1 {
    private String name;
    private String country;
    private String group;
    private int age;
    private double score;
}

class StudentMain1 {
    static void main(String[] args) {
        List<Student1> students = List.of(
                new Student1("Ali", "UZB", "Java", 20, 87.5),
                new Student1("Ali", "UZB", "Java", 15, 87.5),
                new Student1("Vali", "UZB", "Java", 22, 91.0),
                new Student1("Hasan", "UZB", "Spring", 21, 76.5),
                new Student1("John", "US", "Java", 23, 88.0),
                new Student1("Mike", "US", "Spring", 20, 95.5),
                new Student1("Anna", "US", "Java", 21, 82.0),
                new Student1("Hans", "GER", "Java", 25, 79.5),
                new Student1("Emma", "GER", "Spring", 22, 93.0),
                new Student1("Yuki", "JAP", "Java", 19, 89.5),
                new Student1("Ken", "JAP", "Spring", 24, 72.0),
                new Student1("Sardor", "UZB", "Java", 20, 95.0),
                new Student1("Bobur", "UZB", "Spring", 19, 68.5),
                new Student1("Sardor", "UZB", "Java", 23, 84.0)
        );
       students.stream().sorted(Comparator.comparing(Student1::getAge))
               .forEach(System.out::println);
    }
}

