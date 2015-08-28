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
    public AlarmCursorAdapter(Context context, Cursor cursor, int flags){
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent){
        return LayoutInflater.from(context).inflate(R.layout.alarm_entry, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor){
        TextView timeView = (TextView) view.findViewById(R.id.time_view);
        int db_hour = cursor.getInt(cursor.getColumnIndexOrThrow(AlarmReaderContract.AlarmEntry.COLUMN_NAME_HOUR));
        int db_minute = cursor.getInt(cursor.getColumnIndexOrThrow(AlarmReaderContract.AlarmEntry.COLUMN_NAME_MINUTE));
        String hour = Integer.toString(db_hour);
        String minute = Integer.toString(db_minute);
        String time = hour + ":" + minute;
        timeView.setText(time);
    }
}
