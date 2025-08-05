    package getbonus.bl;

    import java.text.SimpleDateFormat;
    import java.util.Date;
    import java.util.Iterator;

    import getbonus.db.ForumDAOInterface;
    import getbonus.domain.Article;
    import getbonus.domain.Purchase;
    import getbonus.domain.PurchasedArticle;
    import getbonus.domain.User;
    import getbonus.exceptions.NullParameterException;
    import getbonus.exceptions.QuantityLessThan1Exception;
    import getbonus.exceptions.UserNotFoundException;

    public class ForumBL {
        ForumDAOInterface dao;
        // API Usuarios

        public ForumBL(ForumDAOInterface dao) {
            this.dao = dao;
        }

        public User addUser(String id, String name, String tel) throws NullParameterException, UserNotFoundException {
            if (id == null || name == null)
                throw new NullParameterException("id or name is null");
            if (!dao.existUserDAO(id)) {
                dao.addUserDAO(id, name, tel);
                return dao.getUserDAO(id);
            } else
                throw new UserNotFoundException("id no in DB");
        }

        public boolean removeUser(String id) {
            dao.removeUserDAO(id);
            return false;
        }

        public User getUser(String id) {
            return dao.getUserDAO(id);
        }

        // Artikulua saskira gehitzen du
        public void addBasket(User u, Article art, int quantity) throws QuantityLessThan1Exception, NullParameterException {
            if (quantity <= 0)
                throw new QuantityLessThan1Exception("ERROR, quantity must be greater that 0");
            if (u == null || art == null)
                throw new NullParameterException("ERROR, user or article is null");

            // in other case
            dao.addBasketDAO(u, art, quantity);
        }

        // Saskian dauden artikuloak erosi egiten dira
        public void buy(User u, Date d) {
            dao.buyDAO(u, d);
        }

        public Iterator<Purchase> getPurchases(User u, Date firstDate, Date lastDate) {
            return dao.getPurchasesDAO(u, firstDate, lastDate);
        }

        public Article addStock(String id, String desc, int precio, boolean isOutlet, int stock) {
            return dao.addStockDAO(id, desc, precio, isOutlet, stock);
        }

        public Article removeStock(String id) {
            return dao.removeStockDAO(id);
        }

        public float getBonus(String id) throws Exception {
            if ((id == null) || (!new ValidadorDNI(id).validar()))
                throw new Exception("id null or not valid");

            User u = dao.getUserDAO(id);

            if (u == null)
                throw new Exception("NAN not in Database");

            if (u.getTelephone() == null)
                throw new Exception(id + " not registered telephone");

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date firstDate = sdf.parse("01/09/2022");
            Date lastDate = sdf.parse("06/12/2022");

            Iterator<Purchase> purchases = dao.getPurchasesDAO(u, firstDate, lastDate);
            float sumPurchases = 0;
            float vat = 0;
            while (purchases.hasNext()) {
                System.out.println("sum" + sumPurchases);
                Purchase c = purchases.next();
                Iterator<PurchasedArticle> articles = c.getPurchaseIterator();
                while (articles.hasNext()) {
                    PurchasedArticle article = articles.next();
                    if (!article.isOutlet())
                        sumPurchases = sumPurchases + article.getPrice() * article.getQuantity();
                    System.out.println("sum" + sumPurchases);

                }
            }
            if (sumPurchases > 30)
                if (sumPurchases > 288)
                    vat = 50;
                else
                    vat = (float) (sumPurchases * 0.1735);

            return vat;
        }
    }
