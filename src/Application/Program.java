package Application;

import java.util.Date;
import model.dao.DAOFactory;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {

		Department objT = new Department(1, "Books");

		Seller seller = new Seller(21, "Bob", "Bob@gmail.com", new Date(), 3000.0, objT);

		SellerDAO sellerDAO = DAOFactory.createSellerDao();
		
		System.out.println(seller);

	}

}
