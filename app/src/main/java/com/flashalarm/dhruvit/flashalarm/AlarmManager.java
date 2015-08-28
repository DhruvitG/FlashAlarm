package com.flashalarm.dhruvit.flashalarm;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class AlarmManager {
    private Context context;

    public AlarmManager(Context context){
        this.context = context;
    }

    public void setAlarm(int time, long id)
    {
        Intent intent = new Intent(this.context, AlarmReceiver.class);
        intent.putExtra(Intent.EXTRA_UID, id);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.context, 0, intent, 0);
        android.app.AlarmManager alarmManager = (android.app.AlarmManager) this.context.getSystemService(Context.ALARM_SERVICE);
        //alarmManager.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), 1000*60*1, pendingIntent);
        alarmManager.setExact(android.app.AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + time * 60 * 1000, pendingIntent);;
    }

    public void CancelAlarm(long id)
    {
        Intent intent = new Intent(this.context, AlarmReceiver.class);
        intent.putExtra(Intent.EXTRA_UID, id);
        PendingIntent sender = PendingIntent.getBroadcast(this.context, 0, intent, 0);
        android.app.AlarmManager alarmManager = (android.app.AlarmManager) this.context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }
}
