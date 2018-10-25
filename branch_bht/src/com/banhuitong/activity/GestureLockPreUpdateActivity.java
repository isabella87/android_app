package com.banhuitong.activity;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.banhuitong.view.gesture.GestureLockViewGroup;
import com.banhuitong.view.gesture.GestureLockViewGroup.OnGestureLockViewListener;

public class GestureLockPreUpdateActivity extends BaseActivity {

	private GestureLockViewGroup mGestureLockViewGroup;
	private ImageView imgBack;
	
	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gesture_lock);
		init();
		setListener();
	}
	
	private void init() {
		imgBack = (ImageView) findViewById(R.id.back);
		
		mGestureLockViewGroup = (GestureLockViewGroup) findViewById(R.id.id_gestureLockViewGroup);
		TextView tvTitle = (TextView)findViewById(R.id.tv_title);
		tvTitle.setText("修改手势密码");
	}
	
	private void setListener() {
		imgBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				GestureLockPreUpdateActivity.this.finish();
			}
		});
		
		mGestureLockViewGroup
		.setOnGestureLockViewListener(new OnGestureLockViewListener() {

			@Override
			public void onUnmatchedExceedBoundary() {
				Toast.makeText(GestureLockPreUpdateActivity.this,
						"错误5次...", Toast.LENGTH_SHORT).show();
				mGestureLockViewGroup.setUnMatchExceedBoundary(5);
			}

			@Override
			public void onActionUp(List<Integer> mChoose, int[] mAnswer) {

				String answer = mApp.getGestureLock();
				char[] ch = answer.toCharArray();
				int[] answerSaved = new int[ch.length];
				for (int i = 0; i < ch.length; i++) {
					answerSaved[i] = Integer.parseInt(String
							.valueOf(ch[i]));
				}

				if (mGestureLockViewGroup.checkAnswer(mChoose,
						answerSaved)) {
					Intent intent = new Intent(GestureLockPreUpdateActivity.this, GestureLockCreateActivity.class);
					startActivity(intent); 
					GestureLockPreUpdateActivity.this.finish();
				} else {
					mGestureLockViewGroup.changeItemMode(false);
					Toast.makeText(GestureLockPreUpdateActivity.this,
							"输入错误！", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			GestureLockPreUpdateActivity.this.finish();
			return true;
		}
		return false;
	}
}
