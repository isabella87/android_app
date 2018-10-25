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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.banhuitong.adapter.InvestEntAdapter;
import com.banhuitong.async.DefaultThreadPool;
import com.banhuitong.async.GetInvestGongDetailTask;
import com.banhuitong.async.GetInvestGongTask;
import com.banhuitong.enumerate.InvestPrjStatus;
import com.banhuitong.inf.OnRefreshListener;
import com.banhuitong.item.AccInvestGong;
import com.banhuitong.util.Constants;
import com.banhuitong.util.ViewUtils;
import com.banhuitong.view.MyListView;
import com.banhuitong.view.RepayListDialog;

public class AccInvestGongActivity extends BaseFragmentActivity {
	
	public static final String TAG = "AccInvestGongActivity";  

	private InvestEntAdapter mAdapter;
	private MyListView lv;
	private List<Map<String,Object>> invests;
	private RadioGroup rg;
	public RadioButton mRadio1, mRadio2, mRadio3, mRadio4;
	private ImageView imgBack;
	private int tab = 2;
	
    @Override
	protected void onStop() {
		super.onStop();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.invest_gong);
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
		case 4:
			mRadio4.performClick();
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
		tab = intent.getIntExtra("tab", 2);
		
		lv = (MyListView) findViewById(R.id.lv_invest);
		rg = (RadioGroup) findViewById(R.id.tabs_rg);
		mRadio1 = (RadioButton) findViewById(R.id.tab_rb_financing);
		mRadio2 = (RadioButton) findViewById(R.id.tab_rb_repaying);
		mRadio3 = (RadioButton) findViewById(R.id.tab_rb_repayover);
		mRadio4 = (RadioButton) findViewById(R.id.tab_rb_failed);
		imgBack = (ImageView) findViewById(R.id.back);
		
		TextView tvTitle = (TextView)findViewById(R.id.tv_title);
		tvTitle.setText("项目直投");
		
		mAdapter = new InvestEntAdapter(AccInvestGongActivity.this, invests);
   	  	
   	  	rg.clearCheck();
	}
	
	private void setListener() {
		
		imgBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AccInvestGongActivity.this.finish();
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
				invests = new ArrayList<Map<String,Object>>();
				
				switch(tab){
				case 1:
					DefaultThreadPool.getInstance().execute(new GetInvestGongTask(handler,lv.page, InvestPrjStatus.FINANCING.value()));
					break;
				case 2:
					DefaultThreadPool.getInstance().execute(new GetInvestGongTask(handler,lv.page, InvestPrjStatus.REPAYING.value()));
					break;
				case 3:
					DefaultThreadPool.getInstance().execute(new GetInvestGongTask(handler,lv.page, InvestPrjStatus.REPAYOVER.value()));
					break;
				case 4:
					DefaultThreadPool.getInstance().execute(new GetInvestGongTask(handler,lv.page, InvestPrjStatus.FAILED.value()));
					break;
				default :
				}
			}

			@Override
			public void onLoadMoring(boolean finish) {
				loading.setVisibility(View.VISIBLE);
				lv.page++;
				switch(tab){
				case 1:
					DefaultThreadPool.getInstance().execute(new GetInvestGongTask(handler,lv.page, InvestPrjStatus.FINANCING.value()));
					break;
				case 2:
					DefaultThreadPool.getInstance().execute(new GetInvestGongTask(handler,lv.page, InvestPrjStatus.REPAYING.value()));
					break;
				case 3:
					DefaultThreadPool.getInstance().execute(new GetInvestGongTask(handler,lv.page, InvestPrjStatus.REPAYOVER.value()));
					break;
				case 4:
					DefaultThreadPool.getInstance().execute(new GetInvestGongTask(handler,lv.page, InvestPrjStatus.FAILED.value()));
					break;
				default :
				}
			}
		});
		
		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				loading.setVisibility(View.VISIBLE);
				
				lv.setAdapter(mAdapter);
				invests = new ArrayList<Map<String,Object>>();
				lv.page = 1;
				
				if(checkedId==mRadio1.getId()){
					tab = 1;
					DefaultThreadPool.getInstance().execute(new GetInvestGongTask(handler,lv.page, InvestPrjStatus.FINANCING.value()));
				}else if(checkedId==mRadio2.getId()){
					tab = 2;
					DefaultThreadPool.getInstance().execute(new GetInvestGongTask(handler,lv.page, InvestPrjStatus.REPAYING.value()));
				}else if(checkedId==mRadio3.getId()){
					tab = 3;
					DefaultThreadPool.getInstance().execute(new GetInvestGongTask(handler,lv.page, InvestPrjStatus.REPAYOVER.value()));
				}else if(checkedId==mRadio4.getId()){
					tab = 4;
					DefaultThreadPool.getInstance().execute(new GetInvestGongTask(handler,lv.page, InvestPrjStatus.FAILED.value()));
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
		
//		lv.setOnItemClickListener(new OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				if(tab==2 || tab==3){
//					loading.setVisibility(View.VISIBLE);
//					Map<String, Object> item = invests.get(position - 1);
//					long pId = (long) item.get("pId");
//					long tiId = (long) item.get("tiId");
//					DefaultThreadPool.getInstance().execute(new GetInvestGongDetailTask(handler, pId, tiId, 0));
//				}
//			}
//		});
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			AccInvestGongActivity.this.finish();
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
				case Constants.GET_INVEST_ENTS:
					AccInvestGong accInvestGong = (AccInvestGong) msg.obj;
					 
					 if(invests!=null){
						 invests.addAll(accInvestGong.getInvests());
					 }else{
						 invests = accInvestGong.getInvests();
					 }
		        	  
					 mAdapter.setTab(tab);
		        	 mAdapter.setInvests(invests);
		        	 mAdapter.notifyDataSetChanged();
					break;
					
				case Constants.GET_INVEST_ENT_DETAIL:
					Map<String,Object> detail = (Map<String, Object>) msg.obj;
					List<Map<String, Object>> bonusList = (List<Map<String, Object>>) detail.get("bonusList");
					long assignTime = (Long) detail.get("assignTime");
					 
					new RepayListDialog(AccInvestGongActivity.this, bonusList, assignTime, tab);
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
