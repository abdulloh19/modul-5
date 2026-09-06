package uz.pdp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
class Student3 {
    private String name;
    private String country;
    private String group;
    private int age;
    private double score;
}

class StudentMain {
    static void main(String[] args) {
        List<Student3> students = List.of(
                new Student3("Ali", "UZB", "Java", 20, 87.5),
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
       students.stream().sorted(Comparator.comparingInt(Student3::getAge))
               .forEach(System.out::println);
    }
}

