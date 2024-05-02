package Application;

import java.util.Date;
import model.dao.DAOFactory;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {

		Department objT = new Department(1, "Books");
//		Seller seller = new Seller(21, "Bob", "Bob@gmail.com", new Date(), 3000.0, objT);
//		System.out.println("============= SELLER TEST 1 =================");
		SellerDAO sellerDAO = DAOFactory.createSellerDao();
//		Seller findById = sellerDAO.findById(3);
//		System.out.println(findById);
//		System.out.println("============= SELLER TEST 1 =================");
//		System.out.println("\n============= SELLER TEST 2 findByDepartment =================");
//		Department dep = new Department(2, null);
//		List<Seller> list = sellerDAO.findByDepartment(dep);
//		list.forEach(c -> System.out.println("====================\n" + c));
//		System.out.println("============= SELLER TEST 2 findByDepartment =================");
//		System.out.println("\n============= SELLER TEST 3 findAll =================");
//		List<Seller> list = sellerDAO.findAll();
//		list.forEach(c -> System.out.println("====================\n" + c));
//		System.out.println("============= SELLER TEST 3 findAll =================");
//		System.out.println("============= SELLER TEST 4 Insert =================");
//		Seller newSeller = new Seller(null, "Greg", "greg@gmail.com", new Date(), 4000.0, objT);
//		sellerDAO.insert(newSeller);
//		System.out.println("Inserted! new Id: " + newSeller);
//		System.out.println("============= SELLER TEST 4 Insert =================");
		
		System.out.println("============= SELLER TEST 5 Update =================");
		Seller seller = sellerDAO.findById(1);
		seller.setName("Martha Wayne");
		sellerDAO.update(seller);
		System.out.println(seller + "\nUpdate completed!!");
		System.out.println("============= SELLER TEST 5 Update =================");
	}

}
