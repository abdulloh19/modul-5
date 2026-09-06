package uz.pdp;

import java.io.IOException;
import java.util.List;

public interface AuthService {
    MyUsers login(String username, String password);
    MyUsers register(String firstName, String lastName, String email, String username, String password) throws IOException;
    List<MyUsers> getUsers();
}
