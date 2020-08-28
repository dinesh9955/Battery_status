package com.replaycreation.application.settings;

import android.Manifest;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.replaycreation.application.R;
import com.replaycreation.application.graph.ChargingOrDischargingGraph;
import com.replaycreation.application.theftalarm.OldTheftPass;
import com.replaycreation.application.theftalarm.ThaftAlarmActivity;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    CheckedTextView OnSilentMode,OnReboot,NotificationPanel,TheftAlarm,OnVibrationOrRingMode;
    TextView graph,sound,security,convertTempUnit,UnitTxt;

    SharedPreferences sharedPreference;

    TextView txtFullBattery,txtLowBattery,txtTempWarning;
    TextView fullBatteryLevel,lowBatteryLevel,tempWarnLevel;
    LinearLayout layoutFullbattery,layoutLowBattery,layoutWarnig;

    String strFullBatteryLevel="",strLowBatteryLevel="",strTempWarnLevel="";
    String strFullBatteryLevelPos,strLowBatteryLevelPos,strTempWarnLevelPos;

    String FullBatteryLevel,LowBatteryLevel,TempWarnLevel,totalCapacity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SetColorTheme();
        setContentView(R.layout.activity_settings);



       /* if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getApplicationContext().getPackageName()));
            // request permission via start activity for result
            startActivityForResult(intent, 1000);
        }*/

        sharedPreference = getSharedPreferences("SharedPreference", 0);
        totalCapacity=getIntent().getStringExtra("totalCapacity");

        FullBatteryLevel = sharedPreference.getString("FullBatteryLevel", "");
        String FullBatteryLevelPos = sharedPreference.getString("FullBatteryLevelPos", "");
        LowBatteryLevel = sharedPreference.getString("LowBatteryLevel", "");
        String LowBatteryLevelPos = sharedPreference.getString("LowBatteryLevelPos", "");
        TempWarnLevel = sharedPreference.getString("TempWarnLevel", "");
        String TempWarnLevelPos = sharedPreference.getString("TempWarnLevelPos", "");


        OnSilentMode= (CheckedTextView) findViewById(R.id.OnSilentMode);
        OnReboot= (CheckedTextView) findViewById(R.id.OnReboot);
        NotificationPanel= (CheckedTextView) findViewById(R.id.NotificationPanel);
        TheftAlarm= (CheckedTextView) findViewById(R.id.TheftAlarm);
        OnVibrationOrRingMode= (CheckedTextView) findViewById(R.id.OnVibrationOrRingMode);



        sound= (TextView) findViewById(R.id.sound);
        security= (TextView) findViewById(R.id.privacy);
        convertTempUnit= (TextView) findViewById(R.id.convertTemp);
        UnitTxt= (TextView) findViewById(R.id.UnitTxt);
        graph= (TextView) findViewById(R.id.graph);

        layoutFullbattery= (LinearLayout) findViewById(R.id.layoutFullbattery);
        layoutLowBattery= (LinearLayout) findViewById(R.id.layoutLowBattery);
        layoutWarnig= (LinearLayout) findViewById(R.id.layoutWarnig);

        txtFullBattery= (TextView) findViewById(R.id.txtFullBattery);
        txtLowBattery= (TextView) findViewById(R.id.txtLowBattery);
        txtTempWarning= (TextView) findViewById(R.id.txtTempWarning);

        fullBatteryLevel= (TextView) findViewById(R.id.fullBatteryLevel);
        lowBatteryLevel= (TextView) findViewById(R.id.lowBatteryLevel);
        tempWarnLevel= (TextView) findViewById(R.id.tempWarnLevel);


        String strConvertTempUnit=sharedPreference.getString("convertTempUnit", "");
        if (strConvertTempUnit.equals("Fahrenheit"))
            UnitTxt.setText("°F");
        if (strConvertTempUnit.equals("Celsius"))
            UnitTxt.setText("°C");

        String strTheftAutoEnable=sharedPreference.getString("theftAutoEnable", "");
        if (strTheftAutoEnable.equals("true"))
            TheftAlarm.setChecked(true);
        if (strTheftAutoEnable.equals("false"))
            TheftAlarm.setChecked(false);

        String strOnVibrationOrRingMode=sharedPreference.getString("OnVibrationOrRingMode", "");
        if (strOnVibrationOrRingMode.equals("true"))
            OnVibrationOrRingMode.setChecked(true);
        if (strOnVibrationOrRingMode.equals("false"))
            OnVibrationOrRingMode.setChecked(false);




        String strAutoStartupOnReboot=sharedPreference.getString("StartupOnReboot", "");
        if (strAutoStartupOnReboot.equals("true"))
            OnReboot.setChecked(true);
        if (strAutoStartupOnReboot.equals("false"))
            OnReboot.setChecked(false);

        String strSilentModeStatus=sharedPreference.getString("SilentModeStatus", "");
        if (strSilentModeStatus.equals("true"))
            OnSilentMode.setChecked(true);
        if (strSilentModeStatus.equals("false"))
            OnSilentMode.setChecked(false);

        String PanelAutoEnable=sharedPreference.getString("NotifyStatus", "");
        if (PanelAutoEnable.equals("true"))
            NotificationPanel.setChecked(true);
        if (PanelAutoEnable.equals("false"))
            NotificationPanel.setChecked(false);

        if (FullBatteryLevelPos.equals(""))
            fullBatteryLevel.setText("Alert At Selected Battery level : 100");
        else
            fullBatteryLevel.setText("Alert At Selected Battery level : "+FullBatteryLevel);
        if (LowBatteryLevelPos.equals("0") || LowBatteryLevelPos.equals(""))
            lowBatteryLevel.setText("Off");
        else
            lowBatteryLevel.setText("Alert At Selected Battery level : "+LowBatteryLevel);
        if (TempWarnLevelPos.equals("0") || TempWarnLevelPos.equals(""))
            tempWarnLevel.setText("Off");
        else
            tempWarnLevel.setText("Alert At Selected Temperature level : "+TempWarnLevel);


        OnSilentMode.setOnClickListener(this);
        OnReboot.setOnClickListener(this);
        NotificationPanel.setOnClickListener(this);
        TheftAlarm.setOnClickListener(this);
        OnVibrationOrRingMode.setOnClickListener(this);


        sound.setOnClickListener(this);
        security.setOnClickListener(this);
        convertTempUnit.setOnClickListener(this);
        graph.setOnClickListener(this);

        layoutFullbattery.setOnClickListener(this);
        layoutLowBattery.setOnClickListener(this);
        layoutWarnig.setOnClickListener(this);

        registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

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
            setTheme(R.style.AppThemeTurqoise);
        }else if (ThemeColorName.equals("Greensea")){
            setTheme(R.style.AppThemeGreensea);
        }else if (ThemeColorName.equals("Sunflower")){
            setTheme(R.style.AppThemeSunflower);
        }else if (ThemeColorName.equals("Orange")){
            setTheme(R.style.AppThemeOrange);
        }else if (ThemeColorName.equals("Emerland")){
            setTheme(R.style.AppThemeEmerland);
        }else if (ThemeColorName.equals("Nephritis")){
            setTheme(R.style.AppThemeNephritis);
        }else if (ThemeColorName.equals("Carrot")){
            setTheme(R.style.AppThemeCarrot);
        }else if (ThemeColorName.equals("Pumpkin")){
            setTheme(R.style.AppThemePumpkin);
        }else if (ThemeColorName.equals("Peterriver")){
            setTheme(R.style.AppThemePeterriver);
        }else if (ThemeColorName.equals("Belizehole")){
            setTheme(R.style.AppThemeBelizehole);
        }else if (ThemeColorName.equals("Alizarin")){
            setTheme(R.style.AppThemeAlizarin);
        }else if (ThemeColorName.equals("Silver")){
            setTheme(R.style.AppThemeSilver);
        }else {
            setTheme(R.style.AppTheme);
        }
    }

    @Override
    public void onClick(View v) {
        if (v==OnSilentMode){
            SilentMode();
        }if (v==OnVibrationOrRingMode){
            ActivateVibrationOrRingMode();
        }if (v==OnReboot){
            RebootMethod();
        }if (v==NotificationPanel){
            NotificationPanelMethod();
        }if (v==TheftAlarm){
            TheftAlarmMethod();
        }if (v==sound){
            Volume();
        }if (v==graph){
            Intent i=new Intent(SettingsActivity.this, ChargingOrDischargingGraph.class);
            i.putExtra("totalCapacity",totalCapacity);
            startActivity(i);
        }if (v==convertTempUnit){
            String status = sharedPreference.getString("status", "");
            String title="Change Temperature Unit",Fahrenheit="Fahrenheit",Celcius="Celsius",cancel="Cancel";
            if (status.equals("true")){
                title = sharedPreference.getString("ChangeTemperatureUnit", "");
                Fahrenheit=sharedPreference.getString("fahrenheit", "");
                Celcius=sharedPreference.getString("Celcius", "");
                cancel=sharedPreference.getString("cancel", "");}

            final CharSequence[] items = {Fahrenheit,Celcius,cancel};
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(title);
            builder.setItems(items, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    if (item==0){
                        UnitTxt.setText("°F");
                        SharedPreferences.Editor ee = sharedPreference.edit();
                        ee.putString("convertTempUnit", "Fahrenheit");
                        ee.apply();
                    }else if (item==1){
                        SharedPreferences.Editor ee = sharedPreference.edit();
                        ee.putString("convertTempUnit", "Celsius");
                        ee.apply();
                        UnitTxt.setText("°C");
                    }
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }if (v==security){
            sharedPreference = getSharedPreferences("SharedPreference", 0);
            String oldPass = sharedPreference.getString("theftPassword", "");
            if (!oldPass.equals("")){
            Intent i=new Intent(SettingsActivity.this,OldTheftPass.class);
            startActivity(i);}
            else {
                Toast.makeText(SettingsActivity.this, "You did not set passcode yet!", Toast.LENGTH_SHORT).show();
            }
        }if (v==layoutFullbattery){
            showFullBatteryReminderDialog();
        }if (v==layoutLowBattery){
            showLowBatteryReminderDialog();
        }if (v==layoutWarnig){
            SetTemperatueWarningLevel();
        }
    }

    private void ActivateVibrationOrRingMode() {
        if (OnVibrationOrRingMode.isChecked()){
            SharedPreferences.Editor ee=sharedPreference.edit();
            ee.putString("OnVibrationOrRingMode","false");
            ee.apply();
            OnVibrationOrRingMode.setChecked(false);
            Toast.makeText(this, "Ring Mode Activated!", Toast.LENGTH_SHORT).show();
        }else {
            SharedPreferences.Editor ee=sharedPreference.edit();
            ee.putString("OnVibrationOrRingMode","true");
            ee.apply();
            OnVibrationOrRingMode.setChecked(true);
            Toast.makeText(this, "Vibration Mode Activated!", Toast.LENGTH_SHORT).show();
        }
    }


    public void SilentMode(){

        NotificationManager notificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !notificationManager.isNotificationPolicyAccessGranted()) {
            new AlertDialog.Builder(SettingsActivity.this)
                    .setTitle("Need Permission")
                    .setMessage("You need to provide access to Full Battery Alarm. Kindly press 'Ok' and Allow to modify system settings.")
                    .setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(
                            android.provider.Settings
                                    .ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                    startActivity(intent);
                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            }).show();
        }else{
            if (OnSilentMode.isChecked()){
                    OnSilentMode.setChecked(false);
                    SharedPreferences.Editor ee=sharedPreference.edit();
                    ee.putString("SilentModeStatus","false");
                    ee.apply();
            }else {
                    OnSilentMode.setChecked(true);
                    SharedPreferences.Editor ee=sharedPreference.edit();
                    ee.putString("SilentModeStatus","true");
                    ee.apply();

            }
        }

        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            if (OnSilentMode.isChecked()){
                OnSilentMode.setChecked(false);
                SharedPreferences.Editor ee=sharedPreference.edit();
                ee.putString("SilentModeStatus","false");
                ee.apply();
            }else {
                OnSilentMode.setChecked(true);
                SharedPreferences.Editor ee=sharedPreference.edit();
                ee.putString("SilentModeStatus","true");
                ee.apply();
            }
        }

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            boolean s=ContextCompat.checkSelfPermission(SettingsActivity.this, Manifest.permission.ACCESS_NOTIFICATION_POLICY) == PackageManager.PERMISSION_GRANTED;
//            if (s){
//                if (OnSilentMode.isChecked()){
//                    OnSilentMode.setChecked(false);
//                    SharedPreferences.Editor ee=sharedPreference.edit();
//                    ee.putString("SilentModeStatus","false");
//                    ee.apply();
//                }else {
//                    OnSilentMode.setChecked(true);
//                    SharedPreferences.Editor ee=sharedPreference.edit();
//                    ee.putString("SilentModeStatus","true");
//                    ee.apply();
//                }
//            }else {
//                new AlertDialog.Builder(SettingsActivity.this)
//                        .setTitle("Need Permission")
//                        .setMessage("You need to provide access to Full Battery Alarm. Kindly press 'Ok' and Allow to modify system settings.")
//                        .setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Intent intent = new Intent(
//                                android.provider.Settings
//                                        .ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
//                        startActivity(intent);
//                    }
//                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                    }
//                }).show();
//            }
//        }
// else{
//            if (OnSilentMode.isChecked()){
//                OnSilentMode.setChecked(false);
//                SharedPreferences.Editor ee=sharedPreference.edit();
//                ee.putString("SilentModeStatus","false");
//                ee.apply();
//            }else {
//                OnSilentMode.setChecked(true);
//                SharedPreferences.Editor ee=sharedPreference.edit();
//                ee.putString("SilentModeStatus","true");
//                ee.apply();
//            }
//
//        }
    }

    public void RebootMethod(){
        if (OnReboot.isChecked()){
            SharedPreferences.Editor ee=sharedPreference.edit();
            ee.putString("StartupOnReboot","false");
            ee.apply();
            OnReboot.setChecked(false);
        }else {
            SharedPreferences.Editor ee=sharedPreference.edit();
            ee.putString("StartupOnReboot","true");
            ee.apply();
            OnReboot.setChecked(true);
        }
    }
    public void NotificationPanelMethod(){
        if (NotificationPanel.isChecked()){
            NotificationPanel.setChecked(false);
            SharedPreferences.Editor ee=sharedPreference.edit();
            ee.putString("NotifyStatus","false");
            ee.apply();
        }else {
            NotificationPanel.setChecked(true);
            SharedPreferences.Editor ee=sharedPreference.edit();
            ee.putString("NotifyStatus","true");
            ee.apply();
        }
    }
    public void TheftAlarmMethod(){
        String finalTheftPassword = sharedPreference.getString("finalTheftPassword", "");
        if (finalTheftPassword.length()>0) {
            if (TheftAlarm.isChecked()) {
                TheftAlarm.setChecked(false);
                SharedPreferences.Editor ee = sharedPreference.edit();
                ee.putString("theftAutoEnable", "false");
                ee.apply();
            } else {
                SharedPreferences.Editor ee = sharedPreference.edit();
                ee.putString("theftAutoEnable", "true");
                ee.apply();
                TheftAlarm.setChecked(true);
            }
        }else {
            Intent intent=new Intent(SettingsActivity.this,ThaftAlarmActivity.class);
            startActivity(intent);
        }
    }
    public void Volume(){
        View vv= LayoutInflater.from(SettingsActivity.this).inflate(R.layout.about_phone_battery,null);
        final AlertDialog ab=new AlertDialog.Builder(SettingsActivity.this,R.style.MyDialogTheme).create();
        //ab.setCancelable(false);
        //audioManager.setStreamVolume(AudioManager.STREAM_RING, set_volume, 0);
        final AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);


        SeekBar volControl = (SeekBar)vv.findViewById(R.id.seekBar);
        LinearLayout volume_bg = (LinearLayout)vv.findViewById(R.id.volume_bg);
        SetBackGroundColor(volume_bg);
        volControl.setMax(maxVolume);
        volControl.setProgress(curVolume);
        volControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar arg0) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar arg0) {
            }

            @Override
            public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
                SharedPreferences.Editor ee=sharedPreference.edit();
                ee.putString("SoundeLevel",String.valueOf(arg1));
                ee.apply();
                String SoundeLevel = sharedPreference.getString("SoundeLevel", "");
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, Integer.parseInt(SoundeLevel), 0);
//                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
//                        arg1, AudioManager.FLAG_PLAY_SOUND);

//                Toast.makeText(SettingsActivity.this, String.valueOf(arg1), Toast.LENGTH_SHORT).show();
            }
        });

//        ImageView imgbackPress=(ImageView)vv.findViewById(R.id.backPress);
//        imgbackPress.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ab.dismiss();
//            }
//        });
        ab.setView(vv);
        ab.show();
    }


    public void SetBackGroundColor(LinearLayout volume_bg){
        SharedPreferences sp = getSharedPreferences("SharedPreference", 0);
        String ThemeColorName;
        try{
            ThemeColorName = sp.getString("ThemeColorName", "");
        }catch (Exception e){
            ThemeColorName="d";
        }
        if (ThemeColorName.equals("Turquoise")){
            volume_bg.setBackgroundColor(Color.parseColor("#1abc9c"));
        }else if (ThemeColorName.equals("Greensea")){
            volume_bg.setBackgroundColor(Color.parseColor("#16a085"));
        }else if (ThemeColorName.equals("Sunflower")){
            volume_bg.setBackgroundColor(Color.parseColor("#f1c40f"));
        }else if (ThemeColorName.equals("Orange")){
            volume_bg.setBackgroundColor(Color.parseColor("#f39c12"));
        }else if (ThemeColorName.equals("Emerland")){
            volume_bg.setBackgroundColor(Color.parseColor("#2ecc71"));
        }else if (ThemeColorName.equals("Nephritis")){
            volume_bg.setBackgroundColor(Color.parseColor("#27ae60"));
        }else if (ThemeColorName.equals("Carrot")){
            volume_bg.setBackgroundColor(Color.parseColor("#e67e22"));
        }else if (ThemeColorName.equals("Pumpkin")){
            volume_bg.setBackgroundColor(Color.parseColor("#d35400"));
        }else if (ThemeColorName.equals("Peterriver")){
            volume_bg.setBackgroundColor(Color.parseColor("#3498db"));
        }else if (ThemeColorName.equals("Belizehole")){
            volume_bg.setBackgroundColor(Color.parseColor("#2980b9"));
        }else if (ThemeColorName.equals("Alizarin")){
            volume_bg.setBackgroundColor(Color.parseColor("#e74c3c"));
        }else if (ThemeColorName.equals("Silver")){
            volume_bg.setBackgroundColor(Color.parseColor("#bdc3c7"));
        }else {
            volume_bg.setBackgroundColor(Color.parseColor("#e74343"));
        }

    }
    public void showFullBatteryReminderDialog() {
        String whichCheck = sharedPreference.getString("FullBatteryLevelPos", "");
        String title = sharedPreference.getString("SelectFullBatteryLevel", "");
        try {
            if (whichCheck.length()!=0);
            else whichCheck="15";
        }catch (Exception e){
            whichCheck="15";
        }
        final String color[]={"50 %","60 %","70 %","80 %","85 %","90 %","91 %","92 %","93 %","94 %","95 %","96 %","97 %","98 %","99 %","100 %(default)"};
        AlertDialog.Builder dialog=new AlertDialog.Builder(this,R.style.MyDialogTheme);
        dialog.setCancelable(false);
        if (!title.equals(""))
            dialog.setTitle(title);
        else dialog.setTitle("Select Full Battery Level");
        dialog.setSingleChoiceItems(color, Integer.parseInt(whichCheck), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), "If remaining level "+color[which]+". You'll notify!", Toast.LENGTH_SHORT).show();

                switch(which)
                {
                    case 0:
                        strFullBatteryLevel="50";
                        break;
                    case 1:
                        strFullBatteryLevel="60";
                        break;
                    case 2:
                        strFullBatteryLevel="70";
                        break;
                    case 3:
                        strFullBatteryLevel="80";
                        break;
                    case 4:
                        strFullBatteryLevel="85";
                        break;
                    case 5:
                        strFullBatteryLevel="90";
                        break;
                    case 6:
                        strFullBatteryLevel="91";
                        break;
                    case 7:
                        strFullBatteryLevel="92";
                        break;
                    case 8:
                        strFullBatteryLevel="93";
                        break;
                    case 9:
                        strFullBatteryLevel="94";
                        break;
                    case 10:
                        strFullBatteryLevel="95";
                        break;
                    case 11:
                        strFullBatteryLevel="96";
                        break;
                    case 12:
                        strFullBatteryLevel="97";
                        break;
                    case 13:
                        strFullBatteryLevel="98";
                        break;
                    case 14:
                        strFullBatteryLevel="99";
                        break;
                    case 15:
                        strFullBatteryLevel="100";
                        break;
                }

                strFullBatteryLevelPos=String.valueOf(which);
                SharedPreferences.Editor ee6=sharedPreference.edit();
                ee6.putString("FullBatteryLevel",strFullBatteryLevel);
                ee6.putString("FullBatteryLevelPos",strFullBatteryLevelPos);
                ee6.putString("Triggered", "ON");
                ee6.apply();


                fullBatteryLevel.setText("Alert At Selected Battery level : "+strFullBatteryLevel);
                dialog.dismiss();
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
    public void showLowBatteryReminderDialog() {
        String whichCheck = sharedPreference.getString("LowBatteryLevelPos", "");
        String SelectCustomBatteryLevel = sharedPreference.getString("SelectCustomBatteryLevel", "");

        try {
            if (whichCheck.length()!=0);
            else whichCheck="0";
        }catch (Exception e){
            whichCheck="0";
        }
        final String color[]={"Off","5 %","10 %","15 %","20 %","25 %","30 %","35 %","40 %","45 %","50 %"};
        AlertDialog.Builder dialog=new AlertDialog.Builder(this,R.style.MyDialogTheme);
        dialog.setCancelable(false);
        if (!SelectCustomBatteryLevel.equals(""))
            dialog.setTitle(SelectCustomBatteryLevel);
        else  dialog.setTitle("Select Custom Battery Level");
        dialog.setSingleChoiceItems(color, Integer.parseInt(whichCheck), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), "If remaining level "+color[which]+". You'll notify!", Toast.LENGTH_SHORT).show();

                switch(which) {
                    case 0:
                        strLowBatteryLevel="0";
                        break;
                    case 1:
                        strLowBatteryLevel="5";
                        break;
                    case 2:
                        strLowBatteryLevel="10";
                        break;
                    case 3:
                        strLowBatteryLevel="15";
                        break;
                    case 4:
                        strLowBatteryLevel="20";
                        break;
                    case 5:
                        strLowBatteryLevel="25";
                        break;
                    case 6:
                        strLowBatteryLevel="30";
                        break;
                    case 7:
                        strLowBatteryLevel="35";
                        break;
                    case 8:
                        strLowBatteryLevel="40";
                        break;
                    case 9:
                        strLowBatteryLevel="45";
                        break;
                    case 10:
                        strLowBatteryLevel="50";
                        break;
                }
                strLowBatteryLevelPos=String.valueOf(which);
                SharedPreferences.Editor ee6=sharedPreference.edit();
                ee6.putString("LowBatteryLevel",strLowBatteryLevel);
                ee6.putString("LowBatteryLevelPos",strLowBatteryLevelPos);
                ee6.apply();
                if (strLowBatteryLevelPos.equals("0"))
                    lowBatteryLevel.setText("Off");
                else
                    lowBatteryLevel.setText("Alert At Selected Battery level : "+strLowBatteryLevel);
                dialog.dismiss();
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
    public void SetTemperatueWarningLevel() {
        String whichCheck = sharedPreference.getString("TempWarnLevelPos", "");
        String SelectTemperatureWarningLevel = sharedPreference.getString("SelectTemperatureWarningLevel", "");


        try {
            if (whichCheck.length()!=0);
            else whichCheck="0";
        }catch (Exception e){
            whichCheck="0";
        }
        final String color[]={"Off","30°C ","31°C ","32°C ","33°C ","34°C ","35°C ","36°C ","37°C ","38°C ","39°C ","40°C ","41°C ","42°C ","43°C ","44°C ","45°C "};
        final String colorNew[]={"Off","30°C / 86°F","31°C / 87.8°F","32°C / 89.6°F","33°C / 91.4°F","34°C / 93.2°F","35°C / 95°F","36°C / 96.8°F","37°C / 98.6°F","38°C / 100.4°F","39°C / 102.2°F","40°C / 104°F","41°C / 105.8°F","42°C / 107.6°F","43°C / 109.4°F","44°C / 111.2°F","45°C / 113°F"};
        AlertDialog.Builder dialog=new AlertDialog.Builder(this,R.style.MyDialogTheme);
        dialog.setCancelable(false);
        if (!SelectTemperatureWarningLevel.equals(""))
            dialog.setTitle(SelectTemperatureWarningLevel);
        else dialog.setTitle("Select Temperature Warning Level");
        dialog.setSingleChoiceItems(colorNew, Integer.parseInt(whichCheck), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), "If remaining level "+colorNew[which]+"." +
                        " You'll notify!", Toast.LENGTH_SHORT).show();

                switch(which) {
                    case 0:
                        strTempWarnLevel="off";
                        break;
                    case 1:
                        strTempWarnLevel="30";
                        break;
                    case 2:
                        strTempWarnLevel="31";
                        break;
                    case 3:
                        strTempWarnLevel="32";
                        break;
                    case 4:
                        strTempWarnLevel="33";
                        break;
                    case 5:
                        strTempWarnLevel="34";
                        break;
                    case 6:
                        strTempWarnLevel="35";
                        break;
                    case 7:
                        strTempWarnLevel="36";
                        break;
                    case 8:
                        strTempWarnLevel="37";
                        break;
                    case 9:
                        strTempWarnLevel="38";
                        break;
                    case 10:
                        strTempWarnLevel="39";
                        break;
                    case 11:
                        strTempWarnLevel="40";
                        break;
                    case 12:
                        strTempWarnLevel="41";
                        break;
                    case 13:
                        strTempWarnLevel="42";
                        break;
                    case 14:
                        strTempWarnLevel="43";
                        break;
                    case 15:
                        strTempWarnLevel="44";
                        break;
                    case 16:
                        strTempWarnLevel="45";
                        break;
                }
                strTempWarnLevelPos=String.valueOf(which);
                SharedPreferences.Editor ee6=sharedPreference.edit();
                ee6.putString("TempWarnLevel",strTempWarnLevel);
                ee6.putString("TempWarnLevelPos",strTempWarnLevelPos);
                ee6.apply();
                if (strTempWarnLevelPos.equals("off"))
                    tempWarnLevel.setText("Off");
                else tempWarnLevel.setText("Alert At Selected Battery level : "+strTempWarnLevel);
                dialog.dismiss();
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
    public void SetTxtOnView(){
        String str1 = sharedPreference.getString("RingAlarmOnSilentMode", "");
        String str2 = sharedPreference.getString("AutoStartupOnReboot", "");
        String str3 = sharedPreference.getString("ActivateNotificationPanel", "");
        String str4 = sharedPreference.getString("Sound", "");
        String str5 = sharedPreference.getString("ChangeTheftPasscode", "");
        String str6 = sharedPreference.getString("AutoEnableTheftAlarm", "");
        String str7 = sharedPreference.getString("FullBatteryLevelTxt", "");
        String str8 = sharedPreference.getString("LowBatteryLevelTxt", "");
        String str9 = sharedPreference.getString("BatteryTemperatureWarninglevel", "");

        String str10 = sharedPreference.getString("ActivateVibrationMode", "");
        String str11 = sharedPreference.getString("ChangeTempUnit", "");
        String str12 = sharedPreference.getString("BatteryMonitor", "");

        String strTitle = sharedPreference.getString("navSettings", "");
        if (!strTitle.equals(""))
            getSupportActionBar().setTitle(strTitle);


        if (!str1.equals("")){
        OnSilentMode.setText(str1);
        OnReboot.setText(str2);
        NotificationPanel.setText(str3);
        security.setText(str5);
        TheftAlarm.setText(str6);
            OnVibrationOrRingMode.setText(str10);

        sound.setText(str4);
        txtFullBattery.setText(str7);
        txtLowBattery.setText(str8);
        txtTempWarning.setText(str9);
            convertTempUnit.setText(str11);
            graph.setText(str12);
        }
    }

    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context c, Intent intent) {

            //Volume();
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        SetTxtOnView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBatInfoReceiver);
    }
}
