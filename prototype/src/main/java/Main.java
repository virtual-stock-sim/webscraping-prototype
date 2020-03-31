

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        Scraper example = new Scraper();
        //Change the ticker to whatever you want
        String ticker = "GOOGL";
        //System.out.println("Description: " + example.getDescription(ticker));
        //System.out.println("Current Price: " + example.getCurrentPrice(ticker));


        System.out.println(example.getDescriptionAndHistory("Googl",TimeInterval.ONEMONTH));

       // System.out.println(example.checkStockExists("brett"));



    }
}
