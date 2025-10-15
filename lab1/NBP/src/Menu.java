// File: pl/nbp/kursy/console/Menu.java

import java.util.Scanner;


public class Menu extends ConsoleInterface {

    private final int OPTION_DISPLAY_TABLE = 1;
    private final int OPTION_EXCHANGE = 2;
    private final int OPTION_EXIT = 3;


    private final ConsoleInterface displayTable;
    private final ConsoleInterface exchangeDisplay;
    private final ConsoleInterface exit;


    public Menu(ConsoleInterface displayTable, ConsoleInterface exchangeDisplay, ConsoleInterface exit) {

        this.displayTable = displayTable;
        this.exchangeDisplay = exchangeDisplay;
        this.exit = exit;

        this.next = this;
        this.parameter = "";
    }

    @Override
    public boolean checkCondition(Scanner scanner) {
        System.out.println("\n--- Narodowy Bank Polski - Kalkulator Walut ---");
        System.out.println(OPTION_DISPLAY_TABLE + ". Wyświetl tabelę kursów");
        System.out.println(OPTION_EXCHANGE + ". Przelicz walutę");
        System.out.println(OPTION_EXIT + ". Wyjdź");
        System.out.print("Wybór: ");

        if (scanner.hasNextInt()) {
            int choice = scanner.nextInt();
            scanner.nextLine();
            this.parameter = String.valueOf(choice);
            return true;
        } else {
            System.out.println("Nieprawidłowy format. Wprowadź liczbę.");
            scanner.nextLine();
            this.next = this;
            return false;
        }
    }

    @Override
    public void handleConcrete(Scanner scanner) {
        int choice = Integer.parseInt(this.parameter);


        switch (choice) {
            case OPTION_DISPLAY_TABLE:
                this.next = displayTable;
                break;
            case OPTION_EXCHANGE:
                this.next = exchangeDisplay;
                break;
            case OPTION_EXIT:
                this.next = exit;
                break;
            default:
                System.out.println(" Nieznana opcja. Spróbuj ponownie.");
                this.next = this;
                break;
        }
    }


    @Override
    public ConsoleInterface getNext() { return next; }

    @Override
    public void setNext(ConsoleInterface next) { this.next = next; }
}