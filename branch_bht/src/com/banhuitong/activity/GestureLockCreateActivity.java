package com.banhuitong.activity;

import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.banhuitong.view.TopBarView;
import com.banhuitong.view.gesture.GestureLockViewGroup;
import com.banhuitong.view.gesture.GestureLockViewGroup.OnGestureLockViewListener;

public class GestureLockCreateActivity extends BaseActivity {

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
		tvTitle.setText("设置手势密码");
	}
	
	private void setListener() {
		imgBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				GestureLockCreateActivity.this.finish();
			}
		});
		
		mGestureLockViewGroup
		.setOnGestureLockViewListener(new OnGestureLockViewListener() {

			@Override
			public void onUnmatchedExceedBoundary() {
				Toast.makeText(GestureLockCreateActivity.this,
						"错误5次...", Toast.LENGTH_SHORT).show();
				mGestureLockViewGroup.setUnMatchExceedBoundary(5);
			}

			@Override
			public void onActionUp(List<Integer> mChoose, int[] mAnswer) {
				
				if(mChoose.size()<4){
					mGestureLockViewGroup.changeItemMode(false);
					Toast.makeText(GestureLockCreateActivity.this,
							"长度不能小于4！", Toast.LENGTH_SHORT).show();
					return;
				}

				if (mAnswer.length > 0) {
					if (mGestureLockViewGroup.checkAnswer(mChoose,
							mAnswer)) {
						String gestureLock = "";
						int[] answer = mGestureLockViewGroup.getAnswer();

						for (int i = 0; i < answer.length; i++) {
							gestureLock += String.valueOf(answer[i]);
						}

						mApp.setGestureLock(gestureLock);
						GestureLockCreateActivity.this.finish();
					} else {
						mGestureLockViewGroup.changeItemMode(false);
						Toast.makeText(GestureLockCreateActivity.this,
								"输入错误！", Toast.LENGTH_SHORT).show();
					}
				} else {
					mAnswer = new int[mChoose.size()];
					for (int i = 0; i < mChoose.size(); i++) {
						mAnswer[i] = mChoose.get(i);
					}
					mGestureLockViewGroup.setAnswer(mAnswer);
					mGestureLockViewGroup.doubleInput();
					Toast.makeText(GestureLockCreateActivity.this,
							"请再输入一次", Toast.LENGTH_SHORT).show();
				}
			}

		});
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			GestureLockCreateActivity.this.finish();
			return true;
		}
		return false;
	}

}
