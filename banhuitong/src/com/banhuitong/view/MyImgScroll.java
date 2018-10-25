package com.banhuitong.view;

import java.lang.ref.WeakReference;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.banhuitong.activity.IndexActivity2;
import com.banhuitong.activity.ShowWebView2Activity;
import com.banhuitong.async.ImageLoadTask;
import com.banhuitong.util.Constants;

public class MyImgScroll extends ViewPager {

	public View[] mImageViews;
	private MyPagerAdapter adapter;
	private WeakReference<IndexActivity2> weak;

	// private int[] imgIdArray ;

	public WeakReference<IndexActivity2> getActivity() {
		return weak;
	}

	public void setActivity(IndexActivity2 activity) {
		this.weak = new WeakReference<IndexActivity2>(activity);
	}

	public MyImgScroll(Context context, AttributeSet attrs) {
		super(context, attrs);

		// imgIdArray = new int[]{R.drawable.ban_banner1,
		// R.drawable.ban_banner2, R.drawable.ban_banner3,
		// R.drawable.ban_banner4, R.drawable.ban_banner5};
		// mImageViews = new View[imgIdArray.length];

		// for(int i=0; i<mImageViews.length; i++){
		// ImageView imageView = new ImageView(this.getContext());
		// mImageViews[i] = imageView;
		// imageView.setBackgroundResource(imgIdArray[i]);
		// }

		mImageViews = new View[4];

		mImageViews[0] = new ImageView(MyImgScroll.this.getContext());
		((ImageView) mImageViews[0]).setScaleType(ScaleType.FIT_XY);
		new ImageLoadTask(MyImgScroll.this.getContext()).execute(
				mImageViews[0], Constants.mobileUrl
						+ "web/themes/default/images/banner/ban_banner1.jpg",
				getResources());

		mImageViews[1] = new ImageView(MyImgScroll.this.getContext());
		((ImageView) mImageViews[1]).setScaleType(ScaleType.FIT_XY);
		new ImageLoadTask(MyImgScroll.this.getContext()).execute(
				mImageViews[1], Constants.mobileUrl
						+ "web/themes/default/images/banner/ban_banner2.jpg",
				getResources());

		mImageViews[2] = new ImageView(MyImgScroll.this.getContext());
		((ImageView) mImageViews[2]).setScaleType(ScaleType.FIT_XY);
		new ImageLoadTask(MyImgScroll.this.getContext()).execute(
				mImageViews[2], Constants.mobileUrl
						+ "web/themes/default/images/banner/ban_banner3.jpg",
				getResources());

		mImageViews[3] = new ImageView(MyImgScroll.this.getContext());
		((ImageView) mImageViews[3]).setScaleType(ScaleType.FIT_XY);
		new ImageLoadTask(MyImgScroll.this.getContext()).execute(
				mImageViews[3], Constants.mobileUrl
						+ "web/themes/default/images/banner/ban_banner4.jpg",
				getResources());

		adapter = new MyPagerAdapter();
		this.setAdapter(adapter);
		this.setCurrentItem(0);
	}

	private class MyPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return Integer.MAX_VALUE;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(View container, int position, Object object) {
			// ((ViewPager) container).removeView(mImageViews[position
			// % mImageViews.length]);
		}

		@Override
		public Object instantiateItem(View container, int position) {

			if (mImageViews[position % mImageViews.length].getParent() != null) {
				((ViewPager) mImageViews[position % mImageViews.length]
						.getParent()).removeView(mImageViews[position
						% mImageViews.length]);
			}

			((ViewPager) container).addView(mImageViews[position
					% mImageViews.length], 0);

			mImageViews[position % mImageViews.length]
					.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {

							int pn = (MyImgScroll.this.getCurrentItem() + 1)
									% mImageViews.length == 0 ? mImageViews.length
									: (MyImgScroll.this.getCurrentItem() + 1)
											% mImageViews.length;

							Intent intent = new Intent(MyImgScroll.this
									.getContext(), ShowWebView2Activity.class);
							intent.putExtra("url", Constants.mobileUrl
									+ "app/banner.html?pn=" + pn);
							weak.get().startActivity(intent);
						}
					});

			return mImageViews[position % mImageViews.length];
		}
	}
}
