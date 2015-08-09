package blacksoftware.venda.activities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import blacksoftware.venda.R;
import blacksoftware.venda.config.DatabaseOrm;
import blacksoftware.venda.dao.GenericDAO;
import blacksoftware.venda.models.Cliente;
import blacksoftware.venda.models.Pedido;

public class ClienteActivity extends Activity {

	private Cliente clienteAtual;
	
	private ClienteFormHelper formHelper;
	private ListView clientes;
	private SparseArray<TextView> clienteDescricao;
	private RatingBar rate;
	private DatabaseOrm db = new DatabaseOrm(this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cliente);
		rate = (RatingBar) findViewById(R.id.rate);
		clienteDescricao = new SparseArray<TextView>();
		formHelper = new ClienteFormHelper();
		formHelper.initDescricaoView(this, clienteDescricao);
		clientes = (ListView) findViewById(R.id.clientes);
		clientes.setOnItemClickListener(itemSelectedListener);
		clientes.setOnItemLongClickListener(itemLongSelectedListener);
	}

	@Override
	protected void onResume() {
		super.onResume();
		List<Cliente> lista = new ArrayList<Cliente>();
		lista = new GenericDAO<Cliente>(db, Cliente.class).list();
		formHelper.setClientesView(this, lista, clientes);
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
					Toast.makeText(this, "Selecione um cliente", Toast.LENGTH_SHORT).show();
				} else {
					Intent intent = new Intent(this, PedidoActivity.class);
					intent.putExtra("cliente", clienteAtual);
					startActivity(intent);
				}
				break;
			default:
		}
		return super.onOptionsItemSelected(item);
	}
	
	private OnItemClickListener itemSelectedListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
			clientes.setSelection(position);
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
			final List<Pedido> pedidos = new ArrayList<Pedido>(clienteAtual.getPedidos());
			String[] itens = null;
			if (pedidos != null && pedidos.size() > 0) {
				itens = new String[pedidos.size() + 1];
				itens[0] = "Cod - Dt Pedido - Hora Pedido - Valor";
				for (int i = 1; i < itens.length; i++) {
					itens[i] = pedidos.get(i - 1).toString();
				}
			}
			new AlertDialog.Builder(ClienteActivity.this)
			.setTitle("Pedidos")
			.setItems(itens, new OnClickListener() {
				@Override
				public void onClick(DialogInterface di, int index) {
					System.out.println("Index" + index);
					if (index != 0) {
						Intent intent = new Intent(ClienteActivity.this, PedidoActivity.class);
						Pedido pedido = pedidos.get(index - 1);
						System.out.println(pedido.getItensPedido().size());
						intent.putExtra("pedido", pedido);
						startActivity(intent);
					}
				}
			}).show();
			return false;
		}
	};
}
