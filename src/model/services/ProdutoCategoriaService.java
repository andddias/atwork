package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.ProdutoCategoriaDao;
import model.entities.ProdutoCategoria;

public class ProdutoCategoriaService {
	
	private ProdutoCategoriaDao dao = DaoFactory.createProdutoCategoriaDao();
	
	public ProdutoCategoria findById(Integer id) {
		return dao.findById(id);
	}
	
	public List<ProdutoCategoria> findAll() {
		return dao.findAll();
	}
	
	public List<ProdutoCategoria> findDescricao(String string) {
		return dao.findByDescricao(string);
	}
	
	public void saveOrUpdate(ProdutoCategoria obj) {
		if (obj.getId_cat() == null) {
			dao.insert(obj);
		}
		else {
			dao.update(obj);
		}
	}
	
	public void remove(ProdutoCategoria obj) {
		dao.deleteById(obj.getId_cat());
	}
}
