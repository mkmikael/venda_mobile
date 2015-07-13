package blacksoftware.venda.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="item_pedido")
public class ItemPedido implements Serializable {

	private static final long serialVersionUID = 7976143108320065168L;
	@DatabaseField(generatedId=true)
	private int id;
	@DatabaseField(columnName="produto_id", foreignAutoRefresh=true, maxForeignAutoRefreshLevel=3, foreign=true)
	private Produto produto;
	@DatabaseField(columnName="pedido_id", foreignAutoRefresh=true, maxForeignAutoRefreshLevel=3, foreign=true)
	private Pedido pedido;
	@DatabaseField(columnName="quantidade")
	private int quantidade = 0;
	@DatabaseField(columnName="total", dataType=DataType.BIG_DECIMAL)
	private BigDecimal total;
	@DatabaseField(columnName="desconto", dataType=DataType.BIG_DECIMAL)
	private BigDecimal desconto;
	@DatabaseField(columnName="prazo")
	private int prazo;
	@DatabaseField(columnName="bonificacao")
	private int bonificacao = 0;
	@DatabaseField(columnName="precoNegociado", dataType=DataType.BIG_DECIMAL)
	private BigDecimal precoNegociado;

	public ItemPedido() {
	}

	public ItemPedido(int id, Produto produto, Pedido pedido, int quantidade, BigDecimal total,
			BigDecimal desconto, int prazo, int bonificacao, BigDecimal precoNegociado) {
		super();
		this.id = id;
		this.produto = produto;
		this.pedido = pedido;
		this.quantidade = quantidade;
		this.total = total;
		this.desconto = desconto;
		this.prazo = prazo;
		this.precoNegociado = precoNegociado;
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

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
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

	public int getBonificacao() {
		return bonificacao;
	}

	public void setBonificacao(int bonificacao) {
		this.bonificacao = bonificacao;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public BigDecimal getPrecoNegociado() {
		try {
			precoNegociado = getTotal().divide(new BigDecimal(quantidade + bonificacao), 2, RoundingMode.UP);
		} catch (Exception e) {
			e.printStackTrace();
			precoNegociado = BigDecimal.ZERO;
		}
		return precoNegociado;
	}

	public BigDecimal getTotal() {
		try {
			if (desconto == null) desconto = BigDecimal.TEN;
			total = produto.getPreco().multiply(BigDecimal.ONE.subtract(desconto.divide(BigDecimal.valueOf(100))))
					.multiply(new BigDecimal(quantidade)).divide(BigDecimal.ONE, 2, RoundingMode.UP);
		} catch (Exception e) {
			e.printStackTrace();
			total = BigDecimal.ZERO;
		}
		return total;
	}
}
