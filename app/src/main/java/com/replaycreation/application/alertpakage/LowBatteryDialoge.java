package com.replaycreation.application.alertpakage;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.replaycreation.application.R;

public class LowBatteryDialoge extends Activity {

    TextView txtLowBatteryLevel,levelPercentage;
    ImageView cancel,batteryIcon;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_low_battery_dialoge);
        MobileAds.initialize(this, "ca-app-pub-7160073219914148~7699452512");


        mAdView = (AdView) findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);

        AdRequest request = new AdRequest.Builder()
                .addTestDevice("83588BB4D69EC1104E3E1A684562C91C")
                .build();
        mAdView.loadAd(request);

        SharedPreferences sharedPreference = getSharedPreferences("SharedPreference", 0);
        String whenNotifyUser = sharedPreference.getString("whenNotifyUser", "");

        txtLowBatteryLevel=(TextView)findViewById(R.id.txtLowBatteryLevel);
        levelPercentage=(TextView)findViewById(R.id.levelPercentage);
        levelPercentage.setText("Battery <"+whenNotifyUser+"%");
        txtLowBatteryLevel.setText("Your device battery level "+whenNotifyUser+"%. Its time to charge your device.");
        batteryIcon=(ImageView) findViewById(R.id.batteryIcon);
        batteryIcon.startAnimation(AnimationUtils.loadAnimation(LowBatteryDialoge.this, R.anim.click));
        cancel=(ImageView) findViewById(R.id.imgCancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(final Context c, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);

            //int voltage = intent.getIntExtra("voltage", 0);
            //int temperature = intent.getIntExtra("temperature", 0);
            //int health = intent.getIntExtra("health", 0);
            int plugged = intent.getIntExtra("plugged", -1);

            String ChargingBy=getPlugTypeString(plugged);
            if (!ChargingBy.equals("Unknown")){
                finish();
            }

        }
    };

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
}
