package example.eje10_7;

import example.eje10_7.MainActivity.ReceptorOperacio;
import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;

public class IntentServiceOperacio extends IntentService {

	public IntentServiceOperacio() {
		super("IntentServiceoperacio");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		double n = intent.getExtras().getDouble("numero");
		SystemClock.sleep(5000);
		//MainActivity.sortida.append(n*n + "\n");
		// llansa launci broadcast enviant una dada en el extra
		Intent in= new Intent();
		in.setAction(ReceptorOperacio.ACTION_RESP);
		in.addCategory(Intent.CATEGORY_DEFAULT);
		in.putExtra("resultat", n*n);
		sendBroadcast(in);
	}

}
