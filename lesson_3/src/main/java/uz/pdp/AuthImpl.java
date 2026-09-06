package uz.pdp;

import com.github.javafaker.Faker;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class AuthImpl implements AuthService {
    private List<User> users;
    private static final String userFile = "users.txt";

    public AuthImpl() {
        this.users = new ArrayList<>();
        loadUsersFromFile();
        seedFakeUsersIfNeeded();
    }

    private void loadUsersFromFile() {
        File file = new File(userFile);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println("Users.txt faylini yaratishda xatolik: " + e.getMessage());
            }
            return;
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                User user = User.fromFileLine(line);
                if (user != null) {
                    users.add(user);
                }
            }
        } catch (IOException e) {
            System.err.println("Users.txt faylidan o'qishda xatolik: " + e.getMessage());
        }
    }

    private synchronized void saveUsersToFile() {
        saveUsersToFile(users);
    }

    private synchronized void saveUsersToFile(List<User> myUsers) {
        File file = new File(userFile);

        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false)))) {
            for (User myUser : myUsers) {
                bw.write(myUser.toFileLine());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("User faylga yozilishda xatolik: " + e.getMessage());
        }
    }

    private void seedFakeUsersIfNeeded() {
        if (users.size() >= 10) {
            return;
        }

        Faker faker = new Faker();
        for (int i = 0; i < 10; i++) {
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = faker.internet().emailAddress();
            String username = faker.name().username();
            String password = faker.internet().password();

            users.add(User.builder()
                    .id((long) (i + 1))
                    .firstName(firstName)
                    .lastName(lastName)
                    .email(email)
                    .username(username)
                    .password(password)
                    .build());
        }

        saveUsersToFile();
    }

    private Long generateNextId() {
        long maxId = 0L;
        for (User user : users) {
            if (user.getId() != null && user.getId() > maxId) {
                maxId = user.getId();
            }
        }
        return maxId + 1L;
    }

    @Override
    public User register(String firstName, String lastName, String email, String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username bo'sh bo'lishi mumkin emas!");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Parol bo'sh bo'lishi mumkin emas!");
        }

        String trimmedUsername = username.trim();
        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(trimmedUsername)) {
                throw new IllegalArgumentException("Ushbu username (" + trimmedUsername + ") allaqachon ro'yxatdan o'tgan!");
            }
        }

        User newUser = User.builder()
                .id(generateNextId())
                .firstName(firstName != null ? firstName.trim() : "")
                .lastName(lastName != null ? lastName.trim() : "")
                .email(email != null ? email.trim() : "")
                .username(trimmedUsername)
                .password(password)
                .build();

        users.add(newUser);
        saveUsersToFile();
        return newUser;
    }

    @Override
    public User login(String username, String password) {
        if (username == null || password == null) {
            return null;
        }
        String trimmedUsername = username.trim();
        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(trimmedUsername)
                    && u.getPassword().equals(password)) {
                System.out.println("Username: " + u.getUsername() + ", Password: " + u.getPassword());
                return u;
            }
        }
        return null;
    }

    @Override
    public List<User> getUsers() {
        return users;
    }

    @Override
    public User getUserById(Long id) {
        if (id == null) return null;
        for (User u : users) {
            if (id.equals(u.getId())) {
                return u;
            }
        }
        return null;
    }
}
