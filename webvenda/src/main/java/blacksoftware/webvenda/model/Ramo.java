package blacksoftware.webvenda.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ramo")
public class Ramo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -837137987388457176L;
	@Id
	@GeneratedValue
	private Long id;
	private String codigo;
	private String nome;

	public Ramo() {
	}

	public Ramo(Long id, String codigo, String nome) {
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
