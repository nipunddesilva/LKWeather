package com.blogspot.nipunswritings.lkweather.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.blogspot.nipunswritings.lkweather.fragment.CurrentWeatherFragment;
import com.blogspot.nipunswritings.lkweather.fragment.WeatherForecastFragment;

/**
 * Created by Nipun on 6/28/2016.
 */
public class WeatherPagerAdapter extends FragmentPagerAdapter {

    private CurrentWeatherFragment mCurrentWeatherFragment;
    private WeatherForecastFragment mWeatherForecastFragment;

    public WeatherPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                mCurrentWeatherFragment = new CurrentWeatherFragment();
                return mCurrentWeatherFragment;
            case 1:
                mWeatherForecastFragment = new WeatherForecastFragment();
                return mWeatherForecastFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Current";
            case 1:
                return "Forecast";
        }
        return null;
    }

    public WeatherForecastFragment getmWeatherForecastFragment() {
        return mWeatherForecastFragment;
    }

    public CurrentWeatherFragment getmCurrentWeatherFragment() {
        return mCurrentWeatherFragment;
    }
}
