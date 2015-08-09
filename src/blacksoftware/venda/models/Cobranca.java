package blacksoftware.venda.models;

import java.io.Serializable;
import java.util.Date;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "cobranca")
public class Cobranca implements Serializable {

	private static final long serialVersionUID = 3172458060983406670L;
	@DatabaseField(generatedId = true)
	private Integer id;
	@DatabaseField(dataType=DataType.DATE)
	private Date dataCriacao;
	@DatabaseField
	private String codigo;
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true)
	private Cliente cliente;

	public Cobranca() {
	}

	public Cobranca(Integer id, String codigo, Cliente cliente) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.cliente = cliente;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String toString() {
		return codigo + " - " + cliente;
	}
}
