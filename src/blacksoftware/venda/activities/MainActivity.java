package blacksoftware.venda.activities;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import blacksoftware.venda.R;
import blacksoftware.venda.components.ComboList;

public class MainActivity extends Activity {
	
	private ComboList<String> comboList;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		List<String> strings = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			strings.add("Mikael");
			strings.add("Yan");
			strings.add("Guilherme");
		}
		comboList = (ComboList<String>) findViewById(R.id.comboList);
		comboList.setList(strings);
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
		RequestQueue requestQueue = Volley.newRequestQueue(this);
		String url = "http://192.168.100.6:8084/webvenda/rest/produto";
		JsonArrayRequest stringRequest = new JsonArrayRequest(url,
			new Response.Listener<JSONArray>() {
				public void onResponse(JSONArray content) {
					new AlertDialog.Builder(MainActivity.this)
					.setMessage(content.toString())
					.setTitle("Request Produtos")
					.show();
				}
		}, 
			new Response.ErrorListener() {
				public void onErrorResponse(VolleyError error) {
					error.printStackTrace();
					Toast.makeText(MainActivity.this, "Erro", Toast.LENGTH_SHORT).show();
				}
		});
		requestQueue.add(stringRequest);
	}
	
	public void irParaClientes(View view) {
		Intent intent = new Intent(this, ClienteActivity.class);
		startActivity(intent);
	}
	
	
}
