import Services.ExchangeRateService;
import data.ExchangeTable;
import java.util.InputMismatchException;
import java.util.Scanner;


public class ExchangeDisplay extends ConsoleInterface {

    private final Exchange converter;
    private ExchangeTable table;
    private double amount;
    private String sourceCode;
    private String targetCode;
    private final ExchangeRateService rate;
    public ExchangeDisplay(Exchange converter, ExchangeRateService rate) {
        this.converter = converter;
        this.rate=rate;
    }
//usunac
    public void setTable(ExchangeTable table) {
        this.table = table;
    }

    @Override
    protected boolean checkCondition(Scanner scanner) {
        System.out.println("\n--- Przeliczanie Walut ---");
        try {
            System.out.print("Wprowadź kwotę do przeliczenia: ");
            if (scanner.hasNextDouble()) {
                this.amount = scanner.nextDouble();
            } else {
                throw new InputMismatchException("Wprowadzona kwota jest nieprawidłowa.");
            }
            scanner.nextLine();

            System.out.print("Wprowadź kod waluty źródłowej (np. USD, PLN): ");
            this.sourceCode = scanner.nextLine().toUpperCase().trim();

            System.out.print("Wprowadź kod waluty docelowej (np. EUR, PLN): ");
            this.targetCode = scanner.nextLine().toUpperCase().trim();

            if (sourceCode.isEmpty() || targetCode.isEmpty()) {
                throw new IllegalArgumentException("Kody walut nie mogą być puste.");
            }
            this.table=rate.fetchAndParseTable();
            if (table == null) {
                throw new IllegalStateException("Brak tabeli kursów. Nie można przeliczyć waluty.");
            }

            return true;

        } catch (InputMismatchException | IllegalArgumentException | IllegalStateException e) {
            System.out.println(" Błąd: " + e.getMessage());
            scanner.nextLine();
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void handleConcrete(Scanner scanner) {
        try {
            double result = converter.calculateExchange(amount, sourceCode, targetCode, table);


            System.out.printf("%.2f %s = %.2f %s%n",
                    amount, sourceCode, result, targetCode);

        } catch (IllegalArgumentException e) {
            System.out.println("Błąd waluty: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Błąd przeliczenia: " + e.getMessage());
        } finally {
            System.out.println("\nNaciśnij ENTER, aby wrócić do menu");
            scanner.nextLine();

            this.next = this.getNext() != null ? this.getNext() : null;
        }
    }
}
