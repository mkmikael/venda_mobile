package blacksoftware.venda.activities;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
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
import blacksoftware.venda.activities.adapters.PedidoAdapter;
import blacksoftware.venda.components.ComboList;
import blacksoftware.venda.config.DatabaseOrm;
import blacksoftware.venda.dao.GenericDAO;
import blacksoftware.venda.models.Cliente;
import blacksoftware.venda.models.Fornecedor;
import blacksoftware.venda.models.Grupo;
import blacksoftware.venda.models.ItemPedido;
import blacksoftware.venda.models.Pedido;
import blacksoftware.venda.models.Prazo;
import blacksoftware.venda.models.Produto;

public class PedidoActivity extends Activity {

	private ListView itensPedidoView;
	private TextView total;
	private Button btnCalendar;
	private ComboList<Fornecedor> filtroFornecedores;
	private ComboList<Grupo> filtroGrupos;
	private Spinner alterarPrazos;
	private PedidoAdapter pedidoAdapter;
	private CheckBox checkPedidos;
	private DatabaseOrm db = new DatabaseOrm(this);
	private Pedido pedido;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pedido);
		initBtnCalendar();
		initFiltroFornecedores();
		initFiltroGrupos();
		initAlterarPrazos();
		initCheckPedidos();
		itensPedidoView = (ListView) findViewById(R.id.itens);
		total = (TextView) findViewById(R.id.total);
		pedidoAdapter = new PedidoAdapter(this, db, total);
	}

	@Override
	protected void onResume() {
		super.onResume();
		resumeItensPedidos();
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
			salvarAction();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void salvarAction() {
		GenericDAO<Pedido> pedidoDAO = new GenericDAO<Pedido>(db, Pedido.class);
		GenericDAO<ItemPedido> itemPedidoDAO = new GenericDAO<ItemPedido>(db, ItemPedido.class);
		pedidoDAO.save(pedido);
		List<ItemPedido> itensPedidoCopy = new ArrayList<ItemPedido>(pedido.getItensPedido());
		for (ItemPedido ip : itensPedidoCopy) {
			if (ip.getQuantidade() == 0) {
				Log.i(this.getClass().getSimpleName() + ".onOptionsItemSelected", "Removendo item pedido: " + ip);
				pedido.getItensPedido().remove(ip);
				if (ip.getId() > 0) itemPedidoDAO.delete(ip);
			} else {
				Log.i(this.getClass().getSimpleName() + ".onOptionsItemSelected", "Salvando item pedido: " + ip);
				itemPedidoDAO.save(ip);
			}
		}
		Intent irParaCliente = new Intent(this, ClienteActivity.class);
		startActivity(irParaCliente);
		Toast.makeText(this, "O pedido foi salvo!", Toast.LENGTH_SHORT).show();
	}
	
	private void resumeItensPedidos() {
		Cliente clienteInstance = (Cliente) getIntent().getExtras().get("cliente"); 
		Pedido pedidoInstance = (Pedido) getIntent().getExtras().get("pedido"); 
		GenericDAO<Produto> daoProduto = new GenericDAO<Produto>(db, Produto.class);
		if (clienteInstance != null) {
			Log.i(this.getClass().getSimpleName() + ".resumeItensPedidos", "Novo pedido para o cliente " + clienteInstance);
			pedido = new Pedido();
			pedido.setCliente(clienteInstance);
			for (Produto produto : daoProduto.list()) {
				Log.i(this.getClass().getSimpleName() + ".resumeItensPedidos", "Add novo item a lista: " + produto);
				pedido.getItensPedido().add(new ItemPedido(pedido, produto));
			}
		} else if (pedidoInstance != null) {
			Log.i(this.getClass().getSimpleName() + ".resumeItensPedidos", "Alterar pedido " + pedidoInstance);
			for (Produto produto : daoProduto.list()) {
				if (!pedidoInstance.getProdutos().contains(produto)) {
					Log.i(this.getClass().getSimpleName() + ".resumeItensPedidos", "Add novo item a lista: " + produto);
					pedidoInstance.getItensPedido().add(new ItemPedido(pedidoInstance, produto));
				}
			}
			pedido = pedidoInstance;
		}
		Log.i(this.getClass().getSimpleName() + ".resumeItensPedidos", "Pedidos listados" + pedido.getItensPedido());
		pedidoAdapter.setItens(pedido.getItensPedido());
		itensPedidoView.setAdapter(pedidoAdapter);
		total.setText(pedido.getTotal().toString());
	}
	
	private void initCheckPedidos() {
		checkPedidos = (CheckBox) findViewById(R.id.checkPedidos);
		checkPedidos.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
				pedidoAdapter.filterItensVendidos(checked);
			}
		});
	}
	
	private void initAlterarPrazos() {
		alterarPrazos = (Spinner) findViewById(R.id.alterarPrazos);
		GenericDAO<Prazo> prazoDao = new GenericDAO<Prazo>(db, Prazo.class);
		final List<Prazo> list = prazoDao.list();
		ArrayAdapter<Prazo> arrayAdapter = new ArrayAdapter<Prazo>(this, android.R.layout.simple_spinner_item, list);
		alterarPrazos.setAdapter(arrayAdapter);
		alterarPrazos.setActivated(false);
		alterarPrazos.setOnItemSelectedListener(new OnItemSelectedListener() {
			
			private int count = 0;
			
			@Override
			public void onItemSelected(AdapterView<?> adapter, View view, int position, long id) {
				if (count == 0) return;
				count++;
				Prazo prazo = list.get(position);
				for (int i = 0; i < itensPedidoView.getChildCount(); i++) {
					Spinner spPrazo = (Spinner) itensPedidoView.getChildAt(i).findViewById(R.id.prazo);
					spPrazo.setSelection(position);
				}
				for (ItemPedido item : pedido.getItensPedido()) {
					item.setPrazo(prazo);
				}
				itensPedidoView.invalidate();
			}

			@Override
			public void onNothingSelected(AdapterView<?> adapter) {
			}
		});
	}
	
	@SuppressWarnings("rawtypes")
	private ComboList.OnSelectedItemListener filterListener = new ComboList.OnSelectedItemListener() {
		public void onSelectedItem(Object item, int position) {
			pedidoAdapter.filter((Fornecedor)filtroFornecedores.getSelectedItem(), (Grupo)filtroGrupos.getSelectedItem());
		}
	};
	
	@SuppressWarnings("unchecked")
	private void initFiltroFornecedores() {
		filtroFornecedores = (ComboList<Fornecedor>) findViewById(R.id.filtroFornecedores);
		GenericDAO<Fornecedor> forDao = new GenericDAO<Fornecedor>(db, Fornecedor.class);
		final LinkedList<Fornecedor> fornecedoresList = new LinkedList<Fornecedor>(forDao.list());
		final Fornecedor fornecedorNull = new Fornecedor(0, "", "Todos");
		fornecedoresList.addFirst(fornecedorNull);
		filtroFornecedores.setList(fornecedoresList);
		filtroFornecedores.setOnSelectedItemListener(filterListener);
	}

	@SuppressWarnings("unchecked")
	private void initFiltroGrupos() {
		filtroGrupos = (ComboList<Grupo>) findViewById(R.id.filtroGrupos);
		GenericDAO<Grupo> grupoDao = new GenericDAO<Grupo>(db, Grupo.class);
		final LinkedList<Grupo> grupoList = new LinkedList<Grupo>(grupoDao.list());
		final Grupo grupoNull = new Grupo(0, "", "Todos");
		grupoList.addFirst(grupoNull);
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
