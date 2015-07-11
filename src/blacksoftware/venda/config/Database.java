package blacksoftware.venda.config;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Database extends SQLiteOpenHelper {

	private static String BANCO_DADOS = "venda.db";
	private static int VERSAO = 1;
	
	public Database(Context context) {
		super(context, BANCO_DADOS, null, VERSAO);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.i("DATABASE", "Criando tabelas...");
		db.execSQL("CREATE TABLE cliente (id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "codigo TEXT, nome TEXT, cnpj TEXT, nome_fantasia,"
				+ "razao_social TEXT, endereco TEXT,"
				+ "bairro TEXT, referencia TEXT, cidade TEXT,"
				+ "inscricao_estadual TEXT, situacao TEXT,"
				+ "rate float, limite DOUBLE, telefone TEXT,"
				+ "responsavel TEXT, canal TEXT, ramo TEXT);");
		
		db.execSQL("CREATE TABLE produto(id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "codigo TEXT, nome TEXT, fornecedor TEXT,"
				+ "grupo TEXT, embalagem TEXT, estoque DOUBLE,"
				+ "preco DOUBLE, preco_minimo DOUBLE, bonificacao DOUBLE);");
		
		db.execSQL("CREATE TABLE pedido(id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "codigo TEXT, dataCriacao DATE, cliente_id INTEGER"
				+ "data_de_faturamento DATE, total DOUBLE,"
				+ "FOREIGN key(cliente_id) REFERENCES cliente(id));");
		
		db.execSQL("CREATE TABLE item_pedido(id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "prazo INTEGER, quantidade DOUBLE, produto_id INTEGER"
				+ "total DOUBLE, desconto DOUBLE,"
				+ "FOREIGN key(produto_id) REFERENCES produto(id));");
		Log.i("DATABASE", "Tabelas criadas!");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
	}

}
