package game.pong.elementos;

import android.content.Context;
import android.graphics.Rect;
import game.pong.pintar.PongGameThread;

public class BolaMoveThread extends Thread {

	private Bola bola;
	private Raqueta raquetaIzda;
	private Raqueta raquetaDcha;
	private Rect screen;

	private boolean run;
	private int speed;

	public BolaMoveThread(Bola bola, Raqueta izda, Raqueta dcha, Rect screen) {
		this.bola = bola;
		this.raquetaIzda = izda;
		this.raquetaDcha = dcha;
		this.screen = screen;
		this.run = false;
		this.speed = 1;
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
			}
			bola.move(speed, speed);

		}
	}
}
