
package com.example.androidannotationtesttwo.fragment.subfragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;

import com.example.androidannotationtesttwo.R;
import com.example.androidannotationtesttwo.activity.BaseActivity;
import com.example.androidannotationtesttwo.activity.PhotoDetailActivity_;
import com.example.androidannotationtesttwo.adapter.CardsAnimationAdapter;
import com.example.androidannotationtesttwo.adapter.PhotoAdapter;
import com.example.androidannotationtesttwo.bean.PhotoModle;
import com.example.androidannotationtesttwo.http.HttpUtil;
import com.example.androidannotationtesttwo.http.json.PhotoListJson;
import com.example.androidannotationtesttwo.initview.InitView;
import com.example.androidannotationtesttwo.util.PreferencesUtils;
import com.example.androidannotationtesttwo.util.StringUtils;
import com.example.androidannotationtesttwo.widget.swiptlistview.SwipeListView;
import com.example.androidannotationtesttwo.widget.viewimage.Animations.SliderLayout;
import com.example.androidannotationtesttwo.widget.viewimage.SliderTypes.BaseSliderView;
import com.example.androidannotationtesttwo.widget.viewimage.SliderTypes.BaseSliderView.OnSliderClickListener;
import com.nhaarman.listviewanimations.swinginadapters.AnimationAdapter;
/**
 * 图片新闻----趣图
 * @author hosa2015
 *
 */
@EFragment(R.layout.fragment_main)
public class TuPianDuJiaFragment extends BaseFragment implements
        SwipeRefreshLayout.OnRefreshListener,
        OnSliderClickListener {
    protected SliderLayout mDemoSlider;
    @ViewById(R.id.swipe_container)
    protected SwipeRefreshLayout swipeLayout;
    @ViewById(R.id.listview)
    protected SwipeListView mListView;
    @ViewById(R.id.progressBar)
    protected ProgressBar mProgressBar;
    protected HashMap<String, String> url_maps;

    protected HashMap<String, PhotoModle> newHashMap;

    public int indexId;

    public int index;

    public int count = 0;

    public long lastupdatetimedujia;

    @Bean
    protected PhotoAdapter photoAdapter;
    protected List<PhotoModle> listsModles;
    private boolean isRefresh = false;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @AfterInject
    protected void init() {
        listsModles = new ArrayList<PhotoModle>();
        url_maps = new HashMap<String, String>();
        newHashMap = new HashMap<String, PhotoModle>();

        int count = PreferencesUtils.getInt(getActivity(), "indexdujia");
        lastupdatetimedujia = PreferencesUtils.getLong(getActivity(), "lastupdatetimedujia");
        if (count != -1) {
            if ((lastupdatetimedujia + (24 * 60 * 60 * 1000)) < System.currentTimeMillis()) {
                lastupdatetimedujia = System.currentTimeMillis();
                PreferencesUtils.putLong(getActivity(), "lastupdatetimedujia", lastupdatetimedujia);
                int beishu = (int) (System.currentTimeMillis() / (lastupdatetimedujia + (24 * 60 * 60 * 1000)));
                count = (count + 10) * beishu;
            } else {
                indexId = count;
            }
        } else {
            indexId = 42011;
            PreferencesUtils.putLong(getActivity(), "lastupdatetimedujia",
                    System.currentTimeMillis());
            PreferencesUtils.putInt(getActivity(), "indexdujia", indexId);
        }

        index = indexId;
    }

    @AfterViews
    protected void initView() {
        swipeLayout.setOnRefreshListener(this);
        InitView.instance().initSwipeRefreshLayout(swipeLayout);
        InitView.instance().initListView(mListView, getActivity());
        View headView = LayoutInflater.from(getActivity()).inflate(R.layout.head_item, null);
        mDemoSlider = (SliderLayout) headView.findViewById(R.id.slider);
        // mListView.addHeaderView(headView);
        AnimationAdapter animationAdapter = new CardsAnimationAdapter(photoAdapter);
        animationAdapter.setAbsListView(mListView);
        mListView.setAdapter(animationAdapter);
        System.out.println(getDuJiaPicsUrl(indexId + ""));
        loadData(getDuJiaPicsUrl(indexId + ""));

        mListView.setOnBottomListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPagte++;
                indexId = indexId - 10;
                loadData(getDuJiaPicsUrl(indexId + ""));
            }
        });
    }

    private void loadData(String url) {
        if (getMyActivity().hasNetWork()) {
            loadNewList(url);
        } else {
            mListView.onBottomComplete();
            mProgressBar.setVisibility(View.GONE);
            getMyActivity().showShortToast(getString(R.string.not_network));
            String result = getMyActivity().getCacheStr("TuPianDuJiaFragment" + currentPagte);
            if (!StringUtils.isEmpty(result)) {
                getResult(result);
            }
        }
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                currentPagte = 1;
                isRefresh = true;
                index = index + 5;
                loadData(getDuJiaPicsUrl(index + ""));
                url_maps.clear();
                mDemoSlider.removeAllSliders();
                PreferencesUtils.putInt(getActivity(), "indexdujia", index);
            }
        }, 2000);
    }

    @ItemClick(R.id.listview)
    protected void onItemClick(int position) {
        PhotoModle photoModle = listsModles.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("photoUrl", photoModle.getSeturl());
        ((BaseActivity) getActivity()).openActivity(PhotoDetailActivity_.class,
                bundle, 0);
    }

    @Background
    void loadNewList(String url) {
        String result;
        try {
            result = HttpUtil.getByHttpClient(getActivity(), url);
            getResult(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @UiThread
    public void getResult(String result) {
        getMyActivity().setCacheStr("TuPianDuJiaFragment" + currentPagte, result);
        if (isRefresh) {
            isRefresh = false;
            photoAdapter.clear();
            listsModles.clear();
        }
        mProgressBar.setVisibility(View.GONE);
        swipeLayout.setRefreshing(false);
        List<PhotoModle> list = PhotoListJson.instance(getActivity()).readJsonPhotoListModles(
                result);
        // if (count == 0) {
        // initSliderLayout(list);
        // } else {
        photoAdapter.appendList(list);
        // }
        listsModles.addAll(list);
        mListView.onBottomComplete();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        // enterDetailActivity(newModle);
    }

    @Override
    public void onResume() {
        super.onResume();
//        MobclickAgent.onPageStart("MainScreen"); // 统计页面
    }

    @Override
    public void onPause() {
        super.onPause();
//        MobclickAgent.onPageEnd("MainScreen");
    }
}
