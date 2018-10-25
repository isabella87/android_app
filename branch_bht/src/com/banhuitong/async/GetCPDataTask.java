package com.banhuitong.async;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.banhuitong.cache.CacheService;
import com.banhuitong.http.RequestService;
import com.banhuitong.util.Constants;
import com.banhuitong.util.HttpUtils;
import com.banhuitong.util.Urls;

public class GetCPDataTask implements Runnable {
	private static final String TAG = "GetCPDataTask" ;  
	private final String cacheKey = "cpData";
	private Handler handler;
	private Context ctx;
	
	public GetCPDataTask(Handler handler, Context ctx) {
		super();
		this.handler = handler;
		this.ctx = ctx;
	}

	@Override  
    public void run() {
//		String path = Constants.serverUrlp2p + "app/user-info";
		String path = Urls.URL_16;
		
		Map<String, String> map = new HashMap<String, String>();
	   	List<BasicNameValuePair> params = HttpUtils.setParams(map);
	   	
		Map<String, String> map2 = new HashMap<String, String>();
	   	map2.put("Accept-Encoding", "gzip, deflate");
	   	map2.put("Accept", "application/json");

		Map<String,Object> cpData = new HashMap<String,Object>();
		String jsonStr = "";
		
		try {
			//缓存json
			Map<String,String> cacheAcc = CacheService.getInstance(ctx).query(cacheKey);
			if(cacheAcc.get("value")==null){
				jsonStr = RequestService.getInstance().getRequest(params, path, map2);
				CacheService.getInstance(ctx).insert(cacheKey, jsonStr);
			}else if(new Date().getTime() - NumberUtils.toLong(cacheAcc.get("datepoint"),0) > 300*1000){
				jsonStr = RequestService.getInstance().getRequest(params, path, map2);
				CacheService.getInstance(ctx).update(cacheKey, jsonStr);
			}else{
				jsonStr = cacheAcc.get("value");
			}
			
			JSONObject jsonObject = new JSONObject(jsonStr);
//			String strSign = (String)jsonObject.get("sign-content");
//			
//			JSONObject jsonSign = new JSONObject(strSign);
			cpData.put("appSysId", jsonObject.optString("appSysId",""));
			cpData.put("sign", jsonObject.optString("sign",""));
			
			cpData.put("cardNo", jsonObject.optString("cardNo",""));
			cpData.put("cerType", jsonObject.optString("cerType",""));
			cpData.put("cerNo", jsonObject.optString("cerNo",""));
			cpData.put("cerName", jsonObject.optString("cerName",""));
			cpData.put("cardMobile", jsonObject.optString("cardMobile",""));
			cpData.put("env", jsonObject.optString("env",""));
		} catch (Exception e) {
			e.printStackTrace();
			Message msg = new Message();  
	        msg.what = Constants.NETWORK_ERROR;
	        handler.sendMessage(msg); 
	        return;
		}
		
        Message msg = new Message();  
        msg.obj = cpData;
        msg.what = Constants.GET_CP_DATA_SUCCESS;
        handler.sendMessage(msg); 
    }  
}
