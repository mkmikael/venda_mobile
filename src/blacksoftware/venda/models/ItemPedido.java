package blacksoftware.venda.models;

import co.uk.rushorm.core.RushObject;
import co.uk.rushorm.core.annotations.RushCustomTableName;

@RushCustomTableName(name = "item_pedido")
public class ItemPedido extends RushObject {

	private String codigoProduto;
	private double quantidade;
	private double total;
	private double desconto;
	private String prazo;

	public ItemPedido() {
	}

	public ItemPedido(String codigoProduto, double quantidade, double total,
			double desconto, String prazo) {
		super();
		this.codigoProduto = codigoProduto;
		this.quantidade = quantidade;
		this.total = total;
		this.desconto = desconto;
		this.prazo = prazo;
	}

	public String getCodigoProduto() {
		return codigoProduto;
	}

	public void setCodigoProduto(String codigoProduto) {
		this.codigoProduto = codigoProduto;
	}

	public double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(double quantidade) {
		this.quantidade = quantidade;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public double getDesconto() {
		return desconto;
	}

	public void setDesconto(double desconto) {
		this.desconto = desconto;
	}

	public String getPrazo() {
		return prazo;
	}

	public void setPrazo(String prazo) {
		this.prazo = prazo;
	}

}
