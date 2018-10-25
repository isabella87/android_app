package com.banhuitong.async;

import android.os.Handler;
import android.os.Message;

import com.banhuitong.util.Constants;

public class CountdownTask implements Runnable {
	
	private static final String TAG = "CountdownTask" ;  
	private Handler handler;
	
	public CountdownTask(Handler handler) {
		super();
		this.handler = handler;
	}

	@Override  
    public void run() {  
		while(true){
			Message msg = new Message();
			msg.what = Constants.COUNTDOWN_PORTAL_ENTS;
			handler.sendMessage(msg);
			
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
    }  
}
