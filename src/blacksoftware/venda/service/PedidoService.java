package blacksoftware.venda.service;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import blacksoftware.venda.config.DatabaseOrm;
import blacksoftware.venda.dao.GenericDAO;
import blacksoftware.venda.models.ItemPedido;
import blacksoftware.venda.models.Pedido;
import blacksoftware.venda.models.Produto;

public class PedidoService {

	private GenericDAO<ItemPedido> daoItemPedido;
	private GenericDAO<Pedido> daoPedido;
	
	public PedidoService(DatabaseOrm db) {
		daoItemPedido = new GenericDAO<ItemPedido>(db, ItemPedido.class);
		daoPedido = new GenericDAO<Pedido>(db, Pedido.class);
	}

	public void changeItensPedido(Pedido pedido, ItemPedido itemPedido) {
		if (itemPedido.getQuantidade() > 0) {
			if (!pedido.getItensPedido().contains(itemPedido)) {
				pedido.getItensPedido().add(itemPedido);
				Log.i("PedidoService.changeItensPedido", "Add item: " + itemPedido.toString());
			}
		} else if (pedido.getItensPedido().contains(itemPedido)) {
			pedido.getItensPedido().remove(itemPedido);
			Log.i("PedidoService.changeItensPedido", "Delete " + itemPedido.toString());
		}
	}
	
	public void saveItensPedido(Pedido pedido) {
		daoPedido.save(pedido);
		for (ItemPedido itemPedido : pedido.getItensPedido()) {
			if (itemPedido.getQuantidade() > 0) {
				if (!itemPedido.getPedido().isSincronizado()) {
					daoItemPedido.save(itemPedido);
				}
				Log.i("PedidoService.saveItensPedido", "salvando item pedido:"  + itemPedido);
			} else {
				Log.i("PedidoService.saveItensPedido", "o item pedido nao foi salvo:"  + itemPedido);
			}
		}
	}
	
	public List<ItemPedido> carregarItensPedido(Pedido pedido, List<Produto> produtoList) {
		List<ItemPedido> itemPedidoList = new ArrayList<ItemPedido>();
		if (produtoList == null) return new ArrayList<ItemPedido>();
		Log.i(getClass().getSimpleName() + ".carregarItensPedido", pedido.getCliente() + " - Itens: " + pedido.getItensPedido().size());
		if (pedido.getItensPedido().isEmpty()) {
			Log.i(getClass().getSimpleName(), "Lista vazia");
			for (Produto produto : produtoList) {
				itemPedidoList.add(new ItemPedido(pedido, produto));
			}
		} else {
			for (Produto produto : produtoList) {
				if (pedido.getProdutos().contains(produto)) {
					Log.i(getClass().getSimpleName(), "Ja carregado" + produto);
					itemPedidoList.add(pedido.getItemPedidoByProduto(produto));
				} else {
					itemPedidoList.add(new ItemPedido(pedido, produto));
				}
			}
		}
		return itemPedidoList;
	}
	
}
