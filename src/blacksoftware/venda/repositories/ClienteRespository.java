package blacksoftware.venda.repositories;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import blacksoftware.venda.config.Database;
import blacksoftware.venda.models.Cliente;
import blacksoftware.venda.models.enums.Situacao;

public class ClienteRespository implements CrudRepository<Cliente> {

	private Database db;
	
	public ClienteRespository(Database db) {
		this.db = db;
	}
	
	@Override
	public void save(Cliente cliente) {
		SQLiteDatabase wr = db.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("codigo", cliente.getCodigo());
		values.put("cnpj", cliente.getCnpj());
		values.put("nome_fantasia", cliente.getNomeFantasia());
		values.put("razao_social", cliente.getRazaoSocial());
		values.put("endereco", cliente.getEndereco());
		values.put("bairro", cliente.getBairro());
		values.put("referencia", cliente.getReferencia());
		values.put("cidade", cliente.getCidade());
		values.put("inscricao_estadual", cliente.getInscricaoEstadual());
		values.put("rate", cliente.getRate());
		values.put("telefone", cliente.getTelefone());
		values.put("responsavel", cliente.getResponsavel());
		values.put("canal", cliente.getCanal());
		values.put("ramo", cliente.getRamo());
		if (cliente.getSituacao() != null) values.put("situacao", cliente.getSituacao().name());
		if (cliente.getLimite() != null) values.put("limite", cliente.getLimite().doubleValue());
		
		if (cliente.getId() == 0)
			wr.insert("cliente", null, values);
		else
			wr.update("cliente", values, "id = ?", new String[] { cliente.getId() + "" });
	}

	@Override
	public void delete(Cliente cliente) {
		SQLiteDatabase wr = db.getWritableDatabase();
		wr.delete("cliente", "id = ?", new String[] { cliente.getId() + "" });
	}

	@Override
	public Cliente get(Object id) {
		SQLiteDatabase wr = db.getWritableDatabase();
		ContentValues values = new ContentValues();
		String[] columns = {"id", "codigo", "cnpj", "nome_fantasia", "razao_social",
				"endereco", "bairro", "referencia", "cidade", "inscricao_estadual",
				"rate", "telefone", "responsavel", "canal", "ramo", "situacao", "limite" };
		String[] args = { id.toString() };
		Cursor cursor = wr.query("cliente", columns, "id = ? ", args, null, null, null);
		cursor.moveToFirst();
		Cliente cliente = getCliente(cursor);
		cursor.close();
		return cliente;
	}

	@Override
	public List<Cliente> list() {
		SQLiteDatabase wr = db.getWritableDatabase();
		ContentValues values = new ContentValues();
		String[] columns = { "id", "codigo", "cnpj", "nome_fantasia", "razao_social",
				"endereco", "bairro", "referencia", "cidade", "inscricao_estadual",
				"rate", "telefone", "responsavel", "canal", "ramo", "situacao", "limite" };
		Cursor cursor = wr.query("cliente", columns, null, null, null, null, "nome_fantasia");
		List<Cliente> clientes = new ArrayList<Cliente>();	
		while (cursor.moveToNext()) {
			clientes.add(getCliente(cursor));
		}
		cursor.close();
		return clientes;
	}

	private Cliente getCliente(Cursor cursor) {
		Cliente cliente = new Cliente(cursor.getInt(0));
		cliente.setCodigo(cursor.getString(1));
		cliente.setCnpj(cursor.getString(2));
		cliente.setNomeFantasia(cursor.getString(3));
		cliente.setRazaoSocial(cursor.getString(4));
		cliente.setEndereco(cursor.getString(5));
		cliente.setBairro(cursor.getString(6));
		cliente.setReferencia(cursor.getString(7));
		cliente.setCidade(cursor.getString(8));
		cliente.setInscricaoEstadual(cursor.getString(9));
		cliente.setRate(cursor.getFloat(10));
		cliente.setTelefone(cursor.getString(11));
		cliente.setResponsavel(cursor.getString(12));
		cliente.setCanal(cursor.getString(13));
		cliente.setRamo(cursor.getString(14));
		cliente.setSituacao(Situacao.valueOf(cursor.getString(15)));
		cliente.setLimite(new BigDecimal(cursor.getDouble(16)));
		return cliente;
	}
}
