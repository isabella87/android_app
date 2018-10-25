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

public class LogoutTask implements Runnable {
	
	private static final String TAG = "LogoutTask" ;  
	private Handler handler;
	
	public LogoutTask(Handler handler) {
		super();
		this.handler = handler;
	}

	@Override  
    public void run() {  
//		String path = Constants.serverUrlAcc + "account/sign-out";
		String path = Urls.URL_5;
		
		Map<String, String> map = new HashMap<String, String>();
	   	List<BasicNameValuePair> params = HttpUtils.setParams(map);
	   	
	   	Map<String, String> map2 = new HashMap<String, String>();
	   	map2.put("Accept-Encoding", "gzip, deflate");
	   	map2.put("Accept", "application/json");
	   	map2.put("ContentType", "application/x-www-form-urlencoded; charset=UTF-8");

		try {
			String jsonStr = RequestService.getInstance().postRequest(params, path, map2);
		} catch (Exception e) {
			e.printStackTrace();
			Message msg = new Message();
			msg.what = Constants.NETWORK_ERROR;
			handler.sendMessage(msg);
			return;
		}
		
		Message msg = new Message();
		msg.what = Constants.LOGOUT_SUCCESS;
		handler.sendMessage(msg);
    }  
}
