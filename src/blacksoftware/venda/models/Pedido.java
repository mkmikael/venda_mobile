package blacksoftware.venda.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="pedido")
public class Pedido implements Serializable {

	private static final long serialVersionUID = -925355088132929355L;
	@DatabaseField(generatedId=true)
	private int id;
	@DatabaseField(columnName="codigo")
	private String codigo;
	@DatabaseField(columnName="cliente_id", foreign=true, foreignAutoRefresh=true)
	private Cliente cliente;
	@DatabaseField(columnName="data_criacao", dataType=DataType.DATE)
	private Date dataCriacao;
	@DatabaseField(columnName="data_de_faturamento", dataType=DataType.DATE)
	private Date dataDeFaturamento;
	@DatabaseField(columnName="total",dataType=DataType.BIG_DECIMAL)
	private BigDecimal total;
	@ForeignCollectionField(eager=true, foreignFieldName="pedido", maxEagerLevel=2)
	private ForeignCollection<ItemPedido> itensPedido;
	
	public Pedido() {
	}	

	public Pedido(int id, String codigo, Cliente cliente, Date dataCriacao,
			Date dataDeFaturamento, BigDecimal total) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.cliente = cliente;
		this.dataCriacao = dataCriacao;
		this.dataDeFaturamento = dataDeFaturamento;
		this.total = total;
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

	public ForeignCollection<ItemPedido> getItensPedido() {
		return itensPedido;
	}

	public BigDecimal getTotal() {
		BigDecimal sum = BigDecimal.ZERO;
		for (ItemPedido itemPedido : getItensPedido())
			sum = sum.add(itemPedido.getTotal());
		total = sum;
		return total;
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
		Pedido other = (Pedido) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
	public String toString() {
		return codigo;
	}
}
