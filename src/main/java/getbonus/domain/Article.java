package getbonus.domain;

public class Article {

    private String id;
    private String description;
    private float price;
    private boolean isOutlet;
    private int stock;

    public Article(String id, String description, float price2, boolean isOutlet, int stock) {
        super();
        this.id = id;
        this.description = description;
        this.price = price2;
        this.isOutlet = isOutlet;
        this.stock = stock;
    }

    public String getId() {
        return id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isOutlet() {
        return isOutlet;
    }

    public void setOutlet(boolean isOutlet) {
        this.isOutlet = isOutlet;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
