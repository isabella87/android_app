package com.banhuitong.activity.jx;

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

import com.banhuitong.activity.BaseActivity;
import com.banhuitong.activity.R;
import com.banhuitong.adapter.jx.JxIncomeAdapter;
import com.banhuitong.async.DefaultThreadPool;
import com.banhuitong.async.jx.JxGetIncomeTask;
import com.banhuitong.enumerate.JxInComeDetailType;
import com.banhuitong.inf.OnRefreshListener;
import com.banhuitong.item.AccIncomes;
import com.banhuitong.util.Constants;
import com.banhuitong.util.ViewUtils;
import com.banhuitong.view.MyListView;

public class JxIncomeActivity extends BaseActivity {
	
	private ImageView imgBack;
	private int icType;
	private MyListView lv;
	private List<Map<String,Object>> incomes;
	private JxIncomeAdapter iAdpter;
	private String lastNxReld, lastNxTrnn, lastDatepoint;
	
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
		DefaultThreadPool.getInstance().execute(new JxGetIncomeTask(handler, icType, lastNxReld, lastNxTrnn, lastDatepoint, lv.page));
	}
	
	private void init() {
		loading = ViewUtils.initRotateAnimation(this);
		
		Intent intent = getIntent();
		icType = intent.getIntExtra("type", 0);
		
		imgBack = (ImageView) findViewById(R.id.back);
		lv = (MyListView) findViewById(R.id.lv_income);
		
		iAdpter = new JxIncomeAdapter(JxIncomeActivity.this, incomes);
		lv.setAdapter(iAdpter);
		
		TextView tvTitle = (TextView)findViewById(R.id.tv_title);
		if(icType==JxInComeDetailType.IN.value()){
			tvTitle.setText("收入");
        }else if(icType==JxInComeDetailType.OUT.value()){
        	tvTitle.setText("支出");
        }
	}
	
	private void setListener() {
		imgBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				JxIncomeActivity.this.finish();
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
				lastNxReld = lastNxTrnn = lastDatepoint = null;
				DefaultThreadPool.getInstance().execute(new JxGetIncomeTask(handler, icType, lastNxReld, lastNxTrnn, lastDatepoint, lv.page));
			}

			@Override
			public void onLoadMoring(boolean finish) {
				loading.setVisibility(View.VISIBLE);
				lv.page++;
				DefaultThreadPool.getInstance().execute(new JxGetIncomeTask(handler, icType, lastNxReld, lastNxTrnn, lastDatepoint, lv.page));
			}
		});
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			JxIncomeActivity.this.finish();
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
		        	 
		        	 if(incomes.size()>0){
		        		 lastNxReld = "-999";
			        	 lastNxTrnn = "-999";
			        	 lastDatepoint = Long.toString((Long) incomes.get(incomes.size()-1).get("datepoint"));
		        	 }
		        	 
					break;
				default:
			}
			lv.onRefreshFinish();
		}
	};

}
