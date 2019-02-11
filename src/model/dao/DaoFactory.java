package model.dao;

import db.DB;
import model.dao.impl.ProdutoCategoriaDaoJDBC;
import model.dao.impl.ProdutoDaoJDBC;

public class DaoFactory {
	
	public static ProdutoDao createProdutoDao() {
		return new ProdutoDaoJDBC(DB.getConnection());
	}	
	
	public static ProdutoCategoriaDao createProdutoCategoriaDao() {
		return new ProdutoCategoriaDaoJDBC(DB.getConnection());
	}
}
