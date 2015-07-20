package blacksoftware.webvenda.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "cobranca")
public class Cobranca implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6180021715553228779L;
	@Id
	@GeneratedValue
	private Long id;
	private String codigo;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_criacao")
	private Date dataCriacao;
	@ManyToOne
	private Cliente cliente;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "cobranca")
	private List<ItemCobranca> cobrancas;

	public Cobranca() {
	}

	public Cobranca(Long id, String codigo, Date dataCriacao, Cliente cliente,
			List<ItemCobranca> cobrancas) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.dataCriacao = dataCriacao;
		this.cliente = cliente;
		this.cobrancas = cobrancas;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<ItemCobranca> getCobrancas() {
		return cobrancas;
	}

	public void setCobrancas(List<ItemCobranca> cobrancas) {
		this.cobrancas = cobrancas;
	}

	public Long getId() {
		return id;
	}

}
