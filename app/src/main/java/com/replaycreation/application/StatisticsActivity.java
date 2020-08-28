package com.replaycreation.application;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StatisticsActivity extends AppCompatActivity {

    String healthTv,batteryPctTv,pluggedTv,chargingStatusTv,voltageTv,tempTv,technologyTv,totalCapacity;
    SharedPreferences sharedPreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SetColorTheme();;
        setContentView(R.layout.activity_statistics);

        SetBackGroundColor();
        totalCapacity=getIntent().getStringExtra("totalCapacity");
        healthTv=getIntent().getStringExtra("health");
        batteryPctTv=getIntent().getStringExtra("batteryPct");
        pluggedTv=getIntent().getStringExtra("plugged");
        chargingStatusTv=getIntent().getStringExtra("chargingStatus");
        voltageTv=getIntent().getStringExtra("voltage");
        tempTv=getIntent().getStringExtra("temp");
        technologyTv=getIntent().getStringExtra("technology");


        sharedPreference = getSharedPreferences("SharedPreference", 0);
        String Status = sharedPreference.getString("status", "");

        String str_batteryCapacity = sharedPreference.getString("batteryCapacity", "");
        String str_batteryHealth = sharedPreference.getString("batteryHealth", "");
        String str_batteryLevel = sharedPreference.getString("batteryLevel", "");
        String str_plugged = sharedPreference.getString("plugged", "");
        String str_technology = sharedPreference.getString("technology", "");
        String str_chargingStatus = sharedPreference.getString("chargingStatus", "");
        String str_voltage = sharedPreference.getString("voltage", "");
        String str_temperature = sharedPreference.getString("temperature", "");
        String str_statistics = sharedPreference.getString("statistics", "");
        String str_title = sharedPreference.getString("fullBatteryAlarm", "");



        TextView batteryCapacity= (TextView) findViewById(R.id.capacityTxt);
        TextView health= (TextView) findViewById(R.id.health);
        TextView level= (TextView) findViewById(R.id.level);
        TextView plugged= (TextView) findViewById(R.id.plugged);
        TextView chargingstatus= (TextView) findViewById(R.id.chargingstatus);
        TextView voltage= (TextView) findViewById(R.id.voltage);
        TextView temp= (TextView) findViewById(R.id.temp);
        TextView tech= (TextView) findViewById(R.id.tech);
        TextView statistics= (TextView) findViewById(R.id.statistics);

        if (Status.equals("true")) {
            getSupportActionBar().setTitle(str_title);
            batteryCapacity.setText(str_batteryCapacity);
            health.setText(str_batteryHealth);
            level.setText(str_batteryLevel);
            plugged.setText(str_plugged);
            chargingstatus.setText(str_chargingStatus);
            voltage.setText(str_voltage);
            temp.setText(str_temperature);
            tech.setText(str_technology);
            statistics.setText(str_statistics);
        }


        TextView capacity=(TextView)findViewById(R.id.Capacity);
        TextView txthealthTv=(TextView)findViewById(R.id.TxtHealth);
        TextView txtbatteryPctTv=(TextView)findViewById(R.id.batteryPctTv);
        TextView txtpluggedTv=(TextView)findViewById(R.id.pluggedTv);
        TextView textchargingStatusTv=(TextView)findViewById(R.id.chargingStatusTv);
        TextView txtvoltageTv=(TextView)findViewById(R.id.voltageTv);
        TextView txttempTv=(TextView)findViewById(R.id.tempTv);
        TextView txttechnologyTv=(TextView)findViewById(R.id.technologyTv);
        ImageView imgbackPress=(ImageView)findViewById(R.id.backPress);


        capacity.setText(totalCapacity);
        txthealthTv.setText(healthTv);
        txtbatteryPctTv.setText(batteryPctTv);
        txtpluggedTv.setText(pluggedTv);
        textchargingStatusTv.setText(chargingStatusTv);
        txtvoltageTv.setText(voltageTv);
        txttempTv.setText(tempTv);
        txttechnologyTv.setText(technologyTv);
        imgbackPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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

    public void SetBackGroundColor(){
        SharedPreferences sp = getSharedPreferences("SharedPreference", 0);
        String ThemeColorName;
        LinearLayout mainLayout= (LinearLayout) findViewById(R.id.statisticsLayout);
        try{
            ThemeColorName = sp.getString("ThemeColorName", "");
        }catch (Exception e){
            ThemeColorName="d";
        }
        if (ThemeColorName.equals("Turquoise")){
            mainLayout.setBackgroundColor(Color.parseColor("#1abc9c"));
        }else if (ThemeColorName.equals("Greensea")){
            mainLayout.setBackgroundColor(Color.parseColor("#16a085"));
        }else if (ThemeColorName.equals("Sunflower")){
            mainLayout.setBackgroundColor(Color.parseColor("#f1c40f"));
        }else if (ThemeColorName.equals("Orange")){
            mainLayout.setBackgroundColor(Color.parseColor("#f39c12"));
        }else if (ThemeColorName.equals("Emerland")){
            mainLayout.setBackgroundColor(Color.parseColor("#2ecc71"));
        }else if (ThemeColorName.equals("Nephritis")){
            mainLayout.setBackgroundColor(Color.parseColor("#27ae60"));
        }else if (ThemeColorName.equals("Carrot")){
            mainLayout.setBackgroundColor(Color.parseColor("#e67e22"));
        }else if (ThemeColorName.equals("Pumpkin")){
            mainLayout.setBackgroundColor(Color.parseColor("#d35400"));
        }else if (ThemeColorName.equals("Peterriver")){
            mainLayout.setBackgroundColor(Color.parseColor("#3498db"));
        }else if (ThemeColorName.equals("Belizehole")){
            mainLayout.setBackgroundColor(Color.parseColor("#2980b9"));
        }else if (ThemeColorName.equals("Alizarin")){
            mainLayout.setBackgroundColor(Color.parseColor("#e74c3c"));
        }else if (ThemeColorName.equals("Silver")){
            mainLayout.setBackgroundColor(Color.parseColor("#bdc3c7"));
        }else {
            mainLayout.setBackgroundColor(Color.parseColor("#e74343"));
        }

    }
}
