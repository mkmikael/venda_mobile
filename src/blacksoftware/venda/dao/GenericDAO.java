package blacksoftware.venda.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import blacksoftware.venda.config.DatabaseOrm;

public class GenericDAO<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7383195183829858104L;

	private Dao<T, Integer> dao;

	public GenericDAO(DatabaseOrm db, Class<T> clazz) {
		super();
		try {
			dao = db.getDao(clazz);
		} catch (Exception e) {
			throw new RuntimeException("ERRO INSTANCE DAO");
		}
	}

	public void save(T o) {
		try {
			dao.createOrUpdate(o);
		} catch (Exception e) {
			throw new RuntimeException("ERRO IN SAVE");
		}
	}

	public void delete(T o) {
		try {
			dao.delete(o);
		} catch (Exception e) {
			throw new RuntimeException("ERRO IN DELETE");
		}
	}

	public T get(Integer id) {
		try {
			return dao.queryForId(id);
		} catch (Exception e) {
			throw new RuntimeException("ERRO IN GET");
		}
	}

	public List<T> list() {
		try {
			return dao.queryForAll();
		} catch (Exception e) {
			throw new RuntimeException("ERRO IN LIST");
		}
	}

	public List<T> listBy(Map<String, Object> params) {
		try {
			return dao.queryForFieldValues(params);
		} catch (Exception e) {
			throw new RuntimeException("ERRO IN listBy");
		}
	}
	
	public boolean exist(Integer id) {
		try {
			if (id == null || id == 0) return false;
			return dao.idExists(id);
		} catch (Exception e) {
			throw new RuntimeException("ERRO IN listBy");
		}
	}
	
	public void nextId() {
	}
	
	public T saveOrUpdateForField(T model, String field, Object value) {
		try {
			List<T> result = dao.queryForEq(field, value);
			if (result == null || result.isEmpty()) {
				dao.create(model);
			} else if (result.size() > 2) {
				throw new RuntimeException("ERRO IN saveOrUpdateForField: Field Not Unique");
			} else {
				dao.update(result.get(0));
			}
			return model;
		} catch (Exception e) {
			throw new RuntimeException("ERRO IN saveOrUpdateForField");
		}
	}
}
