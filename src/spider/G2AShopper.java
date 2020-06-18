package spider;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import java.awt.*;

import static java.lang.Integer.parseInt;
//public class SteamShopper implements ShopperInterface
public class G2AShopper implements Shopper
{
    final String shop = "G2A";
    final String baseUrl = "https://www.g2a.com/search?category_id=gry-c189&device%5B0%5D=1118&query=";
    String title;
    String textPrice;
    String imageSrc;
    String link;
    int price;
    String query;
    String appid;
    public G2AShopper(String query){
        this.query = query;
    }


    @Override
    public void run() {
        try {
            Document doc = Jsoup.connect(baseUrl + query)
                    .referrer("https://www.google.com")
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.97 Safari/537.36")
                    .get();
//            Node docu = doc.selectFirst("#search_resultsRows > a:nth-child(1)");
            title = doc.selectFirst(".Card__title").text();
            textPrice = doc.selectFirst(".Card__price-cost price").text();
            textPrice = textPrice.replace('.', ',') + "zł";

            Element priceElement = doc.selectFirst("div.search_price_discount_combined");
            link = doc.selectFirst(".Card__title > a").attr("href");
            price = parseInt(priceElement.attr("data-price-final"));
            appid = doc.selectFirst("#search_resultsRows > a:nth-child(1)").attr("data-ds-appid");
            imageSrc = doc.selectFirst(".Card__cover > div > img ").attr("data-src");
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
