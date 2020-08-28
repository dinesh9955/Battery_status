package com.replaycreation.application.theftalarm;


import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.replaycreation.application.R;

public class ThaftAlarmActivity extends AppCompatActivity implements View.OnClickListener {

    Button one, two, three, four, five, six, seven, eight, nine, zero,cancel;
    EditText disp;
    TextView CONFIRM,setTheftPss;
    SharedPreferences sharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SetColorTheme();
        setContentView(R.layout.activity_thaft_alarm);
        sharedPreference = getSharedPreferences("SharedPreference", 0);
        String Status = sharedPreference.getString("status", "");

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        setTheftPss= (TextView) findViewById(R.id.setTheftPass);
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

        disp = (EditText) findViewById(R.id.display);
        CONFIRM = (TextView) findViewById(R.id.CONFIRM);

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
            CONFIRM.setOnClickListener(this);
        }
        catch(Exception e){

        }

        if (Status.equals("true")){
            String str_title = sharedPreference.getString("fullBatteryAlarm", "");
            String str_setTheftPss = sharedPreference.getString("SetTheftPasscode", "");
            String str_PasscodeArea = sharedPreference.getString("PasscodeArea", "");
            String str_Set = sharedPreference.getString("Set", "");

            setTheftPss.setText(str_setTheftPss);
            disp.setHint(str_PasscodeArea);
            CONFIRM.setText(str_Set);
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
        Editable str =  disp.getText();
        v.startAnimation(AnimationUtils.loadAnimation(ThaftAlarmActivity.this, R.anim.click));
        switch(v.getId()) {
            case R.id.zero:
                str = str.append(zero.getText());
                disp.setText(str);
                break;
            case R.id.one:
                str = str.append(one.getText());
                disp.setText(str);
                break;
            case R.id.two:
                str = str.append(two.getText());
                disp.setText(str);
                break;
            case R.id.three:
                str = str.append(three.getText());
                disp.setText(str);
                break;
            case R.id.four:
                str = str.append(four.getText());
                disp.setText(str);
                break;
            case R.id.five:
                str = str.append(five.getText());
                disp.setText(str);
                break;
            case R.id.six:
                str = str.append(six.getText());
                disp.setText(str);
                break;
            case R.id.seven:
                str = str.append(seven.getText());
                disp.setText(str);
                break;
            case R.id.eight:
                str = str.append(eight.getText());
                disp.setText(str);
                break;
            case R.id.nine:
                str = str.append(nine.getText());
                disp.setText(str);
                break;
            case R.id.CONFIRM:

                String str_pass = (disp.getText().toString().trim());
                //Toast.makeText(this, str_pass, Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor ee=sharedPreference.edit();
                ee.putString("theftPassword",str_pass);
                ee.commit();
                if (str_pass.length()>=4){
                    Intent i=new Intent(ThaftAlarmActivity.this,ConfirmTheftPass.class);
                    startActivity(i);
                    finish();
                }else{
                    Toast.makeText(this, "Pass length should contain minimum 4 digit.", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.cancel:
                String strpassword = (disp.getText().toString().trim());
                String returnStr=method(strpassword);
                disp.setText(returnStr);
                //Toast.makeText(this, returnStr, Toast.LENGTH_SHORT).show();
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
