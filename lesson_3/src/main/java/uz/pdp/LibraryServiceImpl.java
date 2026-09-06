package uz.pdp;

import com.github.javafaker.Faker;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class LibraryServiceImpl implements LibraryService {
    private List<MyBook> books;
    private List<User> users;
    private static String booksFile = "books.txt";

    public LibraryServiceImpl(List<User> users) {
        this.users = users;
        this.books = new ArrayList<>();
        loadBooksFromFile();
    }

    private void loadBooksFromFile() {
        File file = new File(booksFile);
        if (!file.exists() || file.length() == 0) {
            initDefaultBooks();
            return;
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split(";", -1);
                if (parts.length >= 4) {
                    Long id = Long.parseLong(parts[0].trim());
                    String title = parts[1].trim();
                    String author = parts[2].trim();
                    Integer year = Integer.parseInt(parts[3].trim());
                    User borrowedUser = null;
                    if (parts.length >= 5 && !parts[4].trim().isEmpty()) {
                        try {
                            Long userId = Long.parseLong(parts[4].trim());
                            borrowedUser = findUserById(userId);
                        } catch (NumberFormatException ignored) {
                        }
                    }
                    MyBook book = MyBook.builder()
                            .id(id)
                            .title(title)
                            .author(author)
                            .year(year)
                            .borrowedBy(borrowedUser)
                            .build();
                    books.add(book);
                }
            }
        } catch (IOException e) {
            System.err.println("Fayldan o'qishda xatolik: " + e.getMessage());
        }

        if (books.isEmpty()) {
            initDefaultBooks();
        }
    }

    private void initDefaultBooks() {
        generateBooksWithFaker(20);
    }

    @Override
    public void generateBooksWithFaker(int count) {
        books.clear();
        Faker faker = new Faker();
        for (int i = 1; i <= count; i++) {
            com.github.javafaker.Book fakeBook = faker.book();
            int year = faker.number().numberBetween(1950, 2024);
            MyBook myBook = MyBook.builder()
                    .id((long) i)
                    .title(fakeBook.title())
                    .author(fakeBook.author())
                    .year(year)
                    .borrowedBy(null)
                    .build();
            books.add(myBook);
        }
        saveBooksToFile();
    }

    private synchronized void saveBooksToFile() {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(booksFile), StandardCharsets.UTF_8))) {
            for (MyBook book : books) {
                writer.write(book.toFileLine());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Faylga saqlashda xatolik: " + e.getMessage());
        }
    }

    private User findUserById(Long userId) {
        if (userId == null || users == null) return null;
        for (User user : users) {
            if (userId.equals(user.getId())) {
                return user;
            }
        }
        return null;
    }

    @Override
    public List<MyBook> getAvailableBooks() {
        List<MyBook> available = new ArrayList<>();
        for (MyBook book : books) {
            if (book.getBorrowedBy() == null) {
                available.add(book);
            }
        }
        return available;
    }

    @Override
    public void borrowBook(Long bookId, User currentUser) {
        if (bookId == null) {
            throw new IllegalArgumentException("Kitob ID si kiritilmadi!");
        }
        if (currentUser == null) {
            throw new IllegalArgumentException("Foydalanuvchi tizimga kirmagan!");
        }

        MyBook targetBook = getBookById(bookId);
        if (targetBook == null) {
            throw new IllegalArgumentException("ID: " + bookId + " bo'lgan kitob topilmadi!");
        }

        if (targetBook.getBorrowedBy() != null) {
            throw new IllegalStateException("Kechirasiz, ushbu kitob allaqachon band qilingan!");
        }

        targetBook.setBorrowedBy(currentUser);
        saveBooksToFile();
    }

    @Override
    public void returnBook(Long bookId) {
        returnBook(bookId, null);
    }

    @Override
    public void returnBook(Long bookId, User currentUser) {
        if (bookId == null) {
            throw new IllegalArgumentException("Kitob ID si kiritilmadi!");
        }

        MyBook targetBook = getBookById(bookId);
        if (targetBook == null) {
            throw new IllegalArgumentException("ID: " + bookId + " bo'lgan kitob topilmadi!");
        }

        if (targetBook.getBorrowedBy() == null) {
            throw new IllegalStateException("Ushbu kitob allaqachon kutubxonada mavjud (ijaraga olinmagan)!");
        }

        if (currentUser != null && targetBook.getBorrowedBy().getId() != null) {
            if (!targetBook.getBorrowedBy().getId().equals(currentUser.getId())) {
                throw new IllegalStateException("Siz bu kitobni olmagansiz! Faqat o'zingiz olgan kitoblarni qaytara olasiz.");
            }
        }

        targetBook.setBorrowedBy(null);
        saveBooksToFile();
    }

    @Override
    public List<MyBook> getBooksByUser(User currentUser) {
        List<MyBook> userBooks = new ArrayList<>();
        if (currentUser == null || currentUser.getId() == null) {
            return userBooks;
        }

        for (MyBook book : books) {
            if (book.getBorrowedBy() != null && currentUser.getId().equals(book.getBorrowedBy().getId())) {
                userBooks.add(book);
            }
        }
        return userBooks;
    }

    @Override
    public List<MyBook> getAllBooks() {
        return books;
    }

    @Override
    public MyBook getBookById(Long bookId) {
        if (bookId == null) return null;
        for (MyBook book : books) {
            if (bookId.equals(book.getId())) {
                return book;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        LibraryServiceImpl service = new LibraryServiceImpl(new ArrayList<>());
        System.out.println("Kitoblar soni: " + service.getAllBooks().size());
        for (MyBook book : service.getAllBooks()) {
            System.out.println(book);
        }
    }
}
