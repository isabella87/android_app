package com.banhuitong.service;

import java.util.Map;

import android.annotation.SuppressLint;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.banhuitong.activity.MainActivity3;
import com.banhuitong.activity.R;
import com.banhuitong.activity.jx.JxBindCardActivity;
import com.banhuitong.async.DefaultThreadPool;
import com.banhuitong.async.GetUnreadMsgTask;
import com.banhuitong.util.Constants;
import com.banhuitong.util.ViewUtils;

@SuppressLint("NewApi")
public class PushService extends BaseService {

	public static final String TAG = "PushService";

	private String cookie;
	private Intent messageIntent = null;
	private PendingIntent messagePendingIntent = null;
	private int messageNotificationID = 1000;
	private Notification messageNotification = null;
	private NotificationManager messageNotificatioManager = null;
	private static int preAmId = -999;

	Handler handler = new Handler() {
		@SuppressWarnings("deprecation")
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			switch (msg.what) {
			case Constants.GET_UNREAD_MESSAGE_SUCCESS:
				Log.w(TAG, "推送消息");
				Map<String, Object> notification = (Map<String, Object>) msg.obj;

				if (notification != null && notification.size() > 0) {
					int amId = (Integer) notification.get("amId");
					String title = (String) notification.get("title");
					String brief = (String) notification.get("brief");

					synchronized (PushService.class) {
						preAmId = sp.getInt(Constants.AM_ID, -999);
						Log.v(TAG, "preAmId = " + preAmId);
						Log.v(TAG, "amId = " + amId);

						if (amId > preAmId) {
							Editor ed = sp.edit();
							ed.putInt(Constants.AM_ID, amId);
							ed.commit();

							if (!mApp.isAppOnForeground()) {
								messageIntent = new Intent(PushService.this,
										MainActivity3.class);
								messageIntent.putExtra("moveToMyAcc", "Y");
								messagePendingIntent = PendingIntent
										.getActivity(
												PushService.this,
												0,
												messageIntent,
												PendingIntent.FLAG_UPDATE_CURRENT);
							}

							messageNotification.tickerText = title;
							messageNotification.setLatestEventInfo(
									PushService.this, title, brief,
									messagePendingIntent);
							messageNotificatioManager.notify(
									messageNotificationID, messageNotification);
							messageNotificationID++;
						}

						if (km.isKeyguardLocked()) {
							Log.w(TAG, "当前被锁屏");
							ViewUtils.showSystemDialog(title, brief, getApplicationContext(), ViewUtils.Button_type_confirm, null);
						}
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
			handler.postDelayed(this, 60 * 1000);
			DefaultThreadPool.getInstance().execute(
					new GetUnreadMsgTask(handler, getApplicationContext(),
							cookie));
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
		if (!isLogin()) {
			Log.w(TAG, "stop self");
			this.stopSelf();
		}

		long when = System.currentTimeMillis();
		messageNotification = new Notification();
		messageNotification.icon = R.drawable.ic_launcher;
		messageNotification.when = when;
		messageNotification.defaults = Notification.DEFAULT_SOUND;
		messageNotification.flags |= Notification.FLAG_AUTO_CANCEL;
		messageNotificatioManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

		if (!isStarted) {
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

		if (isLogin()) {
			Intent localIntent = new Intent();
			localIntent.setClass(this, PushService.class);
			this.startService(localIntent);
		}
	}
}
