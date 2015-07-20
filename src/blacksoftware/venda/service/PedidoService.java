package blacksoftware.venda.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PedidoService {

	public BigDecimal calcularTotal(int quantidade, BigDecimal preco, BigDecimal desconto) {
		try {
			if (preco == null) preco = BigDecimal.ZERO;
			if (desconto == null) desconto = BigDecimal.ZERO;
			return preco.multiply(BigDecimal.ONE.subtract(desconto.divide(BigDecimal.valueOf(100))))
					.multiply(new BigDecimal(quantidade)).divide(BigDecimal.ONE, 2, RoundingMode.UP);
		} catch (Exception e) {
			e.printStackTrace();
			return BigDecimal.ZERO;
		}
	}
	
	public BigDecimal calcularUnitario(int quantidade, BigDecimal preco, BigDecimal desconto, int bonificacao) {
		try {
			return calcularTotal(quantidade, preco, desconto).divide(new BigDecimal(quantidade + bonificacao), 2, RoundingMode.UP);
		} catch (Exception e) {
			e.printStackTrace();
			return BigDecimal.ZERO;
		}
	}
}
