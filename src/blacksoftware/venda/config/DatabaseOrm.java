package blacksoftware.venda.config;

import java.sql.SQLException;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import blacksoftware.venda.models.Cliente;
import blacksoftware.venda.models.Cobranca;
import blacksoftware.venda.models.ItemCobranca;
import blacksoftware.venda.models.ItemPedido;
import blacksoftware.venda.models.Pedido;
import blacksoftware.venda.models.Prazo;
import blacksoftware.venda.models.Produto;
import blacksoftware.venda.models.Unidade;
import blacksoftware.venda.models.Usuario;

public class DatabaseOrm extends OrmLiteSqliteOpenHelper {

	private static final String DATABASE = "venda.db";
	private static final int VERSION = 1;
	
	public DatabaseOrm(Context context) {
		super(context, DATABASE, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			TableUtils.createTable(connectionSource, Cliente.class);
			TableUtils.createTable(connectionSource, Produto.class);
			TableUtils.createTable(connectionSource, Pedido.class);
			TableUtils.createTable(connectionSource, ItemPedido.class);
			TableUtils.createTable(connectionSource, Unidade.class);
			TableUtils.createTable(connectionSource, Prazo.class);
			TableUtils.createTable(connectionSource, Cobranca.class);
			TableUtils.createTable(connectionSource, ItemCobranca.class);
			TableUtils.createTable(connectionSource, Usuario.class);
		} catch (SQLException e) {
			Log.e(this.getClass().getName(), "Unable to create databases", e);
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, ConnectionSource arg1, int arg2, int arg3) {
	}
	
}
