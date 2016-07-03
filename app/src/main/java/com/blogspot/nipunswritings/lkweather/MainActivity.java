package com.blogspot.nipunswritings.lkweather;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.blogspot.nipunswritings.lkweather.adapter.WeatherPagerAdapter;
import com.blogspot.nipunswritings.lkweather.database.WeatherForcastDataManager;
import com.blogspot.nipunswritings.lkweather.fragment.CurrentWeatherFragment;
import com.blogspot.nipunswritings.lkweather.network.NetUtils;
import com.blogspot.nipunswritings.lkweather.weather.Weather;
import com.blogspot.nipunswritings.lkweather.weather.WeatherHttpDataProcessor;
import com.blogspot.nipunswritings.lkweather.weather.WeatherUtil;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_TEMP = "temp";
    public static final String KEY_DESC = "desc";
    public static final String KEY_HUMI = "humi";
    public static final String KEY_PRES = "pres";
    public static final String KEY_WIND_SPEED = "wind_spee";
    public static final String KEY_WIND_DEG = "wind_degr";

    private ViewPager mWeatherFragmentVp;
    private WeatherPagerAdapter mWeaPageadapter;
    private ProgressDialog mProgressDialog;

    private boolean gotCurrentWeatherData;
    private boolean gotWeatherForcastData;


    private Handler mCurrentWeatherHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 0){
                WeatherForcastDataManager manager = new WeatherForcastDataManager(MainActivity.this);
                manager.createWritableDb();
                List<Weather> list = manager.getAllForcastData();
                if (list != null) {
                    mWeaPageadapter.getmWeatherForecastFragment().updateWeatherForcast(list);
                }
                manager.closeDb();

            } else if (msg.what == 1) {
                Bundle data = msg.getData();
                String temp = data.getString(KEY_TEMP);
                String desc = data.getString(KEY_DESC);
                String humid = data.getString(KEY_HUMI);
                String pressure = data.getString(KEY_PRES);
                String windSpeed = data.getString(KEY_WIND_SPEED);
                String windDirection = data.getString(KEY_WIND_DEG);

                temp = String.valueOf(Math.round(WeatherUtil.kelvinToCelsiusTemp(Float.valueOf(temp))));

                DecimalFormat decimalFormat = new DecimalFormat("0.0");

                windSpeed = decimalFormat.format(WeatherUtil.mdevsTokdevh(Float.valueOf(windSpeed)));
                windDirection = WeatherUtil.getCompassForDegree(Math.round(Float.valueOf(windDirection)));


                CurrentWeatherFragment fragment = mWeaPageadapter.getmCurrentWeatherFragment();
                fragment.updateWeatherInfoUis(temp, desc, humid+" %", pressure+" hPa", windSpeed+" km/h, from "+windDirection);


            }


        }
    };

    private Runnable currentWeatherTask = new Runnable() {
        @Override
        public void run() {
            WeatherHttpDataProcessor dataProcessor = new WeatherHttpDataProcessor(MainActivity.this);
            LKWeatherApp app = LKWeatherApp.getSingleton();
            if (dataProcessor.getCurrentWeatherInfo(app.getCurrentLocationId())){

                Message message = mCurrentWeatherHandler.obtainMessage();
                Bundle weathetDateBundle = new Bundle();

                Map<String, String> currWeMap = dataProcessor.getCurrentWeatherInfoMap();

                weathetDateBundle.putString(KEY_TEMP, currWeMap.get(KEY_TEMP));
                weathetDateBundle.putString(KEY_DESC, currWeMap.get(KEY_DESC));
                weathetDateBundle.putString(KEY_HUMI, currWeMap.get(KEY_HUMI));
                weathetDateBundle.putString(KEY_PRES, currWeMap.get(KEY_PRES));
                weathetDateBundle.putString(KEY_WIND_DEG, currWeMap.get(KEY_WIND_DEG));
                weathetDateBundle.putString(KEY_WIND_SPEED, currWeMap.get(KEY_WIND_SPEED));

                message.setData(weathetDateBundle);

                message.what = 1;
                mCurrentWeatherHandler.sendMessage(message);

            } else {
                showToastOnUIThread("Error while downloading current weather !");
            }

            gotCurrentWeatherData = true;
            stopProgressDialogs();
        }
    };

    private Runnable forcastWeatherTask = new Runnable() {
        @Override
        public void run() {
            WeatherHttpDataProcessor dataProcessor = new WeatherHttpDataProcessor(MainActivity.this);


            if (dataProcessor.getWeatherForcast(LKWeatherApp.getSingleton().getCurrentLocationId())){
                mCurrentWeatherHandler.sendEmptyMessage(0);

            } else {
                showToastOnUIThread("Error while downloading weather forecast !");
            }

            gotWeatherForcastData = true;
            stopProgressDialogs();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressDialog = new ProgressDialog(MainActivity.this);
        mProgressDialog.setCancelable(true);
        mProgressDialog.setMessage("Running");
        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Toast.makeText(MainActivity.this, "Data might not be correct", Toast.LENGTH_LONG).show();
            }
        });


        mWeatherFragmentVp = (ViewPager) findViewById(R.id.act_main_weather_vp);

        mWeaPageadapter = new WeatherPagerAdapter(getSupportFragmentManager());
        mWeatherFragmentVp.setAdapter(mWeaPageadapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        if (sharedPreferences.getBoolean("pref_auto_download", true)){
            getNewWeatherData();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.activity_main_menu_refresh:
                getNewWeatherData();
                return true;
            case R.id.activity_main_menu_location:
                showCityList();
                return true;
            case R.id.activity_main_menu_setting:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void getNewWeatherData () {
        if(!NetUtils.isConnectedToNetwork(MainActivity.this)){
            Toast.makeText(MainActivity.this, "No Network Connection", Toast.LENGTH_LONG).show();
            return;
        }
        setTitle(LKWeatherApp.getSingleton().getCurrentLocationName());
        gotWeatherForcastData = false;
        gotCurrentWeatherData = false;
        mProgressDialog.show();
        (new Thread(currentWeatherTask)).start();
        (new Thread(forcastWeatherTask)).start();
    }

    private void stopProgressDialogs () {
        if (gotCurrentWeatherData && gotWeatherForcastData) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mProgressDialog.dismiss();
                }
            });
        }
    }

    private void showToastOnUIThread (final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }


    private void showCityList () {
        final String cities[] = LKWeatherApp.getSingleton().getCityNameIdMap().keySet().toArray(new String[0]);

        AlertDialog.Builder aBuilder = new AlertDialog.Builder(MainActivity.this);
        aBuilder.setItems(cities, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String key = cities[which];
                LKWeatherApp app = LKWeatherApp.getSingleton();
                app.setCurrentLocationId(String.valueOf(app.getCityNameIdMap().get(key)));
                app.setCurrentLocationName(key);
                getNewWeatherData();
            }
        });

        AlertDialog dialog = aBuilder.create();
        dialog.show();
    }
}
