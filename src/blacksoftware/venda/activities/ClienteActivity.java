package blacksoftware.venda.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import blacksoftware.venda.R;
import blacksoftware.venda.models.Cliente;
import co.uk.rushorm.core.RushSearch;

public class ClienteActivity extends Activity {

	private ListView clientes;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cliente);
		clientes = (ListView) findViewById(R.id.clientes);
	}

	@Override
	protected void onResume() {
		super.onResume();
		List<Cliente> lista = new RushSearch().find(Cliente.class);
		ArrayList<String> objects = new ArrayList<String>();
		for (Cliente c : lista) {
			objects.add(c.getNomeFantasia());
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, objects);
		clientes.setAdapter(adapter);
	}

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
}
