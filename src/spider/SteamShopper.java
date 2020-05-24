package spider;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import java.awt.*;

import static java.lang.Integer.parseInt;

public class SteamShopper implements Shopper{
    final String baseUrl = "https://store.steampowered.com/search/?term=";
    String title;
    String textPrice;
    String imageSrc;
    int price;

    @Override
    public void run(String query) {
                try {
            Document doc = Jsoup.connect(baseUrl + query).get();
            Node docu = doc.selectFirst("#search_resultsRows > a:nth-child(1)");
            System.out.println("doc:");
            System.out.println(doc);
            title = doc.selectFirst("span.title").text();
            textPrice = doc.selectFirst("div.search_price").text();
            Element priceElement = doc.selectFirst("div.search_price_discount_combined");
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
}
