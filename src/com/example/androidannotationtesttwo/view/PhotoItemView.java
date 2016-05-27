
package com.example.androidannotationtesttwo.view;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.androidannotationtesttwo.R;
import com.example.androidannotationtesttwo.util.Options;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
/**
 * 继承自RelativeLayout的自定义的PhotoItemView
 * @author hosa2015
 */
@EViewGroup(R.layout.item_photo)
public class PhotoItemView extends RelativeLayout {

    @ViewById(R.id.photo_img)
    protected ImageView photoImg;

    @ViewById(R.id.photo_title)
    protected TextView photoTitle;

    protected ImageLoader imageLoader = ImageLoader.getInstance();

    protected DisplayImageOptions options;

    public PhotoItemView(Context context) {
        super(context);
        options = Options.getListOptions();
    }

    public void setData(String title, String picUrl) {

        picUrl = picUrl.replace("auto", "854x480x75x0x0x3");

        photoTitle.setText(title);
        imageLoader.displayImage(picUrl, photoImg, options);
    }

}
