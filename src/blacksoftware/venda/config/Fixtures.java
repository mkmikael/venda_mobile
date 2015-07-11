package blacksoftware.venda.config;

import java.math.BigDecimal;
import java.util.Date;

import blacksoftware.venda.models.Cliente;
import blacksoftware.venda.models.Pedido;
import blacksoftware.venda.models.enums.Situacao;

public class Fixtures {
	public static Cliente createCliente() {
		return new Cliente(0, "00000000001", "123123123", "MAGNUS CLUB SHOW", "razionado", "Rua Laurival Cunha 382", "Centro", "Prox a antena da telepara", "Barcarena", "1231233", Situacao.EM_DIA, 5f, new BigDecimal(1000), "98888-4444", "Mikael Lima", "VAREJO", "ATACADO");
	}
	
	public static Pedido createPedido(Cliente cliente) {
		return new Pedido(0, "000001", cliente, new Date(), new Date(), BigDecimal.TEN);
	}
}
