package uz.pdp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;

    public User(String s, String s1, String s2, String username, String password) {

    }

    public String toFileLine() {
        return String.format("%d, %s, %s, %s, %s, %s",
                id, firstName, lastName, email, username, password);
    }

    public static User fromFileLine(String line) {
        if (line == null || line.trim().isEmpty()) {
            return null;
        }
        String[] parts = line.split(", ", -1);
        if (parts.length >= 6) {
            return User.builder()
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
