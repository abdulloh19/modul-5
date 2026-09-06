package uz.pdp;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class AuthServiceImpl implements AuthService {
    private final List<MyUsers> users;

    public AuthServiceImpl() {
        this(new ArrayList<>());
    }

    public AuthServiceImpl(List<MyUsers> users) {
        this.users = users;
    }

    @Override
    public MyUsers login(String username, String password) {
        for (MyUsers user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    private Long generateNextId() {
        long maxId = 0L;
        for (MyUsers user : users) {
            if (user.getId() != null && user.getId() > maxId) {
                maxId = user.getId();
            }
        }
        return maxId + 1L;
    }

    @Override
    public MyUsers register( String firstName, String lastName, String email, String username, String password) throws IOException {

        File file = new File("users.txt");
        file.createNewFile();
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))){
            for (MyUsers user : users) {
                bufferedWriter.write(user.getId() + ", " + user.getFirstName() + ", " + user.getLastName() + ", " + user.getEmail() + ", " + user.getUsername() + ", " + user.getPassword());
                bufferedWriter.newLine();
            }
        }
        MyUsers newUser = MyUsers.builder()
                .id(generateNextId())
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .username(username)
                .password(password)
                .build();
        users.add(newUser);
        return newUser;
    }

    @Override
    public List<MyUsers> getUsers() {
        return users;
    }
}
