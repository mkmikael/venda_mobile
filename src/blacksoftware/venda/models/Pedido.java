package blacksoftware.venda.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="pedido")
public class Pedido implements Serializable {

	public enum StatusPedido { NOVO, SINCRONIZADO, CANCELADO }
	
	private static final long serialVersionUID = -925355088132929355L;
	@DatabaseField(generatedId=true)
	private Integer id;
	@DatabaseField
	private String codigo;
	@DatabaseField(foreign=true, foreignAutoRefresh=true)
	private Cliente cliente;
	@DatabaseField(foreign=true, foreignAutoCreate=true, foreignAutoRefresh=true)
	private Prazo prazo;
	@DatabaseField(dataType=DataType.DATE)
	private Date dataCriacao = new Date();
	@DatabaseField(dataType=DataType.DATE)
	private Date dataDeFaturamento = new Date();
	@DatabaseField(dataType=DataType.BIG_DECIMAL)
	private BigDecimal total;
	@DatabaseField(dataType=DataType.ENUM_INTEGER)
	private StatusPedido statusPedido = StatusPedido.NOVO;
	@ForeignCollectionField(eager=true, foreignFieldName="pedido", maxEagerLevel=2)
	private Collection<ItemPedido> itensPedido;
	private Set<Produto> produtos;
	
	public Pedido() {
	}	

	public Pedido(Integer id, String codigo, Cliente cliente, Date dataCriacao,
			Date dataDeFaturamento, BigDecimal total) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.cliente = cliente;
		this.dataCriacao = dataCriacao;
		this.dataDeFaturamento = dataDeFaturamento;
		this.total = total;
	}

	public boolean isSincronizado() {
		return statusPedido == StatusPedido.SINCRONIZADO;
	}
	
	public Integer getId() {
		return id;
	}
	
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Prazo getPrazo() {
		return prazo;
	}

	public void setPrazo(Prazo prazo) {
		this.prazo = prazo;
	}

	public StatusPedido getStatusPedido() {
		return statusPedido;
	}

	public void setStatusPedido(StatusPedido statusPedido) {
		this.statusPedido = statusPedido;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Date getDataDeFaturamento() {
		return dataDeFaturamento;
	}

	public void setDataDeFaturamento(Date dataDeFaturamento) {
		this.dataDeFaturamento = dataDeFaturamento;
	}

	public void addToItemPedido(ItemPedido itemPedido) {
		itemPedido.setPedido(this);
		getItensPedido().add(itemPedido);
	}
	
	public Collection<ItemPedido> getItensPedido() {
		if (itensPedido == null) {
			itensPedido = new ArrayList<ItemPedido>();
		}
		return itensPedido;
	}
	
	public Set<Produto> getProdutos() {
		if (produtos == null) {
			produtos = new HashSet<Produto>();
		}
		for (ItemPedido itemPedido : itensPedido) {
			produtos.add(itemPedido.getProduto());
		}
		return produtos;
	}
	
	public ItemPedido getItemPedidoByProduto(Produto produto) {
		for (ItemPedido itemPedido : getItensPedido()) {
			if (itemPedido.getProduto().equals(produto)) {
				return itemPedido;
			}
		}
		return null;
	}
	
	public void setItensPedido(Collection<ItemPedido> itensPedido) {
		this.itensPedido = itensPedido;
	}

	public BigDecimal getTotal() {
		BigDecimal sum = BigDecimal.ZERO;
		if (getItensPedido() == null) return sum;
		for (ItemPedido itemPedido : getItensPedido())
			sum = sum.add(itemPedido.getTotal());
		total = sum;
		return total;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		Integer result = 1;
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
		Pedido other = (Pedido) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
	public String toString() {
		String dateCriacao = new SimpleDateFormat("dd/MM/yyyy").format(this.getDataCriacao());
		String dateFatu = new SimpleDateFormat("dd/MM/yyyy").format(this.getDataDeFaturamento());
		String hora = new SimpleDateFormat("HH:mm").format(this.getDataCriacao());
		String valor = NumberFormat.getCurrencyInstance().format(this.getTotal());
		return dateCriacao + " - " + dateFatu + " - " + hora + " - " + valor + " - " + statusPedido;
	}
}
