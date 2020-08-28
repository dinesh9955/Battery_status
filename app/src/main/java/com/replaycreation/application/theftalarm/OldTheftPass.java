package com.replaycreation.application.theftalarm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.replaycreation.application.R;

public class OldTheftPass extends AppCompatActivity implements View.OnClickListener {
    Button one, two, three, four, five, six, seven, eight, nine, zero, add, sub, mul, div, cancel, equal;
    EditText oldPass;
    TextView changePss,enterOldPass;
    SharedPreferences sharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SetColorTheme();
        setContentView(R.layout.activity_old_theft_pass);

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        sharedPreference = getSharedPreferences("SharedPreference", 0);
        String Status = sharedPreference.getString("status", "");

        enterOldPass= (TextView) findViewById(R.id.enterOldPass);
        one = (Button) findViewById(R.id.one);
        two = (Button) findViewById(R.id.two);
        three = (Button) findViewById(R.id.three);
        four = (Button) findViewById(R.id.four);
        five = (Button) findViewById(R.id.five);
        six = (Button) findViewById(R.id.six);
        seven = (Button) findViewById(R.id.seven);
        eight = (Button) findViewById(R.id.eight);
        nine = (Button) findViewById(R.id.nine);
        zero = (Button) findViewById(R.id.zero);
        cancel = (Button) findViewById(R.id.cancel);


        oldPass = (EditText) findViewById(R.id.oldPass);
        changePss = (TextView) findViewById(R.id.changePss);

        try {
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

            changePss.setOnClickListener(this);
        } catch (Exception e) {

        }

        if (Status.equals("true")){
            String str_title = sharedPreference.getString("fullBatteryAlarm", "");
            String str_EnterOldPasscode = sharedPreference.getString("EnterOldPasscode", "");
            String str_OldPassword = sharedPreference.getString("OldPassword", "");
            String str_Set = sharedPreference.getString("Change", "");

            enterOldPass.setText(str_EnterOldPasscode);
            oldPass.setHint(str_OldPassword);
            changePss.setText(str_Set);
            getSupportActionBar().setTitle(str_title);
        }

    }

    @Override
    public void onClick(View v) {
        Editable str = oldPass.getText();
        v.startAnimation(AnimationUtils.loadAnimation(OldTheftPass.this, R.anim.click));
        switch (v.getId()) {
            case R.id.zero:
                str = str.append(zero.getText());
                oldPass.setText(str);
                break;
            case R.id.one:
                str = str.append(one.getText());
                oldPass.setText(str);
                break;
            case R.id.two:
                str = str.append(two.getText());
                oldPass.setText(str);
                break;
            case R.id.three:
                str = str.append(three.getText());
                oldPass.setText(str);
                break;
            case R.id.four:
                str = str.append(four.getText());
                oldPass.setText(str);
                break;
            case R.id.five:
                str = str.append(five.getText());
                oldPass.setText(str);
                break;
            case R.id.six:
                str = str.append(six.getText());
                oldPass.setText(str);
                break;
            case R.id.seven:
                str = str.append(seven.getText());
                oldPass.setText(str);
                break;
            case R.id.eight:
                str = str.append(eight.getText());
                oldPass.setText(str);
                break;
            case R.id.nine:
                str = str.append(nine.getText());
                oldPass.setText(str);
                break;
            case R.id.changePss:
                String str_pass = (oldPass.getText().toString().trim());
                String finalTheftPassword = sharedPreference.getString("finalTheftPassword", "");
                if (finalTheftPassword.equals(str_pass)){
                    Intent i=new Intent(OldTheftPass.this,ThaftAlarmActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    oldPass.getText().clear();
                    Toast.makeText(this, "Wrong Password", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.cancel:
                String strpassword = (oldPass.getText().toString().trim());
                String returnStr = method(strpassword);
                oldPass.setText(returnStr);
                break;
        }
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

    public String method(String str) {
        if (str.length() > 0) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }
}