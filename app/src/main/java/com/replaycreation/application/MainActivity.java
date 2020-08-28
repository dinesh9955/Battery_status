package com.replaycreation.application;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.hardware.camera2.CameraManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
/*import com.inmobi.ads.InMobiAdRequest;
import com.inmobi.ads.InMobiAdRequestStatus;
import com.inmobi.ads.InMobiNative;
import com.inmobi.sdk.InMobiSdk;*/
import com.replaycreation.application.app_intro.FullBatteryAlarmIntro;
import com.replaycreation.application.database.PowerEventsTable;
import com.replaycreation.application.receiver.AlarmReceiver;
import com.replaycreation.application.receiver.BroadcastReceiverClass;

import com.replaycreation.application.service.MyService;
import com.replaycreation.application.service.ServiceActivity;
import com.replaycreation.application.settings.SettingsActivity;
import com.replaycreation.application.theftalarm.StopTheftAlarmActivity;
import com.replaycreation.application.theftalarm.ThaftAlarmActivity;
import com.replaycreation.application.widgets.ProgressCircle;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import petrov.kristiyan.colorpicker.ColorPicker;

import static com.google.android.gms.wearable.DataMap.TAG;
import static java.lang.Math.abs;


public class MainActivity extends AppCompatActivity implements View.OnClickListener,NavigationView.OnNavigationItemSelectedListener {

    public static String TAG = "MainActivity";

    private TextView batteryLevel;
    private TextView voltageLevel;
    private TextView temperatureLevel,TxtCapacity,chargeBy,txtOnOff,TxtRemainingCapacity;
    ProgressCircle progressCircle;
    LinearLayout llChargeBy;

    double temps;


    int s;
    ImageView OnOff,theftImage;
    String ChargingLevel;
    PowerEventsTable dbClass;

    String whenNotifyUser="",theftAlarmStatus="off";
    SharedPreferences sharedPreference;

    String healthTv,batteryPctTv,pluggedTv,chargingStatusTv,capacityTv,voltageTv,tempTv,technologyTv,totalCapacity,FullAlarmStatus;

    final static int RQS_RINGTONEPICKER = 1;

    LinearLayout onoffLayout,theftAlarmLayout,settingsLayout;
    TextView txtTheft,txtSettings;
    String str_start="START",str_stop="STOP";
    TextView txt_Capacity,txthealth,txtVoltage,txtTemp,txtbrightness,txt_RemainingCapacity,flashTxt;
    NavigationView navigationView;
    MenuItem nav_reminder,nav_details,nav_alarm_ringtone,nav_language,nav_settings,nav_rate_us,nav_contact_us,nav_share,nav_theme;
    String Status,whichCheck;

    String whenNotify="-1",pos;

    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;

    String strChargeVia;
    SeekBar brightnessSeekBar;
    protected int set_brightness;
    static int CODE_WRITE_SETTINGS_PERMISSION=100;
    ImageView torch;

    SwitchCompat flash;
    private static final int PERMISSION_REQUEST_CODE = 200;
    LinearLayout mainLayout;

    private String[] mThumbColorCodes = {"#1abc9c","#16a085","#f1c40f","#f39c12","#2ecc71","#27ae60","#e67e22","#d35400","#3498db","#2980b9","#e74c3c","#bdc3c7",};
    private String[] mThumbColorNames = {
            "Turquoise", "Greensea",
            "Orange","Emerland", "Nephritis",
            "Carrot", "Pumpkin","Peterriver",
            "Belizehole", "Alizarin","Silver","default"
    };


    String colorName;
    int backposition = -1;
    public static Camera cam = null;

    private Camera camera;
    private boolean isFlashOn=true;
    private boolean hasFlash;
    private Camera.Parameters params;

    private AdView mAdView;

    ServiceActivity mBoundService;
    boolean mServiceBound = false;



//    public void setAlarm(long timeInMillis){
//        AlarmManager alarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        Intent intent=new Intent(this, StopAlarmActivity.class);
//        PendingIntent pendingIntent=PendingIntent.getBroadcast(this,0,intent,0);
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,timeInMillis,AlarmManager.INTERVAL_DAY,pendingIntent);
//        Toast.makeText(getApplicationContext(),"Alarm is Set",Toast.LENGTH_SHORT).show();
//
//
//    }



    private void startSS() {
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
                .setContentTitle("tracking.")
                .setPriority(Notification.PRIORITY_MAX)
                .setAutoCancel(true);
//                .setCategory(Notification.CATEGORY_SERVICE)
//                .setTicker("VINlocity")
//                .setContentText("VINlocity Driver is tracking.")
//                .setDefaults(Notification.DEFAULT_ALL)
               // .setOngoing(true);
        notificationManager.notify(13, builder.build());
        //startForeground(11, builder.build());

    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //InMobiSdk.init(MainActivity.this, "49543d3b31364c18a8bc6a84db03c359");
        MobileAds.initialize(this, "ca-app-pub-7160073219914148~7699452512");
        SetColorTheme();
        setContentView(R.layout.activity_main2);

        if(!ServiceTools.isServiceRunning(getApplicationContext(), new MyService().getClass().getName())){
            Intent intent = new Intent(getApplicationContext(), MyService.class);
            intent.setAction(MyService.ACTION_START_FOREGROUND_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(intent);
            } else {
                startService(intent);
            }


//            Intent intent = new Intent(getApplicationContext(), MyService.class);
//            intent.setAction(MyService.ACTION_START_FOREGROUND_SERVICE);
//            startService(intent);
        }


//        AlarmManager alarmMgr;
//        PendingIntent alarmIntent;
//
//        alarmMgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
//        Intent intent = new Intent(this, StopAlarmActivity.class);
//        alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
//
//        alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
//                SystemClock.elapsedRealtime() +
//                        20 * 1000, alarmIntent);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.SET_ALARM)!= PackageManager.PERMISSION_GRANTED){
            Log.d("Perm check:SET_ALARM", "Permission Denied");
            requestPermissions(new String[]{Manifest.permission.SET_ALARM},1);
        }else{
            Log.d("Perm check:SET_ALARM", "Permission Exists");
        }

//        AlarmManager am = (AlarmManager) getSystemService(getApplicationContext().ALARM_SERVICE);
//        Intent I = new Intent(getApplicationContext(),StopAlarmActivity.class);
//        PendingIntent P = PendingIntent.getBroadcast(getApplicationContext(), 0, I, PendingIntent.FLAG_CANCEL_CURRENT);
//        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 5000, P);

//        PendingIntent pendingIntent = PendingIntent.getActivity(
//                this,
//                777,
//                new Intent(this, StopAlarmActivity.class),
//                PendingIntent.FLAG_UPDATE_CURRENT);
//        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
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

      //  startSS();


//        AlarmManager manager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
//
//        Intent alarmIntent = new Intent(getApplicationContext(), StopAlarmActivity.class);
//        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(getApplicationContext(), 0, alarmIntent, 0);
//        int interval = 8000;
//        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent1);


        AppIntro();

        //AdMoB.......
//        mAdView = (AdView) findViewById(R.id.adView);
//        AdRequest request = new AdRequest.Builder()
//                .addTestDevice("83588BB4D69EC1104E3E1A684562C91C")
//                .build();
//        mAdView.loadAd(request);


        //INMOB....
//        InMobiNative nativeAd= new InMobiNative(MainActivity.this,1495838983200L, new InMobiNative.NativeAdListener() {
//            @Override
//            public void onAdLoadSucceeded(InMobiNative inMobiNative) {
//                Log.d(TAG, "onAdLoadSucceeded" + "Success");
//            }
//
//            @Override
//            public void onAdLoadFailed(InMobiNative inMobiNative, InMobiAdRequestStatus inMobiAdRequestStatus) {
//                Log.d(TAG, "onAdLoadFailed" + "Failed");
//            }
//
//            @Override
//            public void onAdDismissed(InMobiNative inMobiNative) {
//                Log.d(TAG, "onAdDismissed" + inMobiNative.toString());
//            }
//
//            @Override
//            public void onAdDisplayed(InMobiNative inMobiNative) {
//                Log.d(TAG, "onAdDisplayed" + inMobiNative.toString());
//            }
//
//            @Override
//            public void onUserLeftApplication(InMobiNative inMobiNative) {
//                Log.d(TAG, "onUserLeftApplication" + inMobiNative.toString());
//            }
//        });
//        nativeAd.load();


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        SetBackGroundColor();
        setSupportActionBar(toolbar);

        SetInitialValue();

        sharedPreference = getSharedPreferences("SharedPreference", 0);

        whenNotifyUser = sharedPreference.getString("whenNotifyUser", "");
        if (whenNotifyUser.equals("")){
            SharedPreferences.Editor ee6=sharedPreference.edit();
            ee6.putString("whenNotifyUser","30");
            ee6.apply();
        }

        whichCheck = sharedPreference.getString("whichCheck", "");
        try {
           if (whichCheck.length()!=0);
           else whichCheck="2";
        }catch (Exception e){
            whichCheck="2";
        }


        callMathAlarmScheduleService();
        theftAlarmStatus = sharedPreference.getString("theftAlarmStatus", "");
        FullAlarmStatus = sharedPreference.getString("FullAlarmStatus", "").trim();
        if (FullAlarmStatus.equals("")){
            FullAlarmStatus="ON";
            SharedPreferences.Editor ee1=sharedPreference.edit();
            ee1.putString("FullAlarmStatus","ON");
            ee1.apply();
        }

        ShowOrNotNotificationPanel();

        Status = sharedPreference.getString("status", "");


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();



        // get menu from navigationView
        Menu menu = navigationView.getMenu();
        // find MenuItem you want to change
        nav_reminder = menu.findItem(R.id.nav_reminder);
        nav_details = menu.findItem(R.id.nav_details);
        nav_language = menu.findItem(R.id.nav_language);
        nav_alarm_ringtone = menu.findItem(R.id.nav_alarm_ringtone);
        nav_settings = menu.findItem(R.id.nav_settings);
        nav_theme = menu.findItem(R.id.nav_theme);
        nav_share = menu.findItem(R.id.nav_share);
        nav_contact_us = menu.findItem(R.id.nav_contact_us);
        nav_rate_us = menu.findItem(R.id.nav_rate_us);
        navigationView.setNavigationItemSelectedListener(this);


        onoffLayout= (LinearLayout) findViewById(R.id.onoffLayout);
        theftAlarmLayout= (LinearLayout) findViewById(R.id.theftAlarmLayout);
        settingsLayout= (LinearLayout) findViewById(R.id.settingsLayout);



        txtTheft= (TextView) findViewById(R.id.txtTheft);
        txtSettings= (TextView) findViewById(R.id.txtSettings);
        txt_Capacity= (TextView) findViewById(R.id.textView);
        txt_RemainingCapacity= (TextView) findViewById(R.id.textViewRemaining);
        txthealth= (TextView) findViewById(R.id.textView1);
        txtVoltage= (TextView) findViewById(R.id.textView2);
        txtTemp= (TextView) findViewById(R.id.textView3);
        txtbrightness= (TextView) findViewById(R.id.txtbrightness);
        flashTxt= (TextView) findViewById(R.id.flashTxt);


        batteryLevel = (TextView)findViewById(R.id.battery_health);
        voltageLevel = (TextView)findViewById(R.id.voltage_level);
        temperatureLevel = (TextView)findViewById(R.id.temperature_level);
        TxtCapacity = (TextView)findViewById(R.id.TxtCapacity);
        TxtRemainingCapacity = (TextView)findViewById(R.id.TxtRemainingCapacity);
        llChargeBy=(LinearLayout)findViewById(R.id.llChargeBy);
        txtOnOff = (TextView)findViewById(R.id.txtOnOff);
        chargeBy = (TextView)findViewById(R.id.chargeBy);

        OnOff = (ImageView)findViewById(R.id.image1);
        theftImage = (ImageView)findViewById(R.id.image2);

        progressCircle = (ProgressCircle) findViewById(R.id.progress_circle);
        brightnessSeekBar = (SeekBar) findViewById(R.id.brightness);
        brightnessSeekBar.setMax(255);

        BrightnessAdjustment();
        try {
            brightnessSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    set_brightness = progress;
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    youDesirePermissionCode(MainActivity.this,set_brightness);
                }
            });
        }catch (Exception e){
        }


        progressCircle.startAnimation();
        //registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));


        FullAlarmStatus = sharedPreference.getString("FullAlarmStatus", "");
        if (FullAlarmStatus.equals("OFF")) {
            OnOff.setImageResource(R.drawable.ic_play_circle_outline_black_24dp);
            txtOnOff.setText(str_start);
        } if (FullAlarmStatus.equals("ON")) {
            OnOff.setImageResource(R.drawable.ic_stop_black_24dp);
            txtOnOff.setText(str_stop);
        }
        SetLanguage(Status);

        //FLASH LIGHT
        flash=(SwitchCompat)findViewById(R.id.flash);
        flash.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (Build.VERSION.SDK_INT <= 22) {
                    boolean hasFlash = MainActivity.this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
                    if (!hasFlash) {
                        IfFlashDoesNot();
                    }
                    if (hasFlash){
                        if (isChecked){
                            try{
                                cam = Camera.open();
                                params = cam.getParameters();
                                params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                                cam.setParameters(params);
                                cam.startPreview();
                            }catch (Exception e){}

                        } else{
                            try{
                                params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                                cam.setParameters(params);
                                cam.stopPreview();
                                cam.release();
                            }catch(Exception e){}
                        }
                    }
                }else {
                    boolean flashValue = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
                    if (flashValue) {
                        if (isChecked) {
                            try {
                                turnOnFlashLight();
                            } catch (Exception e) {
                            }
                        } else {
                            try {
                                turnOffFlashLight();
                            } catch (Exception e) {
                            }
                        }
                    }else {
                        Toast.makeText(MainActivity.this, "Flash Not Supported!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        onoffLayout.setOnClickListener(this);
        theftAlarmLayout.setOnClickListener(this);
        settingsLayout.setOnClickListener(this);
        brightnessSeekBar.setOnClickListener(this);
    }

    private void BrightnessAdjustment() {
        float curBrightnessValue = 0;
        try {
            curBrightnessValue = android.provider.Settings.System.getInt(
                    getContentResolver(),
                    android.provider.Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }

        //BRIGHTNESS
        int screen_brightness = (int) curBrightnessValue;
        brightnessSeekBar.setProgress(screen_brightness);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    public void AppIntro(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                //  Intro App Initialize SharedPreferences
                SharedPreferences getSharedPreferences = PreferenceManager
                        .getDefaultSharedPreferences(getBaseContext());

                //  Create a new boolean and preference and set it to true
                boolean isFirstStart = getSharedPreferences.getBoolean("firstStart", true);

                //  Check either activity or app is open very first time or not and do action
                if (isFirstStart) {

                    //  Launch application introduction screen
                    Intent i = new Intent(MainActivity.this, FullBatteryAlarmIntro.class);
                    startActivity(i);
                    SharedPreferences.Editor e = getSharedPreferences.edit();
                    e.putBoolean("firstStart", false);
                    e.apply();
                }
            }
        });
        t.start();
    }


    public void IfFlashDoesNot(){
        final AlertDialog alert = new AlertDialog.Builder(MainActivity.this).create();
        alert.setTitle("Error");
        alert.setMessage("Sorry, your device doesn't support flash light!");
        alert.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alert.dismiss();
            }
        });
        alert.show();
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
    public void SetColorTheme(){
        SharedPreferences sp = getSharedPreferences("SharedPreference", 0);
        String ThemeColorName;
        try{
            ThemeColorName = sp.getString("ThemeColorName", "");
        }catch (Exception e){
            ThemeColorName="d";
        }

        if (ThemeColorName.equals("Turquoise")){
            setTheme(R.style.AppThemeNoActionBarTurquoise);
        }else if (ThemeColorName.equals("Greensea")){
            setTheme(R.style.AppThemeNoActionBarGreensea);
        }else if (ThemeColorName.equals("Sunflower")){
            setTheme(R.style.AppThemeNoActionBarSunflower);
        }else if (ThemeColorName.equals("Orange")){
            setTheme(R.style.AppThemeNoActionBarOrange);
        }else if (ThemeColorName.equals("Emerland")){
            setTheme(R.style.AppThemeNoActionBarEmerland);
        }else if (ThemeColorName.equals("Nephritis")){
            setTheme(R.style.AppThemeNoActionBarNephritis);
        }else if (ThemeColorName.equals("Carrot")){
            setTheme(R.style.AppThemeNoActionBarCarrot);
        }else if (ThemeColorName.equals("Pumpkin")){
            setTheme(R.style.AppThemeNoActionBarPumpkin);
        }else if (ThemeColorName.equals("Peterriver")){
            setTheme(R.style.AppThemeNoActionBarPeterriver);
        }else if (ThemeColorName.equals("Belizehole")){
            setTheme(R.style.AppThemeNoActionBarBelizehole);
        }else if (ThemeColorName.equals("Alizarin")){
            setTheme(R.style.AppThemeNoActionBarAlizarin);
        }else if (ThemeColorName.equals("Silver")){
            setTheme(R.style.AppThemeNoActionBarSilver);
        }else {
            //setTheme(R.style.AppTheme1);
        }
    }
    public void SetBackGroundColor(){
        SharedPreferences sp = getSharedPreferences("SharedPreference", 0);
        String ThemeColorName;
        mainLayout= (LinearLayout) findViewById(R.id.mainLayout);
        View hView =  navigationView.getHeaderView(0);
        try{
            ThemeColorName = sp.getString("ThemeColorName", "");
        }catch (Exception e){
            ThemeColorName="d";
        }
        if (ThemeColorName.equals("Turquoise")){
            mainLayout.setBackgroundColor(Color.parseColor("#1abc9c"));
            hView.setBackgroundColor(Color.parseColor("#1abc9c"));
            toolbar.setBackgroundColor(Color.parseColor("#1abc9c"));
        }else if (ThemeColorName.equals("Greensea")){
            mainLayout.setBackgroundColor(Color.parseColor("#16a085"));
            hView.setBackgroundColor(Color.parseColor("#16a085"));
            toolbar.setBackgroundColor(Color.parseColor("#16a085"));
        }else if (ThemeColorName.equals("Sunflower")){
            mainLayout.setBackgroundColor(Color.parseColor("#f1c40f"));
            hView.setBackgroundColor(Color.parseColor("#f1c40f"));
            toolbar.setBackgroundColor(Color.parseColor("#f1c40f"));
        }else if (ThemeColorName.equals("Orange")){
            mainLayout.setBackgroundColor(Color.parseColor("#f39c12"));
            hView.setBackgroundColor(Color.parseColor("#f39c12"));
            toolbar.setBackgroundColor(Color.parseColor("#f39c12"));
        }else if (ThemeColorName.equals("Emerland")){
            mainLayout.setBackgroundColor(Color.parseColor("#2ecc71"));
            hView.setBackgroundColor(Color.parseColor("#2ecc71"));
            toolbar.setBackgroundColor(Color.parseColor("#2ecc71"));
        }else if (ThemeColorName.equals("Nephritis")){
            mainLayout.setBackgroundColor(Color.parseColor("#27ae60"));
            hView.setBackgroundColor(Color.parseColor("#27ae60"));
            toolbar.setBackgroundColor(Color.parseColor("#27ae60"));
        }else if (ThemeColorName.equals("Carrot")){
            mainLayout.setBackgroundColor(Color.parseColor("#e67e22"));
            hView.setBackgroundColor(Color.parseColor("#e67e22"));
            toolbar.setBackgroundColor(Color.parseColor("#e67e22"));
        }else if (ThemeColorName.equals("Pumpkin")){
            mainLayout.setBackgroundColor(Color.parseColor("#d35400"));
            hView.setBackgroundColor(Color.parseColor("#d35400"));
            toolbar.setBackgroundColor(Color.parseColor("#d35400"));
        }else if (ThemeColorName.equals("Peterriver")){
            mainLayout.setBackgroundColor(Color.parseColor("#3498db"));
            hView.setBackgroundColor(Color.parseColor("#3498db"));
            toolbar.setBackgroundColor(Color.parseColor("#3498db"));
        }else if (ThemeColorName.equals("Belizehole")){
            mainLayout.setBackgroundColor(Color.parseColor("#2980b9"));
            hView.setBackgroundColor(Color.parseColor("#2980b9"));
            toolbar.setBackgroundColor(Color.parseColor("#2980b9"));
        }else if (ThemeColorName.equals("Alizarin")){
            mainLayout.setBackgroundColor(Color.parseColor("#e74c3c"));
            hView.setBackgroundColor(Color.parseColor("#e74c3c"));
            toolbar.setBackgroundColor(Color.parseColor("#e74c3c"));
        }else if (ThemeColorName.equals("Silver")){
            mainLayout.setBackgroundColor(Color.parseColor("#bdc3c7"));
            hView.setBackgroundColor(Color.parseColor("#bdc3c7"));
            toolbar.setBackgroundColor(Color.parseColor("#bdc3c7"));
        }else {
            mainLayout.setBackgroundColor(Color.parseColor("#e74343"));
            hView.setBackgroundColor(Color.parseColor("#e74343"));
            toolbar.setBackgroundColor(Color.parseColor("#e74343"));
        }

    }

    public void flashLightOn() {
        try {
//            if (getPackageManager().hasSystemFeature(
//                    PackageManager.FEATURE_CAMERA_FLASH)) {
                camera = Camera.open();
                params = camera.getParameters();
                params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                camera.setParameters(params);
                camera.startPreview();
                isFlashOn = false;
//            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getBaseContext(), "Exception flashLightOn()",
                    Toast.LENGTH_SHORT).show();
        }
    }
    public void flashLightOff() {
//        if (!isFlashOn) {
            params = camera.getParameters();
            params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(params);
            camera.stopPreview();
//        }
    }

    public void turnOnFlashLight() {
        try {
            if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
                CameraManager mg=(CameraManager) getSystemService(Context.CAMERA_SERVICE);
                String[] list=mg.getCameraIdList();
                mg.setTorchMode(list[0],true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
    public void turnOffFlashLight() {
        try {
            if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
                CameraManager mg=(CameraManager)getSystemService(Context.CAMERA_SERVICE);
                String[] list=mg.getCameraIdList();
                mg.setTorchMode(list[0],false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getBaseContext(), "Exception throws in turning off flashlight.", Toast.LENGTH_SHORT).show();
        }
    }


    public void ShowOrNotNotificationPanel(){
        String NotifyStatus = sharedPreference.getString("NotifyStatus", "");
        if (NotifyStatus.equals("")){
            SharedPreferences.Editor ee=sharedPreference.edit();
            ee.putString("NotifyStatus","true");
            ee.apply();
        }
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        try{
            if(mBatInfoReceiver!=null)
                unregisterReceiver(mBatInfoReceiver);
        }catch(Exception e) {e.printStackTrace();}
        super.onDestroy();
    }

    public static void youDesirePermissionCode(final Activity context, int set_brightness){
        boolean permission;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permission = Settings.System.canWrite(context);
        } else {
            permission = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_SETTINGS) == PackageManager.PERMISSION_GRANTED;
        }
        if (permission) {
            //do your code
            //Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
            android.provider.Settings.System.putInt(context.getContentResolver(),
                    android.provider.Settings.System.SCREEN_BRIGHTNESS,
                    set_brightness);
        }  else {
            new AlertDialog.Builder(context)
                    .setTitle("Permission")
                    .setMessage("You need to provide access to Full Battery Alarm. Kindly press 'Ok' and Allow to modify system settings.")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                        Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                        intent.setData(Uri.parse("package:" + context.getPackageName()));
                        context.startActivityForResult(intent, MainActivity.CODE_WRITE_SETTINGS_PERMISSION);
                    } else {
                        ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.WRITE_SETTINGS}, MainActivity.CODE_WRITE_SETTINGS_PERMISSION);
                    }
                }
            }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            }).show();
        }
    }

    @SuppressLint("NewApi")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_WRITE_SETTINGS_PERMISSION && Settings.System.canWrite(this)){
            Log.d("TAG", "CODE_WRITE_SETTINGS_PERMISSION success");
            //do your code
        }
        if(requestCode == RQS_RINGTONEPICKER && resultCode == RESULT_OK){
            Uri uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
            setAlarm(uri);
        }
    }

    public void SetInitialValue(){
        SharedPreferences sp = getSharedPreferences("SharedPreference", 0);
        String fulllevel = sp.getString("FullBatteryLevel", "");
        String lowlevel = sp.getString("LowBatteryLevel", "");
        String templevel = sp.getString("TempWarnLevel", "");
        String SilentModeStatus = sp.getString("SilentModeStatus", "");
        String RingtoneUri = sp.getString("RingtoneUri", "");
        String tourchStatus = sp.getString("tourchStatus", "");
        String walkThrough = sp.getString("walkThrough", "");
        String SoundeLevel = sp.getString("SoundeLevel", "");
        SharedPreferences.Editor ee=sp.edit();
        if (fulllevel.equals(""))
        ee.putString("FullBatteryLevel","100");
        if (RingtoneUri.equals(""))
        ee.putString("RingtoneUri","Default");
        if (lowlevel.equals(""))
        ee.putString("LowBatteryLevel","Off");
        if (templevel.equals("")) {
            ee.putString("TempWarnLevel", "Off");
            ee.putString("TempWarnLevelPos", "0");
        }
        if (SilentModeStatus.equals(""))
            ee.putString("SilentModeStatus","false");

        if (tourchStatus.equals(""))
            ee.putString("tourchStatus","OFF");

        if (walkThrough.equals(""))
            ee.putString("walkThrough","true");

        if (SoundeLevel.equals(""))
            ee.putString("SoundeLevel","12");

        ee.apply();
    }

    public void HowToUse(){


        View vv= LayoutInflater.from(MainActivity.this).inflate(R.layout.how_to_use,null);
        final AlertDialog ab=new AlertDialog.Builder(this,R.style.MyDialogTheme).create();

        TextView title= (TextView) vv.findViewById(R.id.TxtTitle);
        TextView TxtTheftAlam= (TextView) vv.findViewById(R.id.TxtTheftAlam);
        TextView TxtTheftA= (TextView) vv.findViewById(R.id.theft_A);
        TextView TxtTheftB= (TextView) vv.findViewById(R.id.theft_B);
        TextView TxtTheftC= (TextView) vv.findViewById(R.id.theft_C);
        TextView TxtTheftD= (TextView) vv.findViewById(R.id.theft_D);
        TextView TxtFullBatteryAlarm= (TextView) vv.findViewById(R.id.TxtFullBatteryAlarm);
        TextView FullBatteryAlarm_A= (TextView) vv.findViewById(R.id.FullBatteryAlarm_A);
        TextView FullBatteryAlarm_B= (TextView) vv.findViewById(R.id.FullBatteryAlarm_B);

        if (Status.equals("true")){
            String StrTitle=sharedPreference.getString("HowToUse", "");
            String StrTheftalarm=sharedPreference.getString("theftalarm", "");
            String StrTheftA=sharedPreference.getString("TheftA", "");
            String StrTheftB=sharedPreference.getString("TheftB", "");
            String StrTheftC=sharedPreference.getString("TheftC", "");
            String StrTheftD=sharedPreference.getString("TheftD", "");
            String StrFullBatteryAlarm=sharedPreference.getString("fullBatteryAlarm", "");
            String StrFullBatteryAlarmA=sharedPreference.getString("FullBatteryAlarmA", "");
            String StrFullBatteryAlarmB=sharedPreference.getString("FullBatteryAlarmB", "");

            title.setText(StrTitle);
            TxtTheftAlam.setText(StrTheftalarm);
            TxtTheftA.setText(StrTheftA);
            TxtTheftB.setText(StrTheftB);
            TxtTheftC.setText(StrTheftC);
            TxtTheftD.setText(StrTheftD);
            TxtFullBatteryAlarm.setText(StrFullBatteryAlarm);
            FullBatteryAlarm_A.setText(StrFullBatteryAlarmA);
            FullBatteryAlarm_B.setText(StrFullBatteryAlarmB);

        }

        ImageView imgCancel = (ImageView)vv.findViewById(R.id.imgCancel);
        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ab.dismiss();
            }
        });
        ab.setView(vv);
        ab.show();

//        TextView title=new TextView(this);
//        title.setText("How To Use");
//        title.setBackgroundColor(R.color.colorPrimary);
//        TextView content = new TextView(this);
//        content.setText(R.string.how_to_use);
//        content.setTextColor(Color.BLACK);
//        content.setPadding(10,5,10,5);
//        content.setTypeface(Typeface.SANS_SERIF);
//        content.setIncludeFontPadding(true);
//        //content.setTypeface(Typeface.defaultFromStyle(R.style.TextFont));
//        new AlertDialog.Builder(MainActivity.this,R.style.MyDialogTheme).setView(content).setNeutralButton("Close dialog", null).setTitle(title.getText().toString()).show().setCancelable(true);
    }
    public void Instruction(){
        View vv= LayoutInflater.from(MainActivity.this).inflate(R.layout.instruction,null);
        final AlertDialog ab=new AlertDialog.Builder(this,R.style.DialogTheme).create();
        TextView cancel=(TextView)vv.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ab.dismiss();
            }
        });
        ab.setView(vv);
        ab.show();
    }
    protected void sendEmail() {
        Log.i("Send email", "");
        String[] TO = {"replaycreation@gmail.com"};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            //finish();
            //Log.i("Finished sending email...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }catch (Exception e){}
    }
    public void SetLanguage(String status){
        if(status.equals("true")) {
            sharedPreference = getSharedPreferences("SharedPreference", 0);

            String str_helth = sharedPreference.getString("health", "");
            String str_volt = sharedPreference.getString("volt", "");
            String str_temp = sharedPreference.getString("temp", "");
            String str_brightness = sharedPreference.getString("brightness", "");

            String str_batteryCapacity = sharedPreference.getString("batteryCapacity", "");
            String str_RemainingCapacity = sharedPreference.getString("remainingBatteryCapacity", "");
            str_stop = sharedPreference.getString("stop", "");
            str_start = sharedPreference.getString("start", "");
            String str_settings = sharedPreference.getString("settings", "");
            String str_navSettings = sharedPreference.getString("navSettings", "");
            String str_Theme = sharedPreference.getString("chooseColorTheme", "");
            String str_reminder = sharedPreference.getString("LowBatteryReminder", "");
            String str_batterystatistics = sharedPreference.getString("batterystatistics", "");
            String str_language = sharedPreference.getString("language", "");
            String str_alarmringtone = sharedPreference.getString("alarmringtone", "");
            String str_theftalarm = sharedPreference.getString("theftalarm_caps", "");
            String str_share = sharedPreference.getString("share", "");
            String str_contact = sharedPreference.getString("contact", "");
            String str_RateUs = sharedPreference.getString("RateUs", "");
            String str_flashLight = sharedPreference.getString("flashLight", "");
            String str_title = sharedPreference.getString("fullBatteryAlarm", "");

            getSupportActionBar().setTitle(str_title);

            nav_reminder.setTitle(str_reminder);
            nav_details.setTitle(str_batterystatistics);
            nav_language.setTitle(str_language);
            nav_alarm_ringtone.setTitle(str_alarmringtone);
            nav_settings.setTitle(str_navSettings);
            nav_theme.setTitle(str_Theme);
            //nav_theft_Alarm.setTitle(str_theftalarm);
            nav_share.setTitle(str_share);
            nav_contact_us.setTitle(str_contact);
            nav_rate_us.setTitle(str_RateUs);


            txtTheft.setText(str_theftalarm );
            txtSettings.setText(str_settings);
            txt_Capacity.setText(str_batteryCapacity);
            txt_RemainingCapacity.setText(str_RemainingCapacity);
            txthealth.setText(str_helth);
            txtVoltage.setText(str_volt);
            txtTemp.setText(str_temp);
            txtbrightness.setText(str_brightness);
            flashTxt.setText(str_flashLight);



            FullAlarmStatus = sharedPreference.getString("FullAlarmStatus", "");
            if (FullAlarmStatus.equals("OFF")) {
                OnOff.setImageResource(R.drawable.ic_play_circle_outline_black_24dp);
                txtOnOff.setText(str_start);
            } else {
                OnOff.setImageResource(R.drawable.ic_stop_black_24dp);
                txtOnOff.setText(str_stop);
            }
        }
    }



    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context c, Intent intent) {
            /*new Intent().setComponent(new ComponentName("com.samsung.android.lool",
                    "com.samsung.android.sm.ui.battery.BatteryActivity"));*/
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            int voltage = intent.getIntExtra("voltage", 0);
            int temperature = intent.getIntExtra("temperature", 0);
            int health = intent.getIntExtra("health", 0);
            int plugged = intent.getIntExtra("plugged", -1);

            updateBatteryData(intent);

            float v=voltage/1000;
            ChargingLevel=String.valueOf(level);
            voltageLevel.setText(String.valueOf(v)+"V");
            temps = (double)temperature / 10;
            String strConvertTempUnit=sharedPreference.getString("convertTempUnit", "");
            if (strConvertTempUnit.equals("Fahrenheit")){
                double fahrenheit = (9.0/5.0)*temps + 32;
                temperatureLevel.setText(String.valueOf(Math.round(fahrenheit))+"°F");
            }else
                temperatureLevel.setText(String.valueOf(temps)+"°C");

            String batteryHealth = getHealthString(health);
            batteryLevel.setText(String.valueOf(batteryHealth));
            BrightnessAdjustment();

            strChargeVia=getPlugTypeString(plugged);
            SharedPreferences.Editor e=sharedPreference.edit();
            e.putString("strChargeVia",strChargeVia);
            e.apply();
            if (!strChargeVia.equals("Unknown")){
                TheftAutoEnableStatus();
                SharedPreferences.Editor ee=sharedPreference.edit();
                ee.putString("StopAlarmStatus","ON");
                ee.apply();
                llChargeBy.setVisibility(View.VISIBLE);
                chargeBy.setText(strChargeVia+" Connected");
            }
            else{
                llChargeBy.setVisibility(View.GONE);
            }


            String walkThrough = sharedPreference.getString("walkThrough", "");
            if (walkThrough.equals("true")) {
                SharedPreferences.Editor ee6=sharedPreference.edit();
                ee6.putString("walkThrough","false");
                ee6.apply();
                //Instruction();
                dbClass=new PowerEventsTable(MainActivity.this);

                if(!strChargeVia.equals("Unknown")){
                    dbClass.insert(ChargingLevel,getCurrentDate(),getCurrentTime(),"1");
                }
                if (strChargeVia.equals("Unknown")){
                    dbClass.insert(ChargingLevel,getCurrentDate(),getCurrentTime(),"0");
                }
            }


            //Check THEFT ALARM auto enable ON OR OFF...
            String strTheftAutoEnable=sharedPreference.getString("theftAutoEnable", "");
            try {
                if (strTheftAutoEnable.equals("true") && !strChargeVia.equals("Unknown") ){
                    SharedPreferences.Editor ee=sharedPreference.edit();
                    ee.putString("TheftAlarmStatus","ON");
                    ee.apply();
                    txtTheft.setTextColor(Color.GREEN);
                    theftImage.setImageResource(R.drawable.thief_color);
                }
            }catch (Exception ex){}

            String TotalCapacity=getBatteryCapacity(batteryHealth);
            setRemainingCapacity(level,TotalCapacity);

            float value=(float) level/100;
            animate(value);
        }
    };

    public String getBatteryCapacity(String batteryHealth) {
        Object mPowerProfile_ = null;

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
            TxtCapacity.setText(String.valueOf(l));
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
        TxtRemainingCapacity.setText(String.valueOf(k));
        return k;
    }

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
                SharedPreferences.Editor e=sharedPreference.edit();
                e.putString("StopAlarmStatus","ON");
                e.apply();
                break;
        }

        return plugType;
    }
    private String getHealthString(int health) {
        String healthString = "Unknown";

        switch (health) {
            case BatteryManager.BATTERY_HEALTH_DEAD:
                healthString = "Dead";
                break;
            case BatteryManager.BATTERY_HEALTH_GOOD:
                healthString = "GOOD";
                break;
            case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                healthString = "Over Voltage";
                break;
            case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                healthString = "Over Heat";
                break;
            case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                healthString = "Failure";
                break;
        }

        return healthString;
    }
    private String getStatusString(int status) {
        String statusString = "Unknown";

        switch (status) {
            case BatteryManager.BATTERY_STATUS_CHARGING:
                statusString = "Charging";
                break;
            case BatteryManager.BATTERY_STATUS_DISCHARGING:
                statusString = "Discharging";
                break;
            case BatteryManager.BATTERY_STATUS_FULL:
                statusString = "Full";
                break;
            case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                statusString = "Not Charging";
                break;
        }

        return statusString;
    }
    public void animate(float level) {
        progressCircle.setProgress(level);
        progressCircle.startAnimation();
    }

    @Override
    public void onClick(View v) {
        SharedPreferences.Editor ee2 = sharedPreference.edit();
        ee2.putInt("count", 0);
        ee2.apply();

        FullAlarmStatus = sharedPreference.getString("FullAlarmStatus", "");
        if (v==settingsLayout){
            Intent i=new Intent(MainActivity.this,SettingsActivity.class);
            i.putExtra("totalCapacity",totalCapacity);
            startActivity(i);
        }if (v==theftAlarmLayout){
            TheftAlarmClickEvent();
        }if (v==onoffLayout){
            String AlarmStatus = sharedPreference.getString("FullAlarmStatus", "");
            if (AlarmStatus.equals("ON")){
                OnOff.setImageResource(R.drawable.ic_play_circle_outline_black_24dp);
                txtOnOff.setText(str_start);
                SharedPreferences.Editor ee1=sharedPreference.edit();
                ee1.putString("FullAlarmStatus","OFF");
                ee1.apply();
                FullAlarmStatus = sharedPreference.getString("FullAlarmStatus", "");
                Toast.makeText(this, "Full battery alarm is "+FullAlarmStatus, Toast.LENGTH_SHORT).show();
            } else{
                OnOff.setImageResource(R.drawable.ic_stop_black_24dp);
                txtOnOff.setText(str_stop);
                SharedPreferences.Editor ee1=sharedPreference.edit();
                ee1.putString("FullAlarmStatus","ON");
                ee1.putString("Triggered","");
                ee1.apply();

                TheftAutoEnableStatus();
                FullAlarmStatus = sharedPreference.getString("FullAlarmStatus", "");
                Toast.makeText(this, "Full battery alarm is "+FullAlarmStatus, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void TheftAlarmClickEvent() {
        String finalTheftPassword = sharedPreference.getString("finalTheftPassword", "");
        String TheftAlarmStatus = sharedPreference.getString("TheftAlarmStatus", "");
        String FullAlarm = sharedPreference.getString("FullAlarmStatus", "");

        if(finalTheftPassword.length() == 0){
            //Toast.makeText(this, finalTheftPassword, Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(MainActivity.this,ThaftAlarmActivity.class);
            startActivity(intent);
        }

        if(finalTheftPassword.length() > 0){
            if(TheftAlarmStatus.equals("") && !strChargeVia.equals("Unknown")){
                txtTheft.setTextColor(Color.GREEN);
                theftImage.setImageResource(R.drawable.thief_color);
                SharedPreferences.Editor ee=sharedPreference.edit();
                ee.putString("TheftAlarmStatus","ON");
                ee.apply();
            }if(TheftAlarmStatus.equals("") && strChargeVia.equals("Unknown")){
                Toast.makeText(this, "You need to connect the charger!", Toast.LENGTH_SHORT).show();
            }if (TheftAlarmStatus.equals("ON")){
                Intent i=new Intent(MainActivity.this, StopTheftAlarmActivity.class);
                i.putExtra("theftDeactivate","y");
                startActivity(i);
            }

        }
    }


    private void updateBatteryData(Intent intent) {
        boolean present = intent.getBooleanExtra(BatteryManager.EXTRA_PRESENT, false);

        if (present) {
            int health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0);
            int healthLbl = -1;

            switch (health) {
                case BatteryManager.BATTERY_HEALTH_COLD:
                    healthLbl = R.string.battery_health_cold;
                    break;

                case BatteryManager.BATTERY_HEALTH_DEAD:
                    healthLbl = R.string.battery_health_dead;
                    break;

                case BatteryManager.BATTERY_HEALTH_GOOD:
                    healthLbl = R.string.battery_health_good;
                    break;

                case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                    healthLbl = R.string.battery_health_over_voltage;
                    break;

                case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                    healthLbl = R.string.battery_health_overheat;
                    break;

                case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                    healthLbl = R.string.battery_health_unspecified_failure;
                    break;

                case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                default:
                    break;
            }

            if (healthLbl != -1) {
                // display battery health ...
                healthTv=(getString(healthLbl));
            }

            // Calculate Battery Pourcentage ...
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

            if (level != -1 && scale != -1) {
                int batteryPct = (int) ((level / (float) scale) * 100f);
                batteryPctTv=(batteryPct + " %");
            }

            int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0);
            int pluggedLbl = R.string.battery_plugged_none;

            switch (plugged) {
                case BatteryManager.BATTERY_PLUGGED_WIRELESS:
                    pluggedLbl = R.string.battery_plugged_wireless;
                    break;

                case BatteryManager.BATTERY_PLUGGED_USB:
                    pluggedLbl = R.string.battery_plugged_usb;
                    break;

                case BatteryManager.BATTERY_PLUGGED_AC:
                    pluggedLbl = R.string.battery_plugged_ac;
                    break;

                default:
                    pluggedLbl = R.string.battery_plugged_none;
                    break;
            }

            // display plugged status ...
            pluggedTv=(getString(pluggedLbl));

            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            int statusLbl = R.string.battery_status_discharging;

            switch (status) {
                case BatteryManager.BATTERY_STATUS_CHARGING:
                    statusLbl = R.string.battery_status_charging;
                    break;

                case BatteryManager.BATTERY_STATUS_DISCHARGING:
                    statusLbl = R.string.battery_status_discharging;
                    break;

                case BatteryManager.BATTERY_STATUS_FULL:
                    statusLbl = R.string.battery_status_full;
                    break;

                case BatteryManager.BATTERY_STATUS_UNKNOWN:
                    statusLbl = -1;
                    break;

                case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                default:
                    statusLbl = R.string.battery_status_discharging;
                    break;
            }

            if (statusLbl != -1) {
                chargingStatusTv=(getString(statusLbl));
            }

            if (intent.getExtras() != null) {
                String technology = intent.getExtras().getString(BatteryManager.EXTRA_TECHNOLOGY);

                if (!"".equals(technology)) {
                    technologyTv=(technology);
                }
            }

            int temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0);

            if (temperature > 0) {
                float temp = ((float) temperature) / 10f;
                tempTv=(temp + " °C");
            }

            int voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);

            if (voltage > 0) {
                float v=voltage/1000;
                voltageTv=( v + " V");
            }
            long capacity = getBatteryCapacity(this);

            if (capacity > 0) {
                capacityTv=(capacity + " mAh");
            }

        } else {
            Toast.makeText(this, "No Battery present", Toast.LENGTH_SHORT).show();
        }

    }
    public long getBatteryCapacity(Context ctx) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            BatteryManager mBatteryManager = (BatteryManager) ctx.getSystemService(Context.BATTERY_SERVICE);
            Long chargeCounter = mBatteryManager.getLongProperty(BatteryManager.BATTERY_PROPERTY_CHARGE_COUNTER);
            Long capacity = mBatteryManager.getLongProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);

            if (chargeCounter != null && capacity != null) {
                long value = (long) (((float) chargeCounter / (float) capacity) * 100f);
                return value;
            }
        }
        return 0;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_reminder) {
            showReminderRadioDialog();

        } else if (id == R.id.nav_details) {
            Intent i=new Intent(getApplicationContext(),StatisticsActivity.class);
            i.putExtra("totalCapacity",totalCapacity);
            i.putExtra("health",healthTv);
            i.putExtra("batteryPct",batteryPctTv);
            i.putExtra("plugged",pluggedTv);
            i.putExtra("chargingStatus",chargingStatusTv);
            i.putExtra("voltage",voltageTv);
            i.putExtra("temp",tempTv);
            i.putExtra("technology",technologyTv);
            startActivity(i);
        }else if (id == R.id.nav_language) {
            Intent i=new Intent(getApplicationContext(),LanguageActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_alarm_ringtone) {
            SetRingtone();
        } else if (id == R.id.nav_settings) {
            Intent i=new Intent(MainActivity.this, SettingsActivity.class);
            i.putExtra("totalCapacity",totalCapacity);
            startActivity(i);
        }else if (id == R.id.nav_theme) {
            //showColorThemeAlertDialog();
            THEME();
        }
        else if (id == R.id.nav_share) {
            shareText();
        } else if (id == R.id.nav_contact_us) {
            sendEmail();
        }else if (id == R.id.nav_rate_us) {
            launchMarket();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showColorThemeAlertDialog() {
        backposition = -1;
        final ArrayList<String> myObjects=new ArrayList();
        final GridView gridView = new GridView(this);
        final ColorAdapter adapter=new ColorAdapter(this);
        gridView.setAdapter(adapter);
        gridView.setChoiceMode(GridView.CHOICE_MODE_SINGLE);
        gridView.setNumColumns(3);

        // Set grid view to alertDialog
        final AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.MyDialogTheme);
        builder.setView(gridView);
        builder.setTitle("Choose Color Theme");

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView txt=(TextView)view.findViewById(R.id.colorTxt);
                TextView colorNameTxt=(TextView)view.findViewById(R.id.colorNameTxt);
                ImageView selectTheme=(ImageView)view.findViewById(R.id.selectTheme);

                selectTheme.setVisibility(View.VISIBLE);

                if (backposition!=-1){
                adapter.notifyDataSetChanged();
                gridView.setAdapter(adapter);}

                colorName=colorNameTxt.getText().toString();
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences.Editor ee=sharedPreference.edit();
                ee.putString("ThemeColorName",colorName);
                ee.apply();
                dialog.dismiss();
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog ad = builder.create();
        ad.show();
    }

    public void THEME(){
        String title = sharedPreference.getString("Theme", "");
        ColorPicker colorPicker = new ColorPicker(MainActivity.this);
        if (title.equals(""))
            colorPicker.setTitle("Theme");
        else colorPicker.setTitle(title);
        ArrayList<String> colors = new ArrayList<>();
        colors.add("#1abc9c");
        colors.add("#16a085");
        //colors.add("#f1c40f");
        colors.add("#f39c12");
        colors.add("#2ecc71");
        colors.add("#27ae60");
        colors.add("#e67e22");
        colors.add("#d35400");
        colors.add("#3498db");
        colors.add("#2980b9");
        colors.add("#e74c3c");
        colors.add("#bdc3c7");

        final SharedPreferences sp = getSharedPreferences("SharedPreference", 0);
        colorPicker.setColors(colors).setDefaultColorButton(Color.parseColor("#000000")).setColumns(4).setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
            @Override
            public void onChooseColor(int position, int color) {
                try{
                    String colorName=mThumbColorNames[position];
                    SharedPreferences.Editor ee=sp.edit();
                    ee.putString("ThemeColorName",colorName);
                    ee.apply();
                }catch (Exception e){}

                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }

            @Override
            public void onCancel() {}
        }).setRoundColorButton(true).show();
    }

    public class ColorAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        public ColorAdapter(Context c) {
            mInflater = LayoutInflater.from(c);
        }
        public int getCount() {
            return mThumbColorCodes.length;
        }
        public Object getItem(int position) {
            return null;
        }
        public long getItemId(int position) {
            return 0;
        }
        // create a new ImageView for each item referenced by the
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {  // if it's not recycled,
                convertView = mInflater.inflate(R.layout.color_theme_layout, null);
                convertView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,200));
                holder = new ViewHolder();
                holder.title = (TextView) convertView.findViewById(R.id.colorTxt);
                holder.colorNameTxt = (TextView) convertView.findViewById(R.id.colorNameTxt);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.title.setBackgroundColor(Color.parseColor(mThumbColorCodes[position]));
            holder.colorNameTxt.setText(mThumbColorNames[position]);
            return convertView;
        }
        class ViewHolder {
            TextView title,colorNameTxt;
        }
    }


    private void launchMarket() {
        Uri uri = Uri.parse("market://details?id=" + getPackageName());
        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            startActivity(myAppLinkToMarket);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, " unable to find market app", Toast.LENGTH_LONG).show();
        }
    }



    private void TheftAlarm(){
        final String finalTheftPassword = sharedPreference.getString("finalTheftPassword", "");

        final MediaPlayer mMediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.slient_hill);
        mMediaPlayer.setLooping(true);
        mMediaPlayer.start();

        final Dialog ImageDialog = new Dialog(this);
        ImageDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ImageDialog.setCancelable(false);
        ImageDialog.setContentView(R.layout.theft_alarm_layout);
        ImageView icon = (ImageView)ImageDialog.findViewById(R.id.imageView);
        final EditText theftPlayPass = (EditText)ImageDialog.findViewById(R.id.theftPlayPass);
        TextView txtOn = (TextView)ImageDialog.findViewById(R.id.on);
        TextView txtOff = (TextView)ImageDialog.findViewById(R.id.off);
        ImageDialog.show();

        theftPlayPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (finalTheftPassword.equals(theftPlayPass.getText().toString().trim())){
                    mMediaPlayer.stop();
                    ImageDialog.cancel();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    protected void callMathAlarmScheduleService() {
        Intent AlarmServiceIntent = new Intent(this, BroadcastReceiverClass.class);
        sendBroadcast(AlarmServiceIntent, null);
    }


    private void SetRingtone() {
        String status = sharedPreference.getString("status", "");
        String title="Ringtone",str1="Default Ringtone",str2="Set Custom Ringtone";
        if (status.equals("true")) {
            title = sharedPreference.getString("Ringtone", "");
            str1 = sharedPreference.getString("DefaultRingtone", "");
            str2 = sharedPreference.getString("SetCustomRingtone", "");
        }

        final CharSequence[] items = {str1, str2};
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this,R.style.MyDialogTheme);
        builder.setTitle(title);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (item==0) {
                    SharedPreferences.Editor ee=sharedPreference.edit();
                    //ee.putString("RingtoneUri","android.resource://com.replaycreation.fullbatteryalarm/R.raw.slient_hill");
                    ee.putString("RingtoneUri","Default");
                    ee.apply();
                    Toast.makeText(MainActivity.this, "Set successfully", Toast.LENGTH_SHORT).show();
                } else if (item==1) {
                    if (checkPermission()){
                        startRingTonePicker();
                    }else {
                        requestPermission();
                    }
                }
            }
        });
        builder.show();
    }

    private void startRingTonePicker(){
        Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        startActivityForResult(intent, RQS_RINGTONEPICKER);
    }

    private void setAlarm(Uri passuri){
        try{
        String passString = passuri.toString();
        SharedPreferences.Editor ee=sharedPreference.edit();
        ee.putString("RingtoneUri",passString);
        ee.apply();
        }catch (Exception e){
            Toast.makeText(this, "Unable to set ringtone. Please contact developer using the 'Contact us' section.", Toast.LENGTH_SHORT).show();}
        Toast.makeText(this, "Set successfully", Toast.LENGTH_SHORT).show();
    }


    public void showReminderRadioDialog() {
        String title = sharedPreference.getString("LowBatteryReminder", "");
        final String color[]={"10 %","20 %","30 %","40 %","50 %","None"};
        AlertDialog.Builder dialog=new AlertDialog.Builder(this,R.style.MyDialogTheme);
        dialog.setCancelable(false);
        if (title.equals(""))
            dialog.setTitle("Low Battery Reminder");
        else dialog.setTitle(title);
        dialog.setSingleChoiceItems(color, Integer.parseInt(whichCheck), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), "If remaining level "+color[which]+". You'll notify!", Toast.LENGTH_SHORT).show();

                switch(which) {
                    case 0:
                        whenNotify="10";
                        pos=String.valueOf(which);
                        break;
                    case 1:
                        whenNotify="20";
                        pos=String.valueOf(which);
                        break;
                    case 2:
                        whenNotify="30";
                        pos=String.valueOf(which);
                        break;
                    case 3:
                        whenNotify="40";
                        pos=String.valueOf(which);
                        break;
                    case 4:
                        whenNotify="50";
                        pos=String.valueOf(which);
                        break;
                    case 5:
                        whenNotify="101";
                        pos=String.valueOf(-1);
                        break;
                }
            }

        });
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences.Editor ee6=sharedPreference.edit();
                ee6.putString("whenNotifyUser",whenNotify);
                ee6.putString("whichCheck",pos);
                ee6.apply();
                dialog.dismiss();
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog ad = dialog.create();
        ad.show();
    }
    public void shareText() {
//        Uri pictureUri = Uri.parse("android.resource://com.replaycreation.application/drawable/full_battery_alarm_logo");
//        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
//        sharingIntent.putExtra(Intent.EXTRA_TEXT, "Download Full Battery Alarm App. Download Now: https://play.google.com/store/apps/details?id=com.replaycreation.application&hl=en");
//        sharingIntent.putExtra(Intent.EXTRA_STREAM, pictureUri);
//        sharingIntent.setType("image/png");
//        startActivity(Intent.createChooser(sharingIntent, "Share With"));
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");
        String shareBodyText = "https://play.google.com/store/apps/details?id=com.replaycreation.application&hl=en";
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject/Title");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
        startActivity(Intent.createChooser(intent, "Sharing With"));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();


        TheftAutoEnableStatus();

        C2F(temps);
        FullAlarmStatus = sharedPreference.getString("FullAlarmStatus", "");
        if (FullAlarmStatus.equals("OFF")) {
            OnOff.setImageResource(R.drawable.ic_play_circle_outline_black_24dp);
            txtOnOff.setText(str_start);
        } if (FullAlarmStatus.equals("ON")) {
            OnOff.setImageResource(R.drawable.ic_stop_black_24dp);
            txtOnOff.setText(str_stop);
        }

        String s = sharedPreference.getString("TheftAlarmStatus", "");
        if (s.equals("")){
            txtTheft.setTextColor(Color.WHITE);
            theftImage.setImageResource(R.drawable.thief);
        }if (s.equals("ON")){
            txtTheft.setTextColor(Color.GREEN);
            theftImage.setImageResource(R.drawable.thief_color);
        }
        Status = sharedPreference.getString("status", "");
        SetLanguage(Status);
    }

    public void C2F(double temps){
        String strConvertTempUnit=sharedPreference.getString("convertTempUnit", "");
        if (strConvertTempUnit.equals("Fahrenheit")){
            double fahrenheit = (9.0/5.0)*temps + 32;
            //Math.round(fahrenheit);
            temperatureLevel.setText(String.valueOf(Math.round(fahrenheit))+"°F");
        }else
            temperatureLevel.setText(String.valueOf(temps)+"°C");
    }


    private void CkeckCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            int hasCameraPermission = checkSelfPermission(Manifest.permission.CAMERA);

            List<String> permissions = new ArrayList<String>();

            if (hasCameraPermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.CAMERA);

            }
            if (!permissions.isEmpty()) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), 111);
            }
        }
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        switch (requestCode) {
//            case 111: {
//                for (int i = 0; i < permissions.length; i++) {
//                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
//                        SharedPreferences.Editor ee=sharedPreference.edit();
//                        ee.putString("CameraPermission","true");
//                        ee.apply();
//                        //System.out.println("Permissions --> " + "Permission Granted: " + permissions[i]);
//                    } else if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
//                        flash.setChecked(false);
//                        SharedPreferences.Editor ee=sharedPreference.edit();
//                        ee.putString("CameraPermission","false");
//                        ee.apply();
//                        //System.out.println("Permissions --> " + "Permission Denied: " + permissions[i]);
//                    }
//                }
//            }
//            break;
//            default: {
//                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//            }
//        }
//    }
    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        //int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA);

        return result == PackageManager.PERMISSION_GRANTED;// && result1 == PackageManager.PERMISSION_GRANTED;
    }
    private void requestPermission() {

        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {

            case 111: {
                for (int i = 0; i < permissions.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        SharedPreferences.Editor ee=sharedPreference.edit();
                        ee.putString("CameraPermission","true");
                        ee.apply();
                        //System.out.println("Permissions --> " + "Permission Granted: " + permissions[i]);
                    } else if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        flash.setChecked(false);
                        SharedPreferences.Editor ee=sharedPreference.edit();
                        ee.putString("CameraPermission","false");
                        ee.apply();
                        //System.out.println("Permissions --> " + "Permission Denied: " + permissions[i]);
                    }
                }
            }
            break;

//            case PERMISSION_REQUEST_CODE:
//                if (grantResults.length > 0) {
//
//                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
//                    boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
//
//                    if (locationAccepted && cameraAccepted) {
//                        //Toast.makeText(getApplicationContext(),"Permission Granted, Now you can access gallery and camera.",Toast.LENGTH_LONG).show();
//                        //proceedAfterPermission();
////                        Snackbar.make(view, "Permission Granted, Now you can access gallery and camera.", Snackbar.LENGTH_LONG).show();
//                    }
//                    else {
//                        //Toast.makeText(getApplicationContext(),"Permission Denied, You cannot access gallery and camera.",Toast.LENGTH_LONG).show();
////                        Snackbar.make(view, "Permission Denied, You cannot access gallery and camera.", Snackbar.LENGTH_LONG).show();
//
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
//                                showMessageOKCancel("You need to allow access to both the permissions",
//                                        new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialog, int which) {
//                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                                                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
//                                                            PERMISSION_REQUEST_CODE);
//                                                }
//                                            }
//                                        });
//                                return;
//                            }
//                        }
//
//                    }
//                }
//
//
//                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getApplicationContext())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void TheftAutoEnableStatus(){
        String strTheftAutoEnable=sharedPreference.getString("theftAutoEnable", "");
        String Status = sharedPreference.getString("FullAlarmStatus", "");
        try {
            if (strTheftAutoEnable.equals("true") && Status.equals("ON") && !strChargeVia.equals("Unknown") ){
                SharedPreferences.Editor ee=sharedPreference.edit();
                ee.putString("TheftAlarmStatus","ON");
                ee.apply();
                txtTheft.setTextColor(Color.GREEN);
                theftImage.setImageResource(R.drawable.thief_color);
            }
        }catch (Exception e){}

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        super.onCreateOptionsMenu(menu);
        MenuInflater mymenu = getMenuInflater();
        mymenu.inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            case R.id.action_settings:
                HowToUse();
                break;
        }

        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        /*Intent mIntent = new Intent(MainActivity.this, LoadAlarmsService.class);
        ContextCompat.startForegroundService(getApplicationContext(),mIntent);*/
        /*try{
            Intent monitorIntent = new Intent(this, ServiceActivity.class);
            //monitorIntent.putExtra(ServiceActivity.BATTERY_UPDATE, true);
            bindService(monitorIntent, mServiceConnection, Context.BIND_AUTO_CREATE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                ContextCompat.startForegroundService(this,monitorIntent);
            } else {
                this.startService(monitorIntent);
            }

        }catch (Exception e){
            e.printStackTrace();
        }*/
    }
    @Override
    protected void onStop() {
        super.onStop();
        /*if (mServiceBound) {
            unbindService(mServiceConnection);
            mServiceBound = false;
        }*/
    }


    public ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            mServiceBound = false;
        }
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            ServiceActivity.MyBinder myBinder = (ServiceActivity.MyBinder) service;
            mBoundService = myBinder.getService();
            mServiceBound = true;
        }
    };
}