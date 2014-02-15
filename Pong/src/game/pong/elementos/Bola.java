package game.pong.elementos;

import android.graphics.Rect;

public class Bola extends Elemento implements MoverElemento {

	// La bola solo se mover� con los �ngulos de 45�, 135�, 225� y 315�)
	// principales angulos
	public static final int DCHA_ARRIBA = 1;
	public static final int IZDA_ARRIBA = 2;
	public static final int IZDA_ABAJO = 3;
	public static final int DCHA_ABAJO = 4;

	private int direccion;

	public Bola(Coordenada origen, int ancho, int alto) {
		super(origen, ancho, alto);
		direccion = 1;
	}

	@Override
	public void move(int x, int y) {
		switch (direccion) {
		case DCHA_ARRIBA:
			origen.setX(origen.getX() + x);
			origen.setY(origen.getY() - y);
			break;
		case IZDA_ARRIBA:
			origen.setX(origen.getX() - x);
			origen.setY(origen.getY() - y);
			break;
		case IZDA_ABAJO:
			origen.setX(origen.getX() - x);
			origen.setY(origen.getY() + y);
			break;
		case DCHA_ABAJO:
			origen.setX(origen.getX() + x);
			origen.setY(origen.getY() + y);
			break;
		}

	}

	// Comprovamos si podemos mover
	public boolean canMove(int x, int y, Rect screen, Rect raquetaIzda,
			Rect raquetaDcha) {
		if (!canMove(x, y, screen))
			return false;
		if (hit(x, y, raquetaIzda))
			return false;
		if (hit(x, y, raquetaDcha))
			return false;

		return true;
	}

	// Comprovamos si chocamos, las 4 direcciones posibles
	// Sumamos para preveer el movimiento
	private boolean hit(int x, int y, Rect raqueta) {
		if (raqueta.contains(origen.getX() + x, origen.getY() + y))
			return true;
		if (raqueta.contains(origen.getX() + ancho + x, origen.getY() + y))
			return true;
		if (raqueta.contains(origen.getX() + x, origen.getY() + alto + y))
			return true;
		if (raqueta.contains(origen.getX() + ancho + x, origen.getY() + alto
				+ y))
			return true;

		return false;
	}

	// M�todo para rebotar la bola
	public void rebota(int x, int y, Rect screen, Rect raquetaIzda,
			Rect raquetaDcha) {
		// Bordes de pantalla
		if (!canMove(x, y, screen)) {
			switch (direccion) {
			case DCHA_ARRIBA:
				direccion = (origen.getY() - y <= screen.top) ? DCHA_ABAJO
						: IZDA_ARRIBA;
				break;
			case IZDA_ARRIBA:
				direccion = (origen.getY() - y <= screen.top) ? IZDA_ABAJO
						: DCHA_ARRIBA;
				break;
			case IZDA_ABAJO:
				direccion = (origen.getY() + alto + y >= screen.bottom) ? IZDA_ARRIBA
						: DCHA_ABAJO;
				break;
			case DCHA_ABAJO:
				direccion = (origen.getY() + alto + y >= screen.bottom) ? DCHA_ARRIBA
						: IZDA_ABAJO;
				break;
			}
		}
		// Vigilamos si choca con la raqueta
		Rect raqueta = null;
		if (hit(x, y, raquetaIzda)) // Si choca con las raquetas
			raqueta = raquetaIzda;
		if (hit(x, y, raquetaDcha))
			raqueta = raquetaDcha;
		if (raqueta != null) { // Si hemos elegido una raqueta
			switch (direccion) {
			case DCHA_ARRIBA:
				direccion = (origen.getX() + ancho < raqueta.left) ? IZDA_ARRIBA
						: DCHA_ABAJO;
				break;
			case IZDA_ARRIBA:
				direccion = (origen.getX() > raqueta.right) ? DCHA_ARRIBA
						: IZDA_ABAJO;
				break;
			case IZDA_ABAJO:
				direccion = (origen.getX() > raqueta.right) ? IZDA_ARRIBA
						: DCHA_ABAJO;
				break;
			case DCHA_ABAJO:
				direccion = (origen.getX() + ancho < raqueta.left) ? IZDA_ABAJO
						: DCHA_ARRIBA;
				break;
			}
		}
	}
}
