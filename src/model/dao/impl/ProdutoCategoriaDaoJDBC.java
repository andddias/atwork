package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbIntegrityException;
import model.dao.ProdutoCategoriaDao;
import model.entities.ProdutoCategoria;

public class ProdutoCategoriaDaoJDBC implements ProdutoCategoriaDao{
	
	private Connection conn;
	
	public ProdutoCategoriaDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(ProdutoCategoria obj) {
		
		PreparedStatement st = null;

		try {
			st = conn.prepareStatement(
					"INSERT INTO p_categoria "
					+ "(p_cat) "
					+ "VALUES "
					+ "(?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getP_cat());
			
			
			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId_cat(id);
				}
				DB.closeResultSet(rs);
			}
			else {
				throw new DbIntegrityException("Erro inesperado! Nenhuma linha afetada!");
			}
		}
		catch (SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}		
	}

	@Override
	public void update(ProdutoCategoria obj) {
		
		PreparedStatement st = null;

		try {
			conn.setAutoCommit(false);
			st = conn.prepareStatement(
					"UPDATE p_categoria "
					+ "SET "
					+ "p_cat = ? "
					+ "WHERE id_cat = ?");
			
			st.setString(1, obj.getP_cat());			
			st.setInt(2, obj.getId_cat());
			
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
	public void deleteById(Integer id) {
		
		PreparedStatement st = null;

		try {
			conn.setAutoCommit(false);
			
			st = conn.prepareStatement(
					"DELETE FROM p_categoria "
					+ "WHERE id_cat = ?");
			
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
	public ProdutoCategoria findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProdutoCategoria> findAll() {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT * FROM p_categoria "
					+ "ORDER BY p_cat");	
			rs = st.executeQuery();
			
			List<ProdutoCategoria> list = new ArrayList<>();

			while (rs.next()) {			
				ProdutoCategoria obj = instanciacaoProdutoCategoria(rs);	
				list.add(obj);				
			}
			
			return list;			
					
		}
		catch (SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	private ProdutoCategoria instanciacaoProdutoCategoria(ResultSet rs) throws SQLException {
		ProdutoCategoria obj = new ProdutoCategoria();
		obj.setId_cat(rs.getInt("id_cat"));
		obj.setP_cat(rs.getString("p_cat"));
		
		return obj;
	}

	@Override
	public List<ProdutoCategoria> findByDescricao(String string) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT * FROM p_categoria "
					+ "WHERE p_cat  LIKE \"%"
					+ string
					+ "%\" "
					+ "ORDER BY p_cat");			
			
			rs = st.executeQuery();
			
			List<ProdutoCategoria> list = new ArrayList<>();

			while (rs.next()) {			
				ProdutoCategoria obj = instanciacaoProdutoCategoria(rs);	
				list.add(obj);				
			}
			
			return list;			
					
		}
		catch (SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
}
