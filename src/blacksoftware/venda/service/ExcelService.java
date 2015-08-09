package blacksoftware.venda.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import android.content.Context;
import blacksoftware.venda.config.DatabaseOrm;
import blacksoftware.venda.dao.GenericDAO;
import blacksoftware.venda.models.Fornecedor;
import blacksoftware.venda.models.Grupo;
import blacksoftware.venda.models.Produto;

public class ExcelService {
	
	private Context context;
	
	public ExcelService(Context context) {
		this.context = context;
	}
	
	public List<Produto> importProduto(InputStream inputStream) throws IOException {
		DatabaseOrm db = new DatabaseOrm(context);
		GenericDAO<Produto> daoProduto = new GenericDAO<Produto>(db, Produto.class);
		GenericDAO<Fornecedor> daoFornecedor = new GenericDAO<Fornecedor>(db, Fornecedor.class);
		GenericDAO<Grupo> daoGrupo = new GenericDAO<Grupo>(db, Grupo.class);
		List<Produto> produtos = new ArrayList<Produto>();
		Workbook workbook = new HSSFWorkbook(inputStream);
		Sheet sheetProduto = workbook.getSheet("produto");
		Row row;
		int countRow = 0;
		while ((row = sheetProduto.getRow(countRow)) != null) {
			String codigo = row.getCell(0).getStringCellValue();
			String nome = row.getCell(1).getStringCellValue();
			String fornecedor = row.getCell(2).getStringCellValue();
			String grupo = row.getCell(3).getStringCellValue();
			Fornecedor fornecedorInstance = daoFornecedor.saveOrUpdateForField(new Fornecedor(0, "", fornecedor), "nome", fornecedor);
			Grupo grupoInstance = daoGrupo.saveOrUpdateForField(new Grupo(0, "", grupo), "nome", grupo);
			Produto produto = new Produto(0, codigo, nome, fornecedorInstance, grupoInstance);
			produto = daoProduto.saveOrUpdateForField(produto, "codigo", codigo);
			produtos.add(produto);
		}
		workbook.close();
		inputStream.close();
		return produtos;
	}
}
