package uz.pdp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student9 {
    private String name;
    private String country;
    private String group;
    private int age;
    private double score;
}

class StudentMain9{
    public static void main(String[] args) {
        List<Student9> students = List.of(
                new Student9("Ali", "UZB", "Java", 15, 87.5),
                new Student9("Vali", "UZB", "Java", 22, 91.0),
                new Student9("Hasan", "UZB", "Spring", 21, 76.5),
                new Student9("John", "US", "Java", 23, 88.0),
                new Student9("Mike", "US", "Spring", 20, 95.5),
                new Student9("Anna", "US", "Java", 21, 82.0),
                new Student9("Hans", "GER", "Java", 25, 79.5),
                new Student9("Emma", "GER", "Spring", 22, 93.0),
                new Student9("Yuki", "JAP", "Java", 19, 89.5),
                new Student9("Ken", "JAP", "Spring", 24, 72.0),
                new Student9("Sardor", "UZB", "Java", 20, 95.0),
                new Student9("Bobur", "UZB", "Spring", 19, 68.5),
                new Student9("Sardor", "UZB", "Java", 23, 84.0)
        );
        // Variant-1
        /*Map<String, Long> collect = students.stream().collect(Collectors.groupingBy(
                Student8::getCountry,
                Collectors.counting() // takrorlansa 1 qoshib ketadi
        ));*/

        // Variant-2
        Map<String, Long> collect = students.stream().collect(Collectors.toMap(
                Student9::getCountry,
                student -> 1L,
                Long::sum
        ));

        collect.forEach((country, count) -> System.out.println(country + ": " + count));
    }
}
