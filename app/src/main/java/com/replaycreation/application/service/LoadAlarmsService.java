package com.replaycreation.application.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.JobIntentService;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.replaycreation.application.MainActivity;
import com.replaycreation.application.R;
import com.replaycreation.application.StopAlarmActivity;

import static android.support.v4.app.NotificationCompat.DEFAULT_VIBRATE;

public class LoadAlarmsService extends JobIntentService {
    static final int JOB_ID = 1000;
    String NOTIFICATION_CHANNEL_ID = "1000";
    String NOTIFICATION_CHANNEL_ID2 = "2000";
    String NOTIFICATION_CHANNEL_NAME2 = "FullBatteryAlarm2";
    int NOTIFICATION_ID = 1;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.w("Ankush","Job Execution Started");
        createNotificationChannel();
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID2)
                .setContentTitle(getString(R.string.app_name))
                .setContentText("Charging Stated")
                .setDefaults(DEFAULT_VIBRATE)
                //.setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                .setLights(Color.YELLOW, 1000, 300)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setPriority(android.support.v4.app.NotificationCompat.PRIORITY_MAX)
                .setSmallIcon(R.drawable.ic_battery_charging_full_black_24dp)
                //.setContentIntent(pendingIntent)
                .build();
        startForeground(NOTIFICATION_ID, notification);
    }
    public static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, LoadAlarmsService.class, JOB_ID, work);
    }
    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Log.w("Ankush","LoadAlarmsService Start");
        Intent i = new Intent(LoadAlarmsService.this, StopAlarmActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
    /*private static final String TAG = LoadAlarmsService.class.getSimpleName();
    public static final String ACTION_COMPLETE = TAG + ".ACTION_COMPLETE";
    public static final String ALARMS_EXTRA = "alarms_extra";
    public LoadAlarmsService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }

    public static void launchLoadAlarmsService(Context context) {
        Intent alarmIntent = new Intent("android.intent.action.MAIN");
        alarmIntent.setClass(context, StopAlarmActivity.class);
        alarmIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(alarmIntent);
    }*/


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    NOTIFICATION_CHANNEL_ID2,
                    NOTIFICATION_CHANNEL_NAME2,
                    NotificationManager.IMPORTANCE_HIGH
            );
            serviceChannel.setSound(null,null);
            serviceChannel.setDescription(NOTIFICATION_CHANNEL_ID);
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(serviceChannel);
            }
        }
    }
}
