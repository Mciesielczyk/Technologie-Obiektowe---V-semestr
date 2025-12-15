package Observers;

public class ConsoleObserver implements Observer {

    private long lastPrintTime = 0;
    private final long interval = 500; // ms

    @Override
    public void update(String message) {

            System.out.println("[LOG] " + message);

    }


}
