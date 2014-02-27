package com.example.eje10_1servicio;
import android.annotation.SuppressLint;
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
		nm.cancel(ID_NOTIFICACIO_CREAR); // Elimina la notificacio creada
	}

	@SuppressLint("NewApi")
	@Override
	public int onStartCommand(Intent intent, int flags, int idArranc) {
		Toast.makeText(this, "Servei arrancat " + idArranc, Toast.LENGTH_SHORT).show();
		//reproductor.start();
		
		Notification.Builder builder = new Notification.Builder(this);
		builder.setContentTitle("Creant servei de música")
								.setSmallIcon(R.drawable.ic_launcher);
		Notification n = builder.build();
		// Informacio adicional
		PendingIntent intencioPendent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
		n.setLatestEventInfo(this, "Reproduint Música", "Informació addicional", intencioPendent);
		// Pasa la notificacio creada al NM
		n.defaults |= Notification.DEFAULT_SOUND;
		n.defaults |= Notification.DEFAULT_VIBRATE;
		
		//LED
		n.defaults |= Notification.DEFAULT_LIGHTS;
		n.ledARGB = 0x00ff00; // color verd
		n.ledOnMS = 300;
		n.ledOffMS = 1000;
		n.flags |= Notification.FLAG_SHOW_LIGHTS;
		nm.notify(ID_NOTIFICACIO_CREAR,n);
		return START_STICKY;
	}

}
