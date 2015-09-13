package com.flashalarm.dhruvit.flashalarm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ListView;

import java.io.Console;

public class AlarmsDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "user_alarms";
    private static final int DATABASE_VERSION = 1;
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + AlarmReaderContract.AlarmEntry.TABLE_NAME + " (" +
            AlarmReaderContract.AlarmEntry.COLUMN_NAME_ALARM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            AlarmReaderContract.AlarmEntry.COLUMN_NAME_HOUR + " MEDIUMINT[24]," +
            AlarmReaderContract.AlarmEntry.COLUMN_NAME_MINUTE + " MEDIUMINT[60]," +
            AlarmReaderContract.AlarmEntry.COLUMN_NAME_IS_REPEATING + " TINYINT[1]," +
            AlarmReaderContract.AlarmEntry.COLUMN_NAME_IS_ON + " TINYINT[1])";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + AlarmReaderContract.AlarmEntry.TABLE_NAME;

    AlarmsDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        // Create table
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }

    public Cursor getAlarms(){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                AlarmReaderContract.AlarmEntry.COLUMN_NAME_ALARM_ID,
                AlarmReaderContract.AlarmEntry.COLUMN_NAME_HOUR,
                AlarmReaderContract.AlarmEntry.COLUMN_NAME_MINUTE,
                AlarmReaderContract.AlarmEntry.COLUMN_NAME_IS_REPEATING,
                AlarmReaderContract.AlarmEntry.COLUMN_NAME_IS_ON,
        };

        String sortOrder = AlarmReaderContract.AlarmEntry.COLUMN_NAME_ALARM_ID + " ASC";

        Cursor cursor = db.query(
                AlarmReaderContract.AlarmEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );
        cursor.moveToFirst();

        return cursor;
    }

    public Cursor getAlarm(long id){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                AlarmReaderContract.AlarmEntry.COLUMN_NAME_ALARM_ID,
                AlarmReaderContract.AlarmEntry.COLUMN_NAME_HOUR,
                AlarmReaderContract.AlarmEntry.COLUMN_NAME_MINUTE,
                AlarmReaderContract.AlarmEntry.COLUMN_NAME_IS_REPEATING,
                AlarmReaderContract.AlarmEntry.COLUMN_NAME_IS_ON,
        };

        String sortOrder = AlarmReaderContract.AlarmEntry.COLUMN_NAME_ALARM_ID + " ASC";

        Cursor cursor = db.query(
                AlarmReaderContract.AlarmEntry.TABLE_NAME,
                projection,
                "_id = " + id,
                null,
                null,
                null,
                sortOrder
        );
        cursor.moveToFirst();
        return cursor;
    }

    public int insertAlarm(int hourOfDay, int minute){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AlarmReaderContract.AlarmEntry.COLUMN_NAME_HOUR, hourOfDay);
        values.put(AlarmReaderContract.AlarmEntry.COLUMN_NAME_MINUTE, minute);
        values.put(AlarmReaderContract.AlarmEntry.COLUMN_NAME_IS_REPEATING, 0);
        values.put(AlarmReaderContract.AlarmEntry.COLUMN_NAME_IS_ON, 1);
        int newRowId = (int)db.insert(AlarmReaderContract.AlarmEntry.TABLE_NAME, null, values);
        return newRowId;
    }

    public void deleteAlarm(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(AlarmReaderContract.AlarmEntry.TABLE_NAME, AlarmReaderContract.AlarmEntry.COLUMN_NAME_ALARM_ID + "=" + id, null);
    }

    public void turnOnAlarm(int id){
        ContentValues contentValues = new ContentValues();
        contentValues.put(AlarmReaderContract.AlarmEntry.COLUMN_NAME_IS_ON, 1);
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(AlarmReaderContract.AlarmEntry.TABLE_NAME, contentValues, AlarmReaderContract.AlarmEntry.COLUMN_NAME_ALARM_ID + "=" + id, null);
    }

    public void turnOffAlarm(int id){
        ContentValues contentValues = new ContentValues();
        contentValues.put(AlarmReaderContract.AlarmEntry.COLUMN_NAME_IS_ON, 0);
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.update(AlarmReaderContract.AlarmEntry.TABLE_NAME, contentValues, AlarmReaderContract.AlarmEntry.COLUMN_NAME_ALARM_ID + "= " + id, null);
        System.out.println("rowsAffected");
        System.out.println(rowsAffected);
    }
}
