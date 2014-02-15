package game.pong;

import opciones.Opciones;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;

public class PongOpcionesActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.opciones);
		
		CheckBox sonido = (CheckBox) findViewById(R.id.checkBoxSonido);
		sonido.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Opciones.getInstance().toggleSound();
			}
		});

		CheckBox vibracion = (CheckBox) findViewById(R.id.checkBoxVibracion);
		vibracion.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Opciones.getInstance().toggleVibration();
			}
		});

		sonido.setChecked(Opciones.getInstance().soundEnabled());
		vibracion.setChecked(Opciones.getInstance().vibrationEnabled());
	}
	
}
