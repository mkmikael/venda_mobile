package blacksoftware.venda.service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import blacksoftware.venda.models.Produto;

public class RestfulService {
	public void importProduto(JSONObject jsonObject) throws JSONException {
		JSONArray produtoListJSON = jsonObject.getJSONArray("produto");
		for (int i = 0; i < produtoListJSON.length(); i++) {
			JSONObject produtoJSON = produtoListJSON.getJSONObject(i);
			Produto produto = new Produto();
			if (!produtoJSON.isNull("id")) produto.setId(produtoJSON.getInt("id"));
			if (!produtoJSON.isNull("codigo")) produto.setCodigo(produtoJSON.getString("codigo"));
			if (!produtoJSON.isNull("nome")) produto.setNome(produtoJSON.getString("nome"));
			if (!produtoJSON.isNull("fornecedor")) produto.setFornecedor(produtoJSON.getString("fornecedor"));
			if (!produtoJSON.isNull("grupo")) produto.setGrupo(produtoJSON.getString("grupo"));
			System.out.println(produto);
		}
	}
}
