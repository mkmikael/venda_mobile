package blacksoftware.venda.models;

import co.uk.rushorm.core.RushObject;

public class Cliente extends RushObject {

	private String cnpj;
	private String nomeFantasia;
	private String razaoSocial;
	private String endereco;
	private String inscricaoEstadual;
	private Situacao situacao;
	private float rate;
	private double limite;
	private String telefone;
	private String responsavel;
	private Canal canal;
	private Ramo ramo;

	public Cliente() {
	}

	public Cliente(String cnpj, String nomeFantasia, String razaoSocial,
			String endereco, String inscricaoEstadual, Situacao situacao,
			float rate, double limite, String telefone, String responsavel,
			Canal canal, Ramo ramo) {
		this.cnpj = cnpj;
		this.nomeFantasia = nomeFantasia;
		this.razaoSocial = razaoSocial;
		this.endereco = endereco;
		this.inscricaoEstadual = inscricaoEstadual;
		this.situacao = situacao;
		this.rate = rate;
		this.limite = limite;
		this.telefone = telefone;
		this.responsavel = responsavel;
		this.canal = canal;
		this.ramo = ramo;
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

	public Canal getCanal() {
		return canal;
	}

	public void setCanal(Canal canal) {
		this.canal = canal;
	}

	public Ramo getRamo() {
		return ramo;
	}

	public void setRamo(Ramo ramo) {
		this.ramo = ramo;
	}

}
