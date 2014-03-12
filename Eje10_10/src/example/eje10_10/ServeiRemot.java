package example.eje10_10;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.RemoteException;

public class ServeiRemot extends Service {
	MediaPlayer reproductor;
	
	public void onCreate() {
		super.onCreate();
		reproductor = MediaPlayer.create(ServeiRemot.this, R.raw.audio);
	}
	
	private final IServeiMusica.Stub binder = new IServeiMusica.Stub() {
		
		// Parametres de entrada i sortida no terne tilidad, s'han indicat per mostrar un exemple de dada primitiva.
		@Override
		public void setPosicio(int ms) throws RemoteException {
			reproductor.seekTo(ms);
		}
		
		@Override
		public String reprodueix(String missatge) throws RemoteException {
			reproductor.start();
			return missatge;
		}
		
		@Override
		public int getposicio() throws RemoteException {
			return reproductor.getCurrentPosition();
		}

		@Override
		public void atura() throws RemoteException {
			reproductor.stop();
		}

	};
	
	@Override
	public IBinder onBind(Intent intent) {
		// Retorna un objecte de la classe que implementa la nostra ainterficie
		return binder;
	}

}
