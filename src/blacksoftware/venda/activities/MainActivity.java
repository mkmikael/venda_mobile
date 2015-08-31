package blacksoftware.venda.activities;

import java.io.FileInputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import blacksoftware.venda.R;
import blacksoftware.venda.config.DatabaseOrm;
import blacksoftware.venda.config.Fixtures;
import blacksoftware.venda.service.ExcelService;

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
		//File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void atualizar(View view) {
		Fixtures.context = this;
		Fixtures.createCliente();
		try {
			FileInputStream inputStream = new FileInputStream("/storage/sdcard/Download/sistema_venda.xls");
			new ExcelService(this, db).importData(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
//		RequestQueue requestQueue = Volley.newRequestQueue(this);
//		String url = "http://192.168.100.6:8084/webvenda/rest/produto";
//		JsonArrayRequest stringRequest = new JsonArrayRequest(url,
//			new Response.Listener<JSONArray>() {
//				public void onResponse(JSONArray content) {
//					new AlertDialog.Builder(MainActivity.this)
//					.setMessage(content.toString())
//					.setTitle("Request Produtos")
//					.show();
//				}
//		}, 
//			new Response.ErrorListener() {
//				public void onErrorResponse(VolleyError error) {
//					error.printStackTrace();
//					Toast.makeText(MainActivity.this, "Erro", Toast.LENGTH_SHORT).show();
//				}
//		});
//		requestQueue.add(stringRequest);
	}
	
	public void irParaClientes(View view) {
		Intent intent = new Intent(this, ClienteActivity.class);
		startActivity(intent);
	}
	
}
