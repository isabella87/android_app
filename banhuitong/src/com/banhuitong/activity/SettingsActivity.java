package com.banhuitong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SettingsActivity extends BaseActivity {
	
	private ImageView imgBack;
	private RelativeLayout rlGestureLockSet,rlGestureLockCheck;
	private TextView tvGestureLock;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		init();
		setListener();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		if(mApp.hasGestureLock()){
			tvGestureLock.setBackgroundResource(R.drawable.switch_on);
			rlGestureLockCheck.setVisibility(View.VISIBLE);
		}else{
			tvGestureLock.setBackgroundResource(R.drawable.switch_off);
			rlGestureLockCheck.setVisibility(View.GONE);
		}
	}
	
	private void init() {
		imgBack = (ImageView) findViewById(R.id.back);
		rlGestureLockSet = (RelativeLayout) findViewById(R.id.layer_gesture_lock);
		rlGestureLockCheck = (RelativeLayout) findViewById(R.id.layer_change_gesture_lock);
		tvGestureLock = (TextView) findViewById(R.id.tv_gesture_lock);
		
		TextView tvTitle = (TextView)findViewById(R.id.tv_title);
		tvTitle.setText("设置");
	}
	
	private void setListener() {
		imgBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SettingsActivity.this.finish();
			}
		});
		
		rlGestureLockSet.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mApp.hasGestureLock()){
					Intent intent = new Intent(SettingsActivity.this, GestureLockRemoveActivity.class);
					startActivity(intent); 
				}else{
					Intent intent = new Intent(SettingsActivity.this, GestureLockCreateActivity.class);
					startActivity(intent); 
				}
			}
		});
		
		rlGestureLockCheck.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mApp.hasGestureLock()){
					Intent intent = new Intent(SettingsActivity.this, GestureLockPreUpdateActivity.class);
					startActivity(intent); 
				}
			}
		});
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			SettingsActivity.this.finish();
			return true;
		}
		return false;
	}
	
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
		}
	};

}
