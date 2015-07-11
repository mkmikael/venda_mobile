package blacksoftware.venda.models;

import java.io.Serializable;
import java.math.BigDecimal;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="item_pedido")
public class ItemPedido implements Serializable {

	private static final long serialVersionUID = 7976143108320065168L;
	@DatabaseField(generatedId=true)
	private int id;
	@DatabaseField(columnName="produto_id", foreign=true)
	private Produto produto;
	@DatabaseField(columnName="quantidade", dataType=DataType.BIG_DECIMAL)
	private BigDecimal quantidade;
	@DatabaseField(columnName="total", dataType=DataType.BIG_DECIMAL)
	private BigDecimal total;
	@DatabaseField(columnName="desconto", dataType=DataType.BIG_DECIMAL)
	private BigDecimal desconto;
	@DatabaseField(columnName="prazo")
	private int prazo;

	public ItemPedido() {
	}

	public ItemPedido(int id, Produto produto, BigDecimal quantidade, BigDecimal total,
			BigDecimal desconto, int prazo) {
		super();
		this.id = id;
		this.produto = produto;
		this.quantidade = quantidade;
		this.total = total;
		this.desconto = desconto;
		this.prazo = prazo;
	}

	public int getId() {
		return id;
	}
	
	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getDesconto() {
		return desconto;
	}

	public void setDesconto(BigDecimal desconto) {
		this.desconto = desconto;
	}

	public int getPrazo() {
		return prazo;
	}

	public void setPrazo(int prazo) {
		this.prazo = prazo;
	}

}
