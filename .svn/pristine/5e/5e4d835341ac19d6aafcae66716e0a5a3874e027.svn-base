package com.banhuitong.view;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.banhuitong.activity.R;
import com.banhuitong.activity.RegisterStep1Activity;
import com.banhuitong.async.DefaultThreadPool;
import com.banhuitong.async.IsRegisterTask;
import com.banhuitong.util.Constants;
import com.banhuitong.util.ViewUtils;

public class InputMobileDialog {
	private Context context;
	private AlertDialog ad;
	private TextView tvNext;
	private EditText etMobile;
	private ImageView imgBack;
	private String mobile;

	public InputMobileDialog(Context context) {
		this.context=context;
		ad = new AlertDialog.Builder(context).create();
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		RelativeLayout layout = (RelativeLayout)inflater.inflate(R.layout.input_mobile, null);
		ad.setView(layout);
		ad.show();

		// 关键在下面的两行,使用window.setContentView,替换整个对话框窗口的布局
		Window window = ad.getWindow();
		window.setContentView(R.layout.input_mobile);

		tvNext = (TextView) window.findViewById(R.id.tv_next);
		etMobile = (EditText) window.findViewById(R.id.dt_mobile);
		imgBack = (ImageView) window.findViewById(R.id.back);
		
		TextView tvTitle = (TextView) window.findViewById(R.id.tv_title);
		tvTitle.setText("填写手机号");
		
		setListener();
	}
	
	private void setListener() {
		imgBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ad.dismiss();
			}
		});
	}

	public void setPositiveButton(final View.OnClickListener listener) {
		tvNext.setOnClickListener(listener);
	}

	/**
	 * 关闭对话框
	 */
	public void dismiss() {
		ad.dismiss();
	}
	
	public void doNext() {
		mobile = etMobile.getText().toString().trim();
		if(validate()){
			DefaultThreadPool.getInstance().execute(new IsRegisterTask(handler, mobile));
		}
	}
	
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			
			switch (msg.what) {
				case Constants.REGISTER_ALREADY:
					Map<String,Object> map = (Map<String, Object>) msg.obj;
					String mobile = map.get("mobile").toString();
					
					ViewUtils.showDialog("", "", "提示", "该手机号已注册！", context, ViewUtils.Button_type_sure, null);
					break;
				case Constants.REGISTER_NOT_YET:
					Map<String,Object> map2 = (Map<String, Object>) msg.obj;
					String mobile2 = map2.get("mobile").toString();
					
					Intent intent2 = new Intent(context,RegisterStep1Activity.class);
					intent2.putExtra("mobile", mobile2);
					context.startActivity(intent2);
					((Activity)context).finish();
					break;
				default:
			}
		}
	};
	
	private boolean validate(){
		if("".equals(mobile)){
			ViewUtils.showDialog("", "", "提示", "手机号不能为空！", context, ViewUtils.Button_type_sure, null);
			return false;
		}
		
		Pattern pattern = Pattern.compile(Constants.PATTERN_MOBILE);
		Matcher matcher = pattern.matcher((CharSequence) mobile);
		boolean result = matcher.matches();
		if(!result){
			ViewUtils.showDialog("", "", "提示", "手机号无效！", context, ViewUtils.Button_type_sure, null);
			return false;
		}
		return true;
	}
}
