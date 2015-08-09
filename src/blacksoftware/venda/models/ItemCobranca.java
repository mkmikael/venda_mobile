package blacksoftware.venda.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

public class ItemCobranca implements Serializable {

	private static final long serialVersionUID = -402687192480167146L;
	@DatabaseField(generatedId = true)
	private Integer id;
	@DatabaseField(dataType=DataType.DATE)
	private Date dataCobranca;
	@DatabaseField(dataType=DataType.BIG_DECIMAL)
	private BigDecimal valor;
	
	public ItemCobranca() {
	}

	public ItemCobranca(Integer id, Date dataCobranca, BigDecimal valor) {
		super();
		this.id = id;
		this.dataCobranca = dataCobranca;
		this.valor = valor;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDataCobranca() {
		return dataCobranca;
	}

	public void setDataCobranca(Date dataCobranca) {
		this.dataCobranca = dataCobranca;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public String toString() {
		return new SimpleDateFormat("dd/MM/yyyy").format(dataCobranca) + " - " + NumberFormat.getCurrencyInstance().format(valor.doubleValue());
	}
	
}
