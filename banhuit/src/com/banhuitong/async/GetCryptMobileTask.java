package com.banhuitong.async;

import java.util.HashMap;
import java.util.Map;

import android.os.Handler;
import android.os.Message;

import com.banhuitong.util.Constants;

public class GetCryptMobileTask implements Runnable {
	
//	private static final String TAG = "GetCryptMobileTask" ;  
	private Handler handler;
	private String rcode;
	
	public GetCryptMobileTask(Handler handler,String rcode) {
		super();
		this.handler = handler;
		this.rcode = rcode;
	}

	@Override  
    public void run() {  
	   	Map<String,String> rstMap = new HashMap<String,String>();
			rstMap.put("_rcode", rcode);
		Message msg = new Message();		
		msg.obj = rstMap;
		msg.what = Constants.GET_CRYPT_MOBILE_SUCCESS;
		handler.sendMessage(msg);
    }  
}
