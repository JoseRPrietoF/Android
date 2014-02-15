package game.pong.elementos;

import android.graphics.Rect;

public abstract class Elemento {

	protected Coordenada origen;
	protected int ancho;
	protected int alto;

	public Elemento(Coordenada origen, int ancho, int alto) {
		this.origen = origen;
		this.ancho = ancho;
		this.alto = alto;
	}

	public int getOrigenX() {
		return origen.getX();
	}

	public int getOrigenY() {
		return origen.getY();
	}

	public int getAncho() {
		return ancho;
	}

	public int getAlto() {
		return alto;
	}

	public Rect getRectElemento() {
		return new Rect(getOrigenX(), getOrigenY(), getOrigenX() + ancho,
				getOrigenY() + alto);
	}
	
	
	// modificar el origen de las coordenadas
	public void setOrigenY(int newY) {
		origen.setY(newY);
	}
	
	public void setOrigenX(int newX) {
		origen.setX(newX);
	}
}
