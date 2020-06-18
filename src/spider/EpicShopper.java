package spider;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import java.awt.*;

import static java.lang.Integer.parseInt;
//public class SteamShopper implements ShopperInterface
public class EpicShopper implements Shopper
{
    final String shop = "Steam";
    final String baseUrl = "https://store.steampowered.com/search/?category1=998&term=";
    String title;
    String textPrice;
    String imageSrc;
    String link;
    int price;
    String query;
    String appid;
    public EpicShopper(String query){
        this.query = query;
    }


    @Override
    public void run() {
        try {
            Document doc = Jsoup.connect(baseUrl + query).get();
//            Node docu = doc.selectFirst("#search_resultsRows > a:nth-child(1)");
            title = doc.selectFirst("span.title").text();
            textPrice = doc.selectFirst("div.search_price").text();
            if (textPrice.equals("")) textPrice = "No price available.";

            Element priceElement = doc.selectFirst("div.search_price_discount_combined");
            link = doc.selectFirst("#search_resultsRows > a:nth-child(1)").attr("href");
            price = parseInt(priceElement.attr("data-price-final"));
            appid = doc.selectFirst("#search_resultsRows > a:nth-child(1)").attr("data-ds-appid");
            imageSrc = "https://steamcdn-a.akamaihd.net/steam/apps/"+appid + "/header.jpg";
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
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