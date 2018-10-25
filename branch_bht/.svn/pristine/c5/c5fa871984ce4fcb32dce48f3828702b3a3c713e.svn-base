package com.banhuitong.activity.jx;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

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

import com.banhuitong.activity.BaseActivity;
import com.banhuitong.activity.R;
import com.banhuitong.activity.ShowWebView2Activity;
import com.banhuitong.async.DefaultThreadPool;
import com.banhuitong.async.jx.JxSendMobileCodeTask;
import com.banhuitong.cache.CacheObject;
import com.banhuitong.util.ActivityUtils;
import com.banhuitong.util.Constants;
import com.banhuitong.util.ViewUtils;

public class JxSetTradePwdActivity extends BaseActivity {

	private ImageView imgBack;
	private TextView tvSubmit;
	private TextView tvMobileCode;
	private EditText etMobileCode;

	private String mobile;
	private String mobileCode;
	private int countdown = 59;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jx_set_trade_pwd);
		init();
		setListener();
	}

	@Override
	protected void onStart() {
		super.onStart();
		JSONObject personalInfo = CacheObject.getInstance().getPersonInfo();
		if (personalInfo != null) {
			try {
				mobile = personalInfo.get("mobile").toString();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	private void init() {
		loading = ViewUtils.initRotateAnimation(this);

		imgBack = (ImageView) findViewById(R.id.back);
		tvSubmit = (TextView) findViewById(R.id.tv_submit);
		tvMobileCode = (TextView) findViewById(R.id.tv_mobile_code);
		etMobileCode = (EditText) findViewById(R.id.et_mobile_code);

		TextView tvTitle = (TextView) findViewById(R.id.tv_title);
		tvTitle.setText("设置交易密码");
	}

	private void setListener() {
		imgBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				back();
			}
		});

		tvSubmit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mobileCode = etMobileCode.getText().toString().trim();

				if (validate()) {
					Intent intent = new Intent(JxSetTradePwdActivity.this,
							ShowWebView2Activity.class);
					intent.putExtra("url", Constants.mobileUrl
							+ "account/to-jxpay-password.html?mobile-number="
							+ mobile + "&&mobile-code=" + mobileCode);
					intent.putExtra("title", "设置交易密码");
					startActivity(intent);
					JxSetTradePwdActivity.this.finish();
				}
			}
		});

		tvMobileCode.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DefaultThreadPool.getInstance().execute(
						new JxSendMobileCodeTask(handler, mobile, "2"));
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
			loading.setVisibility(View.GONE);

			switch (msg.what) {
			case Constants.MOBILE_CODE_INVALID:
				Map<String, Object> map = (Map<String, Object>) msg.obj;
				ViewUtils.showDialog("", "", "提示", map.get("errorMsg")
						.toString(), JxSetTradePwdActivity.this,
						ViewUtils.Button_type_sure, null);
				break;
			case Constants.SEND_MOBILE_CODE_FAILED:
				Map<String, Object> map2 = (Map<String, Object>) msg.obj;
				ViewUtils.showDialog("", "", "提示", map2.get("errorMsg")
						.toString(), JxSetTradePwdActivity.this,
						ViewUtils.Button_type_sure, null);
				break;
			case Constants.SEND_MOBILE_CODE_SUCCESS:
				ViewUtils.showDialog("", "", "提示", "手机激活码已发送到手机【"
						+ ActivityUtils.formatMobile(mobile) + "】上，请注意查收",
						JxSetTradePwdActivity.this, ViewUtils.Button_type_sure,
						null);
				handler.postDelayed(runnable, 1000);
				break;
			default:
			}
		}
	};

	private boolean validate() {

		if ("".equals(mobileCode)) {
			ViewUtils.showDialog("", "", "提示", "验证码不能为空！",
					JxSetTradePwdActivity.this, ViewUtils.Button_type_sure,
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
				tvMobileCode.setText("获取验证码");
				tvMobileCode.setBackgroundResource(R.drawable.button_style);
				tvMobileCode.setEnabled(true);
				countdown = 59;
			}
		}
	};

	private void back() {
		JxSetTradePwdActivity.this.finish();
	}
}
