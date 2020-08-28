package com.replaycreation.application.theftalarm;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.replaycreation.application.R;

public class StopTheftAlarmActivity extends AppCompatActivity implements View.OnClickListener {

    MediaPlayer mp=new MediaPlayer();
    SharedPreferences sharedPreference;
    TextView theftPass;
    String StrTheftPassword;
    String theftActivation="";
    TextView one, two, three, four, five, six, seven, eight, nine, zero,cancel;
    AudioManager audioManager;
    boolean checkSilentMode=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SetColorTheme();
        setContentView(R.layout.activity_stop_theft_alarm);

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        sharedPreference = getSharedPreferences("SharedPreference", 0);
        StrTheftPassword = sharedPreference.getString("theftPassword", "");

        Bundle bundle = getIntent().getExtras();
        try {
            theftActivation = bundle.getString("theftDeactivate");
        }catch (Exception e){
            theftActivation = "P";
        }

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        if ( audioManager.getRingerMode() != AudioManager.RINGER_MODE_NORMAL ){
            //String SilentModeStatus = sharedPreference.getString("SilentModeStatus", "");
            //new AlertDialog.Builder(StopTheftAlarmActivity.this).setMessage(SilentModeStatus).show();

            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 20, 0);
            checkSilentMode=true;
            if (theftActivation.equals("P")) {
                mp = MediaPlayer.create(this, R.raw.police_siren);
                mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mp.setLooping(true);
                mp.start();
            }
        }else if ( audioManager.getRingerMode() == AudioManager.RINGER_MODE_NORMAL ){
            if (theftActivation.equals("P")) {
                audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                mp = MediaPlayer.create(this, R.raw.police_siren);
                mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mp.setLooping(true);
                mp.start();
            }
        }

        theftPass=(TextView) findViewById(R.id.password);
        theftPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str=theftPass.getText().toString().trim();
                if (str.equals(StrTheftPassword)){
                    mp.stop();
                    if (checkSilentMode)
                        audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                    if (theftActivation.equals("T")) {
                        SharedPreferences.Editor ee=sharedPreference.edit();
                        ee.putString("FullAlarmStatus","OFF");
                        ee.apply();
                    }
                    SharedPreferences.Editor ee=sharedPreference.edit();
                    ee.putString("TheftAlarmStatus","");
                    ee.putString("Theft","");
                    ee.apply();
                    finish();
                }if(!str.equals(StrTheftPassword) && StrTheftPassword.length()==str.length()){
                    theftPass.setText(null);
                    Toast.makeText(StopTheftAlarmActivity.this, "You entered wrong passcode.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (StrTheftPassword.equals(s)){
                    mp.stop();
                    SharedPreferences.Editor ee=sharedPreference.edit();
                    ee.putString("TheftAlarmStatus","");
                    ee.putString("Theft","ON");
                    ee.apply();
                    finish();
                }
            }
        });


        one = (TextView) findViewById(R.id.one);
        two = (TextView) findViewById(R.id.two);
        three = (TextView) findViewById(R.id.three);
        four = (TextView) findViewById(R.id.four);
        five = (TextView) findViewById(R.id.five);
        six = (TextView) findViewById(R.id.six);
        seven = (TextView) findViewById(R.id.seven);
        eight = (TextView) findViewById(R.id.eight);
        nine = (TextView) findViewById(R.id.nine);
        zero = (TextView) findViewById(R.id.zero);
        cancel = (TextView) findViewById(R.id.cancl);

        try{
            one.setOnClickListener(this);
            two.setOnClickListener(this);
            three.setOnClickListener(this);
            four.setOnClickListener(this);
            five.setOnClickListener(this);
            six.setOnClickListener(this);
            seven.setOnClickListener(this);
            eight.setOnClickListener(this);
            nine.setOnClickListener(this);
            zero.setOnClickListener(this);
            cancel.setOnClickListener(this);
        }catch(Exception e){}
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
        String str =  theftPass.getText().toString().trim();
        v.startAnimation(AnimationUtils.loadAnimation(StopTheftAlarmActivity.this, R.anim.click));
        switch(v.getId()) {
            case R.id.zero:
                str = str+(zero.getText().toString().trim());
                theftPass.setText(str);
                break;
            case R.id.one:
                str = str+(one.getText().toString().trim());
                theftPass.setText(str);
                break;
            case R.id.two:
                str = str+(two.getText().toString().trim());
                theftPass.setText(str);
                break;
            case R.id.three:
                str = str+(three.getText().toString().trim());
                theftPass.setText(str);
                break;
            case R.id.four:
                str = str+(four.getText().toString().trim());
                theftPass.setText(str);
                break;
            case R.id.five:
                str = str+(five.getText().toString().trim());
                theftPass.setText(str);
                break;
            case R.id.six:
                str = str+(six.getText().toString().trim());
                theftPass.setText(str);
                break;
            case R.id.seven:
                str = str+(seven.getText().toString().trim());
                theftPass.setText(str);
                break;
            case R.id.eight:
                str = str+(eight.getText().toString().trim());
                theftPass.setText(str);
                break;
            case R.id.nine:
                str = str+(nine.getText().toString().trim());
                theftPass.setText(str);
                break;

            case R.id.cancl:
                String strpassword = (theftPass.getText().toString().trim());
                String returnStr=method(strpassword);
                theftPass.setText(returnStr);
                break;
        }
    }

    public String method(String str) {
        if (str.length() > 0) {
            str = str.substring(0, str.length()-1);
        }
        return str;
    }


}
