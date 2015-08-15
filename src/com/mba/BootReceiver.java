package com.mba;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {

	private static final String TAG = BootReceiver.class.getSimpleName();
	private static final long DEFAULT_INTERVAL = AlarmManager.INTERVAL_FIFTEEN_MINUTES;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		long interval = Long.parseLong(prefs.getString("interval",
				Long.toString(DEFAULT_INTERVAL)));
		PendingIntent operation = PendingIntent.getService(context, -1,
				new Intent(context, RefreshService.class),
				PendingIntent.FLAG_UPDATE_CURRENT);
		AlarmManager manager = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);

		if (interval == 0) {
			manager.cancel(operation);
			Log.d(TAG, "cancelling repeat operation");
		} else {
			manager.setInexactRepeating(AlarmManager.RTC,
					System.currentTimeMillis(), interval, operation);
			Log.d(TAG, "setting repeat operation");
		}
		Log.d("BootReceiver:", "onReceiverd");

	}
}
