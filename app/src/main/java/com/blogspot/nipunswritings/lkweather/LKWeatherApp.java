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
