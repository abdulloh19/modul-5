package uz.pdp;

import java.util.Scanner;

public class Util {
    public static String strUtil(String input){
        Scanner scanner = new Scanner(System.in);
        System.out.print(input);
        return  scanner.nextLine();
    }
    public static int intUtil(String input){
        Scanner scanner = new Scanner(System.in);
        System.out.print(input);
        return  scanner.nextInt();
    }
}
