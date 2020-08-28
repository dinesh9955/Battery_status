package com.replaycreation.application.alertpakage;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
/*import com.inmobi.ads.InMobiAdRequestStatus;
import com.inmobi.ads.InMobiBanner;
import com.inmobi.ads.InMobiNative;
import com.inmobi.sdk.InMobiSdk;*/
import com.replaycreation.application.R;
import com.replaycreation.application.graph.ChargingOrDischargingGraph;


import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;

import static com.google.android.gms.wearable.DataMap.TAG;
import static java.lang.Math.abs;

public class ChargingNotificationLayout extends Activity {

    TextView txt,levelTxt,lastTimeDuration,currentTimeDuration,chargingStatus;
    SharedPreferences sharedPreference;

    String StrChargingConnectTime,StrChargingDisconnetTime;
    private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //InMobiSdk.init(this, "");
        MobileAds.initialize(this, "ca-app-pub-7160073219914148~7699452512");
        Fresco.initialize(this);
        setContentView(R.layout.activity_charging_notification_layout);




        /*Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);*/
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        mAdView = (AdView) findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);

        AdRequest request = new AdRequest.Builder()
                .addTestDevice("83588BB4D69EC1104E3E1A684562C91C")
                .build();
        mAdView.loadAd(request);





        sharedPreference = getSharedPreferences("SharedPreference", 0);
        final String StrChargingConnect = sharedPreference.getString("ChargingConnectTime", "");
        //Toast.makeText(this, StrChargingConnect, Toast.LENGTH_SHORT).show();
        //String StrChargingDisconnetTime = sharedPreference.getString("ChargingDisconnetTime", "");

        try {
            StrChargingConnectTime=getIntent().getStringExtra("ConnectTime");
            StrChargingDisconnetTime=getIntent().getStringExtra("DisConnectTime");
        }catch (Exception e){}

        registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
//        BatteryChangeReceiver receiver = new BatteryChangeReceiver();
//        registerReceiver(receiver, filter);


//        SharedPreferences.Editor ee1 = sharedPreference.edit();
//        ee1.putString("Triggered","ON");
//        ee1.apply();


        ImageView img=(ImageView)findViewById(R.id.img);
        ImageView volt=(ImageView)findViewById(R.id.volt);
        ImageView imgCancel=(ImageView)findViewById(R.id.imgCancel);
        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lastTimeDuration=(TextView)findViewById(R.id.lastTimeDuration) ;
        currentTimeDuration=(TextView)findViewById(R.id.currentTimeDuration) ;
        chargingStatus=(TextView)findViewById(R.id.chargingStatus) ;
        txt=(TextView)findViewById(R.id.txtBatteryLevel);
        levelTxt=(TextView)findViewById(R.id.level);

        /*RelativeLayout bannerAd = (RelativeLayout) findViewById(R.id.banner);
        InMobiBanner bannerAd_ = new InMobiBanner(ChargingNotificationLayout.this, 	1497847531572L);
        bannerAd_.setListener(new InMobiBanner.BannerAdListener() {
            @Override
            public void onAdLoadSucceeded(InMobiBanner inMobiBanner) {
                Log.d(TAG, "onAdLoadSucceeded");
            }

            @Override
            public void onAdLoadFailed(InMobiBanner inMobiBanner, InMobiAdRequestStatus inMobiAdRequestStatus) {
                Log.d(TAG, "Banner ad failed to load with error: " +
                        inMobiAdRequestStatus.getMessage());
            }

            @Override
            public void onAdDisplayed(InMobiBanner inMobiBanner) {
                Log.d(TAG, "onAdDisplayed");
            }

            @Override
            public void onAdDismissed(InMobiBanner inMobiBanner) {
                Log.d(TAG, "onAdDismissed");
            }

            @Override
            public void onAdInteraction(InMobiBanner inMobiBanner, Map<Object, Object> map) {
                Log.d(TAG, "onAdInteraction");
            }

            @Override
            public void onUserLeftApplication(InMobiBanner inMobiBanner) {

            }

            @Override
            public void onAdRewardActionCompleted(InMobiBanner inMobiBanner, Map<Object, Object> map) {

            }
        });
        bannerAd.addView(bannerAd_);
        bannerAd_.load();*/

        Difference(StrChargingConnectTime,StrChargingDisconnetTime);
        Animation myFadeInAnimation = AnimationUtils.loadAnimation(ChargingNotificationLayout.this, R.anim.blink_one);
        volt.startAnimation(myFadeInAnimation);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                Timer updateTimer = new Timer();
                updateTimer.schedule(new TimerTask() {
                    public void run(){
                        try {
                            SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
                            Date Date1 = format.parse(StrChargingConnect);
                            Date Date2 = format.parse(getCurrentTime());
                            long mills = Date1.getTime() - Date2.getTime();
                            Log.v("Data1", ""+Date1.getTime());
                            Log.v("Data2", ""+Date2.getTime());

                            int Hours = abs((int) (mills/(1000 * 60 * 60)));
                            int Mins = abs((int) (mills/(1000*60)) % 60);
                            long Secs = abs((int) (mills / 1000) % 60);

                            String diff = Hours + ":" + Mins+":"+Secs;
                            currentTimeDuration.setText(diff);
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }, 0, 1000);

            }
        });

    }


    public class BatteryChangeReceiver extends BroadcastReceiver {

        @Override public void onReceive(Context context, Intent intent) {
            // here I get all battery status.
            BatteryManager batteryManager = (BatteryManager) getApplicationContext().getSystemService(Context.BATTERY_SERVICE);
            int v = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW);
            Toast.makeText(ChargingNotificationLayout.this, String.valueOf(v), Toast.LENGTH_SHORT).show();
        }
    }

    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(final Context c, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            //int voltage = intent.getIntExtra("voltage", 0);
            //int temperature = intent.getIntExtra("temperature", 0);
            //int health = intent.getIntExtra("health", 0);
            int plugged = intent.getIntExtra("plugged", -1);


            String ChargingBy=getPlugTypeString(plugged);
            if (!ChargingBy.equals("Unknown")){
                if (ChargingBy.equals("Power Adapter")){
                    chargingStatus.setText("CHARGER CONNECTED");
                }if (ChargingBy.equals("USB")){
                    chargingStatus.setText("USB CONNECTED");
                }
            }

            if (ChargingBy.equals("Unknown")){
                finish();
            }
            BatteryManager batteryManager = (BatteryManager) getApplicationContext().getSystemService(Context.BATTERY_SERVICE);
            long v = 0;
            float v1 = 0;
            try {

                if (Build.VERSION.SDK_INT <=22) {
                    if (ChargingBy.equals("Power Adapter")){
                        v1= (float) abs(1.5*1000);
                    }
                    if (ChargingBy.equals("USB")){
                        v1= (float) abs(500);
                    }
                } else {
                    v = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW);
                    v1=abs(v/1000);
                }
            }catch (Exception e) {}



            String TotalCapacity=getBatteryCapacity();
            int remainCapa=setRemainingCapacity(level,TotalCapacity);
            double capacity=Integer.parseInt(TotalCapacity);
            int forChargCapa= (int) (capacity-remainCapa);

            float result= abs(forChargCapa/v1);

            double result1= abs(capacity/v1);
            double i=result1/capacity;
            result = (float) (Math.floor(result * 100) / 100);
            float r = (float) (Math.floor(i*forChargCapa * 100) / 100);


            try {
                levelTxt.setText(String.valueOf(level) + "%");
            }catch (Exception e){}

            try{
                String finStr = String.valueOf(r);
                StringTokenizer tokens = new StringTokenizer(finStr, ".");
                final String hour = tokens.nextToken();
                String str_min = tokens.nextToken();

                final int min = (Integer.parseInt(str_min) * 60) / 100;

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        txt.setText(hour+"H "+String.valueOf(min)+"MIN");
                    }
                }, 20000);
            }catch (Exception e){}
        }
    };



    public void Difference(String strChargingConnectTime, String strChargingDisconnetTime){
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        Date dateOne = null;
        try {
            dateOne = df.parse(strChargingDisconnetTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }catch (Exception e1){}
        Date dateTwo = null;
        try {
            dateTwo = df.parse(strChargingConnectTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }catch (Exception e1){}
        try {
            long timeDiff = Math.abs(dateOne.getTime() - dateTwo.getTime());
            int Hours = (int) (timeDiff/(1000 * 60 * 60));
            int Mins = (int) (timeDiff/(1000*60)) % 60;
            long Secs = (int) (timeDiff / 1000) % 60;

            String diff = Hours + ":" + Mins + ":" + Secs;
            String diff1 = Hours + " H " + Mins+" MIN";
            String diff2 = Mins+" MIN";
            if (Hours==0){
                lastTimeDuration.setText(diff2);
            }else if(Mins!=0){
                lastTimeDuration.setText(diff1);
            }else  lastTimeDuration.setText("Not Available");
        }catch (Exception e){}
    }


    public static String getCurrentTime() {
        //date output format
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }

    private String getPlugTypeString(int plugged) {
        String plugType = "Unknown";

        switch (plugged) {
            case BatteryManager.BATTERY_PLUGGED_AC:
                plugType = "Power Adapter";
                break;
            case BatteryManager.BATTERY_PLUGGED_USB:
                plugType = "USB";
                break;
        }

        return plugType;
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        try{
            if(mBatInfoReceiver!=null)
                unregisterReceiver(mBatInfoReceiver);
        }catch(Exception e) {}
        super.onDestroy();
    }

    public String getBatteryCapacity() {
        Object mPowerProfile_ = null;
        String totalCapacity = null;

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
        return k;
    }




    /*private void placeNativeAds() {
            InMobiNative nativeAd = new InMobiNative(ChargingNotificationLayout.this, 1495838983200L, new InMobiNative.NativeAdListener() {
                @Override
                public void onAdLoadSucceeded(InMobiNative inMobiNative) {
                    Log.d(TAG, "onAdLoadSuccess" + inMobiNative.toString());
                    try {
                        JSONObject content = new JSONObject((String) inMobiNative.getAdContent());
                        Log.d(TAG, "onAdLoadSucceeded" + content.toString());
                        Toast.makeText(ChargingNotificationLayout.this, content.toString(), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        Log.d(TAG, "Error while parsing JSON", e);
                    }
                }

                @Override
                public void onAdLoadFailed(InMobiNative inMobiNative, InMobiAdRequestStatus inMobiAdRequestStatus) {
                    Log.d(TAG, "onAdLoadFailed" + inMobiAdRequestStatus.getMessage());
                }

                @Override
                public void onAdDismissed(InMobiNative inMobiNative) {
                    Log.e(TAG, "onAdDismissed" + inMobiNative.getAdContent());
                }

                @Override
                public void onAdDisplayed(InMobiNative inMobiNative) {
                    Log.e(TAG, "onAdDisplayed" + inMobiNative.getAdContent());
                }

                @Override
                public void onUserLeftApplication(InMobiNative inMobiNative) {
                    Log.e(TAG, "onUserLeftApplication. " + inMobiNative.getAdContent());
                }
            });
            nativeAd.load();
//            mNativeAds[i] = nativeAd;

    }*/
}
