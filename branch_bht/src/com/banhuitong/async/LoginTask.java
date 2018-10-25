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

public class LoginTask implements Runnable {
	
	private static final String TAG = "LoginTask" ;  
	private Handler handler;
	private String username;
	private String password;
	
	public LoginTask(Handler handler, String username, String password) {
		super();
		this.handler = handler;
		this.username = username;
		this.password = password;
	}

	@Override  
    public void run() {  
//		String path = Constants.serverUrlAcc + "account/sign-in";
		String path = Urls.URL_4;
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("login-name-or-mobile", username);
		map.put("pwd", password);
	   	List<BasicNameValuePair> params = HttpUtils.setParams(map);
	   	
	   	Map<String, String> map2 = new HashMap<String, String>();
	   	map2.put("Accept-Encoding", "gzip, deflate");
	   	map2.put("Accept", "application/json");
	   	map2.put("ContentType", "application/x-www-form-urlencoded; charset=UTF-8");

	   	String rst;
		try {
			String jsonStr = RequestService.getInstance().postRequest(params, path, map2);
		   	JSONObject jsonObject = HttpUtils.jsonToObj2(jsonStr);
		   	
		   	rst = jsonObject.optString("valid");
		} catch (Exception e) {
			e.printStackTrace();
			Message msg = new Message();
			msg.what = Constants.NETWORK_ERROR;
			handler.sendMessage(msg);
			return;
		}
		
		Message msg = new Message();
		if("true".equals(rst)){
			Map<String,Object> obj = new HashMap<String,Object>();
			obj.put(Constants.USER_NAME, username);
			obj.put(Constants.USER_PASS, password);
			
			msg.obj = obj;
			msg.what = Constants.LOGIN_SUCCESS;
		}else{
			msg.what = Constants.LOGIN_FAILED;
		}
		handler.sendMessage(msg);
    }  
}
