

import Services.ExchangeRateService;

import java.util.Scanner;

public class DisplayTable extends ConsoleInterface {

    private final ExchangeRateService service;


    public DisplayTable(ExchangeRateService service) {
        this.service = service;
    }

    @Override
    protected boolean checkCondition(Scanner scanner) {
        return true;
    }

    @Override
    protected void handleConcrete(Scanner scanner) {
        try {
            var table = service.fetchAndParseTable();
            System.out.println("\nData publikacji: " + table.getPublicationDate());
            table.getRates().forEach((code, rate) ->
                    System.out.printf("%s (%s): %.4f PLN%n", rate.getName(), code, rate.getUnitRate()));
        } catch (Exception e) {
            System.out.println("Błąd pobierania lub parsowania danych: " + e.getMessage());
        }
    }
}