package blacksoftware.venda.activities;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import blacksoftware.venda.R;
import blacksoftware.venda.activities.adapters.ItemPedidoChangedListener;
import blacksoftware.venda.activities.adapters.OnSelectedItemAdapter;
import blacksoftware.venda.activities.adapters.PedidoAdapter;
import blacksoftware.venda.components.ComboList;
import blacksoftware.venda.components.ComboList.OnSelectedItemListener;
import blacksoftware.venda.config.DatabaseOrm;
import blacksoftware.venda.dao.GenericDAO;
import blacksoftware.venda.dao.ProdutoDAO;
import blacksoftware.venda.models.ItemPedido;
import blacksoftware.venda.models.Pedido;
import blacksoftware.venda.models.Pedido.StatusPedido;
import blacksoftware.venda.models.Prazo;
import blacksoftware.venda.service.PedidoService;

public class PedidoActivity extends Activity {

	private ListView itensPedidoView;
	private TextView total;
	private Button btnCalendar;
	private ComboList<String> filtroFornecedores;
	private ComboList<String> filtroGrupos;
	private Spinner alterarPrazos;
	private PedidoAdapter pedidoAdapter;
	private CheckBox checkPedidos;
	
	private DatabaseOrm db = new DatabaseOrm(this);
	private Pedido pedido;
	private PedidoService pedidoService;
	private ProdutoDAO daoProduto;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		pedidoService = new PedidoService(db);
		daoProduto = new ProdutoDAO(db);
		setContentView(R.layout.activity_pedido);
		initBtnCalendar();
		initFiltroFornecedores();
		initFiltroGrupos();
		initAlterarPrazos();
		initCheckPedidos();
		itensPedidoView = (ListView) findViewById(R.id.itens);
		total = (TextView) findViewById(R.id.total);
		initPedidoAdapter();
	}

	@Override
	protected void onResume() {
		super.onResume();
		resumeItensPedidos();
	}

	@Override
	protected void onPause() {
		super.onPause();
		filtroFornecedores.getDialog().dismiss();
		filtroGrupos.getDialog().dismiss();
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
		int id = item.getItemId();
		if (id == R.id.action_salvar_pedido) {
			if (pedido.getStatusPedido() == StatusPedido.SINCRONIZADO) {
				String msg = "Voce nao pode editar um pedido sincronizado";
				Toast.makeText(this, msg , Toast.LENGTH_LONG).show();
			} else if (pedido.getStatusPedido() == StatusPedido.NOVO) {
				salvarAction();
			}
			return true;
		} else if (id == R.id.action_clientes) {
			Intent intent = new Intent(this, ClienteActivity.class);
			startActivity(intent);
		} else if (id == R.id.action_cancelar_pedido) {
			if (pedido.getStatusPedido() == StatusPedido.SINCRONIZADO) {
				String msg = "Voce nao pode cancelar um pedido sincronizado";
				Toast.makeText(this, msg , Toast.LENGTH_LONG).show();
			} else if (pedido.getStatusPedido() == StatusPedido.NOVO) {
				pedido.setStatusPedido(StatusPedido.CANCELADO);
				salvarAction();
			}
		}
		return super.onOptionsItemSelected(item);
	}

	private void initPedidoAdapter() {
		pedidoAdapter = new PedidoAdapter(this);
		pedidoAdapter.setItemPedidoChangedListener(new ItemPedidoChangedListener() {
			@Override
			public void itemPedidoChanged(ItemPedido itemPedido) {
				pedidoService.changeItensPedido(pedido, itemPedido);
				total.setText(pedido.getTotal() + "");
			}
		});
	}
	
	private void salvarAction() {
		if (pedido.getItensPedido().isEmpty()) {
			Toast.makeText(this, "Selecione pelo menos 1(um) produto para poder salvar!", Toast.LENGTH_SHORT).show();
		} else {
			pedidoService.saveItensPedido(pedido);
			Intent irParaCliente = new Intent(this, ClienteActivity.class);
			startActivity(irParaCliente);
		}
	}
	
	private void resumeItensPedidos() {
		pedido = (Pedido) getIntent().getExtras().get("pedido"); 
		List<ItemPedido> itensPedidoList = pedidoService.carregarItensPedido(pedido, daoProduto.list());
		pedidoAdapter.setItens(itensPedidoList);
		itensPedidoView.setAdapter(pedidoAdapter);
		total.setText(pedido.getTotal() + "");
		if (pedido != null && pedido.getPrazo() != null) {
			ArrayAdapter<Prazo> adapter = (ArrayAdapter<Prazo>) alterarPrazos.getAdapter();
			alterarPrazos.setSelection(adapter.getPosition(pedido.getPrazo()));
		}
	}
	
	private void initCheckPedidos() {
		checkPedidos = (CheckBox) findViewById(R.id.checkPedidos);
		checkPedidos.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
				if (checked) {
					pedidoAdapter.setItens(pedido.getItensPedido());
					pedidoAdapter.notifyDataSetChanged();
				} else {
					filterListener.onSelectedItem(null, 0);
				}
			}
		});
	}
	
	private void initAlterarPrazos() {
		alterarPrazos = (Spinner) findViewById(R.id.alterarPrazos);
		GenericDAO<Prazo> prazoDao = new GenericDAO<Prazo>(db, Prazo.class);
		final List<Prazo> list = prazoDao.list();
		ArrayAdapter<Prazo> arrayAdapter = new ArrayAdapter<Prazo>(this, android.R.layout.simple_spinner_item, list);
		alterarPrazos.setOnItemSelectedListener(new OnSelectedItemAdapter() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				Prazo prazo = (Prazo) parent.getSelectedItem();
				pedido.setPrazo(prazo);
			}
		});
		alterarPrazos.setAdapter(arrayAdapter);
	}
	
	private ComboList.OnSelectedItemListener<String> filterListener = new ComboList.OnSelectedItemListener<String>() {
		@Override
		public void onSelectedItem(String item, int position) {
			String fornecedor = filtroFornecedores.getSelectedItem();
			String grupo = filtroGrupos.getSelectedItem();
			grupo = grupo.equals("Todos") ? null : grupo;
			fornecedor = fornecedor.equals("Todos") ? null : fornecedor;
			List<ItemPedido> carregarItensPedido = pedidoService.carregarItensPedido(pedido, daoProduto.criteriaFornecedorAndGrupo(fornecedor, grupo));
			pedidoAdapter.setItens(carregarItensPedido);
			pedidoAdapter.notifyDataSetChanged();
		}
	};
	
	@SuppressWarnings("unchecked")
	private void initFiltroFornecedores() {
		filtroFornecedores = (ComboList<String>) findViewById(R.id.filtroFornecedores);
		ProdutoDAO daoProduto = new ProdutoDAO(db);
		final LinkedList<String> fornecedoresList = new LinkedList<String>(daoProduto.findFornecedores());
		fornecedoresList.addFirst("Todos");
		filtroFornecedores.setList(fornecedoresList);
		filtroFornecedores.setOnSelectedItemListener(new OnSelectedItemListener<String>() {
			@Override
			public void onSelectedItem(String item, int position) {
				ProdutoDAO daoProduto = new ProdutoDAO(db);
				if (!filtroFornecedores.getSelectedItem().equals("Todos")) {
					LinkedList<String> gruposList = new LinkedList<String>(daoProduto.findGruposByFornecedor(filtroFornecedores.getSelectedItem()));
					gruposList.addFirst("Todos");
					filtroGrupos.setList(gruposList);
				}
				filterListener.onSelectedItem(item, position);
			}
		});
	}

	@SuppressWarnings("unchecked")
	private void initFiltroGrupos() {
		filtroGrupos = (ComboList<String>) findViewById(R.id.filtroGrupos);
		ProdutoDAO daoProduto = new ProdutoDAO(db);
		final LinkedList<String> grupoList = new LinkedList<String>(daoProduto.findGrupos());
		grupoList.addFirst("Todos");
		filtroGrupos.setList(grupoList);
		filtroGrupos.setOnSelectedItemListener(filterListener);
	}
	
	private void initBtnCalendar() {
		btnCalendar = (Button) findViewById(R.id.btnCalendar);
		btnCalendar.setText(DateFormat.getDateFormat(this).format(new Date()));
		
		final DatePicker datePicker = new DatePicker(this);
		final AlertDialog calendarDialog = new AlertDialog.Builder(PedidoActivity.this)
		.setTitle("Data de Faturamento")
		.setView(datePicker)
		.setPositiveButton("OK", new android.content.DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				pedido.setDataDeFaturamento(new Date(datePicker.getCalendarView().getDate()));
				btnCalendar.setText(datePicker.getDayOfMonth() + "/" + (datePicker.getMonth() + 1) + "/" + datePicker.getYear());
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
