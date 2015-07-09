package blacksoftware.venda.models;

import co.uk.rushorm.core.RushObject;
import co.uk.rushorm.core.annotations.RushCustomTableName;

@RushCustomTableName(name = "cliente")
public class Cliente extends RushObject {

	private String codigo;
	private String cnpj;
	private String nomeFantasia;
	private String razaoSocial;
	private String endereco;
	private String bairro;
	private String referencia;
	private String cidade;
	private String inscricaoEstadual;
	private Situacao situacao = Situacao.EM_DIA;
	private float rate;
	private double limite;
	private String telefone;
	private String responsavel;
	private String canal;
	private String ramo;

	public Cliente() {
	}

	public Cliente(String codigo, String cnpj, String nomeFantasia, String razaoSocial,
			String endereco, String bairro, String referencia, String cidade, String inscricaoEstadual, 
			Situacao situacao, float rate, double limite, String telefone, String responsavel,
			String canal, String ramo) {
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

	public double getLimite() {
		return limite;
	}

	public void setLimite(double limite) {
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
