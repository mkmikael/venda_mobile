package blacksoftware.venda.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import blacksoftware.venda.models.enums.TipoUnidade;

@DatabaseTable(tableName="produto")
public class Produto implements Serializable {

	private static final long serialVersionUID = -6654002649371591056L;
	@DatabaseField(generatedId=true)
	private int id;
	@DatabaseField
	private String codigo;
	@DatabaseField
	private String nome;
	@DatabaseField(foreign=true, foreignAutoCreate=true, foreignAutoRefresh=true)
	private Fornecedor fornecedor;
	@DatabaseField(foreign=true, foreignAutoCreate=true, foreignAutoRefresh=true)
	private Grupo grupo;
	@ForeignCollectionField(eager=true, foreignFieldName="produto", maxEagerLevel=2)
	private Collection<Unidade> unidades;

	private transient boolean vendido = false;

	public Produto() {
	}

	public Produto(int id, String codigo, String nome, Fornecedor fornecedor, Grupo grupo) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.nome = nome;
		this.fornecedor = fornecedor;
		this.grupo = grupo;
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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public boolean isVendido() {
		return vendido;
	}

	public void setVendido(boolean vendido) {
		this.vendido = vendido;
	}

	public Collection<Unidade> getUnidades() {
		return unidades;
	}

	public void setUnidades(Collection<Unidade> unidades) {
		this.unidades = unidades;
	}

	public Unidade getUnidade(TipoUnidade tipoUnidade) {
		for (Unidade unidade : getUnidades()) {
			if (unidade.getTipo() == tipoUnidade) {
				return unidade;
			}
		}
		return null;
	}
	
	public Unidade getUnidadeDefault() {
		for (Unidade unidade : getUnidades()) {
			if (unidade.getTipo() == TipoUnidade.CXA) {
				return unidade;
			}
		}
		return null;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}
	
	public String toStringUnidades() {
		StringBuilder builder = new StringBuilder();
		if (unidades == null && unidades.size() < 1) return null;
		List<Unidade> unidadesList = new ArrayList<Unidade>(unidades);
		builder.append(unidadesList.get(0).getQuantidade());
		for (int i = 1; i < unidadesList.size(); i++) {
			builder.append(" | ")
			.append(unidadesList.get(i).getQuantidade());
		}
		return builder.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	public String toString() {
		return nome;
	}
}
