package Observers;

public class L3Observer implements Observer {

    private long lastPrintTime = 0;
    private final long interval = 500; // ms

    @Override
    public void update(String message) {

        System.out.println("[LOGL3] " + message);

    }


}
