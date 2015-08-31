package blacksoftware.venda.activities.adapters;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import android.view.View;
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
import blacksoftware.venda.util.Util;

public class PedidoViewHolder {
	
	private View view;
	private TextView content;
	private TextView subcontent;
	private TextView estoque;
	private Spinner tipoView;
	private Spinner prazoView;
	private TextView precoView;
	private EditText quantidadeView;
	private EditText bonificacaoView;
	private EditText descontoView;
	private TextView precoUnitario;
	private TextView subtotal;
	
	private ItemPedido itemPedido;
	private List<Prazo> prazos;
	private ItemPedidoChangedListener itemPedidoChangedListener;
	
	public PedidoViewHolder(ItemPedido itemPedido, View view, List<Prazo> prazos) {
		this.itemPedido = itemPedido;
		this.view = view;
		this.prazos = prazos;
		content = (TextView) view.findViewById(R.id.content);
		subcontent = (TextView) view.findViewById(R.id.subcontent);
		estoque = (TextView) view.findViewById(R.id.estoque);
		precoView = (TextView) view.findViewById(R.id.preco);
		precoUnitario = (TextView) view.findViewById(R.id.precoUnitario);
		subtotal = (TextView) view.findViewById(R.id.subtotal);
		initTipo();
		initPrazo();
		initDesconto();
		initQuantidade();
		initBonificacao();
	}

	private void initTipo() {
		tipoView = (Spinner) view.findViewById(R.id.tipo);
		List<Unidade> unidades = new ArrayList<Unidade>(itemPedido.getProduto().getUnidades());
		if (unidades == null || unidades.isEmpty()) return;
		ArrayAdapter<Unidade> arrayAdapter = new ArrayAdapter<Unidade>(view.getContext(), android.R.layout.simple_spinner_item, unidades);
		tipoView.setAdapter(arrayAdapter);
		tipoView.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> adapter, View view, int position, long id) {
				Unidade unidade = (Unidade) adapter.getSelectedItem();
				itemPedido.setUnidade(unidade);
				precoView.setText(Util.roundToString(unidade.getValor()));
				calcularItemPedido();
			}
			public void onNothingSelected(AdapterView<?> adapter) {}
		});
	}

	private void initPrazo() {
		prazoView = (Spinner) view.findViewById(R.id.prazo);
		if (prazos == null || prazos.isEmpty()) return;
		ArrayAdapter<Prazo> arrayAdapter = new ArrayAdapter<Prazo>(view.getContext(),
				android.R.layout.simple_spinner_item, prazos);
		prazoView.setAdapter(arrayAdapter);
		prazoView.setSelection(arrayAdapter.getPosition(itemPedido.getPrazo()));
		prazoView.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> adapter, View view, int position, long id) {
				itemPedido.setPrazo((Prazo) adapter.getSelectedItem());
			}
			public void onNothingSelected(AdapterView<?> adapter) {}
		});
	}

	private void initDesconto() {
		descontoView = (EditText) view.findViewById(R.id.desconto);
		descontoView.addTextChangedListener(new TextWatcherAdapter() {
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				BigDecimal desc = BigDecimal.ZERO;
				try {
					desc = new BigDecimal(s.toString());
					if (desc.doubleValue() < 0 && desc.doubleValue() > 100) 
						throw new NumberFormatException();
				} catch (NumberFormatException e) {
					descontoView.setText("");
					desc = BigDecimal.ZERO;
				} finally {
					itemPedido.setDesconto(desc);
					calcularItemPedido();
				}
			}
		});
	}

	private void initQuantidade() {
		quantidadeView = (EditText) view.findViewById(R.id.quantidade);
		quantidadeView.addTextChangedListener(new TextWatcherAdapter() {
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				int quant = 0;
				try {
					if (s.toString().isEmpty()) {
						quant = 0;
					} else {
						quant = Integer.valueOf(s.toString());
					}
				} catch (NumberFormatException e) {
					quantidadeView.setText("");
					quant = 0;
				} finally {
					itemPedido.setQuantidade(quant);
					calcularItemPedido();
				}
			}
		});
	}

	private void initBonificacao() {
		bonificacaoView = (EditText) view.findViewById(R.id.bonificacao);
		bonificacaoView.addTextChangedListener(new TextWatcherAdapter() {
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				int bonif = 0;
				try {
					bonif = Integer.valueOf(s.toString());
				} catch (NumberFormatException e) {
					bonificacaoView.setText("");
					bonif = 0;
				} finally {
					itemPedido.setBonificacao(bonif);
					calcularItemPedido();
				}
			}
		});
	}

	public void setItemPedidoChangedListener(ItemPedidoChangedListener itemPedidoChangedListener) {
		this.itemPedidoChangedListener = itemPedidoChangedListener;
	}

	public void setItemPedido(ItemPedido itemPedido) {
		this.itemPedido = itemPedido;
		content.setText(itemPedido.getProduto().getNome());
		estoque.setText(itemPedido.getProduto().toStringUnidades());
		subcontent.setText(itemPedido.getProduto().getSubDescricao());
		descontoView.setText(itemPedido.getDesconto() + "");
		quantidadeView.setText(itemPedido.getQuantidade() + "");
		bonificacaoView.setText(itemPedido.getBonificacao() + "");
		if (itemPedido.getUnidade() != null) {
			BigDecimal valor = itemPedido.getUnidade().getValor().setScale(2, RoundingMode.UP);
			precoView.setText(valor.toString());
		}
		initTipo();
		subtotal.setText(itemPedido.getTotal().toString());
	}
	
	public void calcularItemPedido() {
		itemPedidoChangedListener.itemPedidoChanged(itemPedido);
		precoUnitario.setText(itemPedido.getPrecoNegociado() + "");
		subtotal.setText(itemPedido.getTotal() + "");
	}
}