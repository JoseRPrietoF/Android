package com.example.eje10_1servicio;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;


public class ServeiMusica extends Service {

	MediaPlayer reproductor;
	private NotificationManager nm;
	private static final int ID_NOTIFICACIO_CREAR = 1;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		Toast.makeText(this, "Servei creat", Toast.LENGTH_SHORT).show();
		reproductor = MediaPlayer.create(this, R.raw.menorca);
		nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
	}

	@Override
	public void onDestroy() {
		Toast.makeText(this, "Servei aturat", Toast.LENGTH_SHORT).show();
		reproductor.stop();
		nm.cancel(ID_NOTIFICACIO_CREAR);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int idArranc) {
		Toast.makeText(this, "Servei arrancat " + idArranc, Toast.LENGTH_SHORT).show();
		reproductor.start();
		Notification notificacio = new Notification(R.drawable.ic_launcher, "Crat Servei de Música",
				System.currentTimeMillis());
		// Informacio adicional
		PendingIntent intencioPendent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
		notificacio.setLatestEventInfo(this, "Reproduint Música", "Informació addicional", intencioPendent);
		// Pasa la notificacio creada al NM
		nm.notify(ID_NOTIFICACIO_CREAR,notificacio);
		return START_STICKY;
	}

}
