package com.mengcheng.async;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;

import com.mengcheng.http.RequestService;
import com.mengcheng.util.Constants;
import com.mengcheng.util.HttpUtils;
import com.mengcheng.util.Urls;

public class GetCryptMobileTask extends BaseTask {
	
	private static final String TAG = "GetCryptMobileTask" ;  
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
			rstMap.put("rcode", rcode);
		Message msg = new Message();		
		msg.obj = rstMap;
		msg.what = Constants.GET_CRYPT_MOBILE_SUCCESS;
		handler.sendMessage(msg);
    }  
}
