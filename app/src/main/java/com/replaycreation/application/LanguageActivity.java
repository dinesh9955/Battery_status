package com.replaycreation.application;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class LanguageActivity extends AppCompatActivity {

    SharedPreferences sharedPreference;
    String[] categories = new String[]{"عربى","中文","ČEŠTINA","DANSK","NEDERLANDS","ENGLISH","ESPERANTO","FRANÇAIS","DEUTSCHE","ΕΛΛΗΝΙΚΑ","हिंदी","MAGYAR","BAHASA INDONESIA","ITALIANO", "KURDÎ",
            "POLSKIE","PORTUGUÊS","РУССКИЙ","ESPAÑOL","SVENSKA","TÜRK"};
    ListView lv;
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SetColorTheme();
        setContentView(R.layout.activity_language);

        sharedPreference = getSharedPreferences("SharedPreference", 0);
        String Status = sharedPreference.getString("status", "");
        if (Status.equals("true")){
            String str_title = sharedPreference.getString("language", "");
            getSupportActionBar().setTitle(str_title);
        }
        String str_pos=sharedPreference.getString("position", "");
        try {
            pos=Integer.parseInt(str_pos);
        }catch (Exception e){
            pos=5;
        }


        lv=(ListView)findViewById(R.id.listView);
        TextView done=(TextView)findViewById(R.id.TxtChangeLanguage);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ArrayAdapter<String> arrayadapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice, categories){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                View view = super.getView(position, convertView, parent);
                TextView textview = (TextView) view.findViewById(android.R.id.text1);
                textview.setTextSize(14);
                return view;
            }
        };
        lv.setAdapter(arrayadapter);
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lv.setSelection(2);
        lv.setItemChecked(pos, true);



        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
                CheckedTextView v = ((CheckedTextView) view);
                v.setChecked(true);
                SharedPreferences.Editor ee=sharedPreference.edit();
                ee.putString("position",String.valueOf(position));
                ee.commit();

                if (position == 0) {ARABIC();}
                if (position == 1) {CHINEESE();}
                if (position == 2) {CZECH();}
                if (position == 3) {DANISH();}
                if (position == 4) {DUTCH();}
                if (position == 5) {ENGLISH();}
                if (position == 6) {ESPERANTO();}
                if (position == 7) {FRENCH();}
                if (position == 8) {GERMAN();}
                if (position == 9) {GREEK();}
                if (position == 10) {HINDI();}
                if (position == 11) {HUNGARIAN();}
                if (position == 12) {INDONESIAN();}
                if (position == 13) {ITALIAN();}
                if (position == 14) {KURDISH();}
                if (position == 15) {POLISH();}
                if (position == 16) {PORTUGUESE();}
                if (position == 17) {RUSSIAN();}
                if (position == 18) {SPANISH();}
                if (position == 19) {SWEDISH();}
                if (position == 20) {TURKISH();}

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

    public void ENGLISH(){
        SharedPreferences.Editor ee=sharedPreference.edit();

        ee.putString("status","true");
        ee.putString("fullBatteryAlarm","Full Battery Alarm");

        ee.putString("health","Battery Health");
        ee.putString("volt","Voltage");
        ee.putString("temp","Temperature");
        ee.putString("brightness","Screen Brightness");
        ee.putString("flashLight","Torch");
        ee.putString("theftalarm_caps","THEFT ALARM");
        ee.putString("theftalarm","Theft Alarm");

        ee.putString("stop","STOP");
        ee.putString("start","START");
        ee.putString("settings","SETTINGS");
        ee.putString("navSettings","Settings");


        ee.putString("batteryCapacity","Battery Capacity :");
        ee.putString("remainingBatteryCapacity","Remaining Capacity :");
        ee.putString("batteryHealth","Battery Health :");
        ee.putString("batteryLevel","Battery Level :");
        ee.putString("plugged","Plugged :");
        ee.putString("technology","Technology :");
        ee.putString("chargingStatus","Charging Status :");
        ee.putString("voltage","Voltage :");
        ee.putString("temperature","Temperature :");
        ee.putString("statistics","STATISTICS");

        ee.putString("LowBatteryReminder","Low Battery Reminder");
        ee.putString("batterystatistics","Battery Statistics");
        ee.putString("language","Language");
        ee.putString("alarmringtone","Alarm Ringtone");
        ee.putString("chooseColorTheme","Choose Color Theme");
        ee.putString("share","Share");
        ee.putString("contact","Contact us");
        ee.putString("RateUs","Rate Us");

        ee.putString("RingAlarmOnSilentMode","Ring Alarm On Silent Mode");
        ee.putString("AutoStartupOnReboot","Auto Startup On Reboot");
        ee.putString("ActivateNotificationPanel","Activate Notification Panel");
        ee.putString("Sound","Sound Volume");
        ee.putString("ChangeTheftPasscode","Change Theft Passcode");
        ee.putString("AutoEnableTheftAlarm","Auto Enable Theft Alarm");

        ee.putString("FullBatteryLevelTxt","Full Battery Alarm Level");
        ee.putString("LowBatteryLevelTxt","Notification At Custom Battery Level");
        ee.putString("BatteryTemperatureWarninglevel","Set Battery Temperature Warning Level");

        ee.putString("ActivateVibrationMode","Activate Vibration Mode");
        ee.putString("ChangeTempUnit","Change Temp Unit");
        ee.putString("BatteryMonitor","Battery Monitor");



        ee.putString("HowToUse","How To Use");
        ee.putString("TheftA","To use theft alarm, connect the mobile charger and click on ‘Theft Alarm’.");
        ee.putString("TheftB","You will then be asked to enter a password. This is a one-time activity.");
        ee.putString("TheftC","After successfully configuring the password, the theft alarm will be activated");
        ee.putString("TheftD","Now if the phone is unplugged, an alarm will sound and will only stop by inserting the correct password.");
        ee.putString("FullBatteryAlarmA","Once the application is installed, full battery alarm is activated.");
        ee.putString("FullBatteryAlarmB","While charging, once the battery is fully charged at 100%, the app will sound an alarm which can be stopped by either clicking the dismiss alarm button or by unplugging the charger.");


        //DialogeTile
        ee.putString("volume","Volume");
        ee.putString("Theme","Theme");
        ee.putString("LowBatteryReminder","Low Battery Reminder");
        ee.putString("SelectFullBatteryLevel","Select Full Battery Level");
        ee.putString("SelectCustomBatteryLevel","Select Custom Battery Level");
        ee.putString("SelectTemperatureWarningLevel","Select Temperature Warning Level");
        ee.putString("ChangeTemperatureUnit","Change Temperature Unit");

        ee.putString("ActivateVibrationMode","Activate Vibration Mode");
        ee.putString("ChangeTempUnit","Change Temperature Unit");
        ee.putString("BatteryMonitor","Battery Monitor");

        ee.putString("Celcius","Celsius");
        ee.putString("fahrenheit","Fahrenheit");
        ee.putString("cancel","Cancel");

        ee.commit();
    }
    public void HINDI(){
        SharedPreferences.Editor ee=sharedPreference.edit();

        ee.putString("status","true");
        ee.putString("fullBatteryAlarm","पूर्ण बैटरी अलार्म");

        ee.putString("health","बैटरी स्वास्थ्य");
        ee.putString("volt","वोल्टेज");
        ee.putString("temp","तापमान");
        ee.putString("brightness","स्क्रीन चमकना");
        //ee.putString("flashLight","फ़्लैश लाइट");
        ee.putString("flashLight","प्रकाश");
        ee.putString("theftalarm","चोरी अलार्म");
        ee.putString("theftalarm_caps","चोरी अलार्म");

        ee.putString("batteryCapacity","बैटरी क्षमता :");
        ee.putString("remainingBatteryCapacity","शेष क्षमता :");
        ee.putString("batteryHealth","बैटरी स्वास्थ्य :");
        ee.putString("batteryLevel","बैटरी का स्तर :");
        ee.putString("plugged","प्लग-इन :");
        ee.putString("technology","प्रौद्योगिकी :");
        ee.putString("chargingStatus","चार्जिंग स्थिति :");
        ee.putString("voltage","वोल्टेज :");
        ee.putString("temperature","तापमान :");
        ee.putString("statistics","सांख्यिकी");

        ee.putString("stop","रद्द करना");
        ee.putString("start","प्रारंभ");
        ee.putString("volume","वॉल्यूम");
        ee.putString("settings","सेटिंग्स");
        ee.putString("navSettings","सेटिंग्स");

        ee.putString("LowBatteryReminder","कम बैटरी रिमाइंडर");
        ee.putString("batterystatistics","बैटरी आंकड़े");
        ee.putString("language","भाषा");
        ee.putString("alarmringtone","अलार्म रिंगटोन");
        ee.putString("chooseColorTheme","रंग विषय चुनें");
        ee.putString("share","साझा करें");
        ee.putString("contact","संपर्क करें");
        ee.putString("RateUs","हमें रेटिंग दें");

        ee.putString("RingAlarmOnSilentMode","मौन मोड पर रिंग अलार्म");
        ee.putString("AutoStartupOnReboot","रिबूट पर ऑटो स्टार्टअप");
        ee.putString("ActivateNotificationPanel","सूचना पैनल सक्रिय करेंं");
        ee.putString("Sound","ध्वनि वॉल्यूम");
        ee.putString("ChangeTheftPasscode","चोरी पासकोड बदलें");
        ee.putString("AutoEnableTheftAlarm","ऑटो चोरी अलार्म सक्षम करें");
        ee.putString("FullBatteryLevelTxt","पूर्ण बैटरी अलार्म स्तर");
        ee.putString("LowBatteryLevelTxt","कस्टम बैटरी स्तर पर अधिसूचना");
        ee.putString("BatteryTemperatureWarninglevel","बैटरी तापमान चेतावनी स्तर सेट करें");

        ee.putString("ActivateVibrationMode","कंपन मोड सक्रिय करें");
        ee.putString("ChangeTempUnit","तापमान यूनिट बदलें");
        ee.putString("BatteryMonitor","बैटरी मॉनिटर");

        //HOW TO USE
        ee.putString("HowToUse","कैसे इस्तेमाल करे");
        ee.putString("TheftA","चोरी अलार्म का उपयोग करने के लिए, मोबाइल चार्जर से कनेक्ट करें और 'चोरी अलार्म' पर क्लिक करें");
        ee.putString("TheftB","फिर आपको एक पासवर्ड दर्ज करने के लिए कहा जाएगा। यह एक बार की गतिविधि है");
        ee.putString("TheftC","पासवर्ड को सफलतापूर्वक कॉन्फ़िगर करने के बाद, चोरी अलार्म सक्रिय हो जाएगा");
        ee.putString("TheftD","अब अगर फोन अनप्लग हो गया है, तो अलार्म ध्वनि जाएगा और सही पासवर्ड डालने से केवल रोक दिया जाएगा");
        ee.putString("FullBatteryAlarmA","एक बार आवेदन इंस्टॉल हो जाने पर, पूर्ण बैटरी अलार्म सक्रिय हो जाता है।");
        ee.putString("FullBatteryAlarmB","चार्ज करते समय, बैटरी पूरी तरह से 100% पर चार्ज हो जाने पर, ऐप एक अलार्म को ध्वनित करेगा जिसे या तो अलार्म बटन को खारिज करने या चार्जर को अनप्लग करके क्लिक किया जा सकता है");



        ee.putString("Theme","थीम");
        ee.putString("SelectFullBatteryLevel","पूर्ण बैटरी स्तर चुनें");
        ee.putString("SelectCustomBatteryLevel","कस्टम बैटरी स्तर चुनें");
        ee.putString("SelectTemperatureWarningLevel","तापमान चेतावनी स्तर चुनें");

        ee.putString("ChangeTemperatureUnit","तापमान यूनिट बदलें");
        ee.putString("Celcius","सेल्सियस");
        ee.putString("fahrenheit","फ़ारेनहाइट");
        ee.putString("cancel","रद्द करें");


        //Ringtone
        ee.putString("Ringtone","रिंगटोन");
        ee.putString("DefaultRingtone","बकाया घंटी");
        ee.putString("SetCustomRingtone","कस्टम रिंगटोन सेट करें");

        //Theft passcode..
        ee.putString("SetTheftPasscode","चोरी पासकोड सेट करें");
        ee.putString("PasscodeArea","पासकोड एरिया");
        ee.putString("Set","सेट");
        ee.putString("EnterPasscode","पासकोड दर्ज करें");
        ee.putString("ConfirmTheftPasscode","चोरी पासकोड की पुष्टि करें");
        ee.putString("Confirm","पुष्टि करें");
        ee.putString("EnterOldPasscode","पुराने पासकोड दर्ज करें");
        ee.putString("OldPassword","पुराना पासवर्ड");
        ee.putString("Change","परिवर्तन");



        ee.commit();

    }
    public void CHINEESE(){
         SharedPreferences.Editor ee=sharedPreference.edit();

         ee.putString("status","true");
         ee.putString("fullBatteryAlarm","全电池报警");

         ee.putString("health","电池健康");
         ee.putString("volt","电压");
         ee.putString("temp","温度");
         ee.putString("brightness","屏幕亮度");
         //ee.putString("flashLight","手电筒");
         ee.putString("flashLight","火炬");
         ee.putString("theftalarm","盗窃报警");
         ee.putString("theftalarm_caps","盗窃报警");

         ee.putString("stop","停止");
         ee.putString("start","开始");
         ee.putString("volume","卷");
         ee.putString("settings","设置");


         //STATISTICS
         ee.putString("statistics","统计");
         ee.putString("batteryCapacity","电池容量 :");
         ee.putString("remainingBatteryCapacity","剩余能力 :");
         ee.putString("batteryHealth","电池健康 :");
         ee.putString("batteryLevel","电池电量 :");
         ee.putString("plugged","堵塞 :");
         ee.putString("technology","技术 :");
         ee.putString("chargingStatus","充电状态 :");
         ee.putString("voltage","电压 :");
         ee.putString("temperature","温度 :");


         //DRAWER
         ee.putString("LowBatteryReminder","低电量提醒");
         ee.putString("batterystatistics","电池统计");
         ee.putString("language","语言");
         ee.putString("alarmringtone","报警铃声");
         ee.putString("navSettings","设置");
         ee.putString("chooseColorTheme","选择颜色主题");
         ee.putString("share","分享");
         ee.putString("contact","联系我们");
         ee.putString("RateUs","评价我们");

         //SETTINGS
         ee.putString("RingAlarmOnSilentMode","环聊在静音模式下报警");
         ee.putString("AutoStartupOnReboot","重新启动时自动启动");
         ee.putString("ActivateNotificationPanel","激活通知面板");
         ee.putString("Sound","音量");
         ee.putString("ChangeTheftPasscode","更改盗窃密码");
         ee.putString("AutoEnableTheftAlarm","自动启用盗窃报警");
         ee.putString("FullBatteryLevelTxt","全电池报警电平");
         ee.putString("LowBatteryLevelTxt","通知自定义电池电量");
         ee.putString("BatteryTemperatureWarninglevel","设置电池温度警告级别");

         ee.putString("PowerAdapterConnected","电源适配器连接");

         //HOW TO USE
         ee.putString("HowToUse","如何使用");
         ee.putString("TheftA","要使用盗窃警报，请连接手机充电器，然后点击“盗窃警报”");
         ee.putString("TheftB","然后，您将被要求输入密码。 这是一次性的活动");
         ee.putString("TheftC","成功配置密码后，盗用报警器将被激活");
         ee.putString("TheftD","如果手机已拔下电源，会发出警报，只能插入正确的密码才能停止");
         ee.putString("FullBatteryAlarmA","安装应用程序后，全电池报警被激活。");
         ee.putString("FullBatteryAlarmB","充电时，一旦电池充满电100％，应用程序将发出警报，可以通过点击关闭报警按钮或拔掉充电器来停止");

        ee.putString("ActivateVibrationMode","激活振动模式");
        ee.putString("ChangeTempUnit","更改温度单位");
        ee.putString("BatteryMonitor","电池监视器");


        ee.putString("Theme","题材");
        ee.putString("SelectFullBatteryLevel","选择全电池电量");
        ee.putString("SelectCustomBatteryLevel","选择自定义电池电量");
        ee.putString("SelectTemperatureWarningLevel","选择温度警告级别");
        ee.putString("ChangeTemperatureUnit","更改温度单位");

        ee.putString("ChangeTemperatureUnit","更改温度单位");
        ee.putString("Celcius","摄氏");
        ee.putString("fahrenheit","华氏");
        ee.putString("cancel","取消");


        //Ringtone
        ee.putString("Ringtone","铃声");
        ee.putString("DefaultRingtone","默认铃声");
        ee.putString("SetCustomRingtone","设置自定义铃声");

        //Theft passcode..
        ee.putString("SetTheftPasscode","设置盗窃密码");
        ee.putString("PasscodeArea","密码区");
        ee.putString("Set","组");
        ee.putString("EnterPasscode","输入密码");
        ee.putString("ConfirmTheftPasscode","确认盗窃密码");
        ee.putString("Confirm","确认");
        ee.putString("EnterOldPasscode","输入旧密码");
        ee.putString("OldPassword","旧密码");
        ee.putString("Change","更改");

         ee.commit();
    }
    public void ARABIC(){
        SharedPreferences.Editor ee=sharedPreference.edit();

        ee.putString("status","true");
        ee.putString("fullBatteryAlarm","كامل إنذار البطارية");

        ee.putString("health","صحة البطارية");
        ee.putString("volt","الجهد االكهربى");
        ee.putString("temp","درجة الحرارة");
        ee.putString("brightness","سطوع الشاشة");
        //ee.putString("flashLight","مصباح يدوي");
        ee.putString("flashLight","شعلة");
        ee.putString("theftalarm","إنذار السرقة");
        ee.putString("theftalarm_caps","إنذار السرقة");

        ee.putString("batteryCapacity",": قدرة البطارية");
        ee.putString("remainingBatteryCapacity",": القدرة المتبقية");
        ee.putString("batteryHealth",": صحة البطارية");
        ee.putString("batteryLevel",": مستوى البطارية");
        ee.putString("plugged",": مسدود");
        ee.putString("technology",": تقنية");
        ee.putString("chargingStatus",": حالة الشحن");
        ee.putString("voltage",": الجهد االكهربى");
        ee.putString("temperature",": درجة الحرارة");
        ee.putString("statistics","الإحصاء");

        ee.putString("stop","توقف");
        ee.putString("start","بداية");
        ee.putString("settings","إعدادات");



        ee.putString("LowBatteryReminder","انخفاض البطارية تذكير");
        ee.putString("batterystatistics","إحصاءات البطارية");
        ee.putString("language","لغة");
        ee.putString("alarmringtone","نغمة التنبيه");
        ee.putString("navSettings","إعدادات");
        ee.putString("chooseColorTheme","اختيار موضوع اللون");
        ee.putString("share","شارك");
        ee.putString("contact","اتصل بنا");
        ee.putString("RateUs","قيمنا");

        ee.putString("RingAlarmOnSilentMode","حلقة إنذار على الوضع الصامت");
        ee.putString("AutoStartupOnReboot","بدء التشغيل التلقائي على إعادة التشغيل");
        ee.putString("ActivateNotificationPanel","تنشيط لوحة الإشعارات");
        ee.putString("Sound","حجم الصوت");
        ee.putString("ChangeTheftPasscode","تغيير سرقة رمز المرور");
        ee.putString("AutoEnableTheftAlarm","السيارات تمكين إنذار سرقة");

        ee.putString("FullBatteryLevelTxt","كامل مستوى إنذار البطارية");
        ee.putString("LowBatteryLevelTxt","إعلام في مستوى البطارية مخصص");
        ee.putString("BatteryTemperatureWarninglevel","تعيين مستوى درجة حرارة البطارية تحذير");

        ee.putString("PowerAdapterConnected","محول الطاقة متصل");

        //HOW TO USE
        ee.putString("HowToUse","كيف تستعمل");
        ee.putString("TheftA"," لاستخدام إنذار السرقة، قم بتوصيل شاحن المحمول وانقر على إنذار سرقة");
        ee.putString("TheftB","سوف يطلب منك بعد ذلك إدخال كلمة مرور. هذا نشاط لمرة واحدة");
        ee.putString("TheftC"," بعد تكوين كلمة المرور بنجاح، سيتم تنشيط إنذار السرقة");
        ee.putString("TheftD","الآن إذا كان الهاتف غير موصول، سوف صوت التنبيه وسوف تتوقف فقط عن طريق إدخال كلمة المرور الصحيحة");
        ee.putString("FullBatteryAlarmA"," بمجرد تثبيت التطبيق، يتم تنشيط التنبيه بطارية كاملة.");
        ee.putString("FullBatteryAlarmB","أثناء الشحن، وبمجرد أن البطارية مشحونة بالكامل في 100٪، فإن التطبيق يبدو ناقوس الخطر التي يمكن أن تتوقف عن طريق النقر على زر رفض التنبيه أو عن طريق فصل الشاحن");


        ee.putString("ActivateVibrationMode","تنشيط وضع الاهتزاز");
        ee.putString("ChangeTempUnit","تغيير وحدة درجة الحرارة");
        ee.putString("BatteryMonitor","مراقبة البطارية");

        ee.putString("volume","الصوت");
        ee.putString("Theme","المقدمة");
        ee.putString("SelectFullBatteryLevel","حدد مستوى البطارية الكامل");
        ee.putString("SelectCustomBatteryLevel","حدد مستوى البطارية المخصص");
        ee.putString("SelectTemperatureWarningLevel","حدد مستوى تحذير درجة الحرارة");

        ee.putString("ChangeTemperatureUnit","تغيير وحدة درجة الحرارة");
        ee.putString("Celcius","درجة مئوية");
        ee.putString("fahrenheit","فهرنهايت");
        ee.putString("cancel","إلغاء");


        //Ringtone
        ee.putString("Ringtone","نغمة");
        ee.putString("DefaultRingtone","نغمات الافتراضي");
        ee.putString("SetCustomRingtone","تعيين نغمة رنين مخصصة");

        //Theft passcode..
        ee.putString("SetTheftPasscode","تعيين سرقة كلمة السر");
        ee.putString("PasscodeArea","كلمة المرور المنطقة");
        ee.putString("Set","جلس");
        ee.putString("EnterPasscode","أدخل كلمة المرور");
        ee.putString("ConfirmTheftPasscode","تأكيد سرقة كلمة المرور");
        ee.putString("Confirm","تؤكد");
        ee.putString("EnterOldPasscode","أدخل كلمة المرور القديمة");
        ee.putString("OldPassword","كلمة المرور القديمة");
        ee.putString("Change","يتغيرون");


        ee.commit();
    }
    public void CZECH(){
        SharedPreferences.Editor ee=sharedPreference.edit();

        ee.putString("status","true");
        ee.putString("fullBatteryAlarm","Alarm plné baterie");

        ee.putString("health","Zdraví baterie");
        ee.putString("volt","Napětí");
        ee.putString("temp","Teplota");
        ee.putString("brightness","Jas obrazovky");
        //ee.putString("flashLight","Svítilna");
        ee.putString("flashLight","Pochodeň");
        ee.putString("theftalarm","Alarm krádeže");
        ee.putString("theftalarm_caps","Alarm krádeže");

        ee.putString("stop","Stop");
        ee.putString("start","Start");
        ee.putString("settings","Nastavení");


        //STATISTICS
        ee.putString("statistics","Statistika");
        ee.putString("batteryCapacity","Kapacita baterie :");
        ee.putString("remainingBatteryCapacity","Zbývající kapacita :");
        ee.putString("batteryHealth","Zdraví baterie :");
        ee.putString("batteryLevel","Úroveň baterie :");
        ee.putString("plugged","Připojen :");
        ee.putString("technology","Technika :");
        ee.putString("chargingStatus","Stav nabíjení :");
        ee.putString("voltage","Napětí :");
        ee.putString("temperature","Teplota :");


        //DRAWER
        ee.putString("LowBatteryReminder","Připomenutí nízké baterie");
        ee.putString("batterystatistics","Statistika baterií");
        ee.putString("language","Jazyk");
        ee.putString("alarmringtone","Vyzvánění vyzvánění");
        ee.putString("navSettings","Nastavení");
        ee.putString("chooseColorTheme","Vyberte Téma barvy");
        ee.putString("share","Podíl");
        ee.putString("contact","Kontaktujte nás");
        ee.putString("RateUs","Ohodnoťte nás");

        //SETTINGS
        ee.putString("RingAlarmOnSilentMode","Alarm zvonění v tichém režimu");
        ee.putString("AutoStartupOnReboot","Automatické spuštění při restartování");
        ee.putString("ActivateNotificationPanel","Aktivujte panel oznámení");
        ee.putString("Sound","Hlasitost zvuku");
        ee.putString("ChangeTheftPasscode","Změnit kód krádeže krádeže");
        ee.putString("AutoEnableTheftAlarm","Automaticky povolit poplach proti krádeži");
        ee.putString("FullBatteryLevelTxt","Úroveň alarmu plné baterie");
        ee.putString("LowBatteryLevelTxt","Upozornění na vlastní úrovni baterie");
        ee.putString("BatteryTemperatureWarninglevel","Nastavte úroveň varování teploty baterie");
        ee.putString("PowerAdapterConnected","Napájecí adaptér je připojen");

        //HOW TO USE
        ee.putString("HowToUse","Jak používat");
        ee.putString("TheftA"," Chcete-li použít krádežní poplach, připojte mobilní nabíječku a klikněte na 'Theft Alarm'.");
        ee.putString("TheftB","Poté budete požádáni o zadání hesla. Jedná se o jednorázovou činnost.");
        ee.putString("TheftC","Po úspěšné konfiguraci hesla se aktivuje poplach.");
        ee.putString("TheftD","Pokud je telefon odpojen od sítě, zazní budík a zastaví se pouze vložením správného hesla.");
        ee.putString("FullBatteryAlarmA","Po instalaci aplikace je aktivován alarm plné baterie.");
        ee.putString("FullBatteryAlarmB","Během nabíjení se po úplném nabití baterie na 100% aplikace ozve poplach, který lze zastavit buď klepnutím na tlačítko Vypnout poplach nebo odpojením nabíječky.");

        ee.putString("ActivateVibrationMode","Aktivujte režim vibrací");
        ee.putString("ChangeTempUnit","Změna jednotky teploty");
        ee.putString("BatteryMonitor","Monitor baterie");

        ee.putString("Theme","Téma");
        ee.putString("volume","Hlasitost");
        ee.putString("SelectFullBatteryLevel","Zvolte Úplnou úroveň baterie");
        ee.putString("SelectCustomBatteryLevel","Vyberte možnost Vlastní úroveň baterie");
        ee.putString("SelectTemperatureWarningLevel","Zvolte úroveň varování teploty");

        ee.putString("ChangeTemperatureUnit","Změna jednotky teploty");
        ee.putString("Celcius","Celsia");
        ee.putString("fahrenheit","Fahrenheita");
        ee.putString("cancel","zrušení");


        //Ringtone
        ee.putString("Ringtone","Vyzváněcí tón");
        ee.putString("DefaultRingtone","Výchozí vyzvánění");
        ee.putString("SetCustomRingtone","Nastavte vlastní vyzváněcí tón");

        //Theft passcode..
        ee.putString("SetTheftPasscode","Nastavte heslo krádeže");
        ee.putString("PasscodeArea","Oblast hesla");
        ee.putString("Set","Soubor");
        ee.putString("EnterPasscode","zadejte heslo");
        ee.putString("ConfirmTheftPasscode","Potvrďte heslo krádeže");
        ee.putString("Confirm","Potvrdit");
        ee.putString("EnterOldPasscode","Zadejte původní heslo");
        ee.putString("OldPassword","staré heslo");
        ee.putString("Change","Změna");


        ee.commit();
    }
    public void DANISH(){
        SharedPreferences.Editor ee=sharedPreference.edit();

        ee.putString("status","true");
        ee.putString("fullBatteryAlarm","Fuld batteri alarm");

        ee.putString("health","Batteri sundhed");
        ee.putString("volt","Spænding");
        ee.putString("temp","Temperatur");
        ee.putString("brightness","Skærmlysstyrke");
        ee.putString("flashLight","Fakkel");
        ee.putString("theftalarm","Tyveri alarm");
        ee.putString("theftalarm_caps","Tyveri alarm");

        ee.putString("stop","Hold op");
        ee.putString("start","Start");
        ee.putString("settings","Indstillinger");


        //STATISTICS
        ee.putString("statistics","Statistik");
        ee.putString("batteryCapacity","Batterikapacitet :");
        ee.putString("remainingBatteryCapacity","Resterende kapacitet :");
        ee.putString("batteryHealth","Batteri sundhed :");
        ee.putString("batteryLevel","Batteri niveau :");
        ee.putString("plugged","Plugged :");
        ee.putString("technology","Teknologi :");
        ee.putString("chargingStatus","Opladningsstatus :");
        ee.putString("voltage","Spænding :");
        ee.putString("temperature","Temperatur :");

        //DRAWER
        ee.putString("LowBatteryReminder","Lavt batteri påmindelse");
        ee.putString("batterystatistics","Batteristatistik");
        ee.putString("language","Sprog");
        ee.putString("alarmringtone","Alarm ringetone");
        ee.putString("navSettings","Indstillinger");
        ee.putString("chooseColorTheme","Vælg farve tema");
        ee.putString("share","Del");
        ee.putString("contact","Kontakt os");
        ee.putString("RateUs","Bedøm os");

        //SETTINGS
        ee.putString("RingAlarmOnSilentMode","Ringalarm i lydløs tilstand");
        ee.putString("AutoStartupOnReboot","Automatisk opstart ved genstart");
        ee.putString("ActivateNotificationPanel","Aktivér meddelelsespanel");
        ee.putString("Sound","Lydvolumen");
        ee.putString("ChangeTheftPasscode","Skift tyveri kode");
        ee.putString("AutoEnableTheftAlarm","Auto aktiver tyverialarm");
        ee.putString("FullBatteryLevelTxt","Fuld batteri alarm niveau");
        ee.putString("LowBatteryLevelTxt","Meddelelse Til Tilpasset Batteriniveau");
        ee.putString("BatteryTemperatureWarninglevel","Indstil batteritemperaturvarningsniveau");

        ee.putString("PowerAdapterConnected","Netadapter tilsluttet");

        //HOW TO USE
        ee.putString("HowToUse","Sådan bruges");
        ee.putString("TheftA","For at bruge tyverialarm skal du tilslutte mobil opladeren og klikke på 'Tyverialarm'");
        ee.putString("TheftB","Du bliver derefter bedt om at indtaste et kodeord. Dette er en engangsaktivitet");
        ee.putString("TheftC","Når du har konfigureret adgangskoden, aktiveres tyverialarm");
        ee.putString("TheftD","Nu, hvis telefonen er frakoblet, lyder en alarm og stopper kun ved at indsætte den korrekte adgangskode");
        ee.putString("FullBatteryAlarmA","Når applikationen er installeret, aktiveres fuld batterilarm.");
        ee.putString("FullBatteryAlarmB","Under opladning, når batteriet er fuldt opladet til 100%, lader appen en alarm, som kan stoppes ved enten at klikke på afvis alarmknappen eller ved at tage opladeren ud af stikkontakten");


        ee.putString("ActivateVibrationMode","Aktiver vibrationsfunktion");
        ee.putString("ChangeTempUnit","Skift temperatur Enhed");
        ee.putString("BatteryMonitor","Batteri Monitor");

        ee.putString("Theme","Tema");
        ee.putString("volume","Bind");
        ee.putString("SelectFullBatteryLevel","Vælg Fuld Batteri Niveau");
        ee.putString("SelectCustomBatteryLevel","Vælg Brugerdefineret Batteri Niveau");
        ee.putString("SelectTemperatureWarningLevel","Vælg Temperatur Advarsels Niveau");

        ee.putString("ChangeTemperatureUnit","Skift Temperatur Enhed");
        ee.putString("Celcius","Celsius");
        ee.putString("fahrenheit","fahrenheit");
        ee.putString("cancel","Afbestille");

        //Ringtone
        ee.putString("Ringtone","Ringetone");
        ee.putString("DefaultRingtone","Standard ringetoner");
        ee.putString("SetCustomRingtone","Indstil en brugerdefineret ringetone");

        //Theft passcode..
        ee.putString("SetTheftPasscode","Indstil tyveriadgangskode");
        ee.putString("PasscodeArea","Adgangskode område");
        ee.putString("Set","Sæt");
        ee.putString("EnterPasscode","Indtast adgangskode");
        ee.putString("ConfirmTheftPasscode","Bekræft tyveriadgangskode");
        ee.putString("Confirm","Bekræfte");
        ee.putString("EnterOldPasscode","Indtast det gamle kodeord");
        ee.putString("OldPassword","gammelt kodeord");
        ee.putString("Change","Lave om");

        ee.commit();
    }
    public void DUTCH(){
        SharedPreferences.Editor ee=sharedPreference.edit();

        ee.putString("status","true");
        ee.putString("fullBatteryAlarm","Volledige Batterij Alarm");

        ee.putString("health","Batterij Gezondheid");
        ee.putString("volt","Spanning");
        ee.putString("temp","Temperatuur");
        ee.putString("brightness","Scherm Helderheid");
        //ee.putString("flashLight","Zaklamp");
        ee.putString("flashLight","Toorts");
        ee.putString("theftalarm","Diefstal Alarm");
        ee.putString("theftalarm_caps","Diefstal Alarm");

        ee.putString("stop","Hou op");
        ee.putString("start","Begin");
        ee.putString("settings","Instellingen");


        //STATISTICS
        ee.putString("statistics","Statistieken");
        ee.putString("batteryCapacity","Batterij Capaciteit :");
        ee.putString("remainingBatteryCapacity","Resterende Capaciteit :");
        ee.putString("batteryHealth","Batterij Gezondheid :");
        ee.putString("batteryLevel","Batterij Niveau :");
        ee.putString("plugged","Aangesloten :");
        ee.putString("technology","Technologie :");
        ee.putString("chargingStatus","Oplaadstatus :");
        ee.putString("voltage","Spanning :");
        ee.putString("temperature","Temperatuur :");


        //DRAWER
        ee.putString("LowBatteryReminder","Batterij herinnering");
        ee.putString("batterystatistics","Batterij statistieken");
        ee.putString("language","taal");
        ee.putString("alarmringtone","Alarm beltoon");
        ee.putString("navSettings","Instellingen");
        ee.putString("chooseColorTheme","Kies Kleur Thema");
        ee.putString("share","Delen");
        ee.putString("contact","Neem contact met ons op");
        ee.putString("RateUs","Beoordeel ons");

        //SETTINGS
        ee.putString("RingAlarmOnSilentMode","Ring alarm in stille modus");
        ee.putString("AutoStartupOnReboot","Automatisch opstarten bij opnieuw opstarten");
        ee.putString("ActivateNotificationPanel","Activeringspaneel activeren");
        ee.putString("Sound","geluidsvolume");
        ee.putString("ChangeTheftPasscode","Wijzig de diefstalcode");
        ee.putString("AutoEnableTheftAlarm","Automatisch inschakelen diefstal alarm");
        ee.putString("FullBatteryLevelTxt","Volledig batterij alarm niveau");
        ee.putString("LowBatteryLevelTxt","Notificatie op aangepast batterijniveau");
        ee.putString("BatteryTemperatureWarninglevel","Stel het waarschuwingsniveau van de batterij temperatuur in");

        ee.putString("PowerAdapterConnected","Voedingsadapter aangesloten");

        //HOW TO USE
        ee.putString("HowToUse","Hoe te gebruiken");
        ee.putString("TheftA","Als u diefstalalarm wilt gebruiken, sluit u de mobiele oplader aan en klik op 'Theft Alarm'.");
        ee.putString("TheftB","U wordt dan gevraagd een wachtwoord in te voeren. Dit is een eenmalige activiteit.");
        ee.putString("TheftC","Nadat het wachtwoord succesvol is geconfigureerd, wordt het diefstalalarm geactiveerd.");
        ee.putString("TheftD","Nu als de telefoon is losgekoppeld, klinkt een alarm en zal alleen stoppen door het juiste wachtwoord in te voegen.");
        ee.putString("FullBatteryAlarmA","Nadat de toepassing is geïnstalleerd, is het volledige batterij alarm geactiveerd.");
        ee.putString("FullBatteryAlarmB","Tijdens het opladen, wanneer de batterij volledig is opgeladen bij 100%, klinkt de app een alarm dat kan worden gestopt door te klikken op de weggooien alarmknop of door de lader uit te trekken.");

        ee.putString("ActivateVibrationMode","Activeer de trillingsmodus");
        ee.putString("ChangeTempUnit","Verander Temperatuur Eenheid");
        ee.putString("BatteryMonitor","Batterij Monitor");

        ee.putString("Theme","Thema");
        ee.putString("volume","Volume");
        ee.putString("SelectFullBatteryLevel","Selecteer het volledige batterijniveau");
        ee.putString("SelectCustomBatteryLevel","Selecteer Aangepast batterijniveau");
        ee.putString("SelectTemperatureWarningLevel","Selecteer het waarschuwingsniveau van de temperatuur");

        ee.putString("ChangeTemperatureUnit","Verander Temperatuur Eenheid");
        ee.putString("Celcius","Celsius");
        ee.putString("fahrenheit","Fahrenheit");
        ee.putString("cancel","Annuleer");


        //Ringtone
        ee.putString("Ringtone","ringtone");
        ee.putString("DefaultRingtone","Standaard Ringtones");
        ee.putString("SetCustomRingtone","Stel een aangepaste beltoon in");

        //Theft passcode..
        ee.putString("SetTheftPasscode","Set Theft Password");
        ee.putString("PasscodeArea","Wachtwoordgebied");
        ee.putString("Set","set");
        ee.putString("EnterPasscode","Voer wachtwoord in");
        ee.putString("ConfirmTheftPasscode","Bevestig diefstalwachtwoord");
        ee.putString("Confirm","Bevestigen");
        ee.putString("EnterOldPasscode","Voer het oude wachtwoord in");
        ee.putString("OldPassword","Oud Wachtwoord");
        ee.putString("Change","Verandering");


        ee.commit();
    }
    public void ESPERANTO(){
        SharedPreferences.Editor ee=sharedPreference.edit();

        ee.putString("status","true");
        ee.putString("fullBatteryAlarm","Plena Baterio Alarmo");

        ee.putString("health","Baterio Sano");
        ee.putString("volt","Tensio");
        ee.putString("temp","Temperaturo");
        ee.putString("brightness","Ekrano Heleco");
        //ee.putString("flashLight","Poŝlampo");
        ee.putString("flashLight","torĉo");
        ee.putString("theftalarm","ŝtelo Alarmo");
        ee.putString("theftalarm_caps","ŝtelo Alarmo");

        ee.putString("stop","Ĉesu");
        ee.putString("start","komenco");
        ee.putString("settings","Agordoj");


        //STATISTICS
        ee.putString("statistics","Statistikoj");
        ee.putString("batteryCapacity","Bateria kapacito :");
        ee.putString("remainingBatteryCapacity","Restanta kapablo :");
        ee.putString("batteryHealth","Baterio Sano :");
        ee.putString("batteryLevel","Bateria nivelo :");
        ee.putString("plugged","ŝtopita :");
        ee.putString("technology","teknologio :");
        ee.putString("chargingStatus","akuzanta Statuso :");
        ee.putString("voltage","Tensio :");
        ee.putString("temperature","Temperaturo :");


        //DRAWER
        ee.putString("LowBatteryReminder","Malalta baterio memorigilo");
        ee.putString("batterystatistics","Baterio Statistiko");
        ee.putString("language","Lingvo");
        ee.putString("alarmringtone","Alarmo ringtone");
        ee.putString("navSettings","Agordoj");
        ee.putString("chooseColorTheme","elekti Koloro Temo");
        ee.putString("share","Share");
        ee.putString("contact","Kontaktu nin");
        ee.putString("RateUs","Imposto nin");

        //SETTINGS
        ee.putString("RingAlarmOnSilentMode","Ringo Alarmo sur silenta reĝimo");
        ee.putString("AutoStartupOnReboot","Aŭtomata starto sur reboot");
        ee.putString("ActivateNotificationPanel","Aktivigi sciigo panelo");
        ee.putString("Sound","Sono volumo");
        ee.putString("ChangeTheftPasscode","Ŝanĝo Theft Passcode");
        ee.putString("AutoEnableTheftAlarm","Aŭtomata Ebligi ŝtelo Alarmo");
        ee.putString("FullBatteryLevelTxt","Plena Baterio Alarm nivelo");
        ee.putString("LowBatteryLevelTxt","Sciigo Ĉe Propra Bateria nivelo");
        ee.putString("BatteryTemperatureWarninglevel","Agordu Baterio Temperaturo Averto nivelo");
        ee.putString("PowerAdapterConnected","Potenco adaptilo konektita");

        //HOW TO USE
        ee.putString("HowToUse","Kiel uzi");
        ee.putString("TheftA","Por uzi ŝtelo alarmo, konekti la movebla plado kaj alklaku 'Theft Alarmo'.");
        ee.putString("TheftB","Vi tiam estos demandita enigi pasvorton. Jen unu-tempa aktiveco.");
        ee.putString("TheftC","Post sukcese agordi la pasvorton, la ŝtelo alarmo estos aktivigita.");
        ee.putString("TheftD","Se la telefono estas malŝaltita, alarmo sonos kaj nur haltos enigante la ĝustan pasvorton.");
        ee.putString("FullBatteryAlarmA","Iam la apliko estas instalita, plena baterio alarmo estas aktivigita.");
        ee.putString("FullBatteryAlarmB","Dum ŝarĝante, tuj la baterio estas plene ŝarĝita al la 100%, la app kriu alarmon kiu povas esti detenita de ĉu klakante la eksigi alarmo butono aŭ unplugging la plado.");

        ee.putString("ActivateVibrationMode","Aktivigi Vibro Mode");
        ee.putString("ChangeTempUnit","Ŝanĝo Temperaturo Unueco");
        ee.putString("BatteryMonitor","Baterio Monitoro");

        ee.putString("Theme","Temo");
        ee.putString("volume","Volumo");
        ee.putString("SelectFullBatteryLevel","Elektu Plena Bateria nivelo");
        ee.putString("SelectCustomBatteryLevel","Elektu Propra Bateria nivelo");
        ee.putString("SelectTemperatureWarningLevel","Elektu Temperaturo Averto nivelo");

        ee.putString("ChangeTemperatureUnit","Ŝanĝo Temperaturo Unueco");
        ee.putString("Celcius","Celsius");
        ee.putString("fahrenheit","Fahrenheit");
        ee.putString("cancel","Nuligi");


        //Ringtone
        ee.putString("Ringtone","Ringtone");
        ee.putString("DefaultRingtone","defaŭlta Ringtones");
        ee.putString("SetCustomRingtone","Ŝanĝu Propra Ringtone");

        //Theft passcode..
        ee.putString("SetTheftPasscode","Agordu Theft Pasvorto");
        ee.putString("PasscodeArea","Pasvorto Areo");
        ee.putString("Set","Agordu");
        ee.putString("EnterPasscode","Enigu pasvorton");
        ee.putString("ConfirmTheftPasscode","Konfirmi Theft Pasvorto");
        ee.putString("Confirm","konfirmu");
        ee.putString("EnterOldPasscode","Enmetu la malnovan pasvorton");
        ee.putString("OldPassword","malnova pasvorto");
        ee.putString("Change","ŝanĝo");


        ee.commit();
    }
    public void FRENCH(){
        SharedPreferences.Editor ee=sharedPreference.edit();

        ee.putString("status","true");
        ee.putString("fullBatteryAlarm","Alarme de batterie complète");

        ee.putString("health","Santé de la batterie");
        ee.putString("volt","Tension");
        ee.putString("temp","Température");
        ee.putString("brightness","Luminosité de l'écran");
        ee.putString("flashLight","Torche");
        ee.putString("theftalarm","Alarme de vol");
        ee.putString("theftalarm_caps","Alarme de vol");

        ee.putString("stop","Arrêtez");
        ee.putString("start","Début");
        ee.putString("settings","Paramètres");


        //STATISTICS
        ee.putString("statistics","Statistiques");
        ee.putString("batteryCapacity","Capacité de la batterie :");
        ee.putString("remainingBatteryCapacity","Capacité restante :");
        ee.putString("batteryHealth","Batterie de santé :");
        ee.putString("batteryLevel","Niveau de batterie :");
        ee.putString("plugged","Branché :");
        ee.putString("technology","La technologie :");
        ee.putString("chargingStatus","État de charge :");
        ee.putString("voltage","Tension :");
        ee.putString("temperature","Température :");


        //DRAWER
        ee.putString("LowBatteryReminder","Rappel de batterie faible");
        ee.putString("batterystatistics","Statistiques de la batterie");
        ee.putString("language","La langue");
        ee.putString("alarmringtone","Sonnerie d'alarme");
        ee.putString("navSettings","Paramètres");
        ee.putString("chooseColorTheme","Choisissez un thème couleur");
        ee.putString("share","Partager");
        ee.putString("contact","Contactez nous");
        ee.putString("RateUs","Évaluez nous");

        //SETTINGS
        ee.putString("RingAlarmOnSilentMode","Anneau d'alarme en mode silencieux");
        ee.putString("AutoStartupOnReboot","Démarrage automatique lors du redémarrage");
        ee.putString("ActivateNotificationPanel","Activer le panneau de notification");
        ee.putString("Sound","Volume sonore");
        ee.putString("ChangeTheftPasscode","Changement de code de vol");
        ee.putString("AutoEnableTheftAlarm","Activer automatiquement l'alarme antivol");
        ee.putString("FullBatteryLevelTxt","Niveau d'alarme de batterie complète");
        ee.putString("LowBatteryLevelTxt","Notification au niveau de batterie personnalisé");
        ee.putString("BatteryTemperatureWarninglevel","Réglez le niveau d'avertissement de la température de la batterie");

        ee.putString("PowerAdapterConnected","Adaptateur secteur connecté");

        //HOW TO USE
        ee.putString("HowToUse","Comment utiliser");
        ee.putString("TheftA","Pour utiliser l'alarme contre le vol, connectez le chargeur mobile et cliquez sur 'Alarme de vol'.");
        ee.putString("TheftB","Vous serez alors invité à entrer un mot de passe. Il s'agit d'une activité ponctuelle.");
        ee.putString("TheftC","Après avoir réussi à configurer le mot de passe, l'alarme de vol sera activée.");
        ee.putString("TheftD","Maintenant, si le téléphone est débranché, une alarme retentit et n'arrêtera qu'en insérant le mot de passe correct.");
        ee.putString("FullBatteryAlarmA","Une fois l'application installée, l'alarme de batterie complète est activée.");
        ee.putString("FullBatteryAlarmB","Lors de la charge, une fois que la batterie est complètement chargée à 100%, l'application émettra une alarme qui peut être arrêtée en cliquant sur le bouton d'alarme de renvoi ou en débranchant le chargeur.");


        ee.putString("ActivateVibrationMode","Activer le mode vibration");
        ee.putString("ChangeTempUnit","Unité de changement de température");
        ee.putString("BatteryMonitor","Moniteur de Batterie");

        ee.putString("Theme","Thème");
        ee.putString("volume","Le volume");
        ee.putString("SelectFullBatteryLevel","Sélectionnez le niveau complet de la batterie");
        ee.putString("SelectCustomBatteryLevel","Sélectionnez le niveau de batterie personnalisé");
        ee.putString("SelectTemperatureWarningLevel","Sélectionnez le niveau d'avertissement de température");

        ee.putString("ChangeTemperatureUnit","Unité de changement de température");
        ee.putString("Celcius","Celsius");
        ee.putString("fahrenheit","Fahrenheit");
        ee.putString("cancel","Annuler");


        //Ringtone
        ee.putString("Ringtone","Sonnerie");
        ee.putString("DefaultRingtone","Sonneries par défaut");
        ee.putString("SetCustomRingtone","Définir une sonnerie personnalisée");

        //Theft passcode..
        ee.putString("SetTheftPasscode","Set Theft Password");
        ee.putString("PasscodeArea","Zone de mot de passe");
        ee.putString("Set","Ensemble");
        ee.putString("EnterPasscode","Entrer le mot de passe");
        ee.putString("ConfirmTheftPasscode","Confirmer le mot de passe du vol");
        ee.putString("Confirm","Confirmer");
        ee.putString("EnterOldPasscode","Entrez l'ancien mot de passe");
        ee.putString("OldPassword","ancien mot de passe");
        ee.putString("Change","Changement");


        ee.commit();
    }
    public void GREEK(){
        SharedPreferences.Editor ee=sharedPreference.edit();

        ee.putString("status","true");
        ee.putString("fullBatteryAlarm","Πλήρης συναγερμός μπαταρίας");

        ee.putString("health","Υγεία μπαταρίας");
        ee.putString("volt","Τάση");
        ee.putString("temp","Θερμοκρασία");
        ee.putString("brightness","Φωτεινότητα οθόνης");
        ee.putString("flashLight","Δάδα");
        ee.putString("theftalarm_caps","ΗΛΕΚΤΡΙΚΗ ΣΥΝΑΓΕΡΜΟΣ");
        ee.putString("theftalarm","Συναγερμός κλοπής");

        ee.putString("stop","ΝΑ ΣΤΑΜΑΤΗΣΕΙ");
        ee.putString("start","ΑΡΧΗ");
        ee.putString("settings","ΡΥΘΜΙΣΕΙΣ");
        ee.putString("navSettings","Ρυθμίσεις");


        ee.putString("batteryCapacity","Χωρητικότητα μπαταρίας :");
        ee.putString("remainingBatteryCapacity","Υπόλοιπη χωρητικότητα :");
        ee.putString("batteryHealth","Υγεία μπαταρίας :");
        ee.putString("batteryLevel","Επίπεδο μπαταρίας :");
        ee.putString("plugged","Plugged :");
        ee.putString("technology","Τεχνολογία :");
        ee.putString("chargingStatus","Κατάσταση φόρτισης :");
        ee.putString("voltage","Τάση :");
        ee.putString("temperature","Θερμοκρασία :");
        ee.putString("statistics","ΣΤΑΤΙΣΤΙΚΗ");

        ee.putString("LowBatteryReminder","Υπενθύμιση χαμηλής μπαταρίας");
        ee.putString("batterystatistics","Στατιστικά μπαταρίας");
        ee.putString("language","Γλώσσα");
        ee.putString("alarmringtone","Ήχος κλήσης συναγερμού");
        ee.putString("chooseColorTheme","Επιλέξτε Χρώμα Θέμα");
        ee.putString("share","Μερίδιο");
        ee.putString("contact","Επικοινωνήστε μαζί μας");
        ee.putString("RateUs","Βαθμολογήστε μας");

        ee.putString("RingAlarmOnSilentMode","Ειδοποίηση κουδουνίσματος στην αθόρυβη λειτουργία");
        ee.putString("AutoStartupOnReboot","Αυτόματη εκκίνηση κατά την επανεκκίνηση");
        ee.putString("ActivateNotificationPanel","Ενεργοποιήστε τον πίνακα ειδοποιήσεων");
        ee.putString("Sound","Ήχος έντασης");
        ee.putString("ChangeTheftPasscode","Αλλάξτε τον κωδικό πρόσβασης κλοπής");
        ee.putString("AutoEnableTheftAlarm","Αυτόματη ενεργοποίηση συναγερμού κλοπής");
        ee.putString("FullBatteryLevelTxt","Πλήρες επίπεδο συναγερμού μπαταρίας");
        ee.putString("LowBatteryLevelTxt","Ειδοποίηση στη στάθμη προσαρμοσμένης μπαταρίας");
        ee.putString("BatteryTemperatureWarninglevel","Ρυθμίστε το επίπεδο προειδοποίησης θερμοκρασίας μπαταρίας");


        //HOW TO USE
        ee.putString("HowToUse","Πώς να χρησιμοποιήσετε");
        ee.putString("TheftA","Για να χρησιμοποιήσετε συναγερμό κλοπής, συνδέστε το φορητό φορτιστή και κάντε κλικ στο 'Theft Alarm'.");
        ee.putString("TheftB","Στη συνέχεια θα σας ζητηθεί να εισαγάγετε έναν κωδικό πρόσβασης.Πρόκειται για μια δραστηριότητα μιας ώρας.");
        ee.putString("TheftC","Αφού ρυθμίσετε επιτυχώς τον κωδικό πρόσβασης, θα ενεργοποιηθεί ο συναγερμός κλοπής.");
        ee.putString("TheftD","Τώρα, αν το τηλέφωνο είναι αποσυνδεδεμένο, θα ακουστεί ένας συναγερμός και θα σταματήσει μόνο εισάγοντας τον σωστό κωδικό πρόσβασης.");
        ee.putString("FullBatteryAlarmA","Μόλις εγκατασταθεί η εφαρμογή, ενεργοποιείται συναγερμός πλήρους μπαταρίας.");
        ee.putString("FullBatteryAlarmB","Κατά τη φόρτιση, μόλις ολοκληρωθεί η φόρτιση της μπαταρίας στο 100%, η εφαρμογή θα ακούσει έναν συναγερμό ο οποίος μπορεί να σταματήσει είτε κάνοντας κλικ στο κουμπί απομάκρυνσης συναγερμού είτε αποσυνδέοντας το φορτιστή.");


        ee.putString("ActivateVibrationMode","Ενεργοποιήστε τη λειτουργία δόνησης");
        ee.putString("ChangeTempUnit","Αλλαγή μονάδας θερμοκρασίας");
        ee.putString("BatteryMonitor","Παρακολούθηση μπαταριών");

        ee.putString("Theme","Θέμα");
        ee.putString("volume","Ενταση ΗΧΟΥ");
        ee.putString("SelectFullBatteryLevel","Επιλέξτε πλήρες επίπεδο μπαταρίας");
        ee.putString("SelectCustomBatteryLevel","Επιλέξτε Προσαρμοσμένη στάθμη μπαταρίας");
        ee.putString("SelectTemperatureWarningLevel","Επιλέξτε επίπεδο προειδοποίησης θερμοκρασίας");

        ee.putString("ChangeTemperatureUnit","Αλλαγή μονάδας θερμοκρασίας");
        ee.putString("Celcius","Κελσίου");
        ee.putString("fahrenheit","θερμόμετρο Φαρενάιτ");
        ee.putString("cancel","Ματαίωση");





        //Ringtone
        ee.putString("Ringtone","Ηχος ΚΛΗΣΗΣ");
        ee.putString("DefaultRingtone","Προεπιλεγμένα ήχοι κλήσης");
        ee.putString("SetCustomRingtone","Ορίστε προσαρμοσμένο ήχο κλήσης");

        //Theft passcode..
        ee.putString("SetTheftPasscode","Ορίστε τον κωδικό κλοπής");
        ee.putString("PasscodeArea","Περιοχή κωδικού πρόσβασης");
        ee.putString("Set","ΣΕΙΡΑ");
        ee.putString("EnterPasscode","Εισάγετε τον κωδικό πρόσβασης");
        ee.putString("ConfirmTheftPasscode","Επιβεβαιώστε τον κωδικό κλοπής");
        ee.putString("Confirm","Επιβεβαιώνω");
        ee.putString("EnterOldPasscode","Εισάγετε τον παλιό κωδικό πρόσβασης");
        ee.putString("OldPassword","ΠΑΛΙΟΣ ΚΩΔΙΚΟΣ");
        ee.putString("Change","Αλλαγή");


        ee.commit();
    }
    public void GERMAN(){
        SharedPreferences.Editor ee=sharedPreference.edit();

        ee.putString("status","true");
        ee.putString("fullBatteryAlarm","Voller Batteriealarm");

        ee.putString("health","Batteriegesundheit");
        ee.putString("volt","Stromspannung");
        ee.putString("temp","Temperatur");
        ee.putString("brightness","Bildschirmhelligkeit");
        ee.putString("flashLight","Fackel");
        ee.putString("theftalarm","Diebstahl sicherung");
        ee.putString("theftalarm_caps","Diebstahl sicherung");

        ee.putString("stop","Halt");
        ee.putString("start","Anfang");
        ee.putString("settings","Einstellungen");


        //STATISTICS
        ee.putString("statistics","Statistiken");
        ee.putString("batteryCapacity","Batteriekapazität :");
        ee.putString("remainingBatteryCapacity","Verbleibende Kapazität :");
        ee.putString("batteryHealth","Batteriegesundheit :");
        ee.putString("batteryLevel","Batterie Level :");
        ee.putString("plugged","Verstopft :");
        ee.putString("technology","Technologie :");
        ee.putString("chargingStatus","Aufladung Status :");
        ee.putString("voltage","Stromspannung :");
        ee.putString("temperature","Temperatur :");


        //DRAWER
        ee.putString("LowBatteryReminder","Niedrige Batterie Erinnerung");
        ee.putString("batterystatistics","Batteriestatistik");
        ee.putString("language","Sprache");
        ee.putString("alarmringtone","Alarm Klingelton");
        ee.putString("navSettings","Einstellungen");
        ee.putString("chooseColorTheme","Wählen Sie das Farbthema");
        ee.putString("share","Aktie");
        ee.putString("contact","Kontaktiere uns");
        ee.putString("RateUs","Bewerten Sie uns");

        //SETTINGS
        ee.putString("RingAlarmOnSilentMode","Ring Alarm im stillen Modus");
        ee.putString("AutoStartupOnReboot","Automatischer Start beim Neustart");
        ee.putString("ActivateNotificationPanel","Benachrichtigungsfeld aktivieren");
        ee.putString("Sound","Lautstärke");
        ee.putString("ChangeTheftPasscode","Denbstahlpasscode ändern");
        ee.putString("AutoEnableTheftAlarm","Automatischer Durchdringungsalarm");
        ee.putString("FullBatteryLevelTxt","Voller Batteriealarm");
        ee.putString("LowBatteryLevelTxt","Benachrichtigung bei benutzerdefiniertem Batteriestand");
        ee.putString("BatteryTemperatureWarninglevel","Akku-Temperatur-Warnstufe einstellen");

        ee.putString("PowerAdapterConnected","Netzteil angeschlossen");

        //HOW TO USE
        ee.putString("HowToUse","Wie benutzt man");
        ee.putString("TheftA","Um den Diebstahl-Alarm zu verwenden, schließen Sie das mobile Ladegerät an und klicken Sie auf 'Diebstahl-Alarm'.");
        ee.putString("TheftB","Sie werden dann aufgefordert, ein Passwort einzugeben. Dies ist eine einmalige Aktivität.");
        ee.putString("TheftC","Nach erfolgreichem Konfigurieren des Passwortes wird der Diebstahlalarm aktiviert.");
        ee.putString("TheftD","Wenn das Telefon ausgesteckt ist, ertönt ein Alarm und stoppt nur, indem er das richtige Passwort einfügt.");
        ee.putString("FullBatteryAlarmA","Sobald die Anwendung installiert ist, wird der volle Batteriealarm aktiviert.");
        ee.putString("FullBatteryAlarmB","Während des Ladevorgangs, sobald der Akku bei 100% voll aufgeladen ist, ertönt die App einen Alarm, der durch Anklicken der Alarmtaste oder durch Herausziehen des Ladegeräts gestoppt werden kann.");

        ee.putString("ActivateVibrationMode","Vibrationsmodus aktivieren");
        ee.putString("ChangeTempUnit","Temperatureinheit ändern");
        ee.putString("BatteryMonitor","Batterie-Monitor");

        ee.putString("Theme","Thema");
        ee.putString("volume","Volumen");
        ee.putString("SelectFullBatteryLevel","Wählen Sie den vollen Akkustand aus");
        ee.putString("SelectCustomBatteryLevel","Wählen Sie Benutzerdefinierte Batterielevel");
        ee.putString("SelectTemperatureWarningLevel","Wählen Sie Temperatur-Warnstufe");

        ee.putString("ChangeTemperatureUnit","Temperatureinheit ändern");
        ee.putString("Celcius","Celcius");
        ee.putString("fahrenheit","Fahrenheit");
        ee.putString("cancel","Stornieren");





        //Ringtone
        ee.putString("Ringtone","Klingelton");
        ee.putString("DefaultRingtone","Standard-Klingeltöne");
        ee.putString("SetCustomRingtone","Setzen Sie einen benutzerdefinierten Klingelton");

        //Theft passcode..
        ee.putString("SetTheftPasscode","Set Theft Password");
        ee.putString("PasscodeArea","Passwortbereich");
        ee.putString("Set","Set");
        ee.putString("EnterPasscode","Passwort eingeben");
        ee.putString("ConfirmTheftPasscode","Bestätigen Sie Diebstahl-Kennwort");
        ee.putString("Confirm","Bestätigen");
        ee.putString("EnterOldPasscode","Geben Sie das alte Passwort ein");
        ee.putString("OldPassword","Altes Passwort");
        ee.putString("Change","Veränderung");


        ee.commit();
    }
    public void HUNGARIAN(){
        SharedPreferences.Editor ee=sharedPreference.edit();

        ee.putString("status","true");
        ee.putString("fullBatteryAlarm","Teljes akkumulátor riasztás");

        ee.putString("health","Az akkumulátor egészsége");
        ee.putString("volt","Feszültség");
        ee.putString("temp","Hőmérséklet");
        ee.putString("brightness","A képernyő fényereje");
        ee.putString("flashLight","Fáklya");
        ee.putString("theftalarm","Lopás riasztás");
        ee.putString("theftalarm_caps","Lopás riasztás");

        ee.putString("stop","Állj meg");
        ee.putString("start","Rajt");
        ee.putString("settings","Beállítások");


        //STATISTICS
        ee.putString("statistics","Statisztika");
        ee.putString("batteryCapacity","Akkumulátor-kapacitás :");
        ee.putString("remainingBatteryCapacity","Megmaradt kapacitás :");
        ee.putString("batteryHealth","Az akkumulátor egészsége :");
        ee.putString("batteryLevel","Akkumulátor szint :");
        ee.putString("plugged","Bedugott :");
        ee.putString("technology","Technológia :");
        ee.putString("chargingStatus","Töltés állapota :");
        ee.putString("voltage","Feszültség :");
        ee.putString("temperature","Hőmérséklet :");


        //DRAWER
        ee.putString("LowBatteryReminder","Alacsony elemtartó emlékeztető");
        ee.putString("batterystatistics","Akkumulátor statisztikák");
        ee.putString("language","Nyelv");
        ee.putString("alarmringtone","Riasztási csengőhang");
        ee.putString("navSettings","Beállítások");
        ee.putString("chooseColorTheme","Válassza a Színes téma lehetőséget");
        ee.putString("share","Ossza meg");
        ee.putString("contact","Lépjen kapcsolatba velünk");
        ee.putString("RateUs","Értékelj minket");

        //SETTINGS
        ee.putString("RingAlarmOnSilentMode","Csengetés riasztás csendes üzemmódban");
        ee.putString("AutoStartupOnReboot","Automatikus indítás újraindításkor");
        ee.putString("ActivateNotificationPanel","Aktiváljon értesítési panelt");
        ee.putString("Sound","Hangerő");
        ee.putString("ChangeTheftPasscode","A lopás jelszavának módosítása");
        ee.putString("AutoEnableTheftAlarm","Automatikus lopás riasztás engedélyezése");
        ee.putString("FullBatteryLevelTxt","A teljes elem riasztási szintje");
        ee.putString("LowBatteryLevelTxt","Értesítés az egyéni akkumulátor szintjén");
        ee.putString("BatteryTemperatureWarninglevel","Állítsa be az akkumulátor hőmérsékletének figyelmeztetési szintjét");

        ee.putString("PowerAdapterConnected","Hálózati adapter csatlakoztatv");

        //HOW TO USE
        ee.putString("HowToUse","Hogyan kell használni");
        ee.putString("TheftA","A lopás riasztás használatához csatlakoztassa a mobil töltőt és kattintson a \"Lopás riasztás\".");
        ee.putString("TheftB","Ezután felkérést kap a jelszó megadására. Ez egy egyszeri tevékenység.");
        ee.putString("TheftC","A jelszó sikeres konfigurálása után a lopás riasztás aktiválódik.");
        ee.putString("TheftD","Most, ha a telefon ki van húzva, riasztás hallható, és csak a helyes jelszó beillesztésével fog megállni.");
        ee.putString("FullBatteryAlarmA","Az alkalmazás telepítése után a teljes akkumulátor riasztás aktiválódik.");
        ee.putString("FullBatteryAlarmB","A töltés után, ha az akkumulátor teljesen fel van töltve 100% -kal, az alkalmazás riasztást hallat, amelyet le lehet állítani az elakadásjelző gomb megnyomásával vagy a töltő kihúzásával.");


        ee.putString("ActivateVibrationMode","Aktiválja a rezgési módot");
        ee.putString("ChangeTempUnit","Hőmérsékleti egység módosítása");
        ee.putString("BatteryMonitor","Akkumulátor monitor");

        ee.putString("Theme","Téma");
        ee.putString("volume","Kötet");
        ee.putString("SelectFullBatteryLevel","Válassza ki a teljes akkumulátorszintet");
        ee.putString("SelectCustomBatteryLevel","Válassza az Egyéni akkumulátor töltöttségi szintjét");
        ee.putString("SelectTemperatureWarningLevel","Válassza ki a hőmérséklet figyelmeztetés szintjét");

        ee.putString("ChangeTemperatureUnit","Hőmérsékleti egység módosítása");
        ee.putString("Celcius","Celsius");
        ee.putString("fahrenheit","Fahrenheit");
        ee.putString("cancel","Megszünteti");


        //Ringtone
        ee.putString("Ringtone","Csengőhang");
        ee.putString("DefaultRingtone","Alapértelmezett csengőhangok");
        ee.putString("SetCustomRingtone","Egyéni csengőhang beállítása");

        //Theft passcode..
        ee.putString("SetTheftPasscode","Állítsa be a lopás jelszavát");
        ee.putString("PasscodeArea","Jelszófelület");
        ee.putString("Set","Készlet");
        ee.putString("EnterPasscode","Írd be a jelszót");
        ee.putString("ConfirmTheftPasscode","A lopás jelszó megerősítése");
        ee.putString("Confirm","Jóváhagy");
        ee.putString("EnterOldPasscode","Írja be a régi jelszót");
        ee.putString("OldPassword","régi jelszó");
        ee.putString("Change","változás");


        ee.commit();
    }
    public void INDONESIAN(){
        SharedPreferences.Editor ee=sharedPreference.edit();

        ee.putString("status","true");
        ee.putString("fullBatteryAlarm","Alarm baterai penuh");

        ee.putString("health","Kesehatan baterai");
        ee.putString("volt","Tegangan");
        ee.putString("temp","Suhu");
        ee.putString("brightness","Kecerahan layar");
        ee.putString("flashLight","Obor");
        ee.putString("theftalarm","Alarm Pencurian");
        ee.putString("theftalarm_caps","Alarm Pencurian");

        ee.putString("stop","Berhenti");
        ee.putString("start","Mulai");
        ee.putString("settings","Pengaturan");


        //STATISTICS
        ee.putString("statistics","Statistik");
        ee.putString("batteryCapacity","Kapasitas Baterai :");
        ee.putString("remainingBatteryCapacity","Sisa kapasitas :");
        ee.putString("batteryHealth","Kesehatan Baterai :");
        ee.putString("batteryLevel","Tingkat Baterai :");
        ee.putString("plugged","Terpasang :");
        ee.putString("technology","Teknologi :");
        ee.putString("chargingStatus","Status pengisian :");
        ee.putString("voltage","Tegangan :");
        ee.putString("temperature","Suhu :");


        //DRAWER
        ee.putString("LowBatteryReminder","Pengingat baterai rendah");
        ee.putString("batterystatistics","Statistik Baterai");
        ee.putString("language","Bahasa");
        ee.putString("alarmringtone","Nada dering alarm");
        ee.putString("navSettings","Pengaturan");
        ee.putString("chooseColorTheme","Pilih Theme Warna");
        ee.putString("share","Bagikan");
        ee.putString("contact","Hubungi kami");
        ee.putString("RateUs","Beri peringkat kami");

        //SETTINGS
        ee.putString("RingAlarmOnSilentMode","Alarm Ring pada mode senyap");
        ee.putString("AutoStartupOnReboot","Auto startup saat reboot");
        ee.putString("ActivateNotificationPanel","Aktifkan panel notifikasi");
        ee.putString("Sound","Volume suara");
        ee.putString("ChangeTheftPasscode","Ubah kode lolos pencurian");
        ee.putString("AutoEnableTheftAlarm","Auto enable theft alarm");
        ee.putString("FullBatteryLevelTxt","Tingkat Alarm Baterai Penuh");
        ee.putString("LowBatteryLevelTxt","Pemberitahuan Pada Tingkat Baterai Khusus");
        ee.putString("BatteryTemperatureWarninglevel","Mengatur Tingkat Peringatan Suhu Baterai");

        ee.putString("PowerAdapterConnected","Adaptor daya tersambung");

        //HOW TO USE
        ee.putString("HowToUse","Bagaimana cara menggunakan");
        ee.putString("TheftA","Untuk menggunakan alarm pencurian, sambungkan pengisi daya ponsel dan klik 'Alarm Pencurian'.");
        ee.putString("TheftB","Anda kemudian akan diminta memasukkan kata sandi. Ini adalah aktivitas satu kali.");
        ee.putString("TheftC","Setelah berhasil mengonfigurasi password, alarm pencurian akan diaktifkan.");
        ee.putString("TheftD","Sekarang jika telepon dicabut, alarm akan berbunyi dan hanya akan berhenti dengan memasukkan kata sandi yang benar.");
        ee.putString("FullBatteryAlarmA","Setelah aplikasi terinstal, alarm baterai penuh diaktifkan.");
        ee.putString("FullBatteryAlarmB","Saat mengisi daya, setelah baterai terisi penuh pada 100%, aplikasi akan membunyikan alarm yang dapat dihentikan dengan mengklik tombol alarm yang dihantam atau dengan mencabut pengisi baterai.");


        ee.putString("ActivateVibrationMode","Aktifkan Mode Getar");
        ee.putString("ChangeTempUnit","Ubah Unit Suhu");
        ee.putString("BatteryMonitor","Monitor Baterai");

        ee.putString("Theme","Tema");
        ee.putString("volume","Volume");
        ee.putString("SelectFullBatteryLevel","Pilih Tingkat Baterai Penuh");
        ee.putString("SelectCustomBatteryLevel","Pilih Tingkat Baterai Khusus");
        ee.putString("SelectTemperatureWarningLevel","Pilih Temperature Warning Level");

        ee.putString("ChangeTemperatureUnit","Ubah Unit Suhu");
        ee.putString("Celcius","Celsius");
        ee.putString("fahrenheit","Fahrenheit");
        ee.putString("cancel","Membatalkan");



        //Ringtone
        ee.putString("Ringtone","Ringtone");
        ee.putString("DefaultRingtone","Nada Dering default");
        ee.putString("SetCustomRingtone","Tetapkan Ringtone Khusus");

        //Theft passcode..
        ee.putString("SetTheftPasscode","Set Theft Password");
        ee.putString("PasscodeArea","Area kata sandi");
        ee.putString("Set","SET");
        ee.putString("EnterPasscode","Masukkan kata kunci");
        ee.putString("ConfirmTheftPasscode","Konfirmasikan Password Pencurian");
        ee.putString("Confirm","Memastikan");
        ee.putString("EnterOldPasscode","Masukkan kata sandi lama");
        ee.putString("OldPassword","password lama");
        ee.putString("Change","Perubahan");

        ee.commit();
    }
    public void ITALIAN(){
        SharedPreferences.Editor ee=sharedPreference.edit();

        ee.putString("status","true");
        ee.putString("fullBatteryAlarm","Allarme batteria totale");

        ee.putString("health","Salute della batteria");
        ee.putString("volt","Voltaggio");
        ee.putString("temp","Temperatura");
        ee.putString("brightness","Luminosità dello schermo");
        ee.putString("flashLight","Torcia");
        ee.putString("theftalarm","Allarme allarme");
        ee.putString("theftalarm_caps","Allarme allarme");

        ee.putString("stop","Stop");
        ee.putString("start","Inizio");
        ee.putString("settings","Impostazioni");


        //STATISTICS
        ee.putString("statistics","Statistica");
        ee.putString("batteryCapacity","Capacità della batteria :");
        ee.putString("remainingBatteryCapacity","Capacità rimanente :");
        ee.putString("batteryHealth","Salute della batteria :");
        ee.putString("batteryLevel","Livello della batteria :");
        ee.putString("plugged","Tappato :");
        ee.putString("technology","Tecnologia :");
        ee.putString("chargingStatus","Carica dello stato :");
        ee.putString("voltage","Voltaggio :");
        ee.putString("temperature","Temperatura :");


        //DRAWER
        ee.putString("LowBatteryReminder","Ricordo basso della batteria");
        ee.putString("batterystatistics","Statistiche della batteria");
        ee.putString("language","Lingua");
        ee.putString("alarmringtone","Suoneria di allarme");
        ee.putString("navSettings","Impostazioni");
        ee.putString("chooseColorTheme","Scegliere il tema a colori");
        ee.putString("share","Condividere");
        ee.putString("contact","Contattaci");
        ee.putString("RateUs","Votaci");

        //SETTINGS
        ee.putString("RingAlarmOnSilentMode","Allarme suoneria in modalità silenziosa");
        ee.putString("AutoStartupOnReboot","Avvio automatico al riavvio");
        ee.putString("ActivateNotificationPanel","Attiva il pannello di notifica");
        ee.putString("Sound","Volume del suono");
        ee.putString("ChangeTheftPasscode","Cambia codice di furto");
        ee.putString("AutoEnableTheftAlarm","Abilita l'allarme automatico");
        ee.putString("FullBatteryLevelTxt","Livello di allarme completo della batteria");
        ee.putString("LowBatteryLevelTxt","Notifica a livello di batteria personalizzato");
        ee.putString("BatteryTemperatureWarninglevel","Impostare il livello di avviso di temperatura della batteria");

        ee.putString("PowerAdapterConnected","Alimentatore collegato");

        //HOW TO USE
        ee.putString("HowToUse","Come usare");
        ee.putString("TheftA","Per utilizzare l'allarme di furto, collegare il caricatore mobile e fare clic su 'Allarme antifurto'.");
        ee.putString("TheftB","Verrà quindi richiesto di inserire una password. Questa è un'attività unica.");
        ee.putString("TheftC","Dopo aver configurato correttamente la password, l'allarme furto verrà attivato.");
        ee.putString("TheftD","Ora se il telefono è scollegato, un allarme suona e si interromperà solo inserendo la password corretta.");
        ee.putString("FullBatteryAlarmA","Una volta installata l'applicazione, viene attivata l'allarme completo della batteria.");
        ee.putString("FullBatteryAlarmB","Durante la ricarica, una volta che la batteria è completamente carica al 100%, l'applicazione emette un allarme che può essere arrestato facendo clic sul pulsante di allarme di disattivazione o scollegando il caricabatterie.");


        ee.putString("ActivateVibrationMode","Attivare la modalità di vibrazione");
        ee.putString("ChangeTempUnit","Cambia unità di temperatura");
        ee.putString("BatteryMonitor","Monitor della batteria");

        ee.putString("Theme","Tema");
        ee.putString("volume","Volume");
        ee.putString("SelectFullBatteryLevel","Seleziona il livello della batteria totale");
        ee.putString("SelectCustomBatteryLevel","Seleziona Livello di batteria personalizzato");
        ee.putString("SelectTemperatureWarningLevel","Seleziona Livello di avviso temperatura");

        ee.putString("ChangeTemperatureUnit","Cambia unità di temperatura");
        ee.putString("Celcius","Centigrado");
        ee.putString("fahrenheit","Fahrenheit");
        ee.putString("cancel","Annulla");


        //Ringtone
        ee.putString("Ringtone","Suoneria");
        ee.putString("DefaultRingtone","Suonerie predefinite");
        ee.putString("SetCustomRingtone","Imposta suoneria personalizzata");

        //Theft passcode..
        ee.putString("SetTheftPasscode","Imposta la password di furto");
        ee.putString("PasscodeArea","Area Password");
        ee.putString("Set","IMPOSTATO");
        ee.putString("EnterPasscode","Inserire la password");
        ee.putString("ConfirmTheftPasscode","Conferma la password di furto");
        ee.putString("Confirm","Conferma");
        ee.putString("EnterOldPasscode","Inserisci la vecchia password");
        ee.putString("OldPassword","vecchia password");
        ee.putString("Change","Modificare");

        ee.commit();
    }
    public void KURDISH(){
        SharedPreferences.Editor ee=sharedPreference.edit();

        ee.putString("status","true");
        ee.putString("fullBatteryAlarm","full Alarm Pîlê");

        ee.putString("health","Tenduristiyê Pîlê");
        ee.putString("volt","Woltî");
        ee.putString("temp","Germî");
        ee.putString("brightness","Bright screen");
        ee.putString("flashLight","Finddar");
        ee.putString("theftalarm","Alarm Dizîya");
        ee.putString("theftalarm_caps","Alarm Dizîya");

        ee.putString("stop","Rawestan");
        ee.putString("start","Destpêkirin");
        ee.putString("settings","Mîhengên");


        //STATISTICS
        ee.putString("statistics","Jimare");
        ee.putString("batteryCapacity","Kapasîteya pîlê :");
        ee.putString("remainingBatteryCapacity","Kapasîteya xwe ya mayî :");
        ee.putString("batteryHealth","Tenduristiyê pîlê :");
        ee.putString("batteryLevel","Asta pîlê :");
        ee.putString("plugged","Têxistin :");
        ee.putString("technology","Teknolocî :");
        ee.putString("chargingStatus","Rewş ji mêrekî ji :");
        ee.putString("voltage","Woltî :");
        ee.putString("temperature","Germî :");


        //DRAWER
        ee.putString("LowBatteryReminder","Bibîrxistin Battery Low");
        ee.putString("batterystatistics","Statistics pîlê");
        ee.putString("language","Ziman");
        ee.putString("alarmringtone","Ringtone Alarm");
        ee.putString("navSettings","Mîhengên");
        ee.putString("chooseColorTheme","Wybierz motyw koloru");
        ee.putString("share","Par");
        ee.putString("contact","Paqij bûn");
        ee.putString("RateUs","çavkaniyên me");

        //SETTINGS
        ee.putString("RingAlarmOnSilentMode","Alarm Ring li ser moda bêdeng");
        ee.putString("AutoStartupOnReboot","Destpêkê de Auto li rebootboot");
        ee.putString("ActivateNotificationPanel","Panel notification aktîv bike");
        ee.putString("Sound","Volume Sound");
        ee.putString("ChangeTheftPasscode","Passcode Change Dizîya");
        ee.putString("AutoEnableTheftAlarm","Auto çalak alarm dizî");
        ee.putString("FullBatteryLevelTxt","Full Level Alarm Pîlê");
        ee.putString("LowBatteryLevelTxt","Hişyariya At Asta Pîlê Custom");
        ee.putString("BatteryTemperatureWarninglevel","Set Battery Level Warning: Germahiya");

        ee.putString("PowerAdapterConnected","Adapter Power ve girêdayî ye");

        //HOW TO USE
        ee.putString("HowToUse","Çawa bi kar bînin");
        ee.putString("TheftA","Ji bo bikaranîna alarm dizî, di navbera charger mobile û click on 'Dizîya Alarm'.");
        ee.putString("TheftB","Tu wê demê dê bê pirsîn ku bikevin a şîfreya. Ev çalakî yek-dem e.");
        ee.putString("TheftC","piştî bi serkeftî sazkirina şîfreya, alarm dizî li kerdene dê bên.");
        ee.putString("TheftD","Îcar, eger bi telefonê Unplugged e, alarma dengê wê û wê tenê bi bicîkirina li şîfreya xwe kontrol bike dev ji");
        ee.putString("FullBatteryAlarmA","Dema ku sepan hatiye sazkirin, alarm battery full aktîfkirin ye.");
        ee.putString("FullBatteryAlarmB","Dema ku ji mêrekî, carekê pîlê bi temamî li 100% doz, di app zengilek ku dikare bê destê tikandina bişkoka dûrkirina alarm an by unplugging li charger rawestandin dengê wê");

        ee.putString("ActivateVibrationMode","Aktîv Mode Vibration");
        ee.putString("ChangeTempUnit","Unit Change: Germahiya");
        ee.putString("BatteryMonitor","Nîşandêra Pîlan");

        ee.putString("Theme","Mijad");
        ee.putString("volume","Bend");
        ee.putString("SelectFullBatteryLevel","Select Full Asta Pîlê");
        ee.putString("SelectCustomBatteryLevel","Select Asta Pîlê Custom");
        ee.putString("SelectTemperatureWarningLevel","Level Warning Hilbijêre: Germahiya");

        ee.putString("ChangeTemperatureUnit","Unit Change: Germahiya");
        ee.putString("Celcius","celsius");
        ee.putString("fahrenheit","Fahrenheit");
        ee.putString("cancel","Bişûndekirin");


        //Ringtone
        ee.putString("Ringtone","Ringtone");
        ee.putString("DefaultRingtone","Default Ringtones");
        ee.putString("SetCustomRingtone","Set Ringtone Custom");

        //Theft passcode..
        ee.putString("SetTheftPasscode","Set Password Dizîya");
        ee.putString("PasscodeArea","Area Password");
        ee.putString("Set","DANÎN");
        ee.putString("EnterPasscode","Şifreyê têke");
        ee.putString("ConfirmTheftPasscode","Confirm Dizîya Password");
        ee.putString("Confirm","Tesdîqkirin");
        ee.putString("EnterOldPasscode","şîfreya xwe yê kevin binivîse");
        ee.putString("OldPassword","Şîfre Old");
        ee.putString("Change","Gûherrandinî");


        ee.commit();
    }
    public void POLISH(){
        SharedPreferences.Editor ee=sharedPreference.edit();

        ee.putString("status","true");
        ee.putString("fullBatteryAlarm","Pełny alarm akumulatora");

        ee.putString("health","Stan baterii");
        ee.putString("volt","Napięcie");
        ee.putString("temp","Temperatura");
        ee.putString("brightness","Jasność ekranu");
        ee.putString("flashLight","Pochodnia");
        ee.putString("theftalarm","Alarm kradzieży");
        ee.putString("theftalarm_caps","Alarm kradzieży");

        ee.putString("stop","Zatrzymać");
        ee.putString("start","Początek");
        ee.putString("settings","Ustawienia");


        //STATISTICS
        ee.putString("statistics","Statystyka");
        ee.putString("batteryCapacity","Pojemność baterii :");
        ee.putString("remainingBatteryCapacity","Pozostała pojemność :");
        ee.putString("batteryHealth","Stan baterii :");
        ee.putString("batteryLevel","poziom baterii :");
        ee.putString("plugged","Zatkany :");
        ee.putString("technology","Technologia :");
        ee.putString("chargingStatus","Stan ładowania :");
        ee.putString("voltage","Napięcie :");
        ee.putString("temperature","Temperatura :");


        //DRAWER
        ee.putString("LowBatteryReminder","Ostrzeżenie o niskim poziomie baterii");
        ee.putString("batterystatistics","Statystyki baterii");
        ee.putString("language","Język");
        ee.putString("alarmringtone","Dzwonek alarmu");
        ee.putString("navSettings","Ustawienia");
        ee.putString("chooseColorTheme","Wybierz motyw koloru");
        ee.putString("share","Dzielić");
        ee.putString("contact","Skontaktuj się z nami");
        ee.putString("RateUs","Oceń nas");

        //SETTINGS
        ee.putString("RingAlarmOnSilentMode","Alarm dzwonka w trybie cichym");
        ee.putString("AutoStartupOnReboot","Automatyczne uruchamianie po restarcie");
        ee.putString("ActivateNotificationPanel","Uaktywnij panel powiadomień");
        ee.putString("Sound","Głośność dźwięku");
        ee.putString("ChangeTheftPasscode","Zmień kod dostępu do kradzieży");
        ee.putString("AutoEnableTheftAlarm","Automatycznie włącz alarm kradzieży");
        ee.putString("FullBatteryLevelTxt","Pełny poziom alarmu akumulatora");
        ee.putString("LowBatteryLevelTxt","Powiadomienie na niestandardowym poziomie baterii");
        ee.putString("BatteryTemperatureWarninglevel","Ustaw poziom ostrzegania o temperaturze akumulatora");

        ee.putString("PowerAdapterConnected","Podłączony adapter zasilania");

        //HOW TO USE
        ee.putString("HowToUse","Jak używać");
        ee.putString("TheftA","Aby użyć alarmu kradzieży, podłącz ładowarkę do telefonu i kliknij 'Alarm kradzieży'.");
        ee.putString("TheftB","Zostaniesz poproszony o podanie hasła. To jednorazowa aktywność.");
        ee.putString("TheftC","Po pomyślnym skonfigurowaniu hasła zostanie aktywowany alarm kradzieży.");
        ee.putString("TheftD","Jeśli telefon jest odłączony, zadzwoni alarm, który zatrzyma się tylko przez wprowadzenie właściwego hasła.");
        ee.putString("FullBatteryAlarmA","Po zainstalowaniu aplikacji uruchomiony zostanie pełny alarm akumulatora.");
        ee.putString("FullBatteryAlarmB","Podczas ładowania, gdy bateria jest w pełni naładowana w 100%, aplikacja wyemituje alarm, który może zostać zatrzymany, klikając przycisk Odrzuć alarm lub odłączając ładowarkę.");


        ee.putString("ActivateVibrationMode","Włącz tryb wibracji");
        ee.putString("ChangeTempUnit","Zmiana jednostki temperatury");
        ee.putString("BatteryMonitor","Monitor baterii");

        ee.putString("Theme","Motyw");
        ee.putString("volume","Tom");
        ee.putString("SelectFullBatteryLevel","Wybierz pełny poziom baterii");
        ee.putString("SelectCustomBatteryLevel","Wybierz Niestandardowy poziom baterii");
        ee.putString("SelectTemperatureWarningLevel","Wybierz poziom ostrzegania o temperaturze");

        ee.putString("ChangeTemperatureUnit","Zmiana jednostki temperatury");
        ee.putString("Celcius","Celsjusz");
        ee.putString("fahrenheit","Fahrenheit");
        ee.putString("cancel","Anuluj");



        //Ringtone
        ee.putString("Ringtone","Dzwonek");
        ee.putString("DefaultRingtone","Domyślne dzwonki");
        ee.putString("SetCustomRingtone","Ustaw niestandardowy dzwonek");

        //Theft passcode..
        ee.putString("SetTheftPasscode","Ustaw hasło kradzieży");
        ee.putString("PasscodeArea","Obszar haseł");
        ee.putString("Set","ZESTAW");
        ee.putString("EnterPasscode","Wprowadź hasło");
        ee.putString("ConfirmTheftPasscode","Potwierdź hasło kradzieży");
        ee.putString("Confirm","Potwierdzać");
        ee.putString("EnterOldPasscode","Wprowadź stare hasło");
        ee.putString("OldPassword","stare hasło");
        ee.putString("Change","Zmiana");

        ee.commit();
    }
    public void PORTUGUESE(){
        SharedPreferences.Editor ee=sharedPreference.edit();

        ee.putString("status","true");
        ee.putString("fullBatteryAlarm","Alarme de bateria cheia");

        ee.putString("health","Saúde da bateria");
        ee.putString("volt","Voltagem");
        ee.putString("temp","Temperatura");
        ee.putString("brightness","Brilho do ecrã");
        ee.putString("flashLight","Tocha");
        ee.putString("theftalarm","Alarme anti-roubo");
        ee.putString("theftalarm_caps","Alarme anti-roubo");

        ee.putString("stop","Pare");
        ee.putString("start","Começar");
        ee.putString("settings","Configurações");


        //STATISTICS
        ee.putString("statistics","Estatisticas");
        ee.putString("batteryCapacity","Capacidade de carga :");
        ee.putString("remainingBatteryCapacity","Capacidade restante :");
        ee.putString("batteryHealth","Saúde da bateria :");
        ee.putString("batteryLevel","nível de bateria :");
        ee.putString("plugged","Obstruído :");
        ee.putString("technology","Tecnologia :");
        ee.putString("chargingStatus","Status de cobrança :");
        ee.putString("voltage","Voltagem :");
        ee.putString("temperature","Temperatura :");


        //DRAWER
        ee.putString("LowBatteryReminder","Lembrete de bateria fraca");
        ee.putString("batterystatistics","Estatísticas de bateria");
        ee.putString("language","Língua");
        ee.putString("alarmringtone","Toque de alarme");
        ee.putString("navSettings","Configurações");
        ee.putString("chooseColorTheme","Escolha o tema da cor");
        ee.putString("share","Compartilhar");
        ee.putString("contact","Contate-Nos");
        ee.putString("RateUs","Nos avalie");

        //SETTINGS
        ee.putString("RingAlarmOnSilentMode","Alarme de toque no modo silencioso");
        ee.putString("AutoStartupOnReboot","Inicialização automática ao reinicializar");
        ee.putString("ActivateNotificationPanel","Ativar painel de notificação");
        ee.putString("Sound","Volume de som");
        ee.putString("ChangeTheftPasscode","Alterar senha de roubo");
        ee.putString("AutoEnableTheftAlarm","Alarme de roubo de habilitação automática");
        ee.putString("FullBatteryLevelTxt","Nível de alarme completo da bateria");
        ee.putString("LowBatteryLevelTxt","Notificação ao nível da bateria personalizada");
        ee.putString("BatteryTemperatureWarninglevel","Definir Nível de advertência de temperatura da bateria");

        ee.putString("PowerAdapterConnected","Adaptador de alimentação conectado");

        //HOW TO USE
        ee.putString("HowToUse","Como usar");
        ee.putString("TheftA","Para usar o alarme de roubo, conecte o carregador móvel e clique em \"Alarme de roubo\".");
        ee.putString("TheftB","Você será solicitado a digitar uma senha. Esta é uma atividade única.");
        ee.putString("TheftC","Depois de configurar com êxito a senha, o alarme de roubo será ativado.");
        ee.putString("TheftD","Agora, se o telefone estiver desconectado, um alarme soará e só irá parar inserindo a senha correta.");
        ee.putString("FullBatteryAlarmA","Uma vez instalado o aplicativo, o alarme de bateria cheia é ativado.");
        ee.putString("FullBatteryAlarmB","Durante o carregamento, quando a bateria estiver completamente carregada a 100%, o aplicativo emitirá um alarme que pode ser interrompido clicando no botão de desativar alarme ou desconectando o carregador.");

        ee.putString("ActivateVibrationMode","Ativar o Modo de vibração");
        ee.putString("ChangeTempUnit","Unidade de mudança de temperatura");
        ee.putString("BatteryMonitor","Monitor de bateria");

        ee.putString("Theme","Tema");
        ee.putString("volume","Volume");
        ee.putString("SelectFullBatteryLevel","Selecione o nível de bateria total");
        ee.putString("SelectCustomBatteryLevel","Selecione o Nível de bateria personalizado");
        ee.putString("SelectTemperatureWarningLevel","Selecione o nível de advertência de temperatura");

        ee.putString("ChangeTemperatureUnit","Unidade de mudança de temperatura");
        ee.putString("Celcius","Celsius");
        ee.putString("fahrenheit","Fahrenheit");
        ee.putString("cancel","Cancelar");

        //Ringtone
        ee.putString("Ringtone","toque");
        ee.putString("DefaultRingtone","Ringtones padrão");
        ee.putString("SetCustomRingtone","Set Custom Ringtone");

        //Theft passcode..
        ee.putString("SetTheftPasscode","Definir Senha do Roubo");
        ee.putString("PasscodeArea","Área de senha");
        ee.putString("Set","CONJUNTO");
        ee.putString("EnterPasscode","Digite a senha");
        ee.putString("ConfirmTheftPasscode","Confirmar senha do roubo");
        ee.putString("Confirm","confirme");
        ee.putString("EnterOldPasscode","Digite a senha antiga");
        ee.putString("OldPassword","Senha Antiga");
        ee.putString("Change","mudança");

        ee.commit();
    }
    public void RUSSIAN(){
        SharedPreferences.Editor ee=sharedPreference.edit();

        ee.putString("status","true");
        ee.putString("fullBatteryAlarm","Сигнал полной батареи");

        ee.putString("health","Здоровье батареи");
        ee.putString("volt","напряжение");
        ee.putString("temp","Температура");
        ee.putString("brightness","Яркость экрана");
        ee.putString("flashLight","факел");
        ee.putString("theftalarm","Аварийная сигнализация");
        ee.putString("theftalarm_caps","Аварийная сигнализация");

        ee.putString("stop","Стоп");
        ee.putString("start","Начало");
        ee.putString("settings","Настройки");


        //STATISTICS
        ee.putString("statistics","Статистика");
        ee.putString("batteryCapacity","Емкость батареи :");
        ee.putString("remainingBatteryCapacity","Оставшаяся емкость :");
        ee.putString("batteryHealth","Здоровье батареи :");
        ee.putString("batteryLevel","Уровень батареи :");
        ee.putString("plugged","подключен :");
        ee.putString("technology","Технологии :");
        ee.putString("chargingStatus","Статус зарядки :");
        ee.putString("voltage","напряжение :");
        ee.putString("temperature","Температура :");


        //DRAWER
        ee.putString("LowBatteryReminder","Напоминание о низком заряде аккумулятора");
        ee.putString("batterystatistics","Статистика батареи");
        ee.putString("language","Язык");
        ee.putString("alarmringtone","Сигнал будильника");
        ee.putString("navSettings","Настройки");
        ee.putString("chooseColorTheme","Выберите тему цвета");
        ee.putString("share","Поделиться");
        ee.putString("contact","Свяжитесь с нами");
        ee.putString("RateUs","Оцените нас");

        //SETTINGS
        ee.putString("RingAlarmOnSilentMode","Сигнал вызова в бесшумном режиме");
        ee.putString("AutoStartupOnReboot","Автоматический запуск при перезагрузке");
        ee.putString("ActivateNotificationPanel","Активировать панель уведомлений");
        ee.putString("Sound","Громкость");
        ee.putString("ChangeTheftPasscode","Сменить пароль для кражи");
        ee.putString("AutoEnableTheftAlarm","Автоматическое включение предупреждения о краже");
        ee.putString("FullBatteryLevelTxt","Уровень сигнала полной батареи");
        ee.putString("LowBatteryLevelTxt","Уведомление на пользовательском уровне батареи");
        ee.putString("BatteryTemperatureWarninglevel","Установить уровень предупреждения о температуре батареи");

        ee.putString("PowerAdapterConnected","Подключен адаптер питания");

        //HOW TO USE
        ee.putString("HowToUse","Как использовать");
        ee.putString("TheftA","Чтобы использовать сигнализацию воровства, подключите мобильное зарядное устройство и нажмите «Предупреждение о краже»,");
        ee.putString("TheftB","Затем вас попросят ввести пароль. Это одноразовая операция.");
        ee.putString("TheftC","После успешной настройки пароля активируется сигнализация о краже");
        ee.putString("TheftD","Теперь, если телефон отключен, раздастся звуковой сигнал и он остановится, введя правильный пароль");
        ee.putString("FullBatteryAlarmA","После того, как приложение установлено, активируется сигнал полной батареи.");
        ee.putString("FullBatteryAlarmB","Во время зарядки, как только аккумулятор полностью зарядится на 100%, в приложении будет звучать сигнал тревоги, который можно остановить, нажав кнопку убрать тревогу или отключив зарядное устройство");


        ee.putString("ActivateVibrationMode","Активировать режим вибрации");
        ee.putString("ChangeTempUnit","Изменить температурный блок");
        ee.putString("BatteryMonitor","Монитор батареи");

        ee.putString("Theme","тема");
        ee.putString("volume","объем");
        ee.putString("SelectFullBatteryLevel","Выберите уровень полной батареи");
        ee.putString("SelectCustomBatteryLevel","Выберите пользовательский уровень заряда батареи");
        ee.putString("SelectTemperatureWarningLevel","Выберите уровень предупреждения о температуре");

        ee.putString("ChangeTemperatureUnit","Изменить температурный блок");
        ee.putString("Celcius","Celsius");
        ee.putString("fahrenheit","Фаренгейт");
        ee.putString("cancel","Отмена");


        //Ringtone
        ee.putString("Ringtone","Мелодия");
        ee.putString("DefaultRingtone","Мелодии звонка по умолчанию");
        ee.putString("SetCustomRingtone","Настройка пользовательского рингтона");

        //Theft passcode..
        ee.putString("SetTheftPasscode","Установить пароль для кражи");
        ee.putString("PasscodeArea","Область паролей");
        ee.putString("Set","ЗАДАВАТЬ");
        ee.putString("EnterPasscode","Введите пароль");
        ee.putString("ConfirmTheftPasscode","Подтвердить пароль");
        ee.putString("Confirm","подтвердить");
        ee.putString("EnterOldPasscode","Введите старый пароль");
        ee.putString("OldPassword","Старый пароль");
        ee.putString("Change","+ Изменить");

        ee.commit();
    }
    public void SPANISH(){
        SharedPreferences.Editor ee=sharedPreference.edit();

        ee.putString("status","true");
        ee.putString("fullBatteryAlarm","Alarma de batería completa");

        ee.putString("health","Salud de la batería");
        ee.putString("volt","voltaje");
        ee.putString("temp","Temperatura");
        ee.putString("brightness","Brillo de la pantalla");
        ee.putString("flashLight","Antorcha");
        ee.putString("theftalarm","Alarma de robo");
        ee.putString("theftalarm_caps","Alarma de robo");

        ee.putString("stop","detener");
        ee.putString("start","comienzo");
        ee.putString("settings","Ajustes");


        //STATISTICS
        ee.putString("statistics","ESTADÍSTICA");
        ee.putString("batteryCapacity","Capacidad de la batería :");
        ee.putString("remainingBatteryCapacity","Capacidad restante :");
        ee.putString("batteryHealth","Salud de la batería :");
        ee.putString("batteryLevel","Nivel de bateria :");
        ee.putString("plugged","Atascado :");
        ee.putString("technology","Tecnología :");
        ee.putString("chargingStatus","Estado de carga :");
        ee.putString("voltage","voltaje :");
        ee.putString("temperature","Temperatura :");


        //DRAWER
        ee.putString("LowBatteryReminder","Recordatorio de batería baja");
        ee.putString("batterystatistics","Estadísticas de la batería");
        ee.putString("language","Idioma");
        ee.putString("alarmringtone","Alarma ringtone");
        ee.putString("navSettings","Ajustes");
        ee.putString("chooseColorTheme","Elegir el tema de color");
        ee.putString("share","Compartir");
        ee.putString("contact","Contáctenos");
        ee.putString("RateUs","Califique Nosotros");

        //SETTINGS
        ee.putString("RingAlarmOnSilentMode","Alarma de timbre en modo silencioso");
        ee.putString("AutoStartupOnReboot","Inicio automático al reiniciar");
        ee.putString("ActivateNotificationPanel","Activar panel de notificaciones");
        ee.putString("Sound","Volumen de sonido");
        ee.putString("ChangeTheftPasscode","Cambiar código de robo");
        ee.putString("AutoEnableTheftAlarm","Alarma de robo de habilitación automática");
        ee.putString("FullBatteryLevelTxt","Nivel de alarma de batería completa");
        ee.putString("LowBatteryLevelTxt","Notificación a nivel de batería personalizado");
        ee.putString("BatteryTemperatureWarninglevel","Ajuste del nivel de advertencia de la temperatura de la batería");

        ee.putString("PowerAdapterConnected","Adaptador de corriente conectado");

        //HOW TO USE
        ee.putString("HowToUse","Cómo utilizar");
        ee.putString("TheftA","Para usar la alarma de robo, conecte el cargador móvil y haga clic en \"Alarma de robo\".");
        ee.putString("TheftB","Se le pedirá que ingrese una contraseña. Esta es una actividad única.");
        ee.putString("TheftC","Después de configurar correctamente la contraseña, la alarma de robo se activará.");
        ee.putString("TheftD","Ahora, si el teléfono está desenchufado, suena una alarma y solo se detendrá insertando la contraseña correcta.");
        ee.putString("FullBatteryAlarmA","Una vez instalada la aplicación, se activa la alarma de batería completa.");
        ee.putString("FullBatteryAlarmB","Durante la carga, una vez que la batería esté completamente cargada al 100%, la aplicación emitirá una alarma que puede detenerse haciendo clic en el botón de alarma de la alarma o desconectando el cargador");


        ee.putString("ActivateVibrationMode","Activar el modo de vibración");
        ee.putString("ChangeTempUnit","Cambiar la unidad de temperatura");
        ee.putString("BatteryMonitor","Monitor de batería");

        ee.putString("Theme","Tema");
        ee.putString("volume","Volumen");
        ee.putString("SelectFullBatteryLevel","Seleccione el nivel de batería completo");
        ee.putString("SelectCustomBatteryLevel","Seleccione el nivel de batería personalizado");
        ee.putString("SelectTemperatureWarningLevel","Seleccione el nivel de advertencia de temperatura");

        ee.putString("ChangeTemperatureUnit","Cambiar la unidad de temperatura");
        ee.putString("Celcius","Celsius");
        ee.putString("fahrenheit","Fahrenheit");
        ee.putString("cancel","Cancelar");

        //Ringtone
        ee.putString("Ringtone","Tono de llamada");
        ee.putString("DefaultRingtone","Tonos de llamada predeterminados");
        ee.putString("SetCustomRingtone","Definir tono personalizado");

        //Theft passcode..
        ee.putString("SetTheftPasscode","Establecer contraseña de robo");
        ee.putString("PasscodeArea","Área de la contraseña");
        ee.putString("Set","CONJUNTO");
        ee.putString("EnterPasscode","Introducir la contraseña");
        ee.putString("ConfirmTheftPasscode","Confirmar contraseña de robo");
        ee.putString("Confirm","Confirmar");
        ee.putString("EnterOldPasscode","Introducir contraseña antigua");
        ee.putString("OldPassword","Contraseña anterior");
        ee.putString("Change","Cambio");


        ee.commit();
    }
    public void SWEDISH(){
        SharedPreferences.Editor ee=sharedPreference.edit();

        ee.putString("status","true");
        ee.putString("fullBatteryAlarm","Full batterilarm");

        ee.putString("health","Batterihälsa");
        ee.putString("volt","Spänning");
        ee.putString("temp","Temperatur");
        ee.putString("brightness","Skärmens ljusstyrka");
        ee.putString("flashLight","Fackla");
        ee.putString("theftalarm","Stöldlarm");
        ee.putString("theftalarm_caps","Stöldlarm");

        ee.putString("stop","Sluta");
        ee.putString("start","Start");
        ee.putString("settings","inställningar");


        //STATISTICS
        ee.putString("statistics","STATISTIK");
        ee.putString("batteryCapacity","Batterikapacitet :");
        ee.putString("remainingBatteryCapacity","Återstående kapacitet :");
        ee.putString("batteryHealth","Batterihälsa :");
        ee.putString("batteryLevel","Batteri nivå :");
        ee.putString("plugged","Pluggas :");
        ee.putString("technology","Teknologi :");
        ee.putString("chargingStatus","Laddningsstatus :");
        ee.putString("voltage","Spänning :");
        ee.putString("temperature","Temperatur :");


        //DRAWER
        ee.putString("LowBatteryReminder","Låg batteriladdare");
        ee.putString("batterystatistics","Batteristatistik");
        ee.putString("language","Språk");
        ee.putString("alarmringtone","Alarm ringsignal");
        ee.putString("navSettings","inställningar");
        ee.putString("chooseColorTheme","Välj färgtema");
        ee.putString("share","Dela med sig");
        ee.putString("contact","Kontakta oss");
        ee.putString("RateUs","Gradera oss");

        //SETTINGS
        ee.putString("RingAlarmOnSilentMode","Ringlarm i tyst läge");
        ee.putString("AutoStartupOnReboot","Automatisk start vid omstart");
        ee.putString("ActivateNotificationPanel","Aktivera anmälningspanelen");
        ee.putString("Sound","Ljudvolym");
        ee.putString("ChangeTheftPasscode","Ändra stöldskyddskoden");
        ee.putString("AutoEnableTheftAlarm","Automatiskt aktivera stöldlarm");
        ee.putString("FullBatteryLevelTxt","Full batterilarmnivå");
        ee.putString("LowBatteryLevelTxt","Anmälan vid anpassad batterinivå");
        ee.putString("BatteryTemperatureWarninglevel","Ange varningsnivå för batteritemperatur");

        ee.putString("PowerAdapterConnected","Strömadapter ansluten");

        //HOW TO USE
        ee.putString("HowToUse","Hur man använder");
        ee.putString("TheftA","För att använda stöldlarm, anslut mobilladdaren och klicka på \"Stöldlarm\".");
        ee.putString("TheftB","Du kommer då att bli ombedd att ange ett lösenord. Detta är en engångsaktivitet.");
        ee.putString("TheftC","Efter att du har konfigurerat lösenordet aktiveras stöldlarmet.");
        ee.putString("TheftD","Om telefonen är urkopplad hörs ett larm och stoppar bara genom att sätta in det korrekta lösenordet");
        ee.putString("FullBatteryAlarmA","När applikationen är installerad aktiveras full batterilarm.");
        ee.putString("FullBatteryAlarmB","Vid laddning, när batteriet är fulladdat till 100%, ljuder appen ett larm som kan stoppas genom att antingen klicka på larmknappen för avvisande eller genom att dra ur laddaren");

        ee.putString("ActivateVibrationMode","Aktivera vibrationsläge");
        ee.putString("ChangeTempUnit","Byt temperaturenhet");
        ee.putString("BatteryMonitor","Batteriövervakare");

        ee.putString("Theme","Tema");
        ee.putString("volume","Volym");
        ee.putString("SelectFullBatteryLevel","Välj Full batterinivå");
        ee.putString("SelectCustomBatteryLevel","Välj Anpassad batterinivå");
        ee.putString("SelectTemperatureWarningLevel","Välj temperaturvarningsnivå");

        ee.putString("ChangeTemperatureUnit","Byt Temperaturenhet");
        ee.putString("Celcius","Celsius");
        ee.putString("fahrenheit","fahrenheit");
        ee.putString("cancel","Annullera");

        //Ringtone
        ee.putString("Ringtone","ring~~POS=TRUNC");
        ee.putString("DefaultRingtone","Standard ringsignaler");
        ee.putString("SetCustomRingtone","Ange anpassad ringsignal");

        //Theft passcode..
        ee.putString("SetTheftPasscode","Ange stöldlösenord");
        ee.putString("PasscodeArea","Lösenordsområde");
        ee.putString("Set","UPPSÄTTNING");
        ee.putString("EnterPasscode","Skriv in lösenord");
        ee.putString("ConfirmTheftPasscode","Bekräfta stöldlösenordet");
        ee.putString("Confirm","Bekräfta");
        ee.putString("EnterOldPasscode","Ange gammalt lösenord");
        ee.putString("OldPassword","Gammalt lösenord");
        ee.putString("Change","Byta");

        ee.commit();
    }
    public void TURKISH(){
        SharedPreferences.Editor ee=sharedPreference.edit();

        ee.putString("status","true");
        ee.putString("fullBatteryAlarm","Tam Pil Alarmı");

        ee.putString("health","Pil sağlığ");
        ee.putString("volt","Pil seviyesi");
        ee.putString("temp","Sıcaklık");
        ee.putString("brightness","Ekran parlaklığı");
        ee.putString("flashLight","Meşale");
        ee.putString("theftalarm","Hırsızlık Alarmı");
        ee.putString("theftalarm_caps","Hırsızlık Alarmı");

        ee.putString("stop","Dur");
        ee.putString("start","Başlama");
        ee.putString("settings","Ayarlar");


        //STATISTICS
        ee.putString("statistics","İSTATİSTİK");
        ee.putString("batteryCapacity","Akü kapasitesi :");
        ee.putString("remainingBatteryCapacity","Kalan kapasite :");
        ee.putString("batteryHealth","Pil sağlığı :");
        ee.putString("batteryLevel","Pil seviyesi :");
        ee.putString("plugged","tıkalı :");
        ee.putString("technology","teknoloji :");
        ee.putString("chargingStatus","Şarj Durumu :");
        ee.putString("voltage","Voltaj :");
        ee.putString("temperature","Sıcaklık :");


        //DRAWER
        ee.putString("LowBatteryReminder","Zayıf pil uyarısı");
        ee.putString("batterystatistics","Akü İstatistikleri");
        ee.putString("language","Dil");
        ee.putString("alarmringtone","Alarm zil sesi");
        ee.putString("navSettings","Ayarlar");
        ee.putString("chooseColorTheme","Renk Teması'nı seçin");
        ee.putString("share","Pay");
        ee.putString("contact","Bizimle iletişime geçin");
        ee.putString("RateUs","Bizi değerlendirin");

        //SETTINGS
        ee.putString("RingAlarmOnSilentMode","Sessiz modda Zil Alarmı");
        ee.putString("AutoStartupOnReboot","Yeniden başlatma sırasında otomatik başlatma");
        ee.putString("ActivateNotificationPanel","Bildirim panelini etkinleştir");
        ee.putString("Sound","Ses seviyesi");
        ee.putString("ChangeTheftPasscode","Hırsızlık Şifre Kodunu Değiştir");
        ee.putString("AutoEnableTheftAlarm","Hırsızlık alarmını otomatik etkinleştir");
        ee.putString("FullBatteryLevelTxt","Tam Pil Alarm Seviyesi");
        ee.putString("LowBatteryLevelTxt","Özel Pil Seviyesinde Bildirim");
        ee.putString("BatteryTemperatureWarninglevel","Batarya Sıcaklığı Uyarı Seviyesini Ayarlayın");
        ee.putString("PowerAdapterConnected","Güç adaptörü bağlı");

        //HOW TO USE
        ee.putString("HowToUse","Nasıl kullanılır");
        ee.putString("TheftA","Hırsızlık alarmını kullanmak için mobil şarj cihazını bağlayın ve 'Hırsızlık Alarmı'");
        ee.putString("TheftB","Daha sonra bir şifre girmeniz istenecektir. Bu bir defalık bir etkinliktir");
        ee.putString("TheftC","Şifreyi başarıyla yapılandırdıktan sonra, hırsızlık alarmı etkinleştirilir");
        ee.putString("TheftD","Şimdi telefon prizden çekilirse, bir alarm duyulur ve yalnızca doğru şifre girilerek durdurulur");
        ee.putString("FullBatteryAlarmA","Uygulama kurulduktan sonra, tam pil alarmı etkinleştirilir.");
        ee.putString("FullBatteryAlarmB","Şarj olurken, batarya% 100 oranında tamamen şarj edildiğinde, uygulama, alarmı kapat düğmesini tıklayarak veya şarj cihazının fişini çekerek durdurulabilen bir alarm çalar");

        ee.putString("ActivateVibrationMode","Titreşim Modunu Etkinleştir");
        ee.putString("ChangeTempUnit","Sıcaklık Birimini Değiştir");
        ee.putString("BatteryMonitor","Pil Monitörü");

        ee.putString("Theme","Tema");
        ee.putString("volume","hacim");
        ee.putString("SelectFullBatteryLevel","Tam Pil Seviyesi'ni seçin");
        ee.putString("SelectCustomBatteryLevel","Özel Pil Seviyesi'ni seçin");
        ee.putString("SelectTemperatureWarningLevel","Sıcaklık Uyarı Seviyesi'ni seçin");

        ee.putString("ChangeTemperatureUnit","Sıcaklık Birimini Değiştir");
        ee.putString("Celcius","Selsius");
        ee.putString("fahrenheit","fahrenhayt");
        ee.putString("cancel","İptal etmek");


        //Ringtone
        ee.putString("Ringtone","Zil");
        ee.putString("DefaultRingtone","Varsayılan Zil Sesleri");
        ee.putString("SetCustomRingtone","Özel Zil Sesi Ayarla");

        //Theft passcode..
        ee.putString("SetTheftPasscode","Hırsızlık Şifreyi Ayarla");
        ee.putString("PasscodeArea","Şifre Alanı");
        ee.putString("Set","SET");
        ee.putString("EnterPasscode","Parolanı Gir");
        ee.putString("ConfirmTheftPasscode","Hırsızlık Şifreyi Onayla");
        ee.putString("Confirm","Onaylamak");
        ee.putString("EnterOldPasscode","Eski şifreyi gir");
        ee.putString("OldPassword","eski şifre");
        ee.putString("Change","Değişiklik");

        ee.commit();
    }
}
