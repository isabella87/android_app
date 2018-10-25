package com.banhuitong.activity;

import java.util.Map;

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
import com.banhuitong.async.FindbackPwdTask;
import com.banhuitong.async.ImageLoadTask;
import com.banhuitong.async.SendMobileCodeLostPwdTask;
import com.banhuitong.inf.MyDialogInterface;
import com.banhuitong.util.ActivityUtils;
import com.banhuitong.util.Constants;
import com.banhuitong.util.Urls;
import com.banhuitong.util.ViewUtils;

public class FindbackPwdActivity extends BaseActivity {

	private ImageView imgBack;
	private TextView tvFindbackPwd;
	private TextView tvMobileCode;
	private ImageView imgCaptchaCode;
	private EditText etMobileCode;
	private EditText etCaptchaCode;
	private EditText etUsername;

	private String usernameOrMobile;
	private String mobileCode;
	private String captchaCode;
	private int countdown = 59;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.findback_pwd);
		init();
		setListener();
	}

	@Override
	protected void onStart() {
		super.onStart();

		ImageLoadTask imageLoadTask1 = new ImageLoadTask(
				getApplicationContext());
//		imageLoadTask1.execute(imgCaptchaCode, Constants.serverUrlAcc
//				+ "/reg/captcha-image", getResources());
		imageLoadTask1.execute(imgCaptchaCode, Urls.URL_1, getResources());
	}

	private void init() {

		imgBack = (ImageView) findViewById(R.id.back);
		tvFindbackPwd = (TextView) findViewById(R.id.tv_next_in_step1);
		tvMobileCode = (TextView) findViewById(R.id.tv_mobile_code);
		imgCaptchaCode = (ImageView) findViewById(R.id.image_captcha_code);
		etMobileCode = (EditText) findViewById(R.id.et_mobile_code);
		etCaptchaCode = (EditText) findViewById(R.id.et_captcha_code);
		etUsername = (EditText) findViewById(R.id.et_username);

		TextView tvTitle = (TextView) findViewById(R.id.tv_title);
		tvTitle.setText("找回密码");
	}

	private void setListener() {
		imgBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				back();
			}
		});

		tvFindbackPwd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mobileCode = etMobileCode.getText().toString().trim();
				usernameOrMobile = etUsername.getText().toString().trim();

				if (validate()) {
					DefaultThreadPool.getInstance().execute(
							new FindbackPwdTask(handler, usernameOrMobile,
									mobileCode));
				}
			}
		});

		imgCaptchaCode.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ImageLoadTask imageLoadTask1 = new ImageLoadTask(
						getApplicationContext());
				imageLoadTask1.execute(imgCaptchaCode, Urls.URL_1, getResources());
			}
		});

		tvMobileCode.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				captchaCode = etCaptchaCode.getText().toString().trim();
				usernameOrMobile = etUsername.getText().toString().trim();

				if (validate2()) {
					DefaultThreadPool.getInstance().execute(
							new SendMobileCodeLostPwdTask(handler,
									usernameOrMobile, captchaCode));
				}
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			back();
			return true;
		}
		return false;
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			switch (msg.what) {
			case Constants.MOBILE_CODE_VALID:
				
				ViewUtils.showDialog("", "", "提示", "密码已发送到手机上，请注意查收",
						FindbackPwdActivity.this,
						ViewUtils.Button_type_confirm, new MyDialogInterface() {
							
							@Override
							public void onButtonSure() {
								FindbackPwdActivity.this.finish();
							}
							
							@Override
							public void onButtonCancel() {
							}
						});
				break;
			case Constants.MOBILE_CODE_INVALID:
				Map<String, Object> map = (Map<String, Object>) msg.obj;
				ViewUtils.showDialog("", "", "提示", map.get("errorMsg")
						.toString(), FindbackPwdActivity.this,
						ViewUtils.Button_type_confirm, null);
				break;
			case Constants.SEND_MOBILE_CODE_FAILED:
				Map<String, Object> map2 = (Map<String, Object>) msg.obj;
				ViewUtils.showDialog("", "", "提示", map2.get("errorMsg")
						.toString(), FindbackPwdActivity.this,
						ViewUtils.Button_type_confirm, null);
				break;
			case Constants.SEND_MOBILE_CODE_SUCCESS:
				ViewUtils.showDialog("", "", "提示", "手机激活码已发送到手机【"
						+ ActivityUtils.formatMobile((String) msg.obj)
						+ "】上，请注意查收", FindbackPwdActivity.this,
						ViewUtils.Button_type_confirm, null);
				handler.postDelayed(runnable, 1000);
				break;
			default:
			}
		}
	};

	private boolean validate() {
		if ("".equals(usernameOrMobile)) {
			ViewUtils.showDialog("", "", "提示", "请输入正确的用户名或手机号码！",
					FindbackPwdActivity.this, ViewUtils.Button_type_confirm,
					null);
			return false;
		}

		if ("".equals(mobileCode)) {
			ViewUtils.showDialog("", "", "提示", "激活码不能为空！",
					FindbackPwdActivity.this, ViewUtils.Button_type_confirm,
					null);
			return false;
		}
		return true;
	}

	private boolean validate2() {
		if ("".equals(usernameOrMobile)) {
			ViewUtils.showDialog("", "", "提示", "请输入正确的用户名或手机号码！",
					FindbackPwdActivity.this, ViewUtils.Button_type_confirm,
					null);
			return false;
		}

		if ("".equals(captchaCode)) {
			ViewUtils.showDialog("", "", "提示", "验证码不能为空！",
					FindbackPwdActivity.this, ViewUtils.Button_type_confirm,
					null);
			return false;
		}
		return true;
	}

	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			if (countdown > 0) {
				tvMobileCode.setText(countdown-- + "秒后重试");
				tvMobileCode
						.setBackgroundResource(R.drawable.button_style_disable);
				tvMobileCode.setEnabled(false);
				handler.postDelayed(this, 1000);
			} else {
				tvMobileCode.setText("获取激活码");
				tvMobileCode.setBackgroundResource(R.drawable.button_style);
				tvMobileCode.setEnabled(true);
				countdown = 59;
			}
		}
	};

	private void back() {
		FindbackPwdActivity.this.finish();
	}
}
