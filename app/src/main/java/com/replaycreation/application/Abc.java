package com.replaycreation.application;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.replaycreation.application.service.MyService;

public class Abc extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.list_header_view);

//        Intent i = new Intent(this, StopAlarmActivity.class);
//        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(i);


        if(!ServiceTools.isServiceRunning(getApplicationContext(), new MyService().getClass().getName())){
            Intent intent = new Intent(getApplicationContext(), MyService.class);
            intent.setAction(MyService.ACTION_START_FOREGROUND_SERVICE);
            startService(intent);
        }

    }
}
