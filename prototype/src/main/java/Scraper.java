
import java.io.*;
import java.math.BigDecimal;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



public class Scraper {
    public Scraper(){

    }

    //will be called within the getJson method
    public String getDescription(String tickerSymbol) throws IOException, ConnectException {

            String URL = "https://finance.yahoo.com/quote/" + tickerSymbol + "/profile?p=" + tickerSymbol;
            Document doc = Jsoup.connect(URL).timeout(0).get();//timeout set to 0 indicates infinite
            Element s = doc.getElementsByClass("Mt(15px) Lh(1.6)").first();
            return s.text();


    }

    //Might not need to use this depending on what we end up doing with the API
    public BigDecimal getCurrentPrice(String tickerSymbol)throws IOException{
        String URL = "https://finance.yahoo.com/quote/"+tickerSymbol+"/profile?p="+tickerSymbol;

        Document doc = Jsoup.connect(URL).get();
        Element e = doc.getElementsByClass("Trsdu(0.3s) Fw(b) Fz(36px) Mb(-4px) D(ib)").first();
        return new BigDecimal(e.text().replace(",",""));
    }



    public JsonArray getJson(String ticker, int numYears) throws IOException {
        long unixTime = System.currentTimeMillis() / 1000L;
        //calculate seconds passed & sub from current unix time
        long oldUnixTime = unixTime - (numYears*365*24*60*60);

        String companyDescription = this.getDescription(ticker);

        String webResponse = new Scanner(new URL("https://query1.finance.yahoo.com/v7/finance/download/"+ticker+"?period1="+oldUnixTime+"&period2="+unixTime+"&interval=1mo&events=history").openStream(), "UTF-8").useDelimiter("\\A").next();

        List<String> rowsList =  new LinkedList<String>(Arrays.asList(webResponse.split("\\n")));//get rows separated by line
        rowsList.remove(0);      //get rid of header

        List<String> col = new LinkedList<String>();

        for(int i=0;i<rowsList.size();i++){
            col.addAll(Arrays.asList(rowsList.get(i).split(",")));
        }
        JsonArray ja = new JsonArray();
        JsonObject companyDesc = new JsonObject();
        companyDesc.addProperty("description",this.getDescription(ticker));
        ja.add(companyDesc);
        for(int i=0; i<col.size()-6;i+=7){
            JsonObject jo = new JsonObject();
            jo.addProperty("date",col.get(i));
            jo.addProperty("open",col.get(i+1));
            jo.addProperty("high",col.get(i+2));
            jo.addProperty("low",col.get(i+3));
            jo.addProperty("close",col.get(i+4));
            jo.addProperty("adjclose",col.get(i+5));
            jo.addProperty("volume",col.get(i+6));
            ja.add(jo);
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        return ja;
        //return gson.toJson(ja);//This is done to enable prettyPrint
    }



    public JsonArray getJsonMonthly(String ticker, int numMonths) throws IOException {
        long unixTime = System.currentTimeMillis() / 1000L;
        //calculate seconds passed & sub from current unix time
        long oldUnixTime = unixTime - (numMonths*	2629743);

        String companyDescription = this.getDescription(ticker);

        String webResponse = new Scanner(new URL("https://query1.finance.yahoo.com/v7/finance/download/"+ticker+"?period1="+oldUnixTime+"&period2="+unixTime+"&interval=1mo&events=history").openStream(), "UTF-8").useDelimiter("\\A").next();

        List<String> rowsList =  new LinkedList<String>(Arrays.asList(webResponse.split("\\n")));//get rows separated by line
        rowsList.remove(0);      //get rid of header

        List<String> col = new LinkedList<String>();

        for(int i=0;i<rowsList.size();i++){
            col.addAll(Arrays.asList(rowsList.get(i).split(",")));
        }
        JsonArray ja = new JsonArray();
        JsonObject companyDesc = new JsonObject();
        companyDesc.addProperty("description",this.getDescription(ticker));
        ja.add(companyDesc);
        for(int i=0; i<col.size()-6;i+=7){
            JsonObject jo = new JsonObject();
            jo.addProperty("date",col.get(i));
            jo.addProperty("open",col.get(i+1));
            jo.addProperty("high",col.get(i+2));
            jo.addProperty("low",col.get(i+3));
            jo.addProperty("close",col.get(i+4));
            jo.addProperty("adjclose",col.get(i+5));
            jo.addProperty("volume",col.get(i+6));
            ja.add(jo);
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        return ja;
        //return gson.toJson(ja);//This is done to enable prettyPrint
    }







}
