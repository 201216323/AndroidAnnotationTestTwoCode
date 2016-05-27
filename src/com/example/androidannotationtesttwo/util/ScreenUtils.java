
package com.example.androidannotationtesttwo.util;

import android.content.Context;
import android.util.DisplayMetrics;

public class ScreenUtils {
	/**
	 *  dp转pix
	 * @param context
	 * @param dp
	 * @return
	 */
    public static int convertDpToPixel(Context context, float dp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return (int) px;
    }
}
