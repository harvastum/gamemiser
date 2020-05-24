package spider;

public interface Shopper {
    void run(String query);
    String getTitle();
    String getImgSrc();
    String getPrice();
}
