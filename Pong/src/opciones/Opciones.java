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

	public void toggleSound() {
		sonido = !sonido;
	}

	public void toggleVibration() {
		vibracion = !vibracion;
	}

	// Devuelve el estado 
	public boolean soundEnabled() {
		return sonido;
	}

	public boolean vibrationEnabled() {
		return vibracion;
	}
}
