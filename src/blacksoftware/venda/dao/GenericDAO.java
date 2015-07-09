package blacksoftware.venda.dao;

import java.util.List;

import co.uk.rushorm.core.RushObject;
import co.uk.rushorm.core.RushSearch;

public class GenericDAO<T extends RushObject> {
	
	private Class<T> clazz;
	
	public GenericDAO(Class<T> clazz) {
		this.clazz = clazz;
	}
	
	public List<T> list() {
		return new RushSearch().find(clazz);
	}
	
	public T get(Object id) {
		return new RushSearch().findSingle(clazz);
	}
}
