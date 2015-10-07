package blacksoftware.venda.dao;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.stmt.QueryBuilder;

import android.util.Log;
import blacksoftware.venda.config.DatabaseOrm;
import blacksoftware.venda.models.Cliente;
import blacksoftware.venda.models.Pedido;

public class PedidoDAO extends GenericDAO<Pedido> {

	private static final long serialVersionUID = 2767186792200120954L;

	public PedidoDAO(DatabaseOrm db) {
		super(db, Pedido.class);
	}
	
	public PedidoDAO(DatabaseOrm db, Class<Pedido> clazz) {
		super(db, clazz);
	}

	public List<Pedido> findAllByCliente(Cliente cliente) {
		try {
			QueryBuilder<Pedido,Integer> queryBuilder = getDao().queryBuilder();
			List<Pedido> query = queryBuilder.where().eq("cliente_id", cliente).query();
			Log.i("PedidoDAO.findAllByCliente", queryBuilder.prepareStatementString());
			return query;
		} catch (SQLException e) {
			Log.e("PedidoDAO.findAllByCliente", "", e);
			return null;
		}
	}
}
