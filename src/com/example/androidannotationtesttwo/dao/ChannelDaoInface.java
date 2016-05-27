
package com.example.androidannotationtesttwo.dao;

import android.content.ContentValues;

import java.util.List;
import java.util.Map;

import com.example.androidannotationtesttwo.bean.ChannelItem;
/**
 * 自定义的ChannelDao接口包含六个方法
 * 第一  addCache
 * 第二  deleteCache
 * 第三  updateCache
 * 第四  viewCache
 * 第五  listCache
 * 第六 clearFeedTable
 * @author hosa2015
 *
 */
public interface ChannelDaoInface {
    public boolean addCache(ChannelItem item);

    public boolean deleteCache(String whereClause, String[] whereArgs);

    public boolean updateCache(ContentValues values, String whereClause,
            String[] whereArgs);

    public Map<String, String> viewCache(String selection,
            String[] selectionArgs);

    public List<Map<String, String>> listCache(String selection,
            String[] selectionArgs);

    public void clearFeedTable();
}
