package com.banhuitong.activity;

import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.banhuitong.activity.R.drawable;
import com.banhuitong.async.DefaultThreadPool;
import com.banhuitong.async.ImageLoadTask;
import com.banhuitong.async.SendMobileCodeTask;
import com.banhuitong.async.ValidateMobileCodeTask;
import com.banhuitong.util.ActivityUtils;
import com.banhuitong.util.Constants;
import com.banhuitong.util.Urls;
import com.banhuitong.util.ViewUtils;

public class RegisterStep1Activity extends BaseActivity {

	private ImageView imgBackInStep1;
	private TextView tvNextInStep1;
	private TextView tvMobileCode;
	private ImageView imgCaptchaCode;
	private EditText etMobileCode;
	private EditText etCaptchaCode;
	private CheckBox chbAgreement;
	private TextView tvAgreement;

	private String mobile;
	private String mobileCode;
	private String captchaCode;
	private int countdown = 59;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_step1);
		init();
		setListener();
	}

	@Override
	protected void onStart() {
		super.onStart();

		ImageLoadTask imageLoadTask1 = new ImageLoadTask(
				getApplicationContext());
		imageLoadTask1.execute(imgCaptchaCode, Urls.URL_1, getResources());
	}

	private void init() {
		Intent intent = getIntent();
		mobile = intent.getStringExtra("mobile");

		imgBackInStep1 = (ImageView) findViewById(R.id.back);
		tvNextInStep1 = (TextView) findViewById(R.id.tv_next_in_step1);
		tvMobileCode = (TextView) findViewById(R.id.tv_mobile_code);
		imgCaptchaCode = (ImageView) findViewById(R.id.image_captcha_code);
		etMobileCode = (EditText) findViewById(R.id.et_mobile_code);
		etCaptchaCode = (EditText) findViewById(R.id.et_captcha_code);
		chbAgreement = (CheckBox) findViewById(R.id.chk_agreement);
		tvAgreement = (TextView) findViewById(R.id.tv_reg_agreement);

		TextView tvTitle = (TextView) findViewById(R.id.tv_title);
		tvTitle.setText("手机验证");
	}

	private void setListener() {
		imgBackInStep1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				back();
			}
		});

		tvNextInStep1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mobileCode = etMobileCode.getText().toString().trim();
				if (validate()) {
					DefaultThreadPool.getInstance().execute(
							new ValidateMobileCodeTask(handler, mobile,
									mobileCode, captchaCode));
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
				if (validate2()) {
					DefaultThreadPool.getInstance()
							.execute(
									new SendMobileCodeTask(handler, mobile,
											captchaCode));
				}
			}
		});

		tvAgreement.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(RegisterStep1Activity.this,
						ShowWebViewActivity.class);
				intent.putExtra("url", Constants.mobileUrl + "app/reg-contact.html");
				intent.putExtra("title", "注册协议");
				startActivity(intent);
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
				Intent intent = new Intent(RegisterStep1Activity.this,
						RegisterStep2Activity.class);
				intent.putExtra("mobile", mobile);
				startActivity(intent);
				RegisterStep1Activity.this.finish();
				break;
			case Constants.MOBILE_CODE_INVALID:
				Map<String, Object> map = (Map<String, Object>) msg.obj;
				ViewUtils.showDialog("", "", "提示", map.get("errorMsg")
						.toString(), RegisterStep1Activity.this,
						ViewUtils.Button_type_sure, null);
				break;
			case Constants.SEND_MOBILE_CODE_FAILED:
				Map<String, Object> map2 = (Map<String, Object>) msg.obj;
				ViewUtils.showDialog("", "", "提示", map2.get("errorMsg")
						.toString(), RegisterStep1Activity.this,
						ViewUtils.Button_type_sure, null);
				break;
			case Constants.SEND_MOBILE_CODE_SUCCESS:
				ViewUtils.showDialog("", "", "提示", "手机激活码已发送到手机【"
						+ ActivityUtils.formatMobile(mobile) + "】上，请注意查收",
						RegisterStep1Activity.this, ViewUtils.Button_type_sure,
						null);
				handler.postDelayed(runnable, 1000);
				break;
			default:
			}
		}
	};

	private boolean validate() {
		if ("".equals(mobileCode)) {
			ViewUtils.showDialog("", "", "提示", "激活码不能为空！",
					RegisterStep1Activity.this, ViewUtils.Button_type_sure,
					null);
			return false;
		}

		if (!chbAgreement.isChecked()) {
			ViewUtils.showDialog("", "", "提示", "请先阅读并勾选《班汇通注册协议》！",
					RegisterStep1Activity.this, ViewUtils.Button_type_sure,
					null);
			return false;
		}
		return true;
	}

	private boolean validate2() {
		if ("".equals(captchaCode)) {
			ViewUtils.showDialog("", "", "提示", "验证码不能为空！",
					RegisterStep1Activity.this, ViewUtils.Button_type_sure,
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
	
	private void back(){
		RegisterStep1Activity.this.finish();
	}
}
