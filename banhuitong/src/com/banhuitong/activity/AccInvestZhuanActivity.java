package com.banhuitong.activity;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;

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

import com.banhuitong.adapter.InvestCaAdapter;
import com.banhuitong.async.CaCancelTask;
import com.banhuitong.async.DefaultThreadPool;
import com.banhuitong.async.GetInvestZhuanTask;
import com.banhuitong.enumerate.CreditPrjStatusType;
import com.banhuitong.inf.MyDialogInterface;
import com.banhuitong.inf.OnRefreshListener;
import com.banhuitong.item.AccInvestZhuan;
import com.banhuitong.util.Constants;
import com.banhuitong.util.ViewUtils;
import com.banhuitong.view.MyListView;

public class AccInvestZhuanActivity extends BaseFragmentActivity {
	
	public static final String TAG = "AccInvestZhuanActivity";  

	private InvestCaAdapter mAdapter,mAdapter2,mAdapter3;
	private MyListView lv;
	private List<Map<String,Object>> invests;
	private RadioGroup rg;
	public RadioButton mRadio1, mRadio2, mRadio3;
	private ImageView imgBack;
	private int tab;
	private long pId = 0l;
	private boolean exitOnstart = false;
	
    @Override
	protected void onStop() {
		super.onStop();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.invest_zhuan);
		init();
		setListener();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
		if(exitOnstart){
			exitOnstart = false;
			return;
		}
		
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
		mRadio1 = (RadioButton) findViewById(R.id.tab_rb_appliable);
		mRadio2 = (RadioButton) findViewById(R.id.tab_rb_proceeding);
		mRadio3 = (RadioButton) findViewById(R.id.tab_rb_done);
		imgBack = (ImageView) findViewById(R.id.back);
		
		TextView tvTitle = (TextView)findViewById(R.id.tv_title);
		tvTitle.setText("债权转让");
		
		mAdapter = new InvestCaAdapter(AccInvestZhuanActivity.this, invests);
		mAdapter2 = new InvestCaAdapter(AccInvestZhuanActivity.this, invests);
		mAdapter3 = new InvestCaAdapter(AccInvestZhuanActivity.this, invests);
		
		rg.clearCheck();
	}
	
	private void setListener() {
		
		imgBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AccInvestZhuanActivity.this.finish();
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
					DefaultThreadPool.getInstance().execute(new GetInvestZhuanTask(handler,lv.page, CreditPrjStatusType.CA_APPLIABLE.value()));
				}else if(tab==2){
					DefaultThreadPool.getInstance().execute(new GetInvestZhuanTask(handler,lv.page, CreditPrjStatusType.CA_PROCESSING.value()));
				}else if(tab==3){
					DefaultThreadPool.getInstance().execute(new GetInvestZhuanTask(handler,lv.page, CreditPrjStatusType.CA_DONE.value()));
				}
			}

			@Override
			public void onLoadMoring(boolean finish) {
				loading.setVisibility(View.VISIBLE);
				lv.page++;
				
				if(tab==1){
					DefaultThreadPool.getInstance().execute(new GetInvestZhuanTask(handler,lv.page, CreditPrjStatusType.CA_APPLIABLE.value()));
				}else if(tab==2){
					DefaultThreadPool.getInstance().execute(new GetInvestZhuanTask(handler,lv.page, CreditPrjStatusType.CA_PROCESSING.value()));
				}else if(tab==3){
					DefaultThreadPool.getInstance().execute(new GetInvestZhuanTask(handler,lv.page, CreditPrjStatusType.CA_DONE .value()));
				}
			}
		});
		
		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				loading.setVisibility(View.VISIBLE);
				invests = null;
				lv.page = 1;
				
				if(checkedId==mRadio1.getId()){
					tab = 1;
					lv.setAdapter(mAdapter);
					DefaultThreadPool.getInstance().execute(new GetInvestZhuanTask(handler,lv.page, CreditPrjStatusType.CA_APPLIABLE.value()));
				}else if(checkedId==mRadio2.getId()){
					tab = 2;
					lv.setAdapter(mAdapter2);
					DefaultThreadPool.getInstance().execute(new GetInvestZhuanTask(handler,lv.page, CreditPrjStatusType.CA_PROCESSING.value()));
				}else if(checkedId==mRadio3.getId()){
					tab = 3;
					lv.setAdapter(mAdapter3);
					DefaultThreadPool.getInstance().execute(new GetInvestZhuanTask(handler,lv.page, CreditPrjStatusType.CA_DONE.value()));
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
		
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				switch(tab){
				case 1:
					if(invests!=null){
						Map<String,Object> item = invests.get(position-1);
						
						if(item.get("pType").toString().equals("3") || item.get("pType").toString().equals("4")){
							Intent intent = new Intent(AccInvestZhuanActivity.this, BhbCaApplyActivity.class);
							intent.putExtra("tiId", item.get("tiId").toString());
							startActivityForResult(intent,0); 
						}else if(item.get("pType").toString().equals("1")){
							Intent intent = new Intent(AccInvestZhuanActivity.this, CaApplyActivity.class);
							intent.putExtra("tiId", item.get("tiId").toString());
							startActivityForResult(intent,0); 
						}
					}
					break;
				case 2:
					if(invests!=null){
						Map<String,Object> item2 = invests.get(position-1);
						pId = NumberUtils.toLong(item2.get("pId").toString(),0);
						
						ViewUtils.showDialog("", "", "提示", "确认撤销转让？", AccInvestZhuanActivity.this, ViewUtils.Button_type_all, new MyDialogInterface() {
							
							@Override
							public void onButtonSure() {
								loading.setVisibility(View.VISIBLE);
								
								DefaultThreadPool.getInstance().execute(new CaCancelTask(handler, pId));
							}
							
							@Override
							public void onButtonCancel() {
							}
						});
					}
					break;
				default :
				}
			}
		});
	}
	
	@Override  
	  protected void onActivityResult(int requestCode, int resultCode, Intent data)  
	  {  
	    switch (resultCode)  
	    {  
	      case Constants.RESULT_RESTART_SELF: 
	    	  loading.setVisibility(View.VISIBLE);
	    	  exitOnstart = true;
	    	  invests = null;
	    	  tab = 1;
	    	  lv.setAdapter(mAdapter);
	    	  DefaultThreadPool.getInstance().execute(new GetInvestZhuanTask(handler,1, CreditPrjStatusType.CA_APPLIABLE.value()));
	        break;  
	      default:  
	        break;  
	    }  
	  }  
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			AccInvestZhuanActivity.this.finish();
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
				case Constants.GET_INVEST_CA:
					AccInvestZhuan accInvestZhuan = (AccInvestZhuan) msg.obj;
					
					 if(invests!=null){
						 invests.addAll(accInvestZhuan.getInvests());
					 }else{
						 invests = accInvestZhuan.getInvests();
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
				case Constants.CA_CANCEL_SUCCESS:
						ViewUtils.showDialog("", "", "提示", "撤销转让成功。", AccInvestZhuanActivity.this, ViewUtils.Button_type_sure, new MyDialogInterface() {
						
						@Override
						public void onButtonSure() {
							 loading.setVisibility(View.VISIBLE);
					    	 invests = null;
					    	 tab = 2;
					    	 lv.setAdapter(mAdapter2);
					    	 DefaultThreadPool.getInstance().execute(new GetInvestZhuanTask(handler,1, CreditPrjStatusType.CA_PROCESSING.value()));
						}
						
						@Override
						public void onButtonCancel() {
						}
					});
					break;
				default:
			}
			lv.onRefreshFinish();
		}
	};
	
}
