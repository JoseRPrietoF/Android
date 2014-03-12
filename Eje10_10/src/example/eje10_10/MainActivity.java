package example.eje10_10;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	private IServeiMusica servei;
	private ServiceConnection connexio = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			servei = null;
			Toast.makeText(MainActivity.this, "Connectat a Servei",
					Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder iservei) {
			servei = IServeiMusica.Stub.asInterface(iservei);
			Toast.makeText(MainActivity.this, "Connectat a Servei",
					Toast.LENGTH_SHORT).show();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Obte l'objecte Vista
		Button botoConnectar = (Button) findViewById(R.id.arrancar);
		Button botoReproduir = (Button) findViewById(R.id.reproduir);
		Button botoAvansar = (Button) findViewById(R.id.avan);
		Button botoDesconnectar = (Button) findViewById(R.id.aturar);
		Button aturar = (Button) findViewById(R.id.stop);
		// Configurar escoltador del boto Connectar
		botoConnectar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MainActivity.this.bindService(new Intent(MainActivity.this,
						ServeiRemot.class), connexio, Context.BIND_AUTO_CREATE);
			}
		});
		// Configurar escoltador del boto Connectar
		aturar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					servei.atura();
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		});
		// Configurar escoltador del boto Reproduir
		botoReproduir.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					servei.reprodueix("titol");
				} catch (Exception e) {
					Toast.makeText(MainActivity.this, e.toString(),
							Toast.LENGTH_SHORT).show();
				}

			}
		});
		// Configurar escoltador del boto avançar
		botoAvansar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					servei.setPosicio(servei.getposicio() + 10000);
				} catch (Exception e) {
					Toast.makeText(MainActivity.this, e.toString(),
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		// Configura escoltador del boto desconnectar
		botoDesconnectar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					servei.atura();
					MainActivity.this.unbindService(connexio);
				} catch (Exception e) {
					Toast.makeText(MainActivity.this, e.toString(),
							Toast.LENGTH_SHORT).show();
				}
				servei = null;
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
