package game.pong;

import opciones.Opciones;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	private TextView jugar, opciones, salir;

	// Musica --------------------//
	private MediaPlayer mp;
	int pos;

	// ------------------------------//

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		jugar = (TextView) findViewById(R.id.jugar);
		jugar.setOnClickListener(this);
		opciones = (TextView) findViewById(R.id.opciones);
		opciones.setOnClickListener(this);
		salir = (TextView) findViewById(R.id.salir);
		salir.setOnClickListener(this);
		if (Opciones.getInstance().soundEnabled()){
			mp = MediaPlayer.create(this, R.raw.audio );
			mp.start();
		}
		

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
			lanzarOpciones(null);
			break;
		case R.id.salir:
			salir(null);
			break;
		}
	}
	
	public void lanzarOpciones(View view) {
		Intent opciones = new Intent(this, PongOpcionesActivity.class);
		this.startActivity(opciones);
	}

	public void lanzarJuego(View view) {
		Intent juego = new Intent(this, PongJuego.class);
		this.startActivity(juego);
	}

	public void salir(View view) {
		finish();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		if (Opciones.getInstance().soundEnabled()){
			mp.pause();
			pos=mp.getCurrentPosition();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (Opciones.getInstance().soundEnabled()){
			mp.seekTo(pos);
			mp.start();	
		}
	}
	
	@Override
	protected void onSaveInstanceState(Bundle guardarEstat) {
		super.onSaveInstanceState(guardarEstat);
		guardarEstat.putInt("posicio", pos);
	}
	@Override
	protected void onRestoreInstanceState(Bundle recEstat) {
		super.onRestoreInstanceState(recEstat);
		pos=recEstat.getInt("posicio");
	}

}
