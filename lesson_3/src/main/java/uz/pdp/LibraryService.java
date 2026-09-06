package uz.pdp;

import java.util.List;

public interface LibraryService {
    List<MyBook> getAvailableBooks();
    void borrowBook(Long bookId, User currentUser);
    void returnBook(Long bookId);
    void returnBook(Long bookId, User currentUser);
    List<MyBook> getBooksByUser(User currentUser);
    List<MyBook> getAllBooks();
    MyBook getBookById(Long bookId);
    void generateBooksWithFaker(int count);
}
