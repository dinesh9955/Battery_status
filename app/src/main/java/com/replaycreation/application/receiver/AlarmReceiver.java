package com.replaycreation.application.receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;
import android.util.Log;

import com.replaycreation.application.service.ConnectivityService;

/**
 * Created by Servicing1 on 15-04-2017.
 */

public class AlarmReceiver extends BroadcastReceiver {
    private static final String TAG = "AlarmReceiver";
    private static final int REQUEST_CODE = 777;
    public static final long ALARM_INTERVAL = DateUtils.MINUTE_IN_MILLIS;


    // Call this from your service
    public static void startAlarms(final Context context) {

        final AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        // start alarm right away
        if (manager != null) {
            manager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 0, ALARM_INTERVAL,
                    getAlarmIntent(context));
        }
    }

    /*
     * Creates the PendingIntent used for alarms of this receiver.
     */
    private static PendingIntent getAlarmIntent(final Context context) {
        return PendingIntent.getBroadcast(context, REQUEST_CODE, new Intent(context, AlarmReceiver.class), PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Override
    public void onReceive(final Context context, final Intent intent) {

       // MyWakeLock.acquire(context);


        Log.e(TAG, "onReceive().." + intent.getAction());
//        SharedPreferences sharedPreference = context.getSharedPreferences("SharedPreference", 0);
//        SharedPreferences.Editor ee1 = sharedPreference.edit();
//        ee1.putString("Triggered","ON");
//        ee1.apply();

        if(intent != null){
            if(intent.getAction() == Intent.ACTION_POWER_CONNECTED){
                Intent intentStart = new Intent(context, ConnectivityService.class);
                intentStart.putExtra("key" , "powerConnected");
                context.startService(intentStart);
            }else if(intent.getAction() == Intent.ACTION_POWER_DISCONNECTED){
//                Intent intentStart = new Intent(context, ConnectivityService.class);
//                intentStart.putExtra("key" , "powerDisconnected");
//                context.startService(intentStart);
            }
        }

      //  MyWakeLock.release();



//        if (intent.getAction() == Intent.ACTION_BATTERY_CHANGED) {
//            IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
//            context.registerReceiver(this, filter);
//            context.registerReceiver(null, filter);
//
//            PendingIntent operation = PendingIntent.getBroadcast(
//                    context,
//                    0,
//                    new Intent(intent.getAction()),
//                    PendingIntent.FLAG_UPDATE_CURRENT);
//            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//            alarmManager.set(
//                    AlarmManager.RTC_WAKEUP,
//                    System.currentTimeMillis() + 3000,
//                    operation);
//        } else if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {
//            context.unregisterReceiver(this);
//            int level = intent.getIntExtra("level", 0);
//            Log.d(TAG, "level: " + level);
//            // TODO: log battery level to file
//        }




//
//        if (intent == null){
//            // No intent was passed to your receiver; this also really shouldn't happen
//            return;
//        }
//
//
//        if (intent == null){
//            // No intent was passed to your receiver; this also really shouldn't happen
//            return;
//        }
//       // if (intent.getAction() == null) {
//            // If you called your Receiver explicitly, this is what you should expect to happen
//            /*Intent monitorIntent = new Intent(context, ServiceActivity.class);
//            monitorIntent.putExtra(ServiceActivity.BATTERY_UPDATE, true);
//            context.startService(monitorIntent);*/
//            try{
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                    Intent monitorIntent1 = new Intent(context, ServiceActivity.class);
//                    monitorIntent1.putExtra(ServiceActivity.BATTERY_UPDATE, true);
//                    context.startService(monitorIntent1);
////                    ContextCompat.startForegroundService(context.getApplicationContext(),monitorIntent);
//
//                    /*IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
//                    context.registerReceiver(battery_receiver, filter);*/
//                } else {
//                    Intent monitorIntent2 = new Intent(context, ServiceActivity.class);
//                    monitorIntent2.putExtra(ServiceActivity.BATTERY_UPDATE, true);
//                    context.startService(monitorIntent2);
//                }
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//
//      //  }
//



    }





}