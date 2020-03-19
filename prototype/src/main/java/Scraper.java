import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.*;

//this class is in charge of getting data

public class Scraper {
    public Scraper(){

    }

    public String getDescription(String tickerSymbol) throws IOException {
        String URL = "https://finance.yahoo.com/quote/"+tickerSymbol+"/profile?p="+tickerSymbol;
        Document doc = Jsoup.connect(URL).get();
        Element s = doc.getElementsByClass("Mt(15px) Lh(1.6)").first();
        return s.text();
    }

    public BigDecimal getCurrentPrice(String tickerSymbol)throws IOException{
        String URL = "https://finance.yahoo.com/quote/"+tickerSymbol+"/profile?p="+tickerSymbol;
        Document doc = Jsoup.connect(URL).get();
        Element e = doc.getElementsByClass("Trsdu(0.3s) Fw(b) Fz(36px) Mb(-4px) D(ib)").first();
        return new BigDecimal(e.text().replace(",",""));
    }

    public List <BigDecimal> getFiveYearMonthlyPriceHistory(String tickerSymbol) throws IOException{
        long unixTime = System.currentTimeMillis() / 1000L;
        long oldUnixTime = unixTime - (5*365*24*60*60);
        String URL = "https://finance.yahoo.com/quote/"+tickerSymbol+"/history?period1="+oldUnixTime+"&period2="+unixTime+"&interval=1mo&filter=history&frequency=1mo";
        Document doc = Jsoup.connect(URL).get();
        Elements x = doc.getElementsByClass("Py(10px) Pstart(10px)");
        String s = x.text().replace(",","");
        List<String> rawData = new LinkedList<String>(Arrays.asList(s.split(" ")));
        List<BigDecimal> openingPrices = new LinkedList<>();
        for(int i=0;i<rawData.size();i++) {
            if(i%6==0){
                openingPrices.add(new BigDecimal(rawData.get(i)));
            }
        }
        return openingPrices;
    }




}
