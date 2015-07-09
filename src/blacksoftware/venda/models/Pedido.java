package blacksoftware.venda.models;

import java.util.Date;

import co.uk.rushorm.core.RushObject;

public class Pedido extends RushObject {

	private String codigo;
	private String codigoCliente;
	private Date dataCriacao;
	private Date dataDeFaturamento;
	private double total;
	
	public Pedido() {
	}	

	public Pedido(String codigo, String codigoCliente, Date dataCriacao,
			Date dataDeFaturamento, double total) {
		super();
		this.codigo = codigo;
		this.codigoCliente = codigoCliente;
		this.dataCriacao = dataCriacao;
		this.dataDeFaturamento = dataDeFaturamento;
		this.total = total;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getCodigoCliente() {
		return codigoCliente;
	}

	public void setCodigoCliente(String codigoCliente) {
		this.codigoCliente = codigoCliente;
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

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
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
