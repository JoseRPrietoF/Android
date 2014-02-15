package game.pong;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	private TextView jugar, opciones, salir;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		jugar = (TextView)findViewById(R.id.jugar);
		jugar.setOnClickListener(this);
		opciones = (TextView)findViewById(R.id.opciones);
		opciones.setOnClickListener(this);
		salir = (TextView)findViewById(R.id.salir);
		salir.setOnClickListener(this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	// metode sense emprar classe interna
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.jugar:
				lanzarJuego(null);
				break;
			case R.id.opciones:
				//llancarPreferencias(null);
				break;
			case R.id.salir:
				//llancarSobre(null);
				break;
		}
	}

	
	public void lanzarJuego(View view){
		 Intent juego = new Intent(this, PongJuego.class);
		 this.startActivity(juego);
	}
	
	public void salir(View view) {
		finish();
	}
	
}
