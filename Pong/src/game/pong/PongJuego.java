package game.pong;

import game.pong.pintar.PongGameView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class PongJuego extends Activity {

	private PongGameView view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		if (getIntent().getExtras() != null) {
			// Se recogen puntos de la ultima partida
			int puntosIzda = getIntent().getExtras().getInt("PuntosIzda");
			int puntosDcha = getIntent().getExtras().getInt("PuntosDcha");
			if (puntosIzda == Marcador.MAX_PUNT || puntosDcha == Marcador.MAX_PUNT)
				view = new PongGameView(this , 0, 0); // Si alguno ya habia llegado al maximo
			else
				view = new PongGameView(this, puntosIzda,
						puntosDcha);
		} else
			view = new PongGameView(this, 0, 0);

		setContentView(view);
	}

	// Si pulsamos "atras"
	@Override
	public void onBackPressed() {
		Bundle bundle = new Bundle();
		bundle.putInt("PuntosIzda", view.getMarcador().getPuntosIzda());
		bundle.putInt("PuntosDcha", view.getMarcador().getPuntosDcha());

		Intent mIntent = new Intent();
		mIntent.putExtras(bundle);
		setResult(Activity.RESULT_OK, mIntent);
		super.onBackPressed();
	}

}
