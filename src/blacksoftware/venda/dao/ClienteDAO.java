package blacksoftware.venda.dao;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.stmt.QueryBuilder;

import android.util.Log;
import blacksoftware.venda.config.DatabaseOrm;
import blacksoftware.venda.models.Cliente;

public class ClienteDAO extends GenericDAO<Cliente> {

	private static final long serialVersionUID = 7929802785623258570L;

	public ClienteDAO(DatabaseOrm db) {
		super(db, Cliente.class);
	}

	public List<Cliente> findAllLikeNome(String nome) {
		try {
			QueryBuilder<Cliente,Integer> query = getDao().queryBuilder();
			List<Cliente> list = query
									.where()
									.like("nome_fantasia", "%" + nome + "%")
									.query();
			Log.i("ClienteDAO.findAllLikeNome", query.prepareStatementString());
			if (list.isEmpty()) {
				return list();
			} else {
				return list;
			}
		} catch (SQLException e) {
			Log.e("ClienteDAO.findAllLikeNome", "Content: " + nome, e);
			return null;
		}
	}
}
