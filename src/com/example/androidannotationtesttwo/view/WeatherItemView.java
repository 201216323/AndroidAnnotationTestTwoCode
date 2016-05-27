
package com.example.androidannotationtesttwo.view;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.androidannotationtesttwo.R;
import com.example.androidannotationtesttwo.bean.WeatherModle;
import com.example.androidannotationtesttwo.initview.SlidingMenuView;

@EViewGroup(R.layout.item_weather)
public class WeatherItemView extends LinearLayout {

    @ViewById(R.id.week)
    protected TextView mWeek;
    @ViewById(R.id.weahter_image)
    protected ImageView mWeatherImage;
    @ViewById(R.id.temperature)
    protected TextView mTemperature;
    @ViewById(R.id.weather)
    protected TextView mWeather;
    @ViewById(R.id.wind)
    protected TextView mWind;

    public WeatherItemView(Context context) {
        super(context);
    }

    public void setData(WeatherModle weatherModle) {
        try {
            mWeek.setText("星" + weatherModle.getDate().split("日星")[1]);
            mTemperature.setText(weatherModle.getTemperature().replace("低温", "~").split("高温")[1]);
            mWeather.setText(weatherModle.getWeather());
            mWind.setText(weatherModle.getWind());
            SlidingMenuView.instance().setWeatherImage(mWeatherImage, weatherModle.getWeather());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
