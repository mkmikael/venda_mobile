package blacksoftware.venda.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Util {

	public static BigDecimal round(BigDecimal bigDecimal) {
		return bigDecimal.divide(BigDecimal.ONE, 2, RoundingMode.UP);
	}
	
	public static String roundToString(BigDecimal bigDecimal) {
		return round(bigDecimal).toString();
	}
}
