package blacksoftware.webvenda.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import blacksoftware.webvenda.model.Ramo;

@Named
@RequestScoped
public class RamoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 323239351598301868L;

	private Ramo ramo = new Ramo();
	private List<Ramo> ramos;

	@PostConstruct
	public void init() {
		if (ramo == null) {
			ramo = new Ramo();
		}
	}

	public String salvar() {
		return "/ramo/list.xhtml";
	}
	
	public String deletar() {
		return "/ramo/list.xhtml"; 
	}
	
	public Ramo getRamo() {
		return ramo;
	}

	public void setRamo(Ramo ramo) {
		this.ramo = ramo;
	}

	public List<Ramo> getRamos() {
		if (ramos == null) {
			ramos = new ArrayList<Ramo>();
		}
		return ramos;
	}

}
