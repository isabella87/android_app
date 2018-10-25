package com.banhuitong.activity;

import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.banhuitong.async.DefaultThreadPool;
import com.banhuitong.async.LoginTask;
import com.banhuitong.util.ActivityUtils;
import com.banhuitong.util.Constants;
import com.banhuitong.util.ViewUtils;
import com.banhuitong.view.InputMobileDialog;

public class LoginActivity extends BaseActivity {

	private ImageView imgBack;
	private TextView tvLogin;
	private EditText etUsername;
	private EditText etPassword;
	private CheckBox chkMemorizeUsername;
	private TextView tvRegister;
	private TextView tvFindbackPwd;

	private String username;
	private String password;
	private Vibrator vibrator;

	@Override
	protected void onStop() {
		super.onStop();
		Log.i("", "调用onStop");
		vibrator.cancel();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("", "调用onCreate");
		setContentView(R.layout.login);
		init();
		setListener();
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.i("", "调用onStart");
	}

	private void init() {
		loading = ViewUtils.initRotateAnimation(this);

		imgBack = (ImageView) findViewById(R.id.back);
		tvLogin = (TextView) findViewById(R.id.tv_login);
		etUsername = (EditText) findViewById(R.id.et_username);
		etPassword = (EditText) findViewById(R.id.et_password);
		chkMemorizeUsername = (CheckBox) findViewById(R.id.chk_memorize_username);
		tvRegister = (TextView) findViewById(R.id.tv_register);
		tvFindbackPwd = (TextView) findViewById(R.id.tv_findback_pwd);

		TextView tvTitle = (TextView) findViewById(R.id.tv_title);
		tvTitle.setText("登录");

		Drawable drawable = getResources().getDrawable(R.drawable.ic_user);
		drawable.setBounds(0, 0, MyApplication.screen_w / 11,
				MyApplication.screen_w / 17);
		etUsername.setCompoundDrawables(drawable, null, null, null);
		etUsername.setCompoundDrawablePadding(5);

		drawable = getResources().getDrawable(R.drawable.ic_password);
		drawable.setBounds(0, 0, MyApplication.screen_w / 11,
				MyApplication.screen_w / 17);
		etPassword.setCompoundDrawables(drawable, null, null, null);
		etPassword.setCompoundDrawablePadding(5);

		if (isMemoryUsername()) {
			chkMemorizeUsername.setChecked(true);
			etUsername.setText(getMemoryUsername());
		} else {
			// etUsername.setText(getPhoneNumber());
		}

		vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
	}

	private void setListener() {
		imgBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				LoginActivity.this.finish();
			}
		});

		tvLogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				username = etUsername.getText().toString().trim();
				password = etPassword.getText().toString().trim();

				if (validate()) {
					loading.setVisibility(View.VISIBLE);
					DefaultThreadPool.getInstance().execute(
							new LoginTask(handler, username, password));

					if (chkMemorizeUsername.isChecked()) {
						memorizeUsername(username);
					} else {
						unmemorizeUsername();
					}
				}
			}
		});

		tvRegister.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// ActivityUtils.startIntent(LoginActivity.this,
				// RegisterOrLoginActivity.class);
				// LoginActivity.this.finish();

				final InputMobileDialog ad = new InputMobileDialog(
						LoginActivity.this);
				ad.setPositiveButton(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						ad.doNext();
					}
				});
			}
		});

		tvFindbackPwd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoginActivity.this,
						FindbackPwdActivity.class);
				startActivity(intent);
			}
		});

	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		Log.i("", "调用onRestart");
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.i("", "调用onResume");
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.i("", "调用onPause");
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			LoginActivity.this.finish();
			return true;
		}
		return false;
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			loading.setVisibility(View.GONE);

			switch (msg.what) {
			case Constants.LOGIN_SUCCESS:
				Toast.makeText(getApplicationContext(), "登录成功。", 1).show();
				Map<String, Object> map = (Map<String, Object>) msg.obj;
				mApp.setSavedPassword(map.get(Constants.USER_PASS).toString());
				mApp.setSavedUsername(map.get(Constants.USER_NAME).toString());
				login();

				Intent intent = new Intent();
				intent.setAction(Constants.ACTION_LOG_IN);
				sendBroadcast(intent);
				LoginActivity.this.finish();
				break;
			case Constants.LOGIN_FAILED:
				ViewUtils.showDialog("", "", "提示", "登录失败！", LoginActivity.this,
						ViewUtils.Button_type_sure, null);
				break;
			default:
			}
		}
	};

	private boolean validate() {
		if ("".equals(username)) {
			vibrator.vibrate(100);
			ViewUtils.showDialog("", "", "提示", "用户名不能为空！", LoginActivity.this,
					ViewUtils.Button_type_sure, null);
			return false;
		}

		if ("".equals(password)) {
			vibrator.vibrate(100);
			ViewUtils.showDialog("", "", "提示", "密码不能为空！", LoginActivity.this,
					ViewUtils.Button_type_sure, null);
			return false;
		}
		return true;
	}

	private void memorizeUsername(String username) {
		Editor ed = MyApplication.sp.edit();
		ed.putString(Constants.MEMORY_USERNAME, username);
		ed.commit();
	}

	private void unmemorizeUsername() {
		Editor ed = MyApplication.sp.edit();
		ed.putString(Constants.MEMORY_USERNAME, "");
		ed.commit();
	}

	private String getMemoryUsername() {
		return MyApplication.sp.getString(Constants.MEMORY_USERNAME, "");
	}

	public boolean isMemoryUsername() {
		return !"".equals(MyApplication.sp.getString(Constants.MEMORY_USERNAME,
				""));
	}
}
