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
import com.banhuitong.async.LoginTask;
import com.banhuitong.async.RegisterTask;
import com.banhuitong.inf.MyDialogInterface;
import com.banhuitong.util.Constants;
import com.banhuitong.util.ViewUtils;

public class RegisterStep2Activity extends BaseActivity {
	
	private ImageView imgBackInStep2;
	private TextView tvRegSubmit;
	private EditText etRealname;
	private EditText etId;
	private EditText etUsername;
	private EditText etPassword;
	private EditText etRePassword;
	private EditText etRecommenderNo;
	private EditText etOrgCode;
	
	private String mobile;
	private String realname;
	private String idNo;
	private String username;
	private String password;
	private String rePassword;
	private String recommenderNo;
	private String orgCode;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_step2);
		init();
		setListener();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
	}
	
	private void init() {
		Intent intent = getIntent();
		mobile = intent.getStringExtra("mobile");
		
		imgBackInStep2 = (ImageView) findViewById(R.id.back);
		tvRegSubmit = (TextView) findViewById(R.id.tv_reg_submit);
		etRealname = (EditText) findViewById(R.id.et_realname);
		etId = (EditText) findViewById(R.id.et_id);
		etUsername = (EditText) findViewById(R.id.et_username);
		etPassword = (EditText) findViewById(R.id.et_password);
		etRePassword = (EditText) findViewById(R.id.et_repassword);
		etRecommenderNo = (EditText) findViewById(R.id.et_recommender_no);
		etOrgCode = (EditText) findViewById(R.id.dt_org_code);
		
		TextView tvTitle = (TextView)findViewById(R.id.tv_title);
		tvTitle.setText("完善资料");
	}
	
	private void setListener() {
		imgBackInStep2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(RegisterStep2Activity.this,RegisterStep1Activity.class);
				intent.putExtra("mobile", mobile);
				startActivity(intent);
				RegisterStep2Activity.this.finish();
			}
		});
		
		tvRegSubmit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				realname = etRealname.getText().toString().trim();
				idNo = etId.getText().toString().trim();
				username = etUsername.getText().toString().trim();
				password = etPassword.getText().toString().trim();
				rePassword = etRePassword.getText().toString().trim();
				recommenderNo = etRecommenderNo.getText().toString().trim();
				orgCode = etOrgCode.getText().toString().trim();
				
				if(validate()){
					RegisterTask task = new RegisterTask(handler, mobile, realname, idNo, username, password, recommenderNo, orgCode);
			  		DefaultThreadPool.getInstance().execute(task);
				}
			}
		});
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent(RegisterStep2Activity.this,RegisterStep1Activity.class);
			intent.putExtra("mobile", mobile);
			startActivity(intent);
			RegisterStep2Activity.this.finish();
			return true;
		}
		return false;
	}
	
	private boolean validate(){
		if("".equals(realname)){
			ViewUtils.showDialog("", "", "提示", "真实姓名不能为空！", RegisterStep2Activity.this, ViewUtils.Button_type_sure, null);
			return false;
		}
		
//		if("".equals(idNo)){
//			ViewUtils.showDialog("", "", "提示", "身份证号不能为空！", RegisterStep2Activity.this, ViewUtils.Button_type_sure, null);
//			return false;
//		}
		
		if("".equals(username)){
			ViewUtils.showDialog("", "", "提示", "用户名不能为空！", RegisterStep2Activity.this, ViewUtils.Button_type_sure, null);
			return false;
		}
		
		if("".equals(password)){
			ViewUtils.showDialog("", "", "提示", "密码不能为空！", RegisterStep2Activity.this, ViewUtils.Button_type_sure, null);
			return false;
		}
		
		if(!rePassword.equals(password)){
			ViewUtils.showDialog("", "", "提示", "密码不一致！", RegisterStep2Activity.this, ViewUtils.Button_type_sure, null);
			return false;
		}
		
		Pattern pattern = Pattern.compile(Constants.PATTERN_LETTER_NUMBER);
		Matcher matcher = pattern.matcher((CharSequence) recommenderNo);
		boolean result = matcher.matches();
		if(!result){
			ViewUtils.showDialog("", "", "提示", "推荐码只包含数字和字母！", RegisterStep2Activity.this, ViewUtils.Button_type_sure, null);
			return false;
		}
		
		return true;
	}
	
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			
			switch (msg.what) {
				case Constants.REGISTER_SUCCESS:
					LoginTask loginTask = new LoginTask(handler, etUsername.getText().toString().trim(), etPassword.getText().toString().trim());
					DefaultThreadPool.getInstance().execute(loginTask);
					break;
				case Constants.REGISTER_FAILED:
					Map<String,Object> map = (Map<String, Object>) msg.obj;
					ViewUtils.showDialog("", "", "提示", map.get("errorMsg").toString(),RegisterStep2Activity.this, ViewUtils.Button_type_sure, null);
					break;
				case Constants.LOGIN_SUCCESS:
					Map<String,Object> map2 = (Map<String, Object>) msg.obj;
					mApp.setSavedPassword(map2.get(Constants.USER_PASS).toString());
					mApp.setSavedUsername(map2.get(Constants.USER_NAME).toString());
					login();					

					ViewUtils.showDialog("", "", "提示", "注册成功。", RegisterStep2Activity.this, ViewUtils.Button_type_sure, new MyDialogInterface() {
						
						@Override
						public void onButtonSure() {
							Intent intent = new Intent();  
			                intent.setAction(Constants.ACTION_LOG_IN);  
			                sendBroadcast(intent);  
							RegisterStep2Activity.this.finish();
						}
						
						@Override
						public void onButtonCancel() {
						}
					});
					break;
				default:
			}
		}
	};
}
