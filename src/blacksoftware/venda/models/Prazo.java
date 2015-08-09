package blacksoftware.venda.models;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "prazo")
public class Prazo implements Serializable {

	private static final long serialVersionUID = -1459437967574817263L;
	@DatabaseField(generatedId = true)
	private Integer id;
	@DatabaseField
	private int parcelas;
	@DatabaseField
	private String periodicidade;

	public Prazo() {
	}

	public Prazo(Integer id, int parcelas, String periodicidade) {
		super();
		this.id = id;
		this.parcelas = parcelas;
		this.periodicidade = periodicidade;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getParcelas() {
		return parcelas;
	}

	public void setParcelas(int parcelas) {
		this.parcelas = parcelas;
	}

	public String getPeriodicidade() {
		return periodicidade;
	}

	public void setPeriodicidade(String periodicidade) {
		this.periodicidade = periodicidade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + parcelas;
		result = prime * result + ((periodicidade == null) ? 0 : periodicidade.hashCode());
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
		Prazo other = (Prazo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (parcelas != other.parcelas)
			return false;
		if (periodicidade == null) {
			if (other.periodicidade != null)
				return false;
		} else if (!periodicidade.equals(other.periodicidade))
			return false;
		return true;
	}

	public String toString() {
		return periodicidade;
	}
}
