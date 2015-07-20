package blacksoftware.webvenda.model;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import blacksoftware.webvenda.dao.DAO;

@Entity
@Table(name = "canal")
@NamedQueries({
	@NamedQuery(name="Canal.findAll", query="select c from Canal c")
})
public class Canal implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3223783312979051846L;
	@Id
	@GeneratedValue
	private Long id;
	private String codigo;
	private String nome;

	public Canal() {
	}

	public Canal(Long id, String codigo, String nome) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.nome = nome;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
