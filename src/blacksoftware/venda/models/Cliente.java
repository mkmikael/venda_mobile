package blacksoftware.venda.models;

import java.io.Serializable;
import java.math.BigDecimal;

import blacksoftware.venda.models.enums.Situacao;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "cliente")
public class Cliente implements Serializable {

	private static final long serialVersionUID = -4433715046586611341L;
	
	@DatabaseField(generatedId=true)
	private int id;
	@DatabaseField(columnName="codigo")
	private String codigo;
	@DatabaseField(columnName="cnpj")
	private String cnpj;
	@DatabaseField(columnName="nome_fantasia")
	private String nomeFantasia;
	@DatabaseField(columnName="razao_social")
	private String razaoSocial;
	@DatabaseField(columnName="endereco")
	private String endereco;
	@DatabaseField(columnName="bairro")
	private String bairro;
	@DatabaseField(columnName="referencia")
	private String referencia;
	@DatabaseField(columnName="cidade")
	private String cidade;
	@DatabaseField(columnName="inscricao_estadual")
	private String inscricaoEstadual;
	@DatabaseField(dataType=DataType.ENUM_INTEGER)
	private Situacao situacao = Situacao.EM_DIA;
	@DatabaseField(columnName="rate")
	private float rate;
	@DatabaseField(columnName="limite", dataType=DataType.BIG_DECIMAL)
	private BigDecimal limite;
	@DatabaseField(columnName="telefone")
	private String telefone;
	@DatabaseField(columnName="responsavel")
	private String responsavel;
	@DatabaseField(columnName="canal")
	private String canal;
	@DatabaseField(columnName="ramo")
	private String ramo;

	public Cliente() {
	}

	public Cliente(int id) {
		this.id = id;
	}
	
	public Cliente(int id, String codigo, String cnpj, String nomeFantasia, String razaoSocial,
			String endereco, String bairro, String referencia, String cidade, String inscricaoEstadual, 
			Situacao situacao, float rate, BigDecimal limite, String telefone, String responsavel,
			String canal, String ramo) {
		this.id = id;
		this.codigo = codigo;
		this.cnpj = cnpj;
		this.nomeFantasia = nomeFantasia;
		this.razaoSocial = razaoSocial;
		this.endereco = endereco;
		this.bairro = bairro;
		this.referencia = referencia;
		this.cidade = cidade;
		this.inscricaoEstadual = inscricaoEstadual;
		this.situacao = situacao;
		this.rate = rate;
		this.limite = limite;
		this.telefone = telefone;
		this.responsavel = responsavel;
		this.canal = canal;
		this.ramo = ramo;
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
	
	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getInscricaoEstadual() {
		return inscricaoEstadual;
	}

	public void setInscricaoEstadual(String inscricaoEstadual) {
		this.inscricaoEstadual = inscricaoEstadual;
	}

	public Situacao getSituacao() {
		return situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}

	public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}

	public BigDecimal getLimite() {
		return limite;
	}

	public void setLimite(BigDecimal limite) {
		this.limite = limite;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

	public String getCanal() {
		return canal;
	}

	public void setCanal(String canal) {
		this.canal = canal;
	}

	public String getRamo() {
		return ramo;
	}

	public void setRamo(String ramo) {
		this.ramo = ramo;
	}

	@Override
	public String toString() {
		return nomeFantasia;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cnpj == null) ? 0 : cnpj.hashCode());
		result = prime
				* result
				+ ((inscricaoEstadual == null) ? 0 : inscricaoEstadual
						.hashCode());
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
		Cliente other = (Cliente) obj;
		if (cnpj == null) {
			if (other.cnpj != null)
				return false;
		} else if (!cnpj.equals(other.cnpj))
			return false;
		if (inscricaoEstadual == null) {
			if (other.inscricaoEstadual != null)
				return false;
		} else if (!inscricaoEstadual.equals(other.inscricaoEstadual))
			return false;
		return true;
	}
}
