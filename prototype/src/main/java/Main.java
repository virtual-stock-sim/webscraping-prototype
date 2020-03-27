import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        Scraper example = new Scraper();
        //Change the ticker to whatever you want
        String ticker = "GOOGL";
        System.out.println("Description: " + example.getDescription(ticker));
        System.out.println("Current Price: " + example.getCurrentPrice(ticker));


        System.out.println(example.getJson("GOOGL",5));


        //example.getHistoricalData("GOOGL","priceHistory",5);
        //List <BigDecimal> priceHistory = example.getOpeningPriceHistory("priceHistory");
        //print out values in priceHistory
        //for(BigDecimal item : priceHistory){
        //    System.out.println(item);
        //}
    }
}
