package com.banhuitong.async;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;

import com.banhuitong.http.RequestService;
import com.banhuitong.util.Constants;
import com.banhuitong.util.HttpUtils;
import com.banhuitong.util.Urls;

public class GetCryptMobileTask extends BaseTask {
	
	private static final String TAG = "GetCryptMobileTask" ;  
	private Handler handler;
	
	public GetCryptMobileTask(Handler handler) {
		super();
		this.handler = handler;
	}

	@Override  
    public void run() {  
//		String path = Constants.serverUrlp2p + "reg/mobile";
		String path = Urls.URL_17;
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("login-name-or-mobile", username);
		map.put("pwd", password);
	   	List<BasicNameValuePair> params = HttpUtils.setParams(map);
	   	
	   	Map<String, String> map2 = new HashMap<String, String>();
		map2.put("Accept-Encoding", "gzip, deflate");
	   	map2.put("Accept", "application/json");

	   	Map<String,String> rstMap = new HashMap<String,String>();
		try {
			String jsonStr = RequestService.getInstance().getRequest(params, path, map2);   
			JSONObject jsonObject = new JSONObject(jsonStr);
				
			rstMap.put("rcode", jsonObject.optString("rcode"));
			rstMap.put("mobile", jsonObject.optString("mobile"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Message msg = new Message();		
		msg.obj = rstMap;
		msg.what = Constants.GET_CRYPT_MOBILE_SUCCESS;
		handler.sendMessage(msg);
    }  
}
