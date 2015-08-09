package blacksoftware.venda.activities.adapters;

import java.math.BigDecimal;
import java.util.List;

import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import blacksoftware.venda.R;
import blacksoftware.venda.models.ItemPedido;
import blacksoftware.venda.models.Prazo;
import blacksoftware.venda.models.Unidade;
import blacksoftware.venda.models.enums.TipoUnidade;
import blacksoftware.venda.util.Util;

public class PedidoViewHolder {
	private View view;
	private TextView content;
	private TextView subcontent;
	private TextView estoque;
	private Spinner tipo;
	private Spinner prazo;
	private TextView preco;
	private EditText quantidade;
	private EditText bonificacao;
	private EditText desconto;
	private TextView precoUnitario;
	private TextView subtotal;
	private ItemPedido itemPedido;
	private TextView total;
	private List<Prazo> prazos;
	
	public PedidoViewHolder(ItemPedido itemPedido, View view,TextView total, List<Prazo> prazos) {
		this.itemPedido = itemPedido;
		this.view = view;
		this.total = total;
		this.prazos = prazos;
		content = (TextView) view.findViewById(R.id.content);
		subcontent = (TextView) view.findViewById(R.id.subcontent);
		estoque = (TextView) view.findViewById(R.id.estoque);
		preco = (TextView) view.findViewById(R.id.preco);
		precoUnitario = (TextView) view.findViewById(R.id.precoUnitario);
		subtotal = (TextView) view.findViewById(R.id.subtotal);
		getTipo();
		getPrazo();
		getDesconto();
		getQuantidade();
		getBonificacao();
	}

	public void setItemPedido(ItemPedido itemPedido) {
		this.itemPedido = itemPedido;
	}

	public Spinner getTipo() {
		if (tipo == null) {
			tipo = (Spinner) view.findViewById(R.id.tipo);
			ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(view.getContext(),
					android.R.layout.simple_spinner_item, TipoUnidade.array());
			tipo.setAdapter(arrayAdapter);
			tipo.setSelection(itemPedido.getUnidade().getTipo().ordinal());
			tipo.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> adapter, View view, int position, long id) {
					TipoUnidade tipoUnidade = TipoUnidade.values()[position];
					Unidade unidade = itemPedido.getProduto().getUnidade(tipoUnidade);
					itemPedido.setUnidade(unidade);
					preco.setText(Util.roundToString(unidade.getValor()));
					calcularSubTotalListener.onKey(null, 0, null);
				}

				@Override
				public void onNothingSelected(AdapterView<?> adapter) {
				}
			});
		}
		return tipo;
	}

	public Spinner getPrazo() {
		if (prazo == null) {
			prazo = (Spinner) view.findViewById(R.id.prazo);
			ArrayAdapter<Prazo> arrayAdapter = new ArrayAdapter<Prazo>(view.getContext(),
					android.R.layout.simple_spinner_item, prazos);
			prazo.setAdapter(arrayAdapter);
			prazo.setSelection(arrayAdapter.getPosition(itemPedido.getPrazo()));
			prazo.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> adapter, View view, int position, long id) {
					System.out.println("ViewHolder Model: " + itemPedido.getPrazo());
					System.out.println("ViewHolder: " + adapter.getItemAtPosition(position));
					System.out.println("ViewHolder: " + adapter.getSelectedItem());
					itemPedido.setPrazo((Prazo) adapter.getItemAtPosition(position));
				}

				@Override
				public void onNothingSelected(AdapterView<?> adapter) {
				}
			});
		}
		return prazo;
	}

	public EditText getDesconto() {
		if (desconto == null) {
			desconto = (EditText) view.findViewById(R.id.desconto);
			desconto.setOnKeyListener(calcularSubTotalListener);
		}
		return desconto;
	}

	public EditText getQuantidade() {
		if (quantidade == null) {
			quantidade = (EditText) view.findViewById(R.id.quantidade);
			quantidade.setOnKeyListener(calcularSubTotalListener);
		}
		return quantidade;
	}

	public EditText getBonificacao() {
		if (bonificacao == null) {
			bonificacao = (EditText) view.findViewById(R.id.bonificacao);
			bonificacao.setOnKeyListener(calcularSubTotalListener);
		}
		return bonificacao;
	}

	public TextView getContent() {
		return content;
	}

	public TextView getSubcontent() {
		return subcontent;
	}

	public TextView getEstoque() {
		return estoque;
	}

	public TextView getPreco() {
		return preco;
	}
	
	public TextView getSubtotal() {
		return subtotal;
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