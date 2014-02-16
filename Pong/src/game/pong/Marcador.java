package game.pong;

public class Marcador {

	public static final int MAX_PUNT = 9;

	private int puntosIzda;
	private int puntosDcha;

	public void initMarcador() {
		puntosIzda = 0;
		puntosDcha = 0;
	}

	public Marcador() {
		initMarcador(); // Reinicia puntos justo crear objeto
	}
	
	public Marcador(int i, int d) { // por una partida sin axabar
		puntosIzda = i;
		puntosDcha = d;
	}

	public int getPuntosIzda() {
		return puntosIzda;
	}

	public int getPuntosDcha() {
		return puntosDcha;
	}

	public void addPuntoIzda() {
		puntosIzda++;
	}

	public void addPuntoDcha() {
		puntosDcha++;
	}

	public boolean acabado() {
		// Mira si algun lado ha llegado al máximo de puntos
		return puntosDcha == MAX_PUNT || puntosIzda == MAX_PUNT;
	}
}
