package model.dao.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import db.DB;
import db.DbException;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

public class SellerJDBC implements SellerDAO {

	private Connection conn;

	public SellerJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller seller) {

	}

	@Override
	public void update(Seller seller) {

	}

	@Override
	public void deleteById(Integer Id) {

	}

	@Override
	public Seller findById(Integer Id) {

		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = conn.prepareStatement(
					"SELECT seller .*, department.Name as DepName FROM seller INNER JOIN department ON "
					+ "seller.DepartmentId = Department.Id WHERE seller.Id = ?");

			pst.setInt(1, Id);
			/*caso a consulta nao vier nenhum registro a condicao dara falso e retornara null*/
			rs = pst.executeQuery();

			if (rs.next()) {
				Department dep = new Department();
				dep.setId(rs.getInt("DepartmentId"));
				dep.setName(rs.getString("DepName"));
				
				Seller seller = new Seller();
				seller.setId(rs.getInt("Id"));
				seller.setName(rs.getString("Name"));
				seller.setEmail(rs.getString("Email"));
				seller.setBirthDate(rs.getDate("BirthDate"));
				seller.setBaseSalary(rs.getDouble("BaseSalary"));
				seller.setDepartment(dep);

				return seller;
			}

			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(pst);
			DB.closeResultSet(rs);
			
		}
	}

	@Override
	public List<Seller> findAll() {
		return null;
	}

}
