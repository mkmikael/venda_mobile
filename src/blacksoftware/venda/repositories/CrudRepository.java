package blacksoftware.venda.repositories;

import java.util.List;

public interface CrudRepository<T> {
	void save(T model);
	void delete(T model);
	T get(Object id);
	List<T> list();
}
