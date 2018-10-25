package com.banhuitong.async;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.banhuitong.cache.CacheObject;
import com.banhuitong.http.RequestService;
import com.banhuitong.util.Constants;
import com.banhuitong.util.HttpUtils;
import com.banhuitong.util.Urls;

public class GetAccSurveyTask extends BaseTask {
	private static final String TAG = "GetAccSurveyTask" ;  
	private Handler handler;
	private Context ctx;
	
	public GetAccSurveyTask(Handler handler, Context ctx) {
		super();
		this.handler = handler;
		this.ctx = ctx;
	}

	@Override  
    public void run() {
		String path = Urls.URL_15;
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("login-name-or-mobile", username);
		map.put("pwd", password);
	   	List<BasicNameValuePair> params = HttpUtils.setParams(map);
	   	
	   	
		Map<String, String> map2 = new HashMap<String, String>();
	   	map2.put("Accept-Encoding", "gzip, deflate");
	   	map2.put("Accept", "application/json");

		Map<String,Object> acc = new HashMap<String,Object>();
		
		String jsonStr = "";
		try {
			//缓存json
//			Map<String,String> cacheAcc = CacheService.getInstance(ctx).query("accSurvey");
//			if(cacheAcc.get("value")==null){
				jsonStr = RequestService.getInstance().getRequest(params, path, map2);
//				CacheService.getInstance(ctx).insert("accSurvey", jsonStr);
//			}else if(new Date().getTime() - NumberUtils.toLong(cacheAcc.get("datepoint"),0) > 10*1000){
//				jsonStr = RequestService.getInstance().getRequest(params, path, map2);
//				CacheService.getInstance(ctx).update("accSurvey", jsonStr);
//			}else{
//				jsonStr = cacheAcc.get("value");
//			}
			
			JSONObject jsonObject = new JSONObject(jsonStr);
			
			CacheObject.getInstance().setSurvey(jsonObject);
			
			acc.put("balance", jsonObject.optString("availableBal",""));
			acc.put("collecting", jsonObject.optString("receiving",""));
			acc.put("freezing", jsonObject.optString("receivingPrincipal",""));
			
		} catch (Exception e) {
			e.printStackTrace();
			Message msg = new Message();  
	        msg.what = Constants.NETWORK_ERROR;
	        handler.sendMessage(msg); 
	        return;
		}
		
        Message msg = new Message();  
        msg.obj = acc;
        msg.what = Constants.GET_ACC_SURVEY;
        handler.sendMessage(msg); 
    }  
}
