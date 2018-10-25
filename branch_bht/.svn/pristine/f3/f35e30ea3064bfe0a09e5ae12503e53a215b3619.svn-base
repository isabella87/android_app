package com.banhuitong.activity.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.banhuitong.activity.BaseActivity;
import com.banhuitong.activity.R;
import com.banhuitong.activity.cp.CPActivity;

public class TestActivity extends BaseActivity {
	
	private TextView tvCP;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);
		init();
		setListener();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
	
	private void init() {
		tvCP = (TextView) findViewById(R.id.tv_cp);
	}
	
	private void setListener() {
		
		tvCP.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(TestActivity.this, CPActivity.class);
				startActivity(intent);
			}
		});
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			TestActivity.this.finish();
			return true;
		}
		return false;
	}

}
