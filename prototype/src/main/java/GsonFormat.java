public class GsonFormat {

    private String date, open, high, low, close, adjclose,volume;

    public GsonFormat(String date, String open, String high, String low, String close, String adjclose, String volume ){
        this.date=date;
        this.open=open;
        this.high=high;
        this.low=low;
        this.close=close;
        this.adjclose = adjclose;
        this.volume=volume;
    }

    public String getString(){
        return "Date: " + this.date + ",\r"+
                "Open"+ this.open+",\r"+
                "High"+this.high+",\r"+
                "Low"+this.low+",\r"+
                "Close"+this.close+",\r"+
                "Adj Close:"+this.adjclose + "\r"+
                "Volume:"+this.volume;

    }
}
