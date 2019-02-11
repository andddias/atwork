package model.dao;

import java.util.List;

import model.entities.ProdutoCategoria;

public interface ProdutoCategoriaDao {
	
	void insert(ProdutoCategoria obj);
	void update(ProdutoCategoria obj);
	void deleteById(Integer id);
	ProdutoCategoria findById(Integer id);
	List<ProdutoCategoria> findAll();
	List<ProdutoCategoria> findByDescricao(String string);

}
