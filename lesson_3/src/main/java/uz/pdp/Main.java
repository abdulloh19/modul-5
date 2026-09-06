package uz.pdp;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

public class Main {
    private static AuthService authService;
    private static LibraryService libraryService;
    private static User currentUser = null;
    private static AuthImpl authImpl;

    public static void main(String[] args) {
        authService = new AuthImpl();
        libraryService = new LibraryServiceImpl(authService.getUsers());
        authImpl = (AuthImpl) authService;

        while (true) {
            if (currentUser == null) {
                showGuestMenu();
            } else {
                showUserMenu();
            }
        }
    }

    private static void showGuestMenu() {
        System.out.println();
        System.out.println(" 1. Ro'yxatdan o'tish (Register)");
        System.out.println(" 2. Tizimga kirish (Login)");
        System.out.println(" 0. Chiqish");
        System.out.println("--------------------------------------------------------------------------");

        int choice = Utils.intUtil("Tanlovingizni kiriting: ");
        switch (choice) {
            case 1 -> handleRegister();
            case 2 -> handleLogin();
            case 0 -> {
                System.out.println("Dastur yakunlandi. Xayr, salomat bo'ling!");
                System.exit(0);
            }
            default -> System.out.println("Noto'g'ri tanlov! Qaytadan urinib ko'ring.");
        }
    }

    private static void handleRegister() {
        System.out.println();
        String firstName = Utils.strUtil("Ismingizni kiriting: ");
        String lastName = Utils.strUtil("Familiyangizni kiriting: ");
        String email = Utils.strUtil("Emailingizni kiriting: ");
        String username = Utils.strUtil("Username tanlang: ");
        String password = Utils.strUtil("Parol kiriting: ");

        try {
            User registeredUser = authService.register(firstName, lastName, email, username, password);
            Properties properties = new Properties();
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "465");
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.auth", "true");
            String usernameMail = "parij4320@gmail.com";
            String passwordMail = "gqbnxzlmsbwdxysj";
            Session session = Session.getDefaultInstance(properties, new Authenticator() {
                @Override
                protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                    return new javax.mail.PasswordAuthentication(usernameMail, passwordMail);
                }
            });
            Message message = new MimeMessage(session);
            message.setSubject("Ro'yxatdan o'tish muvaffaqiyatli!");
            message.setContent("<h1 style=\"background-color: black; color: white\">Siz muvaffaqiyatli ro'yxatdan o'tdingiz! ID raqamingiz: " + registeredUser.getId() + "</h1>", "text/html");
            message.setFrom(new InternetAddress(usernameMail));
            String recevier = email;
            message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress(recevier)});
            Transport.send(message);
            System.out.println("Ro'yxatdan o'tish muvaffaqiyatli! Sizga tasdiqlash xati yuborildi: " + email);
            System.out.println("Siz muvaffaqiyatli ro'yxatdan o'tdingiz! ID raqamingiz: " + registeredUser.getId());
        } catch (Exception e) {
            System.out.println("Ro'yxatdan o'tishda xatolik: " + e.getMessage());
        }
    }

    private static void handleLogin() {
        System.out.println();
        System.out.println("                Ro'yxatdan o'tgan foydalanuvchilar:");
        for (User u : authService.getUsers()) {
            System.out.println("Username: " + u.getUsername() + " | Password: " + u.getPassword());
        }
        System.out.println("--------------------------------------------------------------------------");
        String username = Utils.strUtil("Username: ");
        String password = Utils.strUtil("Parol: ");
        User user = authService.login(username, password);
        if (user != null) {
            System.out.println("        Login muvaffaqiyatli bo'ldi!");
            System.out.println("Username: " + user.getUsername());
            System.out.println("Password: " + user.getPassword());
            currentUser = user;
            System.out.println("Xush kelibsiz, " + currentUser.getFirstName() + " " + currentUser.getLastName() + "!");
        } else {
            System.out.println("Login yoki parol noto'g'ri! Qayta urinib ko'ring.");
        }
    }

    private static void showUserMenu() {
        System.out.println();
        System.out.println("==========================================================================");
        System.out.println("Foydalanuvchi paneli: " + currentUser.getFirstName() + " (" + currentUser.getUsername() + ")");
        System.out.println("==========================================================================");
        System.out.println(" 1. Bo'sh kitoblarni ko'rish");
        System.out.println(" 2. Kitob olish (Borrow)");
        System.out.println(" 3. Kitob qaytarish (Return)");
        System.out.println(" 4. Mening olgan kitoblarim");
        System.out.println(" 5. Tizimdan chiqish (Logout)");
        System.out.println("--------------------------------------------------------------------------");

        int choice = Utils.intUtil("Tanlovingizni kiriting: ");
        switch (choice) {
            case 1 -> handleViewAvailableBooks();
            case 2 -> handleBorrowBook();
            case 3 -> handleReturnBook();
            case 4 -> handleViewMyBooks();
            case 5 -> handleLogout();
            default -> System.out.println("Noto'g'ri tanlov! Qaytadan urinib ko'ring.");
        }
    }

    private static void handleViewAvailableBooks() {
        List<MyBook> availableBooks = libraryService.getAvailableBooks();
        printBooksTable(availableBooks, "Bo'sh (olish uchun mavjud) kitoblar ro'yxati");
    }

    private static void handleBorrowBook() {
        List<MyBook> availableBooks = libraryService.getAvailableBooks();
        if (availableBooks.isEmpty()) {
            System.out.println("Afsuski, hozirda barcha kitoblar band qilingan!");
            return;
        }

        printBooksTable(availableBooks, "Kitob olish uchun mavjud kitoblar");
        Long bookId = (long) Utils.intUtil("Olishni istagan kitobingiz ID sini kiriting (0 - orqaga): ");
        if (bookId == 0) {
            return;
        }

        try {
            libraryService.borrowBook(bookId, currentUser);
            MyBook borrowedBook = libraryService.getBookById(bookId);
            System.out.println("\"" + borrowedBook.getTitle() + "\" kitobi muvaffaqiyatli olindi!");
        } catch (Exception e) {
            System.out.println("Kitobni olishda xatolik: " + e.getMessage());
        }
    }

    private static void handleReturnBook() {
        List<MyBook> myBooks = libraryService.getBooksByUser(currentUser);
        if (myBooks.isEmpty()) {
            System.out.println("Sizda hozircha olingan kitoblar mavjud emas!");
            return;
        }

        printBooksTable(myBooks, "Siz olgan kitoblar (qaytarish uchun)");
        Long bookId = (long) Utils.intUtil("Qaytarmoqchi bo'lgan kitobingiz ID sini kiriting (0 - orqaga): ");
        if (bookId == 0) {
            return;
        }

        try {
            MyBook targetBook = libraryService.getBookById(bookId);
            String title = (targetBook != null) ? targetBook.getTitle() : "Kitob";
            libraryService.returnBook(bookId, currentUser);
            System.out.println("\"" + title + "\" kitobi kutubxonaga muvaffaqiyatli qaytarildi!");
        } catch (Exception e) {
            System.out.println("Kitobni qaytarishda xatolik: " + e.getMessage());
        }
    }

    private static void handleViewMyBooks() {
        List<MyBook> myBooks = libraryService.getBooksByUser(currentUser);
        printBooksTable(myBooks, "Mening olgan kitoblarim");
    }

    private static void handleLogout() {
        System.out.println(currentUser.getFirstName() + ", siz tizimdan muvaffaqiyatli chiqdingiz.");
        currentUser = null;
    }

    private static void printBooksTable(List<MyBook> books, String title) {
        System.out.println();
        System.out.println("==========================================================================");
        System.out.println(title);
        System.out.println("==========================================================================");
        if (books == null || books.isEmpty()) {
            System.out.println("   (Hozircha kitoblar mavjud emas)");
            System.out.println("--------------------------------------------------------------------------");
            return;
        }

        for (MyBook book : books) {
            String status = (book.getBorrowedBy() == null)
                    ? "Bo'sh"
                    : "Band (" + book.getBorrowedBy().getUsername() + ")";

            String displayTitle = book.getTitle();
            if (displayTitle != null) {
                if (displayTitle.length() > 28) {
                    displayTitle = displayTitle.substring(0, 25) + "...";
                }
            } else {
                displayTitle = "Noma'lum";
            }

            String displayAuthor = book.getAuthor();
            if (displayAuthor != null) {
                if (displayAuthor.length() > 20) {
                    displayAuthor = displayAuthor.substring(0, 17) + "...";
                }
            } else {
                displayAuthor = "Noma'lum";
            }

            String statusPlain = (book.getBorrowedBy() == null) ? "Bo'sh" : "Band (" + book.getBorrowedBy().getUsername() + ")";
            if (statusPlain.length() > 12) {
                statusPlain = statusPlain.substring(0, 9) + "...";
            }
            int padding = 12 - statusPlain.length();
            String paddedStatus = status + " ".repeat(Math.max(0, padding));

            System.out.printf("| %-4d | %-30s | %-22s | %-5d | " + paddedStatus + " |%n",
                    book.getId(), displayTitle, displayAuthor, book.getYear());
        }
    }
}
