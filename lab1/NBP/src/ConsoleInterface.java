
import java.util.Scanner;


public abstract class ConsoleInterface {

    protected String parameter;
    protected ConsoleInterface next;


    public ConsoleInterface handle(Scanner scanner) {

        if (checkCondition(scanner)) {
            handleConcrete(scanner);
        }

        return this.next;
    }


    protected abstract boolean checkCondition(Scanner scanner);


    protected abstract void handleConcrete(Scanner scanner);

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public ConsoleInterface getNext() {
        return next;
    }

    public void setNext(ConsoleInterface next) {
        this.next = next;
    }
}