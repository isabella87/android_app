package com.banhuitong.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.banhuitong.adapter.InvestBhbAdapter;
import com.banhuitong.async.DefaultThreadPool;
import com.banhuitong.async.GetInvestBaoTask;
import com.banhuitong.enumerate.BhbPhaseType;
import com.banhuitong.inf.OnRefreshListener;
import com.banhuitong.item.AccInvestBao;
import com.banhuitong.util.Constants;
import com.banhuitong.util.ViewUtils;
import com.banhuitong.view.MyListView;

public class AccInvestBaoActivity extends BaseFragmentActivity {
	
	public static final String TAG = "AccInvestBaoActivity";  

	private InvestBhbAdapter mAdapter,mAdapter2,mAdapter3;
	private MyListView lv;
	private List<Map<String,Object>> invests;
	private RadioGroup rg;
	public RadioButton mRadio1, mRadio2, mRadio3;
	private ImageView imgBack;
	private int tab;
	
    @Override
	protected void onStop() {
		super.onStop();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.invest_bao);
		init();
		setListener();
	}
	
	@Override
	protected void onStart() {
		super.onStart();

		switch(tab){
		case 1:
			mRadio1.performClick();
			break;
		case 2:
			mRadio2.performClick();
			break;
		case 3:
			mRadio3.performClick();
			break;
		default :
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	private void init() {
		loading = ViewUtils.initRotateAnimation(this);
		
		Intent intent = getIntent();
		tab = intent.getIntExtra("tab", 1);
		
		lv = (MyListView) findViewById(R.id.lv_invest);
		rg = (RadioGroup) findViewById(R.id.tabs_rg);
		mRadio1 = (RadioButton) findViewById(R.id.tab_rb_financing);
		mRadio2 = (RadioButton) findViewById(R.id.tab_rb_close);
		mRadio3 = (RadioButton) findViewById(R.id.tab_rb_complete);
		imgBack = (ImageView) findViewById(R.id.back);
		
		TextView tvTitle = (TextView)findViewById(R.id.tv_title);
		tvTitle.setText("班汇宝");
		
		mAdapter = new InvestBhbAdapter(AccInvestBaoActivity.this, invests);
		mAdapter2 = new InvestBhbAdapter(AccInvestBaoActivity.this, invests);
		mAdapter3 = new InvestBhbAdapter(AccInvestBaoActivity.this, invests);
   	  	
   	  	rg.clearCheck();
	}
	
	private void setListener() {
		
		imgBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AccInvestBaoActivity.this.finish();
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
				invests = null;
				
				if(tab==1){
					DefaultThreadPool.getInstance().execute(new GetInvestBaoTask(handler,lv.page, BhbPhaseType.RAISING.value()));
				}else if(tab==2){
					DefaultThreadPool.getInstance().execute(new GetInvestBaoTask(handler,lv.page, BhbPhaseType.CLOSED.value()));
				}else if(tab==3){
					DefaultThreadPool.getInstance().execute(new GetInvestBaoTask(handler,lv.page, BhbPhaseType.COMPLETED .value()));
				}
			}

			@Override
			public void onLoadMoring(boolean finish) {
				loading.setVisibility(View.VISIBLE);
				lv.page++;
				
				if(tab==1){
					DefaultThreadPool.getInstance().execute(new GetInvestBaoTask(handler,lv.page, BhbPhaseType.RAISING.value()));
				}else if(tab==2){
					DefaultThreadPool.getInstance().execute(new GetInvestBaoTask(handler,lv.page, BhbPhaseType.CLOSED.value()));
				}else if(tab==3){
					DefaultThreadPool.getInstance().execute(new GetInvestBaoTask(handler,lv.page, BhbPhaseType.COMPLETED .value()));
				}
			}
		});
		
		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				loading.setVisibility(View.VISIBLE);
				invests = new ArrayList<Map<String,Object>>();
				lv.page = 1;
				
				if(checkedId==mRadio1.getId()){
					tab = 1;
					lv.setAdapter(mAdapter);
					DefaultThreadPool.getInstance().execute(new GetInvestBaoTask(handler,lv.page, BhbPhaseType.RAISING.value()));
				}else if(checkedId==mRadio2.getId()){
					tab = 2;
					lv.setAdapter(mAdapter2);
					DefaultThreadPool.getInstance().execute(new GetInvestBaoTask(handler,lv.page, BhbPhaseType.CLOSED.value()));
				}else if(checkedId==mRadio3.getId()){
					tab = 3;
					lv.setAdapter(mAdapter3);
					DefaultThreadPool.getInstance().execute(new GetInvestBaoTask(handler,lv.page, BhbPhaseType.COMPLETED .value()));
				}else{
					loading.setVisibility(View.GONE);
					lv.setAdapter(null);
				}
				
				for (int i = 0; i < rg.getChildCount(); i++) {
					RadioButton rb = (RadioButton) rg.getChildAt(i);
					rb.setTextColor(Color.rgb(178,34,34));
					rb.setBackgroundResource(R.drawable.main_tab);
					
					if(rb.getId()==checkedId){
						rb.setTextColor(Color.rgb(255, 255, 255));
						rb.setBackgroundColor(Color.rgb(178,34,34));
					}
				}
			}
		});
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			AccInvestBaoActivity.this.finish();
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
				case Constants.GET_INVEST_BHB:
					AccInvestBao accInvestBao = (AccInvestBao) msg.obj;
					 
					 if(invests!=null){
						 invests.addAll(accInvestBao.getInvests());
					 }else{
						 invests = accInvestBao.getInvests();
					 }
		        	  
					 if(tab==1){
						 mAdapter.setTab(tab);
			        	 mAdapter.setInvests(invests);
			        	 mAdapter.notifyDataSetChanged();
					 }else if(tab==2){
						 mAdapter2.setTab(tab);
			        	 mAdapter2.setInvests(invests);
			        	 mAdapter2.notifyDataSetChanged();
					 }else if(tab==3){
						 mAdapter3.setTab(tab);
			        	 mAdapter3.setInvests(invests);
			        	 mAdapter3.notifyDataSetChanged();
					 }
					break;
				case Constants.NETWORK_ERROR:
					Toast.makeText(getApplicationContext(), "网络异常！", 1).show();
					break;
				default:
			}
			lv.onRefreshFinish();
		}
	};
	
}
