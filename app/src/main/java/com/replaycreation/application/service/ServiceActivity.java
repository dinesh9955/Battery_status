package com.replaycreation.application.service;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.BatteryManager;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.RemoteViews;

import com.replaycreation.application.MainActivity;
import com.replaycreation.application.R;
import com.replaycreation.application.StopAlarmActivity;
import com.replaycreation.application.alertpakage.ChargingNotificationLayout;
import com.replaycreation.application.alertpakage.LowBatteryDialoge;
import com.replaycreation.application.alertpakage.TemperatureAlertDialog;
import com.replaycreation.application.database.PowerEventsTable;
import com.replaycreation.application.receiver.AlarmReceiver;
import com.replaycreation.application.theftalarm.StopTheftAlarmActivity;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.StringTokenizer;

import static android.support.v4.app.NotificationCompat.DEFAULT_VIBRATE;
import static java.lang.Math.abs;

/**
 * Created by GV Team on 14-04-2017.
 */

public class ServiceActivity extends Service {
    public static final String TAG = "ServiceActivity";
    public static final String BATTERY_UPDATE = "battery";
    String NOTIFICATION_CHANNEL_ID = "1000";
    String NOTIFICATION_CHANNEL_ID2 = "2000";
    String NOTIFICATION_CHANNEL_NAME = "FullBatteryAlarm";
    String NOTIFICATION_CHANNEL_NAME2 = "FullBatteryAlarm2";

    int counter = 0;

    NotificationManager mNotificationManager;
    int NOTIFICATION_ID = 1;
    boolean isRuning=false;
    private IBinder mBinder = new MyBinder();

    public ServiceActivity() {
        super();
    }

    private static String LOG_TAG = "ServiceActivity";
    public class MyBinder extends Binder {
        public ServiceActivity getService() {
            return ServiceActivity.this;
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        Log.v(LOG_TAG, "in onBind");
        return null;
    }
    /*@Override
    public void onRebind(Intent intent) {
        Log.v(LOG_TAG, "in onRebind");
        super.onRebind(intent);
    }
    @Override
    public boolean onUnbind(Intent intent) {
        Log.v(LOG_TAG, "in onUnbind");
        return true;
    }*/



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//
//        String input = "";
//
//        if(intent  != null){
//            if (intent.hasExtra("inputExtra")) {
//                input = intent.getStringExtra("inputExtra");
//            }
//        }


//        if (intent.hasExtra("battery")) {
//            input = intent.getStringExtra("battery");
//        }


        createNotificationChannel();
//        Notification notification = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID2)
//                .setContentTitle("Full Battery Alarm Notification")
//                .setContentText(input)
//                .setDefaults(DEFAULT_VIBRATE)
//                //.setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
//                .setLights(Color.YELLOW, 1000, 300)
//                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
//                .setPriority(android.support.v4.app.NotificationCompat.PRIORITY_MAX)
//                .setSmallIcon(R.drawable.ic_battery_charging_full_black_24dp)
//                //.setContentIntent(pendingIntent)
//                .build();
        //startForeground(0, notification);
//        AlarmReceiver.startAlarms(ServiceActivity.this.getApplicationContext());

        Log.e(TAG,  "intent:: "+intent);
        Log.e(TAG,  "BATTERY_UPDATE:: "+BATTERY_UPDATE);

        if(intent != null){
            if (intent.hasExtra(BATTERY_UPDATE)){
                new BatteryCheckAsync(this).execute();
            }
        }

        return START_STICKY;
    }




    @Override
    public void onCreate() {
        super.onCreate();
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
        ServiceActivity activity;
        BatteryCheckAsync(ServiceActivity serviceActivity) {
            this.activity=serviceActivity;
        }

        @Override
        protected String doInBackground(Object... arg0) {

            //Battery State check - create log entries of current battery state
            IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            Intent batteryStatus = ServiceActivity.this.registerReceiver(null, ifilter);

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
                    Log.e(TAG, "mNotificationManager:: "+mNotificationManager);
                    if(mNotificationManager != null){
                        mNotificationManager.deleteNotificationChannel("10000");
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
                    Intent i=new Intent(activity,StopTheftAlarmActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    activity.startActivity(i);
                }
            }

            int myBatteryLevel = Integer.parseInt(level);
            int setBatteryLevel = Integer.parseInt(FullBatteryLevel);


            if (myBatteryLevel >= setBatteryLevel && AlarmStatus.equals("ON") && !strChargeVia.equals("Unknown")) {
                Log.e(TAG , "AAAAAAAA");

                String triggered=sharedPreference.getString("Triggered", "");

                if(triggered.equals("") || triggered.equals("ON")){
                    Log.e(TAG, "BBBBBBBB");
                    SharedPreferences.Editor ee=sharedPreference.edit();
                    ee.putString("Triggered","OFF");
                    ee.apply();

                    PendingIntent pendingIntent = PendingIntent.getActivity(
                            activity,
                            777,
                            new Intent(activity, StopAlarmActivity.class),
                            PendingIntent.FLAG_UPDATE_CURRENT);
                    AlarmManager alarmManager = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
                    long triggerAtMillis = System.currentTimeMillis() + 5000;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        AlarmManager.AlarmClockInfo alarmClockInfo = new AlarmManager.AlarmClockInfo(triggerAtMillis, pendingIntent);
                        if (alarmManager != null) {
                            alarmManager.setAlarmClock(alarmClockInfo, pendingIntent);
                        }
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        if (alarmManager != null) {
                            alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
                        }
                    } else {
                        if (alarmManager != null) {
                            alarmManager.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
                        }
                    }
                    /*Intent i = new Intent(activity, StopAlarmActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    activity.startActivity(i);*/
                }else{
                    Log.e(TAG, "CCCCCCCCCCC");
                }
            }else{
                Log.e(TAG, "DDDDDDDDD");
            }






//            String check = sharedPreference.getString("check", "OFF");
//
//            if (myBatteryLevel >= setBatteryLevel && AlarmStatus.equals("ON") && !strChargeVia.equals("Unknown")) {
//                Log.e(TAG , "AAAAAAAA");
//                if(check.equalsIgnoreCase("OFF")){
//                    Log.e(TAG , "1111111");
//                    Log.e(TAG , "BBBBBBBB");
//                    SharedPreferences.Editor ee=sharedPreference.edit();
//                    ee.putString("Triggered","ON");
//                    ee.apply();
//
//                    if(counter == 0){
//                        PendingIntent pendingIntent = PendingIntent.getActivity(
//                                activity,
//                                777,
//                                new Intent(activity, StopAlarmActivity.class),
//                                PendingIntent.FLAG_UPDATE_CURRENT);
//                        AlarmManager alarmManager = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
//                        long triggerAtMillis = System.currentTimeMillis() + 5000;
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                            AlarmManager.AlarmClockInfo alarmClockInfo = new AlarmManager.AlarmClockInfo(triggerAtMillis, pendingIntent);
//                            if (alarmManager != null) {
//                                alarmManager.setAlarmClock(alarmClockInfo, pendingIntent);
//                            }
//                        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                            if (alarmManager != null) {
//                                alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
//                            }
//                        } else {
//                            if (alarmManager != null) {
//                                alarmManager.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
//                            }
//                        }
//
//                    }
//
//
//                    counter = 1;
//
////                       }
//
//                }else{
//                    SharedPreferences.Editor ee1 = sharedPreference.edit();
//                    ee1.putString("check","OFF");
//                    ee1.apply();
//                }
//            }else{
//                Log.e(TAG , "2222222222");
//                SharedPreferences.Editor ee1 = sharedPreference.edit();
//                ee1.putString("check","OFF");
//                ee1.apply();
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
            Intent i=new Intent(getApplicationContext(),LowBatteryDialoge.class);
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
                Intent i=new Intent(getApplicationContext(),TemperatureAlertDialog.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        }
    }
    public void DefaultNotificationTone(){
        Uri defaultRingtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        MediaPlayer mediaPlayer = new MediaPlayer();

        try {
            mediaPlayer.setDataSource(ServiceActivity.this, defaultRingtoneUri);
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

            Intent ii=new Intent(getApplicationContext(),ChargingNotificationLayout.class);
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

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        //if (Build.VERSION.SDK_INT <21) {
            mBuilder.setSmallIcon(R.drawable.ic_battery_charging_full_black_24dp);
        /*}else{
            mBuilder.setSmallIcon(R.drawable.full_battery_alarm_logo);
        }*/
        mBuilder.setContentTitle("Charging..");
        //mBuilder.setAutoCancel(true);
        mBuilder.setContentText("Level: "+level+"% ");
//        mBuilder.setContentText("Level: "+level+"% | Remaining Time: "+(hour)+"h "+(min)+"min");
        mBuilder.setPriority(android.support.v4.app.NotificationCompat.PRIORITY_HIGH);

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(contentIntent);

        NotificationManager manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        String NotifyStatus = sharedPreference.getString("NotifyStatus", "");


        if ( NotifyStatus.equals("true"))
        if (!strChargeVia.equals("Unknown")){
            mBuilder.setOngoing( true );

            // Add as notificationg
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, importance);
                notificationChannel.enableLights(true);
                notificationChannel.setLightColor(Color.RED);
                //notificationChannel.enableVibration(true);
                //notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                assert manager != null;
                mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
                manager.createNotificationChannel(notificationChannel);
                manager.notify(0, mBuilder.getNotification());

            } else {
                assert manager != null;
                manager.notify(0, mBuilder.getNotification());
            }
        }
        else {
            if (manager != null) {
                manager.cancel(0);
            }if (manager != null) {
//                Log.e(TAG, "manager:: "+manager.getNotificationChannels());
              //  manager.deleteNotificationChannel(NOTIFICATION_CHANNEL_ID);
            }
        }
        else if (manager != null) {
            Log.e(TAG, "manager2:: "+manager);
            manager.deleteNotificationChannel(NOTIFICATION_CHANNEL_ID);
        }







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

    @Override
    public void onDestroy(){
        //unregisterReceiver(batterTrackingReceiver);
        super.onDestroy();
        counter = 0;
    }


    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        counter = 0;
    }

    private void ShowNotification(Context context, String fullBatteryLevel) {
        String content="";
        content = ("Your mobile fully charged");

        MediaPlayer mMediaPlayer=new MediaPlayer();
        mMediaPlayer = MediaPlayer.create(context, R.raw.full_battery_alarm);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setLooping(true);
        mMediaPlayer.start();

        Intent add_event_intent = new Intent(context, StopAlarmActivity.class);
        add_event_intent.putExtra("Action","Stop Alarm");
        add_event_intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        PendingIntent add_event_pendingIntent = PendingIntent.getActivity(context, 6, add_event_intent, 0); // PendingIntent.FLAG_IMMUTABLE

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_battery_charging_full_black_24dp)
                .setContentTitle("Stop Alarm")
                .addAction(R.drawable.ic_stop, "Stop Alarm", add_event_pendingIntent)
                .setColor(ContextCompat.getColor(context, R.color.color1))
                .setContentText(content);
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent resultIntent = new Intent(context, StopAlarmActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 666, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        /*String[] events = new String[6];
        int todoSize;
        if (todayList.size() > 5) {
            todoSize = 5;
            events[5] = "...";
        } else {
            todoSize = todayList.size();
        }
        for (int i = 0; i < todoSize; i++) {
            if (todayList.get(i) != null) {
                events[i] = todayList.get(i).get(TASK_NAME);
            }
        }*/
        //inboxStyle.setSummaryText(content);
        inboxStyle.setBigContentTitle("Your battery reach at ");
        //for (int i = 0; i < events.length; i++) {
            inboxStyle.addLine("Level : "+fullBatteryLevel+" %");
        //}
        mBuilder.setStyle(inboxStyle);
        mBuilder.setOngoing(true);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
        {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel("10000", "STOP_ALARM_NOTIFICATION_CHANNEL", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert mNotificationManager != null;
            mBuilder.setChannelId("10000");
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(1, mBuilder.build());
    }
    private void sendNotification() {

        RemoteViews expandedView = new RemoteViews(getPackageName(), R.layout.notification_layout);
        //expandedView.setTextViewText(R.id.timestamp, DateUtils.formatDateTime(this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME));
        //expandedView.setTextViewText(R.id.notification_message, mEditText.getText());
        //expandedView.setTextViewText(R.id.imgStop, mEditText.getText());
        // adding action to left button
        Intent leftIntent = new Intent(this, StopAlarmActivity.class);
        leftIntent.setAction("left");
        expandedView.setOnClickPendingIntent(R.id.imgStop, PendingIntent.getService(this, 0, leftIntent, PendingIntent.FLAG_UPDATE_CURRENT));
        // adding action to right button
        //Intent rightIntent = new Intent(this, NotificationIntentService.class);
        //rightIntent.setAction("right");
        //expandedView.setOnClickPendingIntent(R.id.right_button, PendingIntent.getService(this, 1, rightIntent, PendingIntent.FLAG_UPDATE_CURRENT));

        //RemoteViews collapsedView = new RemoteViews(getPackageName(), R.layout.view_collapsed_notification);
        //collapsedView.setTextViewText(R.id.timestamp, DateUtils.formatDateTime(this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME));

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                // these are the three things a NotificationCompat.Builder object requires at a minimum
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle(getString(R.string.app_name))
                .setContentText("Your mobile fully charged")
                // notification will be dismissed when tapped
                .setAutoCancel(false)
                // tapping notification will open MainActivity
                .setContentIntent(PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0))
                // setting the custom collapsed and expanded views
                //.setCustomContentView(collapsedView)
                .setCustomBigContentView(expandedView)
                // setting style to DecoratedCustomViewStyle() is necessary for custom views to display
                .setStyle(new android.support.v4.app.NotificationCompat.DecoratedCustomViewStyle());

        // retrieves android.app.NotificationManager
        NotificationManager notificationManager = (android.app.NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //notificationManager.notify(1, builder.build());

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
        {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel("10000", "STOP_ALARM_NOTIFICATION_CHANNEL", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert notificationManager != null;
            builder.setChannelId("10000");
            notificationManager.createNotificationChannel(notificationChannel);
        }
        assert notificationManager != null;
        notificationManager.notify(1, builder.build());
    }

}
