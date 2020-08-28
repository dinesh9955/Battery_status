package com.replaycreation.application.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;


import com.replaycreation.application.MainActivity;
import com.replaycreation.application.R;
import com.replaycreation.application.StopAlarmActivity;


public class AlarmService extends Service {
    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;

    @Override
    public void onCreate() {
        super.onCreate();

        mediaPlayer = MediaPlayer.create(this, R.raw.bubble);
        mediaPlayer.setLooping(true);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent notificationIntent = new Intent(this, StopAlarmActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        String alarmTitle = "SSSSS";

//        Notification notification = NotificationCompat.Builder(this, "CHANNEL_ID")
//                .setContentTitle(alarmTitle)
//                .setContentText("Ring Ring .. Ring Ring")
//                .setSmallIcon(R.drawable.ic_alarm_black_24dp)
//                .setContentIntent(pendingIntent)
//                .build();

        mediaPlayer.start();

        long[] pattern = { 0, 100, 1000 };
        vibrator.vibrate(pattern, 0);


//        NotificationCompat.Builder mBuilder= new NotificationCompat.Builder(this)
//                .setSmallIcon(R.drawable.notification_icon)
//                .setContentTitle("title")
//                .setAutoCancel(true)
//                .setContentText("content")
//                .setContentIntent(PendingIntent.getActivity(this, 0, new Intent(), 0));
//        NotificationManager notificationManager= (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(0, mBuilder.build());


        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notificationChannel = new NotificationChannel("ID", "Name", importance);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            notificationManager.createNotificationChannel(notificationChannel);
            builder = new NotificationCompat.Builder(getApplicationContext(), notificationChannel.getId());
        } else {
            builder = new NotificationCompat.Builder(getApplicationContext());
        }

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
       // builder.getNotification().flags |= Notification.FLAG_AUTO_CANCEL;
        builder = builder
                .setSmallIcon(R.drawable.notification_icon)
                // .setColor(ContextCompat.getColor(context, R.color.color))
                .setWhen(System.currentTimeMillis())
                .setContentTitle("Alarm Ringing")
                .setPriority(Notification.PRIORITY_MAX)
                .setAutoCancel(true)
              //  .setContentText("in");;
//                .setCategory(Notification.CATEGORY_SERVICE)
//                .setTicker("VINlocity")
//                .setContentText("VINlocity Driver is tracking.")
                .setDefaults(Notification.DEFAULT_ALL)
         .setOngoing(true);
       // notificationManager.notify(12, builder.build());

        startForeground(12, builder.build());

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mediaPlayer.stop();
        vibrator.cancel();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
