package com.replaycreation.application.alertpakage;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.replaycreation.application.R;

public class TemperatureAlertDialog extends Activity {

    ImageView imgCancel;
    TextView txtempWarn,temperature;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature_alert_dialog);
        SharedPreferences sharedPreference = getSharedPreferences("SharedPreference", 0);
        String TempWarnLevelStatus = sharedPreference.getString("TempWarnLevel", "");

        ((Vibrator)getSystemService(VIBRATOR_SERVICE)).vibrate(800);

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        imgCancel=(ImageView)findViewById(R.id.imgCancel);
        txtempWarn=(TextView)findViewById(R.id.txtempWarn);
        temperature=(TextView)findViewById(R.id.temperature);
        temperature.setText("Temp >"+TempWarnLevelStatus+"°C");
        txtempWarn.setText("Your device temperature has reached "+ TempWarnLevelStatus+"°C. Close all open application or clean device RAM.");
        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
