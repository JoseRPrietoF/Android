package com.example.eje10_1servicio;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button arrancar = (Button) findViewById(R.id.arrancar);
		arrancar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startService(new Intent(MainActivity.this, ServeiMusica.class));
			}
		});

		Button aturar = (Button) findViewById(R.id.aturar);
		aturar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				stopService(new Intent(MainActivity.this, ServeiMusica.class));
			}
		});
		Button sos = (Button) findViewById(R.id.sos);
		sos.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				notificacio();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void notificacio() {
		// Cream Notificacio
		int t = 400, l = 500, c = 1500;
		long [] patron = {0, l, t, l, t, l, t, c, t , c, t, c, t, l, t , l, t, l, t};
		Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);
		NotificationManager nm = (NotificationManager) this
				.getSystemService(this.NOTIFICATION_SERVICE);
		Notification notificacio = new Notification(R.drawable.ic_launcher,
				"Criada Entrant", System.currentTimeMillis());

		PendingIntent intencioPendent = PendingIntent.getActivity(this, 0,
				new Intent(this, MainActivity.class), 0);
		notificacio.setLatestEventInfo(this, "! Socors ¡", "",
				intencioPendent);
		try {
			Thread.sleep(5000);
			nm.notify(1, notificacio);
			v.vibrate(patron,3);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
