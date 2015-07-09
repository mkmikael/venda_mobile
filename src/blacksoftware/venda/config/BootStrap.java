package blacksoftware.venda.config;

import android.app.Application;
import co.uk.rushorm.android.AndroidInitializeConfig;
import co.uk.rushorm.core.RushCore;

public class BootStrap extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		AndroidInitializeConfig androidInitializeConfig = new AndroidInitializeConfig(getApplicationContext());
		RushCore.initialize(androidInitializeConfig);
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}

}
