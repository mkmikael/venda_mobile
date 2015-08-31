package blacksoftware.venda.service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import android.content.Context;
import android.util.Log;
import blacksoftware.venda.config.DatabaseOrm;
import blacksoftware.venda.dao.GenericDAO;
import blacksoftware.venda.models.Produto;
import blacksoftware.venda.models.Unidade;

public class ExcelService {
	
	private Context context;
	private DatabaseOrm db;
	
	public ExcelService(Context context, DatabaseOrm db) {
		super();
		this.context = context;
		this.db = db;
	}

	public void importData(InputStream inputStream) throws IOException {
		DatabaseOrm db = new DatabaseOrm(context);
		Workbook workbook = new HSSFWorkbook(inputStream);
		
		importProduto(workbook);
		importUnidade(workbook);
		
		inputStream.close();
		workbook.close();
		db.close();
	}
	
	public void importProduto(Workbook workbook) throws IOException {
		GenericDAO<Produto> dao = new GenericDAO<Produto>(db, Produto.class);
		Sheet sheet = workbook.getSheet("produto");
		Row row = sheet.getRow(0);
		int length = 134;
		Log.i("ExcelService.importProduto", "Carregando Sheet Produto");
		for (int i = 1; i <= length; i++) {
			row = sheet.getRow(i);
			Integer id = Integer.valueOf(row.getCell(0).getStringCellValue());
			String codigo = row.getCell(1).getStringCellValue().toUpperCase(Locale.getDefault());
			String nome = row.getCell(2).getStringCellValue().toUpperCase(Locale.getDefault());
			String fornecedor = row.getCell(3).getStringCellValue().toUpperCase(Locale.getDefault());
			String grupo = row.getCell(4).getStringCellValue().toUpperCase(Locale.getDefault());
			Produto produto = new Produto(id, codigo, nome, fornecedor, grupo);
			dao.save(produto);
			Log.i("ExcelService.importProduto", "Produto carregado: " + produto.toString());
		}
		Log.i("ExcelService.importProduto", "Sheet Produto Carregado");
	}
	
	public void importUnidade(Workbook workbook) {
		DatabaseOrm db = new DatabaseOrm(context);
		GenericDAO<Produto> daoProduto = new GenericDAO<Produto>(db, Produto.class);
		GenericDAO<Unidade> daoUnidade = new GenericDAO<Unidade>(db, Unidade.class);
		Sheet sheet = workbook.getSheet("precos");
		Row row = sheet.getRow(0);
		int length = 181;
		Log.i("ExcelService.importProduto", "Carregando Sheet Precos");
		for (int i = 1; i <= length; i++) {
			row = sheet.getRow(i);
			int id = (int) row.getCell(0).getNumericCellValue();
			int idProduto = converter(row.getCell(1));
			String tipo = row.getCell(2).getStringCellValue();
			double valor = row.getCell(3).getNumericCellValue();
			double valorMinimo = row.getCell(4).getNumericCellValue();
			Unidade unidade = new Unidade(id, daoProduto.get(idProduto), tipo, new BigDecimal(valor).setScale(2, RoundingMode.UP), new BigDecimal(valorMinimo).setScale(2, RoundingMode.UP), 0);
			daoUnidade.save(unidade);
			Log.i("ExcelService.importUnidade", "Produto carregado: " + unidade.toString());
		}
		Log.i("ExcelService.importProduto", "Sheet Precos Carregado");
	}
	
	private int converter(Cell cell) {
		switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_STRING:
				return Integer.valueOf(cell.getStringCellValue());
			case HSSFCell.CELL_TYPE_NUMERIC:
				return (int) cell.getNumericCellValue();
			default:
				new RuntimeException("Outro tipo");
		}
		return 0;
	}
}
