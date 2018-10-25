package com.banhuitong.receiver;

import android.content.Context;
import android.content.Intent;

import com.banhuitong.inf.ReceiverCallback;

public class SystemReceiver extends BaseReceiver {

	private ReceiverCallback rcb;
	
	public SystemReceiver(ReceiverCallback rcb){
		this.rcb = rcb;
	}
	
	@Override
	public void onReceive(Context ctx, Intent intent) {
//		if (intent.getAction().equals(Constants.ACTION_LOG_OUT)) {  
		if(rcb!=null){
			rcb.callback(intent.getAction());
		}
//        }
	}

}
