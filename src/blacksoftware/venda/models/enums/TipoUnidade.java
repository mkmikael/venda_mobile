package blacksoftware.venda.models.enums;

import java.util.Arrays;
import java.util.List;

public enum TipoUnidade {
	CXA, UNI;
	
	public static String[] array() {
		return new String[]{ CXA.name(), UNI.name() };
	}
	
	public static List<String> list() {
		return Arrays.<String>asList(array()); 
	}
}
