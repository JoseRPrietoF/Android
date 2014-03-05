package jose.eje10_6receptor_servei_boot;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class Receptor extends BroadcastReceiver{
	
	@Override
	public void onReceive(Context context, Intent intent) {
		context.startService(new Intent(context, ServeiMusica.class));
		
		// Cream Notificacio
		NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notificacio = new Notification(R.drawable.ic_launcher, "Criada Entrant", System.currentTimeMillis());
		
		PendingIntent intencioPendent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0);
		notificacio.setLatestEventInfo(context, "cridada Entrant", "", intencioPendent);
		nm.notify(1, notificacio);
	}

}
