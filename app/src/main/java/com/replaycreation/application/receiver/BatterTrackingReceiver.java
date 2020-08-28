package com.replaycreation.application.receiver;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

import com.replaycreation.application.StopAlarmActivity;
import com.replaycreation.application.service.ServiceActivity;

/**
 * Created by Nandakumar on 9/14/2016.
 */
public class BatterTrackingReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        int batteryPercentage = -1;
        if (level != -1 && scale != -1) {
            batteryPercentage = (int) ((level / (float) scale) * 100f);
        }

        if (batteryPercentage == batteryPercentage){
            Intent alarmIntent = new Intent(context, StopAlarmActivity.class);
            alarmIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(alarmIntent);
            context.stopService(new Intent(context, ServiceActivity.class));
        }
    }
}
