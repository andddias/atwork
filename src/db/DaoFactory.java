package db;

import model.dao.ProdutoDao;
import model.dao.impl.ProdutoDaoJDBC;

public class DaoFactory {
	
	public static ProdutoDao createSellerDao() {
		return new ProdutoDaoJDBC(DB.getConnection());
	}
	
	/*
	public static DepartmentDao createDepartmetDao() {
		return new DepartmentDaoJDBC(DB.getConnection());
	}
	*/
}
