package com.banhuitong.activity;

import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.banhuitong.adapter.IncomeAdapter;
import com.banhuitong.async.DefaultThreadPool;
import com.banhuitong.async.GetIncomeTask;
import com.banhuitong.enumerate.InComeDetailType;
import com.banhuitong.inf.OnRefreshListener;
import com.banhuitong.item.AccIncomes;
import com.banhuitong.util.Constants;
import com.banhuitong.util.ViewUtils;
import com.banhuitong.view.MyListView;

public class IncomeActivity extends BaseActivity {
	
	private ImageView imgBack;
	private int icType;
	private MyListView lv;
	private List<Map<String,Object>> incomes;
	private IncomeAdapter iAdpter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.income);
		init();
		setListener();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		loading.setVisibility(View.VISIBLE);
		
		lv.page = 1;
		DefaultThreadPool.getInstance().execute(new GetIncomeTask(handler, lv.page , icType));
	}
	
	private void init() {
		loading = ViewUtils.initRotateAnimation(this);
		
		Intent intent = getIntent();
		icType = intent.getIntExtra("type", 0);
		
		imgBack = (ImageView) findViewById(R.id.back);
		lv = (MyListView) findViewById(R.id.lv_income);
		
		iAdpter = new IncomeAdapter(IncomeActivity.this, incomes);
		lv.setAdapter(iAdpter);
		
		TextView tvTitle = (TextView)findViewById(R.id.tv_title);
		if(icType==InComeDetailType.RECHARGE.value()){
			tvTitle.setText("充值");
        }else if(icType==InComeDetailType.WITHDRAW.value()){
        	tvTitle.setText("提现");
        }else if(icType==InComeDetailType.TENDER.value()){
        	tvTitle.setText("出借");
        }else if(icType==InComeDetailType.REPAY.value()){
        	tvTitle.setText("还款");
        }else if(icType==InComeDetailType.REWARD.value()){
        	tvTitle.setText("奖励");
        }else if(icType==InComeDetailType.FEE.value()){
        	tvTitle.setText("支出");
        }
	}
	
	private void setListener() {
		imgBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				IncomeActivity.this.finish();
			}
		});
		
		lv.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
			}

			@Override
			public void onLoadfresh(int type, int page) {
				loading.setVisibility(View.VISIBLE);
				lv.page = 1;
				incomes = null;
				DefaultThreadPool.getInstance().execute(new GetIncomeTask(handler, lv.page , icType));
			}

			@Override
			public void onLoadMoring(boolean finish) {
				loading.setVisibility(View.VISIBLE);
				lv.page++;
				DefaultThreadPool.getInstance().execute(new GetIncomeTask(handler, lv.page , icType));
			}
		});
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			IncomeActivity.this.finish();
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
				case Constants.GET_ACC_INCOME:
					AccIncomes accIncomes = (AccIncomes) msg.obj;
					 
					 if(incomes!=null){
						 incomes.addAll(accIncomes.getIncomes());
					 }else{
						 incomes = accIncomes.getIncomes();
					 }
					 
					 iAdpter.setIncomes(incomes);
		        	 iAdpter.notifyDataSetChanged();
					break;
				default:
			}
			lv.onRefreshFinish();
		}
	};

}
