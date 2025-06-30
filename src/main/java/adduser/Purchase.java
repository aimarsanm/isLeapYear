    package adduser;


import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

public class Purchase {
    private Date purchasedDate;
    private double cost = 0;
    private HashMap<Article, PurchasedArticle> basket = new HashMap<Article, PurchasedArticle>();

    public Date getPurchasedDate() {
        return purchasedDate;
    }

    public void setPurchasedDate(Date d) {
        this.purchasedDate = d;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    /**
     * Returns an iterator of the articles added to the basket.
     * 
     * @return The iterator of the articles included in the basket
     */
    public Iterator<PurchasedArticle> getPurchaseIterator() {
        return basket.values().iterator();
    }

    /**
     * Add a new article to the basket.
     * 
     * @param art The article to buy.
     * @param q The added units
     * @return The total ammount of the articles included in the basket
     */
    public double addBasket(Article art, int q) {
        if (purchasedDate != null)
            throw new RuntimeException("The purchase is closed. No articles can be added");
        if (art.getStock() < q)
            throw new RuntimeException("There is not enough stock");

        PurchasedArticle purchasedArticle = basket.get(art);
        if (purchasedArticle == null) {
            PurchasedArticle part = new PurchasedArticle(art, q);
            basket.put(art, part);
        } else
            purchasedArticle.setQuantity(purchasedArticle.getQuantity() + q);
        art.setStock(art.getStock() - q);

        cost = cost + q * art.getPrice();
        return cost;
    }

    /**
     * Remove a number of units of an article from the basket that is not paid yet.
     * 
     * @param art the removed article from the basket
     * @param q the removed units
     * @return the updated cost of the basket
     */

    public double removeBasket(Article art, int q) {
        if (purchasedDate != null) {
            throw new RuntimeException("The purchase is closed. No articles can be removed");
        }
        PurchasedArticle purchasedArticle = basket.get(art);

        if (purchasedArticle == null)
            throw new RuntimeException("This product was not in the basket.");

        int purchasedQuantity = purchasedArticle.getQuantity();
        if (purchasedQuantity < q)
            throw new RuntimeException("Yo have not too much products.");

        if (purchasedQuantity == q)
            basket.remove(art);
        else
            purchasedArticle.setQuantity(purchasedQuantity - q);

        double amount = q * purchasedArticle.getPrice();
        art.setStock(art.getStock() + q);
        cost = cost - amount;

        return cost;
    }

    public void buy() throws RuntimeException {
        if (purchasedDate != null)
            throw new RuntimeException("The purchase is already made");
        purchasedDate = new Date();
    }
}
