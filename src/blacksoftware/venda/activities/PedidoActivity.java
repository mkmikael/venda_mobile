package blacksoftware.venda.activities;

import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import blacksoftware.venda.R;
import blacksoftware.venda.config.DatabaseOrm;
import blacksoftware.venda.models.Pedido;

import com.j256.ormlite.dao.Dao;

public class PedidoActivity extends Activity {

	private ListView itensPedido;
	private TextView total;
	private Button btnCalendar;
	private DatabaseOrm db = new DatabaseOrm(this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pedido);
		initBtnCalendar();
		total = (TextView) findViewById(R.id.total);
		itensPedido = (ListView) findViewById(R.id.itens);
		try {
			Dao<Pedido, Integer> dao = db.getDao(Pedido.class);
			Pedido pedido =  dao.queryForEq("codigo", "000001").get(0);
			itensPedido.setAdapter(new PedidoAdapter(this, pedido.getItensPedido(), total));
			System.out.println(getIntent().getExtras().get("cliente"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		db.close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.pedido, menu);
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

	private void initBtnCalendar() {
		btnCalendar = (Button) findViewById(R.id.btnCalendar);
		btnCalendar.setText(DateFormat.getDateFormat(this).format(new Date()));
		
		FrameLayout frame = (FrameLayout) LayoutInflater.from(PedidoActivity.this).inflate(R.layout.calendar, null, false);
		final DatePicker datePicker = (DatePicker) frame.getChildAt(0);
		final AlertDialog calendarDialog = new AlertDialog.Builder(PedidoActivity.this)
		.setTitle("Data de Faturamento")
		.setView(frame)
		.setPositiveButton("OK", new android.content.DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				btnCalendar.setText(datePicker.getDayOfMonth() + "/" + datePicker.getMonth() + "/" + datePicker.getYear());
			}
		}).create();
		
		btnCalendar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				calendarDialog.show();
			}
		});
	}
	
}
