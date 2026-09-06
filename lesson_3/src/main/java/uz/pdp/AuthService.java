package uz.pdp;

import java.util.List;

public interface AuthService {
    User register(String firstName, String lastName, String email, String username, String password);
    User login(String username, String password);
    List<User> getUsers();
    User getUserById(Long id);
}
