
package com.example.androidannotationtesttwo.adapter;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.EBean.Scope;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.androidannotationtesttwo.bean.NewModle;
import com.example.androidannotationtesttwo.view.NewItemView;
import com.example.androidannotationtesttwo.view.NewItemView_;

@EBean //注：这个普通只能有一个构造函数，这个构造函数要不是没有参数，或者只有一个Context的参数
public class NewAdapter extends BaseAdapter {
    public List<NewModle> lists = new ArrayList<NewModle>();

    private String currentItem;

    public void appendList(List<NewModle> list) {
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

    public void currentItem(String item) {
        this.currentItem = item;
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

        NewItemView newItemView;

        if (convertView == null) {
            newItemView = NewItemView_.build(context);
        } else {
            newItemView = (NewItemView) convertView;
        }

        NewModle newModle = lists.get(position);
        if (newModle.getImagesModle() == null) {
            newItemView.setTexts(newModle.getTitle(), newModle.getDigest(),
                    newModle.getImgsrc(), currentItem);
        } else {
            newItemView.setImages(newModle);
        }

        return newItemView;
    }
}
