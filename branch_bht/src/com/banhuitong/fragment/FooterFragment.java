package com.banhuitong.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.banhuitong.activity.BaseActivity;
import com.banhuitong.activity.LoginActivity;
import com.banhuitong.activity.MainActivity3;
import com.banhuitong.activity.MyApplication;
import com.banhuitong.activity.R;
import com.banhuitong.util.ViewUtils;

@SuppressLint("NewApi")
public class FooterFragment extends Fragment {

	public ImageView imgMyAccount;
	public ImageView imgIndex;
	public ImageView imgMain;
	public ImageView imgMore;
	private Activity activity;
	private LinearLayout llMyAccount;
	private LinearLayout llIndex;
	private LinearLayout llMain;
	private LinearLayout llMore;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.footer, container, true);
		imgMyAccount = (ImageView) view.findViewById(R.id.img_acc);
		imgIndex = (ImageView) view.findViewById(R.id.img_index);
		imgMain = (ImageView) view.findViewById(R.id.img_main);
		imgMore = (ImageView) view.findViewById(R.id.img_more);

		llMyAccount = (LinearLayout) view.findViewById(R.id.ll_acc);
		llIndex = (LinearLayout) view.findViewById(R.id.ll_index);
		llMain = (LinearLayout) view.findViewById(R.id.ll_main);
		llMore = (LinearLayout) view.findViewById(R.id.ll_more);
		return view;
	}

	@Override
	public void onStart() {
		super.onStart();
		setListener();
		initImage();

		
		if (activity instanceof MainActivity3) {
			if(((MainActivity3) activity).pager.getCurrentItem()==0){
				imgIndex.setBackgroundResource(R.drawable.footer_home_highlight); 
			}else if(((MainActivity3) activity).pager.getCurrentItem()==1){			
				imgMain.setBackgroundResource(R.drawable.footer_product_highlight);
			}else if(((MainActivity3) activity).pager.getCurrentItem()==2){
				imgMyAccount.setBackgroundResource(R.drawable.footer_myaccount_highlight);
			}else if(((MainActivity3) activity).pager.getCurrentItem()==3){
				imgMore.setBackgroundResource(R.drawable.footer_more_highlight);
			}
		}

		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				MyApplication.screen_w / 15, MyApplication.screen_h / 25);
		imgMyAccount.setLayoutParams(layoutParams);
		imgIndex.setLayoutParams(layoutParams);
		imgMain.setLayoutParams(layoutParams);
		imgMore.setLayoutParams(layoutParams);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = activity;
	}

	private void setListener() {
		llMyAccount.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (activity instanceof MainActivity3) {
					if (!((MainActivity3) activity).isLogin()) {
						Intent intent = new Intent(activity,
								LoginActivity.class);
						startActivity(intent);
						((MainActivity3) activity).pager.setCurrentItem(0);
						return;
					}
					((MainActivity3) activity).pager.setCurrentItem(2);
				}
			}
		});

		llIndex.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (activity instanceof MainActivity3) {
					((MainActivity3) activity).pager.setCurrentItem(0);
				}
			}
		});

		llMain.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (activity instanceof MainActivity3) {
					if (!((MainActivity3) activity).isLogin()) {
						Intent intent = new Intent(activity,
								LoginActivity.class);
						startActivity(intent);
						((MainActivity3) activity).pager.setCurrentItem(0);
						return;
					}
					((MainActivity3) activity).pager.setCurrentItem(1);
				}
			}
		});

		llMore.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (activity instanceof MainActivity3) {
					((MainActivity3) activity).pager.setCurrentItem(3);
				}
			}
		});
	}

	private void initImage() {
		imgMyAccount.setBackgroundResource(R.drawable.footer_myaccount);
		imgIndex.setBackgroundResource(R.drawable.footer_home);
		imgMain.setBackgroundResource(R.drawable.footer_product);
		imgMore.setBackgroundResource(R.drawable.footer_more);

		imgMyAccount.setLayoutParams(new LinearLayout.LayoutParams(40, 40));
		imgIndex.setLayoutParams(new LinearLayout.LayoutParams(40, 40));
		imgMain.setLayoutParams(new LinearLayout.LayoutParams(40, 40));
		imgMore.setLayoutParams(new LinearLayout.LayoutParams(40, 40));
	}
}
