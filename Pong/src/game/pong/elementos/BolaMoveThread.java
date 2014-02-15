package game.pong.elementos;

import opciones.Opciones;
import android.content.Context;
import android.graphics.Rect;
import android.os.Vibrator;
import game.pong.pintar.PongGameThread;

public class BolaMoveThread extends Thread {

	private Bola bola;
	private Raqueta raquetaIzda;
	private Raqueta raquetaDcha;
	private Rect screen;

	private boolean run;
	private int speed;
	
	private Vibrator v = null;

	public BolaMoveThread(Bola bola, Raqueta izda, Raqueta dcha, 
			Rect screen, Context context) {
		this.bola = bola;
		this.raquetaIzda = izda;
		this.raquetaDcha = dcha;
		this.screen = screen;
		this.run = false;
		this.speed = 1;
		this.v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
	}

	public void setRunning(boolean run) {
		this.run = run;
	}

	@Override
	public void run() {
		while (run) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// Si nno se puede mover, se rebotará
			if (!bola.canMove(speed, speed, screen,
					raquetaIzda.getRectElemento(),
					raquetaDcha.getRectElemento())){
				bola.rebota(speed, speed, screen,
						raquetaIzda.getRectElemento(),
						raquetaDcha.getRectElemento());
				// Se añade vibración para cuando la bola golpee una raqueta
				if (bola.canMove(speed, speed, screen)
						&& Opciones.getInstance().vibrationEnabled())
					v.vibrate(50);
			}
			bola.move(speed, speed);

		}
	}
}
