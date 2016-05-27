package com.example.androidannotationtesttwo.view;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.androidannotationtesttwo.R;
import com.example.androidannotationtesttwo.bean.VideoModle;
import com.example.androidannotationtesttwo.util.Options;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

@EViewGroup(R.layout.item_video)
public class VideoItemView extends LinearLayout {

	@ViewById(R.id.video_img)
	protected ImageView videoView;
	@ViewById(R.id.video_title)
	protected TextView videoTitle;
	@ViewById(R.id.video_time)
	protected TextView videoTime;

	protected ImageLoader imageLoader = ImageLoader.getInstance();

	protected DisplayImageOptions options;

	public VideoItemView(Context context) {
		super(context);
		options = Options.getListOptions();
	}

	public void setData(VideoModle videoModle) {
		videoTime.setText(videoModle.getLength());
		videoTitle.setText(videoModle.getTitle());
		imageLoader.displayImage(videoModle.getCover(), videoView, options);
	}

}
