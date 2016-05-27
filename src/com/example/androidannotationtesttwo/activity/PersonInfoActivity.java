package com.example.androidannotationtesttwo.activity;

import java.io.ByteArrayOutputStream;
import java.io.File;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.androidannotationtesttwo.R;
import com.example.androidannotationtesttwo.util.Options;
import com.example.androidannotationtesttwo.util.ScreenUtils;
import com.example.androidannotationtesttwo.util.SharedPreferencesHelper;
import com.example.androidannotationtesttwo.util.WindowManagerUtil;
import com.example.androidannotationtesttwo.widget.iosdialog.ActionSheetDialog;
import com.example.androidannotationtesttwo.widget.iosdialog.ActionSheetDialog.OnSheetItemClickListener;
import com.example.androidannotationtesttwo.widget.iosdialog.ActionSheetDialog.SheetItemColor;
import com.example.androidannotationtesttwo.widget.xroundimage.XCRoundImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

@EActivity(R.layout.activity_person_info)
public class PersonInfoActivity extends BaseActivity {

	@ViewById(R.id.back)
	protected TextView back;
	@ViewById(R.id.title)
	protected TextView title;
	@ViewById(R.id.ll_imageview)
	protected LinearLayout ll_imageview;
	@ViewById(R.id.headimg)
	protected XCRoundImageView headimg;
	@ViewById(R.id.et_nickname)
	protected EditText et_nickname;
	@ViewById(R.id.tv_headuri)
	protected TextView tv_headuri;
	@ViewById(R.id.et_name)
	protected EditText et_name;
	@ViewById(R.id.et_school)
	protected EditText et_school;
	@ViewById(R.id.et_yuanxi)
	protected EditText et_yuanxi;
	@ViewById(R.id.et_zhuanye)
	protected EditText et_zhuanye;
	@ViewById(R.id.et_xuehao)
	protected EditText et_xuehao;
	@ViewById(R.id.et_sushe)
	protected EditText et_sushe;
	private String str_headimg, str_nickname, str_name, str_school, str_yuanxi,
			str_zhuanye, str_xuehao, str_sushe;
	/** 编辑头像相册选取 */
	private static final int REQUESTCODE_PICK = 1;
	/** 设置头像 */
	private static final int REQUESTCODE_CUTTING = 2;
	/** 编辑头像拍照选取 */
	private static final int PHOTO_REQUEST_TAKEPHOTO = 3;
	/** 标记是拍照还是相册0 是拍照1是相册 */
	private int cameraorpic;
	private SharedPreferencesHelper sph;
	private DisplayImageOptions options;
	private ImageLoader imageLoader;
	private static final int RESULT_CODE = 0x11;
	private ImageView mImageVieBig;
	private Dialog imageDialog;
	private int screenWidth;// 屏幕的宽
	private int screenHeight;// 屏幕的高

	@AfterInject
	// @AfterInject 定义的方法在类的构造方法执行后执行
	void init() {
		sph = new SharedPreferencesHelper(PersonInfoActivity.this,"PersonInfoActivity");
		options = Options.getListOptions();
		imageLoader = ImageLoader.getInstance();
		screenHeight = WindowManagerUtil.getintence(this).getScreenheight();
		
	}

	@AfterViews
	// @AfterViews 定义的方法在setContentView后执行
	void initView() {
		initTitleView();
		initXCRoundImageView();
		loadSpsDate();
	}

	void initTitleView() {
		title.setText("个人信息");

	}

	void initXCRoundImageView() {
		headimg.setType(XCRoundImageView.TYPE_ROUND);
		headimg.setRoundBorderRadius(20);// 设置圆角角度

	}

	@Click({R.id.ll_imageview, R.id.headimg })
	protected void onViewClick(View view) {
		switch (view.getId()) {
		case R.id.ll_imageview:
			popCheck();
			break;
		case R.id.headimg:
			// showShortToast("headimg");
			showImageDialog();
			break;

		default:
			break;
		}

	}

	@Override
	public void onBackPressed() {
//		showShortToast("返回");
		str_headimg = tv_headuri.getText().toString();
		str_nickname = et_nickname.getText().toString();
		if (!TextUtils.isEmpty(str_nickname)) {
			sph.putString("et_nickname", str_nickname);
		}
		str_name = et_name.getText().toString();
		if (!TextUtils.isEmpty(str_name)) {
			sph.putString("et_name", str_name);
		}
		str_school = et_school.getText().toString();
		if (!TextUtils.isEmpty(str_school)) {
			sph.putString("et_school", str_school);
		}
		str_yuanxi = et_yuanxi.getText().toString();
		if (!TextUtils.isEmpty(str_yuanxi)) {
			sph.putString("et_yuanxi", str_yuanxi);
		}
		str_zhuanye = et_zhuanye.getText().toString();
		if (!TextUtils.isEmpty(str_zhuanye)) {
			sph.putString("et_zhuanye", str_zhuanye);
		}
		str_xuehao = et_xuehao.getText().toString();
		if (!TextUtils.isEmpty(str_xuehao)) {
			sph.putString("et_xuehao", str_xuehao);
		}
		str_sushe = et_sushe.getText().toString();
		if (!TextUtils.isEmpty(str_sushe)) {
			sph.putString("et_sushe", str_sushe);
		}
		Intent intent = new Intent();
		intent.putExtra("1", str_headimg);
		intent.putExtra("2", str_nickname);
		setResult(RESULT_CODE,intent);
		finish();
	}

	public void popCheck() {

		new ActionSheetDialog(PersonInfoActivity.this)
				.builder()
				.setCancelable(false)
				.setCanceledOnTouchOutside(false)
				.addSheetItem("拍照", SheetItemColor.Blue,
						new OnSheetItemClickListener() {
							@Override
							public void onClick(int which) {
								cameraorpic = 0;
								openCamera();
							}
						})
				.addSheetItem("打开相册", SheetItemColor.Blue,
						new OnSheetItemClickListener() {
							@Override
							public void onClick(int which) {
								cameraorpic = 1;
								skipPic();
							}
						}).show();
	}

	/** 指定拍摄图片文件位置避免获取到缩略图 */
	File outFile;
	Uri outUri;

	/** 打开相机 */
	private void openCamera() {
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			File outDir = Environment
					.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);// Environment.DIRECTORY_PICTURES
			if (!outDir.exists()) {
				outDir.mkdirs();
			}
			outFile = new File(outDir, System.currentTimeMillis() + ".jpg");
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outFile));
			intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
			startActivityForResult(intent, PHOTO_REQUEST_TAKEPHOTO);
		} else {
			Log.e("CAMERA", "请确认已经插入SD卡");
		}
	}

	/** 设置可编辑头像 */
	public void startPhotoZoom(Uri uri) {
		outUri = uri;
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", true);
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 300);
		intent.putExtra("outputY", 300);
		intent.putExtra("return-data", true);
		intent.putExtra("noFaceDetection", true);
		startActivityForResult(intent, REQUESTCODE_CUTTING);
	}

	/** 打开相册 */
	private void skipPic() {
		Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
		pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				"image/*");
		startActivityForResult(pickIntent, REQUESTCODE_PICK);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 判断请求码是编辑就跳到编辑
		switch (requestCode) {
		case REQUESTCODE_PICK:
			if (data == null || data.getData() == null) {
				return;
			}
			startPhotoZoom(data.getData());
			break;
		case REQUESTCODE_CUTTING:
			if (data != null) {
				setPicToView(data);
			}
			break;
		case PHOTO_REQUEST_TAKEPHOTO:
			if (outFile.exists()) {
				startPhotoZoom(Uri.fromFile(outFile));
			}

			break;
		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * save the picture data 设置头像并保存头像
	 * 
	 * @param picdata
	 */
	private void setPicToView(Intent picdata) {
		Bundle extras = picdata.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			Drawable drawable = new BitmapDrawable(getResources(), photo);
			headimg.setImageDrawable(drawable);
			tv_headuri.setText(outUri.toString());
			sph.putString("headimg", tv_headuri.getText().toString());
			/** 可用于图像上传 */
			// UpdateAvater("url", Bitmap2Bytes(photo));
		}
	}

	/** 将BItmap转换成字节数组 */
	public byte[] Bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	public void loadSpsDate() {
		str_headimg = sph.getString("headimg");
		if (!TextUtils.isEmpty(str_headimg)) {
			imageLoader.displayImage(str_headimg, headimg, options);
		}
		tv_headuri.setText(str_headimg);
		str_nickname = sph.getString("et_nickname");
		if (!TextUtils.isEmpty(str_nickname)) {
			et_nickname.setText(str_nickname);
		}
		str_name = sph.getString("et_name");
		if (!TextUtils.isEmpty(str_name)) {
			et_name.setText(str_name);
		}
		str_school = sph.getString("et_school");
		if (!TextUtils.isEmpty(str_school)) {
			et_school.setText(str_school);
		}
		str_yuanxi = sph.getString("et_yuanxi");
		if (!TextUtils.isEmpty(str_yuanxi)) {
			et_yuanxi.setText(str_yuanxi);
		}
		str_zhuanye = sph.getString("et_zhuanye");
		if (!TextUtils.isEmpty(str_zhuanye)) {
			et_zhuanye.setText(str_zhuanye);
		}
		str_xuehao = sph.getString("et_xuehao");
		if (!TextUtils.isEmpty(str_xuehao)) {
			et_xuehao.setText(str_xuehao);
		}
		str_sushe = sph.getString("et_sushe");
		if (!TextUtils.isEmpty(str_sushe)) {
			et_sushe.setText(str_sushe);
		}

	}
	/**
	 * 图片全屏显示的Dialog
	 */
	private void showImageDialog() {
		View imageDialogView = getLayoutInflater().inflate(
				R.layout.mine_change_touxiang_image, null);
		mImageVieBig = (ImageView) imageDialogView
				.findViewById(R.id.imageview_touxiang_mine);

		imageDialog = new Dialog(PersonInfoActivity.this,
				R.style.style_dialog);

		if (!"".equals(tv_headuri.getText().toString())) {
//			mImageVieBig.getLayoutParams().width = (int) (screenWidth);
			mImageVieBig.getLayoutParams().height = (int) (0.75 * screenHeight);
			try {
				imageLoader.displayImage(tv_headuri.getText().toString(), mImageVieBig, options);
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
