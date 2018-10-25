package com.banhuitong.activity.jx;

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

import com.banhuitong.activity.BaseActivity;
import com.banhuitong.activity.IncomeActivity;
import com.banhuitong.activity.R;
import com.banhuitong.enumerate.JxInComeDetailType;
import com.banhuitong.util.ViewUtils;

public class JxIncomeListActivity extends BaseActivity {
	
	private ImageView imgBack;
	private RelativeLayout rlIn;
	private RelativeLayout rlOut;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jx_income_list);
		init();
		setListener();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
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
		rlIn = (RelativeLayout) findViewById(R.id.layer_in);
		rlOut = (RelativeLayout) findViewById(R.id.layer_out);
		
		TextView tvTitle = (TextView)findViewById(R.id.tv_title);
		tvTitle.setText("收支明细");
	}
	
	private void setListener() {
		imgBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				JxIncomeListActivity.this.finish();
			}
		});
		
		rlIn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(JxIncomeListActivity.this, JxIncomeActivity.class);
				intent.putExtra("type", JxInComeDetailType.IN.value());
				startActivity(intent);
			}
		});
		
		rlOut.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(JxIncomeListActivity.this, JxIncomeActivity.class);
				intent.putExtra("type", JxInComeDetailType.OUT.value());
				startActivity(intent);
			}
		});
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			JxIncomeListActivity.this.finish();
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
				default:
			}
		}
	};

}
