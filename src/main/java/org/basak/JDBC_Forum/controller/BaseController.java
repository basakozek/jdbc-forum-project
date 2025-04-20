package org.basak.JDBC_Forum.controller;
import java.util.Scanner;
import static org.basak.JDBC_Forum.constants.ConsoleColors.COLOR_RED;
import static org.basak.JDBC_Forum.constants.ConsoleColors.COLOR_RESET;

public abstract class BaseController implements AutoCloseable {
    private final Scanner scanner;

    public BaseController() {
        this.scanner = new Scanner(System.in);
    }
    protected String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
    protected int readInt(String title) {
        System.out.print(title + " ");

        while (true) {
            try {
                if (scanner.hasNextInt()) {
                    int value = scanner.nextInt();
                    scanner.nextLine();
                    return value;
                }
                printErrorMessage("Geçersiz giriş! Lütfen sadece sayı giriniz.");
                System.out.print(title + " ");
                scanner.nextLine();
            } catch (Exception e) {
                printErrorMessage("Terminalden değer alırken hata meydana geldi.");
                scanner.nextLine();
            }
        }
    }
    protected void printErrorMessage(String message) {
        System.out.println(COLOR_RED + message + COLOR_RESET);
    }

    @Override
    public void close() {
        if(scanner != null) {
            scanner.close();
        }
    }
}