package blacksoftware.venda.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import blacksoftware.venda.R;
import blacksoftware.venda.models.Cliente;
import co.uk.rushorm.core.RushSearch;

public class ClienteActivity extends Activity {

	private Cliente clienteAtual;
	
	private ListView clientes;
	private SparseArray<TextView> clienteDescricao;
	private RatingBar rate;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cliente);
		initClienteDescricao();
		rate = (RatingBar) findViewById(R.id.rate);
		clientes = (ListView) findViewById(R.id.clientes);
		clientes.setOnItemClickListener(itemSelectedListener);
		clientes.setOnItemLongClickListener(itemLongSelectedListener);
	}

	@Override
	protected void onResume() {
		super.onResume();
		carregarListaDeClientes();
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
			Map<String, Object> item = (Map<String, Object>) adapter.getItemAtPosition(position);
			Cliente clienteSelected = (Cliente) item.get("content");
			clienteAtual = clienteSelected;
			carregarDescricaoCliente(clienteSelected);
			Log.i("Application", "Objeto selectionado " + clienteSelected.getClass().getName() + " : " + clienteSelected);
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
				}
			})
			.show();
			return false;
		}
	};
	
	private void carregarDescricaoCliente(Cliente cliente) {
		clienteDescricao.get(R.id.codigo).setText(cliente.getCodigo());
		clienteDescricao.get(R.id.fantasia).setText(cliente.getNomeFantasia());
		clienteDescricao.get(R.id.cnpj).setText(cliente.getCnpj());
		clienteDescricao.get(R.id.razaoSocial).setText(cliente.getRazaoSocial());
		clienteDescricao.get(R.id.endereco).setText(cliente.getEndereco());
		clienteDescricao.get(R.id.bairro).setText(cliente.getBairro());
		clienteDescricao.get(R.id.referencia).setText(cliente.getReferencia());
		clienteDescricao.get(R.id.cidade).setText(cliente.getCidade());
		clienteDescricao.get(R.id.insEstadual).setText(cliente.getInscricaoEstadual());
		clienteDescricao.get(R.id.limite).setText(cliente.getLimite() + "");
		clienteDescricao.get(R.id.telefone).setText(cliente.getTelefone());
		clienteDescricao.get(R.id.responsavel).setText(cliente.getResponsavel());
		clienteDescricao.get(R.id.situacao).setText(cliente.getSituacao().toString());
		clienteDescricao.get(R.id.canal).setText(cliente.getCanal());
		clienteDescricao.get(R.id.ramo).setText(cliente.getRamo());
		rate.setRating(cliente.getRate());
	}
	
	private void carregarListaDeClientes() {
		List<Cliente> lista = new RushSearch().find(Cliente.class);
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		for (Cliente c : lista) {
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("content", c);
			String subcontent = new StringBuilder(c.getEndereco())
				.append(" - ")
				.append(c.getBairro())
				.append(" - ")
				.append(c.getCidade())
				.toString();
			item.put("subcontent", subcontent);
			data.add(item);
		}
		SimpleAdapter simpleAdapter = new SimpleAdapter(this, data, android.R.layout.simple_expandable_list_item_2,
				new String[] { "content", "subcontent" }, new int[] { android.R.id.text1, android.R.id.text2 });
		clientes.setAdapter(simpleAdapter);
	}
	
	private void initClienteDescricao() {
		clienteDescricao = new SparseArray<TextView>();
		clienteDescricao.put(R.id.codigo, (TextView) findViewById(R.id.codigo));
		clienteDescricao.put(R.id.fantasia, (TextView) findViewById(R.id.fantasia));
		clienteDescricao.put(R.id.cnpj, (TextView) findViewById(R.id.cnpj));
		clienteDescricao.put(R.id.razaoSocial, (TextView) findViewById(R.id.razaoSocial));
		clienteDescricao.put(R.id.endereco, (TextView) findViewById(R.id.endereco));
		clienteDescricao.put(R.id.bairro, (TextView) findViewById(R.id.bairro));
		clienteDescricao.put(R.id.referencia, (TextView) findViewById(R.id.referencia));
		clienteDescricao.put(R.id.cidade, (TextView) findViewById(R.id.cidade));
		clienteDescricao.put(R.id.insEstadual, (TextView) findViewById(R.id.insEstadual));
		clienteDescricao.put(R.id.situacao, (TextView) findViewById(R.id.situacao));
		clienteDescricao.put(R.id.limite, (TextView) findViewById(R.id.limite));
		clienteDescricao.put(R.id.telefone, (TextView) findViewById(R.id.telefone));
		clienteDescricao.put(R.id.responsavel, (TextView) findViewById(R.id.responsavel));
		clienteDescricao.put(R.id.canal, (TextView) findViewById(R.id.canal));
		clienteDescricao.put(R.id.ramo, (TextView) findViewById(R.id.ramo));
	}
}
