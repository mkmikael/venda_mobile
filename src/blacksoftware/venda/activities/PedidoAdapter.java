package blacksoftware.venda.activities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.dao.ForeignCollection;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Spinner;
import android.widget.TextView;
import blacksoftware.venda.R;
import blacksoftware.venda.config.DatabaseOrm;
import blacksoftware.venda.dao.GenericDAO;
import blacksoftware.venda.models.ItemPedido;
import blacksoftware.venda.models.Prazo;
import blacksoftware.venda.models.Produto;
import blacksoftware.venda.models.enums.TipoUnidade;

public class PedidoAdapter extends BaseAdapter implements Filterable {

	private LayoutInflater layoutInflate;
	private TextView total;
	private DatabaseOrm db;
	private List<ItemPedido> itens;
	private List<Prazo> prazos;

	public PedidoAdapter(Activity context, DatabaseOrm db, TextView total) {
		this.db = db;
		this.total = total;
		this.layoutInflate = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		return itens.size();
	}

	@Override
	public ItemPedido getItem(int position) {
		return itens.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		PedidoViewHolder viewHolder = null;
		if (convertView == null) {
			if (prazos == null) prazos = new GenericDAO<Prazo>(db, Prazo.class).list();
			convertView = layoutInflate.inflate(R.layout.row_pedido, parent, false);
			viewHolder = new PedidoViewHolder(convertView, getItem(position), prazos);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (PedidoViewHolder) convertView.getTag();
		}
		ItemPedido itemPedido = getItem(position);
		if (itemPedido != null) {
			Produto produto = itemPedido.getProduto();
			viewHolder.content.setText(produto.getNome());
			viewHolder.subcontent.setText(produto.getEmbalagem() + " - " + produto.getCodigo());
			viewHolder.estoque.setText(produto.getEstoque().toString());
			viewHolder.preco.setText(produto.getPreco().toString());
			viewHolder.quantidade.setText(itemPedido.getQuantidade() + "");
			viewHolder.bonificacao.setText(itemPedido.getBonificacao() + "");
			viewHolder.subtotal.setText(itemPedido.getTotal().toString());
		}
		return convertView;
	}
	
	public void setItens(ForeignCollection<ItemPedido> itens) {
		this.itens = new ArrayList<ItemPedido>(itens);
	}

	@Override
	public Filter getFilter() {
		return new Filter() {
			
			@Override
			protected void publishResults(CharSequence arg0, FilterResults arg1) {
				
			}
			
			@Override
			protected FilterResults performFiltering(CharSequence arg0) {
				return null;
			}
		};
	}
	
	private class PedidoViewHolder {
		View view;
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
		
		public PedidoViewHolder(View view, ItemPedido itemPedido, List<Prazo> prazos) {
			this.view = view;
			this.itemPedido = itemPedido;
			initTipo();
			initPrazo(prazos);
			initQuantidade();
			initBonificacao();
			initDesconto();
			content = (TextView) view.findViewById(R.id.content);
			subcontent = (TextView) view.findViewById(R.id.subcontent);
			estoque = (TextView) view.findViewById(R.id.estoque);
			preco = (TextView) view.findViewById(R.id.preco);
			precoUnitario = (TextView) view.findViewById(R.id.precoUnitario);
			subtotal = (TextView) view.findViewById(R.id.subtotal);
		}

		private void initTipo() {
			tipo = (Spinner) view.findViewById(R.id.tipo);
			ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(view.getContext(),
					android.R.layout.simple_spinner_item, TipoUnidade.array());
			tipo.setAdapter(arrayAdapter);
		}

		private void initPrazo(List<Prazo> prazos) {
			prazo = (Spinner) view.findViewById(R.id.prazo);
			ArrayAdapter<Prazo> arrayAdapter = new ArrayAdapter<Prazo>(view.getContext(),
					android.R.layout.simple_spinner_item, prazos);
			prazo.setAdapter(arrayAdapter);
		}

		private void initDesconto() {
			desconto = (EditText) view.findViewById(R.id.desconto);
			desconto.setOnKeyListener(calcularSubTotalListener);
		}

		private void initQuantidade() {
			quantidade = (EditText) view.findViewById(R.id.quantidade);
			quantidade.setOnKeyListener(calcularSubTotalListener);
		}

		private void initBonificacao() {
			bonificacao = (EditText) view.findViewById(R.id.bonificacao);
			bonificacao.setOnKeyListener(calcularSubTotalListener);
		}

		private OnKeyListener calcularSubTotalListener = new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				int quant = 0;
				int bonif = 0;
				BigDecimal desc = BigDecimal.ZERO;
				if (itemPedido != null) {
					if (quantidade.getText() != null && quantidade.getText().length() > 0)
						quant = Integer.valueOf(quantidade.getText().toString());
					if (bonificacao.getText() != null && bonificacao.getText().length() > 0)
						bonif = Integer.valueOf(bonificacao.getText().toString());
					if (desconto.getText() != null && desconto.getText().length() > 0)
						desc = new BigDecimal(desconto.getText().toString());
					itemPedido.setQuantidade(quant);
					itemPedido.setDesconto(desc);
					itemPedido.setBonificacao(bonif);
					precoUnitario.setText(itemPedido.getPrecoNegociado().toString());
					subtotal.setText(itemPedido.getTotal().toString());
					total.setText(itemPedido.getPedido().getTotal().toString());
				}
				return false;
			}
		};

	}
}
