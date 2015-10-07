package blacksoftware.venda.activities.adapters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import blacksoftware.venda.R;
import blacksoftware.venda.models.ItemPedido;

public class PedidoAdapter extends BaseAdapter {

	private LayoutInflater layoutInflate;
	private List<ItemPedido> itensPedidoList;
	
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
			viewHolder = new PedidoViewHolder(itemPedido, convertView);
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

	public void setItemPedidoChangedListener(ItemPedidoChangedListener itemPedidoChangedListener) {
		this.itemPedidoChangedListener = itemPedidoChangedListener;
	}
	
	
}
