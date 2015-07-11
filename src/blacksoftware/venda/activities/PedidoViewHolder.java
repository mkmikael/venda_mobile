package blacksoftware.venda.activities;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import blacksoftware.venda.R;
import blacksoftware.venda.models.ItemPedido;

public class PedidoViewHolder {
	
	private Activity context;
	private List<ItemPedido> itens;
	
	public PedidoViewHolder(Activity context, List<ItemPedido> itens) {
		super();
		this.context = context;
		this.itens = itens;
	}

	public View convertObjectToView() {
		LinearLayout layout = new LinearLayout(context);
		context.getLayoutInflater().inflate(R.layout.activity_pedido, layout);
		TextView content = (TextView) layout.findViewById(R.id.content);
		TextView subcontent = (TextView) layout.findViewById(R.id.subcontent);
		TextView estoque = (TextView) layout.findViewById(R.id.estoque);
		TextView preco = (TextView) layout.findViewById(R.id.preco);
		TextView total = (TextView) layout.findViewById(R.id.total);
		EditText quantidade = (EditText) layout.findViewById(R.id.quantidade);
		quantidade.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				
			}
		});
		EditText desconto = (EditText) layout.findViewById(R.id.desconto);
		return null;
	}
}
