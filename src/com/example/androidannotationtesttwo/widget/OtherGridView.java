package com.example.androidannotationtesttwo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;
/**
 * 自定义的不可拖动的GridView
 * @author hosa2015
 *
 */
public class OtherGridView extends GridView {

	public OtherGridView(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
