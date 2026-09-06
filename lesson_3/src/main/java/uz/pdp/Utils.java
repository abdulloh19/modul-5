package uz.pdp;

import java.util.Scanner;

public class Utils {
    private static final Scanner scanner = new Scanner(System.in);

    public static String strUtil(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public static int intUtil(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Iltimos, butun son kiriting!");
            }
        }
    }
}
