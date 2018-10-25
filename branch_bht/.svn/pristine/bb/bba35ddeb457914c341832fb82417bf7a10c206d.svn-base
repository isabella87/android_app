package com.banhuitong.service;

import java.util.Map;

import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.banhuitong.async.DefaultThreadPool;
import com.banhuitong.async.GetUnreadMsgCountTask;
import com.banhuitong.util.BadgeUtil;
import com.banhuitong.util.Constants;

public class UpdateBadgeService extends BaseService {

	public static final String TAG = "UpdateBadgeService";
	private String cookie;

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			switch (msg.what) {
			case Constants.GET_UNREAD_MESSAGE_COUNT_SUCCESS:
				Log.w(TAG, "更新Badge");
						
				synchronized(UpdateBadgeService.class){
					int unreadCount = (Integer) ((Map<String, Object>)msg.obj).get("unreadCount");
					if(unreadCount!=BadgeUtil.badgeCount){
						BadgeUtil.badgeCount = unreadCount;
						BadgeUtil.setBadgeCount(getApplicationContext(), BadgeUtil.badgeCount);
					}
				}
				break;
			case Constants.NETWORK_ERROR:
				break;
			default:
			}
		}
	};

	private Runnable task = new Runnable() {
		public void run() {
			handler.postDelayed(this, 30 * 1000);
			DefaultThreadPool.getInstance().execute(new GetUnreadMsgCountTask(handler, getApplicationContext(), cookie));
		}
	};

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.w(TAG, "in onCreate");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.w(TAG, "in onStartCommand");
		if(!isLogin()){
			Log.w(TAG, "stop self");
			this.stopSelf();
		}
		
		if(!isStarted){
			cookie = sp.getString(Constants.SAVED_COOKIE, "");
			handler.post(task);
			isStarted = true;
		}
		
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.w(TAG, "in onDestroy");
		
		handler.removeCallbacks(task);
		
		if(isLogin()){
			Intent localIntent = new Intent();
			localIntent.setClass(this, UpdateBadgeService.class);
			this.startService(localIntent);
		}
	}
}
