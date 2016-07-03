package com.blogspot.nipunswritings.lkweather.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blogspot.nipunswritings.lkweather.R;


public class CurrentWeatherFragment extends Fragment {

    private TextView mCurrentTempTv;
    private TextView mCurrentWeathDescTv;
    private TextView mCurrentHumidTv;
    private TextView mCurrentPressTv;
    private TextView mCurrentWindTv;


    public CurrentWeatherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_current_weather, container, false);

        mCurrentTempTv = (TextView) view.findViewById(R.id.frag_curr_weath_temp_tv);
        mCurrentWeathDescTv = (TextView) view.findViewById(R.id.frag_curr_weath_desc_tv);
        mCurrentHumidTv = (TextView) view.findViewById(R.id.frag_curr_humid_val_tv);
        mCurrentPressTv = (TextView) view.findViewById(R.id.frag_curr_press_val_tv);
        mCurrentWindTv = (TextView) view.findViewById(R.id.frag_curr_wind_val_tv);

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void updateWeatherInfoUis(String temp, String desc, String humid,
                                     String press, String wind) {
        mCurrentTempTv.setText(temp);
        mCurrentWeathDescTv.setText(desc);
        mCurrentHumidTv.setText(": "+humid);
        mCurrentPressTv.setText(": "+press);
        mCurrentWindTv.setText(": "+wind);
    }
}
