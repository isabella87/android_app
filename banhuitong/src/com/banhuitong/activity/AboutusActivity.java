package com.banhuitong.activity;

import com.banhuitong.util.Constants;

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

public class AboutusActivity extends BaseActivity {
	
	private ImageView imgBack;
	private RelativeLayout rlCompanyBrief;
	private RelativeLayout rlManagementStaff;
	private RelativeLayout rlPlatformValue;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aboutus);
		init();
		setListener();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
	}
	
	private void init() {
		imgBack = (ImageView) findViewById(R.id.back);
		rlCompanyBrief = (RelativeLayout) findViewById(R.id.layer_company_brief);
		rlManagementStaff = (RelativeLayout) findViewById(R.id.layer_management_staff);
		rlPlatformValue = (RelativeLayout) findViewById(R.id.layer_platform_value);
		
		TextView tvTitle = (TextView)findViewById(R.id.tv_title);
		tvTitle.setText("了解我们");
	}
	
	private void setListener() {
		imgBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AboutusActivity.this.finish();
			}
		});
		
		rlCompanyBrief.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(AboutusActivity.this, ShowWebViewActivity.class);
				intent.putExtra("url", Constants.mobileUrl + "app/aboutUs.html");
				intent.putExtra("title", "公司简介");
				startActivity(intent); 
			}
		});
		
		rlManagementStaff.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(AboutusActivity.this, ShowWebViewActivity.class);
				intent.putExtra("url", Constants.mobileUrl + "app/manager-team.html");
				intent.putExtra("title", "管理团队");
				startActivity(intent); 
			}
		});
		
		rlPlatformValue.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(AboutusActivity.this, ShowWebViewActivity.class);
				intent.putExtra("url", "http://mp.weixin.qq.com/s?__biz=MzA5NTQ2NDc4Mw==&mid=2650324799&idx=2&sn=f95cf0d5f6a44e4a7116a6191a693ba0#rd");
				intent.putExtra("title", "平台价值");
				startActivity(intent); 
			}
		});
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			AboutusActivity.this.finish();
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
