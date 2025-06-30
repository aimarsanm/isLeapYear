    package getbonus;

    import java.text.SimpleDateFormat;
    import java.util.Date;
    import java.util.Iterator;
    
    public class ForumBL {
        ForumDAOInterface dao;
        // API Usuarios

        public ForumBL(ForumDAOInterface dao) {
            this.dao = dao;
        }

        /**
         * Calculates the bonus for a user based on their purchases within a specific date range.
         *
         * @param id The user's ID (DNI).
         * @return The calculated bonus amount.
         * @throws Exception If the ID is null, invalid, or the user is not found in the database.
         */
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
