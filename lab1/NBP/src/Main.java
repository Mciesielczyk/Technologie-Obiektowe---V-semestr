
import InterfacesServices.IDocumentParser;
import InterfacesServices.IEncoder;
import InterfacesServices.IRemoteRepository;
import Services.DocumentParser;
import Services.Encoder;
import Services.ExchangeRateService;
import Services.RemoteUrlRepository;

import java.util.Scanner;

/**
 * Główna klasa aplikacji. Odpowiada za konfigurację i uruchomienie pętli interfejsu.
 */
public class Main {


    public static void main(String[] args) {


        try {
            SSL.disableCertificateValidation();
        } catch (Exception e) {
            e.printStackTrace();
        }


        Scanner scanner = new Scanner(System.in);

        IRemoteRepository repo = new RemoteUrlRepository();
        IEncoder encoder = new Encoder();
        IDocumentParser parser = new DocumentParser();
        ExchangeRateService exchangeRateService = null;

        try {
            exchangeRateService = new ExchangeRateService(repo, encoder, parser);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Exchange exchange = new Exchange();

        ConsoleInterface exit = new Exit();
        ConsoleInterface displayTable = new DisplayTable(exchangeRateService);
        ConsoleInterface exchangeDisplay = new ExchangeDisplay(exchange,exchangeRateService);


        ConsoleInterface menu = new Menu(displayTable, exchangeDisplay, exit);
        menu.setNext(menu);
        displayTable.setNext(menu);
        exchangeDisplay.setNext(menu);


        ConsoleInterface currentInterface = menu;

        while (currentInterface != null) {
            currentInterface = currentInterface.handle(scanner);
        }

        scanner.close();
    }
}