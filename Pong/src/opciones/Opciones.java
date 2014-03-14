package opciones;


// Clase donde utilizaremos el patron Singleton para usar preferencias
public class Opciones {

	private boolean sonido;
	private boolean vibracion;
	private static Opciones opciones = null;
	
	private Opciones() {
		this.sonido = true;
		this.vibracion = true;
	}
	
	public static synchronized Opciones getInstance() {
		if(opciones == null)
			opciones = new Opciones();
		return opciones;
	}
	// Devuelve el estado 
	public boolean soundEnabled() {
		return sonido;
	}

	public boolean vibrationEnabled() {
		return vibracion;
	}

	public void setSonido(boolean sonido) {
		this.sonido = sonido;
	}

	public void setVibracion(boolean vibracion) {
		this.vibracion = vibracion;
	}
}
