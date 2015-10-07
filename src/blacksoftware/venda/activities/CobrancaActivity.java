package blacksoftware.venda.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import blacksoftware.venda.R;
import blacksoftware.venda.R.id;
import blacksoftware.venda.R.layout;
import blacksoftware.venda.R.menu;
import blacksoftware.venda.models.Cobranca;
import blacksoftware.venda.models.ItemCobranca;

public class CobrancaActivity extends Activity {

	private ListView cobrancasView;
	private ListView itensCobrancasView;
	private Cobranca cobrancaSelected;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cobranca);
		initCobrancasView();
		initItensCobrancasView();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.cobranca, menu);
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
	
	private void initCobrancasView() {
		cobrancasView = (ListView) findViewById(R.id.cobrancas);
		ArrayAdapter<Cobranca> cobrancaAdapter = new ArrayAdapter<Cobranca>(this, android.R.layout.simple_list_item_1);
		cobrancasView.setAdapter(cobrancaAdapter);
		cobrancasView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				cobrancaSelected = (Cobranca) parent.getSelectedItem();
				if (cobrancaSelected != null && !cobrancaSelected.getItensCobranca().isEmpty()) {
					ArrayAdapter<String> adapter = (ArrayAdapter<String>) itensCobrancasView.getAdapter();
					adapter.clear();
					for (ItemCobranca i : cobrancaSelected.getItensCobranca()) {
						adapter.add(i.toString());
					}
				}
			}
		});
	}
	
	private void initItensCobrancasView() {
		itensCobrancasView = (ListView) findViewById(R.id.itens_cobranca);
		ArrayAdapter<String> itensCobrancaAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		itensCobrancasView.setAdapter(itensCobrancaAdapter);
	}
}
