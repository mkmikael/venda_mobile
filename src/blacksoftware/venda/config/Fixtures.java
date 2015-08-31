package blacksoftware.venda.config;

import java.math.BigDecimal;
import java.util.Date;

import com.j256.ormlite.dao.Dao;

import android.content.Context;
import blacksoftware.venda.models.Cliente;
import blacksoftware.venda.models.ItemPedido;
import blacksoftware.venda.models.Pedido;
import blacksoftware.venda.models.Prazo;
import blacksoftware.venda.models.Produto;
import blacksoftware.venda.models.Unidade;
import blacksoftware.venda.models.enums.Situacao;

public class Fixtures {
	
	public static Context context;
	
	public static void init() {
		createCliente();
	}
	
	public static Prazo createPrazo(Prazo prazo) {
		DatabaseOrm databaseOrm = null;
		try {
			databaseOrm = new DatabaseOrm(context);
			Dao<Prazo,Integer> genericDao = databaseOrm.getDao(Prazo.class);
			return genericDao.createIfNotExists(prazo);
		} catch (Exception e) {
			return null;
		}finally {
			if (databaseOrm != null) databaseOrm.close();
		}
	}
	
	public static Cliente createCliente() {
		DatabaseOrm databaseOrm = null;
		try {
			databaseOrm = new DatabaseOrm(context);
			Dao<Cliente,Integer> genericDao = databaseOrm.getDao(Cliente.class);
			Cliente novoCliente = new Cliente(1, "00000000001", "123123123", "MAGNUS CLUB SHOW", "razionado", "Rua Laurival Cunha 382", "Centro", "Prox a antena da telepara", "Barcarena", "1231233", Situacao.EM_DIA, 5f, new BigDecimal(1000), "98888-4444", "Mikael Lima", "VAREJO", "ATACADO");
			return genericDao.createIfNotExists(novoCliente);
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
			Dao<Pedido,Integer> genericDao = databaseOrm.getDao(Pedido.class);
			Pedido pedido = new Pedido(1, "000001", cliente, new Date(), new Date(), BigDecimal.TEN);
			return genericDao.createIfNotExists(pedido);
		} catch (Exception e) {
			return null;
		} finally {
			if (databaseOrm != null) databaseOrm.close();
		}
	}
	
	public static ItemPedido createItemPedido(Integer id, Produto produto, Pedido pedido, Prazo prazo) {
		DatabaseOrm databaseOrm = null;
		try {
			databaseOrm = new DatabaseOrm(context);
			Dao<ItemPedido,Integer> genericDao = databaseOrm.getDao(ItemPedido.class);
			ItemPedido itemPedido = new ItemPedido(id, produto, pedido, prazo, 10, BigDecimal.TEN, BigDecimal.ZERO, 0, BigDecimal.ZERO);
			return genericDao.createIfNotExists(itemPedido);
		} catch (Exception e) {
			return null;
		} finally {
			if (databaseOrm != null) databaseOrm.close();
		}
	}
	
	public static Unidade createUnidade(Unidade unidade) {
		DatabaseOrm databaseOrm = null;
		try {
			databaseOrm = new DatabaseOrm(context);
			Dao<Unidade,Integer> genericDao = databaseOrm.getDao(Unidade.class);
			return genericDao.createIfNotExists(unidade);
		} catch (Exception e) {
			return null;
		} finally {
			if (databaseOrm != null) databaseOrm.close();
		}
	}
	
	public static Produto createProduto(Produto produto) {
		DatabaseOrm databaseOrm = null;
		try {
			databaseOrm = new DatabaseOrm(context);
			Dao<Produto,Integer> genericDao = databaseOrm.getDao(Produto.class);
			return genericDao.createIfNotExists(produto);
		} catch (Exception e) {
			return null;
		} finally {
			if (databaseOrm != null) databaseOrm.close();
		}
	}
}
