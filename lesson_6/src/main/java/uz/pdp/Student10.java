package uz.pdp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student10 {
    private String name;
    private String country;
    private String group;
    private int age;
    private double score;
}

class StudentMain10{
    public static void main(String[] args) {
        List<String> students = List.of("Ali, Vali, Hasan, John, Mike");
        String collect = String.join(", ", students);
        System.out.println(collect);
    }
}
