package game.pong.elementos;

public class Raqueta extends Elemento implements MoverElemento {
	
	public Raqueta(Coordenada origen, int ancho, int alto) {
		super(origen, ancho, alto);
	
	}

	@Override
	public void move(int x, int y) {
		origen.setY(origen.getY() + y); // Se Suma a si misma
		// Como no queremos mover X, no creamos este.
	}
	
	
}
