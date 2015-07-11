package blacksoftware.venda.activities;

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
import blacksoftware.venda.models.Cliente;

public class ClienteActivity extends Activity {

	private Cliente clienteAtual;
	
	private ClienteFormHelper formHelper;
	private ListView clientes;
	private SparseArray<TextView> clienteDescricao;
	private RatingBar rate;
	
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
		List<Cliente> lista = null;
		formHelper.setClientesView(this, lista, clientes);
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
			case R.id.action_pedido:
				if (clienteAtual == null) {
					Toast.makeText(this, "Selecione um cliente", Toast.LENGTH_SHORT).show();
				} else {
					Intent intent = new Intent(this, PedidoActivity.class);
					intent.putExtra("cliente", clienteAtual.getCodigo());
					startActivity(intent);
				}
				break;
			default:
		}
		return true;
		//return super.onOptionsItemSelected(item);
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
			String[] items = { "Dt Pedido - Hora Pedido - Valor", "07/04/2015 - 14:30 - 501,32", "08/04/2015 - 14:30 - 1001,32" };
			new AlertDialog.Builder(ClienteActivity.this)
			.setTitle("Pedidos")
			.setItems(items, new OnClickListener() {
				@Override
				public void onClick(DialogInterface di, int index) {
					System.out.println("Index" + index);
					if (index != 0) {
						Intent intent = new Intent(ClienteActivity.this, PedidoActivity.class);
						intent.putExtra("cliente", clienteAtual.getCodigo());
						startActivity(intent);
					}
				}
			})
			.show();
			return false;
		}
	};
}
