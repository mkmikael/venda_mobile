package blacksoftware.venda.config;

import java.sql.SQLException;

import com.j256.ormlite.dao.Dao;

import android.app.Application;
import blacksoftware.venda.models.Cliente;
import blacksoftware.venda.models.Pedido;
import blacksoftware.venda.repositories.ClienteRespository;
import blacksoftware.venda.repositories.CrudRepository;

public class BootStrap extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		/*
		DatabaseOrm orm = new DatabaseOrm(this);
		try {
			Dao<Cliente,Integer> clienteDao = orm.getClienteDao();
			Dao<Pedido,Integer> pedidoDao = orm.getPedidoDao();
			Cliente cliente = Fixtures.createCliente();
			Pedido pedido = Fixtures.createPedido(cliente);
			System.out.println("ID Antes " + cliente.getId());
			clienteDao.create(cliente);
			System.out.println("ID Depois " + cliente.getId());
			pedidoDao.create(pedido);
			System.out.println(clienteDao.queryForAll());
			for (Pedido p : pedidoDao.queryForAll()) {
				System.out.println(p.getCodigo() + " - " + p.getCliente());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			orm.close();
		}
		 */
	}
	
	@Override
	public void onTerminate() {
		super.onTerminate();
	}

}
