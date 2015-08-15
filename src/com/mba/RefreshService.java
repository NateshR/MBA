package com.mba;

import java.util.List;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.content.Intent;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClient.Status;
import com.marakana.android.yamba.clientlib.YambaClientException;

public class RefreshService extends IntentService {

	final static String TAG = "RefreshService";

	public RefreshService() {
		super(TAG);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.d(TAG, "onCreated");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		super.onStartCommand(intent, flags, startId);
		Log.d(TAG, "onStarted");
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.d(TAG, "onDestroyed");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		final String userName = prefs.getString("username", "");
		final String passWord = prefs.getString("password", "");

		if (TextUtils.isEmpty(userName) || (TextUtils.isEmpty(passWord))) {
			Toast.makeText(this, "Please update your username and password",
					Toast.LENGTH_SHORT).show();
			return;
		}
		Log.d(TAG, "onStarted");

		/* DbHelper dbHelper = new DbHelper(this); */
		/* SQLiteDatabase db = dbHelper.getWritableDatabase(); */
		ContentValues cv = new ContentValues();

		YambaClient cloud = new YambaClient(userName, passWord);
		try {
			List<Status> timeLine = cloud.getTimeline(20);
			int count = 0;
			for (Status status : timeLine) {

				cv.clear();
				cv.put(StatusContract.Columns.ID, status.getId());
				cv.put(StatusContract.Columns.USER, status.getUser());
				cv.put(StatusContract.Columns.MESSAGE, status.getMessage());
				cv.put(StatusContract.Columns.CREATED_AT, status.getCreatedAt()
						.getTime());
				Uri uri = getContentResolver().insert(
						StatusContract.CONTENT_URI, cv);
				if (uri != null) {
					count++;
					Log.d(TAG,
							String.format("%s: %s", status.getUser(),
									status.getMessage()));
				}
				/*
				 * db.insertWithOnConflict(StatusContract.TABLE, null, cv,
				 * SQLiteDatabase.CONFLICT_IGNORE);
				 */
				if (count > 0) {
					sendBroadcast(new Intent("com.mba.action.NEW_STAUTSES")
							.putExtra("count", count));
				}

			}
		} catch (YambaClientException e) {
			Log.e(TAG, "Failed to fetch timeline", e);
			e.printStackTrace();
		}
	}
}
