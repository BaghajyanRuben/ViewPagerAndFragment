package com.photo.viewpagerandfragments;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBhelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "test_db";
	private static final int DB_VERSION = 1;

	private static final String TABLE_NAME = "note";

	private static final String COLUMN_ID = "column_id";
	private static final String COLUMN_NOTE = "column_note";
	private static final String COLUMN_DATE = "column_date";


	private static final String CREATE_TABLE = "CREATE TABLE "
			+ TABLE_NAME
			+ "("
			+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COLUMN_NOTE + " TEXT, "
			+ COLUMN_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP"
			+ ")";


	public DBhelper(Context context){
		super(context, DB_NAME, null, DB_VERSION);
	}

	public DBhelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase sqLiteDatabase) {
		sqLiteDatabase.execSQL(CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int i, int i1) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		// Create tables again
		onCreate(db);
	}

	public void writeToDB(String note){

		SQLiteDatabase db = getWritableDatabase();

		ContentValues cv = new ContentValues();

		cv.put(COLUMN_NOTE, note);

		db.insert(TABLE_NAME, null, cv);

		db.close();
	}

	public  Note readFromDB(int id){

		String query = "SELECT FROM"
				+ TABLE_NAME
				+ " WHERE "
				+ COLUMN_ID
				+ " = ? " + id;

		SQLiteDatabase db = getReadableDatabase();

		Cursor cursor = db.rawQuery(query, null);

		if (cursor == null){
			db.close();
			return null;
		}


		String note = cursor.getColumnName(cursor.getColumnIndex(COLUMN_NOTE));
		int dbId = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
		String date = cursor.getString(cursor.getColumnIndex(COLUMN_NOTE));

		Note item = new Note();

		item.setNote(note);
		item.setId(dbId);
		item.setTimeStamp(date);

		return item;
	}

	public List<Note> readAllFromDB(){
		String query = "SELECT * FROM " + TABLE_NAME;

		SQLiteDatabase db = getReadableDatabase();

		Cursor cursor = db.rawQuery(query, null);

		if (cursor == null){
			db.close();
			return new ArrayList<>();
		}

		cursor.moveToFirst();

		List<Note> items = new ArrayList<>();

		while (cursor.moveToNext()){
			String note = cursor.getColumnName(cursor.getColumnIndex(COLUMN_NOTE));
			int dbId = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
			String date = cursor.getString(cursor.getColumnIndex(COLUMN_NOTE));

			Note item = new Note(dbId, note, date);

			items.add(item);
		}

		return items;
	}


	public Note readLastFromDB(){
		String query = "SELECT * FROM " + TABLE_NAME;

		SQLiteDatabase db = getReadableDatabase();

		Cursor cursor = db.rawQuery(query, null);

		if (cursor == null){
			db.close();
			return null;
		}

		cursor.moveToLast();
		String note = cursor.getColumnName(cursor.getColumnIndex(COLUMN_NOTE));
		int dbId = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
		String date = cursor.getString(cursor.getColumnIndex(COLUMN_NOTE));

		return new Note(dbId, note, date);

	}

	public void removeFromDB(int id){

		SQLiteDatabase db = getWritableDatabase();

		db.delete(TABLE_NAME, COLUMN_ID,
				new String[]{" = ? " + id});

		db.close();

	}

}
