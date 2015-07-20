package blacksoftware.webvenda.dao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

public class DAO<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8765143660533424570L;
	@Inject
	private EntityManager manager;
	private Class<T> clazz;

	public void setClass(Class<T> clazz) {
		this.clazz = clazz;
	}
	
	public void salvar(T entity) {
		if (getId(entity) == null) {
			manager.persist(entity);
		} else {
			manager.merge(entity);
		}
	}

	public void deletar(T entity) {
		manager.remove(entity);
	}

	public T get(Object id) {
		return manager.find(clazz, id);
	}

	public List<T> list() {
		return manager.createNamedQuery(clazz.getSimpleName() + ".findAll",
				clazz).getResultList();
	}

	protected List<T> findListNamedQuery(Class<T> clazz, String name, Map<String, Object> params) {
		return namedQuery(clazz, name, params).getResultList();
	}

	protected T findOneNamedQuery(Class<T> clazz, String name, Map<String, Object> params) {
		return namedQuery(clazz, name, params).getSingleResult();
	}

	private TypedQuery<T> namedQuery(Class<T> clazz, String name, Map<String, Object> params) {
		TypedQuery<T> query = manager.createNamedQuery(clazz.getSimpleName() + "." + name, clazz);
		for (Map.Entry<String, Object> entry : params.entrySet()) {
			if (entry.getValue() instanceof Date) {
				Map.Entry<Date, TemporalType> dateType = (Map.Entry<Date, TemporalType>) entry.getValue();
				query.setParameter(entry.getKey(), (Date) dateType.getKey(), dateType.getValue());
			} else {
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
		return query;
	}
	
	private static Object getId(Object o) {
		Field[] declaredFields = o.getClass().getDeclaredFields();
		for (Field declaredField : declaredFields) {
			if (declaredField.isAnnotationPresent(Id.class)) {
				if (!declaredField.isAccessible()) {
					declaredField.setAccessible(true);
				}
				try {
					return declaredField.get(o);
				} catch (Exception e) {
					return null;
				}
			}
		}
		return null;
	}
}
