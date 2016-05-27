package com.example.androidannotationtesttwo.util;

import android.app.Activity;
import android.util.DisplayMetrics;

public class BaseTools {
	
	/**
	 * 获取屏幕的宽
	 * @param activity
	 * @return
	 */
	public final static int getWindowsWidth(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}
}
