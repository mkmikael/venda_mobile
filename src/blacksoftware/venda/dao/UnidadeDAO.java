package blacksoftware.venda.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.dao.GenericRawResults;

import android.util.Log;
import blacksoftware.venda.config.DatabaseOrm;
import blacksoftware.venda.models.Unidade;

public class UnidadeDAO extends GenericDAO<Unidade> {

	private static final long serialVersionUID = -1942683687777079054L;

	public UnidadeDAO(DatabaseOrm db) {
		super(db, Unidade.class);
	}

	public List<String> findTipos() {
		try {
			Log.i("Unidade.findTipos", "");
			String[] params = {};
			GenericRawResults<String[]> queryRaw = getDao().queryRaw("select distinct tipo from unidade", params);
			List<String> fornecedores = new ArrayList<String>();
			for (String[] values : queryRaw.getResults()) {
				fornecedores.add(values[0]);
			}
			return fornecedores;
		} catch (SQLException e) {
			Log.e("Unidade.findFornecedores", "", e);
			return null;
		}
	}
}
