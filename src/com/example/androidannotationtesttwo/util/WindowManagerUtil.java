package com.example.androidannotationtesttwo.util;

import android.content.Context;
import android.view.WindowManager;

/**
 * @author Administrator
 * 
 */
public class WindowManagerUtil {
	private static WindowManagerUtil windowManagerUtil = null;
	private WindowManager wm = null;

	public WindowManagerUtil(Context context) {
		// TODO Auto-generated constructor stub
		wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
	}

	public static WindowManagerUtil getintence(Context context) {
		if (windowManagerUtil == null) {
			windowManagerUtil = new WindowManagerUtil(context);
		}
		return windowManagerUtil;
	}

	public int getScreenWidth() {
		return wm.getDefaultDisplay().getWidth();
	}

	public int getScreenheight() {
		return wm.getDefaultDisplay().getHeight();
	}
}
