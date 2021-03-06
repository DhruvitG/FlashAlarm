package com.flashalarm.dhruvit.flashalarm;

import android.app.AlarmManager;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.view.WindowManager;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver
{

    @Override
    public void onReceive(Context context, Intent intent)
    {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");

       /* KeyguardManager km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        boolean isPhoneLocked = km.isDeviceLocked();*/

        wakeLock.acquire();

        int id = intent.getIntExtra(Intent.EXTRA_UID, 0);

        Toast.makeText(context, "alarm id: "+ id, Toast.LENGTH_LONG).show();
        Intent myIntent = new Intent(context, FlashActivity.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(myIntent);

        wakeLock.release();
    }

}
