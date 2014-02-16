package game.pong;

import opciones.Opciones;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class PongOpcionesActivity extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferencies);
		
		SharedPreferences pref = 
				getSharedPreferences("Opciones", 
				Context.MODE_PRIVATE);

		Opciones.getInstance().setSonido(pref.getBoolean("sonido_key",false));
		/*
		CheckBox vibracion = (CheckBox) findViewById(R.id.checkBoxVibracion);
		vibracion.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Opciones.getInstance().toggleVibration();
			}
		});

		sonido.setChecked(Opciones.getInstance().soundEnabled());
		vibracion.setChecked(Opciones.getInstance().vibrationEnabled());*/
	}
	
}
