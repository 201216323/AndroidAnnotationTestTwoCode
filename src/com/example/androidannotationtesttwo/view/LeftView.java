
package com.example.androidannotationtesttwo.view;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.example.androidannotationtesttwo.R;
import com.example.androidannotationtesttwo.activity.BaseActivity;
import com.example.androidannotationtesttwo.activity.MessageActivity_;
import com.example.androidannotationtesttwo.activity.MoreActivity_;
import com.example.androidannotationtesttwo.activity.TuPianSinaActivity_;
import com.example.androidannotationtesttwo.activity.VideoActivity_;
import com.example.androidannotationtesttwo.activity.WeatherActivity_;
import com.example.androidannotationtesttwo.initview.SlidingMenuView;

@EViewGroup(R.layout.activity_left)
public class LeftView extends LinearLayout {

    private final BaseActivity context;

    public LeftView(Context context) {
        super(context);
        this.context = (BaseActivity) context;
    }

    @Click(R.id.pics)
    public void enterPics(View view) {
        context.openActivity(TuPianSinaActivity_.class);//打开图片新闻
        isShow();
    }

    @Click(R.id.video)
    public void enterVideo(View view) {
        context.openActivity(VideoActivity_.class);//打开视频新闻
        isShow();
    }

    @Click(R.id.ties)
    public void enterMessage(View view) {
        context.openActivity(MessageActivity_.class);
        isShow();
    }

    @Click(R.id.tianqi)
    public void enterTianQi(View view) {
        context.openActivity(WeatherActivity_.class);
        isShow();
    }

    @Click(R.id.more)
    public void enterMore(View view) {
        context.openActivity(MoreActivity_.class);
        isShow();
    }

    public void isShow() {
        if (SlidingMenuView.instance().slidingMenu.isMenuShowing()) {
            SlidingMenuView.instance().slidingMenu.showContent();
        }
    }

}
