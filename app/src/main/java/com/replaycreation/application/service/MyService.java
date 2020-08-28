package com.replaycreation.application.service;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


import com.replaycreation.application.MainActivity;
import com.replaycreation.application.R;
import com.replaycreation.application.StopAlarmActivity;
import com.replaycreation.application.alertpakage.ChargingNotificationLayout;
import com.replaycreation.application.alertpakage.LowBatteryDialoge;
import com.replaycreation.application.alertpakage.TemperatureAlertDialog;
import com.replaycreation.application.database.PowerEventsTable;
import com.replaycreation.application.receiver.AlarmReceiver;
import com.replaycreation.application.theftalarm.StopTheftAlarmActivity;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.StringTokenizer;

import static java.lang.Math.abs;

public class MyService extends Service {
    String TAG = "MyService";

    int counter = 0;

    String levelTop = "";
    public static final String BATTERY_UPDATE = "battery";
    String NOTIFICATION_CHANNEL_ID = "1000";
    String NOTIFICATION_CHANNEL_ID2 = "2000";
    String NOTIFICATION_CHANNEL_NAME = "FullBatteryAlarm";
    String NOTIFICATION_CHANNEL_NAME2 = "FullBatteryAlarm2";

    private static final String TAG_FOREGROUND_SERVICE = "FOREGROUND_SERVICE";

    public static final String ACTION_START_FOREGROUND_SERVICE = "ACTION_START_FOREGROUND_SERVICE";

    public static final String ACTION_STOP_FOREGROUND_SERVICE = "ACTION_STOP_FOREGROUND_SERVICE";

    public static final String ACTION_PAUSE = "ACTION_PAUSE";

    public static final String ACTION_PLAY = "ACTION_PLAY";

    NotificationManager manager;

//        private final static int INTERVAL = 1000 * 60 * 10; //10 minutes
    private final static int INTERVAL = 1000 * 1 * 1; //30 seconds

    Handler mHandler = new Handler();

//    AlarmManager alarmManager;
//    PendingIntent pendingIntent;
    Runnable mHandlerTask = new Runnable()
    {
        @Override
        public void run() {
            Log.e(TAG, "ccccccccccccc");
            startSS();

            createNotificationChannel();

          //  AlarmReceiver.startAlarms(MyService.this.getApplicationContext());

            new BatteryCheckAsync().execute();

//            counter = counter + 1;
//
//            if(counter == 20){

//                PendingIntent pendingIntent = PendingIntent.getActivity(
//                        getApplicationContext(),
//                        777,
//                        new Intent(getApplicationContext(), StopAlarmActivity.class),
//                        PendingIntent.FLAG_UPDATE_CURRENT);
//                AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
////                alarmManager.cancel(pendingIntent);
//                long triggerAtMillis = System.currentTimeMillis() + 5000;
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    AlarmManager.AlarmClockInfo alarmClockInfo = new AlarmManager.AlarmClockInfo(triggerAtMillis, pendingIntent);
//                    if (alarmManager != null) {
//                        Log.e(TAG, "qqqqqqqq1111111111");
//                        alarmManager.setAlarmClock(alarmClockInfo, pendingIntent);
////                        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
////                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
////                        alarmManager.setExact(AlarmManager.ELAPSED_REALTIME, triggerAtMillis, pendingIntent);
//
//                    }
//                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                    if (alarmManager != null) {
//                        Log.e(TAG, "qqqqqqq2222222222");
//                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
//                    }
//                } else {
//                    if (alarmManager != null) {
//                        Log.e(TAG, "qqqqqqq333333333");
//                        alarmManager.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
//                    }
//                }

//                AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
//                Intent intent = new Intent(getApplicationContext(), AlarmBroadcastReceiver.class);
//                PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 777, intent, 0);
//
//
//                alarmManager.setExact(
//                        AlarmManager.RTC_WAKEUP,
//                        System.currentTimeMillis(),
//                        alarmPendingIntent
//                );
//
//
//
//
//
//            }





            mHandler.postDelayed(mHandlerTask, INTERVAL);
        }
    };



    void startRepeatingTask()
    {
        mHandlerTask.run();
    }


    void stopRepeatingTask()
    {
        mHandler.removeCallbacks(mHandlerTask);
    }


    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG_FOREGROUND_SERVICE, "My foreground service onCreate().");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


//        PendingIntent pendingIntent = PendingIntent.getActivity(
//                getApplicationContext(),
//                777,
//                new Intent(getApplicationContext(), StopAlarmActivity.class),
//                PendingIntent.FLAG_UPDATE_CURRENT);
//        AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
//        long triggerAtMillis = System.currentTimeMillis() + 5000;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            AlarmManager.AlarmClockInfo alarmClockInfo = new AlarmManager.AlarmClockInfo(triggerAtMillis, pendingIntent);
//            if (alarmManager != null) {
//                Log.e(TAG, "qqqqqqqq1111111111");
//                alarmManager.setAlarmClock(alarmClockInfo, pendingIntent);
//            }
//        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            if (alarmManager != null) {
//                Log.e(TAG, "qqqqqqq2222222222");
//                alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
//            }
//        } else {
//            if (alarmManager != null) {
//                Log.e(TAG, "qqqqqqq333333333");
//                alarmManager.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
//            }
//        }



//        registerReceiver(new Restarter(), new IntentFilter(Intent.ACTION_POWER_CONNECTED));
//        registerReceiver(new Restarter(), new IntentFilter(Intent.ACTION_POWER_DISCONNECTED));


//        pendingIntent = PendingIntent.getActivity(
//                this,
//                777,
//                new Intent(this, StopAlarmActivity.class),
//                PendingIntent.FLAG_UPDATE_CURRENT);
//        alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);

//        long triggerAtMillis = System.currentTimeMillis() + 5000;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            AlarmManager.AlarmClockInfo alarmClockInfo = new AlarmManager.AlarmClockInfo(triggerAtMillis, pendingIntent);
//            if (alarmManager != null) {
//                alarmManager.setAlarmClock(alarmClockInfo, pendingIntent);
//            }
//        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            if (alarmManager != null) {
//                alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
//            }
//        } else {
//            if (alarmManager != null) {
//                alarmManager.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
//            }
//        }


        String input = "";

        if (intent != null) {
            if (intent.hasExtra("inputExtra")) {
                input = intent.getStringExtra("inputExtra");
            }


            String action = intent.getAction();
            if(action!=null)

                switch (action) {
                    case ACTION_START_FOREGROUND_SERVICE:
                        //startForegroundService();
                        Log.e(TAG, "ACTION_START_FOREGROUND_SERVICE");
                        startSS();
                        //Toast.makeText(getApplicationContext(), "Foreground service is started.", Toast.LENGTH_LONG).show();
                        startRepeatingTask();
                        break;
                    case ACTION_STOP_FOREGROUND_SERVICE:
                        Log.e(TAG, "ACTION_STOP_FOREGROUND_SERVICE");
                        stopForegroundService();
                       // Toast.makeText(getApplicationContext(), "Foreground service is stopped.", Toast.LENGTH_LONG).show();
                        break;
                    case ACTION_PLAY:
                        //Toast.makeText(getApplicationContext(), "You click Play button.", Toast.LENGTH_LONG).show();
                        break;
                    case ACTION_PAUSE:
                        //Toast.makeText(getApplicationContext(), "You click Pause button.", Toast.LENGTH_LONG).show();
                        break;
                }
        }
        //return super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }









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


    @SuppressLint("StaticFieldLeak")
    class BatteryCheckAsync extends AsyncTask<Object, Object, String> {

        SharedPreferences sharedPreference = getSharedPreferences("SharedPreference", 0);


        @Override
        protected String doInBackground(Object... arg0) {

            //Battery State check - create log entries of current battery state
            IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            Intent batteryStatus = MyService.this.registerReceiver(null, ifilter);

            int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);

            boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL;
            Log.i("BatteryInfo", "Battery is charging: " + isCharging);

            int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            int plugged = batteryStatus.getIntExtra("plugged", -1);
            int Temp = batteryStatus.getIntExtra("temperature", -1);

            double temps = (double)Temp / 10;

            String strChargeVia=getPlugTypeString(plugged);

            if (strChargeVia.equals("Unknown")){
                try{
                    Log.e(TAG, "mNotificationManager:: "+manager);
                    if(manager != null){
                        manager.deleteNotificationChannel("10000");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

                SharedPreferences.Editor ee=sharedPreference.edit();
                ee.putString("Triggered","");
                ee.putString("ChargingNotiShowingOrNot","");
                ee.apply();
            }

            String strTheftAutoEnable=sharedPreference.getString("theftAutoEnable", "");
            try {
                if (strTheftAutoEnable.equals("true") && !strChargeVia.equals("Unknown") ){
                    SharedPreferences.Editor ee=sharedPreference.edit();
                    ee.putString("TheftAlarmStatus","ON");
                    ee.apply();
                }
            }catch (Exception e){e.printStackTrace();}

            Log.i("BatteryInfo", "Battery charge level: " + (level / (float)scale));
            float Str=(level / (float)scale*100);
            int levelInt = Math.round(Str);
            String Str_level=String.valueOf(levelInt);
            String StrTemps=String.valueOf(temps);

            String splitStr=Str_level+"-"+strChargeVia+"-"+StrTemps;
            return splitStr;
        }

        protected void onPostExecute(String splitStr){

            SharedPreferences sharedPreference = getSharedPreferences("SharedPreference", 0);
            String AlarmStatus = sharedPreference.getString("FullAlarmStatus","");
            String FullBatteryLevel = sharedPreference.getString("FullBatteryLevel", "");
//            String FullBatteryLevel = "84";
            String TheftAlarmStatus = sharedPreference.getString("TheftAlarmStatus", "");

            String[] split_url = splitStr.split("-");
            for (int j = 0; j < split_url.length; j++) {}
            String level=split_url[0];
            String strChargeVia=split_url[1];
            String str_temps=split_url[2];

            LowOrCustomBatteryAlarm(level,strChargeVia);

            ChargingNotification(strChargeVia,level);
            LowBettaryAlertNotification(level,strChargeVia);

            ChargingOrDischargingEntry(level,strChargeVia);

            try {
                float temps=Float.parseFloat(str_temps);
                temperatureAlertNotification(temps);
            }
            catch (Exception e){e.printStackTrace();
            }
            String trigger=sharedPreference.getString("Triggered", "");
            Log.e(TAG , "level: "+level);
            Log.e(TAG , "FullBatteryLevel: "+FullBatteryLevel);
            Log.e(TAG , "AlarmStatus: "+AlarmStatus);

            Log.e("Ankush",level+"\t"+FullBatteryLevel+"\t"+AlarmStatus+"\t"+strChargeVia+"\t"+trigger);

            if (strChargeVia.equals("Unknown") && TheftAlarmStatus.equals("ON")){
                String StrTheft=sharedPreference.getString("Theft", "");
                if (StrTheft.equals("")){
                    SharedPreferences.Editor ee=sharedPreference.edit();
                    ee.putString("Theft","ON");
                    ee.apply();
                    Intent i=new Intent(getApplicationContext(), StopTheftAlarmActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplicationContext().startActivity(i);
                }
            }

            int myBatteryLevel = Integer.parseInt(level);
            int setBatteryLevel = Integer.parseInt(FullBatteryLevel);

//            AlarmManager am = (AlarmManager) getSystemService(getApplicationContext().ALARM_SERVICE);
//            Intent I = new Intent(getApplicationContext(),StopAlarmActivity.class);
//            PendingIntent P = PendingIntent.getBroadcast(getApplicationContext(), 0, I, PendingIntent.FLAG_CANCEL_CURRENT);
//            am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 5000, P);



//            if(counter == 0){
//                counter = 1;
//                long triggerAtMillis = System.currentTimeMillis() + 5000;
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    AlarmManager.AlarmClockInfo alarmClockInfo = new AlarmManager.AlarmClockInfo(triggerAtMillis, pendingIntent);
//                    if (alarmManager != null) {
//                        alarmManager.setAlarmClock(alarmClockInfo, pendingIntent);
//                    }
//                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                    if (alarmManager != null) {
//                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
//                    }
//                } else {
//                    if (alarmManager != null) {
//                        alarmManager.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
//                    }
//                }
//            }else{
//
//            }




            if (myBatteryLevel >= setBatteryLevel && AlarmStatus.equalsIgnoreCase("ON") && !strChargeVia.equals("Unknown")) {
                Log.e(TAG , "AAAAAAAA");

                String triggered=sharedPreference.getString("Triggered", "");

                if(triggered.equals("") || triggered.equals("ON")){
                    Log.e(TAG, "BBBBBBBB");
                    SharedPreferences.Editor ee=sharedPreference.edit();
                    ee.putString("Triggered","OFF");
                    ee.apply();

                    Log.e(TAG , "vvvvvvvvvvvv");

                    int count = sharedPreference.getInt("count", 0);
                    Log.e(TAG, "count::::: "+count);

//                    String triggered = sharedPreference.getString("Triggered", "");
                    Log.e(TAG, "triggered::::: "+triggered);

                    if(triggered.equals("") || triggered.equals("ON")) {
                        if (count == 0) {
                            count = 1;

                            SharedPreferences.Editor ee2 = sharedPreference.edit();
                            ee2.putInt("count", count);
                            ee2.apply();

//                            PendingIntent pendingIntent = PendingIntent.getActivity(
//                                    getApplicationContext(),
//                                    777,
//                                    new Intent(getApplicationContext(), StopAlarmActivity.class),
//                                    PendingIntent.FLAG_UPDATE_CURRENT);
//                            AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
//                            long triggerAtMillis = System.currentTimeMillis() + 5000;
//                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                                AlarmManager.AlarmClockInfo alarmClockInfo = new AlarmManager.AlarmClockInfo(triggerAtMillis, pendingIntent);
//                                if (alarmManager != null) {
//                                    Log.e(TAG, "qqqqqqqq1111111111");
//                                    alarmManager.setAlarmClock(alarmClockInfo, pendingIntent);
//                                }
//                            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                                if (alarmManager != null) {
//                                    Log.e(TAG, "qqqqqqq2222222222");
//                                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
//                                }
//                            } else {
//                                if (alarmManager != null) {
//                                    Log.e(TAG, "qqqqqqq333333333");
//                                    alarmManager.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
//                                }
//                            }


                            AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                            Intent intent = new Intent(getApplicationContext(), AlarmBroadcastReceiver.class);
                            PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 777, intent, 0);


                            alarmManager.setExact(
                                    AlarmManager.RTC_WAKEUP,
                                    System.currentTimeMillis(),
                                    alarmPendingIntent
                            );
                        }

                    }

                }else{
                    Log.e(TAG, "CCCCCCCCCCC");
                }
            }else{
                Log.e(TAG, "DDDDDDDDD");
            }


//            int count = sharedPreference.getInt("count", 0);
//            Log.e(TAG, "count::::: "+count);
//
//            String triggered = sharedPreference.getString("Triggered", "");
//            Log.e(TAG, "triggered::::: "+triggered);
//
//            if(triggered.equals("") || triggered.equals("ON")) {
//                if (count == 0) {
//                    count = 1;
//
//                    SharedPreferences.Editor ee2 = sharedPreference.edit();
//                    ee2.putInt("count", count);
//                    ee2.apply();
//
//                    PendingIntent pendingIntent = PendingIntent.getActivity(
//                            getApplicationContext(),
//                            777,
//                            new Intent(getApplicationContext(), StopAlarmActivity.class),
//                            PendingIntent.FLAG_UPDATE_CURRENT);
//                    AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
//                    long triggerAtMillis = System.currentTimeMillis() + 5000;
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        AlarmManager.AlarmClockInfo alarmClockInfo = new AlarmManager.AlarmClockInfo(triggerAtMillis, pendingIntent);
//                        if (alarmManager != null) {
//                            Log.e(TAG, "qqqqqqqq1111111111");
//                            alarmManager.setAlarmClock(alarmClockInfo, pendingIntent);
//                        }
//                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                        if (alarmManager != null) {
//                            Log.e(TAG, "qqqqqqq2222222222");
//                            alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
//                        }
//                    } else {
//                        if (alarmManager != null) {
//                            Log.e(TAG, "qqqqqqq333333333");
//                            alarmManager.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
//                        }
//                    }
//                }
//
//            }



        }
    }

    private void ChargingOrDischargingEntry(String level, String strChargeVia) {
        PowerEventsTable GraphTable=new PowerEventsTable(getApplicationContext());
        SharedPreferences sharedPreference = getSharedPreferences("SharedPreference", 0);
        String triggered=sharedPreference.getString("Charging", "");

        if(!strChargeVia.equals("Unknown") && triggered.equals("")){
            SharedPreferences.Editor ee=sharedPreference.edit();
            ee.putString("Charging","1");
            ee.apply();
            GraphTable.update(level,getCurrentTime());
            GraphTable.insert(level,getCurrentDate(),getCurrentTime(),"1");
        }

        if (strChargeVia.equals("Unknown") && triggered.equals("1")){
            SharedPreferences.Editor ee=sharedPreference.edit();
            ee.putString("Charging","");
            ee.apply();
            GraphTable.update(level,getCurrentTime());
            GraphTable.insert(level,getCurrentDate(),getCurrentTime(),"0");
        }
    }

    public String getBatteryCapacity() {
        Object mPowerProfile_ = null;
        String totalCapacity = null;

        final String POWER_PROFILE_CLASS = "com.android.internal.os.PowerProfile";

        try {
            mPowerProfile_ = Class.forName(POWER_PROFILE_CLASS)
                    .getConstructor(Context.class).newInstance(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            double batteryCapacity = (Double) Class
                    .forName(POWER_PROFILE_CLASS)
                    .getMethod("getAveragePower", java.lang.String.class)
                    .invoke(mPowerProfile_, "battery.capacity");

            long l = (long) batteryCapacity;
            totalCapacity=String.valueOf(l);
            return totalCapacity;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalCapacity;
    }

    private int setRemainingCapacity(int batteryLevel, String totalCapacity) {
        long tCapacity=Integer.parseInt(totalCapacity);
        float i=tCapacity/100;
        float j=i*batteryLevel;
        int k=Math.round(j);
        return k;
    }


    public void LowBettaryAlertNotification(String level, String strChargeVia){
        SharedPreferences sharedPreference = getSharedPreferences("SharedPreference", 0);
        String whenNotifyUser = sharedPreference.getString("whenNotifyUser", "");
        String whenNotifyUserShow = sharedPreference.getString("whenNotifyUserShow", "");
        try {
            int i=Integer.parseInt(level);
            int j=Integer.parseInt(whenNotifyUser);
            if (j<i){
                SharedPreferences.Editor ee=sharedPreference.edit();
                ee.putString("whenNotifyUserShow","");
                ee.apply();
            }
        }catch (Exception e){}

        if (whenNotifyUserShow.equals("") && level.equals(whenNotifyUser) && strChargeVia.equals("Unknown")){
            SharedPreferences.Editor ee=sharedPreference.edit();
            ee.putString("whenNotifyUserShow","y");
            ee.apply();

            DefaultNotificationTone();
            Intent i=new Intent(getApplicationContext(), LowBatteryDialoge.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }
    }
    private void temperatureAlertNotification(float temps) {

        SharedPreferences sharedPreference = getSharedPreferences("SharedPreference", 0);
        String TempWarnLevelStatus = sharedPreference.getString("TempWarnLevel", "");
        String TempWarnNotify = sharedPreference.getString("TempWarnNotify", "");

        if (!TempWarnLevelStatus.equals("Off")){
            float IntTempWarnLevel=Float.parseFloat(TempWarnLevelStatus);
            if (IntTempWarnLevel>temps){
                SharedPreferences.Editor ee=sharedPreference.edit();
                ee.putString("TempWarnNotify","");
                ee.apply();
            }

            if (temps>IntTempWarnLevel && TempWarnNotify.equals("")  ){
                SharedPreferences.Editor ee=sharedPreference.edit();
                ee.putString("TempWarnNotify","y");
                ee.apply();

                DefaultNotificationTone();
                Intent i=new Intent(getApplicationContext(), TemperatureAlertDialog.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        }
    }
    public void DefaultNotificationTone(){
        Uri defaultRingtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        MediaPlayer mediaPlayer = new MediaPlayer();

        try {
            mediaPlayer.setDataSource(MyService.this, defaultRingtoneUri);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_NOTIFICATION);
            mediaPlayer.prepare();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp)
                {
                    mp.release();
                }
            });
            mediaPlayer.start();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getPlugTypeString(int plugged) {
        String plugType = "Unknown";
        switch (plugged) {
            case BatteryManager.BATTERY_PLUGGED_AC:
                plugType = "Power Adapter";
                break;
            case BatteryManager.BATTERY_PLUGGED_USB:
                plugType = "USB";
                break;
        }
        return plugType;
    }

    public void LowOrCustomBatteryAlarm(String level, String strChargeVia){
        SharedPreferences sharedPreference = getSharedPreferences("SharedPreference", 0);
        String LowBatteryLevelStatus = sharedPreference.getString("LowBatteryLevel", "");
        String LowOrCustomBatteryLevelShow = sharedPreference.getString("LowOrCustomBatteryLevelShow", "");

        int IntLevel=Integer.parseInt(level);
        try{
            int IntLowBatteryLevelStatus = Integer.parseInt(LowBatteryLevelStatus);
            if (IntLowBatteryLevelStatus<IntLevel){
                SharedPreferences.Editor ee=sharedPreference.edit();
                ee.putString("LowOrCustomBatteryLevelShow","");
                ee.apply();
            }
        }catch (Exception e){}

        if (level.equals(LowBatteryLevelStatus) && strChargeVia.equals("Unknown") && LowOrCustomBatteryLevelShow.equals("")){
            SharedPreferences.Editor ee=sharedPreference.edit();
            ee.putString("LowOrCustomBatteryLevelShow","y");
            ee.apply();
            AlertNotification(level);

            Log.e(TAG , "AlertNotification: "+level);

        }
//        ChargingNotification(strChargeVia,level);
    }


    public void AlertNotification(String batteryHealth){

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);

        mBuilder.setSmallIcon(R.drawable.notification_icon);
        mBuilder.setContentTitle("Full Battery Alarm");
        mBuilder.setContentText("Battery Level : "+batteryHealth+".\n Plz connect the charger!");
        mBuilder.setDefaults(Notification.DEFAULT_VIBRATE);
        mBuilder.setPriority(android.support.v4.app.NotificationCompat.PRIORITY_HIGH);


        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(contentIntent);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.notify(0, mBuilder.build());
        }
    }



    public void ChargingNotification(String strChargeVia,String level){
        levelTop = level;
        Log.e(TAG , "ChargingNotification:: "+level);
        BatteryManager batteryManager = (BatteryManager) getApplicationContext().getSystemService(Context.BATTERY_SERVICE);
        long v = 0;
        float v1 = 0;
        try{
            if (Build.VERSION.SDK_INT <21) {
                if (strChargeVia.equals("Power Adapter")){
                    v1= (float) abs(1.5*1000);
                }
                if (strChargeVia.equals("USB")){
                    v1= (float) abs(500);
                }
            }
            else {
                v =batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW);
                v1=abs(v/1000);
            }
        }catch (Exception e){}

        String TotalCapacity=getBatteryCapacity();
        int remainCapa=setRemainingCapacity(Integer.parseInt(level),TotalCapacity);
        double capacity=Integer.parseInt(TotalCapacity);
        int forChargCapa= (int) (capacity-remainCapa);


        float result= (forChargCapa/v1);
        //..
        double result1= abs(capacity/v1);
        double i=result1/capacity;
        float r = (float) (Math.floor(i*forChargCapa * 100) / 100);

        result = (float) (Math.floor(result * 100) / 100);
        String hour="",str_min;
        int min=0;
        try {
            //String finStr=String.valueOf(result);
            String finStr=String.valueOf(r);
            StringTokenizer tokens = new StringTokenizer(finStr, ".");
            hour = tokens.nextToken();
            str_min = tokens.nextToken();
            min=(Integer.parseInt(str_min)/60)/100;
        }catch (Exception e){

        }


        SharedPreferences sharedPreference = getSharedPreferences("SharedPreference", 0);
        String ChargingNotiShowingOrNot = sharedPreference.getString("ChargingNotiShowingOrNot", "");


        if (!strChargeVia.equals("Unknown") && ChargingNotiShowingOrNot.equals("")){

            String DisConnectTime="",ConnectTime = "";
            try{
                ConnectTime =sharedPreference.getString("ChargingConnectTime", "");
            }catch (Exception e){e.printStackTrace();}
            try{
                DisConnectTime =sharedPreference.getString("ChargingDisconnetTime", "");
            } catch (Exception e){e.printStackTrace();}

            Intent ii=new Intent(getApplicationContext(), ChargingNotificationLayout.class);
            ii.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ii.putExtra("ConnectTime",ConnectTime);
            ii.putExtra("DisConnectTime",DisConnectTime);
            startActivity(ii);

            SharedPreferences.Editor ee=sharedPreference.edit();
            ee.putString("ChargingNotiShowingOrNot","y");
            ee.putString("ChargingConnectTime",getCurrentTime());
            ee.apply();
        }
        else{
            if(!strChargeVia.equals("Unknown") && ChargingNotiShowingOrNot.equals("y")) {
                SharedPreferences.Editor ee=sharedPreference.edit();
                ee.putString("ChargingDisconnetTime",getCurrentTime());
                ee.apply();
            }
        }

//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
//        //if (Build.VERSION.SDK_INT <21) {
//        mBuilder.setSmallIcon(R.drawable.ic_battery_charging_full_black_24dp);
//        /*}else{
//            mBuilder.setSmallIcon(R.drawable.full_battery_alarm_logo);
//        }*/
//        mBuilder.setContentTitle("111Charging..");
//        //mBuilder.setAutoCancel(true);
//        mBuilder.setContentText("Level: "+level+"% ");
////        mBuilder.setContentText("Level: "+level+"% | Remaining Time: "+(hour)+"h "+(min)+"min");
//        mBuilder.setPriority(android.support.v4.app.NotificationCompat.PRIORITY_HIGH);
//
//        Intent notificationIntent = new Intent(this, MainActivity.class);
//        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
//                PendingIntent.FLAG_UPDATE_CURRENT);
//        mBuilder.setContentIntent(contentIntent);
//
//        manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
//        String NotifyStatus = sharedPreference.getString("NotifyStatus", "");
//
//
//        if ( NotifyStatus.equals("true"))
//            if (!strChargeVia.equals("Unknown")){
//                mBuilder.setOngoing( true );
//
//                // Add as notificationg
//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                    int importance = NotificationManager.IMPORTANCE_HIGH;
//                    NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, importance);
//                    notificationChannel.enableLights(true);
//                    notificationChannel.setLightColor(Color.RED);
//                    //notificationChannel.enableVibration(true);
//                    //notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
//                    assert manager != null;
//                    mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
//                    manager.createNotificationChannel(notificationChannel);
//                    manager.notify(0, mBuilder.getNotification());
//
//                } else {
//                    assert manager != null;
//                    manager.notify(0, mBuilder.getNotification());
//                }
//            }
//            else {
//                if (manager != null) {
//                    manager.cancel(0);
//                }if (manager != null) {
////                Log.e(TAG, "manager:: "+manager.getNotificationChannels());
//                    //  manager.deleteNotificationChannel(NOTIFICATION_CHANNEL_ID);
//                }
//            }
//        else if (manager != null) {
//            Log.e(TAG, "manager2:: "+manager);
//            manager.deleteNotificationChannel(NOTIFICATION_CHANNEL_ID);
//        }





        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID2, NOTIFICATION_CHANNEL_NAME2, importance);
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
                .setSmallIcon(R.drawable.ic_battery_charging_full_black_24dp)
                // .setColor(ContextCompat.getColor(context, R.color.color))
                .setWhen(System.currentTimeMillis())
                .setContentTitle("Charging..")
                .setContentText("Level: "+level+"% ")
               // .setContentText("Level: "+level+"% | Remaining Time: "+(hour)+"h "+(min)+"min")
                .setPriority(Notification.PRIORITY_MAX)
//                .setCategory(Notification.CATEGORY_SERVICE)
//                .setTicker("VINlocity")
//                .setContentText("VINlocity Driver is tracking.")
//                .setDefaults(Notification.DEFAULT_ALL)
                .setOnlyAlertOnce(true)
                .setSound(null)
                .setOngoing(true);
        notificationManager.notify(11, builder.build());
        startForeground(11, builder.build());





    }

    public static String getCurrentTime() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }
    public static String getCurrentDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }




    private void startSS() {


//        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
//        NotificationCompat.Builder builder = null;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            int importance = NotificationManager.IMPORTANCE_DEFAULT;
//            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID2, NOTIFICATION_CHANNEL_NAME2, importance);
//            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
//            notificationManager.createNotificationChannel(notificationChannel);
//            builder = new NotificationCompat.Builder(getApplicationContext(), notificationChannel.getId());
//        } else {
//            builder = new NotificationCompat.Builder(getApplicationContext());
//        }
//
//        builder = builder
//                .setSmallIcon(R.drawable.notification_icon)
//                // .setColor(ContextCompat.getColor(context, R.color.color))
//                .setWhen(System.currentTimeMillis())
//                .setContentTitle("Service...")
//                .setPriority(Notification.PRIORITY_MAX)
////                .setCategory(Notification.CATEGORY_SERVICE)
////                .setTicker("VINlocity")
////                .setContentText("VINlocity Driver is tracking.")
////                .setDefaults(Notification.DEFAULT_ALL)
//                .setOngoing(true);
//        notificationManager.notify(11, builder.build());
//        startForeground(11, builder.build());


        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID2, NOTIFICATION_CHANNEL_NAME2, importance);
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
                .setSmallIcon(R.drawable.ic_battery_charging_full_black_24dp)
                // .setColor(ContextCompat.getColor(context, R.color.color))
                .setWhen(System.currentTimeMillis())
                .setContentTitle("Charging..")
                .setContentText("Level: "+levelTop+"% ")
                // .setContentText("Level: "+level+"% | Remaining Time: "+(hour)+"h "+(min)+"min")
                .setPriority(Notification.PRIORITY_MAX)
//                .setCategory(Notification.CATEGORY_SERVICE)
//                .setTicker("VINlocity")
//                .setContentText("VINlocity Driver is tracking.")
//                .setDefaults(Notification.DEFAULT_ALL)
                .setOnlyAlertOnce(true)
                .setSound(null)
                .setOngoing(true);
        notificationManager.notify(11, builder.build());
        startForeground(11, builder.build());



    }

    private void stopForegroundService() {

        Log.d(TAG_FOREGROUND_SERVICE, "Stop foreground service.");

        // Stop foreground service and remove the notification.
        stopForeground(true);

        // Stop the foreground service.
        stopSelf();
    }





}
