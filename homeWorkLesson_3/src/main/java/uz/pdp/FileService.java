package uz.pdp;

import com.github.javafaker.Book;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileService {

    static final File usersFile = new File("users.txt");
    static final File booksFile = new File("books.txt");

    public static void writeUsersToFile(Faker faker, List<MyUsers> myUsers) {
        myUsers.clear();

        for (int i = 1; i < 5; i++) {
            Name fakeUsers = faker.name();
            MyUsers users = new MyUsers(fakeUsers.firstName(), fakeUsers.lastName(),
                    faker.internet().emailAddress(),
                    fakeUsers.username(), faker.internet().password());
            myUsers.add(users);
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(usersFile, false))) {
            for (MyUsers myUser : myUsers) {
                bw.write(myUser.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("User faylga yozilishda xatolik!");
        }
//        myUsers.forEach(System.out::println);
    }

    public static void readUsersFromFile(Faker faker, List<MyUsers> myUsers) {
        readUsersFromFile(myUsers);
    }

    public static void readUsersFromFile(List<MyUsers> myUsers) {
        myUsers.clear();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(usersFile))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String trimmed = line.trim();
                if (trimmed.isEmpty()) {
                    continue;
                }

                String[] parts = trimmed.split(",\\s*");
                List<String> values = new ArrayList<>();
                for (String part : parts) {
                    if (!part.isBlank()) {
                        values.add(part.trim());
                    }
                }

                if (values.size() >= 5) {
                    myUsers.add(new MyUsers(values.get(0), values.get(1), values.get(2), values.get(3), values.get(4)));
                }
            }
        } catch (IOException e) {
            System.out.println("User fayldan o'qishda xatolik!");
        }
    }

    public static void writeBooksToFile(Faker faker, List<MyBooks> myBooks) {
        myBooks.clear();

        for (int i = 0; i < 50; i++) {
            Book fakeBook = faker.book();
            MyBooks myBook = new MyBooks(fakeBook.title(), fakeBook.author(), fakeBook.genre(),
                    (int) (Math.random() * 1000 + Math.random() * 100 * Math.random() * 10),
                    faker.number().numberBetween(1950, 2024));
            myBooks.add(myBook);
        }

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(booksFile, false))) {
            for (MyBooks myBook : myBooks) {
                bufferedWriter.write(myBook.toString());
                bufferedWriter.newLine();
            }
//            System.out.println("50 ta kitob muvaffaqiyatli generatsiya qilindi va faylga yozildi!");
        } catch (IOException e) {
            System.out.println("Kitob faylga yozilishda xatolik...");
        }
//        myBooks.forEach(System.out::println);
    }

    public static void readBooksFromFile(Faker faker, List<MyBooks> myBooks) {
        readBooksFromFile(myBooks);
    }

    public static void readBooksFromFile(List<MyBooks> myBooks) {
        myBooks.clear();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(booksFile))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String trimmed = line.trim();
                if (trimmed.isEmpty()) {
                    continue;
                }

                String[] parts = trimmed.split(",\\s*");
                List<String> values = new ArrayList<>();
                for (String part : parts) {
                    if (!part.isBlank()) {
                        values.add(part.trim());
                    }
                }

                if (values.size() >= 5) {
                    String yearText = values.get(values.size() - 1).replaceAll("[^0-9]", "");
                    String pageText = values.get(values.size() - 2).replaceAll("[^0-9]", "");
                    String genre = values.get(values.size() - 3);
                    String author = values.get(values.size() - 4);
                    String title = String.join(", ", values.subList(0, values.size() - 4));

                    myBooks.add(new MyBooks(title, author, genre, Integer.parseInt(pageText), Integer.parseInt(yearText)));
                }
            }
        } catch (IOException e) {
            System.out.println("Kitob fayldan o'qishda xatolik!");
        }
    }
}