package com.banhuitong.async;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;

import com.banhuitong.cache.CacheObject;
import com.banhuitong.http.RequestService;
import com.banhuitong.util.Constants;
import com.banhuitong.util.HttpUtils;
import com.banhuitong.util.Urls;

public class GetPersonalInfoTask implements Runnable {
	private static final String TAG = "GetPersonalInfoTask" ;  
	private Handler handler;
	
	public GetPersonalInfoTask(Handler handler) {
		super();
		this.handler = handler;
	}

	@Override  
    public void run() {
//		String path = Constants.serverUrlAcc + "account/user-info";
		String path = Urls.URL_3;
		
		Map<String, String> map = new HashMap<String, String>();
	   	List<BasicNameValuePair> params = HttpUtils.setParams(map);
	   	
		Map<String, String> map2 = new HashMap<String, String>();
	   	map2.put("Accept-Encoding", "gzip, deflate");
	   	map2.put("Accept", "application/json");

		Map<String,Object> personalInfo = new HashMap<String,Object>();
		
		try {
			String jsonStr = RequestService.getInstance().getRequest(params, path, map2);
			JSONObject jsonObject = new JSONObject(jsonStr);
			CacheObject.getInstance().setPersonInfo(jsonObject);
			
			personalInfo.put("realName", jsonObject.optString("realName",""));
			personalInfo.put("loginName", jsonObject.optString("loginName",""));
			personalInfo.put("mobile", jsonObject.optString("mobile",""));
			personalInfo.put("idCard", jsonObject.optString("idCard",""));
			
		} catch (Exception e) {
			e.printStackTrace();
			Message msg = new Message();  
	        msg.what = Constants.NETWORK_ERROR;
	        handler.sendMessage(msg); 
	        return;
		}
		
        Message msg = new Message();  
        msg.obj = personalInfo;
        msg.what = Constants.GET_PERSONAL_INFO;
        handler.sendMessage(msg); 
    }  
}
