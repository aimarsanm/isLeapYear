package adduser;


import java.util.Date;



public interface ForumDAOInterface {

	boolean existUserDAO(String id);
	
	User addUserDAO(String id, String name, String tel);

	User removeUserDAO(String id);

	User getUserDAO(String id);
	
	void addBasketDAO(User u, Article art, int quantity);

	void buyDAO(User u, Date d);

	

	Article addStockDAO(String id, String desc, int price, boolean isOutlet, int stock);

	Article removeStockDAO(String id);

}