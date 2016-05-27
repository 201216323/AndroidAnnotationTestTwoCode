
package com.example.androidannotationtesttwo.activity;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.androidannotationtesttwo.R;
import com.example.androidannotationtesttwo.adapter.MyViewPagerAdapter;
import com.example.androidannotationtesttwo.adapter.WeatherAdapter;
import com.example.androidannotationtesttwo.bean.WeatherModle;
import com.example.androidannotationtesttwo.http.HttpUtil;
import com.example.androidannotationtesttwo.http.ResponseData;
import com.example.androidannotationtesttwo.http.VolleyUtils;
import com.example.androidannotationtesttwo.http.json.WeatherListJson;
import com.example.androidannotationtesttwo.initview.SlidingMenuView;
import com.example.androidannotationtesttwo.util.StringUtils;
import com.example.androidannotationtesttwo.util.TimeUtils;

@EActivity(R.layout.activity_weather)
public class WeatherActivity extends BaseActivity implements ResponseData {

    @ViewById(R.id.title)
    protected TextView mTitle;
    @ViewById(R.id.local)
    protected TextView mLocal;
    @ViewById(R.id.layout)   
    protected RelativeLayout mLayout;//activity_weather 这个布局
    @ViewById(R.id.weatherTemp)   
    protected TextView mWeatherTemp;//温度高温低温
    @ViewById(R.id.weather)
    protected TextView mWeather;    //晴
    @ViewById(R.id.wind)
    protected TextView mWind;       //南风
    @ViewById(R.id.weatherImage)
    protected ImageView mWeatherImage; //太阳   云
    @ViewById(R.id.week)   
    protected TextView mWeek; 	//星期五
    @ViewById(R.id.weather_date)
    protected TextView mWeatherDate;//2014年06月30日
    @ViewById(R.id.vPager)
    protected ViewPager mViewPager;//最近四天的天气

    private List<View> views;

    private View weatherGridView1, weatherGridView2;

    private GridView view1, view2;

    @Bean   //@Bean的标签每次都会创建一个实例,所以不能继承一个使用@EBean的类
    protected WeatherAdapter mWeatherAdapter1, mWeatherAdapter2;

    @AfterInject  //@AfterInject 定义的方法在类的构造方法执行后执行
    public void init() {
        views = new ArrayList<View>();
    }

    @AfterViews   //@AfterViews 定义的方法在setContentView后执行
    public void initView() {
        try {
            initViewPager();
            String titleName = getCacheStr("titleName");
            if (StringUtils.isEmpty(titleName)) {
                titleName = "北京";
            }
            mTitle.setText(titleName + "天气");
            mLocal.setVisibility(View.VISIBLE);
            setBack(titleName);
            loadData(getWeatherUrl(titleName));

            mWeatherDate.setText(TimeUtils.getCurrentTime());
//            showShortToast(""+TimeUtils.getCurrentTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化ViewPager 
     */
    private void initViewPager() {
        weatherGridView1 = LayoutInflater.from(this).inflate(R.layout.gridview_weather, null);
        weatherGridView2 = LayoutInflater.from(this).inflate(R.layout.gridview_weather, null);
        view1 = (GridView) weatherGridView1.findViewById(R.id.gridView);
        view2 = (GridView) weatherGridView2.findViewById(R.id.gridView);
        view1.setAdapter(mWeatherAdapter1);
        view2.setAdapter(mWeatherAdapter2);
        views.add(weatherGridView1);
        views.add(weatherGridView2);
        mViewPager.setOffscreenPageLimit(1);
        MyViewPagerAdapter mAdapetr = new MyViewPagerAdapter(views);
        mViewPager.setAdapter(mAdapetr);
        mViewPager.setCurrentItem(0);
    }

    /**
     * 根据不同的 cityName来给布局加载不同的背景图片
     * @param cityName
     */
    public void setBack(String cityName) {
        if (cityName.equals("北京")) {
            mLayout.setBackgroundResource(R.drawable.biz_plugin_weather_beijin_bg);
        } else if (cityName.equals("上海")) {
            mLayout.setBackgroundResource(R.drawable.biz_plugin_weather_shanghai_bg);
        } else if (cityName.equals("广州")) {
            mLayout.setBackgroundResource(R.drawable.biz_plugin_weather_guangzhou_bg);
        } else if (cityName.equals("深圳")) {
            mLayout.setBackgroundResource(R.drawable.biz_plugin_weather_shenzhen_bg);
        } else {
            mLayout.setBackgroundResource(R.drawable.biz_news_local_weather_bg_big);
        }
    }

    /**
     * 加载网络数据
     * @param url
     */
    private void loadData(String url) {
        if (hasNetWork()) {
            loadNewDetailData(url);
        } else {
            showShortToast(getString(R.string.not_network));
            String result = getCacheStr("WeatherActivity");
            if (!StringUtils.isEmpty(result)) {
                getResult(result);
            }
        }
    }

    @Background //@Background 定义的方法在后台线程执行
    public void loadNewDetailData(String url) {
        String result;
        try {
//             result = HttpUtil.getByHttpClient(this, url,null);
//             getResult(result);
            VolleyUtils.getVolleyData(url, this, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @UiThread  //更新后台UI的线程
    public void getResult(String result) {
        setCacheStr("WeatherActivity", result);
        List<WeatherModle> weatherModles = WeatherListJson.instance(this)
                .readJsonWeatherListModles(
                        result);
        if (weatherModles.size() > 0) {
            setWeather(weatherModles.get(0));
            mWeatherAdapter1.clear();
            mWeatherAdapter2.clear();
            mWeatherAdapter1.appendList(weatherModles.subList(1, 4));
            mWeatherAdapter2.appendList(weatherModles.subList(4, weatherModles.size()));
        } else {
            showShortToast("错误");
        }
    }

    public void setWeather(WeatherModle weatherModle) {
        mWeather.setText(weatherModle.getWeather());
        mWind.setText(weatherModle.getWind());
        mWeatherTemp.setText(weatherModle.getTemperature());
        mWeek.setText(weatherModle.getDate());
//        showShortToast("mWeek"+weatherModle.getDate());
        SlidingMenuView.instance().setWeatherImage(mWeatherImage, weatherModle.getWeather());
    }

    @Click(R.id.local)
    public void chooseCity(View view) {
        ChooseCityActivity_.intent(this).startForResult(REQUEST_CODE);
    }

    @OnActivityResult(REQUEST_CODE)
    void onResult(int resultCode, Intent data) {
        if (data != null) {
            String titleName = data.getStringExtra("cityname");
            setCacheStr("titleName", titleName);
            if (!"".equals(titleName)) {
                mTitle.setText(titleName + "天气");
                setBack(titleName);
                try {
                    loadData(getWeatherUrl(titleName));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
//        MobclickAgent.onPause(this);
    }

    //回调自己写的接口中的方法
    @Override
    public void getResponseData(int id, String result) {
        getResult(result);
    }

}
