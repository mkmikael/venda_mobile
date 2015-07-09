package blacksoftware.venda.models;

import co.uk.rushorm.core.RushObject;
import co.uk.rushorm.core.annotations.RushCustomTableName;
import co.uk.rushorm.core.annotations.RushIgnore;

@RushCustomTableName(name = "produto")
public class Produto extends RushObject {
	
	private String codigo;
	private String nome;
	private String fornecedor;
	private String grupo;
	private double estoque;
	private double preco;
	private String embalagem;
	@RushIgnore
	private boolean vendido = false;
	
	public Produto() {
	}
	
	public Produto(String codigo, String nome, String fornecedor, String grupo,
			double estoque, double preco, String embalagem) {
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.fornecedor = fornecedor;
		this.grupo = grupo;
		this.estoque = estoque;
		this.preco = preco;
		this.embalagem = embalagem;
	}

	public Produto(String codigo, String nome, String fornecedor, String grupo,
			double estoque, double preco, String embalagem, boolean vendido) {
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.fornecedor = fornecedor;
		this.grupo = grupo;
		this.estoque = estoque;
		this.preco = preco;
		this.embalagem = embalagem;
		this.vendido = vendido;
	}

	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getFornecedor() {
		return fornecedor;
	}
	public void setFornecedor(String fornecedor) {
		this.fornecedor = fornecedor;
	}
	public String getGrupo() {
		return grupo;
	}
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	public double getEstoque() {
		return estoque;
	}
	public void setEstoque(double estoque) {
		this.estoque = estoque;
	}
	public double getPreco() {
		return preco;
	}
	public void setPreco(double preco) {
		this.preco = preco;
	}
	public String getEmbalagem() {
		return embalagem;
	}
	public void setEmbalagem(String embalagem) {
		this.embalagem = embalagem;
	}
	public boolean isVendido() {
		return vendido;
	}
	public void setVendido(boolean vendido) {
		this.vendido = vendido;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
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
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	public String toString() {
		return nome;
	}
}
