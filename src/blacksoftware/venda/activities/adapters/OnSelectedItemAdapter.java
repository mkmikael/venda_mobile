package blacksoftware.venda.activities.adapters;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

public abstract class OnSelectedItemAdapter implements OnItemSelectedListener {

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {}
	
}
