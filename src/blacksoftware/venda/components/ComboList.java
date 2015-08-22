package blacksoftware.venda.components;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

public class ComboList<T> extends Button {

	private AlertDialog dialog;
	private ArrayAdapter<T> listAdapter;
	private T selected;
	private OnSelectedItemListener<T> onSelectedItemListener;
	private List<T> list = new ArrayList<T>();
	
	public ComboList(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	public interface OnSelectedItemListener<T> {
		void onSelectedItem(T item, int position);
	}
	
	private DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface di, int position) {
			if (list != null && !list.isEmpty()) {
				selected = list.get(position);
				if (onSelectedItemListener != null) onSelectedItemListener.onSelectedItem(selected, position);
				setText(selected.toString());
			}
			dialog.hide();
		}
	};

	private void init() {
		listAdapter = new ArrayAdapter<T>(getContext(), android.R.layout.select_dialog_singlechoice, list);
		dialog = new Builder(getContext())
				.setSingleChoiceItems(listAdapter, 0, onClickListener)
				.create();
		setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dialog.show();
			}
		});
	}

	public OnSelectedItemListener<T> getOnSelectedItemListener() {
		return onSelectedItemListener;
	}

	public void setOnSelectedItemListener(OnSelectedItemListener<T> onSelectedItemListener) {
		this.onSelectedItemListener = onSelectedItemListener;
	}

	public T getSelectedItem() {
		return selected;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
		if (!list.isEmpty()) {
			selected = list.get(0);
			setText(list.get(0).toString());
			listAdapter.clear();
			listAdapter.addAll(list);
			listAdapter.notifyDataSetChanged();
		} else {
			setText("Sem registros");
		}
	}
	
	public void setTitle(String title) {
		dialog.setTitle(title);
	}
}
