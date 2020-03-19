
import java.io.*;
import java.math.BigDecimal;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.net.URL;
import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



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

    //gets a CSV file of the ticker on a MONTHLY basis
    //accepts the ticker symbol, an output filename, and the number of years to find
    public  void getHistoricalData(String ticker, String outputFileName, int numYears) throws IOException {
        long unixTime = System.currentTimeMillis() / 1000L;
        //calculate seconds passed & sub from current unix time
        long oldUnixTime = unixTime - (numYears*365*24*60*60);
       URL url = new URL ("https://query1.finance.yahoo.com/v7/finance/download/"+ticker+"?period1="+oldUnixTime+"&period2="+unixTime+"&interval=1mo&events=history");
       InputStream stream = url.openStream();
       try {
           FileOutputStream fos = new FileOutputStream(new File(outputFileName));
           int length = -1;
           byte[] buffer = new byte[1024];// buffer for portion of data from connection
           while ((length = stream.read(buffer)) > -1) {
               fos.write(buffer, 0, length);
           }
           fos.close();
           stream.close();
           System.out.println("done getting file");
       }catch (IOException e){
           System.out.println("Program Error: ");
            System.out.println(e.toString());
       }

    }

    public List getOpeningPriceHistory(String fileName) throws IOException {

        File file = new File(fileName);
        List <BigDecimal> priceData = new LinkedList<BigDecimal>();
        List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
        lines.remove(0);

        for (String line : lines) {
            String[] array = line.split(",", -1);
            priceData.add(new BigDecimal(array[1]));
        }

        return priceData;
    }


    //Deprecated method that scrapes HTML instead of getting CSV file
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
            if (i % 6 == 0) {
                openingPrices.add(new BigDecimal(rawData.get(i)));
            }
        }
        return openingPrices;
    }




}
