package com.banhuitong.activity;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.banhuitong.async.DefaultThreadPool;
import com.banhuitong.async.LogoutTask;
import com.banhuitong.cache.CacheObject;
import com.banhuitong.inf.MyDialogInterface;
import com.banhuitong.util.Constants;
import com.banhuitong.util.ViewUtils;

public class PersonalInfoActivity extends BaseActivity {
	
	private TextView tvLogout;
	private ImageView imgBack;
	private TextView tvMobile;
	private TextView tvIdcard;
	private TextView tvRealname;
	private TextView tvUsername;
	private Intent intentLast;  
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		intentLast = this.getIntent();
		setContentView(R.layout.personal_info);
		init();
		setListener();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
		JSONObject personalInfo = CacheObject.getInstance().getPersonInfo();
		if(personalInfo!=null){
			try {
				tvMobile.setText(personalInfo.get("mobile").toString());
				tvUsername.setText(personalInfo.get("loginName").toString());
				tvRealname.setText(personalInfo.get("realName").toString());
				tvIdcard.setText(personalInfo.get("idCard").toString());
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void init() {
		loading = ViewUtils.initRotateAnimation(this);
		
		tvLogout = (TextView) findViewById(R.id.tv_logout);
		tvMobile = (TextView) findViewById(R.id.tv_mobile);
		tvIdcard = (TextView) findViewById(R.id.tv_idcard);
		tvRealname = (TextView) findViewById(R.id.tv_realname);
		imgBack = (ImageView) findViewById(R.id.back);
		tvUsername = (TextView) findViewById(R.id.tv_username);
		
		TextView tvTitle = (TextView)findViewById(R.id.tv_title);
		tvTitle.setText("个人概况");
	}
	
	private void setListener() {
		imgBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				PersonalInfoActivity.this.finish();
			}
		});
		
		tvLogout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ViewUtils.showDialog("", "", "提示", "确认退出登录？", PersonalInfoActivity.this, ViewUtils.Button_type_all, new MyDialogInterface() {
					
					@Override
					public void onButtonSure() {
						loading.setVisibility(View.VISIBLE);
						DefaultThreadPool.getInstance().execute(new LogoutTask(handler));
					}
					
					@Override
					public void onButtonCancel() {
					}
				});
			}
		});
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			PersonalInfoActivity.this.finish();
			return true;
		}
		return false;
	}
	
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			
			switch (msg.what) {
//				case Constants.GET_PERSONAL_INFO:
//					 Map<String,String> personalInfo = (Map<String, String>) msg.obj;
//					 tvMobile.setText(personalInfo.get("mobile").toString());
//					 tvUsername.setText(personalInfo.get("loginName").toString());
//					 tvRealname.setText(personalInfo.get("realName").toString());
//					 tvIdcard.setText(personalInfo.get("idCard").toString());
//					break;
				case Constants.LOGOUT_SUCCESS:
					Intent intent = new Intent();  
	                intent.setAction(Constants.ACTION_LOG_OUT);  
	                sendBroadcast(intent);  
	                
	                SystemClock.sleep(2000);
	                loading.setVisibility(View.GONE);
	                PersonalInfoActivity.this.setResult(Constants.RESULT_BACK_INDEX, intentLast);
	                PersonalInfoActivity.this.finish();
					break;
				default:
			}
		}
	};
}
