package blacksoftware.venda.models;

import java.io.Serializable;
import java.math.BigDecimal;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="produto")
public class Produto implements Serializable {

	private static final long serialVersionUID = -6654002649371591056L;
	@DatabaseField(generatedId=true)
	private int id;
	@DatabaseField(columnName="codigo")
	private String codigo;
	@DatabaseField(columnName="nome")
	private String nome;
	@DatabaseField(columnName="fornecedor")
	private String fornecedor;
	@DatabaseField(columnName="grupo")
	private String grupo;
	@DatabaseField(columnName="embalagem")
	private String embalagem;
	@DatabaseField(columnName="estoque", dataType=DataType.BIG_DECIMAL)
	private BigDecimal estoque;
	@DatabaseField(columnName="preco", dataType=DataType.BIG_DECIMAL)
	private BigDecimal preco;
	@DatabaseField(columnName="preco_minimo", dataType=DataType.BIG_DECIMAL)
	private BigDecimal precoMinimo;
	private transient boolean vendido = false;

	public Produto() {
	}

	public Produto(int id, String codigo, String nome, String fornecedor,
			String grupo, BigDecimal estoque, BigDecimal preco, BigDecimal precoMinimo, String embalagem) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.nome = nome;
		this.fornecedor = fornecedor;
		this.grupo = grupo;
		this.estoque = estoque;
		this.preco = preco;
		this.precoMinimo = precoMinimo;
		this.embalagem = embalagem;
	}

	public Produto(int id, String codigo, String nome, String fornecedor,
			String grupo, BigDecimal estoque, BigDecimal preco, BigDecimal precoMinimo, String embalagem,
			boolean vendido) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.nome = nome;
		this.fornecedor = fornecedor;
		this.grupo = grupo;
		this.estoque = estoque;
		this.preco = preco;
		this.precoMinimo = precoMinimo;
		this.embalagem = embalagem;
		this.vendido = vendido;
	}

	public int getId() {
		return id;
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

	public BigDecimal getEstoque() {
		return estoque;
	}

	public void setEstoque(BigDecimal estoque) {
		this.estoque = estoque;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public BigDecimal getPrecoMinimo() {
		return precoMinimo;
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
