package uz.pdp;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MyUsers {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;

    public MyUsers(String s, String s1, String s2, String username, String password) {

    }


    public String toFileLine() {
        return "%.2f, %s, %s, %s, %s, %s, ".formatted((float) id, firstName, lastName, email, username, password);
    }

    public static MyUsers fromFileLine(String line) {
        if (line == null || line.trim().isEmpty()) {
            return null;
        }
        String[] parts = line.split(", ", -1);
        if (parts.length >= 6) {
            return MyUsers.builder()
                    .id(Long.parseLong(parts[0].trim()))
                    .firstName(parts[1].trim())
                    .lastName(parts[2].trim())
                    .email(parts[3].trim())
                    .username(parts[4].trim())
                    .password(parts[5].trim())
                    .build();
        }
        return null;
    }
}
