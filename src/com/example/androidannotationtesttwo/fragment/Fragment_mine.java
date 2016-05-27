package com.example.androidannotationtesttwo.fragment;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import com.example.androidannotationtesttwo.R;
import com.example.androidannotationtesttwo.activity.PersonInfoActivity;
import com.example.androidannotationtesttwo.activity.PersonInfoActivity_;
import com.example.androidannotationtesttwo.activity.TestActivity;
import com.example.androidannotationtesttwo.util.Options;
import com.example.androidannotationtesttwo.util.SharedPreferencesHelper;
import com.example.androidannotationtesttwo.widget.roundimage.RoundImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

@EFragment(R.layout.fragment_mine)
public class Fragment_mine extends Fragment {
	protected TestActivity self;
	@ViewById(R.id.ll_image)
	protected LinearLayout ll_image;
	@ViewById(R.id.roundImageView_touxiang)
	protected RoundImageView roundImageView_touxiang;
	@ViewById(R.id.textView_nickname)
	protected TextView textView_nickname;
	@ViewById(R.id.relativeLayout_tx_nickname)
	protected RelativeLayout relativeLayout_tx_nickname;
	@ViewById(R.id.linearLayout_collection)
	protected LinearLayout linearLayout_collection;

	@ViewById(R.id.linearLayout_yejian)
	protected LinearLayout linearLayout_yejian;

	@ViewById(R.id.linearLayout_setting)
	protected LinearLayout linearLayout_setting;
	private static final int REQUEST_CODE = 0x10;
	private static final int RESULT_CODE = 0x11;
	private SharedPreferencesHelper sph;
	private DisplayImageOptions options;
	private ImageLoader imageLoader;
	private ImageView mImageVieBig;
	private Dialog imageDialog;
	private int screenWidth;// 屏幕的宽
	private int screenHeight;// 屏幕的高
	private String tempImg, tempNickname, str_headimg, str_nickname;

	@AfterInject
	// @AfterInject 定义的方法在类的构造方法执行后执行
	void init() {
		self = (TestActivity) getActivity();
		options = Options.getListOptions();
		imageLoader = ImageLoader.getInstance();
		sph = new SharedPreferencesHelper(self, "PersonInfoActivity");
	}

	@AfterViews
	// @AfterViews 定义的方法在setContentView后执行
	void initView() {
		initTitleView();
	}

	void initTitleView() {
		loadSpsDate();

	}

	@Click({ R.id.roundImageView_touxiang, R.id.textView_nickname,
			R.id.linearLayout_collection, R.id.linearLayout_yejian,
			R.id.linearLayout_setting, R.id.ll_image })
	protected void onViewClick(View view) {
		switch (view.getId()) {
		case R.id.ll_image:

			break;
		case R.id.roundImageView_touxiang:
			// self.showShortToast("你点击了头像");
			Intent personInfoIntent = new Intent(self,
					PersonInfoActivity_.class);
			startActivityForResult(personInfoIntent, REQUEST_CODE);
			// showImageDialog();
			break;
		case R.id.textView_nickname:
			// self.showShortToast("你点击了登录");
			break;
		case R.id.linearLayout_collection:
//			self.showShortToast("你点击了收藏");
			break;
		case R.id.linearLayout_yejian:
//			self.showShortToast("你点击了夜间");
			break;
		case R.id.linearLayout_setting:
			// self.showShortToast("你点击了设置");

			break;

		default:
			break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch (requestCode) {
		case REQUEST_CODE:
			if (resultCode == RESULT_CODE) {
				String headImage = data.getStringExtra("1");
				String nickName = data.getStringExtra("2");
				if (!"".equals(headImage)) {
					imageLoader.displayImage(headImage,
							roundImageView_touxiang, options);
				}
				if (!"".equals(nickName)) {
					textView_nickname.setText(nickName.trim());
				}

			}

			break;

		default:
			break;
		}
	}

	public void loadSpsDate() {
		str_headimg = sph.getString("headimg");
		if (!"".equals(str_headimg)&&str_headimg!= null) {
			imageLoader.displayImage(str_headimg, roundImageView_touxiang,
					options);
		}
		str_nickname = sph.getString("et_nickname");
		if (!"".equals(str_nickname)&&str_headimg!= null) {
			textView_nickname.setText(str_nickname.trim());
		}

	}

	/**
	 * 图片全屏显示的Dialog
	 */
	private void showImageDialog() {
		View imageDialogView = self.getLayoutInflater().inflate(
				R.layout.mine_change_touxiang_image, null);
		mImageVieBig = (ImageView) imageDialogView
				.findViewById(R.id.imageview_touxiang_mine);

		imageDialog = new Dialog(self, R.style.style_dialog);

		if (!"".equals(str_headimg)) {
			// mImageVieBig.getLayoutParams().width = (int) (screenWidth);
			mImageVieBig.getLayoutParams().height = (int) (0.75 * screenHeight);
			try {
				imageLoader.displayImage(str_headimg, mImageVieBig, options);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			mImageVieBig.setImageDrawable(getResources().getDrawable(
					R.drawable.liuyifei));
		}
		imageDialog.setContentView(imageDialogView);
		// 得到Dialog的属性
		android.view.WindowManager.LayoutParams lay = imageDialog.getWindow()
				.getAttributes();
		// 设置Dialog的布局
		lay.width = LayoutParams.MATCH_PARENT;
		lay.height = LayoutParams.WRAP_CONTENT;
		// 设置Dialog的背景为全黑
		lay.dimAmount = 1.0f;
		imageDialog.show();

		mImageVieBig.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				imageDialog.dismiss();
			}
		});
	}

}
