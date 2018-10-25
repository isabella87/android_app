package com.banhuitong.async;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.banhuitong.http.RequestService;
import com.banhuitong.item.ApkInfoItem;
import com.banhuitong.util.Constants;
import com.banhuitong.util.HttpUtils;
import com.banhuitong.util.Urls;

public class IsRegisterTask implements Runnable {
	
	private static final String TAG = "IsRegisterTask" ;  
	private Handler handler;
	private String mobile;
	
	public IsRegisterTask(Handler handler, String mobile) {
		super();
		this.handler = handler;
		this.mobile = mobile;
	}

	@Override  
    public void run() {  
//		String path = Constants.serverUrlp2p + "app/exist-user-with-mobile";
		String path = Urls.URL_28;
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("mobile", mobile);

	   	List<BasicNameValuePair> params = HttpUtils.setParams(map);

	   	String rstStr;
		try {
			String jsonStr = RequestService.getInstance().getRequest(params, path, new HashMap<String, String>());
		   	rstStr = jsonStr;
		   	
		} catch (Exception e) {
			e.printStackTrace();
			Message msg = new Message();
			msg.what = Constants.NETWORK_ERROR;
			handler.sendMessage(msg);
			return;
		}
		
		Map<String, Object> rst = new HashMap<String, Object>();
		rst.put("mobile", mobile);
		
		if(rstStr!=null && rstStr.contains("true")){
			Message msg = new Message();
			msg.obj = rst;
			msg.what = Constants.REGISTER_ALREADY;
			handler.sendMessage(msg);
		}else if(rstStr!=null && rstStr.contains("false")){
			Message msg = new Message();
			msg.obj = rst;
			msg.what = Constants.REGISTER_NOT_YET;
			handler.sendMessage(msg);
		}
    }  
}
