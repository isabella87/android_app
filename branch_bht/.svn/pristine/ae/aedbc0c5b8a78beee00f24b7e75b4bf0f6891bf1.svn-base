package com.banhuitong.activity;

import org.apache.commons.lang3.math.NumberUtils;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.banhuitong.async.DefaultThreadPool;
import com.banhuitong.async.GetIncomeTotalTask;
import com.banhuitong.enumerate.InComeDetailType;
import com.banhuitong.util.Constants;
import com.banhuitong.util.ViewUtils;

public class IncomeListActivity extends BaseActivity {
	
	private ImageView imgBack;
	private TextView tvInvest;
	private TextView tvRepay;
	private TextView tvExpenditure;
	private TextView tvBonus;
	private TextView tvRecharge;
	private TextView tvWithdraw;
	private RelativeLayout rlInvest;
	private RelativeLayout rlRepay;
	private RelativeLayout rlExpenditure;
	private RelativeLayout rlBonus;
	private RelativeLayout rlRecharge;
	private RelativeLayout rlWithdraw;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.income_list);
		init();
		setListener();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		loading.setVisibility(View.VISIBLE);
		
		DefaultThreadPool.getInstance().execute(new GetIncomeTotalTask(handler, InComeDetailType.RECHARGE.value(), this.getApplicationContext()));		
		DefaultThreadPool.getInstance().execute(new GetIncomeTotalTask(handler, InComeDetailType.WITHDRAW.value(), this.getApplicationContext()));
		DefaultThreadPool.getInstance().execute(new GetIncomeTotalTask(handler, InComeDetailType.TENDER.value(), this.getApplicationContext()));
		DefaultThreadPool.getInstance().execute(new GetIncomeTotalTask(handler, InComeDetailType.REPAY.value(), this.getApplicationContext()));
		DefaultThreadPool.getInstance().execute(new GetIncomeTotalTask(handler, InComeDetailType.REWARD.value(), this.getApplicationContext()));
		DefaultThreadPool.getInstance().execute(new GetIncomeTotalTask(handler, InComeDetailType.FEE.value(), this.getApplicationContext()));
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.i("","调用onResume");
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.i("","调用onPause");
	}
	
	private void init() {
		loading = ViewUtils.initRotateAnimation(this);
		
		imgBack = (ImageView) findViewById(R.id.back);
		tvInvest = (TextView) findViewById(R.id.tv_invest);
		tvRepay = (TextView) findViewById(R.id.tv_repay);
		tvExpenditure = (TextView) findViewById(R.id.tv_expenditure);
		tvBonus = (TextView) findViewById(R.id.tv_bonus);
		tvRecharge = (TextView) findViewById(R.id.tv_recharge);
		tvWithdraw = (TextView) findViewById(R.id.tv_withdraw);
		rlInvest = (RelativeLayout) findViewById(R.id.layer_invest);
		rlRepay = (RelativeLayout) findViewById(R.id.layer_repay);
		rlExpenditure = (RelativeLayout) findViewById(R.id.layer_expenditure);
		rlBonus = (RelativeLayout) findViewById(R.id.layer_bonus);
		rlRecharge = (RelativeLayout) findViewById(R.id.layer_recharge);
		rlWithdraw = (RelativeLayout) findViewById(R.id.layer_withdraw);
		
		TextView tvTitle = (TextView)findViewById(R.id.tv_title);
		tvTitle.setText("资金明细");
	}
	
	private void setListener() {
		imgBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				IncomeListActivity.this.finish();
			}
		});
		
		rlInvest.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(IncomeListActivity.this, IncomeActivity.class);
				intent.putExtra("type", InComeDetailType.TENDER.value());
				startActivity(intent);
			}
		});
		
		rlRepay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(IncomeListActivity.this, IncomeActivity.class);
				intent.putExtra("type", InComeDetailType.REPAY.value());
				startActivity(intent);
			}
		});
		
		rlExpenditure.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(IncomeListActivity.this, IncomeActivity.class);
				intent.putExtra("type", InComeDetailType.FEE.value());
				startActivity(intent);
			}
		});
		
		rlBonus.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(IncomeListActivity.this, IncomeActivity.class);
				intent.putExtra("type", InComeDetailType.REWARD.value());
				startActivity(intent);
			}
		});
		
		rlRecharge.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(IncomeListActivity.this, IncomeActivity.class);
				intent.putExtra("type", InComeDetailType.RECHARGE.value());
				startActivity(intent);
			}
		});
		
		rlWithdraw.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(IncomeListActivity.this, IncomeActivity.class);
				intent.putExtra("type", InComeDetailType.WITHDRAW.value());
				startActivity(intent);
			}
		});
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			IncomeListActivity.this.finish();
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
				case Constants.GET_ACC_INCOME_TOTAL_OF_RECHARGE:
					tvRecharge.setText(ViewUtils.mformat.format(NumberUtils.toDouble(msg.obj.toString(),0)) + "元");
					break;
				case Constants.GET_ACC_INCOME_TOTAL_OF_WITHDRAW:
					tvWithdraw.setText(ViewUtils.mformat.format(NumberUtils.toDouble(msg.obj.toString(),0)) + "元");
					break;
				case Constants.GET_ACC_INCOME_TOTAL_OF_INVEST:
					tvInvest.setText(ViewUtils.mformat.format(NumberUtils.toDouble(msg.obj.toString(),0)) + "元");
					break;
				case Constants.GET_ACC_INCOME_TOTAL_OF_REPAY:
					tvRepay.setText(ViewUtils.mformat.format(NumberUtils.toDouble(msg.obj.toString(),0)) + "元");
					break;
				case Constants.GET_ACC_INCOME_TOTAL_OF_BONUS:
					tvBonus.setText(ViewUtils.mformat.format(NumberUtils.toDouble(msg.obj.toString(),0)) + "元");
					break;
				case Constants.GET_ACC_INCOME_TOTAL_OF_EXPENDITURE:
					tvExpenditure.setText(ViewUtils.mformat.format(NumberUtils.toDouble(msg.obj.toString(),0)) + "元");
					break;
				default:
			}
		}
	};

}
