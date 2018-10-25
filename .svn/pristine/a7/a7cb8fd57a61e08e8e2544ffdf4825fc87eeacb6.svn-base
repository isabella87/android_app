package com.banhuitong.activity;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.banhuitong.async.CaApplyTask;
import com.banhuitong.async.DefaultThreadPool;
import com.banhuitong.async.GetPrjEntDetailTask;
import com.banhuitong.async.SendMobileCodeTask2;
import com.banhuitong.cache.CacheObject;
import com.banhuitong.inf.MyDialogInterface;
import com.banhuitong.util.ActivityUtils;
import com.banhuitong.util.Constants;
import com.banhuitong.util.ViewUtils;

public class CaApplyActivity extends BaseActivity {
	
	private ImageView imgBack;
	private TextView tvRemainingDays;
	private TextView tvOriMoneyRate;
	private TextView tvUnpaid;
	private EditText etWaitingDays;
	private RadioGroup rg;
	private RadioButton mRadio1, mRadio2;
	private TextView tvAssignAmt;
	private EditText etAssignAmt;
	private TextView tvAssignRate;
	private EditText etAssignRate;
	private EditText etMobileCode;
	private TextView tvMobileCode;
	private EditText etPassword;
	private TextView tvItemNo;
	private TextView tvItemShowName;
	private TextView tvApply;
	private CheckBox chbAgreement;
	private TextView tvAgreement;
	private LinearLayout llMain;
	
	private BigDecimal maxAssignAmt = BigDecimal.ZERO;
	private BigDecimal minAssignAmt = BigDecimal.ZERO;
	private BigDecimal maxAssignRate = BigDecimal.ZERO;
	private BigDecimal minAssignRate = BigDecimal.ZERO;
	private BigDecimal capital = BigDecimal.ZERO;
	private BigDecimal unpaidAmt = BigDecimal.ZERO;
	private long waitingDays = 0;
	private long daysRemaining = 0;
	private long maxWaitingDays = 0;
	private String mobileCode = "";
	private String password = "";
	private BigDecimal assignAmt = BigDecimal.ZERO;
	private BigDecimal assignRate = BigDecimal.ZERO;
	private String mobile = "";
	
	private int tiId;
	private int lockType = 1;
	private Intent intentLast;
	
	private InputMethodManager inputMethodManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ca_apply);
		init();
		setListener();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		loading.setVisibility(View.VISIBLE);
		mRadio1.performClick();
		
		JSONObject personalInfo = CacheObject.getInstance().getPersonInfo();
		if(personalInfo!=null){
			try {
				mobile = personalInfo.get("mobile").toString();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		DefaultThreadPool.getInstance().execute(new GetPrjEntDetailTask(handler,tiId));
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
		tvOriMoneyRate = (TextView) findViewById(R.id.tv_ori_money_rate);
		tvUnpaid = (TextView) findViewById(R.id.tv_unpaid);
		etWaitingDays = (EditText) findViewById(R.id.et_waiting_days);
		tvAssignAmt = (TextView) findViewById(R.id.tv_assign_amt);
		etAssignAmt = (EditText) findViewById(R.id.et_assign_amt);
		etAssignRate = (EditText) findViewById(R.id.et_assign_rate);
		tvAssignRate = (TextView) findViewById(R.id.tv_assign_rate);
		etMobileCode = (EditText) findViewById(R.id.et_mobile_code);
		tvMobileCode = (TextView) findViewById(R.id.tv_mobile_code);
		etPassword = (EditText) findViewById(R.id.et_password);
		rg = (RadioGroup) findViewById(R.id.rg_lock_type);
		mRadio1 = (RadioButton) findViewById(R.id.rb_lock_rate);
		mRadio2 = (RadioButton) findViewById(R.id.rb_lock_amt);
		tvItemNo = (TextView) findViewById(R.id.tv_item_no);
		tvItemShowName = (TextView) findViewById(R.id.tv_item_show_name);
		tvApply = (TextView) findViewById(R.id.tv_apply);
		chbAgreement = (CheckBox) findViewById(R.id.chk_agreement);
		tvAgreement = (TextView) findViewById(R.id.tv_agreement);
		llMain = (LinearLayout) findViewById(R.id.ll_main);
		
		TextView tvTitle = (TextView)findViewById(R.id.tv_title);
		tvTitle.setText("转让申请");
		
		rg.clearCheck();
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
				CaApplyActivity.this.finish();
			}
		});
		
		tvAgreement.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				String urlString = Constants.serverUrl + "export/creditassign.pdf";
			}
		});
		
		tvMobileCode.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DefaultThreadPool.getInstance().execute(new SendMobileCodeTask2(handler, mobile));
			}
		});
		
		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				
				if(checkedId==mRadio1.getId()){
					lockType = 1;
					switchLockType();
				}else if(checkedId==mRadio2.getId()){
					lockType = 2;
					switchLockType();
				}
			}
		});
		
		etAssignAmt.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {  
			    @Override  
			    public void onFocusChange(View v, boolean hasFocus) {  
			    	BigDecimal assignAmt = BigDecimal.ZERO;
			    	
			    	if(((EditText)v).getText().length()>0){
			    		assignAmt = new BigDecimal(((EditText)v).getText().toString());
			    	}else{
			    		return;
			    	}
			    		
			        if(hasFocus) {
			        	
			        } else {
			        	tvAssignRate.setText("");
			        	try {
							BigDecimal assignRate = ActivityUtils.getAssignRate(capital, assignAmt, 
									unpaidAmt.subtract(capital), new MathContext(16, RoundingMode.HALF_DOWN), daysRemaining);
							tvAssignRate.setText(ViewUtils.mformat.format(assignRate));
						} catch (Exception e) {
							e.printStackTrace();
						}
			        }
			    }
		});
		
		etAssignRate.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {  
		    @Override  
		    public void onFocusChange(View v, boolean hasFocus) {  
		    	BigDecimal assignRate = BigDecimal.ZERO;
		    	
		    	if(((EditText)v).getText().length()>0 && NumberUtils.isNumber(((EditText)v).getText().toString())){
		    		assignRate = new BigDecimal(((EditText)v).getText().toString());
		    	}else{
		    		return;
		    	}
		    		
		        if(hasFocus) {
		        	
		        } else {
		        	tvAssignAmt.setText("");
		        	try {
						BigDecimal assignAmt = ActivityUtils.getAssignAmtByAssignRate(capital, assignRate, unpaidAmt.subtract(capital), daysRemaining);
						tvAssignAmt.setText(assignAmt.setScale(0,RoundingMode.DOWN).toString());
					} catch (Exception e) {
						e.printStackTrace();
					}
		        }
		    }
		});
		
		tvApply.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(validate()){
					ViewUtils.showDialog("", "", "提示", "确认提交申请？", CaApplyActivity.this, ViewUtils.Button_type_all, new MyDialogInterface() {
						
						@Override
						public void onButtonSure() {
							loading.setVisibility(View.VISIBLE);
							CaApplyTask task = new CaApplyTask(handler, 
									Integer.toString(tiId), mobileCode, password, Long.toString(waitingDays), 
									assignAmt.toString(), Integer.toString(lockType), assignRate.toString());
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
			CaApplyActivity.this.finish();
			return true;
		}
		return false;
	}
	
	private boolean validate() {
		waitingDays = NumberUtils.toLong(etWaitingDays.getText().toString(),0);
		mobileCode = etMobileCode.getText().toString();
		password = etPassword.getText().toString();
		
		if(!(waitingDays>0)){
			ViewUtils.showDialog("", "", "提示", "有效转让期1—" + maxWaitingDays + "天！", CaApplyActivity.this, ViewUtils.Button_type_sure, null);
			etWaitingDays.requestFocus();
			return false;
		}
		
		if(waitingDays>maxWaitingDays){
			ViewUtils.showDialog("", "", "提示", "有效转让期1—" + maxWaitingDays + "天！", CaApplyActivity.this, ViewUtils.Button_type_sure, null);
			etWaitingDays.requestFocus();
			return false;
		}
		
		if(lockType==1){
			if(etAssignRate.getText().length()==0){
				ViewUtils.showDialog("", "", "提示", "折算借款年利率不能为空！", CaApplyActivity.this, ViewUtils.Button_type_sure, null);
				etAssignRate.requestFocus();
				return false;
			}
			
			assignRate = new BigDecimal(etAssignRate.getText().toString());
			if(!(assignRate.compareTo(minAssignRate)>=0 && assignRate.compareTo(maxAssignRate)<=0)){
				ViewUtils.showDialog("", "", "提示", "有效折算借款年利率" + ViewUtils.mformat.format(minAssignRate) + "—" + ViewUtils.mformat.format(maxAssignRate) + "！", CaApplyActivity.this, ViewUtils.Button_type_sure, null);
				etAssignRate.requestFocus();
				return false;
			}
			assignAmt = ActivityUtils.getAssignAmtByAssignRate(capital, assignRate, unpaidAmt.subtract(capital), daysRemaining).setScale(0, RoundingMode.DOWN);
			
		}else if(lockType==2){
			if(etAssignAmt.getText().length()==0){
				ViewUtils.showDialog("", "", "提示", "转让标价不能为空！", CaApplyActivity.this, ViewUtils.Button_type_sure, null);
				etAssignAmt.requestFocus();
				return false;
			}
			
			assignAmt = new BigDecimal(etAssignAmt.getText().toString());
			if(!(assignAmt.compareTo(minAssignAmt)>=0 && assignAmt.compareTo(maxAssignAmt)<=0)){
				ViewUtils.showDialog("", "", "提示", "有效转让标价" + minAssignAmt.setScale(0,RoundingMode.DOWN) + "—" + maxAssignAmt.setScale(0,RoundingMode.DOWN) + "！", CaApplyActivity.this, ViewUtils.Button_type_sure, null);
				etAssignAmt.requestFocus();
				return false;
			}
			
			try {
				assignRate = ActivityUtils.getAssignRate(capital, assignAmt, 
						unpaidAmt.subtract(capital), new MathContext(16, RoundingMode.HALF_DOWN), daysRemaining);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(mobileCode.length()==0){
			ViewUtils.showDialog("", "", "提示", "手机验证码不能为空！", CaApplyActivity.this, ViewUtils.Button_type_sure, null);
			etMobileCode.requestFocus();
			return false;
		}
		
		if(password.length()==0){
			ViewUtils.showDialog("", "", "提示", "登录密码不能为空！", CaApplyActivity.this, ViewUtils.Button_type_sure, null);
			etPassword.requestFocus();
			return false;
		}
		
//		if(!chbAgreement.isChecked()){
//			ViewUtils.showDialog("", "", "提示", "请先阅读并勾选《转让协议》！", CaApplyActivity.this, ViewUtils.Button_type_sure, null);
//			return false;
//		}
		return true;
	}
	
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			loading.setVisibility(View.GONE);
			
			switch (msg.what) {
				case Constants.GET_PRJ_ENT_DETAIL:
					Map<String,Object> map = (Map<String, Object>) msg.obj;
					
					maxAssignAmt = new BigDecimal(map.get("unpaidAmt").toString()).setScale(0, RoundingMode.DOWN);
					minAssignAmt = new BigDecimal(map.get("capital").toString()).multiply(BigDecimal.valueOf(0.8));
					capital = new BigDecimal(map.get("capital").toString());
					unpaidAmt = new BigDecimal(map.get("unpaidAmt").toString());
					daysRemaining = NumberUtils.toLong(map.get("daysRemaining").toString(),0);
					maxWaitingDays = daysRemaining-2>=0?daysRemaining-2:0;
					
					try {
						maxAssignRate = ActivityUtils.getAssignRate(capital, capital.multiply(BigDecimal.valueOf(0.8)), 
								unpaidAmt.subtract(capital), new MathContext(16, RoundingMode.HALF_DOWN), daysRemaining);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					tvItemNo.setText(map.get("itemNo").toString());
					tvItemShowName.setText(map.get("itemShowName").toString());
					tvRemainingDays.setText(map.get("daysRemaining").toString());
					tvOriMoneyRate.setText(ViewUtils.mformat.format(map.get("moneyRate")));
					tvUnpaid.setText(ViewUtils.mformat.format(map.get("unpaidAmt")));
					
					etWaitingDays.setHint("最长转让期" + maxWaitingDays);
					
					switchLockType();
					break;
				case Constants.SEND_MOBILE_CODE_FAILED:
					Map<String,Object> map2 = (Map<String, Object>) msg.obj;
					ViewUtils.showDialog("", "", "提示", map2.get("errorMsg").toString(),CaApplyActivity.this, ViewUtils.Button_type_sure, null);
					break;
				case Constants.SEND_MOBILE_CODE_SUCCESS:
					ViewUtils.showDialog("", "", "提示", "手机激活码已发送到手机【" + ActivityUtils.formatMobile(mobile) + "】上，请注意查收", CaApplyActivity.this, ViewUtils.Button_type_sure, null);
					break;
				case Constants.CA_APPLY_SUCCESS:
						ViewUtils.showDialog("", "", "提示", "申请转让成功。", CaApplyActivity.this, ViewUtils.Button_type_sure, new MyDialogInterface() {
						
						@Override
						public void onButtonSure() {
							CaApplyActivity.this.setResult(Constants.RESULT_RESTART_SELF, intentLast);
							CaApplyActivity.this.finish();
						}
						
						@Override
						public void onButtonCancel() {
						}
					});
					break;
				case Constants.CA_APPLY_FAILED:
					Map<String,Object> map3 = (Map<String, Object>) msg.obj;
					ViewUtils.showDialog("", "", "提示", map3.get("errorMsg").toString(),CaApplyActivity.this, ViewUtils.Button_type_sure, null);
					break;
				default:
			}
		}
	};
	
	private void switchLockType(){
		etAssignRate.setText("");
		tvAssignRate.setText("");
		etAssignAmt.setText("");
		tvAssignAmt.setText("");
		
		if(lockType==1){
			etAssignRate.setVisibility(View.VISIBLE);
			tvAssignRate.setVisibility(View.GONE);
			etAssignAmt.setVisibility(View.GONE);
			tvAssignAmt.setVisibility(View.VISIBLE);
			etAssignRate.setHint("最大值" + ViewUtils.mformat.format(maxAssignRate));
		}else if(lockType==2){
			etAssignRate.setVisibility(View.GONE);
			tvAssignRate.setVisibility(View.VISIBLE);
			etAssignAmt.setVisibility(View.VISIBLE);
			tvAssignAmt.setVisibility(View.GONE);
			etAssignAmt.setHint("最大值" + ViewUtils.mformat.format(maxAssignAmt));
		}
	}
}
