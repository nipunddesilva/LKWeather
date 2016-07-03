package com.blogspot.nipunswritings.lkweather.weather;

import android.content.Context;
import android.util.Log;

import com.blogspot.nipunswritings.lkweather.MainActivity;
import com.blogspot.nipunswritings.lkweather.database.WeatherForcastDataManager;
import com.blogspot.nipunswritings.lkweather.network.HttpClient;
import com.blogspot.nipunswritings.lkweather.network.HttpDataProcessor;
import com.blogspot.nipunswritings.lkweather.network.NetUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by nipun on 6/28/16.
 */
public class WeatherHttpDataProcessor implements HttpDataProcessor {

    public final String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    public final String WEATHER_API_KEY = "6f6391828fe1d9962d5f611844fd29d5";

    private Context mContext;

    private Map<String, String> mCurrentWeatherInfoMap;
    private ArrayList<Weather> mWeatherList;

    private boolean mDidRequestCurrentWeather;

    public WeatherHttpDataProcessor(Context context)
    {
        mContext = context;
        mDidRequestCurrentWeather = true;
    }

    public boolean getCurrentWeatherInfo(String city) {
        boolean isDone;

        mCurrentWeatherInfoMap = new HashMap<>();

        if (NetUtils.isConnectedToNetwork(mContext)) {
            String dataUrl = BASE_URL+"weather?id="+city+"&appid="+WEATHER_API_KEY;
            HttpClient client = new HttpClient(dataUrl, this);
            isDone = client.performGetRequest();
        } else {
            isDone = false;
        }

        return isDone;

    }

    public boolean getWeatherForcast (String city) {
        mDidRequestCurrentWeather = false;

        boolean isDone;
        mWeatherList = new ArrayList<>();

        if (NetUtils.isConnectedToNetwork(mContext)) {
            String dataUrl = BASE_URL+"forecast?id="+city+"&appid="+WEATHER_API_KEY;
            HttpClient client = new HttpClient(dataUrl, this);
            isDone = client.performGetRequest();
        } else {
            isDone = false;
        }

        return isDone;

    }

    @Override
    public boolean processReceivedData(InputStream is) {
        boolean isProcessed = false;
        String weatherDataStr;
        //Processing Current Weather
        weatherDataStr = currentWeatherDataString(is);
        if (weatherDataStr != null) {
            isProcessed = passCurrentWeatherJson(weatherDataStr);

        } else {
            isProcessed = false;
        }

        return isProcessed;
    }

    public String currentWeatherDataString (InputStream is) {
        String line;
        StringBuffer stringBuffer = new StringBuffer();

        InputStreamReader isReader = new InputStreamReader(is);
        BufferedReader reader = new BufferedReader(isReader);

        try {
            while ((line = reader.readLine()) != null) {
                stringBuffer.append(line);
            }
        } catch (IOException e) {
            return null;
        }

        return stringBuffer.toString();

    }

    public boolean passCurrentWeatherJson(String jsonString) {
        boolean isPassed = false;
        JSONObject jsonObject;
        try {
            if (mDidRequestCurrentWeather) {
                jsonObject = new JSONObject(jsonString);

                /*
                    JSONArray array = jsonObject.getJSONArray("weather");
                    JSONObject mainWeathArrayFirstObject = array.getJSONObject(0);
                    JSONObject mainObject = jsonObject.getJSONObject("main");
                    JSONObject windObject = jsonObject.getJSONObject("wind");
                 */

                Weather weather = jsonToWeatherObject(jsonObject);

                /*
                      mCurrentWeatherInfoMap.put(MainActivity.KEY_DESC, mainWeathArrayFirstObject.getString("description"));
                      mCurrentWeatherInfoMap.put(MainActivity.KEY_TEMP, mainObject.getString("temp"));
                      mCurrentWeatherInfoMap.put(MainActivity.KEY_PRES, mainObject.getString("pressure"));
                      mCurrentWeatherInfoMap.put(MainActivity.KEY_HUMI, mainObject.getString("humidity"));

                      mCurrentWeatherInfoMap.put(MainActivity.KEY_WIND_SPEED, windObject.getString("speed"));
                      mCurrentWeatherInfoMap.put(MainActivity.KEY_WIND_DEG, windObject.getString("deg"));
                 */

                if (weather != null) {
                    mCurrentWeatherInfoMap.put(MainActivity.KEY_DESC, weather.getDescription());

                    mCurrentWeatherInfoMap.put(MainActivity.KEY_TEMP, String.valueOf(weather.getTemp()));
                    mCurrentWeatherInfoMap.put(MainActivity.KEY_PRES, String.valueOf(Math.round(weather.getPressure())));
                    mCurrentWeatherInfoMap.put(MainActivity.KEY_HUMI, String.valueOf(Math.round(weather.getHumidity())));

                    mCurrentWeatherInfoMap.put(MainActivity.KEY_WIND_SPEED, String.valueOf(weather.getWindSpeed()));
                    mCurrentWeatherInfoMap.put(MainActivity.KEY_WIND_DEG, String.valueOf(weather.getWindDirec()));

                    isPassed = true;
                } else {
                    isPassed = false;
                }

            } else {
                jsonObject = new JSONObject(jsonString);
                JSONArray weatherObjectArray = jsonObject.getJSONArray("list");

                Weather weather;
                WeatherForcastDataManager manager = new WeatherForcastDataManager(mContext);
                manager.createWritableDb();
                manager.emptyTable();


                for (int i = 0; i < weatherObjectArray.length(); i++) {
                    weather = jsonToWeatherObject(weatherObjectArray.getJSONObject(i));

                    if (weather != null) {
                        weather = manager.insertNewForcastData(
                                weather.getTemp(),
                                weather.getDescription(),
                                weather.getHumidity(),
                                weather.getPressure(),
                                weather.getWindSpeed(),
                                weather.getWindDirec(),
                                weather.getTime()
                        );
                        if (weather == null) {
                            isPassed = false;
                            break;
                        }
                    } else {
                        isPassed = false;
                        break;
                    }
                    isPassed = true;
                }

                manager.closeDb();

            }

        } catch (JSONException e) {
            isPassed = false;
        }


        return isPassed;
    }

    private Weather jsonToWeatherObject(JSONObject jsonObject) {
        Weather weather;
        try {

            JSONArray array = jsonObject.getJSONArray("weather");
            JSONObject mainWeathArrayFirstObject = array.getJSONObject(0);
            JSONObject mainObject = jsonObject.getJSONObject("main");
            JSONObject windObject = jsonObject.getJSONObject("wind");


            float temp = Float.valueOf(mainObject.getString("temp"));
            String desc = mainWeathArrayFirstObject.getString("description");
            float pressure = Float.valueOf(mainObject.getString("pressure"));
            float humidity = Float.valueOf(mainObject.getString("humidity"));
            float windSpeed = Float.valueOf(windObject.getString("speed"));
            float windDeg = Float.valueOf(windObject.getString("deg"));
            long dt = 0;

            if (!mDidRequestCurrentWeather) {
                dt = WeatherUtil.convertTimeToLong(jsonObject.getString("dt_txt"));
                TimeZone tz = TimeZone.getTimeZone("Asia/Colombo");
                dt += tz.getRawOffset();
            }


            weather = new Weather(temp, desc, humidity, pressure, windSpeed, windDeg, dt);



        } catch (JSONException excep) {
            weather = null;
        } catch (NumberFormatException excep) {
            weather = null;
        }


        return weather;
    }

    public Map<String, String> getCurrentWeatherInfoMap() {
        return mCurrentWeatherInfoMap;
    }
}
