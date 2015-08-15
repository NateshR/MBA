package com.mba;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationReceiver extends BroadcastReceiver {
	private static final int Notification_ID = 42;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		int count = intent.getIntExtra("count", 0);
		PendingIntent operation = PendingIntent.getActivity(context, -1,
				new Intent(context, MainActivity.class),
				PendingIntent.FLAG_ONE_SHOT);
		@SuppressWarnings("deprecation")
		Notification notification = new Notification.Builder(context)
				.setContentTitle("New Tweets")
				.setContentText("You've got " + count + " new tweets")
				.setSmallIcon(android.R.drawable.sym_action_email)
				.setContentIntent(operation).setAutoCancel(true)
				.getNotification();
		notificationManager.notify(Notification_ID, notification);

	}

}