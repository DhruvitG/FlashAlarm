package com.flashalarm.dhruvit.flashalarm;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CursorAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

public class AlarmCursorAdapter extends CursorAdapter{
    public AlarmsDbHelper alarmsDbHelper;
    public AlarmManager alarmManager;

    public AlarmCursorAdapter(Context context, Cursor cursor, int flags){
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent){
        this.alarmsDbHelper = new AlarmsDbHelper(context);
        this.alarmManager = new AlarmManager(context);
        return LayoutInflater.from(context).inflate(R.layout.alarm_entry, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor){
        TextView timeView = (TextView) view.findViewById(R.id.time_view);
        Switch toggleButton = (Switch) view.findViewById(R.id.toggle_button);
        int db_hour = cursor.getInt(cursor.getColumnIndexOrThrow(AlarmReaderContract.AlarmEntry.COLUMN_NAME_HOUR));
        int db_minute = cursor.getInt(cursor.getColumnIndexOrThrow(AlarmReaderContract.AlarmEntry.COLUMN_NAME_MINUTE));
        int db_is_on = cursor.getInt(cursor.getColumnIndexOrThrow(AlarmReaderContract.AlarmEntry.COLUMN_NAME_IS_ON));
        int db_id = cursor.getInt(cursor.getColumnIndexOrThrow(AlarmReaderContract.AlarmEntry.COLUMN_NAME_ALARM_ID));
        String hour = Integer.toString(db_hour);
        String minute = Integer.toString(db_minute);
        String time = hour + ":" + minute;
        timeView.setText(time);
        System.out.println(db_is_on);
        if(db_is_on == 1){
            toggleButton.setChecked(true);
        }else{
            toggleButton.setChecked(false);
        }

        toggleButton.setTag(db_id);
        System.out.println("Setting tag");
        System.out.println(db_id);

        // delete alarm if delete button is pressed
        Button delete_button = (Button) view.findViewById(R.id.delete_button);
        delete_button.setTag(db_id);
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int alarm_id = (int)v.getTag();
                System.out.println("delete button pressed");
                alarmsDbHelper.deleteAlarm(alarm_id);
                alarmManager.CancelAlarm(alarm_id);
            }
        });


        // enable/disable alarm according to toggle button for each entry in listview
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                System.out.println("toggle button pressed");
                int alarm_id = (int)buttonView.getTag();
                System.out.println(alarm_id);
                if (isChecked) {
                    alarmsDbHelper.turnOnAlarm(alarm_id);
                    Cursor cursor = alarmsDbHelper.getAlarm(alarm_id);
                    int timeLeft = alarmManager.getTimeLeft(cursor.getColumnIndex(AlarmReaderContract.AlarmEntry.COLUMN_NAME_HOUR), cursor.getColumnIndex(AlarmReaderContract.AlarmEntry.COLUMN_NAME_MINUTE));
                    alarmManager.setAlarm(timeLeft, alarm_id);
                } else {
                    alarmsDbHelper.turnOffAlarm(alarm_id);
                    alarmManager.CancelAlarm(alarm_id);
                }
            }
        });
    }
}
