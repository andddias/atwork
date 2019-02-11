package model.entities;

import java.io.Serializable;

public class ProdutoCategoria implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id_cat;
	private String p_cat;
	
	public ProdutoCategoria(){		
	}

	public ProdutoCategoria(Integer id_cat, String p_cat) {
		this.id_cat = id_cat;
		this.p_cat = p_cat;
	}

	public Integer getId_cat() {
		return id_cat;
	}

	public void setId_cat(Integer id_cat) {
		this.id_cat = id_cat;
	}

	public String getP_cat() {
		return p_cat;
	}

	public void setP_cat(String p_cat) {
		this.p_cat = p_cat;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id_cat == null) ? 0 : id_cat.hashCode());
		result = prime * result + ((p_cat == null) ? 0 : p_cat.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProdutoCategoria other = (ProdutoCategoria) obj;
		if (id_cat == null) {
			if (other.id_cat != null)
				return false;
		} else if (!id_cat.equals(other.id_cat))
			return false;
		if (p_cat == null) {
			if (other.p_cat != null)
				return false;
		} else if (!p_cat.equals(other.p_cat))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProdutoCategoria [id_cat=" + id_cat + ", p_cat=" + p_cat + "]";
	}
}
