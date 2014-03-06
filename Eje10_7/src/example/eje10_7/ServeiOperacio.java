package example.eje10_7;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

public class ServeiOperacio extends Service {

	public int onStartCommand(Intent i, int flags, int idArranc) {
		// Obte el valor extra
		double n = i.getExtras().getDouble("numero");
		//Aturem l'execucio durant 5 segons simulant operacins de calcul
		SystemClock.sleep(5000);
		// Mostra el resultat del calcul en el TextView
		MainActivity.sortida.append(n*n + "\n");
		// Si el sistema atura el servei no lha de tornar a crear
		return START_NOT_STICKY;
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
