package com.banhuitong.service;

import android.app.KeyguardManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;

import com.banhuitong.activity.MyApplication;
import com.banhuitong.receiver.SystemReceiver;
import com.banhuitong.util.Constants;

public class BaseService extends Service {
	protected SharedPreferences sp;
	protected MyApplication mApp;
	protected BroadcastReceiver systemReceiver;
	public boolean isStarted = false;
	protected KeyguardManager km;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		sp = getApplicationContext().getSharedPreferences(Constants.SP_NAME, Context.MODE_PRIVATE);
		mApp = (MyApplication) getApplication();
		systemReceiver = new SystemReceiver(null);
		km = (KeyguardManager) getApplicationContext().getSystemService(Context.KEYGUARD_SERVICE);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return START_NOT_STICKY;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	public boolean isLogin(){
		return (!"".equals(sp.getString(Constants.USER_NAME, "")))&&(!"".equals(sp.getString(Constants.USER_PASS, "")));
	}
}
