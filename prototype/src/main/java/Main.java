import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        Scraper example = new Scraper();
        //Change the ticker to whatever you want
        String ticker = "AAPL";
        System.out.println("Description: " + example.getDescription(ticker));
        System.out.println("Current Price: " + example.getCurrentPrice(ticker));
        System.out.println("Monthly opening price history for the past five years: ");
        List <BigDecimal> openingHistory = example.getFiveYearMonthlyPriceHistory(ticker);
        for(int i=0;i<openingHistory.size();i++){
            System.out.println(openingHistory.get(i));
        }
    }
}
