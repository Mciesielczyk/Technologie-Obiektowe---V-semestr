package data;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class ExchangeTable {
    private final String timestamp;
    private final Map<String,ExchangeRate> rates;

    private static final ExchangeRate PLN = new ExchangeRate(1,1,"zloto", "PLN");

    public ExchangeTable(String timestamp, Map<String,ExchangeRate> rates) {
        this.timestamp = timestamp;
        this.rates = rates;
        this.rates.put("PLN",PLN);
    }

    public Map<String,ExchangeRate> getRates() {
        return Collections.unmodifiableMap(rates);
    }

    public ExchangeRate getRate(String id) {
        return rates.get(id);
    }


    public String getPublicationDate() {
        return timestamp;
    }


}
