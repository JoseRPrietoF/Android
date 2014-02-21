package com.example.eje10_1servicio;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button arrancar = (Button) findViewById(R.id.arrancar);
		arrancar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startService(new Intent (MainActivity.this,
													ServeiMusica.class));
			}
		});
		
		Button aturar = (Button)findViewById(R.id.aturar);
		aturar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				stopService( new Intent(MainActivity.this,
											ServeiMusica.class));
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
