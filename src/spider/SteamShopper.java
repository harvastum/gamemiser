package spider;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import java.awt.*;

import static java.lang.Integer.parseInt;
//public class SteamShopper implements ShopperInterface
public class SteamShopper implements Runnable, Shopper
{
    final String baseUrl = "https://store.steampowered.com/search/?term=";
    String title;
    String textPrice;
    String imageSrc;
    String link;
    int price;
    String query;
    public SteamShopper(String query){
        this.query = query;
    }

    @Override
    public void run() {
                try {
            Document doc = Jsoup.connect(baseUrl + query).get();
//            Node docu = doc.selectFirst("#search_resultsRows > a:nth-child(1)");
            title = doc.selectFirst("span.title").text();
            textPrice = doc.selectFirst("div.search_price").text();
            Element priceElement = doc.selectFirst("div.search_price_discount_combined");
            link = doc.selectFirst("#search_resultsRows > a:nth-child(1)").attr("href");
            imageSrc = doc.selectFirst("#search_resultsRows > a:nth-child(1) > div.col.search_capsule > img").attr("src");
            price = parseInt(priceElement.attr("data-price-final"));

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

}
