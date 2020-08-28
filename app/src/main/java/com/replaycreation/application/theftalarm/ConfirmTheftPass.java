package com.replaycreation.application.theftalarm;

import android.content.Context;
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

public class ConfirmTheftPass extends AppCompatActivity implements View.OnClickListener {

    Button one, two, three, four, five, six, seven, eight, nine, zero, add, sub, mul, div, cancel, equal;
    EditText EditconfirmPass;
    TextView confirmPass,enterPass;
    SharedPreferences sharedPreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SetColorTheme();
        setContentView(R.layout.activity_confirm_theft_pass);

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        sharedPreference = getSharedPreferences("SharedPreference", 0);
        String Status = sharedPreference.getString("status", "");


        enterPass= (TextView) findViewById(R.id.enterPass);
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

        EditconfirmPass = (EditText) findViewById(R.id.EditconfirmPass);
        confirmPass = (TextView) findViewById(R.id.confirmPass);

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
            confirmPass.setOnClickListener(this);
        }
        catch(Exception e){ }

        if (Status.equals("true")){
            String str_title = sharedPreference.getString("fullBatteryAlarm", "");
            String str_1 = sharedPreference.getString("EnterPasscode", "");
            String str_2 = sharedPreference.getString("ConfirmTheftPasscode", "");
            String str_Confirm = sharedPreference.getString("Confirm", "");

            enterPass.setText(str_1);
            EditconfirmPass.setHint(str_2);
            confirmPass.setText(str_Confirm);
            getSupportActionBar().setTitle(str_title);
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

    @Override
    public void onClick(View v) {
        Editable str =  EditconfirmPass.getText();
        v.startAnimation(AnimationUtils.loadAnimation(ConfirmTheftPass.this, R.anim.click));
        switch(v.getId()) {
            case R.id.zero:
                str = str.append(zero.getText());
                EditconfirmPass.setText(str);
                break;
            case R.id.one:
                str = str.append(one.getText());
                EditconfirmPass.setText(str);
                break;
            case R.id.two:
                str = str.append(two.getText());
                EditconfirmPass.setText(str);
                break;
            case R.id.three:
                str = str.append(three.getText());
                EditconfirmPass.setText(str);
                break;
            case R.id.four:
                str = str.append(four.getText());
                EditconfirmPass.setText(str);
                break;
            case R.id.five:
                str = str.append(five.getText());
                EditconfirmPass.setText(str);
                break;
            case R.id.six:
                str = str.append(six.getText());
                EditconfirmPass.setText(str);
                break;
            case R.id.seven:
                str = str.append(seven.getText());
                EditconfirmPass.setText(str);
                break;
            case R.id.eight:
                str = str.append(eight.getText());
                EditconfirmPass.setText(str);
                break;
            case R.id.nine:
                str = str.append(nine.getText());
                EditconfirmPass.setText(str);
                break;
            case R.id.confirmPass:
                String str_pass = (EditconfirmPass.getText().toString().trim());
                String StrTheftPassword = sharedPreference.getString("theftPassword", "");
                String chargeVia = sharedPreference.getString("strChargeVia", "");
                if (StrTheftPassword.equals(str_pass)){
                    if (!chargeVia.equals("Unknown")){
                        SharedPreferences.Editor ee=sharedPreference.edit();
                        ee.putString("TheftAlarmStatus","ON");
                        ee.commit();
                    }
                    SharedPreferences.Editor ee=sharedPreference.edit();
                    ee.putString("finalTheftPassword",str_pass);
                    ee.commit();
                    finish();
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                }else {
                    EditconfirmPass.getText().clear();
                    Toast.makeText(this, "Passcode not matched", Toast.LENGTH_SHORT).show();}
                break;
            case R.id.cancel:
                String strpassword = (EditconfirmPass.getText().toString().trim());
                String returnStr=method(strpassword);
                EditconfirmPass.setText(returnStr);
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
