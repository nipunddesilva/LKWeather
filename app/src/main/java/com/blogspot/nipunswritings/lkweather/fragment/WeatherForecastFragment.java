package com.blogspot.nipunswritings.lkweather.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.blogspot.nipunswritings.lkweather.R;
import com.blogspot.nipunswritings.lkweather.adapter.WeatherDataAdapter;
import com.blogspot.nipunswritings.lkweather.weather.Weather;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherForecastFragment extends Fragment {

    private ListView mForcastedWeatherList;

    public WeatherForecastFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View weatherForcastView = inflater.inflate(R.layout.fragment_weather_forecast, container, false);
        mForcastedWeatherList = (ListView) weatherForcastView.findViewById(R.id.frag_wea_forc_data_lv);
        return weatherForcastView;
    }

    public void updateWeatherForcast (List<Weather> newWeaList) {
        mForcastedWeatherList.setAdapter(new WeatherDataAdapter(getActivity(), newWeaList));
    }

}
