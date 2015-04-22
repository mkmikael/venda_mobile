package blacksoftware.venda.models;

import co.uk.rushorm.core.RushObject;

public class Canal extends RushObject {

	private String nome;

	public Canal() {
	}

	public Canal(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
