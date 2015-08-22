package blacksoftware.venda.activities.adapters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import blacksoftware.venda.R;
import blacksoftware.venda.config.DatabaseOrm;
import blacksoftware.venda.dao.GenericDAO;
import blacksoftware.venda.models.Fornecedor;
import blacksoftware.venda.models.Grupo;
import blacksoftware.venda.models.ItemPedido;
import blacksoftware.venda.models.Prazo;
import blacksoftware.venda.models.Produto;
import blacksoftware.venda.util.Util;

public class PedidoAdapter extends BaseAdapter {

	private LayoutInflater layoutInflate;
	private TextView total;
	private DatabaseOrm db;
	private List<ItemPedido> itensPedidoList;
	private List<ItemPedido> itensPedidoListCopy;
	private List<Prazo> prazos;

	public PedidoAdapter(Activity context, DatabaseOrm db, TextView total) {
		this.db = db;
		this.total = total;
		this.layoutInflate = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		return itensPedidoList.size();
	}

	@Override
	public ItemPedido getItem(int position) {
		return itensPedidoList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		PedidoViewHolder viewHolder = null;
		ItemPedido itemPedido = getItem(position);
		System.out.println("é nulo: " + itemPedido == null);
		if (convertView == null) {
			if (prazos == null) prazos = new GenericDAO<Prazo>(db, Prazo.class).list();
			convertView = layoutInflate.inflate(R.layout.row_pedido, parent, false);
			viewHolder = new PedidoViewHolder(itemPedido, convertView, total, prazos);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (PedidoViewHolder) convertView.getTag();
		}
		if (itemPedido != null) {
			viewHolder.setItemPedido(itemPedido);
			Produto produto = itemPedido.getProduto();
			// TODO - Repassar os codigos abaixo para view holder
			viewHolder.getContent().setText(produto.getNome());
			viewHolder.getDesconto().setText(itemPedido.getDesconto().toString());
			viewHolder.getEstoque().setText(produto.toStringUnidades());
			viewHolder.getQuantidade().setText(itemPedido.getQuantidade() + "");
			viewHolder.getBonificacao().setText(itemPedido.getBonificacao() + "");
			viewHolder.getPreco().setText(Util.roundToString(itemPedido.getUnidade().getValor()));
			viewHolder.getSubtotal().setText(itemPedido.getTotal().toString());
		}
		return convertView;
	}

	public void setItens(List<ItemPedido> itensPedido) {
		this.itensPedidoList = new ArrayList<ItemPedido>(itensPedido);
		this.itensPedidoListCopy = new ArrayList<ItemPedido>(itensPedido);
	}

	public void filterItensVendidos(boolean vendido) {
		if (vendido) {
			Collections.sort(itensPedidoList, new Comparator<ItemPedido>() {
				@Override
				public int compare(ItemPedido item1, ItemPedido item2) {
					if (item1.getQuantidade() < item2.getQuantidade()) {
						return 1;
					} else if (item1.getQuantidade() > item2.getQuantidade()) {
						return -1;
					} else {
						return 0;
					}
				}
			});
		} else {
			Collections.sort(itensPedidoList, new Comparator<ItemPedido>() {
				@Override
				public int compare(ItemPedido item1, ItemPedido item2) {
					return item2.getProduto().getNome().compareToIgnoreCase(item1.getProduto().getNome());
				}
			});
		}
		System.out.println(itensPedidoList);
		notifyDataSetChanged();
	}
	
	public void filter(Fornecedor fornecedor, Grupo grupo) {
		itensPedidoList.clear();
		boolean hasFornecedor = !fornecedor.getNome().equals("Todos");
		boolean hasGrupo = !grupo.getNome().equals("Todos");
		for (ItemPedido itemPedido : itensPedidoListCopy) {
			if (itemPedido.getProduto().getFornecedor().equals(fornecedor) && itemPedido.getProduto().getGrupo().equals(grupo) 
					&& hasFornecedor && hasGrupo) {
					itensPedidoList.add(itemPedido);
			} else if (!hasFornecedor && !hasGrupo) {
				itensPedidoList.addAll(itensPedidoListCopy);
				break;
			} else if (itemPedido.getProduto().getFornecedor().equals(fornecedor) && hasFornecedor && !hasGrupo) {
				itensPedidoList.add(itemPedido);
			} else if (itemPedido.getProduto().getGrupo().equals(grupo) && !hasFornecedor && hasGrupo) {
				itensPedidoList.add(itemPedido);
			}
		}
		notifyDataSetChanged();
	}
}
