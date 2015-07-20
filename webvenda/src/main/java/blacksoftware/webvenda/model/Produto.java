package blacksoftware.webvenda.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "produto")
public class Produto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6794534413726076197L;
	@Id
	@GeneratedValue
	private Long id;
	private String codigo;
	private String nome;
	private String embalagem;
	private BigDecimal preco;
	@Column(name = "preco_minimo")
	private BigDecimal precoMinimo;
	@ManyToOne
	private Grupo grupo;
	@ManyToOne
	private Fornecedor fornecedor;

	public Produto() {
	}

	public Produto(Long id, String codigo, String nome, String embalagem,
			BigDecimal preco, BigDecimal precoMinimo, Fornecedor fornecedor) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.nome = nome;
		this.embalagem = embalagem;
		this.preco = preco;
		this.precoMinimo = precoMinimo;
		this.fornecedor = fornecedor;
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

	public String getEmbalagem() {
		return embalagem;
	}

	public void setEmbalagem(String embalagem) {
		this.embalagem = embalagem;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public BigDecimal getPrecoMinimo() {
		return precoMinimo;
	}

	public void setPrecoMinimo(BigDecimal precoMinimo) {
		this.precoMinimo = precoMinimo;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public Long getId() {
		return id;
	}

}
