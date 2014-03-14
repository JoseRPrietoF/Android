package game.pong;

import opciones.Opciones;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.widget.Toast;

public class PongOpcionesActivity extends PreferenceActivity {

	boolean musica;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferencies);
		
		SharedPreferences pref = 
				getSharedPreferences("game.pong.opciones_preferences", 
				Context.MODE_PRIVATE);
		
		musica = pref.getBoolean("sonido_key", false);
		Toast.makeText(this, ""+musica, Toast.LENGTH_SHORT).show();
		if (musica){
			Opciones.getInstance().setSonido(true);
		}else {
			Opciones.getInstance().setSonido(false);
		}
		
		//Toast.makeText(context, text, duration)
		//Opciones.getInstance().setSonido(true);
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
