package model.dao;

import model.dao.Impl.SellerJDBC;

public class DAOFactory {

	public static SellerDAO createSellerDao() {
		return new SellerJDBC();
	}
}
