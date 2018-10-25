package com.mengcheng.activity;

import java.util.List;

import com.mengcheng.util.Constants;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
/**
 * Android 信息存储
 * @author Administrator
 *
 */
public class MyApplication extends Application {
	
	static {
        // 加载动态库
//        System.loadLibrary("MyApplicationJni");
    }

	private String savedPassword;
	private String savedUsername;
	public static int screen_w;
	public static int screen_h;
	public boolean isStartup = false;
	public static SharedPreferences sp;
//	public native String sayHello(String str);

	@Override
	public void onCreate() {
		super.onCreate();
		sp = getApplicationContext().getSharedPreferences(Constants.SP_NAME, Context.MODE_PRIVATE);
//		SDKInitializer.initialize(this);
//		Log.w("JNI", sayHello("JNI helloworld!"));
	}

	public String getSavedPassword() {
		return savedPassword;
	}

	public void setSavedPassword(String savedPassword) {
		this.savedPassword = savedPassword;
	}

	public String getSavedUsername() {
		return savedUsername;
	}

	public void setSavedUsername(String savedUsername) {
		this.savedUsername = savedUsername;
	}

	public String getVersionName() throws Exception {
		PackageManager packageManager = getPackageManager();
		PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(),
				0);
		return packInfo.versionName;
	}

	public boolean isServiceWork(Context mContext, String serviceName) {
		boolean isWork = false;
		ActivityManager am = (ActivityManager) mContext
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningServiceInfo> myList = am.getRunningServices(100);
		if (myList.size() <= 0) {
			return false;
		}
		for (int i = 0; i < myList.size(); i++) {
			String mName = myList.get(i).service.getClassName().toString();
			if (mName.equals(serviceName)) {
				isWork = true;
				break;
			}
		}
		return isWork;
	}

	public boolean isAppOnForeground() {
		ActivityManager activityManager = (ActivityManager) getApplicationContext()
				.getSystemService(Context.ACTIVITY_SERVICE);
		String packageName = getApplicationContext().getPackageName();
		List<RunningAppProcessInfo> appProcesses = activityManager
				.getRunningAppProcesses();
		if (appProcesses == null)
			return false;
		for (RunningAppProcessInfo appProcess : appProcesses) {
			if (appProcess.processName.equals(packageName)
					&& appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
				return true;
			}
		}
		return false;
	}
	
	public boolean hasGestureLock(){
		return (!"".equals(sp.getString(Constants.GESTURE_LOCK, "")));
	}
	
	public void setGestureLock(final String gestureLock) {
		Editor ed = sp.edit();
		ed.putString(Constants.GESTURE_LOCK, gestureLock);
		ed.commit();
	}
	
	public String getGestureLock(){
		return (sp.getString(Constants.GESTURE_LOCK, ""));
	}
	
	public void removeGestureLock() {
		Editor ed = sp.edit();
		ed.putString(Constants.GESTURE_LOCK, "");
		ed.commit();
	}
}
