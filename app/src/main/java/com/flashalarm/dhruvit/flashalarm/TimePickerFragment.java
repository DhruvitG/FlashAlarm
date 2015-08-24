package com.flashalarm.dhruvit.flashalarm;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        // Use the current time as default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), R.style.AppTheme, this, hour, minute, android.text.format.DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute){
        // TODO: Use alarm manager to ring the alarm at an appropriate time
        final Calendar c = Calendar.getInstance();
        int currentHour = c.get(Calendar.HOUR_OF_DAY);
        int currentMinute = c.get(Calendar.MINUTE);
        int currentTime = currentHour * 60 + currentMinute;
        int setTime = hourOfDay * 60 + minute;
        int timeLeft = setTime - currentTime;
        if(currentTime > setTime){
            timeLeft += 24*60;
        }

        AlarmReceiver alarmReceiver = new AlarmReceiver();
        alarmReceiver.setAlarm(getActivity(), timeLeft);

        insertAlarmDb(hourOfDay, minute);
    }

    public void insertAlarmDb(int hourOfDay, int minute){
        AlarmsDbHelper alarmsOpenHelper = new AlarmsDbHelper(getActivity());
        SQLiteDatabase db = alarmsOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AlarmReaderContract.AlarmEntry.COLUMN_NAME_HOUR, hourOfDay);
        values.put(AlarmReaderContract.AlarmEntry.COLUMN_NAME_MINUTE, minute);
        values.put(AlarmReaderContract.AlarmEntry.COLUMN_NAME_IS_REPEATING, 0);
        values.put(AlarmReaderContract.AlarmEntry.COLUMN_NAME_IS_ON, 1);
        long newRowId;
        newRowId = db.insert(AlarmReaderContract.AlarmEntry.TABLE_NAME, null, values);
    }

}
