package com.geekmech.upplanner;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class EventReaderContract {
	public EventReaderContract(){}
	
	public static abstract class EventEntry implements BaseColumns{
		public static final String TABLE_NAME = "Events";
		public static final String COLUMN_EVENT_TITLE = "Name";
		public static final String COLUMN_EVENT_PARTICIPANTS = "People";
		public static final String COLUMN_EVENT_SETTING = "Setting";
		public static final String COLUMN_EVENT_TIME = "Time";
		public static final String COLUMN_EVENT_DATE = "Date";
		public static final String COLUMN_EVENT_PEOPLE_REQ = "PeopleRequired";
		public static final String COLUMN_EVENT_PEOPLE_MAX = "MaxPeople";
	}
	private static final String TEXT_TYPE = " TEXT";
	private static final String INTEGER_TYPE = " INTEGER";
	private static final String COMMA_SEP = ",";
	private static final String SQL_CREATE_ENTRIES =
	    "CREATE TABLE " + EventEntry.TABLE_NAME + " (" +
		EventEntry._ID + " INTEGER PRIMARY KEY," +
	    EventEntry.COLUMN_EVENT_TITLE + TEXT_TYPE + COMMA_SEP +
	    EventEntry.COLUMN_EVENT_PARTICIPANTS + TEXT_TYPE + COMMA_SEP +
	    EventEntry.COLUMN_EVENT_SETTING + TEXT_TYPE + COMMA_SEP +
	    EventEntry.COLUMN_EVENT_TIME + INTEGER_TYPE + COMMA_SEP +
	    EventEntry.COLUMN_EVENT_DATE + TEXT_TYPE + COMMA_SEP +
	    EventEntry.COLUMN_EVENT_PEOPLE_REQ + INTEGER_TYPE + COMMA_SEP +
	    EventEntry.COLUMN_EVENT_PEOPLE_MAX + INTEGER_TYPE + COMMA_SEP +
	    " )";

	private static final String SQL_DELETE_ENTRIES =
	    "DROP TABLE IF EXISTS " + EventEntry.TABLE_NAME;
	
	public class EventHelper extends SQLiteOpenHelper {
	        // IMPORTANT: If you change the database schema, you must increment the database version.
	        public static final int DATABASE_VERSION = 2;
	        public static final String DATABASE_NAME = "UpPlanner.db";

	        public EventHelper(Context context) {
	            super(context, DATABASE_NAME, null, DATABASE_VERSION);
	        }
	        public void onCreate(SQLiteDatabase db) {
	            db.execSQL(SQL_CREATE_ENTRIES);
	        }
	        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	            // This database is only a cache for online data, so its upgrade policy is
	            // to simply to discard the data and start over
	            db.execSQL(SQL_DELETE_ENTRIES);
	            onCreate(db);
	        }
	        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	            onUpgrade(db, oldVersion, newVersion);
	        }
	    }

}
