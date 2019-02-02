package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbIntegrityException;
import model.dao.ProdutoDao;
import model.entities.Produto;

public class ProdutoDaoJDBC implements ProdutoDao{
	
	private Connection conn;
	
	public ProdutoDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Produto obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Produto obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;

		try {
			conn.setAutoCommit(false);
			
			st = conn.prepareStatement(
					"DELETE FROM produto "
					+ "WHERE id_produto = ?");
			
			st.setInt(1, id);			
			
			st.executeUpdate();
			
			conn.commit();
		}
		catch (SQLException e) {
			try {
				conn.rollback();
				throw new DbIntegrityException(e.getMessage());
			} catch (SQLException e1) {
				throw new DbIntegrityException("Erro ao tentar reverter! Causado por: " + e1.getMessage());
			}			
		}
		finally {
			DB.closeStatement(st);
		}		
		
	}

	@Override
	public Produto findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Produto> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT * FROM produto "
					+ "ORDER BY p_desc");	
			rs = st.executeQuery();
			
			List<Produto> listProduto = new ArrayList<>();

			while (rs.next()) {			
				Produto produto = instanciacaoProduto(rs);	
				listProduto.add(produto);				
			}
			
			return listProduto;			
					
		}
		catch (SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	private Produto instanciacaoProduto(ResultSet rs) throws SQLException {
		Produto prod = new Produto();
		prod.setId_produto(rs.getInt("id_produto"));
		prod.setP_cat(rs.getInt("p_cat"));
		prod.setP_codigo(rs.getString("p_codigo"));
		prod.setP_desc(rs.getString("p_desc"));
		prod.setP_venda(rs.getDouble("p_venda"));
		prod.setP_custo(rs.getDouble("p_custo"));
		prod.setP_codBarra(rs.getString("p_barra"));
		prod.setP_fab(rs.getInt("p_fab"));
		prod.setP_forn(rs.getInt("p_forn"));
		prod.setP_cfab(rs.getString("p_cfab"));
		prod.setP_lucro(rs.getInt("p_lucro"));
		prod.setP_maxDesc(rs.getInt("p_maxdesc"));
		prod.setP_status(rs.getInt("p_st"));		
		return prod;
	}

	@Override
	public List<Produto> findByDepartment(Produto department) {
		// TODO Auto-generated method stub
		return null;
	}

}
