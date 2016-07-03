package com.blogspot.nipunswritings.lkweather;

import android.app.Application;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nipun on 7/3/2016.
 */
public class LKWeatherApp extends Application {
    private static LKWeatherApp singleton;

    private String currentLocationId = "1246007";
    private String currentLocationName = "Gampaha";
    private Map<String, Long> cityNameIdMap;

    public static LKWeatherApp getSingleton () {
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        cityNameIdMap = new HashMap<>();
        cityNameIdMap.put("Ampara", 1251459L);
        cityNameIdMap.put("Colombo", 1248991L);
        cityNameIdMap.put("Gampaha", 1246007L);
        cityNameIdMap.put("Anuradhapura", 1251081L);
        cityNameIdMap.put("Galle", 1246294L);
        cityNameIdMap.put("Hambantota", 1244926L);
        cityNameIdMap.put("Jaffna", 1242833L);
        cityNameIdMap.put("Kalutara", 1241964L);
        cityNameIdMap.put("Kandy", 1241622L);
        cityNameIdMap.put("Kurunegala", 1237980L);
        cityNameIdMap.put("Matara", 1235846L);

        singleton = this;
    }

    public Map<String, Long> getCityNameIdMap() {
        return cityNameIdMap;
    }

    public String getCurrentLocationId() {
        return currentLocationId;
    }

    public void setCurrentLocationId(String currentLocationId) {
        this.currentLocationId = currentLocationId;
    }

    public void setCurrentLocationName(String currentLocationName) {
        this.currentLocationName = currentLocationName;
    }

    public String getCurrentLocationName() {
        return currentLocationName;
    }
}
