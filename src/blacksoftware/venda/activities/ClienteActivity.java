package blacksoftware.venda.activities;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import blacksoftware.venda.R;
import blacksoftware.venda.models.Cliente;
import co.uk.rushorm.core.RushSearch;

public class ClienteActivity extends Activity {

	private ListView clientes;
	private Cliente clienteAtual;
	private SparseArray<TextView> clienteDescricao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cliente);
		initClienteDescricao();
		clientes = (ListView) findViewById(R.id.clientes);
		clientes.setOnItemClickListener(itemSelectedListener);
	}

	@Override
	protected void onResume() {
		super.onResume();
		List<Cliente> lista = new RushSearch().find(Cliente.class);
		ArrayAdapter<Cliente> adapter = new ArrayAdapter<Cliente>(this, android.R.layout.simple_list_item_1, lista);
		clientes.setAdapter(adapter);
	}

	private OnItemClickListener itemSelectedListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
			Cliente clienteSelected = (Cliente) adapter.getItemAtPosition(position);
			if (!clienteSelected.equals(clienteAtual) || clienteAtual == null) {
				clienteAtual = clienteSelected;
				clienteDescricao.get(R.id.fantasia).setText(clienteSelected.getNomeFantasia());
				clienteDescricao.get(R.id.cnpj).setText(clienteSelected.getCnpj());
				clienteDescricao.get(R.id.razaoSocial).setText(clienteSelected.getRazaoSocial());
				clienteDescricao.get(R.id.endereco).setText(clienteSelected.getEndereco());
				clienteDescricao.get(R.id.insEstadual).setText(clienteSelected.getInscricaoEstadual());
				clienteDescricao.get(R.id.limite).setText(clienteSelected.getLimite() + "");
				clienteDescricao.get(R.id.telefone).setText(clienteSelected.getTelefone());
				clienteDescricao.get(R.id.responsavel).setText(clienteSelected.getResponsavel());
				clienteDescricao.get(R.id.situacao).setText(clienteSelected.getSituacao() + "");
				clienteDescricao.get(R.id.canal).setText(clienteSelected.getCanal() + "");
				clienteDescricao.get(R.id.ramo).setText(clienteSelected.getRamo() + "");
				Log.i("Application", "Objeto selectionado " + clienteSelected.getClass().getName() + " : " + clienteSelected);
			}
		}
	};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.cliente, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void initClienteDescricao() {
		clienteDescricao = new SparseArray<TextView>();
		clienteDescricao.put(R.id.fantasia, (TextView) findViewById(R.id.fantasia));
		clienteDescricao.put(R.id.cnpj, (TextView) findViewById(R.id.cnpj));
		clienteDescricao.put(R.id.razaoSocial, (TextView) findViewById(R.id.razaoSocial));
		clienteDescricao.put(R.id.endereco, (TextView) findViewById(R.id.endereco));
		clienteDescricao.put(R.id.insEstadual, (TextView) findViewById(R.id.insEstadual));
		clienteDescricao.put(R.id.situacao, (TextView) findViewById(R.id.situacao));
		clienteDescricao.put(R.id.limite, (TextView) findViewById(R.id.limite));
		clienteDescricao.put(R.id.telefone, (TextView) findViewById(R.id.telefone));
		clienteDescricao.put(R.id.responsavel, (TextView) findViewById(R.id.responsavel));
		clienteDescricao.put(R.id.canal, (TextView) findViewById(R.id.canal));
		clienteDescricao.put(R.id.ramo, (TextView) findViewById(R.id.ramo));
	}
}
