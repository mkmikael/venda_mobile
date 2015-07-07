package blacksoftware.venda.config;

import blacksoftware.venda.models.Canal;
import blacksoftware.venda.models.Cliente;
import blacksoftware.venda.models.Ramo;
import blacksoftware.venda.models.Situacao;
import co.uk.rushorm.android.RushAndroid;
import android.app.Application;

public class BootStrap extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		RushAndroid.initialize(getApplicationContext());
//		Ramo ramo = new Ramo("Ramo");
//		ramo.save();
//		Canal canal = new Canal("Canal");
//		canal.save();
//		Cliente cliente = new Cliente("123123123", "MAGNUS CLUB SHOW", "razionado", "Rua Laurival Cunha 382", "1231233", Situacao.EM_DIA, 5f, 1000.0d, "98888-4444", "Mikael Lima", canal, ramo);
//		cliente.save();
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}
	
}
