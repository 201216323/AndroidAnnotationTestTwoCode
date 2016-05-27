package com.example.androidannotationtesttwo.activity;

import com.example.androidannotationtesttwo.R;
import com.example.androidannotationtesttwo.fragment.Fragment_home_;
import com.example.androidannotationtesttwo.fragment.Fragment_mine;
import com.example.androidannotationtesttwo.fragment.Fragment_mine_;
import com.example.androidannotationtesttwo.fragment.Fragment_topic;
import com.example.androidannotationtesttwo.fragment.Fragment_video;
import com.example.androidannotationtesttwo.initview.SlidingMenuView;
import com.example.androidannotationtesttwo.view.LeftView;
import com.example.androidannotationtesttwo.view.LeftView_;
import com.example.androidannotationtesttwo.widget.slidingmenu.SlidingMenu;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class TestActivity extends BaseActivity implements OnClickListener {

	Fragment_home_ fragment_home_;
	Fragment_video fragment_video_;
	Fragment_topic fragment_topic_;
	Fragment_mine_ fragment_mine_;
	private RadioGroup mRadioGroup;
	private LeftView mLeftView;
	private SlidingMenu side_drawer;
	private ImageView top_head;
	private TextView title_recent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		mLeftView = LeftView_.build(this);
		side_drawer = SlidingMenuView.instance().initSlidingMenuView(this,
				mLeftView);
		top_head = (ImageView) findViewById(R.id.top_head);
		top_head.setOnClickListener(this);
		title_recent = (TextView) findViewById(R.id.title_recent);
		mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup_main);
		mRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.radioButton_home:
					top_head.setVisibility(View.VISIBLE);
					title_recent.setText(R.string.fragment_home);
					showFragment(0);
					break;
				case R.id.radioButton_video:
					top_head.setVisibility(View.INVISIBLE);
					title_recent.setText(R.string.fragment_video);
					showFragment(1);
					break;
				case R.id.radioButton_topic:
					top_head.setVisibility(View.INVISIBLE);
					title_recent.setText(R.string.fragment_topic);
					showFragment(2);
					break;
				case R.id.radioButton_mine:
					top_head.setVisibility(View.INVISIBLE);
					title_recent.setText(R.string.fragment_mine);
					showFragment(3);
					break;
				}

			}
		});
		showFragment(0);

	}

	private void showFragment(int index) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction ft = fragmentManager.beginTransaction();
		// ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
		hideFragment(ft);
		switch (index) {
		case 0:
			if (fragment_home_ != null)
				ft.show(fragment_home_);
			else {
				fragment_home_ = new Fragment_home_();
				ft.add(R.id.framelayout_main, fragment_home_);
			}
			break;
		case 1:
			if (fragment_video_ != null)
				ft.show(fragment_video_);
			else {
				fragment_video_ = new Fragment_video();
				ft.add(R.id.framelayout_main, fragment_video_);
			}
			break;
		case 2:
			if (fragment_topic_ != null)
				ft.show(fragment_topic_);
			else {
				fragment_topic_ = new Fragment_topic();
				ft.add(R.id.framelayout_main, fragment_topic_);
			}
			break;
		case 3:
			if (fragment_mine_ != null)
				ft.show(fragment_mine_);
			else {
				fragment_mine_ = new Fragment_mine_();
				ft.add(R.id.framelayout_main, fragment_mine_);
			}
			break;
		}
		ft.commitAllowingStateLoss();
	}

	private void hideFragment(FragmentTransaction ft) {
		if (fragment_home_ != null)
			ft.hide(fragment_home_);
		if (fragment_video_ != null)
			ft.hide(fragment_video_);
		if (fragment_topic_ != null)
			ft.hide(fragment_topic_);
		if (fragment_mine_ != null)
			ft.hide(fragment_mine_);
	}

	private long timeMillis;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {

			if (side_drawer.isMenuShowing()) {
				side_drawer.showContent();// 关闭侧滑
			} else {
				if ((System.currentTimeMillis() - timeMillis) > 2000) {
					showShortToast("再按一次退出再按程序");
					timeMillis = System.currentTimeMillis();
				} else {
					finish();
					System.exit(0);
				}
			}

			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.top_head:
			if (side_drawer.isMenuShowing()) {
				side_drawer.showContent();
			} else {
				side_drawer.showMenu();
			}

			break;

		default:
			break;
		}

	}

}
