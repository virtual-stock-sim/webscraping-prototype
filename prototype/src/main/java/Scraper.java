
import java.io.*;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



public class Scraper {


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



    public String getJson(String ticker) throws IOException {
        long unixTime = System.currentTimeMillis() / 1000L;
        //calculate seconds passed & sub from current unix time
        long oldUnixTime = unixTime - (5*365*24*60*60);

        String companyDescription = this.getDescription(ticker);

        String webResponse = new Scanner(new URL("https://query1.finance.yahoo.com/v7/finance/download/"+ticker+"?period1="+oldUnixTime+"&period2="+unixTime+"&interval=1mo&events=history").openStream(), "UTF-8").useDelimiter("\\A").next();

        List<String> rowsList =  new LinkedList<String>(Arrays.asList(webResponse.split("\\n")));
        //gets rid of header
        rowsList.remove(0);

        List<String> col = new LinkedList<String>();

        for(int i=0;i<rowsList.size();i++){
            col.addAll(Arrays.asList(rowsList.get(i).split(",")));
        }
        List <GsonFormat> formatted = new LinkedList<GsonFormat>();

        for(int i=0; i<col.size()-6;i+=7){
            formatted.add(new GsonFormat(col.get(i),col.get(i+1),col.get(i+2),col.get(i+3)  ,col.get(i+4),col.get(i+5),col.get(i+6)));
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(formatted);


       return json;
    }







}
