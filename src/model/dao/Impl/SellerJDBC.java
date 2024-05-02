package model.dao.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

		PreparedStatement ps = null;

		try {

			ps = conn.prepareStatement(
					"INSERT INTO seller (Name, Email, BirthDate, BaseSalary, DepartmentId) VALUES (?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, seller.getName());
			ps.setString(2, seller.getEmail());
			ps.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
			ps.setDouble(4, seller.getBaseSalary());
			ps.setInt(5, seller.getDepartment().getId());

			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = ps.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					seller.setId(id);
				}
				DB.closeResultSet(rs);
			} else {
				throw new DbException("Unexpected error!! no rows affected!!");
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(ps);
		}

	}

	@Override
	public void update(Seller seller) {
		PreparedStatement ps = null;

		try {

			ps = conn.prepareStatement(
					"UPDATE seller SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? WHERE Id = ?");

			ps.setString(1, seller.getName());
			ps.setString(2, seller.getEmail());
			ps.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
			ps.setDouble(4, seller.getBaseSalary());
			ps.setInt(5, seller.getDepartment().getId());
			ps.setInt(6, seller.getId());

			ps.executeUpdate();

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(ps);
		}

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
			/*
			 * caso a consulta nao vier nenhum registro a condicao dara falso e retornara
			 * null
			 */
			rs = pst.executeQuery();

			if (rs.next()) {
				Department dep = instantiateDepartment(rs);
				Seller seller = instantiateSeller(rs, dep);

				return seller;
			}

			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(pst);
			DB.closeResultSet(rs);

		}
	}

	private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
		Seller seller = new Seller();
		seller.setId(rs.getInt("Id"));
		seller.setName(rs.getString("Name"));
		seller.setEmail(rs.getString("Email"));
		seller.setBirthDate(rs.getDate("BirthDate"));
		seller.setBaseSalary(rs.getDouble("BaseSalary"));
		seller.setDepartment(dep);

		return seller;
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));
		return dep;
	}

	@Override
	public List<Seller> findAll() {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {

			pst = conn.prepareStatement("SELECT seller .*, department.Name as DepName "
					+ "FROM seller INNER JOIN department ON " + "seller.DepartmentId = Department.Id ORDER by Name;");

			List<Seller> listSeller = new ArrayList<>();
			Map<Integer, Department> mapDep = new HashMap<>();
			rs = pst.executeQuery();
			while (rs.next()) {
				/* verifica por meio da chave id se existe ou nao esse objeto */
				Department department = mapDep.get(rs.getInt("DepartmentId"));

				/* caso seja nullo criar um objeto e add no map */
				if (department == null) {
					department = instantiateDepartment(rs);
					mapDep.put(rs.getInt("DepartmentId"), department);
				}
				Seller objSeller = instantiateSeller(rs, department);
				listSeller.add(objSeller);
			}

			return listSeller;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(pst);
		}

	}

	@Override
	public List<Seller> findByDepartment(Department dep) {

		PreparedStatement pst = null;
		ResultSet rs = null;

		try {

			pst = conn.prepareStatement(
					"SELECT seller .*, department.Name as DepName " + "FROM seller INNER JOIN department ON "
							+ "seller.DepartmentId = Department.Id " + "WHERE DepartmentId = ? ORDER by Name;");

			pst.setInt(1, dep.getId());

			List<Seller> listSeller = new ArrayList<>();
			Map<Integer, Department> mapDep = new HashMap<>();
			rs = pst.executeQuery();
			while (rs.next()) {
				/* verifica por meio da chave id se existe ou nao esse objeto */
				Department department = mapDep.get(rs.getInt("DepartmentId"));

				/* caso seja nullo criar um objeto e add no map */
				if (department == null) {
					department = instantiateDepartment(rs);
					mapDep.put(rs.getInt("DepartmentId"), department);
				}
				Seller objSeller = instantiateSeller(rs, department);
				listSeller.add(objSeller);
			}

			return listSeller;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(pst);
		}

	}

}
