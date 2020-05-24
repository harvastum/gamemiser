
package spider;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

import static java.lang.Integer.parseInt;

//import org.jsoup.nodes.Document;
public class Spider {
    public static void main (String[] args){
        String url = "https://store.steampowered.com/search/?term=skyrim";

        try {
//            Document doc = Jsoup.connect(url).get();
            Document doc = // <editor-fold desc="long doc string">
             Jsoup.parse("<a href=\"https://store.steampowered.com/app/489830/The_Elder_Scrolls_V_Skyrim_Special_Edition/?snr=1_7_7_151_150_1\" data-ds-appid=\"489830\" data-ds-itemkey=\"App_489830\" data-ds-tagids=\"[1695,122,21,4182,1684,4747,4046]\" data-ds-descids=\"[5]\" data-ds-crtrids=\"[33028765,35501445]\" onmouseover=\"GameHover( this, event, 'global_hover', {&quot;type&quot;:&quot;app&quot;,&quot;id&quot;:489830,&quot;public&quot;:1,&quot;v6&quot;:1} );\" onmouseout=\"HideGameHover( this, event, 'global_hover' )\" class=\"search_result_row ds_collapse_flag \" data-search-page=\"1\"> \n" +
                    " <div class=\"col search_capsule\">\n" +
                    "  <img src=\"https://steamcdn-a.akamaihd.net/steam/apps/489830/capsule_sm_120.jpg?t=1590166521\" srcset=\"https://steamcdn-a.akamaihd.net/steam/apps/489830/capsule_sm_120.jpg?t=1590166521 1x, https://steamcdn-a.akamaihd.net/steam/apps/489830/capsule_231x87.jpg?t=1590166521 2x\">\n" +
                    " </div> \n" +
                    " <div class=\"responsive_search_name_combined\"> \n" +
                    "  <div class=\"col search_name ellipsis\"> <span class=\"title\">The Elder Scrolls V: Skyrim Special Edition</span> \n" +
                    "   <p> <span class=\"platform_img win\"></span> </p> \n" +
                    "  </div> \n" +
                    "  <div class=\"col search_released responsive_secondrow\">\n" +
                    "   27 Oct, 2016\n" +
                    "  </div> \n" +
                    "  <div class=\"col search_reviewscore responsive_secondrow\"> <span class=\"search_review_summary positive\" data-tooltip-html=\"Very Positive<br>91% of the 36,863 user reviews for this game are positive.\"> </span> \n" +
                    "  </div> \n" +
                    "  <div class=\"col search_price_discount_combined responsive_secondrow\" data-price-final=\"17499\"> \n" +
                    "   <div class=\"col search_discount responsive_secondrow\"> \n" +
                    "   </div> \n" +
                    "   <div class=\"col search_price  responsive_secondrow\">\n" +
                    "     174,99zł \n" +
                    "   </div> \n" +
                    "  </div> \n" +
                    " </div> \n" +
                    " <div style=\"clear: left;\"></div> </a>\n" +
                    "Title:\n" +
                    "\n" +
                    "Process finished with exit code 0\n");
            // </editor-fold>

            System.out.println("doc:");
            System.out.println(doc);
            String title = doc.select("span.title").text();
            String textPrice = doc.select("div.search_price").text();
            Element priceElement = doc.select("div.search_price_discount_combined").first();
            String imageSrc = doc.selectFirst("img").attr("src");
            int price = parseInt(priceElement.attr("data-price-final"));

            System.out.println("Title:");
            System.out.println(title);
            System.out.println("Text price:");
            System.out.println(textPrice);
            System.out.println("Num price:");
            System.out.println(price);
            System.out.println("Image:");
            System.out.println(imageSrc);

        } catch (/*IO*/Exception e) {
            e.printStackTrace();
        }
    }
}
