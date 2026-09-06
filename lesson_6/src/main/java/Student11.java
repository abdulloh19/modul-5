import java.util.List;
import java.util.stream.Collectors;

public class Student11 {
     static void main(String[] args) {
        List<String> students = List.of("Ali", "Vali", "Hasan", "John", "Mike");
        String collect = students.stream()
                .collect(Collectors
                        .joining(", ", "[ ", " ]"));
        System.out.println(collect);
    }
}
