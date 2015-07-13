package blacksoftware.venda.config;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.j256.ormlite.dao.Dao;

import android.content.Context;
import blacksoftware.venda.models.Cliente;
import blacksoftware.venda.models.ItemPedido;
import blacksoftware.venda.models.Pedido;
import blacksoftware.venda.models.Produto;
import blacksoftware.venda.models.enums.Situacao;

public class Fixtures {
	
	public static Context context;
	
	public static Cliente createCliente() {
		DatabaseOrm databaseOrm = null;
		try {
			databaseOrm = new DatabaseOrm(context);
			Dao<Cliente,Integer> genericDao = databaseOrm.getGenericDao(Cliente.class);
			Cliente novoCliente = new Cliente(1, "00000000001", "123123123", "MAGNUS CLUB SHOW", "razionado", "Rua Laurival Cunha 382", "Centro", "Prox a antena da telepara", "Barcarena", "1231233", Situacao.EM_DIA, 5f, new BigDecimal(1000), "98888-4444", "Mikael Lima", "VAREJO", "ATACADO");
			List<Cliente> lista = genericDao.queryForEq("codigo", novoCliente.getCodigo());
			genericDao.createIfNotExists(novoCliente);
			return novoCliente;
		} catch (Exception e) {
			return null;
		}finally {
			if (databaseOrm != null) databaseOrm.close();
		}
	}
	
	public static Pedido createPedido(Cliente cliente) {
		DatabaseOrm databaseOrm = null;
		try {
			databaseOrm = new DatabaseOrm(context);
			Dao<Pedido,Integer> genericDao = databaseOrm.getGenericDao(Pedido.class);
			Pedido pedido = new Pedido(1, "000001", cliente, new Date(), new Date(), BigDecimal.TEN);
			genericDao.createIfNotExists(pedido);
			return pedido;
		} catch (Exception e) {
			return null;
		} finally {
			if (databaseOrm != null) databaseOrm.close();
		}
	}
	
	public static ItemPedido createItemPedido(Produto produto, Pedido pedido) {
		DatabaseOrm databaseOrm = null;
		try {
			databaseOrm = new DatabaseOrm(context);
			Dao<ItemPedido,Integer> genericDao = databaseOrm.getGenericDao(ItemPedido.class);
			ItemPedido itemPedido = new ItemPedido(1, produto, pedido, 10, BigDecimal.TEN, BigDecimal.ZERO, 3, 0, BigDecimal.ZERO);
			genericDao.createIfNotExists(itemPedido);
			return itemPedido;
		} catch (Exception e) {
			return null;
		} finally {
			if (databaseOrm != null) databaseOrm.close();
		}
	}
	
	public static Produto createProduto() {
		DatabaseOrm databaseOrm = null;
		try {
			databaseOrm = new DatabaseOrm(context);
			Dao<Produto,Integer> genericDao = databaseOrm.getGenericDao(Produto.class);
			Produto produto = new Produto(1, "000000001", "CUP NOODES CAMARAO", "SADIA", "EBD", BigDecimal.valueOf(1000), BigDecimal.valueOf(5.62), BigDecimal.valueOf(5), "Cx./64x1x24.G");
			List<Produto> lista = genericDao.queryForEq("codigo", produto.getCodigo());
			genericDao.createIfNotExists(produto);
			return produto;
		} catch (Exception e) {
			return null;
		} finally {
			if (databaseOrm != null) databaseOrm.close();
		}
	}
}
