package com.example.androidannotationtesttwo.fragment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.os.AsyncTaskCompat;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidannotationtesttwo.R;
import com.example.androidannotationtesttwo.activity.TestActivity;
import com.example.androidannotationtesttwo.flingswipe.SwipeFlingAdapterView;
import com.example.androidannotationtesttwo.flingswipe.SwipeFlingAdapterView.OnItemClickListener;
import com.example.androidannotationtesttwo.flingswipe.SwipeFlingAdapterView.onFlingListener;
import com.example.androidannotationtesttwo.util.Options;
import com.example.androidannotationtesttwo.util.WindowManagerUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class Fragment_topic extends Fragment implements onFlingListener,
		OnItemClickListener {
	protected TestActivity self;
	String[] headerIcons = {
			"http://enjoy.eastday.com/epublish/gb/paper235/92/class023500001/image/img1179927_2.jpg",
			"http://img.tvyugao.com/still/c/044/9858719e39dcca7d.jpg",
			"http://image2.sina.com.cn/ent/d/2003-02-18/3_28-3-326-348_20030218104036.jpg",
			"http://img3.2345.com/dianyingimg/tv/img/cae/5/b/4.jpg",
			"http://img.tvyugao.com/still/c/044/95c621e7e7567d2b.jpg",
			"http://img01.e23.cn/2013/0122/20130122065702497.png",
			"http://p2.so.qhimg.com/t019174721f8b202687.jpg",
			"http://img.ivsky.com/img/tupian/img/201102/25/wushu_gongfu-014.jpg",
			"http://image1.caixin.com/2012-06-10/201206100094_840_560.jpg",
			"http://p1.so.qhimg.com/t01f33e2ad514c0e80b.jpg",
			"http://ww1.sinaimg.cn/large/6e2e50f5tw1e889wnzzw6j20fa09qdgy.jpg",
			"http://www.sqjob.cn/daxue/gaoxiao/shifan31_5.jpg",
			"http://a4.att.hudong.com/76/24/01300000164151124840249176828.jpg",
			"http://college.zjut.cc/static/college/album/big/bb/142137yk4hzrwelz14w6z1.jpg",
			"http://gkcx.eol.cn/upload/2011052714511299.jpg",
			"http://www.mshao.com/uploadfile/2013/1116/20131116055723974.jpg",
			"http://www.ncwu.edu.cn/upload/2013/8/26132333223.png",
			"http://image.xinmin.cn/2011/12/29/20111229162719704996.jpg",
			"http://www.cnr.cn/hnfw/jdt/20150108/W020150108348648124691.jpg",
			"http://a2.att.hudong.com/02/75/01300534693818136565755528473.jpg",
			"http://www.ha.xinhuanet.com/yincang/2011-03/02/xin_4730308021639125771418.jpg",
			"http://cache.house.sina.com.cn/citylifehouse/citylife/88/c8/20090630_45150_1.jpg",
			};
	String[] names = { "张无忌", "周芷若", "敏敏特穆尔", "波斯圣女", "武当张三丰","黄衫女子","东方不败", "风清扬", "小龙女", "令狐冲", "老顽童周伯通","全真王重阳" };
	String[] citys = { "明教","天鹰教", "武当派", "峨眉派", "少林派","崆峒派"};
	String[] edus = { "教主", "光明左使", "光明右使", "紫杉龙王","青翼蝠王", "白眉鹰王", "金毛狮王", "五散人", };
	String[] years = { "1年", "2年", "3年", "4年", "5年" };
	Random ran = new Random();
	private int cardWidth;
	private int cardHeight;
	private SwipeFlingAdapterView swipeView;
	private InnerAdapter adapter;
	private ImageLoader imageLoader = ImageLoader.getInstance();

	private DisplayImageOptions options;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_topic, null);
		
		self = (TestActivity) getActivity();
		// DisplayMetrics{density=3.0, width=1080, height=1920,
		// scaledDensity=3.0,
		// xdpi=449.704, ydpi=447.412}
		DisplayMetrics dm = getResources().getDisplayMetrics();
		float density = dm.density;
		cardWidth = (int) (dm.widthPixels - (2 * 18 * density));
		cardHeight = (int) (dm.heightPixels - (338 * density));

		swipeView = (SwipeFlingAdapterView) view.findViewById(R.id.swipe_view);
//		LayoutParams  params = swipeView.getLayoutParams();
//		params.height = (int) (WindowManagerUtil.getintence(self).getScreenheight()*0.75);
//		swipeView.setLayoutParams(params);
		// swipeView.setIsNeedSwipe(true);
		swipeView.setFlingListener(this);
		swipeView.setOnItemClickListener(this);
		options=Options.getListOptions();
		adapter = new InnerAdapter();
		swipeView.setAdapter(adapter);
		
		loadData();
		return view;
	}

	@Override
	public void onItemClicked(MotionEvent event, View v, Object dataObject) {
		// TODO Auto-generated method stub
		 if (v.getTag() instanceof ViewHolder) {
	            int x = (int) event.getRawX();
	            int y = (int) event.getRawY();
	            ViewHolder vh = (ViewHolder) v.getTag();
	            View child = vh.portraitView;
	            Rect outRect = new Rect();
	            child.getGlobalVisibleRect(outRect);
	            if (outRect.contains(x, y)) {
	                self.showShortToast("click 大图");
	            } else {
	                outRect.setEmpty();
	                child = vh.collectView;
	                child.getGlobalVisibleRect(outRect);
	                if (outRect.contains(x, y)) {
	                    self.showShortToast("click 关注");
	                }
	            }
	        }

	}

	@Override
	public void removeFirstObjectInAdapter() {
		// TODO Auto-generated method stub
		adapter.remove(0);
		
	}

	@Override
	public void onLeftCardExit(Object dataObject) {
		// TODO Auto-generated method stub
		 self.showShortToast("swipe left card");
	}

	@Override
	public void onRightCardExit(Object dataObject) {
		// TODO Auto-generated method stub
		 self.showShortToast("swipe right card");
	}

	@Override
	public void onAdapterAboutToEmpty(int itemsInAdapter) {
		// TODO Auto-generated method stub
		 if (itemsInAdapter == 3) {
	            loadData();
//			 self.showShortToast("加载数据了");
	        }
	}

	@Override
	public void onScroll(float progress, float scrollXProgress) {
		// TODO Auto-generated method stub

	}
	private void loadData() {
        AsyncTaskCompat.executeParallel(new AsyncTask<Void, Void, List<Talent>>() {
            @Override
            protected List<Talent> doInBackground(Void... params) {
                ArrayList<Talent> list = new ArrayList<Talent>(10);
                Talent talent;
                for (int i = 0; i < 10; i++) {
                    talent = new Talent();
                    talent.headerIcon = headerIcons[i];
                    talent.nickname = names[ran.nextInt(names.length-1)];
                    talent.cityName = citys[ran.nextInt(citys.length-1)];
                    talent.educationName = edus[ran.nextInt(edus.length-1)];
                    talent.workYearName = years[ran.nextInt(years.length-1)];
                    list.add(talent);
                }
                return list;
            }

            @Override
            protected void onPostExecute(List<Talent> list) {
                super.onPostExecute(list);
                adapter.addAll(list);
            }
        });
    }

	private class InnerAdapter extends BaseAdapter {

		ArrayList<Talent> objs;

		public InnerAdapter() {
			objs = new ArrayList<Talent>();
		}

		public void addAll(Collection<Talent> collection) {
			if (isEmpty()) {
				objs.addAll(collection);
				notifyDataSetChanged();
			} else {
				objs.addAll(collection);
			}
		}

		public void clear() {
			objs.clear();
			notifyDataSetChanged();
		}

		public boolean isEmpty() {
			return objs.isEmpty();
		}

		public void remove(int index) {
			if (index > -1 && index < objs.size()) {
				objs.remove(index);
				notifyDataSetChanged();
			}
		}

		@Override
		public int getCount() {
			return objs.size();
		}

		@Override
		public Talent getItem(int position) {
			if (objs == null || objs.size() == 0)
				return null;
			return objs.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		// TODO: getView
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Talent talent = getItem(position);
			if (convertView == null)
				convertView = LayoutInflater.from(parent.getContext()).inflate(
						R.layout.card_new_item, parent, false);
			ViewHolder holder = new ViewHolder();
			convertView.setTag(holder);
			convertView.getLayoutParams().width = cardWidth;
			holder.portraitView = (ImageView) convertView
					.findViewById(R.id.portrait);
			// holder.portraitView.getLayoutParams().width = cardWidth;
			holder.portraitView.getLayoutParams().height = cardHeight;
			holder.nameView = (TextView) convertView.findViewById(R.id.name);
			// parentView.getLayoutParams().width = cardWidth;
			// holder.jobView = (TextView) convertView.findViewById(R.id.job);
			// holder.companyView = (TextView)
			// convertView.findViewById(R.id.company);
			holder.cityView = (TextView) convertView.findViewById(R.id.city);
			holder.eduView = (TextView) convertView
					.findViewById(R.id.education);
			holder.workView = (TextView) convertView
					.findViewById(R.id.work_year);
			holder.collectView = (CheckedTextView) convertView
					.findViewById(R.id.favorite);

//			holder.portraitView.setImageURI(Uri.parse(talent.headerIcon));
			 imageLoader.displayImage(talent.headerIcon, holder.portraitView, options);
			holder.nameView.setText(String.format("%s", talent.nickname));
			// holder.jobView.setText(talent.jobName);

			final CharSequence no = "暂无";

			holder.cityView.setHint(no);
			holder.cityView.setText(talent.cityName);
			holder.cityView.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.home01_icon_location, 0, 0);

			holder.eduView.setHint(no);
			holder.eduView.setText(talent.educationName);
			holder.eduView.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.home01_icon_edu, 0, 0);

			holder.workView.setHint(no);
			holder.workView.setText(talent.workYearName);
			holder.workView.setCompoundDrawablesWithIntrinsicBounds(0,
					R.drawable.home01_icon_work_year, 0, 0);

			return convertView;
		}

	}

	private static class ViewHolder {
		ImageView portraitView;
		TextView nameView;
		TextView cityView;
		TextView eduView;
		TextView workView;
		CheckedTextView collectView;

	}

	public static class Talent {

		public String headerIcon;
		public String nickname;
		public String cityName;
		public String educationName;
		public String workYearName;
	}
}
