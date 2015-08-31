package blacksoftware.venda.activities.adapters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import blacksoftware.venda.R;
import blacksoftware.venda.models.ItemPedido;
import blacksoftware.venda.models.Prazo;

public class PedidoAdapter extends BaseAdapter {

	private LayoutInflater layoutInflate;
	private List<ItemPedido> itensPedidoList;
	private List<Prazo> prazos = new ArrayList<Prazo>();
	
	private ItemPedidoChangedListener itemPedidoChangedListener = new ItemPedidoChangedListener() {
		public void itemPedidoChanged(ItemPedido itemPedido) { }
	};

	public PedidoAdapter(Activity context) {
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
		if (convertView == null) {
			convertView = layoutInflate.inflate(R.layout.row_pedido, parent, false);
			viewHolder = new PedidoViewHolder(itemPedido, convertView, prazos);
			viewHolder.setItemPedidoChangedListener(itemPedidoChangedListener);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (PedidoViewHolder) convertView.getTag();
		}
		viewHolder.setItemPedido(itemPedido);
		return convertView;
	}

	public void setItens(Collection<ItemPedido> itensPedido) {
		this.itensPedidoList = new ArrayList<ItemPedido>(itensPedido);
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
		notifyDataSetChanged();
	}

	public void setItemPedidoChangedListener(ItemPedidoChangedListener itemPedidoChangedListener) {
		this.itemPedidoChangedListener = itemPedidoChangedListener;
	}
	
	
}
