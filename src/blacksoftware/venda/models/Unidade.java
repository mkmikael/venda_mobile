package blacksoftware.venda.models;

import java.io.Serializable;
import java.math.BigDecimal;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "unidade")
public class Unidade implements Serializable {

	private static final long serialVersionUID = -3479206665111373452L;
	@DatabaseField(generatedId = true)
	private Integer id;
	@DatabaseField(foreignAutoRefresh = true, foreignAutoCreate = true, maxForeignAutoRefreshLevel = 3, foreign = true)
	private Produto produto;
	@DatabaseField
	private String tipo;
	@DatabaseField(dataType = DataType.BIG_DECIMAL)
	private BigDecimal valor;
	@DatabaseField(dataType = DataType.BIG_DECIMAL)
	private BigDecimal valorMinimo;
	@DatabaseField
	private int quantidade;

	public Unidade() {
	}

	public Integer getId() {
		return id;
	}

	public Unidade(Integer id, Produto produto, String tipo, BigDecimal valor) {
		super();
		this.id = id;
		this.produto = produto;
		this.tipo = tipo;
		this.valor = valor;
	}

	public Unidade(Integer id, Produto produto, String tipo, BigDecimal valor, BigDecimal valorMinimo, int quantidade) {
		super();
		this.id = id;
		this.produto = produto;
		this.tipo = tipo;
		this.valor = valor;
		this.valorMinimo = valorMinimo;
		this.quantidade = quantidade;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public BigDecimal getValorMinimo() {
		return valorMinimo;
	}

	public void setValorMinimo(BigDecimal valorMinimo) {
		this.valorMinimo = valorMinimo;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	@Override
	public String toString() {
		return tipo;
	}

}
