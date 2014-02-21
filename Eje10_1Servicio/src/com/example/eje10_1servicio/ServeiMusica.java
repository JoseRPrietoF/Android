package com.example.eje10_1servicio;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;


public class ServeiMusica extends Service {

	MediaPlayer reproductor;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		Toast.makeText(this, "Servei creat", Toast.LENGTH_SHORT).show();
		reproductor = MediaPlayer.create(this, R.raw.audio);
	}

	@Override
	public void onDestroy() {
		Toast.makeText(this, "Servei aturat", Toast.LENGTH_SHORT).show();
		reproductor.stop();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int idArranc) {
		Toast.makeText(this, "Servei arrancat " + idArranc, Toast.LENGTH_SHORT).show();
		reproductor.start();
		return START_STICKY;
	}

}
