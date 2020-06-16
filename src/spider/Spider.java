
package spider;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

//import org.jsoup.nodes.Document;
public class Spider {
    public static void main (String[] args){
        ArrayList<Shopper> shoppers = new ArrayList<>();
        String query = "gothic";
        shoppers.add(new SteamShopper(query));

        for (Shopper shopper : shoppers){
            shopper.run();
            System.out.println("title:");
            System.out.println(shopper.getTitle());
            System.out.println("price:");
            System.out.println(shopper.getPrice());
            System.out.println("img:");
            System.out.println(shopper.getImgSrc());
        }
    }
}
