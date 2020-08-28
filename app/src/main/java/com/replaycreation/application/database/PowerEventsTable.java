package com.replaycreation.application.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class PowerEventsTable extends SQLiteOpenHelper {

	Context context;
	public static final String DATABASE_NAME = "ChargedOrDischarged.db";
	private static final int DATABASE_VERSION = 1;

	public static final String COLUMN_BATTERY_IS_CHARGING = "isCharging";
	public static final String COLUMN_EVENT_TIME = "eventTime";
	public static final String COLUMN_ID = "column_id";
	public static final String COLUMN_INITIAL_PERCENTAGE = "initial_percentage";
	public static final String COLUMN_FINAL_PERCENTAGE = "final_percentage";
	public static final String COLUMN_TIMING_DURATION= "timing_duration";
	public static final String COLUMN_DATE = "date";
	public static final String COLUMN_CONNECT_TIME = "time";
	public static final String COLUMN_DISCONNECT_TIME = "disconnect_time";



	public static final String TABLE_NAME = "powerEvents";
	private static final String TABLE_CREATE = "CREATE TABLE IF NOT EXISTS " +
			PowerEventsTable.TABLE_NAME + "( " +
			PowerEventsTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			PowerEventsTable.COLUMN_INITIAL_PERCENTAGE + " VARCHAR, " +
			PowerEventsTable.COLUMN_FINAL_PERCENTAGE + " VARCHAR, " +
			PowerEventsTable.COLUMN_TIMING_DURATION + " VARCHAR, " +
			PowerEventsTable.COLUMN_DATE + " VARCHAR, " +
			PowerEventsTable.COLUMN_CONNECT_TIME + " VARCHAR, " +
			PowerEventsTable.COLUMN_DISCONNECT_TIME + " VARCHAR, " +
			PowerEventsTable.COLUMN_BATTERY_IS_CHARGING + " VARCHAR );";


	private static final String TABLE_DROP = "DROP TABLE IF EXISTS " + PowerEventsTable.TABLE_NAME + ";";

	public PowerEventsTable(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context=context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL( PowerEventsTable.TABLE_CREATE );
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

//	public static void onCreate( final SQLiteDatabase db ) {
//		db.execSQL( PowerEventsTable.TABLE_CREATE );
//	}
//
//	public static void onUpgrade( final SQLiteDatabase db, final int oldVersion, final int newVersion ) {
//		// the only upgrade option is to delete the old database...
//		db.execSQL( PowerEventsTable.TABLE_DROP );
//		// ... and to create a new one
//		db.execSQL( PowerEventsTable.TABLE_CREATE );
//	}
//
//	public static void onDowngrade( SQLiteDatabase db, int oldVersion, int newVersion ) {
//		// the only downgrade option is to delete the old database...
//		db.execSQL( PowerEventsTable.TABLE_DROP );
//		// ... and to create a new one
//		db.execSQL( PowerEventsTable.TABLE_CREATE );
//	}


	public boolean insert(String initialTime,String date, String time, String isCharging) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(COLUMN_INITIAL_PERCENTAGE, initialTime);
		contentValues.put(COLUMN_DATE, date);
		contentValues.put(COLUMN_CONNECT_TIME, time);
		contentValues.put(COLUMN_BATTERY_IS_CHARGING, isCharging);
		db.insert(TABLE_NAME, null, contentValues);
		return true;
	}

	public boolean update(String finalPercentage,String disconnectTime) {
		SQLiteDatabase db = this.getWritableDatabase();
		String SELECT_SQL = "SELECT * FROM " + TABLE_NAME;
		Cursor c =  db.rawQuery(SELECT_SQL,null);
		c.moveToLast();
		String tableId = null;
		try{
			tableId = c.getString(0);
		}catch (Exception e){

		}
		ContentValues contentValues = new ContentValues();
		contentValues.put(COLUMN_FINAL_PERCENTAGE, finalPercentage);
		contentValues.put(COLUMN_DISCONNECT_TIME, disconnectTime);
		db.update(TABLE_NAME, contentValues, COLUMN_ID + " = ? ", new String[] { (tableId) } );
		return true;
	}

	public int numberOfRows() {
		SQLiteDatabase db = this.getReadableDatabase();
		int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
		return numRows;
	}

	public ArrayList<HashMap<String,String>> AllData() {
		Cursor c;
		ArrayList<HashMap<String, String>> Al = new ArrayList<>();
		Integer countRows = numberOfRows();
		SQLiteDatabase db = this.getWritableDatabase();
		String SELECT_SQL = "SELECT * FROM " + TABLE_NAME+" ORDER BY "+COLUMN_ID+" ASC";
		c = db.rawQuery(SELECT_SQL, null);
		c.moveToLast();
		c.moveToPrevious();
		for(int i=1;i<countRows;i++) {
			HashMap<String, String> p = new HashMap<String, String>();
			String columnId = p.put("columnId",c.getString(0));
			String initialPercent = p.put("initialPercent",c.getString(1));
			String finalPercent = p.put("finalPercent",c.getString(2));
			String duration = p.put("duration",c.getString(3));
			String date = p.put("date",c.getString(4));
			String time = p.put("connectTime",c.getString(5));
			String diconnectTime = p.put("diconnectTime",c.getString(6));
			String isCharging = p.put("isCharging",c.getString(7));

            if (!c.getString(1).equals(c.getString(2)))
                Al.add(p);
			c.moveToPrevious();
		}
		c.close();
		return Al;
	}
}
