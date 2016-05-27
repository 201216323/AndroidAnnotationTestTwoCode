package com.example.androidannotationtesttwo.fragment;

import java.util.ArrayList;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidannotationtesttwo.App;
import com.example.androidannotationtesttwo.R;
import com.example.androidannotationtesttwo.activity.ChannelActivity_;
import com.example.androidannotationtesttwo.activity.TestActivity;
import com.example.androidannotationtesttwo.adapter.NewsFragmentPagerAdapter;
import com.example.androidannotationtesttwo.bean.ChannelItem;
import com.example.androidannotationtesttwo.bean.ChannelManage;
import com.example.androidannotationtesttwo.fragment.subfragment.BaoXueYouXiFragment_;
import com.example.androidannotationtesttwo.fragment.subfragment.BeiJingFragment_;
import com.example.androidannotationtesttwo.fragment.subfragment.BoKeFragment_;
import com.example.androidannotationtesttwo.fragment.subfragment.CBAFragment_;
import com.example.androidannotationtesttwo.fragment.subfragment.CaiJingFragment_;
import com.example.androidannotationtesttwo.fragment.subfragment.CaiPiaoFragment_;
import com.example.androidannotationtesttwo.fragment.subfragment.DianTaiFragment_;
import com.example.androidannotationtesttwo.fragment.subfragment.DianYingFragment_;
import com.example.androidannotationtesttwo.fragment.subfragment.FangChanFragment_;
import com.example.androidannotationtesttwo.fragment.subfragment.FoodBallFragment_;
import com.example.androidannotationtesttwo.fragment.subfragment.JiaJuFragment_;
import com.example.androidannotationtesttwo.fragment.subfragment.JiaoYuFragment_;
import com.example.androidannotationtesttwo.fragment.subfragment.JingXuanFragment_;
import com.example.androidannotationtesttwo.fragment.subfragment.JunShiFragment_;
import com.example.androidannotationtesttwo.fragment.subfragment.KeJiFragment_;
import com.example.androidannotationtesttwo.fragment.subfragment.LunTanFragment_;
import com.example.androidannotationtesttwo.fragment.subfragment.LvYouFragment_;
import com.example.androidannotationtesttwo.fragment.subfragment.NBAFragment_;
import com.example.androidannotationtesttwo.fragment.subfragment.NewsFragment_;
import com.example.androidannotationtesttwo.fragment.subfragment.QiCheFragment_;
import com.example.androidannotationtesttwo.fragment.subfragment.QinGanFragment_;
import com.example.androidannotationtesttwo.fragment.subfragment.QinZiFragment_;
import com.example.androidannotationtesttwo.fragment.subfragment.SheHuiFragment_;
import com.example.androidannotationtesttwo.fragment.subfragment.ShiShangFragment_;
import com.example.androidannotationtesttwo.fragment.subfragment.ShouJiFragment_;
import com.example.androidannotationtesttwo.fragment.subfragment.ShuMaFragment_;
import com.example.androidannotationtesttwo.fragment.subfragment.TiYuFragment_;
import com.example.androidannotationtesttwo.fragment.subfragment.TuPianFragment_;
import com.example.androidannotationtesttwo.fragment.subfragment.XiaoHuaFragment_;
import com.example.androidannotationtesttwo.fragment.subfragment.YiDongFragment_;
import com.example.androidannotationtesttwo.fragment.subfragment.YouXiFragment_;
import com.example.androidannotationtesttwo.fragment.subfragment.YuLeFragment_;
import com.example.androidannotationtesttwo.initview.SlidingMenuView;
import com.example.androidannotationtesttwo.util.BaseTools;
import com.example.androidannotationtesttwo.view.LeftView;
import com.example.androidannotationtesttwo.view.LeftView_;
import com.example.androidannotationtesttwo.widget.ColumnHorizontalScrollView;
import com.example.androidannotationtesttwo.widget.slidingmenu.SlidingMenu;

@EFragment(R.layout.fragmetn_home)
public class Fragment_home extends Fragment {
	// 水平滚动条和左右阴影图片所在的相对布局
	@ViewById(R.id.rl_column)
	protected RelativeLayout rl_column;
	/** 自定义HorizontalScrollView */
	@ViewById(R.id.mColumnHorizontalScrollView)
	protected ColumnHorizontalScrollView mColumnHorizontalScrollView;
	// 水平滚动条里面包含的线性布局
	@ViewById(R.id.linearLayout_content)
	protected LinearLayout linearLayout_content;
	//+号图片所在的线性布局
	@ViewById(R.id.ll_more_columns)
	protected LinearLayout ll_more_columns;
	// 加号图片
	@ViewById(R.id.button_more_columns)
	protected ImageView button_more_columns;
	//下面的viewpager
	@ViewById(R.id.mViewPager)
	protected ViewPager mViewPager;
	@ViewById(R.id.shade_left)
	protected ImageView shade_left;
	@ViewById(R.id.shade_right)
	protected ImageView shade_right;
	/** 屏幕宽度 */
	private int mScreenWidth = 0;
	/** Item宽度 */
	private int mItemWidth = 0;
	/** 用户选择的新闻分类列表 */
	protected static ArrayList<ChannelItem> userChannelLists;
	/** 请求CODE */
	public final static int CHANNELREQUEST = 1;
	/** 调整返回的RESULTCODE */
	public final static int CHANNELRESULT = 10;
	/** 当前选中的栏目 */
	private int columnSelectIndex = 0;
	private ArrayList<Fragment> fragments;

	private Fragment newfragment;
	private double back_pressed;

	public static boolean isChange = false;

	private NewsFragmentPagerAdapter mAdapetr;
	protected TestActivity self;

	/** 当前选中的栏目 */

	// @Override
	// public boolean isSupportSlide() {
	// return false;
	// }

	@SuppressLint("InlinedApi")
	@AfterInject
	// @AfterInject 定义的方法在类的构造方法执行后执行
	void init() {
		self = (TestActivity) getActivity();
		self.getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
				WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
		mScreenWidth = BaseTools.getWindowsWidth(self);
		mItemWidth = mScreenWidth / 7;// 一个Item宽度为屏幕的1/7
		userChannelLists = new ArrayList<ChannelItem>();// 初始化频道所在的ArrayList
		fragments = new ArrayList<Fragment>();// 初始化各个碎片所在的ArrayList
	}

	@AfterViews
	// @AfterViews 定义的方法在setContentView后执行
	void initView() {
		try {

			initViewPager();
			setChangelView();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initViewPager() {

		mAdapetr = new NewsFragmentPagerAdapter(
				self.getSupportFragmentManager());
		mViewPager.setOffscreenPageLimit(1);
		mViewPager.setAdapter(mAdapetr);
		mViewPager.addOnPageChangeListener(pageListener);
	}

	@Click(R.id.button_more_columns)
	protected void onMoreColumns(View view) {
		// self.openActivityForResult(ChannelActivity_.class, CHANNELREQUEST);
		Intent intent = new Intent(self, ChannelActivity_.class);
		// intent.putExtra(name, value)
		startActivityForResult(intent, CHANNELREQUEST);
	}

	/**
	 * 当栏目项发生变化时候调用
	 */
	public void setChangelView() {
		initColumnData();

	}

	/** 获取Column栏目 数据 */
	private void initColumnData() {
		userChannelLists = ((ArrayList<ChannelItem>) ChannelManage.getManage(
				App.getApp().getSQLHelper()).getUserChannel());
		initTabColumn();
		initFragment();
	}

	/**
	 * 初始化Column栏目项
	 */
	private void initTabColumn() {
		linearLayout_content.removeAllViews();
		int count = userChannelLists.size();
		/**setParam 
		 * activity 所在活动
		 * mScreenWitdh 屏幕的宽度
		 * paramView1 传入整体布局             水平滚动下面的线性布局
		 * paramView2 左阴影图片
		 * paramView3 右阴影图片
		 * paramView4 传入更多栏目选择布局  +号所在的线性布局
		 * paramView5 传入拖动栏布局     水平滚动条和左右阴影图片所在的相对布局
		 */
		mColumnHorizontalScrollView.setParam(self, mScreenWidth,
				linearLayout_content, shade_left, shade_right, ll_more_columns,
				rl_column);
		for (int i = 0; i < count; i++) {
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					mItemWidth, LayoutParams.WRAP_CONTENT);
			params.leftMargin = 5;
			params.rightMargin = 5;
			// TextView localTextView = (TextView)
			// mInflater.inflate(R.layout.column_radio_item, null);
			TextView columnTextView = new TextView(self);
			columnTextView.setTextAppearance(self,
					R.style.top_category_scroll_view_item_text);
			// localTextView.setBackground(getResources().getDrawable(R.drawable.top_category_scroll_text_view_bg));
			columnTextView.setBackgroundResource(R.drawable.radio_buttong_bg);
			columnTextView.setGravity(Gravity.CENTER);
			columnTextView.setPadding(0, 5, 0, 5);
			columnTextView.setId(i);
			columnTextView.setTextSize(14);
			columnTextView.setText(userChannelLists.get(i).getName());
			columnTextView.setTextColor(getResources().getColorStateList(
					R.color.top_category_scroll_text_color_day));
			if (columnSelectIndex == i) {
				columnTextView.setSelected(true);
			}
			columnTextView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					for (int i = 0; i < linearLayout_content.getChildCount(); i++) {
						View localView = linearLayout_content.getChildAt(i);
						if (localView != v)
							localView.setSelected(false);
						else {
							localView.setSelected(true);
							mViewPager.setCurrentItem(i);
						}
					}
				}
			});
			linearLayout_content.addView(columnTextView, i, params);
		}
	}

	/**
	 * 初始化Fragment
	 */
	private void initFragment() {
		fragments.clear();
		int count = userChannelLists.size();
		for (int i = 0; i < count; i++) {
			// Bundle data = new Bundle();
			String nameString = userChannelLists.get(i).getName();
			// data.putString("text", nameString);
			// data.putInt("id", userChannelList.get(i).getId());
			// initFragment(nameString);
			// map.put(nameString, nameString);
			// newfragment.setArguments(data);
			fragments.add(initFragment(nameString));
			// fragments.add(nameString);
		}
		mAdapetr.appendList(fragments);
	}

	public Fragment initFragment(String channelName) {
		if (channelName.equals("头条")) {
			newfragment = new NewsFragment_();
		} else if (channelName.equals("足球")) {
			newfragment = new FoodBallFragment_();
		} else if (channelName.equals("娱乐")) {
			newfragment = new YuLeFragment_();
		} else if (channelName.equals("体育")) {
			newfragment = new TiYuFragment_();
		} else if (channelName.equals("财经")) {
			newfragment = new CaiJingFragment_();
		} else if (channelName.equals("科技")) {
			newfragment = new KeJiFragment_();
		} else if (channelName.equals("电影")) {
			newfragment = new DianYingFragment_();
		} else if (channelName.equals("汽车")) {
			newfragment = new QiCheFragment_();
		} else if (channelName.equals("笑话")) {
			newfragment = new XiaoHuaFragment_();
		} else if (channelName.equals("时尚")) {
			newfragment = new ShiShangFragment_();
		} else if (channelName.equals("北京")) {
			newfragment = new BeiJingFragment_();
		} else if (channelName.equals("军事")) {
			newfragment = new JunShiFragment_();
		} else if (channelName.equals("房产")) {
			newfragment = new FangChanFragment_();
		} else if (channelName.equals("游戏")) {
			newfragment = new YouXiFragment_();
		} else if (channelName.equals("情感")) {
			newfragment = new QinGanFragment_();
		} else if (channelName.equals("精选")) {
			newfragment = new JingXuanFragment_();
		} else if (channelName.equals("电台")) {
			newfragment = new DianTaiFragment_();
		} else if (channelName.equals("图片")) {
			newfragment = new TuPianFragment_();
		} else if (channelName.equals("NBA")) {
			newfragment = new NBAFragment_();
		} else if (channelName.equals("数码")) {
			newfragment = new ShuMaFragment_();
		} else if (channelName.equals("移动")) {
			newfragment = new YiDongFragment_();
		} else if (channelName.equals("彩票")) {
			newfragment = new CaiPiaoFragment_();
		} else if (channelName.equals("教育")) {
			newfragment = new JiaoYuFragment_();
		} else if (channelName.equals("论坛")) {
			newfragment = new LunTanFragment_();
		} else if (channelName.equals("旅游")) {
			newfragment = new LvYouFragment_();
		} else if (channelName.equals("手机")) {
			newfragment = new ShouJiFragment_();
		} else if (channelName.equals("博客")) {
			newfragment = new BoKeFragment_();
		} else if (channelName.equals("社会")) {
			newfragment = new SheHuiFragment_();
		} else if (channelName.equals("家居")) {
			newfragment = new JiaJuFragment_();
		} else if (channelName.equals("暴雪")) {
			newfragment = new BaoXueYouXiFragment_();
		} else if (channelName.equals("亲子")) {
			newfragment = new QinZiFragment_();
		} else if (channelName.equals("CBA")) {
			newfragment = new CBAFragment_();
		}
		return newfragment;
	}

	/**
	 * ViewPager切换监听方法
	 */
	public OnPageChangeListener pageListener = new OnPageChangeListener() {

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageSelected(int position) {
			mViewPager.setCurrentItem(position);
			selectTab(position);
		}
	};

	/**
	 * 选择的Column里面的Tab
	 */
	private void selectTab(int tab_postion) {
		columnSelectIndex = tab_postion;
		for (int i = 0; i < linearLayout_content.getChildCount(); i++) {
			View checkView = linearLayout_content.getChildAt(tab_postion);
			int k = checkView.getMeasuredWidth();
			int l = checkView.getLeft();
			int i2 = l + k / 2 - mScreenWidth / 2;
			// rg_nav_content.getParent()).smoothScrollTo(i2, 0);
			mColumnHorizontalScrollView.smoothScrollTo(i2, 0);
			// mColumnHorizontalScrollView.smoothScrollTo((position - 2) *
			// mItemWidth , 0);
		}
		// 判断是否选中
		for (int j = 0; j < linearLayout_content.getChildCount(); j++) {
			View checkView = linearLayout_content.getChildAt(j);
			boolean ischeck;
			if (j == tab_postion) {
				ischeck = true;
			} else {
				ischeck = false;
			}
			checkView.setSelected(ischeck);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case CHANNELREQUEST:
			if (resultCode == CHANNELRESULT) {
				String isChanged = data.getStringExtra("isChange");
				if ("true".equals(isChanged)) {
					// self.showShortToast("isChange");
					setChangelView();
				}
				break;
			}
		}

		// try {
		// if (isChange) {
		// self.showShortToast("isChange");
		// setChangelView();
		// // initColumnData();
		// // initTabColumn();
		// isChange = false;
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
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
