package com.replaycreation.application.graph;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.replaycreation.application.R;
import com.replaycreation.application.database.PowerEventsTable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ChargingOrDischargingGraph extends AppCompatActivity {

    RecyclerView Rv;
    PowerEventsTable graphTable;
    String totalCapacity;
    SharedPreferences sharedPreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SetColorTheme();
        setContentView(R.layout.activity_charging_or_discharging_graph);

        sharedPreference = getSharedPreferences("SharedPreference", 0);
        String str_title = sharedPreference.getString("fullBatteryAlarm", "");
        if (!str_title.equals(""))
            getSupportActionBar().setTitle(str_title);

        totalCapacity=getIntent().getStringExtra("totalCapacity");
        graphTable=new PowerEventsTable(getApplicationContext());
        Rv=(RecyclerView)findViewById(R.id.rvChargingOrDischarging);
        ArrayList<HashMap<String, String>> fetchAllFeeds = graphTable.AllData();
        GraphAdapter obj = new GraphAdapter(getApplicationContext(),fetchAllFeeds);
        Rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        obj.notifyDataSetChanged();
        Rv.setAdapter(obj);
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

    private class GraphAdapter extends RecyclerView.Adapter<RecyclerViewHolder>{
        Context ctx;
        ArrayList<HashMap<String, String>> aList;

        public GraphAdapter(Context applicationContext, ArrayList<HashMap<String, String>> al) {
            this.ctx=applicationContext;
            this.aList=al;
        }

        @Override
        public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.graph_item_layout, parent, false);
            return new RecyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerViewHolder holder, int position) {
            final HashMap<String, String> map = aList.get(position);


            //String columnId =map.get("columnId");
            String initialPercent =map.get("initialPercent");
            String finalPercent =map.get("finalPercent");
            //String duration =map.get("duration");
            String date =map.get("date");
            String ConnectTime =map.get("connectTime");
            String diconnectTime =map.get("diconnectTime");
            String isCharging =map.get("isCharging");

            int ChargedPercentage=0,charged_mAh=0;
            String DurationTime="00";
            try{
                ChargedPercentage=Integer.parseInt(finalPercent)-Integer.parseInt(initialPercent);
                charged_mAh=(Integer.parseInt(totalCapacity)/100)*ChargedPercentage;
                DurationTime=Difference(ConnectTime,diconnectTime);
                //new AlertDialog.Builder(ChargingOrDischargingGraph.this).setMessage(ConnectTime+"\n"+diconnectTime+"\n"+DurationTime).show();
            }catch (Exception e){}



            try {
                if (Integer.parseInt(initialPercent)<Integer.parseInt(finalPercent)){
                    holder.TxtStatusNDuration.setText("Plugged for "+DurationTime);
                    holder.howPercentageChared.setTextColor(Color.parseColor("#4caf50"));
                    holder.v.setBackgroundColor(Color.parseColor("#4caf50"));
                    holder.howPercentageChared.setText("+"+String.valueOf(ChargedPercentage)+"%");
                    holder.Capacity.setText("+"+String.valueOf(charged_mAh)+"mAh");
                }if (Integer.parseInt(initialPercent)>Integer.parseInt(finalPercent)){
                    holder.TxtStatusNDuration.setText("Used for "+DurationTime);
                    holder.howPercentageChared.setTextColor(Color.RED);
                    holder.v.setBackgroundColor(Color.RED);
                    holder.howPercentageChared.setText(String.valueOf(ChargedPercentage)+"%");
                    holder.Capacity.setText(String.valueOf(charged_mAh)+"mAh");
                }
                holder.TxtIniNfinal.setText(initialPercent+"% to "+finalPercent+"%");
                holder.TxtTime.setText(date+","+ConnectTime);
            }catch (Exception e){}


        }

        @Override
        public int getItemCount() {
            return aList.size();
        }
    }


    private class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView TxtStatusNDuration,TxtIniNfinal,TxtTime,howPercentageChared,Capacity;
        View v;
        public RecyclerViewHolder(View view) {
            super(view);
            TxtStatusNDuration= (TextView) view.findViewById(R.id.TxtStatusDuration);
            TxtIniNfinal= (TextView) view.findViewById(R.id.howPercentage);
            TxtTime= (TextView) view.findViewById(R.id.TxtTiming);
            howPercentageChared= (TextView) view.findViewById(R.id.txtChargedPercentage);
            Capacity= (TextView) view.findViewById(R.id.Capacity);
            v= (View) view.findViewById(R.id.view);

        }
    }

    public String Difference(String strChargingConnectTime, String strChargingDisconnetTime){
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        Date dateOne = null;
        String duration = "0m";
        try {
            dateOne = df.parse(strChargingDisconnetTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date dateTwo = null;
        try {
            dateTwo = df.parse(strChargingConnectTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            long timeDiff = Math.abs(dateOne.getTime() - dateTwo.getTime());
            int Hours = (int) (timeDiff/(1000 * 60 * 60));
            int Mins = (int) (timeDiff/(1000*60)) % 60;
            long Secs = (int) (timeDiff / 1000) % 60;

            String diff = Hours + ":" + Mins + ":" + Secs;
            if (Hours!=0){
                duration = Hours + "h " + Mins+" m";
            }else if(Mins!=0){
                duration = Mins+" m";
            }
        }catch (Exception e){
        }
        return duration;
    }
}
