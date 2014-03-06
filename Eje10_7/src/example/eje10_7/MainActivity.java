package example.eje10_7;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	private EditText entrada;
	// Sera accesible des d'altres classes
	public static TextView sortida;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		entrada = (EditText) findViewById(R.id.entrada);
		sortida = (TextView) findViewById(R.id.sortida);
	}
	// Metode ques es crida quan es pitja el boto del Layout
	public void calcularOperacio (View view){
		// Llegeix el valor del edittext
		double n = Double.parseDouble(entrada.getText().toString());
		// Es mostra loperacio a realitzar
		sortida.append(n+"^2 = ");
		// Cream un Intent amb la classe del servei
		Intent i = new Intent(MainActivity.this, ServeiOperacio.class);
		// Afegim un extra amb el valor introduit
		i.putExtra("numero", n);
		// Iniciem el servei
		startService(i);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
