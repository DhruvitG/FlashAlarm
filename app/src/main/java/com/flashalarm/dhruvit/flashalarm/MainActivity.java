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

//TODO: Feature: enable alarms using toggle
//TODO: add design to the listview

public class MainActivity  extends ActionBarActivity{

    private android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.main_activity);

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);

        this.showUserAlarms();

        // button for adding new alarm
        Button newAlarmButton = (Button) this.findViewById(R.id.new_alarm_button);
        newAlarmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // show timePicker
                DialogFragment timePickerFragment = new TimePickerFragment();
                timePickerFragment.show(getFragmentManager(), "timePicker");
            }
        });
    }

    private void showUserAlarms(){
        final AlarmsDbHelper alarmsDbHelper = new AlarmsDbHelper(this);
        Cursor cursor = alarmsDbHelper.getAlarms();
        ListView alarmListView = (ListView) findViewById(R.id.alarm_listview);
        AlarmCursorAdapter alarmCursorAdapter = new AlarmCursorAdapter(this, cursor, 0);
        alarmListView.setAdapter(alarmCursorAdapter);
        alarmListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(view.getId() == R.id.toggle_button){
                    Switch toggle = (Switch) view;
                    if(toggle.isChecked()){

                    }else{
                        alarmsDbHelper.turnOffAlarm();
                        //disable the alarm
                    }
                }
            }
        });
    }


}
