package com.banhuitong.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.banhuitong.adapter.ViewPagerAdapter;
import com.banhuitong.util.ActivityUtils;
import com.banhuitong.view.PortionView;

public class GuideActivity extends BaseActivity implements OnPageChangeListener {

	private ViewPager vp;
	private ViewPagerAdapter vpAdapter;
	private List<View> views;
	private ImageView[] dots;
	private int currentIndex;
	private TextView tvEntrance;
	private PortionView v1,v2,v3,v4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guide);
		initViews();
		initDots();
	}

	private void initViews() {
		
		tvEntrance = (TextView) findViewById(R.id.tv_entrance);
		
		v1 = new PortionView(getApplication());
		v2 = new PortionView(getApplication());
		v3 = new PortionView(getApplication());
		v4 = new PortionView(getApplication());    
		
		v1.setIndex(0);
		v2.setIndex(1);
		v3.setIndex(2);
		v4.setIndex(3);
    		
		views = new ArrayList<View>();
		views.add(v4);
		views.add(v1);
		views.add(v2);
		views.add(v3);
		
		tvEntrance.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					recordUse();
					
					ActivityUtils.startIntent(GuideActivity.this, WelcomeActivity.class);
					GuideActivity.this.finish();
					break;
				}
				return false;
			}
		});

		vpAdapter = new ViewPagerAdapter(views);
		vp = (ViewPager) findViewById(R.id.viewpager);
		vp.setAdapter(vpAdapter);
		vp.setOnPageChangeListener(this);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void initDots() {
		LinearLayout ll = (LinearLayout) findViewById(R.id.ll);

		dots = new ImageView[views.size()];

		for (int i = 0; i < views.size(); i++) {
			ImageView dot = new ImageView(this);  
			dot.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));  
			dot.setPadding(5, 5, 5, 5);
			dot.setImageResource(R.drawable.dot);  
			ll.addView(dot);
			 
			dots[i] = dot;
			dots[i].setEnabled(true);
		}

		currentIndex = 0;
		dots[currentIndex].setEnabled(false);
	}

	private void setCurrentDot(int position) {
		if (position < 0 || position > views.size() - 1
				|| currentIndex == position) {
			return;
		}

		dots[position].setEnabled(false);
		dots[currentIndex].setEnabled(true);

		currentIndex = position;
		
		if(position==views.size()-1){
			tvEntrance.setVisibility(View.VISIBLE);
		}else{
			tvEntrance.setVisibility(View.GONE);
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		 if(arg0==0){
	        	v1.setIndex(0);
	        }else if(arg0==1){
	        	v2.setIndex(1);
	        }else if(arg0==2){
	        	v3.setIndex(2);
	        }else if(arg0==3){
	        	v4.setIndex(3);
	        }
	}

	@Override
	public void onPageSelected(int arg0) {
		setCurrentDot(arg0);
	}

}
