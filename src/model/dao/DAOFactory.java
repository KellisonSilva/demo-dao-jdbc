package model.dao;

import db.DB;
import model.dao.Impl.SellerJDBC;

public class DAOFactory {

	public static SellerDAO createSellerDao() {
		return new SellerJDBC(DB.getConnection());
	}
}
