package com.banhuitong.activity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.banhuitong.async.BhbCaApplyTask;
import com.banhuitong.async.DefaultThreadPool;
import com.banhuitong.async.GetPrjBhbDetailTask;
import com.banhuitong.async.SendMobileCodeTask2;
import com.banhuitong.cache.CacheObject;
import com.banhuitong.inf.MyDialogInterface;
import com.banhuitong.util.ActivityUtils;
import com.banhuitong.util.Constants;
import com.banhuitong.util.ViewUtils;

public class BhbCaApplyActivity extends BaseActivity {
	
	private ImageView imgBack;
	private TextView tvRemainingDays;
	private TextView tvCurRate;
	private TextView tvCurAmt;
	private TextView tvUnpaid;
	private EditText etWaitingDays;
	private EditText etAssignAmt;
	private EditText etMobileCode;
	private TextView tvMobileCode;
	private EditText etPassword;
	private TextView tvItemNo;
	private TextView tvItemShowName;
	private TextView tvApply;
	
	private BigDecimal maxAssignAmt = BigDecimal.ZERO;
	private BigDecimal minAssignAmt = BigDecimal.ZERO;
	private long waitingDays = 0;
	private long daysRemaining = 0;
	private long expectedBorrowTime = 0;
	private long borrowDays = 0;
	private long maxWaitingDays = 0;
	private BigDecimal unPaiedBonusAmt = BigDecimal.ZERO;
	private String mobileCode = "";
	private String password = "";
	private BigDecimal assignAmt = BigDecimal.ZERO;
	private String mobile = "";
	
	private int tiId;
	private Intent intentLast;
	
	private InputMethodManager inputMethodManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bhb_ca_apply);
		init();
		setListener();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		loading.setVisibility(View.VISIBLE);
		
		JSONObject personalInfo = CacheObject.getInstance().getPersonInfo();
		if(personalInfo!=null){
			try {
				mobile = personalInfo.get("mobile").toString();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		DefaultThreadPool.getInstance().execute(new GetPrjBhbDetailTask(handler,tiId));
	}
	
	private void init() {
		loading = ViewUtils.initRotateAnimation(this);
		
	    intentLast = getIntent();
		tiId = Integer.parseInt(intentLast.getStringExtra("tiId"));
		
		inputMethodManager = (InputMethodManager) 
				getApplicationContext().getSystemService(
						INPUT_METHOD_SERVICE);
		
		imgBack = (ImageView) findViewById(R.id.back);
		tvRemainingDays = (TextView) findViewById(R.id.tv_remaining_days);
		tvCurRate = (TextView) findViewById(R.id.tv_ori_money_rate);
		tvCurAmt = (TextView) findViewById(R.id.tv_cur_amt);
		tvUnpaid = (TextView) findViewById(R.id.tv_unpaid);
		etWaitingDays = (EditText) findViewById(R.id.et_waiting_days);
		etAssignAmt = (EditText) findViewById(R.id.et_assign_amt);
		etMobileCode = (EditText) findViewById(R.id.et_mobile_code);
		tvMobileCode = (TextView) findViewById(R.id.tv_mobile_code);
		etPassword = (EditText) findViewById(R.id.et_password);

		tvItemNo = (TextView) findViewById(R.id.tv_item_no);
		tvItemShowName = (TextView) findViewById(R.id.tv_item_show_name);
		tvApply = (TextView) findViewById(R.id.tv_apply);
		
		TextView tvTitle = (TextView)findViewById(R.id.tv_title);
		tvTitle.setText("转让申请");
	}
	
	@Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
        	inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        return super.dispatchTouchEvent(ev);
    }
	
	private void setListener() {
		
		imgBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				BhbCaApplyActivity.this.finish();
			}
		});
		
		tvMobileCode.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DefaultThreadPool.getInstance().execute(new SendMobileCodeTask2(handler, mobile));
			}
		});
		
		etAssignAmt.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {  
			    @Override  
			    public void onFocusChange(View v, boolean hasFocus) {  
			    	
			    }
		});
		
		tvApply.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(validate()){
					ViewUtils.showDialog("", "", "提示", "确认提交申请？", BhbCaApplyActivity.this, ViewUtils.Button_type_all, new MyDialogInterface() {
						
						@Override
						public void onButtonSure() {
							loading.setVisibility(View.VISIBLE);
							BhbCaApplyTask task = new BhbCaApplyTask(handler, 
									Integer.toString(tiId), mobileCode, password, Long.toString(waitingDays), 
									assignAmt.toString());
					  		DefaultThreadPool.getInstance().execute(task);
						}
						
						@Override
						public void onButtonCancel() {
						}
					});
				}
			}
		});
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			BhbCaApplyActivity.this.finish();
			return true;
		}
		return false;
	}
	
	private boolean validate() {
		waitingDays = NumberUtils.toLong(etWaitingDays.getText().toString(),0);
		mobileCode = etMobileCode.getText().toString();
		password = etPassword.getText().toString();
		
		if(!(waitingDays>0)){
			ViewUtils.showDialog("", "", "提示", "有效转让期1—" + maxWaitingDays + "天！", BhbCaApplyActivity.this, ViewUtils.Button_type_sure, null);
			etWaitingDays.requestFocus();
			return false;
		}
		
		if(waitingDays>maxWaitingDays){
			ViewUtils.showDialog("", "", "提示", "有效转让期1—" + maxWaitingDays + "天！", BhbCaApplyActivity.this, ViewUtils.Button_type_sure, null);
			etWaitingDays.requestFocus();
			return false;
		}
		
		
		if(etAssignAmt.getText().length()==0){
			ViewUtils.showDialog("", "", "提示", "转让标价不能为空！", BhbCaApplyActivity.this, ViewUtils.Button_type_sure, null);
			etAssignAmt.requestFocus();
			return false;
		}
		
		assignAmt = new BigDecimal(etAssignAmt.getText().toString());
		if(!(assignAmt.compareTo(minAssignAmt)>=0 && assignAmt.compareTo(maxAssignAmt)<=0)){
			ViewUtils.showDialog("", "", "提示", "有效转让标价" + minAssignAmt.setScale(0,RoundingMode.DOWN) + "—" + maxAssignAmt.setScale(0,RoundingMode.DOWN) + "！", BhbCaApplyActivity.this, ViewUtils.Button_type_sure, null);
			etAssignAmt.requestFocus();
			return false;
		}	
		
		if(mobileCode.length()==0){
			ViewUtils.showDialog("", "", "提示", "手机验证码不能为空！", BhbCaApplyActivity.this, ViewUtils.Button_type_sure, null);
			etMobileCode.requestFocus();
			return false;
		}
		
		if(password.length()==0){
			ViewUtils.showDialog("", "", "提示", "登录密码不能为空！", BhbCaApplyActivity.this, ViewUtils.Button_type_sure, null);
			etPassword.requestFocus();
			return false;
		}
		
		return true;
	}
	
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			loading.setVisibility(View.GONE);
			
			switch (msg.what) {
				case Constants.GET_PRJ_BHB_DETAIL:
					Map<String,Object> map = (Map<String, Object>) msg.obj;
					
					maxAssignAmt = ((BigDecimal)map.get("curAmt")).setScale(0, RoundingMode.DOWN);
					minAssignAmt = ((BigDecimal)map.get("amt")).multiply(BigDecimal.valueOf(0.8)).setScale(0, RoundingMode.DOWN);
					unPaiedBonusAmt = ((BigDecimal)map.get("unPaiedBonusAmt")).setScale(2, RoundingMode.DOWN);
//					daysRemaining = NumberUtils.toLong(map.get("daysRemaining").toString(),0);
					expectedBorrowTime = (Long)map.get("expectedBorrowTime");
					borrowDays = (Long)map.get("borrowDays");
					
					Date today = new Date();
					daysRemaining = (expectedBorrowTime + borrowDays * 24 * 60 * 60 * 1000 - today.getTime()) / (1000 * 24 * 60 * 60);
					 			 
					maxWaitingDays = daysRemaining-2>=0?daysRemaining-2:0;
					
					 if(((BigDecimal)map.get("curRate")).compareTo(BigDecimal.valueOf(24))>0){
						 tvCurRate.setText("大于24");
				     }else{
				    	 tvCurRate.setText(ViewUtils.mformat.format(map.get("curRate")));
				     }
					
					tvItemNo.setText(map.get("itemNo").toString());
					tvItemShowName.setText(map.get("itemShowName").toString());
					tvRemainingDays.setText(String.valueOf(daysRemaining));
					tvCurAmt.setText(((BigDecimal)map.get("curAmt")).setScale(0, RoundingMode.DOWN).toString());
					tvUnpaid.setText(String.valueOf(unPaiedBonusAmt));
					
					etWaitingDays.setHint("最长转让期" + maxWaitingDays);
					etAssignAmt.setHint(minAssignAmt + "—" + maxAssignAmt);
					
					break;
				case Constants.SEND_MOBILE_CODE_FAILED:
					Map<String,Object> map2 = (Map<String, Object>) msg.obj;
					ViewUtils.showDialog("", "", "提示", map2.get("errorMsg").toString(),BhbCaApplyActivity.this, ViewUtils.Button_type_sure, null);
					break;
				case Constants.SEND_MOBILE_CODE_SUCCESS:
					ViewUtils.showDialog("", "", "提示", "手机激活码已发送到手机【" + ActivityUtils.formatMobile(mobile) + "】上，请注意查收", BhbCaApplyActivity.this, ViewUtils.Button_type_sure, null);
					break;
				case Constants.CA_APPLY_SUCCESS:
						ViewUtils.showDialog("", "", "提示", "申请转让成功。", BhbCaApplyActivity.this, ViewUtils.Button_type_sure, new MyDialogInterface() {
						
						@Override
						public void onButtonSure() {
							BhbCaApplyActivity.this.setResult(Constants.RESULT_RESTART_SELF, intentLast);
							BhbCaApplyActivity.this.finish();
						}
						
						@Override
						public void onButtonCancel() {
						}
					});
					break;
				case Constants.CA_APPLY_FAILED:
					Map<String,Object> map3 = (Map<String, Object>) msg.obj;
					ViewUtils.showDialog("", "", "提示", map3.get("errorMsg").toString(),BhbCaApplyActivity.this, ViewUtils.Button_type_sure, null);
					break;
				default:
			}
		}
	};
}
