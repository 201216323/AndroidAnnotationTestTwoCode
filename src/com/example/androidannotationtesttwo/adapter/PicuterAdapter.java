
package com.example.androidannotationtesttwo.adapter;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.androidannotationtesttwo.bean.PicuterModle;
import com.example.androidannotationtesttwo.view.PhotoItemView;
import com.example.androidannotationtesttwo.view.PhotoItemView_;
/**
 * 图片新闻中用到的图片adapter
 * @author hosa2015
 *
 */
@EBean
public class PicuterAdapter extends BaseAdapter {
    public List<PicuterModle> lists = new ArrayList<PicuterModle>();

    public void appendList(List<PicuterModle> list) {
        if (!lists.containsAll(list) && list != null && list.size() > 0) {
            lists.addAll(list);
        }
        notifyDataSetChanged();
    }

    @RootContext
    Context context;

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

        PhotoItemView photoItemView;

        if (convertView == null) {
            photoItemView = PhotoItemView_.build(context);
        } else {
            photoItemView = (PhotoItemView) convertView;
        }

        PicuterModle picuterModle = lists.get(position);

        photoItemView.setData(picuterModle.getTitle(), picuterModle.getPic());

        return photoItemView;
    }
}
