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
import blacksoftware.venda.models.Cliente;
import blacksoftware.venda.models.Produto;
import blacksoftware.venda.models.Unidade;
import blacksoftware.venda.models.enums.Situacao;
import blacksoftware.venda.util.Util;

public class ExcelService {
	
	private Context context;
	private DatabaseOrm db;
	
	public ExcelService(Context context, DatabaseOrm db) {
		super();
		this.context = context;
		this.db = db;
	}

	public void importData(InputStream inputStream) throws IOException {
		GenericDAO<Produto> daoProduto = new GenericDAO<Produto>(db, Produto.class);
		GenericDAO<Unidade> daoUnidade = new GenericDAO<Unidade>(db, Unidade.class);
		Workbook workbook = new HSSFWorkbook(inputStream);
		if (daoProduto.count()  == 0)
			importProduto(workbook);
		if (daoUnidade.count()  == 0)
			importUnidade(workbook);
		importCliente(workbook);
		
		workbook.close();
		inputStream.close();
		db.close();
	}
	
	public void importCliente(Workbook workbook) throws IOException {
		GenericDAO<Cliente> dao = new GenericDAO<Cliente>(db, Cliente.class);
		Sheet sheet = workbook.getSheet("clientes");
		Row row;
		int length = 219;
		Log.i("ExcelService.importCliente", "Carregando Sheet Cliente");
		for (int i = 2; i <= length; i++) {
			try {
				row = sheet.getRow(i);
				Integer id = converterInt(row.getCell(1));
				String codigo = Util.preencherTexto(8, id.toString());
				String cnpj = converterString(row.getCell(3));
				String nomeFantasia = converterString(row.getCell(4));
				String inscricaoEstadual = converterString(row.getCell(5));
				String endereco = converterString(row.getCell(6));
				String numero = converterString(row.getCell(7));
				String complemento = converterString(row.getCell(8));
				String bairro = converterString(row.getCell(9));
				String telefone = converterString(row.getCell(16));
				
				String nome1;
				String nome2;
				String[] params = nomeFantasia.split(" - ");
				if (params.length == 2) {
					nome1 = params[0];
					nome2 = params[1];
				} else {
					params = nomeFantasia.split("- ");
					if (params.length == 2) {
						nome1 = params[0];
						nome2 = params[1];
					} else if (!nomeFantasia.isEmpty()) {
						nome1 = nome2 = nomeFantasia;
					} else {
						throw new Exception();
					}
				}
				Cliente cliente = new Cliente(id, codigo, cnpj, nome1, nome2, endereco + " " + numero, bairro, complemento, "Barcarena", inscricaoEstadual, Situacao.EM_DIA, 5, BigDecimal.ZERO, telefone, "", "", "");
				dao.save(cliente);
				Log.i("ExcelService.importCliente", "Cliente carregado: " + cliente.toString());
			} catch (Exception e) {
				Log.e("ExcelService.importUnidade", "Wrong Line: " + (i + 1), e);
				return;
			}
		}
		Log.i("ExcelService.importCliente", "Sheet Cliente Carregado");
		workbook.close();
	}
	
	public void importProduto(Workbook workbook) throws IOException {
		GenericDAO<Produto> dao = new GenericDAO<Produto>(db, Produto.class);
		Sheet sheet = workbook.getSheet("produto");
		Row row = sheet.getRow(0);
		int length = 134;
		Log.i("ExcelService.importProduto", "Carregando Sheet Produto");
		for (int i = 1; i <= length; i++) {
			try {
				row = sheet.getRow(i);
				Integer id = Integer.valueOf(row.getCell(0).getStringCellValue());
				String codigo = row.getCell(1).getStringCellValue().toUpperCase(Locale.getDefault());
				String nome = row.getCell(2).getStringCellValue().toUpperCase(Locale.getDefault());
				String fornecedor = row.getCell(3).getStringCellValue().toUpperCase(Locale.getDefault());
				String grupo = row.getCell(4).getStringCellValue().toUpperCase(Locale.getDefault());
				Produto produto = new Produto(id, codigo, nome, fornecedor, grupo);
				dao.save(produto);
				Log.i("ExcelService.importProduto", "Produto carregado: " + produto.toString());
			} catch (Exception e) {
				Log.e("ExcelService.importUnidade", "Wrong Line: " + i, e);
			}
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
			try {
				int id = converterInt(row.getCell(0));
				int idProduto = converterInt(row.getCell(1));
				String tipo = row.getCell(2).getStringCellValue();
				double valor = row.getCell(3).getNumericCellValue();
				double valorMinimo = row.getCell(4).getNumericCellValue();
				Unidade unidade = new Unidade(id, daoProduto.get(idProduto), tipo, new BigDecimal(valor).setScale(2, RoundingMode.UP), new BigDecimal(valorMinimo).setScale(2, RoundingMode.UP), 0);
				daoUnidade.save(unidade);
				Log.i("ExcelService.importUnidade", "Produto carregado: " + unidade.toString());
			} catch (Exception e) {
				Log.e("ExcelService.importUnidade", "Wrong Line: " + i, e);
			}
		}
		Log.i("ExcelService.importProduto", "Sheet Precos Carregado");
	}
	
	private int converterInt(Cell cell) {
		switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_STRING:
				return Integer.valueOf(cell.getStringCellValue());
			case HSSFCell.CELL_TYPE_NUMERIC:
				return (int) cell.getNumericCellValue();
			case HSSFCell.CELL_TYPE_BLANK:
				return 0;
			default:
				new RuntimeException("Outro tipo");
		}
		return 0;
	}
	
	private String converterString(Cell cell) {
		if (cell == null) return "";
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING:
			return cell.getStringCellValue().toUpperCase(Locale.getDefault());
		case HSSFCell.CELL_TYPE_NUMERIC:
			return Double.toString(cell.getNumericCellValue()).toUpperCase(Locale.getDefault());
		case HSSFCell.CELL_TYPE_BLANK:
			return "";
		default:
			new RuntimeException("Outro tipo");
		}
		return "";
	}
}
