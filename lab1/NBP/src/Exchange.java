import data.ExchangeRate;
import data.ExchangeTable;

public class Exchange {
    public Exchange(){};
    public double calculateExchange(double amount, String sourceCode, String targetCode, ExchangeTable table) throws IllegalArgumentException {


        ExchangeRate sourceRate = table.getRate(sourceCode);
        ExchangeRate targetRate = table.getRate(targetCode);

        if (sourceRate == null) {
            throw new IllegalArgumentException("Waluta źródłowa (" + sourceCode + ") nie jest dostępna.");
        }
        if (targetRate == null) {
            throw new IllegalArgumentException("Waluta docelowa (" + targetCode + ") nie jest dostępna.");
        }


        double amountInPln = amount * sourceRate.getUnitRate();


        double finalAmount = amountInPln / targetRate.getUnitRate();

        return finalAmount;
    }
}
