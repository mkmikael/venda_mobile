package blacksoftware.venda.activities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import blacksoftware.venda.R;
import blacksoftware.venda.activities.adapters.TextWatcherAdapter;
import blacksoftware.venda.config.DatabaseOrm;
import blacksoftware.venda.dao.ClienteDAO;
import blacksoftware.venda.dao.PedidoDAO;
import blacksoftware.venda.models.Cliente;
import blacksoftware.venda.models.Pedido;
import blacksoftware.venda.models.Pedido.StatusPedido;

public class ClienteActivity extends Activity {

	private Cliente clienteAtual;
	
	private ClienteFormHelper formHelper;
	private ListView clientesView;
	
	private EditText filtroClientes;
	private SparseArray<TextView> clienteDescricao;
	private RatingBar rate;
	private DatabaseOrm db = new DatabaseOrm(this);
	private PedidoDAO daoPedido;
	private ClienteDAO daoCliente;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		daoPedido = new PedidoDAO(db);
		daoCliente = new ClienteDAO(db);
		setContentView(R.layout.activity_cliente);
		rate = (RatingBar) findViewById(R.id.rate);
		clienteDescricao = new SparseArray<TextView>();
		formHelper = new ClienteFormHelper();
		formHelper.initDescricaoView(this, clienteDescricao);
		clientesView = (ListView) findViewById(R.id.clientes);
		clientesView.setOnItemClickListener(itemSelectedListener);
		clientesView.setOnItemLongClickListener(itemLongSelectedListener);
		initFiltroCliente();
	}

	@Override
	protected void onResume() {
		super.onResume();
		formHelper.setClientesView(this, daoCliente.list(), clientesView);
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		db.close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.cliente, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
			case R.id.action_new_pedido:
				if (clienteAtual == null) {
					Log.i(this.getClass().getSimpleName() + ".resumeItensPedidos", "Novo pedido para o cliente " + clienteAtual);
					Toast.makeText(this, "Selecione um cliente", Toast.LENGTH_SHORT).show();
				} else {
					Pedido pedido = new Pedido();
					pedido.setCliente(clienteAtual);
					Intent intent = new Intent(this, PedidoActivity.class);
					intent.putExtra("pedido", pedido);
					startActivity(intent);
				}
				break;
			case R.id.action_menu_principal:
				Intent intent = new Intent(this, MainActivity.class);
				startActivity(intent);
				break;
			default:
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void initFiltroCliente() {
		filtroClientes = (EditText) findViewById(R.id.filtroClientes);
		filtroClientes.addTextChangedListener(new TextWatcherAdapter() {
			@Override
			public void afterTextChanged(Editable s) {
				super.afterTextChanged(s);
				String constrains = s.toString().toUpperCase(Locale.getDefault());
				SimpleAdapter adapter = (SimpleAdapter) clientesView.getAdapter();
				formHelper.setClientesView(ClienteActivity.this, daoCliente.findAllLikeNome(constrains), clientesView);
				adapter.notifyDataSetChanged();
			}
		});
	}
	
	private OnItemClickListener itemSelectedListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
			@SuppressWarnings("unchecked")
			Map<String, Object> item = (Map<String, Object>) adapter.getItemAtPosition(position);
			clienteAtual = (Cliente) item.get("content");
			formHelper.setClienteView(clienteAtual, clienteDescricao, rate);
		}
	};
	
	private OnItemLongClickListener itemLongSelectedListener = new OnItemLongClickListener() {
		
		@Override
		public boolean onItemLongClick(AdapterView<?> adapter, View view,
				int position, long id) {
			// TODO - Criar tabela pedidos
			// TODO - Usar dismiss() AlertDialog
			itemSelectedListener.onItemClick(adapter, view, position, id);
			final ArrayList<Pedido> pedidos = new ArrayList<Pedido>(clienteAtual.getPedidos());
			Collections.reverse(pedidos);
			List<Pedido> toRemove = new ArrayList<Pedido>();
			for (Pedido pedido : pedidos) {
				if (pedido.getStatusPedido() == StatusPedido.CANCELADO) {
					toRemove.add(pedido);
				}
			}
			pedidos.removeAll(toRemove);
			if (pedidos.isEmpty()) {
				Toast.makeText(ClienteActivity.this, "O cliente nao possui pedidos armazenados", Toast.LENGTH_SHORT).show();
			} else {
				final ArrayAdapter<Pedido> arrayAdapter = new ArrayAdapter<Pedido>(ClienteActivity.this, android.R.layout.select_dialog_singlechoice, pedidos);
				final Intent intent = new Intent(ClienteActivity.this, PedidoActivity.class);
				intent.putExtra("pedido", pedidos.get(0));
				new AlertDialog.Builder(ClienteActivity.this)
						.setTitle("Dt Ped - Dt Fatur - Hora Pedido - Valor - Status")
						.setNegativeButton("Fechar", null)
						.setPositiveButton("Selecionar", new OnClickListener() {
							public void onClick(DialogInterface di, int index) {
								Log.i(ClienteActivity.class.getName(), "Ir para PedidoActivity");
								startActivity(intent);
							}
						})
						.setSingleChoiceItems(arrayAdapter, 0, new OnClickListener() {
							public void onClick(DialogInterface di, int index) {
								Pedido pedido = pedidos.get(index);
								Log.i(this.getClass().getSimpleName() + "$itemLongSelectedListener$OnClickListener.onClick", "Alterar pedido " + clienteAtual);
								Log.i(ClienteActivity.class.getName(), "Selecionou o cliente " + clienteAtual + " com o pedido " + pedido);
								intent.putExtra("pedido", pedido);
							}
						}).show();
			}
			return false;
		}
	};
}
