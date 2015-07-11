package blacksoftware.venda.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.util.SparseArray;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import blacksoftware.venda.R;
import blacksoftware.venda.models.Cliente;

public class ClienteFormHelper {
	public void setClienteView(Cliente cliente, SparseArray<TextView> sparseArray, RatingBar rating) {
		sparseArray.get(R.id.codigo).setText(cliente.getCodigo());
		sparseArray.get(R.id.fantasia).setText(cliente.getNomeFantasia());
		sparseArray.get(R.id.cnpj).setText(cliente.getCnpj());
		sparseArray.get(R.id.razaoSocial).setText(cliente.getRazaoSocial());
		sparseArray.get(R.id.endereco).setText(cliente.getEndereco());
		sparseArray.get(R.id.bairro).setText(cliente.getBairro());
		sparseArray.get(R.id.referencia).setText(cliente.getReferencia());
		sparseArray.get(R.id.cidade).setText(cliente.getCidade());
		sparseArray.get(R.id.insEstadual).setText(cliente.getInscricaoEstadual());
		sparseArray.get(R.id.limite).setText(cliente.getLimite() + "");
		sparseArray.get(R.id.telefone).setText(cliente.getTelefone());
		sparseArray.get(R.id.responsavel).setText(cliente.getResponsavel());
		sparseArray.get(R.id.situacao).setText(cliente.getSituacao().toString());
		sparseArray.get(R.id.canal).setText(cliente.getCanal());
		sparseArray.get(R.id.ramo).setText(cliente.getRamo());
		rating.setRating(cliente.getRate());
	}
	
	public void setClientesView(Activity context, List<Cliente> lista, ListView clientes) {
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		for (Cliente c : lista) {
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("content", c);
			String subcontent = new StringBuilder(c.getEndereco())
				.append(" - ")
				.append(c.getBairro())
				.append(" - ")
				.append(c.getCidade())
				.toString();
			item.put("subcontent", subcontent);
			data.add(item);
		}
		SimpleAdapter simpleAdapter = new SimpleAdapter(context, data, android.R.layout.simple_expandable_list_item_2,
				new String[] { "content", "subcontent" }, new int[] { android.R.id.text1, android.R.id.text2 });
		clientes.setAdapter(simpleAdapter);
	}
	
	public void initDescricaoView(Activity context, SparseArray<TextView> clienteDescricao) {
		clienteDescricao.put(R.id.codigo, (TextView) context.findViewById(R.id.codigo));
		clienteDescricao.put(R.id.fantasia, (TextView) context.findViewById(R.id.fantasia));
		clienteDescricao.put(R.id.cnpj, (TextView) context.findViewById(R.id.cnpj));
		clienteDescricao.put(R.id.razaoSocial, (TextView) context.findViewById(R.id.razaoSocial));
		clienteDescricao.put(R.id.endereco, (TextView) context.findViewById(R.id.endereco));
		clienteDescricao.put(R.id.bairro, (TextView) context.findViewById(R.id.bairro));
		clienteDescricao.put(R.id.referencia, (TextView) context.findViewById(R.id.referencia));
		clienteDescricao.put(R.id.cidade, (TextView) context.findViewById(R.id.cidade));
		clienteDescricao.put(R.id.insEstadual, (TextView) context.findViewById(R.id.insEstadual));
		clienteDescricao.put(R.id.situacao, (TextView) context.findViewById(R.id.situacao));
		clienteDescricao.put(R.id.limite, (TextView) context.findViewById(R.id.limite));
		clienteDescricao.put(R.id.telefone, (TextView) context.findViewById(R.id.telefone));
		clienteDescricao.put(R.id.responsavel, (TextView) context.findViewById(R.id.responsavel));
		clienteDescricao.put(R.id.canal, (TextView) context.findViewById(R.id.canal));
		clienteDescricao.put(R.id.ramo, (TextView) context.findViewById(R.id.ramo));
	}
}
