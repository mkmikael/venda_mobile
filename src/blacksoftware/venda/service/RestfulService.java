package blacksoftware.venda.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.j256.ormlite.dao.Dao;

import android.content.Context;
import android.util.Log;
import blacksoftware.venda.config.DatabaseOrm;
import blacksoftware.venda.models.Cliente;
import blacksoftware.venda.models.ItemPedido;
import blacksoftware.venda.models.Pedido;
import blacksoftware.venda.models.Prazo;
import blacksoftware.venda.models.Produto;
import blacksoftware.venda.models.Unidade;

public class RestfulService {

	private DatabaseOrm db;
	private RequestQueue requestQueue;
	private String host = "192.168.100.9";
	private String port = "8080";
	
	public RestfulService(Context context) {
		db = new DatabaseOrm(context);
		requestQueue = Volley.newRequestQueue(context);
	}

	public void load() {
		loadProdutos();
		loadPrazos();
		loadClientes();
		requestQueue.start();
	}
	
	public void enviarPedidos() {
			String url = "http://" + host + ":" + port + "/web-agil/pedido/savePedidos";
			StringRequest request = new StringRequest(Request.Method.POST, url, 
					new Listener<String>() {
				@Override
				public void onResponse(String responseString) {
					try {
						Dao<Pedido, Integer> dao = db.getDao(Pedido.class);
						JSONArray response = new JSONArray(responseString);
						for (int i = 0; i < response.length(); i++) {
							JSONObject params = response.getJSONObject(i);
							Pedido pedido = dao.queryForId(params.getInt("id"));
							pedido.setCodigo(params.getString("codigo"));
							pedido.setStatusPedido(Pedido.StatusPedido.SINCRONIZADO);
							dao.update(pedido);
						}
					} catch (Exception e) {
						Log.e("RestService.enviarPedidos", "erro no dao", e);
					}
					Log.i("RestService.enviarPedidos", responseString);
				}
			}, // end inner class 
					new ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					if (error != null) {
						Log.e("Erro conection", error.toString());
					} else {
						Log.e("Erro conection", "sem message");
					}
				}
			}) { // end inner class and begin other
				@Override
				protected Map<String, String> getParams() throws AuthFailureError {
					return new HashMap<String, String>() {
					private static final long serialVersionUID = 1L;
					{
						try {
							Dao<Pedido, Integer> dao = db.getDao(Pedido.class);
							List<Pedido> pedidoList = dao.queryForAll();
							JsonArray pedidosJA = new JsonArray();
							for (Pedido pedido : pedidoList) {
								JsonObject pedidoJO = new JsonObject();
								pedidosJA.add(pedidoJO);
								
								pedidoJO.addProperty("id", pedido.getId());
								pedidoJO.addProperty("dataCriacao", pedido.getDataCriacao().toString());
								pedidoJO.addProperty("dataDeFaturamento", pedido.getDataDeFaturamento().toString());
								pedidoJO.addProperty("cliente", pedido.getCliente().getId());
								pedidoJO.addProperty("total", pedido.getTotal());
								pedidoJO.addProperty("prazo", pedido.getPrazo().getId());
								
								JsonArray itensJA = new JsonArray();
								pedidoJO.add("itensPedido", itensJA);
								for (final ItemPedido item : pedido.getItensPedido()) {
									JsonObject itemJO = new JsonObject();
									itemJO.addProperty("bonificacao", item.getBonificacao());
									itemJO.addProperty("desconto", item.getDesconto());
									itemJO.addProperty("precoNegociado", item.getPrecoNegociado());
									itemJO.addProperty("quantidade", item.getQuantidade());
									itemJO.addProperty("total", item.getTotal());
									itemJO.addProperty("unidade", item.getUnidade().getTipo());
									itemJO.addProperty("produto", item.getProduto().getId());
									itensJA.add(itemJO);
								}
							}
							
							Log.i("RestService.enviarPedidos", pedidosJA.toString());
							put("pedidos", pedidosJA.toString());
						} catch (Exception e) {
							Log.e("RestService.enviarPedidos", "erro no dao", e);
						}
					}};
				} //end getParams

			}; //end inner class
			requestQueue.add(request);
			requestQueue.start();
	}
	
	public void loadPrazos() {
		String url = "http://" + host + ":" + port + "/web-agil/prazo/listJson";
		JsonArrayRequest request = new JsonArrayRequest(url, 
				new Listener<JSONArray>() {
			@Override
			public void onResponse(JSONArray array) {
				try {
					Dao<Prazo, Integer> dao = db.getDao(Prazo.class);
					Gson builder = new GsonBuilder().create();
					for (int i = 0; i < array.length(); i++) {
						Prazo prazo = builder.fromJson(array.getJSONObject(i).toString(), Prazo.class);
						if (dao.idExists(prazo.getId())) {
							dao.update(prazo);
						} else
							dao.create(prazo);
						Log.i("RestService.loadPrazos", prazo.toString());
					}
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		}, 
				new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Log.e("Erro conection", error.getMessage());
			}
		});
		
		requestQueue.add(request);
	}
	
	public void loadProdutos() {
		String url = "http://" + host + ":" + port + "/web-agil/produto/listJson";
		JsonArrayRequest request = new JsonArrayRequest(url, 
			new Listener<JSONArray>() {
				@Override
				public void onResponse(JSONArray array) {
					System.out.println(array);
					try {
						Dao<Produto, Integer> daoProduto = db.getDao(Produto.class);
						Dao<Unidade, Integer> daoUnidade = db.getDao(Unidade.class);
						Gson builder = new GsonBuilder().create();
						daoUnidade.executeRawNoArgs("delete from unidade");
						for (int i = 0; i < array.length(); i++) {
							Produto produto = builder.fromJson(array.getJSONObject(i).toString(), Produto.class);
							if (daoProduto.idExists(produto.getId())) {
								daoProduto.update(produto);
							} else {
								daoProduto.create(produto);
							}
							for (Unidade u : produto.getUnidades()) {
								daoUnidade.create(u);
							}
							Log.i("RestService.loadProdutos", produto.toString());
						} // end for
					} catch (Exception e) {
						Log.e("RestService.loadProdutos", "erro", e);
					}
				}
			}, 
			new ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					Log.e("Erro conection", error.getMessage());
				}
			});
		
		requestQueue.add(request);
	}
	
	public void loadClientes() {
		String url = "http://" + host + ":" + port + "/web-agil/cliente/listJson";
		JsonArrayRequest request = new JsonArrayRequest(url, 
			new Listener<JSONArray>() {
				@Override
				public void onResponse(JSONArray array) {
					try {
						Dao<Cliente, Integer> dao = db.getDao(Cliente.class);
						Gson builder = new GsonBuilder().create();
						for (int i = 0; i < array.length(); i++) {
							Cliente cliente = builder.fromJson(array.getJSONObject(i).toString(), Cliente.class);
							if (dao.idExists(cliente.getId()))
								dao.update(cliente);
							else
								dao.create(cliente);
							Log.i("RestService.loadClientes", cliente.toString());
						}
					} catch (Exception e) {
						System.out.println(e);
					}
				}
			}, 
			new ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					Log.e("Erro conection", error.getMessage());
				}
			});
		
		requestQueue.add(request);
	}
	
}
