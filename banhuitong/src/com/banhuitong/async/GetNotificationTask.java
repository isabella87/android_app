package com.banhuitong.async;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.message.BasicNameValuePair;

import android.os.Handler;
import android.os.Message;

import com.banhuitong.util.Constants;
import com.banhuitong.util.HttpUtils;

public class GetNotificationTask implements Runnable {
	private static final String TAG = "GetNotificationTask" ;  
	private Handler handler;
	
	public GetNotificationTask(Handler handler) {
		super();
		this.handler = handler;
	}

	@Override  
    public void run() {
//		String path = Constants.serverUrlp2p + "account/acc-info";
		String path ="";
		
		Map<String, String> map = new HashMap<String, String>();
	   	List<BasicNameValuePair> params = HttpUtils.setParams(map);
	   	
		Map<String, String> map2 = new HashMap<String, String>();
	   	map2.put("Accept-Encoding", "gzip, deflate");
	   	map2.put("Accept", "application/json");

		Map<String,Object> notification = new HashMap<String,Object>();
		
		try {
//			String jsonStr = RequestService.getInstance().getRequest(params, path, map2);
//			JSONObject jsonObject = new JSONObject(jsonStr);
//			CacheObject.getInstance().setPersonInfo(jsonObject);
			
//			notification.put("realName", jsonObject.optString("realName",""));
			notification.put("notification", "消息推送");
			
			
		} catch (Exception e) {
			e.printStackTrace();
			Message msg = new Message();  
	        msg.what = Constants.NETWORK_ERROR;
	        handler.sendMessage(msg); 
	        return;
		}
		
        Message msg = new Message();  
        msg.obj = notification;
        msg.what = Constants.GET_NOTIFICATION;
        handler.sendMessage(msg); 
    }  
}
