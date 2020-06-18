package spider;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import java.awt.*;

import static java.lang.Integer.parseInt;
//public class SteamShopper implements ShopperInterface
public class AllegroShopper implements Shopper
{
    private static final Logger log = Logger.getLogger(AllegroShopper.class);
    final String shop = "Allegro";
    final String baseUrl = "https://allegro.pl/kategoria/komputerowe-pc-45713?bmatch=baseline-product-eyesa2-engag-dict45-cul-1-4-0605&string=";
    String title;
    String textPrice;
    String imageSrc;
    String link;
    int price;
    String query;
    public AllegroShopper(String query){
        this.query = query;
    }


    @Override
    public void run() {
        try {
            log.info("Connecting...");
            Document doc = Jsoup.connect(baseUrl + query).get();
            Node docu = doc.selectFirst("#search_resultsRows > a:nth-child(1)");
            Element result = doc.selectFirst("article:nth-child(1) >div > div:nth-child(2) > div > h2");
            if (result == null) {
                log.info("Search results were empty.");
                return;
            }
            log.info("Results appear intact. Parsing HTML...");
            title = doc.selectFirst("article:nth-child(1) >div > div:nth-child(2) > div > h2").text();
            textPrice = doc.selectFirst("article:nth-child(1) >div > div:nth-child(2) > div:nth-child(2) > div").text();
            textPrice = textPrice.replace(" ", "");
            Element priceElement = doc.selectFirst("div.search_price_discount_combined");
            imageSrc = doc.selectFirst("article:nth-child(1) > div > div > div > a > img").attr("src");
            link = doc.selectFirst("article:nth-child(1) > div > div > div > a").attr("href");
            price = parseInt(priceElement.attr("data-price-final"));
        } catch (java.io.IOException e) {
            log.error("IOException occurred during AllegroShopper run. Message:"+e.getMessage());
            e.printStackTrace();
            return;
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
