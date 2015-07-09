package blacksoftware.venda.config;

import blacksoftware.venda.models.Cliente;
import blacksoftware.venda.models.Situacao;
import co.uk.rushorm.core.RushSearch;

public class Fixtures {
	public static void execute() {
		Cliente cliente = new Cliente("00000000001", "123123123", "MAGNUS CLUB SHOW", "razionado", "Rua Laurival Cunha 382", "Centro", "Prox a antena da telepara", "Barcarena", "1231233", Situacao.EM_DIA, 5f, 1000.0d, "98888-4444", "Mikael Lima", "VAREJO", "ATACADO");
		if (new RushSearch().whereEqual("cnpj", cliente.getCnpj()).findSingle(Cliente.class) == null)
			cliente.save();
	}
}
