package blacksoftware.venda.models;

import java.io.Serializable;
import java.math.BigDecimal;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import blacksoftware.venda.models.enums.TipoUnidade;

@DatabaseTable(tableName="unidade")
public class Unidade implements Serializable {

	private static final long serialVersionUID = -3479206665111373452L;
	@DatabaseField(generatedId = true)
	private Integer id;
	@DatabaseField(foreignAutoRefresh = true, maxForeignAutoRefreshLevel = 3, foreign = true)
	private Produto produto;
	@DatabaseField(dataType = DataType.ENUM_INTEGER)
	private TipoUnidade tipo;
	@DatabaseField(dataType = DataType.BIG_DECIMAL)
	private BigDecimal valor;
	@DatabaseField
	private int quantidade;

	public Unidade() {
	}

	public Integer getId() {
		return id;
	}

	public Unidade(Integer id, Produto produto, TipoUnidade tipo, BigDecimal valor) {
		super();
		this.id = id;
		this.produto = produto;
		this.tipo = tipo;
		this.valor = valor;
	}

	public Unidade(Integer id, Produto produto, TipoUnidade tipo, BigDecimal valor, int quantidade) {
		super();
		this.id = id;
		this.produto = produto;
		this.tipo = tipo;
		this.valor = valor;
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

	public TipoUnidade getTipo() {
		return tipo;
	}

	public void setTipo(TipoUnidade tipo) {
		this.tipo = tipo;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	
}
