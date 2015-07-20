package blacksoftware.venda.config;

import blacksoftware.venda.models.Cliente;
import blacksoftware.venda.models.Pedido;
import blacksoftware.venda.models.Produto;
import android.app.Application;

public class BootStrap extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		Fixtures.context = this;
		Produto produto = Fixtures.createProduto();
		Cliente cliente = Fixtures.createCliente();
		Pedido pedido = Fixtures.createPedido(cliente);
		Fixtures.createItemPedido(produto, pedido);
	}
	
	@Override
	public void onTerminate() {
		super.onTerminate();
	}

}
