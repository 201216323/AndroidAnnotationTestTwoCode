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
import android.widget.Toast;

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
import com.example.androidannotationtesttwo.widget.viewimage.Animations.ChildAnimationExample;
import com.example.androidannotationtesttwo.widget.viewimage.Animations.DescriptionAnimation;
import com.example.androidannotationtesttwo.widget.viewimage.Animations.SliderLayout;
import com.example.androidannotationtesttwo.widget.viewimage.SliderTypes.BaseSliderView;
import com.example.androidannotationtesttwo.widget.viewimage.SliderTypes.BaseSliderView.OnSliderClickListener;
import com.example.androidannotationtesttwo.widget.viewimage.SliderTypes.TextSliderView;
import com.nhaarman.listviewanimations.swinginadapters.AnimationAdapter;

@EFragment(R.layout.fragment_main)
public class NewsFragment extends BaseFragment implements
		SwipeRefreshLayout.OnRefreshListener, OnSliderClickListener {
	protected SliderLayout mDemoSlider;
	@ViewById(R.id.swipe_container)  //
	protected SwipeRefreshLayout swipeLayout;
	@ViewById(R.id.listview)  //swipeLayout里面的listview
	protected SwipeListView mListView;
	@ViewById(R.id.progressBar)//加载的进度条
	protected ProgressBar mProgressBar;
	protected HashMap<String, String> url_maps;
	protected HashMap<String, NewModle> newHashMap;
	@Bean 
	protected NewAdapter newAdapter;//@Bean的标签每次都会创建一个实例,所以不能继承一个使用@EBean的类
	protected List<NewModle> listsModles;
	private int index = 0;
	private boolean isRefresh = false;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@AfterInject //@AfterInject 定义的方法在类的构造方法执行后执行
	protected void init() {

		listsModles = new ArrayList<NewModle>();
		url_maps = new HashMap<String, String>();

		newHashMap = new HashMap<String, NewModle>();
	}

	@AfterViews //@AfterViews 定义的方法在setContentView后执行
	protected void initView() {
		swipeLayout.setOnRefreshListener(this);
		InitView.instance().initSwipeRefreshLayout(swipeLayout);//初始化SwipeRefreshLayout
		InitView.instance().initListView(mListView, getActivity());//初始化SwipeListView
		View headView = LayoutInflater.from(getActivity()).inflate(
				R.layout.head_item, null);
		mDemoSlider = (SliderLayout) headView.findViewById(R.id.slider);//headView中的SliderLayout
		mListView.addHeaderView(headView);
		AnimationAdapter animationAdapter = new CardsAnimationAdapter(newAdapter);//mListView的动画
		animationAdapter.setAbsListView(mListView);//mListView的动画
		mListView.setAdapter(animationAdapter);
		loadData(getNewUrl(index + ""));

		mListView.setOnBottomListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				currentPagte++;
				index = index + 20;
				loadData(getNewUrl(index + ""));
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
			String result = getMyActivity().getCacheStr(
					"NewsFragment" + currentPagte);
			if (!StringUtils.isEmpty(result)) {
				getResult(result);
			}
		}
	}

	private void initSliderLayout(List<NewModle> newModles) {

		if (!isNullString(newModles.get(0).getImgsrc()))
			newHashMap.put(newModles.get(0).getImgsrc(), newModles.get(0));
		if (!isNullString(newModles.get(1).getImgsrc()))
			newHashMap.put(newModles.get(1).getImgsrc(), newModles.get(1));
		if (!isNullString(newModles.get(2).getImgsrc()))
			newHashMap.put(newModles.get(2).getImgsrc(), newModles.get(2));
		if (!isNullString(newModles.get(3).getImgsrc()))
			newHashMap.put(newModles.get(3).getImgsrc(), newModles.get(3));

		if (!isNullString(newModles.get(0).getImgsrc()))
			url_maps.put(newModles.get(0).getTitle(), newModles.get(0)
					.getImgsrc());
		if (!isNullString(newModles.get(1).getImgsrc()))
			url_maps.put(newModles.get(1).getTitle(), newModles.get(1)
					.getImgsrc());
		if (!isNullString(newModles.get(2).getImgsrc()))
			url_maps.put(newModles.get(2).getTitle(), newModles.get(2)
					.getImgsrc());
		if (!isNullString(newModles.get(3).getImgsrc()))
			url_maps.put(newModles.get(3).getTitle(), newModles.get(3)
					.getImgsrc());

		for (String name : url_maps.keySet()) {
			TextSliderView textSliderView = new TextSliderView(getActivity());
			textSliderView.setOnSliderClickListener(this);
			textSliderView.description(name).image(url_maps.get(name));

			textSliderView.getBundle().putString("extra", name);
			mDemoSlider.addSlider(textSliderView);
		}

		mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Background2Foreground);//设置图片切换时候的特效
		mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);//设置点 在什么位置
		mDemoSlider.setCustomAnimation(new DescriptionAnimation());
		newAdapter.appendList(newModles);
	}

	@Override
	public void onRefresh() {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				currentPagte = 1;
				isRefresh = true;
				loadData(getNewUrl("0"));
				url_maps.clear();
				mDemoSlider.removeAllSliders();
				
			}
		}, 1000);
	}

	@ItemClick(R.id.listview)
	protected void onItemClick(int position) {//listview上的点击事件
		NewModle newModle = listsModles.get(position - 1);
		enterDetailActivity(newModle);
	}

	public void enterDetailActivity(NewModle newModle) {
		Bundle bundle = new Bundle();
		bundle.putSerializable("newModle", newModle);
		Class<?> class1;
		if (newModle.getImagesModle() != null
				&& newModle.getImagesModle().getImgList().size() > 1) {
			class1 = ImageDetailActivity_.class;
		} else {
			class1 = DetailsActivity_.class;
		}
		((BaseActivity) getActivity()).openActivity(class1, bundle, 0);

		// Intent intent = new Intent(getActivity(), class1);
		// intent.putExtras(bundle);
		// IntentUtils.startPreviewActivity(getActivity(), intent);
	}

	@Background //@Background 定义的方法在后台线程执行
	void loadNewList(String url) {
		String result;
		try {
			result = HttpUtil.getByHttpClient(getActivity(), url);
			if (!StringUtils.isEmpty(result)) {
				getResult(result);
			} else {
				swipeLayout.setRefreshing(false);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@UiThread
	public void getResult(String result) {
		getMyActivity().setCacheStr("NewsFragment" + currentPagte, result);
		if (isRefresh) {
			isRefresh = false;
			newAdapter.clear();
			listsModles.clear();
		}
		mProgressBar.setVisibility(View.GONE);
		swipeLayout.setRefreshing(false);
		List<NewModle> list = NewListJson.instance(getActivity())
				.readJsonNewModles(result, Url.TopId);
		if (index == 0 && list.size() >= 4) {
			initSliderLayout(list);
		} else {
			newAdapter.appendList(list);
		}
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
	}

	@Override
	public void onPause() {
		super.onPause();
	}
}
