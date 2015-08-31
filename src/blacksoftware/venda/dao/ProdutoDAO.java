package blacksoftware.venda.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.stmt.QueryBuilder;

import android.util.Log;
import blacksoftware.venda.config.DatabaseOrm;
import blacksoftware.venda.models.Produto;

public class ProdutoDAO extends GenericDAO<Produto> {

	private static final long serialVersionUID = 5480892554071927800L;
	
	public ProdutoDAO(DatabaseOrm db) {
		this(db, Produto.class);
	}
	
	private ProdutoDAO(DatabaseOrm db, Class<Produto> clazz) {
		super(db, clazz);
	}

	public List<Produto> criteriaFornecedorAndGrupo(String fornecedor, String grupo) {
		try {
			QueryBuilder<Produto, Integer> query = getDao().queryBuilder();
			if (fornecedor != null) query.where().eq("fornecedor", fornecedor);
			if (grupo != null) query.where().eq("grupo", grupo);
			if (fornecedor != null && grupo != null)
				query.where().eq("fornecedor", fornecedor).and().eq("grupo", grupo);
			Log.i("ProdutoDAO.criteriaFornecedorAndGrupo", query.prepareStatementString());
			return getDao().query(query.prepare());
		} catch (SQLException e) {
			Log.e(getClass().getName() + ".criteriaFornecedorAndGrupo", "");
			return null;
		}
	}
	
	
	public List<String> findFornecedores() {
		try {
			Log.i("ProdutoDAO.findFornecedores", "");
			String[] params = {};
			GenericRawResults<String[]> queryRaw = getDao().queryRaw("select distinct fornecedor from produto", params);
			List<String> fornecedores = new ArrayList<String>();
			for (String[] values : queryRaw.getResults()) {
				fornecedores.add(values[0]);
			}
			return fornecedores;
		} catch (SQLException e) {
			Log.e(getClass().getName() + ".findFornecedores", "");
			return null;
		}
	}
	
	public List<String> findGrupos() {
		try {
			Log.i("ProdutoDAO.findGrupos", "");
			String[] params = {};
			GenericRawResults<String[]> queryRaw = getDao().queryRaw("select distinct grupo from produto", params);
			List<String> grupos = new ArrayList<String>();
			for (String[] values : queryRaw.getResults()) {
				grupos.add(values[0]);
			}
			return grupos;
		} catch (SQLException e) {
			Log.e(getClass().getName() + ".findGrupos", "");
			return null;
		}
	}
	
	public List<String> findGruposByFornecedor(String fornecedor) {
		try {
			Log.i(getClass().getSimpleName() + ".findGruposByFornecedor", "");
			GenericRawResults<String[]> queryRaw = getDao().queryRaw("select distinct grupo from produto where fornecedor = ?", fornecedor);
			List<String> grupos = new ArrayList<String>();
			for (String[] values : queryRaw.getResults()) {
				grupos.add(values[0]);
			}
			return grupos;
		} catch (SQLException e) {
			Log.e(getClass().getName() + ".findGruposByFornecedor", "");
			return null;
		}
	}
}
