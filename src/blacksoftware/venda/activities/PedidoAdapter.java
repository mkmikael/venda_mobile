package blacksoftware.venda.activities;

import java.math.BigDecimal;

import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import blacksoftware.venda.R;
import blacksoftware.venda.models.ItemPedido;
import blacksoftware.venda.models.Produto;
import blacksoftware.venda.models.enums.Unidade;

import com.j256.ormlite.dao.ForeignCollection;

public class PedidoAdapter extends BaseAdapter {

	private Context context;
	private ForeignCollection<ItemPedido> itens;
	private LayoutInflater layoutInflate;
	private TextView total;
	
	public PedidoAdapter(Context context, ForeignCollection<ItemPedido> itens, TextView total) {
		this.context = context;
		this.itens = itens;
		this.total = total;
		this.layoutInflate = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		return itens.size();
	}

	@Override
	public ItemPedido getItem(int position) {
		int i = 0;
		for (ItemPedido itemPedido : itens) {
			if (position == i) {
				return itemPedido;
			}
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		PedidoViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = layoutInflate.inflate(R.layout.row_pedido, parent, false);
			viewHolder = new PedidoViewHolder(convertView, getItem(position));
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (PedidoViewHolder) convertView.getTag();
		}
		ItemPedido itemPedido = getItem(position);
		Produto produto = itemPedido.getProduto();
		viewHolder.content.setText(produto.getNome());
		viewHolder.subcontent.setText(produto.getEmbalagem() + " - " + produto.getCodigo());
		viewHolder.estoque.setText(produto.getEstoque().toString());
		viewHolder.preco.setText(produto.getPreco().toString());
		viewHolder.quantidade.setText(itemPedido.getQuantidade() + "");
		viewHolder.bonificacao.setText(itemPedido.getBonificacao() + "");
		viewHolder.subtotal.setText(itemPedido.getTotal().toString());
		return convertView;
	}
	
	private class PedidoViewHolder {
		TextView content;
		TextView subcontent;
		TextView estoque;
		Spinner tipo;
		Spinner prazo;
		TextView preco;
		EditText quantidade;
		EditText bonificacao;
		EditText desconto;
		TextView precoUnitario;
		TextView subtotal;
		ItemPedido itemPedido;
		
		public PedidoViewHolder(View view, ItemPedido itemPedido) {
			this.itemPedido = itemPedido;
			initTipo(view);
			initPrazo(view);
			initQuantidade(view);
			initBonificacao(view);
			initDesconto(view);
			content = (TextView) view.findViewById(R.id.content);
			subcontent = (TextView) view.findViewById(R.id.subcontent);
			estoque = (TextView) view.findViewById(R.id.estoque);
			preco = (TextView) view.findViewById(R.id.preco);
			precoUnitario = (TextView) view.findViewById(R.id.precoUnitario);
			subtotal = (TextView) view.findViewById(R.id.subtotal);
		}
		
		private void initTipo(View view) {
			tipo = (Spinner) view.findViewById(R.id.tipo);
			ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(view.getContext(),
					android.R.layout.simple_spinner_item, Unidade.array());
			tipo.setAdapter(arrayAdapter);
		}
		
		private void initPrazo(View view) {
			prazo = (Spinner) view.findViewById(R.id.prazo);
			String[] array = {"7-14", "7-14-21"};
			ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(view.getContext(),
					android.R.layout.simple_spinner_item, array);
			prazo.setAdapter(arrayAdapter);
		}
		
		private void initDesconto(View view) {
			desconto = (EditText) view.findViewById(R.id.desconto);
			desconto.setOnKeyListener(calcularSubTotalListener);
		}

		private void initQuantidade(View view) {
			quantidade = (EditText) view.findViewById(R.id.quantidade);
			quantidade.setOnKeyListener(calcularSubTotalListener);
		}
		
		private void initBonificacao(View view) {
			bonificacao = (EditText) view.findViewById(R.id.bonificacao);
			bonificacao.setOnKeyListener(calcularSubTotalListener);
		}
		
		private OnKeyListener calcularSubTotalListener = new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				int quant = 0;
				int bonif = 0;
				BigDecimal desc = null;
				if (quantidade.getText() != null && !quantidade.getText().toString().isEmpty())
					quant = Integer.valueOf(quantidade.getText().toString());
				if (bonificacao.getText() != null && !bonificacao.getText().toString().isEmpty())
					bonif = Integer.valueOf(bonificacao.getText().toString());
				if (desconto.getText() != null && !desconto.getText().toString().isEmpty())
					desc = new BigDecimal(desconto.getText().toString());
				itemPedido.setQuantidade(quant);
				itemPedido.setDesconto(desc);
				itemPedido.setBonificacao(bonif);
				precoUnitario.setText(itemPedido.getPrecoNegociado().toString());
				subtotal.setText(itemPedido.getTotal().toString());
				total.setText(itemPedido.getPedido().getTotal().toString());
				return false;
			}
		};
		
	}
}
