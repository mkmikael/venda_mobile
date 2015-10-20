package blacksoftware.venda.activities;

import com.j256.ormlite.dao.Dao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import blacksoftware.venda.R;
import blacksoftware.venda.config.DatabaseOrm;
import blacksoftware.venda.models.Setting;

public class SettingsActivity extends Activity {

	private EditText hostView;
	private EditText portaView;
	private DatabaseOrm db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		db = new DatabaseOrm(this);
		hostView = (EditText) findViewById(R.id.hostView);
		portaView = (EditText) findViewById(R.id.portaView);
		Setting settings = Setting.defaultSetting(this);
		hostView.setText(settings.getHost());
		portaView.setText(settings.getPorta());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
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
	
	public void salvarSettings(View view) {
		Setting settings = Setting.defaultSetting(this);
		settings.setHost(hostView.getText().toString());
		settings.setPorta(portaView.getText().toString());
		try {
			Dao<Setting, Integer> dao = db.getDao(Setting.class);
			dao.createOrUpdate(settings);
			Log.i("SettingsActivity.salvarSettings", "save settings");
		} catch (Exception e) {
			Log.e("SettingsActivity.salvarSettings", "erro dao", e);
		}	
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}
	
}
