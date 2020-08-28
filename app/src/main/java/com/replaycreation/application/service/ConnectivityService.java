package com.replaycreation.application.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.replaycreation.application.MainActivity;
import com.replaycreation.application.R;

public class ConnectivityService extends Service {

    private static final String TAG = "ConnectivityService";

//    String NOTIFICATION_CHANNEL_ID = "1100";
//    String NOTIFICATION_CHANNEL_ID2 = "2100";
//    String NOTIFICATION_CHANNEL_NAME = "Power Status";

    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String input = "";

       // input = intent.getStringExtra("key");



//
//        else if (intent.hasExtra("powerConnected")) {
//           input = intent.getStringExtra("powerConnected");
//        }

        if(intent != null){
            if (intent.hasExtra("key")) {
                input = intent.getStringExtra("key");
            }
        }



        Log.e(TAG, "input::: "+input);


    //    startNotification(input);


        return super.onStartCommand(intent, flags, startId);
    }



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }










    private void notification(String txt) {
        String in = "";
        if(txt.equalsIgnoreCase("powerConnected")){
            // mBuilder.setContentText("Power Connected!");
            in = "Power Connected!";
//            notification("Power Connected!");
        }

//        else if(txt.equalsIgnoreCase("powerDisconnected")){
//            //mBuilder.setContentText("Power Disconnected!");
////            notification("Power Disconnected!");
//            in = "Power Disconnected!";
//        }


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(),
                default_notification_channel_id )
                .setSmallIcon(R.drawable. notification_icon )
                .setContentTitle( "Power Status" )
                //  .setSound(sound)
                .setAutoCancel(true)
                .setContentText(in);

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(contentIntent);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context. NOTIFICATION_SERVICE ) ;
        if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES.O) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes. CONTENT_TYPE_SONIFICATION )
                    .setUsage(AudioAttributes. USAGE_ALARM )
                    .build() ;
            int importance = NotificationManager. IMPORTANCE_HIGH ;
            NotificationChannel notificationChannel = new
                    NotificationChannel( NOTIFICATION_CHANNEL_ID , "NOTIFICATION_CHANNEL_NAME" , importance) ;
            notificationChannel.enableLights( true ) ;
            notificationChannel.setLightColor(Color.RED ) ;
            notificationChannel.enableVibration( true ) ;


//            notificationChannel.setVibrationPattern( new long []{ 100 , 200 , 300 , 400 , 500 , 400 , 300 , 200 , 400 }) ;
//            notificationChannel.setSound(sound , audioAttributes) ;
            mBuilder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
            assert mNotificationManager != null;
            mNotificationManager.createNotificationChannel(notificationChannel) ;
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(( int ) System. currentTimeMillis (), mBuilder.build()) ;

    }



    private void startNotification(String txt) {
        String in = "";
        if(txt.equalsIgnoreCase("powerConnected")){
            // mBuilder.setContentText("Power Connected!");
            in = "Power Connected!";
//            notification("Power Connected!");
        }
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

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        builder = builder
                .setSmallIcon(R.drawable.notification_icon)
                // .setColor(ContextCompat.getColor(context, R.color.color))
                .setWhen(System.currentTimeMillis())
                .setContentTitle("Power Status")
                .setPriority(Notification.PRIORITY_MAX)
                .setAutoCancel(true)
                .setContentText(in);;
//                .setCategory(Notification.CATEGORY_SERVICE)
//                .setTicker("VINlocity")
//                .setContentText("VINlocity Driver is tracking.")
//                .setDefaults(Notification.DEFAULT_ALL)
        // .setOngoing(true);
        notificationManager.notify(12, builder.build());
        //startForeground(11, builder.build());

    }


}
