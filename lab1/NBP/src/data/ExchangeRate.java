package data;

public class ExchangeRate {

    private final double rate;
    private final double multiplayer;
    private final String name;
    private final String idString;


    public ExchangeRate(double rate, double multiplayer, String name, String idString) {
        this.rate = rate;
        this.multiplayer = multiplayer;
        this.name = name;
        this.idString = idString;
    }

    public double getRate() {
        return rate;
    }
    public double getMultiplayer() {
        return multiplayer;
    }
    public String getName() {
        return name;
    }
    public String getIdString() {
        return idString;
    }

    public double getUnitRate(){
        return rate/multiplayer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExchangeRate that = (ExchangeRate) o;
        return idString.equals(that.idString);
    }

    @Override
    public int hashCode() {
        return idString.hashCode();
    }

}
