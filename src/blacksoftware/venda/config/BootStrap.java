package blacksoftware.venda.config;

import android.app.Application;

public class BootStrap extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		Fixtures.context = this;
		Fixtures.init();
	}
	
	@Override
	public void onTerminate() {
		super.onTerminate();
	}

}
