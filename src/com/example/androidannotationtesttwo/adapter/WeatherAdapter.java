
package com.example.androidannotationtesttwo.adapter;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.androidannotationtesttwo.bean.WeatherModle;
import com.example.androidannotationtesttwo.view.WeatherItemView;
import com.example.androidannotationtesttwo.view.WeatherItemView_;

@EBean  //@EBean 这个普通只能有一个构造函数，这个构造函数要不是没有参数，或者只有一个Context的参数
public class WeatherAdapter extends BaseAdapter {

    public List<WeatherModle> lists = new ArrayList<WeatherModle>();

    @RootContext
    Context context;

    public void appendList(List<WeatherModle> list) {
        if (!lists.containsAll(list) && list != null && list.size() > 0) {
            lists.addAll(list);
        }
        notifyDataSetChanged();
    }

    public void clear() {
        lists.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        WeatherItemView weatherItemView;
        if (convertView == null) {
            weatherItemView = WeatherItemView_.build(context);
        } else {
            weatherItemView = (WeatherItemView) convertView;
        }

        weatherItemView.setData(lists.get(position));

        return weatherItemView;
    }

}
