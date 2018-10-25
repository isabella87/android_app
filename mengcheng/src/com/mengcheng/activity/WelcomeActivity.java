package com.mengcheng.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.mengcheng.activity.R;
import com.mengcheng.async.DefaultThreadPool;
import com.mengcheng.util.ActivityUtils;
import com.mengcheng.util.Constants;
import com.mengcheng.util.DataCleanManager;
import com.mengcheng.view.PortionView;
/**
 * 
 * 欢迎页面
 * @author Administrator
 *
 */
public class WelcomeActivity extends BaseActivity {
	
	private ImageView imgBg;
	private PortionView pv;
	private RelativeLayout rl;
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	Handler handler = new Handler() {	
		public void handleMessage(android.os.Message msg) {
			
			switch (msg.what) {
			case Constants.CALL_BACK_SUCCESS:
				if(mApp.hasGestureLock()){
					ActivityUtils.startIntent(WelcomeActivity.this,GestureLockCheckActivity.class);
				}else{
					ActivityUtils.startIntent(WelcomeActivity.this,LoginActivity.class);
				}
				WelcomeActivity.this.finish();
				break;
			case Constants.LOGIN_SUCCESS:
//				Toast.makeText(getApplicationContext(), "登录成功。", 1).show();
				break;
			case Constants.LOGIN_FAILED:
				Toast.makeText(WelcomeActivity.this, "登录失败。", 1).show();
				break;
			default:
			}
		};
	};
	
	@Override
	protected void onStart() {
		super.onStart();
		
		if(isNotFirstIn()){
			DefaultThreadPool.getInstance().execute(new Runnable() {
				@Override
				public void run() {
					Context context = getBaseContext();
					DataCleanManager.clearAllCache(context);
					SystemClock.sleep(4000);
					Message msg = handler.obtainMessage(Constants.CALL_BACK_SUCCESS);
					msg.sendToTarget();
				}
			});
		}else{
			ActivityUtils.startIntent(WelcomeActivity.this,GuideActivity.class);
			WelcomeActivity.this.finish();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		
	/*	if(isLogin()){
			LoginTask loginTask = new LoginTask(handler, MyApplication.sp.getString(Constants.USER_NAME, ""), MyApplication.sp.getString(Constants.USER_PASS, ""));
			DefaultThreadPool.getInstance().execute(loginTask);		
		}else{
			LogoutTask logoutTask = new LogoutTask(handler);
			DefaultThreadPool.getInstance().execute(logoutTask);
		}*/
			
		rl = (RelativeLayout) findViewById(R.id.v_bg);	
		pv = new PortionView(getApplication());
		rl.addView(pv);
		
		pv.setIndex(3);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return false;
		}
		return false;
	}
	
}
