

import java.util.Scanner;


public class Exit extends ConsoleInterface {

    public Exit() {
        this.next = null;
    }

    @Override
    protected boolean checkCondition(Scanner scanner) {
        return true;
    }

    @Override
    protected void handleConcrete(Scanner scanner) {
        System.out.println("\n elo");
    }
}