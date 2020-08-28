package com.replaycreation.application.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;

import com.replaycreation.application.service.ServiceActivity;


public class BroadcastReceiverClass extends BroadcastReceiver {
    public static final String ACTION_BOOT = "android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent) {
        try {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Intent monitorIntent = new Intent(context, ServiceActivity.class);
                monitorIntent.putExtra(ACTION_BOOT, true);
                //context.startService(monitorIntent);
                ContextCompat.startForegroundService(context,monitorIntent);
            } else {
                Intent monitorIntent = new Intent(context, ServiceActivity.class);
                monitorIntent.putExtra(ACTION_BOOT, true);
                context.startService(monitorIntent);
            }
            /*Intent monitorIntent = new Intent(context, ServiceActivity.class);
            monitorIntent.putExtra(ACTION_BOOT, true);
            context.startService(monitorIntent);*/
        }catch (Exception e){}
    }
}