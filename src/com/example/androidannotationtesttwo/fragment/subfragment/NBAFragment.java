
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
import com.example.androidannotationtesttwo.activity.DetailsActivity_;
import com.example.androidannotationtesttwo.activity.ImageDetailActivity_;
import com.example.androidannotationtesttwo.adapter.CardsAnimationAdapter;
import com.example.androidannotationtesttwo.adapter.NewAdapter;
import com.example.androidannotationtesttwo.bean.NewModle;
import com.example.androidannotationtesttwo.http.HttpUtil;
import com.example.androidannotationtesttwo.http.Url;
import com.example.androidannotationtesttwo.http.json.NewListJson;
import com.example.androidannotationtesttwo.initview.InitView;
import com.example.androidannotationtesttwo.util.StringUtils;
import com.example.androidannotationtesttwo.widget.swiptlistview.SwipeListView;
import com.example.androidannotationtesttwo.widget.viewimage.Animations.SliderLayout;
import com.example.androidannotationtesttwo.widget.viewimage.SliderTypes.BaseSliderView;
import com.example.androidannotationtesttwo.widget.viewimage.SliderTypes.BaseSliderView.OnSliderClickListener;
import com.nhaarman.listviewanimations.swinginadapters.AnimationAdapter;

@EFragment(R.layout.fragment_main)
public class NBAFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,
        OnSliderClickListener {
    protected SliderLayout mDemoSlider;
    @ViewById(R.id.swipe_container)
    protected SwipeRefreshLayout swipeLayout;
    @ViewById(R.id.listview)
    protected SwipeListView mListView;
    @ViewById(R.id.progressBar)
    protected ProgressBar mProgressBar;
    protected HashMap<String, String> url_maps;

    protected HashMap<String, NewModle> newHashMap;

    @Bean
    protected NewAdapter newAdapter;
    protected List<NewModle> listsModles;
    private int index = 0;
    private boolean isRefresh = false;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @AfterInject
    protected void init() {
        listsModles = new ArrayList<NewModle>();
        url_maps = new HashMap<String, String>();

        newHashMap = new HashMap<String, NewModle>();
    }

    @AfterViews
    protected void initView() {

        swipeLayout.setOnRefreshListener(this);
        InitView.instance().initSwipeRefreshLayout(swipeLayout);
        InitView.instance().initListView(mListView, getActivity());
        View headView = LayoutInflater.from(getActivity()).inflate(R.layout.head_item, null);
        mDemoSlider = (SliderLayout) headView.findViewById(R.id.slider);
        // mListView.addHeaderView(headView);
        AnimationAdapter animationAdapter = new CardsAnimationAdapter(newAdapter);
        animationAdapter.setAbsListView(mListView);
        mListView.setAdapter(animationAdapter);
        loadData(getCommonUrl(index + "", Url.NBAId));

        mListView.setOnBottomListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPagte++;
                index = index + 20;
                loadData(getCommonUrl(index + "", Url.NBAId));
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
            String result = getMyActivity().getCacheStr("NBAFragment" + currentPagte);
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
                loadData(getCommonUrl(0 + "", Url.NBAId));
                url_maps.clear();
                mDemoSlider.removeAllSliders();
            }
        }, 2000);
    }

    @ItemClick(R.id.listview)
    protected void onItemClick(int position) {
        NewModle newModle = listsModles.get(position);
        enterDetailActivity(newModle);
    }

    public void enterDetailActivity(NewModle newModle) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("newModle", newModle);
        Class<?> class1;
        if (newModle.getImagesModle() != null && newModle.getImagesModle().getImgList().size() > 1) {
            class1 = ImageDetailActivity_.class;
        } else {
            class1 = DetailsActivity_.class;
        }
        ((BaseActivity) getActivity()).openActivity(class1,
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
        getMyActivity().setCacheStr("NBAFragment" + currentPagte, result);
        if (isRefresh) {
            isRefresh = false;
            newAdapter.clear();
            listsModles.clear();
        }
        mProgressBar.setVisibility(View.GONE);
        swipeLayout.setRefreshing(false);

        List<NewModle> list = NewListJson.instance(getActivity()).readJsonNewModles(result,
                Url.NBAId);
        // if (index == 0) {
        // initSliderLayout(list);
        // } else {
        newAdapter.appendList(list);
        // }
        listsModles.addAll(list);
        mListView.onBottomComplete();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        NewModle newModle = newHashMap.get(slider.getUrl());
        enterDetailActivity(newModle);
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
