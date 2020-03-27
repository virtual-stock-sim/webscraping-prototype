import java.math.BigDecimal;

// this "fake container" class will be replaced by Earl's DataCache class.
// For testing deserializing JSON made in the scraper only
public class FakeContainer {
    private String ticker, description, date;
    private BigDecimal open, high, low, close, adjclose;
    private int volume;

    public FakeContainer(String ticker, String description, String date, BigDecimal open, BigDecimal high, BigDecimal low, BigDecimal close, BigDecimal adjclose, int volume){
        this.ticker=ticker;
        this.description=description;
        this.date=date;
        this.open=open;
        this.high=high;
        this.low=low;
        this.close=close;
        this.adjclose = adjclose;
        this.volume=volume;
    }
}
