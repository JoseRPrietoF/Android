package game.pong.elementos;

import game.pong.Marcador;
import opciones.Opciones;
import android.content.Context;
import android.graphics.Rect;
import android.os.Vibrator;

public class BolaMoveThread extends Thread {

	private Bola bola;
	private Raqueta raquetaIzda;
	private Raqueta raquetaDcha;
	private Rect screen;

	private boolean run;
	private int speed = 1;
	
	private Vibrator v = null;
	
	// Partes del marcador
	private boolean punto;
	private Marcador marcador;
	// Para poder reiniciar la bola despues de hacer punto
	private int bolaInitX;
	private int bolaInitY;

	public BolaMoveThread(Bola bola, Raqueta izda, Raqueta dcha, 
			Rect screen, Context context, Marcador marcador) {
		this.bola = bola;
		this.bolaInitX = bola.getOrigenX();
		this.bolaInitY = bola.getOrigenY();
		this.raquetaIzda = izda;
		this.raquetaDcha = dcha;
		this.screen = screen;
		this.run = false;
		this.marcador = marcador;
		this.punto = false;
		this.v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
	}

	public void setRunning(boolean run) {
		this.run = run;
	}

	@Override
	public void run() {
		while (run) {
			try {
				Thread.sleep(5); // duerme 5ms cada movimiento,
				// Si restamos, aumenta la velocidad.
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// Si es punto se para aqui, si ha llegado al maximo
			// No seguira el juego hatsa volver del menu principal
			if(punto) {
				try {
					Thread.sleep(2000); // Si es punto, duerme 2segundos
					// Entonces la bola no se mueve
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(!marcador.acabado()) {
					punto = false;
				}
				continue;
				// Sigue si se pueden hacer mas puntos
			}
			// Si nno se puede mover, se rebotará
			if (!bola.canMove(speed, speed, screen,
					raquetaIzda.getRectElemento(),
					raquetaDcha.getRectElemento())){
				// Se añade vibración para cuando la bola golpee una raqueta
				switch(bola.rebota(speed, speed, screen,
						raquetaIzda.getRectElemento(), raquetaDcha.getRectElemento())) {
				case 0: // Bola rebota en raqueta y sigue:
					if(bola.canMove(speed, speed, screen) &&
							Opciones.getInstance().vibrationEnabled())
							v.vibrate(50);
					break;
				case -1: // Entro por la Izda, punto para derecha
					marcador.addPuntoDcha();
					reiniciarBola();
					punto = true;
					break;
				case 1: // Entro por la Dcha, punto para Izda
					marcador.addPuntoIzda();
					reiniciarBola();
					punto = true;
					break;
				}
			}
			bola.move(speed, speed);

		}
	}
	
	// Devuelve la bola a una posicion inicial
	private void reiniciarBola() {
		bola.setOrigenX(bolaInitX);
		bola.setOrigenY(bolaInitY);
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	
}
