package com.mba;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

	public DbHelper(Context context) {
		super(context, StatusContract.DB_NAME, null, StatusContract.DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(" CREATE TABLE " + StatusContract.TABLE + " ( "
				+ StatusContract.Columns.ID + " INTEGER PRIMARY KEY, "
				+ StatusContract.Columns.USER + " TEXT, "
				+ StatusContract.Columns.MESSAGE + " TEXT, "
				+ StatusContract.Columns.CREATED_AT + " INTEGER ); ");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL(" DROP TABLE IF EXISTS " + StatusContract.TABLE);
		onCreate(db);
	}

}
