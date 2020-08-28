package com.replaycreation.application.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import android.widget.Toast;


public class Restarter extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences sharedPreference = context.getSharedPreferences("SharedPreference", 0);
        //Toast.makeText(context, "Service restarted", Toast.LENGTH_SHORT).show();

      //  int screenOff = false;
        Log.e("Broadcast Listened", "Service tried to stop: "+intent.getAction());

        SharedPreferences.Editor ee1 = sharedPreference.edit();
        ee1.putString("Triggered","ON");
        ee1.apply();

        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            Log.e("Broadcast Listened", "ZZZZZ1111111");
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            Log.e("Broadcast Listened", "ZZZZZ2222222");
        } else if(intent.getAction().equals(Intent.ACTION_POWER_CONNECTED)) {
            Log.e("Broadcast Listened", "ZZZZZ3333333");
//            SharedPreferences.Editor ee1 = sharedPreference.edit();
//            ee1.putString("Triggered","ON");
//            ee1.apply();
        } else if(intent.getAction().equals(Intent.ACTION_POWER_DISCONNECTED)) {
            Log.e("Broadcast Listened", "ZZZZZ4444444");
        }



        //context.startService(new Intent(context , UpdateService2.class));

//         if(!ServiceTools.isServiceRunning(context, new UpdateService2().getClass().getName())){
//             context.startService(new Intent(context , UpdateService2.class));
//         }

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            context.startForegroundService(new Intent(context, YourService.class));
//        } else {
//            context.startService(new Intent(context, YourService.class));
//        }


//        String loadId = Prefs.with(context).getObject(SharedPreferencesName.CURRENT_RUNNING_LOAD, String.class);
//        if(loadId != null && !loadId.equalsIgnoreCase("")){
//            if(!ServiceTools.isServiceRunning(context, new MyService().getClass().getName())){
//                Intent intent1 = new Intent(context, MyService.class);
//                intent1.setAction(MyService.ACTION_START_FOREGROUND_SERVICE);
//                context.startService(intent1);
//            }
//        }
//



    }
}
