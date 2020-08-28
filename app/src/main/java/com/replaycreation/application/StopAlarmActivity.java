package com.replaycreation.application;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.content.pm.ActivityInfo;
import java.io.IOException;
import android.view.Window;
import android.os.PowerManager;

import com.replaycreation.application.service.AlarmService;


public class StopAlarmActivity extends AppCompatActivity {

    private static final String TAG = "StopAlarmActivity";
    MediaPlayer mMediaPlayer;
    SharedPreferences sharedPreference;
    boolean checkSilentMode=false;
    Vibrator vibrator;
    private PowerManager.WakeLock wl;
    boolean playerStatus=true;

    @SuppressLint("InvalidWakeLockTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_alarm);

        Window window = getWindow();

        window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN // Not displaying keyboard on bg activity's EditText
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        sharedPreference = getSharedPreferences("SharedPreference", 0);
        mMediaPlayer = new MediaPlayer();
        TextView txt=findViewById(R.id.txtStop);


        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        SharedPreferences.Editor ee=sharedPreference.edit();
        ee.putString("deviceSoundLevel",String.valueOf(currentVolume));
        ee.apply();


        try{
            if (audioManager.getRingerMode() != AudioManager.RINGER_MODE_NORMAL ){
                String SilentModeStatus=sharedPreference.getString("SilentModeStatus", "");
                if (SilentModeStatus.equals("true")){
                    checkSilentMode=true;
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                    if (audioManager.getRingerMode() == AudioManager.RINGER_MODE_NORMAL){
                        Log.e(TAG , "11111111");
                        registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
                    }
                }
            }else if (audioManager.getRingerMode() == AudioManager.RINGER_MODE_NORMAL ){
                Log.e(TAG , "2222222");
                registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
            }

        }catch (Exception e){
            Log.e(TAG , "3333333");
            //Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }


        stopService(new Intent(getApplicationContext() , AlarmService.class));

        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor ee1 = sharedPreference.edit();
                ee1.putString("Triggered","OFF");
                ee1.apply();

                SharedPreferences.Editor ee2 = sharedPreference.edit();
                ee2.putInt("count", 0);
                ee2.apply();


                if (checkSilentMode){
                    try {
                        if (vibrator.hasVibrator()) {
                            vibrator.cancel();
                        }
                    }catch (Exception e){e.printStackTrace();}

                    mMediaPlayer.stop();

                    try{
                        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                        audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                    }catch (Exception e){e.printStackTrace();}

                    finish();
                }else {
                    try {
                        if (vibrator.hasVibrator()) {
                            vibrator.cancel();
                        }
                    }catch (Exception e){}
                    mMediaPlayer.stop();
                    finish();
                }
            }
        });
    }

    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context c, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            int voltage = intent.getIntExtra("voltage", 0);
            int temperature = intent.getIntExtra("temperature", 0);
            int health = intent.getIntExtra("health", 0);
            int plugged = intent.getIntExtra("plugged", -1);


            String strChargeVia=getPlugTypeString(plugged);
            String FullBatteryLevel = sharedPreference.getString("FullBatteryLevel", "");



            if (playerStatus){
                playerStatus=false;



//                if (!strChargeVia.equals("Unknown") && level==Integer.parseInt(FullBatteryLevel)){
//                    Log.e(TAG , "444444");
                    GetRinging();
//                }else {
//                    Log.e(TAG , "555555");
//                }
            }
            if (strChargeVia.equals("Unknown")){
                mMediaPlayer.stop();
                //if (!mMediaPlayer.isPlaying())
                    finish();
            }
        }
    };

    private String getPlugTypeString(int plugged) {
        String plugType = "Unknown";
        switch (plugged) {
            case BatteryManager.BATTERY_PLUGGED_AC:
                plugType = "Power Adapter";
                SharedPreferences.Editor ee=sharedPreference.edit();
                ee.putString("StopAlarmStatus","ON");
                ee.apply();
                break;
            case BatteryManager.BATTERY_PLUGGED_USB:
                plugType = "USB";
                SharedPreferences.Editor ee1=sharedPreference.edit();
                ee1.putString("StopAlarmStatus","ON");
                ee1.apply();
                break;
        }
        return plugType;
    }
    private void GetRinging() {

        SharedPreferences sharedPreference = getSharedPreferences("SharedPreference", 0);
        String RingtoneUri = sharedPreference.getString("RingtoneUri", "");
        String VibrationMode = sharedPreference.getString("OnVibrationOrRingMode", "");

        if(VibrationMode.equals("true")){
            Log.e(TAG , "66666666");
            vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            long[] pattern = {0, 300, 1000};
            vibrator.vibrate(pattern, 0);
        }else {
            Log.e(TAG , "77777777");
            String SoundeLevel = sharedPreference.getString("SoundeLevel", "");
            AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            int volumeL = 10;
            try{volumeL=Integer.parseInt(SoundeLevel);}catch (Exception e){}
            try{audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volumeL, 0);}catch (Exception e){}


            if (RingtoneUri.equals("Default")){
                Log.e(TAG , "88888888");
                mMediaPlayer = MediaPlayer.create(this, R.raw.full_battery_alarm);
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mMediaPlayer.setLooping(true);
                mMediaPlayer.start();
            }else{
                Log.e(TAG , "99999999");
                try {
                    Log.e(TAG , "AAAAAAAAAA");

                    mMediaPlayer.setDataSource(this, Uri.parse(RingtoneUri));
                    if (audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) != 0) {
                        Log.e(TAG , "BBBBBBBBBBBB");
                        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        mMediaPlayer.setLooping(true);
                        mMediaPlayer.prepare();
                        mMediaPlayer.start();
                    }
                }catch (IOException e) {
                    // TODO Auto-generated catch block
                    Log.e(TAG , "CCCCCCCCCCC");
                    e.printStackTrace();
                }
            }
        }
    }


    @Override
    public void onDestroy() {

//        wl.release();
        String VLevel = sharedPreference.getString("deviceSoundLevel", "");
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, Integer.parseInt(VLevel), 0);

        try{
            if(mBatInfoReceiver!=null)
                unregisterReceiver(mBatInfoReceiver);
        }catch (Exception e){}

        try{
            Intent i=new Intent(StopAlarmActivity.this,AdMobFullBatteryActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        }catch(Exception e) {}
        super.onDestroy();
//        vibrator.cancel();
    }

    public void Launch(Context launchContext){
        Intent alarmActivityIntent = new Intent(launchContext, StopAlarmActivity.class);
        alarmActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //alarmActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        launchContext.startActivity(alarmActivityIntent);
    }
}
