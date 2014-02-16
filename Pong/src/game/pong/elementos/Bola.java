package game.pong.elementos;

import android.graphics.Rect;

public class Bola extends Elemento implements MoverElemento {

	// La bola solo se moverá con los ángulos de 45º, 135º, 225º y 315º)
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

	// Método para rebotar la bola
	// Devolverá -1 si la bola entró por la izquierda, 
	//1 si entró por la derecha y 0 si sigue en la pantalla.
	public int rebota(int x, int y, Rect screen, Rect raquetaIzda,
			Rect raquetaDcha) {
		// Bordes de pantalla
		if(!canMove(x,y,screen)) {
			switch(direccion) {
			case DCHA_ARRIBA:
				if(origen.getY() - y <= screen.top)
					direccion = DCHA_ABAJO;
				else
					return 1; // Entro por la derecha
				break;
			case IZDA_ARRIBA:
				if(origen.getY() - y <= screen.top)
					direccion = IZDA_ABAJO;
				else
					return -1; // Entro por la izquierda
				break;
			case IZDA_ABAJO:
				if(origen.getY() + alto + y >= screen.bottom)
					direccion = IZDA_ARRIBA;
				else
					return -1; // Entro por la izquierda
				break;
			case DCHA_ABAJO:
				if(origen.getY() + alto + y >= screen.bottom)
					direccion = DCHA_ARRIBA;
				else
					return 1; // Entro por la derecha
				break;
			}
		}
		// Vigilamos si choca con la raqueta
		// Si choca con la raqueta, seguira la bola en pantalla
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
		
		return 0; // La bola seguira en la pantalla
	}
}
