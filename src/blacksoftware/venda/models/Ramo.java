package blacksoftware.venda.models;

import co.uk.rushorm.core.RushObject;

public class Ramo extends RushObject {

	private String nome;

	public Ramo() {
	}

	public Ramo(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		return nome;
	}

	
}
