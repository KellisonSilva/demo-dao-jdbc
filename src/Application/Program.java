package Application;

import model.dao.DAOFactory;
import model.dao.SellerDAO;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {

//		Department objT = new Department(1, "Books");
//		Seller seller = new Seller(21, "Bob", "Bob@gmail.com", new Date(), 3000.0, objT);

		System.out.println("============= SELLER TEST 1 =================");
		SellerDAO sellerDAO = DAOFactory.createSellerDao();
		Seller findById = sellerDAO.findById(3);
		System.out.println(findById);
		System.out.println("============= SELLER TEST 1 =================");

	}

}
