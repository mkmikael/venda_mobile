package blacksoftware.venda.activities;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import blacksoftware.venda.R;
import blacksoftware.venda.models.ItemPedido;

public class PedidoAdapter extends BaseAdapter {

	private Activity activity;
	private List<ItemPedido> itens;
	private List<View> components;
	
	public PedidoAdapter(Activity activity, List<ItemPedido> itens) {
		this.activity = activity;
		this.itens = itens;
		activity.getLayoutInflater().inflate(R.layout.activity_pedido, null);
	}
	
	@Override
	public int getCount() {
		return itens.size();
	}

	@Override
	public Object getItem(int position) {
		return itens.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View view, ViewGroup viewGroup) {
		Button button = new Button(activity);
		return button;
	}
}
