package blacksoftware.venda.config;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import blacksoftware.venda.models.Cliente;
import blacksoftware.venda.models.ItemPedido;
import blacksoftware.venda.models.Pedido;
import blacksoftware.venda.models.Produto;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseOrm extends OrmLiteSqliteOpenHelper {

	private static final String DATABASE = "venda.db";
	private static final int VERSION = 1;
	
	private Dao<Cliente, Integer> clienteDao;
	private Dao<Produto, Integer> produtoDao;
	private Dao<Pedido, Integer> pedidoDao;
	private Dao<ItemPedido, Integer> itemPedidoDao;
	
	public DatabaseOrm(Context context) {
		super(context, DATABASE, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			TableUtils.createTable(connectionSource, Cliente.class);
			TableUtils.createTable(connectionSource, Produto.class);
			TableUtils.createTable(connectionSource, Pedido.class);
			TableUtils.createTable(connectionSource, ItemPedido.class);
		} catch (SQLException e) {
			Log.e(this.getClass().getName(), "Unable to create datbases", e);
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, ConnectionSource arg1, int arg2, int arg3) {
	}
	
	public Dao<Cliente,Integer> getClienteDao() throws SQLException {
		if (clienteDao == null) {
			clienteDao = getDao(Cliente.class);
		}
		return clienteDao;
	}
	
	public <T>Dao<T,Integer> getGenericDao(Class<T> clazz) throws SQLException {
		return getDao(clazz);
	}
	
	public Dao<Produto,Integer> getProdutoDao() throws SQLException {
		if (produtoDao == null) {
			produtoDao = getDao(Produto.class);
		}
		return produtoDao;
	}
	
	public Dao<Pedido,Integer> getPedidoDao() throws SQLException {
		if (pedidoDao == null) {
			pedidoDao = getDao(Pedido.class);
		}
		return pedidoDao;
	}
	
	public Dao<ItemPedido,Integer> getItemPedidoDao() throws SQLException {
		if (itemPedidoDao == null) {
			itemPedidoDao = getDao(ItemPedido.class);
		}
		return itemPedidoDao;
	}
}
