package com.banhuitong.async;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.banhuitong.http.RequestService;
import com.banhuitong.util.Constants;
import com.banhuitong.util.HttpUtils;
import com.banhuitong.util.Urls;

public class GetAccTask implements Runnable {
	private static final String TAG = "GetAccTask" ;  
	private Handler handler;
	private Context ctx;
	
	public GetAccTask(Handler handler, Context ctx) {
		super();
		this.handler = handler;
		this.ctx = ctx;
	}

	@Override  
    public void run() {
//		String path = Constants.serverUrlAcc + "account";
		String path = Urls.URL_2;
		
		Map<String, String> map = new HashMap<String, String>();
	   	List<BasicNameValuePair> params = HttpUtils.setParams(map);
	   	
		Map<String, String> map2 = new HashMap<String, String>();
	   	map2.put("Accept-Encoding", "gzip, deflate");
	   	map2.put("Accept", "application/json");

		Map<String,Object> acc = new HashMap<String,Object>();
		String jsonStr = "";
		try {
			//缓存json
//			Map<String,String> cacheAcc = CacheService.getInstance(ctx).query("acc");
//			if(cacheAcc.get("value")==null){
				jsonStr = RequestService.getInstance().getRequest(params, path, map2);
//				CacheService.getInstance(ctx).insert("acc", jsonStr);
//			}else if(new Date().getTime() - NumberUtils.toLong(cacheAcc.get("datepoint"),0) > 15*1000){
//				jsonStr = RequestService.getInstance().getRequest(params, path, map2);
//				CacheService.getInstance(ctx).update("acc", jsonStr);
//			}else{
//				jsonStr = cacheAcc.get("value");
//			}
			
			JSONObject jsonObject = new JSONObject(jsonStr);
			
			acc.put("realName", jsonObject.optString("realName",""));
			acc.put("loginName", jsonObject.optString("loginName",""));
			acc.put("mobile", jsonObject.optString("mobile",""));
		} catch (Exception e) {
			e.printStackTrace();
			Message msg = new Message();  
	        msg.what = Constants.NETWORK_ERROR;
	        handler.sendMessage(msg); 
	        return;
		}
		
        Message msg = new Message();  
        msg.obj = acc;
        msg.what = Constants.GET_ACC;
        handler.sendMessage(msg); 
    }  
}
