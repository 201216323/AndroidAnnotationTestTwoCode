
package com.example.androidannotationtesttwo.activity;

import java.util.List;
import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import android.widget.TextView;
import com.example.androidannotationtesttwo.R;
import com.example.androidannotationtesttwo.adapter.ImageAdapter;
import com.example.androidannotationtesttwo.bean.NewDetailModle;
import com.example.androidannotationtesttwo.bean.NewModle;
import com.example.androidannotationtesttwo.widget.flipview.FlipView;
import com.example.androidannotationtesttwo.widget.flipview.FlipView.OnFlipListener;
import com.example.androidannotationtesttwo.widget.flipview.FlipView.OnOverFlipListener;
import com.example.androidannotationtesttwo.widget.flipview.OverFlipMode;

@EActivity(R.layout.activity_image)
public class ImageDetailActivity extends BaseActivity implements OnFlipListener,
        OnOverFlipListener {
    @ViewById(R.id.flip_view)
    protected FlipView mFlipView;
    @ViewById(R.id.new_title)
    protected TextView newTitle;
    private NewModle newModle;
    @Bean
    protected ImageAdapter imageAdapter;
    private List<String> imgList;
    private NewDetailModle newDetailModle;
    private String titleString;

    @AfterInject
    public void init() {
        try {
            if (getIntent().getExtras().getSerializable("newDetailModle") != null) {
                newDetailModle = (NewDetailModle) getIntent().getExtras().getSerializable(
                        "newDetailModle");
                imgList = newDetailModle.getImgList();
                titleString = newDetailModle.getTitle();
            } else {
                newModle = (NewModle) getIntent().getExtras().getSerializable("newModle");
                imgList = newModle.getImagesModle().getImgList();
                titleString = newModle.getTitle();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterViews
    public void initView() {
        try {
            newTitle.setText(titleString);
            imageAdapter.appendList(imgList);
            mFlipView.setOnFlipListener(this);
            mFlipView.setAdapter(imageAdapter);
            // mFlipView.peakNext(false);
            mFlipView.setOverFlipMode(OverFlipMode.RUBBER_BAND);
            mFlipView.setOnOverFlipListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onOverFlip(FlipView v, OverFlipMode mode, boolean overFlippingPrevious,
            float overFlipDistance, float flipDistancePerPage) {
    }

    @Override
    public void onFlippedToPage(FlipView v, int position, long id) {
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
