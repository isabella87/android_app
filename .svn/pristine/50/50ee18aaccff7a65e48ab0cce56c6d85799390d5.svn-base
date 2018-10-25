package com.banhuitong.receiver;

import android.content.Context;
import android.content.Intent;

import com.banhuitong.service.PushService;
import com.banhuitong.service.UpdateBadgeService;

public class BootCompleteReceiver extends BaseReceiver {

	private static final String ACTION = "android.intent.action.BOOT_COMPLETED";

	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(ACTION)) {
			Intent i = new Intent(context, PushService.class);
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startService(i);
			
			Intent i2 = new Intent(context, UpdateBadgeService.class);
			i2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startService(i2);
		}
	}
}
