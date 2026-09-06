package uz.pdp;

import com.github.javafaker.Faker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        List<MyBooks> myBooks = new ArrayList<>();
        List<MyUsers> myUsers = new ArrayList<>();
        Faker faker = new Faker();

        FileService.writeBooksToFile(faker, myBooks);
        FileService.writeUsersToFile(faker, myUsers);
        FileService.readUsersFromFile(myUsers);

        AuthServiceImpl authService = new AuthServiceImpl(myUsers);

        while (true) {
            System.out.println("""
                    1. Register
                    2. login
                    """);
            int chooseNumber = Util.intUtil("choose number: ");
            switch (chooseNumber) {
                case 1 -> handleRegister(authService, myUsers);
                case 2 -> handleLogin(authService, myUsers);

                default -> System.out.println("Noto'g'ri tanlov!");
            }
        }
    }

    private static void handleRegister(AuthServiceImpl authService, List<MyUsers> myUsers) throws IOException {
        String firstName = Util.strUtil("Ismingizni kiriting: ");
        String lastName = Util.strUtil("Familiyangizni kiriting: ");
        String email = Util.strUtil("Emailingizni kiriting: ");
        String username = Util.strUtil("Username tanlang: ");
        String password = Util.strUtil("Parol kiriting: ");

        MyUsers register = authService.register(firstName, lastName, email, username, password);
        myUsers.add(register);
        authService.getUsers().add(register);

        System.out.println("Ro'yxatdan o'tish muvaffaqiyatli amalga oshirildi!");
    }

    private static void handleLogin(AuthServiceImpl authService, List<MyUsers> myUsers) {
        for (MyUsers user : authService.getUsers()) {
            System.out.println("username: " + user.getUsername() + ", password: " + user.getPassword());
        }
        String username = Util.strUtil("Username kiriting: ");

        boolean usernameExists = myUsers.stream().anyMatch(user -> user.getUsername().equals(username));
        if (!usernameExists) {
            System.out.println("Xato: Username noto'g'ri!");
            return;
        }

        String password = Util.strUtil("Parol kiriting: ");
        MyUsers users = authService.login(username, password);
        if (users == null) {
            System.out.println("Xato: Password noto'g'ri yoki foydalanuvchi topilmadi!");
            return;
        }

        System.out.println("Muvaffaqiyatli login: " + users);
    }
}