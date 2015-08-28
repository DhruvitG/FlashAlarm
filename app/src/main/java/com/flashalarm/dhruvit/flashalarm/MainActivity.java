package com.flashalarm.dhruvit.flashalarm;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.ToggleButton;
import android.widget.Toolbar;

import java.util.Calendar;

//TODO: Feature: enable alarms using toggle
//TODO: add design to the listview

public class MainActivity  extends ActionBarActivity{
    private android.support.v7.widget.Toolbar toolbar;
    private AlarmCursorAdapter alarmCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.main_activity);

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);

        // retrieve alarm info from db and bind to listview
        this.showUserAlarms();

        // button for displaying time picker
        Button newAlarmButton = (Button) this.findViewById(R.id.new_alarm_button);
        newAlarmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // show timePicker
                DialogFragment timePickerFragment = new TimePickerFragment();
                timePickerFragment.show(getFragmentManager(), "timePicker");
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        //this.alarmCursorAdapter.notifyDataSetChanged();
        showUserAlarms();
    }

    public void showUserAlarms(){
        final AlarmsDbHelper alarmsDbHelper = new AlarmsDbHelper(this);
        final AlarmManager alarmManager = new AlarmManager(this);

        // bind db data to listview
        Cursor cursor = alarmsDbHelper.getAlarms();
        ListView alarmListView = (ListView) findViewById(R.id.alarm_listview);
        this.alarmCursorAdapter = new AlarmCursorAdapter(this, cursor, 0);
        alarmListView.setAdapter(this.alarmCursorAdapter);


        alarmListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // enable/disable alarm according to toggle button for each entry in listview
                if (view.getId() == R.id.toggle_button) {
                    Switch toggle = (Switch) view;
                    if (toggle.isChecked()) {
                        alarmsDbHelper.turnOnAlarm(id);
                        Cursor cursor = alarmsDbHelper.getAlarm(id);
                        int timeLeft = getTimeLeft(cursor.getColumnIndex(AlarmReaderContract.AlarmEntry.COLUMN_NAME_HOUR), cursor.getColumnIndex(AlarmReaderContract.AlarmEntry.COLUMN_NAME_MINUTE));
                        alarmManager.setAlarm(timeLeft, id);
                    } else {
                        alarmsDbHelper.turnOffAlarm(id);
                        alarmManager.CancelAlarm(id);
                    }
                }
            }
        });
    }

    public int getTimeLeft(int hourOfDay, int minute){
        final Calendar c = Calendar.getInstance();
        int currentHour = c.get(Calendar.HOUR_OF_DAY);
        int currentMinute = c.get(Calendar.MINUTE);
        int currentTime = currentHour * 60 + currentMinute;
        int setTime = hourOfDay * 60 + minute;
        int timeLeft = setTime - currentTime;
        if(currentTime > setTime){
            timeLeft += 24*60;
        }
        return timeLeft;
    }


}
