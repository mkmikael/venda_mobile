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
	@DatabaseField(foreignAutoRefresh=true, maxForeignAutoRefreshLevel=3, foreign=true)
	private Produto produto;
	@DatabaseField(foreignAutoRefresh=true, maxForeignAutoRefreshLevel=3, foreign=true)
	private Pedido pedido;
	@DatabaseField
	private int quantidade = 0;
	@DatabaseField(dataType=DataType.BIG_DECIMAL)
	private BigDecimal total;
	@DatabaseField(dataType=DataType.BIG_DECIMAL)
	private BigDecimal desconto = BigDecimal.ZERO;
	@DatabaseField(foreign=true, foreignAutoCreate=true, foreignAutoRefresh=true)
	private Prazo prazo;
	@DatabaseField
	private int bonificacao = 0;
	@DatabaseField(dataType=DataType.BIG_DECIMAL)
	private BigDecimal precoNegociado = BigDecimal.ZERO;
	@DatabaseField(foreignAutoRefresh=true, maxForeignAutoRefreshLevel=3, foreign=true)
	private Unidade unidade;

	public ItemPedido() {
		unidade = produto.getUnidadeDefault();
	}

	public ItemPedido(Produto produto) {
		super();
		this.produto = produto;
		unidade = produto.getUnidadeDefault();
	}

	public ItemPedido(Pedido pedido, Produto produto) {
		this.pedido = pedido;
		this.produto = produto;
		unidade = produto.getUnidadeDefault();
	}
	
	public ItemPedido(int id, Produto produto, Pedido pedido, Prazo prazo, int quantidade, BigDecimal total,
			BigDecimal desconto, int bonificacao, BigDecimal precoNegociado) {
		super();
		this.id = id;
		this.produto = produto;
		this.pedido = pedido;
		this.quantidade = quantidade;
		this.total = total;
		this.desconto = desconto;
		this.prazo = prazo;
		this.precoNegociado = precoNegociado;
		unidade = produto.getUnidadeDefault();
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
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

	public Prazo getPrazo() {
		return prazo;
	}

	public void setPrazo(Prazo prazo) {
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

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	public BigDecimal getPrecoNegociado() {
		try {
			if (quantidade + bonificacao == 0) return BigDecimal.ZERO;
			precoNegociado = getTotal().divide(new BigDecimal(quantidade + bonificacao), 2, RoundingMode.UP);
		} catch (Exception e) {
			e.printStackTrace();
			precoNegociado = BigDecimal.ZERO;
		}
		return precoNegociado;
	}

	public BigDecimal getTotal() {
		try {
			if (unidade == null) { return BigDecimal.ZERO; }
			if (desconto == null) desconto = BigDecimal.ZERO;
			total = unidade.getValor().multiply(BigDecimal.ONE.subtract(desconto.divide(BigDecimal.valueOf(100))))
					.multiply(new BigDecimal(quantidade)).divide(BigDecimal.ONE, 2, RoundingMode.UP);
		} catch (Exception e) {
			e.printStackTrace();
			total = BigDecimal.ZERO;
		}
		return total;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pedido == null) ? 0 : pedido.hashCode());
		result = prime * result + ((produto == null) ? 0 : produto.hashCode());
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
		ItemPedido other = (ItemPedido) obj;
		if (pedido == null) {
			if (other.pedido != null)
				return false;
		} else if (!pedido.equals(other.pedido))
			return false;
		if (produto == null) {
			if (other.produto != null)
				return false;
		} else if (!produto.equals(other.produto))
			return false;
		return true;
	}

	public String toString() {
		return produto.getCodigo() + " - " + quantidade + " - [" + prazo + "] - " + bonificacao;
	}
}
