package blacksoftware.venda.models;

import java.io.Serializable;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import android.content.Context;
import android.util.Log;
import blacksoftware.venda.config.DatabaseOrm;

@DatabaseTable(tableName="settings")
public class Setting implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1350303011972882282L;
	@DatabaseField(id=true)
	private Integer id;
	@DatabaseField
	private String host;
	@DatabaseField
	private String porta;
	
	public Setting() {
	}
	
	public Setting(Integer id, String host, String porta) {
		this.id = id;
		this.host = host;
		this.porta = porta;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPorta() {
		return porta;
	}
	public void setPorta(String porta) {
		this.porta = porta;
	}
	
	private static String HOST = "104.236.216.219";
	private static String PORTA = "8080";
	
	public static Setting defaultSetting(Context context) {
		try {
			DatabaseOrm db = new DatabaseOrm(context);
			Dao<Setting, Integer> dao = db.getDao(Setting.class);
			Setting settings = dao.queryForId(1);
			if (settings == null) {
				settings = new Setting(1, HOST, PORTA);
				dao.create(settings);
			}
			Log.i("Settings.defaultSettings", "init settings");
			return settings;
		} catch (Exception e) {
			Log.e("Settings.defaultSettings", "erro dao", e);
			return null;
		}
	}
}
