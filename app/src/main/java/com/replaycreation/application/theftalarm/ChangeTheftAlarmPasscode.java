package com.replaycreation.application.theftalarm;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.replaycreation.application.R;

public class ChangeTheftAlarmPasscode extends AppCompatActivity implements View.OnClickListener {

    SharedPreferences sharedPreference;
    TextView passArea,titleTxt,txtNext;
    String oldPass,newPass,confrmPass;
    TextView one, two, three, four, five, six, seven, eight, nine, zero, cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_theft_alarm_passcode);

        sharedPreference = getSharedPreferences("SharedPreference", 0);
        oldPass = sharedPreference.getString("finalTheftPassword", "" );
        String Status = sharedPreference.getString("status", "");
        if (Status.equals("true")){
            String str_title = sharedPreference.getString("fullBatteryAlarm", "");
            getSupportActionBar().setTitle(str_title);
        }
        //Toast.makeText(this, oldPass, Toast.LENGTH_SHORT).show();

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

        titleTxt=(TextView)findViewById(R.id.titleTxt);
        txtNext=(TextView)findViewById(R.id.txtNext);
        passArea=(TextView)findViewById(R.id.password);
        passArea.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str=passArea.getText().toString().trim();
                String title=titleTxt.getText().toString().trim();
                if (title.equals("Old Passcode")) {
                    if (oldPass.equals(str)) {
                        titleTxt.setText("NEW PASSCODE");
                        passArea.setText(null);
                        txtNext.setVisibility(View.VISIBLE);
                    }
                    if (str.length()==oldPass.length() && !oldPass.equals(str)){
                        passArea.setText("");
                        Toast.makeText(ChangeTheftAlarmPasscode.this, "Please enter true passcode!", Toast.LENGTH_SHORT).show();
                    }
                }
                if (title.equals("CONFIRM PASSCODE")){
                    String strConfrmPass=passArea.getText().toString().trim();
                    if (newPass.equals(strConfrmPass)){
                        SharedPreferences.Editor ee=sharedPreference.edit();
                        ee.putString("theftPassword",strConfrmPass);
                        ee.commit();
                        Toast.makeText(ChangeTheftAlarmPasscode.this, "Passcode chenged successfully!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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


            txtNext.setOnClickListener(this);
        } catch (Exception e) {
        }
    }

    @Override
    public void onClick(View v) {
        String str = passArea.getText().toString().trim();
        v.startAnimation(AnimationUtils.loadAnimation(ChangeTheftAlarmPasscode.this, R.anim.click));
        switch (v.getId()) {
            case R.id.zero:
                str = str + (zero.getText().toString().trim());
                passArea.setText(str);
                break;
            case R.id.one:
                str = str + (one.getText().toString().trim());
                passArea.setText(str);
                break;
            case R.id.two:
                str = str + (two.getText().toString().trim());
                passArea.setText(str);
                break;
            case R.id.three:
                str = str + (three.getText().toString().trim());
                passArea.setText(str);
                break;
            case R.id.four:
                str = str + (four.getText().toString().trim());
                passArea.setText(str);
                break;
            case R.id.five:
                str = str + (five.getText().toString().trim());
                passArea.setText(str);
                break;
            case R.id.six:
                str = str + (six.getText().toString().trim());
                passArea.setText(str);
                break;
            case R.id.seven:
                str = str + (seven.getText().toString().trim());
                passArea.setText(str);
                break;
            case R.id.eight:
                str = str + (eight.getText().toString().trim());
                passArea.setText(str);
                break;
            case R.id.nine:
                str = str + (nine.getText().toString().trim());
                passArea.setText(str);
                break;

            case R.id.cancl:
                String strpassword = (passArea.getText().toString().trim());
                String returnStr = method(strpassword);
                passArea.setText(returnStr);
                break;
            case R.id.txtNext:
                String title=titleTxt.getText().toString().trim();
                if (title.equals("NEW PASSCODE")) {
                    String strNewPass=passArea.getText().toString().trim();
                    if (strNewPass.length()>=4){
                        newPass=strNewPass;
                        passArea.setText(null);
                        titleTxt.setText("CONFIRM PASSCODE");
                    }
                }
                break;
        }
    }

    public String method(String str) {
        if (str.length() > 0) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }
}