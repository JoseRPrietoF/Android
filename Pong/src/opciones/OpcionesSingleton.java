package opciones;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;

// Clase donde utilizaremos el patron Singleton para usar preferencias
public class OpcionesSingleton {

	private static final String MUSICA = "music";
	private static final String VIBRATION = "vibration";
	private static final String SPEED = "speed";
	private static final String SIZE = "size";
	private static final String COLOR_RAQUETAS = "colorRaqueta";

	private boolean sonido;
	private boolean vibracion;
	private int speedBall = 5;
	private int tamanyRaquetes = 100;
	private String colorRaquetas = "";
	// menos = mas rapida

	private static OpcionesSingleton opciones = null;
	private OnSharedPreferenceChangeListener mListener;
	private SharedPreferences mPrefs;
	private Context mContext;

	private OpcionesSingleton(Context context) {
		this.sonido = true;
		this.vibracion = true;
		mContext = context.getApplicationContext();
		mPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);
		// Attach a listener to update summary when username changes
		mListener = new OnSharedPreferenceChangeListener() {
			@Override
			public void onSharedPreferenceChanged(
					SharedPreferences sharedPreferences, String key) {
				readPrefs();
			}
		};

		// Register a listener on the SharedPreferences object
		mPrefs.registerOnSharedPreferenceChangeListener(mListener);
		// Invoke callback manually to update fields
		mListener.onSharedPreferenceChanged(null, null);
	}

	public void readPrefs() {
		sonido = mPrefs.getBoolean(MUSICA, true);
		vibracion = mPrefs.getBoolean(VIBRATION, false);
		String str = mPrefs.getString(SPEED, null);
		speedBall = Integer.valueOf(str);
		String hodor = mPrefs.getString(SIZE, null);
		tamanyRaquetes = Integer.valueOf(hodor);
		setColorRaquetas(mPrefs.getString(COLOR_RAQUETAS, "Cyan"));
	}

	public static synchronized OpcionesSingleton getInstance(Context context) {
		if (opciones == null)
			opciones = new OpcionesSingleton(context);
		return opciones;
	}

	public boolean isSonido() {
		return sonido;
	}

	public void setSonido(boolean sonido) {
		this.sonido = sonido;
	}

	public boolean isVibracion() {
		return vibracion;
	}

	public void setVibracion(boolean vibracion) {
		this.vibracion = vibracion;
	}

	public int getSpeedBall() {
		return speedBall;
	}

	public void setSpeedBall(int speedBall) {
		this.speedBall = speedBall;
	}

	public int getTamanyRaquetes() {
		return tamanyRaquetes;
	}

	public void setTamanyRaquetes(int tamanyRaquetes) {
		this.tamanyRaquetes = tamanyRaquetes;
	}

	public String getColorRaquetas() {
		return colorRaquetas;
	}

	public void setColorRaquetas(String colorRaquetas) {
		this.colorRaquetas = colorRaquetas;
	}

}
