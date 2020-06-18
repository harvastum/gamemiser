package spider;

import GUI.window1;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import java.awt.*;
import java.util.Objects;

import static java.lang.Integer.parseInt;
//public class SteamShopper implements ShopperInterface
public class UbiShopper implements Shopper
{
    private static final Logger log = Logger.getLogger(UbiShopper.class);
    final String shop = "Ubisoft store";
    final String baseUrl = "https://store.ubi.com/eu/search?prefn1=productTypeRefinementString&prefv1=games&q=";
    String title;
    String textPrice;
    String imageSrc;
    String link;
    int price;
    String query;
    public UbiShopper(String query){
        this.query = query;
    }

    @Override
    public void run() {
        try {
            Document doc = Jsoup.connect(baseUrl + query).get();

            String status = doc.select("#primary > section > div.grid-x > div.small-22.small-offset-1.medium-20.medium-offset-1.large-9.cell > div > div.no-hits-results > p.no-hits-search-term > span:nth-child(1)").text();
            if (Objects.equals(status, "0 Results")){
                title = "No results found!";
                return;
            }
//        Element block = doc.selectFirst("#search-result-items > li:nth-child(4) > div");


//            Node docu = doc.selectFirst("#search_resultsRows > a:nth-child(1)");
        title = doc.select("#search-result-items > li:nth-child(4) > div > div.card-details-wrapper >div > div.card-title > h2").text();
        textPrice = doc.selectFirst("#search-result-items > li:nth-child(4) > div > div.card-details-wrapper > div > div.card-info > div.card-price > div > span").text();
        String[] parts = textPrice.split(" ");
        textPrice = parts[1] + parts[0];
        imageSrc = doc.selectFirst("#search-result-items > li:nth-child(4) > div > div > img").attr("data-mobile-src");


        } catch (java.io.IOException e) {
            log.error("IOException occurred during UbiShopper run. Message:"+e.getMessage());
            e.printStackTrace();
        }
        log.trace("Success");
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getImgSrc() {
        return imageSrc;
    }

    @Override
    public String getPrice() {
        return textPrice;
    }

    @Override
    public String getLink() {
        return link;
    }

    @Override
    public String getShop() {
        return shop;
    }

}