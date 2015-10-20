package blacksoftware.venda.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import blacksoftware.venda.R;
import blacksoftware.venda.config.DatabaseOrm;
import blacksoftware.venda.service.RestfulService;

public class MainActivity extends Activity {

	private DatabaseOrm db = new DatabaseOrm(this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
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
	
	public void atualizar(View view) {
		ProgressDialog progressDialog = ProgressDialog.show(this, "Aguarde...", "Sincronizando com o servidor");
		RestfulService restService = new RestfulService(this, progressDialog);
		restService.load();
		//progressDialog.dismiss();
	}
	
	public void enviar(View view) {
		RestfulService restService = new RestfulService(this);
		restService.enviarPedidos();
	}
	
	public void irParaClientes(View view) {
		Intent intent = new Intent(this, ClienteActivity.class);
		startActivity(intent);
	}
	
	public void irSettings(View view) {
		Intent intent = new Intent(this, SettingsActivity.class);
		startActivity(intent);
	}
}
