
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
        shoppers.add(new SteamShopper());

        for (Shopper shopper : shoppers){
            shopper.run("civ");
            System.out.println("title:");
            System.out.println(shopper.getTitle());
            System.out.println("price:");
            System.out.println(shopper.getPrice());
            System.out.println("img:");
            System.out.println(shopper.getImgSrc());

        }
    }
}
