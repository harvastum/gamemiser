package spider;

public interface Shopper extends Runnable
{
     String getTitle();
     String getImgSrc();
     String getPrice();
     String getLink();
     String getShop();
     void run();
}
