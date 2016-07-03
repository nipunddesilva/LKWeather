package com.blogspot.nipunswritings.lkweather.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.blogspot.nipunswritings.lkweather.R;
import com.blogspot.nipunswritings.lkweather.weather.Weather;
import com.blogspot.nipunswritings.lkweather.weather.WeatherUtil;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Nipun on 7/2/2016.
 */
public class WeatherDataAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<Weather> mDataList;

    public WeatherDataAdapter(Context context, List<Weather> dataList) {
        mContext = context;
        mDataList = dataList;
        mLayoutInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Weather getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = mLayoutInflater.inflate(R.layout.weather_forcast_list_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.timeTv = (TextView) convertView.findViewById(R.id.wea_forc_item_time_tv);
            viewHolder.tempTv = (TextView) convertView.findViewById(R.id.wea_forc_item_temp_tv);
            viewHolder.descTv = (TextView) convertView.findViewById(R.id.wea_forc_item_desc_tv);
            viewHolder.huimidTv = (TextView) convertView.findViewById(R.id.wea_forc_item_humid_val_tv);
            viewHolder.pressTv = (TextView) convertView.findViewById(R.id.wea_forc_item_press_val_tv);
            viewHolder.windTv = (TextView) convertView.findViewById(R.id.wea_forc_item_wind_val_tv);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Weather weather = getItem(position);
        DecimalFormat format = new DecimalFormat("0.0");

        viewHolder.timeTv.setText(WeatherUtil.getTimeFromLong(weather.getTime()));
        viewHolder.tempTv.setText(String.valueOf(Math
                .round(WeatherUtil.kelvinToCelsiusTemp(weather.getTemp()))));
        viewHolder.descTv.setText(weather.getDescription());
        viewHolder.huimidTv.setText(": "+format.format(weather.getHumidity()));
        viewHolder.pressTv.setText(": "+format.format(weather.getPressure())+" hPa");


        String windSpeed = format.format(WeatherUtil.mdevsTokdevh(Float.valueOf(weather.getWindSpeed())));
        String windDirection = WeatherUtil.getCompassForDegree(Math.round(Float.valueOf(weather.getWindDirec())));

        viewHolder.windTv.setText(": "+windSpeed+" km/h from "+windDirection);


        return convertView;
    }

    private class ViewHolder {
        TextView timeTv;
        TextView tempTv;
        TextView descTv;
        TextView huimidTv;
        TextView pressTv;
        TextView windTv;
    }
}
