package model.entities;

import java.io.Serializable;

public class Produto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id_produto;
	private String p_codigo;
	private String p_desc;
	private Double p_venda;
	private Double p_custo;
	private String p_codBarra;
	private Integer p_fab;
	private Integer p_forn;
	private String p_cfab;
	private Integer p_lucro;
	private Integer p_maxDesc;
	private Integer p_status;
	
	private ProdutoCategoria produtoCategoria;
	
	public Produto() {		
	}

	public Produto(Integer id_produto, String p_codigo, String p_desc, Double p_venda, Double p_custo,
			String p_codBarra, Integer p_fab, Integer p_forn, String p_cfab, Integer p_lucro,
			Integer p_maxDesc, Integer p_status, ProdutoCategoria produtoCategoria) {
		this.id_produto = id_produto;
		this.p_codigo = p_codigo;
		this.p_desc = p_desc;
		this.p_venda = p_venda;
		this.p_custo = p_custo;
		this.p_codBarra = p_codBarra;
		this.p_fab = p_fab;
		this.p_forn = p_forn;
		this.p_cfab = p_cfab;
		this.p_lucro = p_lucro;
		this.p_maxDesc = p_maxDesc;
		this.p_status = p_status;
		this.produtoCategoria = produtoCategoria;
	}

	public Integer getId_produto() {
		return id_produto;
	}

	public void setId_produto(Integer id_produto) {
		this.id_produto = id_produto;
	}

	public String getP_codigo() {
		return p_codigo;
	}

	public void setP_codigo(String p_codigo) {
		this.p_codigo = p_codigo;
	}

	public String getP_desc() {
		return p_desc;
	}

	public void setP_desc(String p_desc) {
		this.p_desc = p_desc;
	}

	public Double getP_venda() {
		return p_venda;
	}

	public void setP_venda(Double p_venda) {
		this.p_venda = p_venda;
	}

	public Double getP_custo() {
		return p_custo;
	}

	public void setP_custo(Double p_custo) {
		this.p_custo = p_custo;
	}

	public String getP_codBarra() {
		return p_codBarra;
	}

	public void setP_codBarra(String p_codBarra) {
		this.p_codBarra = p_codBarra;
	}

	public Integer getP_fab() {
		return p_fab;
	}

	public void setP_fab(Integer p_fab) {
		this.p_fab = p_fab;
	}

	public Integer getP_forn() {
		return p_forn;
	}	

	public void setP_forn(Integer p_forn) {
		this.p_forn = p_forn;
	}

	public String getP_cfab() {
		return p_cfab;
	}

	public void setP_cfab(String p_cfab) {
		this.p_cfab = p_cfab;
	}

	public Integer getP_lucro() {
		return p_lucro;
	}

	public void setP_lucro(Integer p_lucro) {
		this.p_lucro = p_lucro;
	}

	public Integer getP_maxDesc() {
		return p_maxDesc;
	}

	public void setP_maxDesc(Integer p_maxDesc) {
		this.p_maxDesc = p_maxDesc;
	}

	public Integer getP_status() {
		return p_status;
	}

	public void setP_status(Integer p_status) {
		this.p_status = p_status;
	}	

	public ProdutoCategoria getProdutoCategoria() {
		return produtoCategoria;
	}

	public void setProdutoCategoria(ProdutoCategoria produtoCategoria) {
		this.produtoCategoria = produtoCategoria;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id_produto == null) ? 0 : id_produto.hashCode());
		result = prime * result + ((p_codBarra == null) ? 0 : p_codBarra.hashCode());
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
		Produto other = (Produto) obj;
		if (id_produto == null) {
			if (other.id_produto != null)
				return false;
		} else if (!id_produto.equals(other.id_produto))
			return false;
		if (p_codBarra == null) {
			if (other.p_codBarra != null)
				return false;
		} else if (!p_codBarra.equals(other.p_codBarra))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Produto [id_produto=" + id_produto + ", p_codigo=" + p_codigo + ", p_desc=" + p_desc + ", p_venda="
				+ p_venda + ", p_custo=" + p_custo + ", p_codBarra=" + p_codBarra + ", p_fab=" + p_fab + ", p_forn="
				+ p_forn + ", p_cfab=" + p_cfab + ", p_lucro=" + p_lucro + ", p_maxDesc=" + p_maxDesc + ", p_status="
				+ p_status + ", produtoCategoria=" + produtoCategoria + "]";
	}
}
