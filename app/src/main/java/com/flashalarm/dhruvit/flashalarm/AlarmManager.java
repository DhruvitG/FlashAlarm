package com.flashalarm.dhruvit.flashalarm;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class AlarmManager {
    private Context context;

    public AlarmManager(Context context){
        this.context = context;
    }

    public void setAlarm(int time, int id)
    {
        Intent intent = new Intent(this.context, AlarmReceiver.class);
        intent.putExtra(Intent.EXTRA_UID, id);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.context, 0, intent, 0);
        android.app.AlarmManager alarmManager = (android.app.AlarmManager) this.context.getSystemService(Context.ALARM_SERVICE);
        //alarmManager.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), 1000*60*1, pendingIntent);
        alarmManager.setExact(android.app.AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + time * 60 * 1000, pendingIntent);;
    }

    public void CancelAlarm(int id)
    {
        Intent intent = new Intent(this.context, AlarmReceiver.class);
        intent.putExtra(Intent.EXTRA_UID, id);
        PendingIntent sender = PendingIntent.getBroadcast(this.context, 0, intent, 0);
        android.app.AlarmManager alarmManager = (android.app.AlarmManager) this.context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
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
