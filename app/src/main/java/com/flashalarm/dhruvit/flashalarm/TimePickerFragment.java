package com.flashalarm.dhruvit.flashalarm;

import android.app.Activity;
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
    onTimeSelectedListener mCallback;

    public interface onTimeSelectedListener{
        public void onTimeSelected();
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (onTimeSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnTimeSelectedListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        // Use the current time as default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), DialogFragment.STYLE_NORMAL, this, hour, minute, android.text.format.DateFormat.is24HourFormat(getActivity()));
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
        AlarmsDbHelper alarmsDbHelper = new AlarmsDbHelper(getActivity());
        int id = alarmsDbHelper.insertAlarm(hourOfDay, minute);
        com.flashalarm.dhruvit.flashalarm.AlarmManager alarmManager = new com.flashalarm.dhruvit.flashalarm.AlarmManager(getActivity());
        alarmManager.setAlarm(timeLeft, id);
        mCallback.onTimeSelected();
    }

}
