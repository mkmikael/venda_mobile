package blacksoftware.venda.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="produto")
public class Produto implements Serializable {

	private static final long serialVersionUID = -6654002649371591056L;
	@DatabaseField(id=true)
	private Integer id;
	@DatabaseField
	private String codigo;
	@DatabaseField
	private String nome;
	@DatabaseField
	private String subDescricao;
	@DatabaseField
	private String fornecedor;
	@DatabaseField
	private String grupo;
	@ForeignCollectionField(eager=true, foreignFieldName="produto", maxEagerLevel=2)
	private Collection<Unidade> unidades;

	private transient boolean vendido = false;

	public Produto() {
	}

	public Produto(Integer id, String codigo, String nome, String fornecedor, String grupo) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.nome = nome;
		this.fornecedor = fornecedor;
		this.grupo = grupo;
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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSubDescricao() {
		return subDescricao;
	}

	public void setSubDescricao(String subDescricao) {
		this.subDescricao = subDescricao;
	}

	public String getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(String fornecedor) {
		this.fornecedor = fornecedor;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
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

	public Unidade getUnidade(String tipoUnidade) {
		for (Unidade unidade : getUnidades()) {
			if (unidade.getTipo() == tipoUnidade) {
				return unidade;
			}
		}
		return null;
	}
	
	public Unidade getUnidadeDefault() {
		for (Unidade unidade : getUnidades()) {
			return unidade;
		}
		return null;
	}
	
	@Override
	public int hashCode() {
		final Integer prime = 31;
		Integer result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}
	
	public String toStringUnidades() {
		if (unidades == null || unidades.size() < 1) { return ""; }
		StringBuilder builder = new StringBuilder();
		List<Unidade> unidadesList = new ArrayList<Unidade>(unidades);
		builder.append(unidadesList.get(0).getQuantidade());
		for (int i = 1; i < unidadesList.size(); i++) {
			builder.append(" ; ")
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
