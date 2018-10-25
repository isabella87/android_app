package com.banhuitong.activity;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.banhuitong.async.DefaultThreadPool;
import com.banhuitong.async.IsRegisterTask;
import com.banhuitong.util.ActivityUtils;
import com.banhuitong.util.Constants;
import com.banhuitong.util.ViewUtils;

public class RegisterOrLoginActivity extends BaseActivity {
	
	private ImageView imgBack;
	private TextView tvNext;
	private EditText etMobile;
	
	private String mobile;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.input_mobile);
		init();
		setListener();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	private void init() {
		imgBack = (ImageView) findViewById(R.id.back);
		tvNext = (TextView) findViewById(R.id.tv_next);
		etMobile = (EditText) findViewById(R.id.dt_mobile);
		
		TextView tvTitle = (TextView)findViewById(R.id.tv_title);
		tvTitle.setText("填写手机号");
	}
	
	private void setListener() {
		imgBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				RegisterOrLoginActivity.this.finish();
			}
		});
		
		tvNext.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mobile = etMobile.getText().toString().trim();
				if(validate()){
					DefaultThreadPool.getInstance().execute(new IsRegisterTask(handler, mobile));
				}
			}
		});
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			ActivityUtils.startIntent(RegisterOrLoginActivity.this,LoginActivity.class);
			RegisterOrLoginActivity.this.finish();
			return true;
		}
		return false;
	}
	
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			
			switch (msg.what) {
				case Constants.REGISTER_ALREADY:
					Map<String,Object> map = (Map<String, Object>) msg.obj;
					String mobile = map.get("mobile").toString();
					
//					Intent intent = new Intent(RegisterOrLoginActivity.this,LoginActivity.class);
//					intent.putExtra("mobile", mobile);
//					startActivity(intent);
//					RegisterOrLoginActivity.this.finish();
					
					ViewUtils.showDialog("", "", "提示", "该手机号已注册！", RegisterOrLoginActivity.this, ViewUtils.Button_type_sure, null);
					break;
				case Constants.REGISTER_NOT_YET:
					Map<String,Object> map2 = (Map<String, Object>) msg.obj;
					String mobile2 = map2.get("mobile").toString();
					
					Intent intent2 = new Intent(RegisterOrLoginActivity.this,RegisterStep1Activity.class);
					intent2.putExtra("mobile", mobile2);
					startActivity(intent2);
					RegisterOrLoginActivity.this.finish();
					break;
				default:
			}
		}
	};
	
	private boolean validate(){
		if("".equals(mobile)){
			ViewUtils.showDialog("", "", "提示", "手机号不能为空！", RegisterOrLoginActivity.this, ViewUtils.Button_type_sure, null);
			return false;
		}
		
		Pattern pattern = Pattern.compile(Constants.PATTERN_MOBILE);
		Matcher matcher = pattern.matcher((CharSequence) mobile);
		boolean result = matcher.matches();
		if(!result){
			ViewUtils.showDialog("", "", "提示", "手机号无效！", RegisterOrLoginActivity.this, ViewUtils.Button_type_sure, null);
			return false;
		}
		return true;
	}
}
