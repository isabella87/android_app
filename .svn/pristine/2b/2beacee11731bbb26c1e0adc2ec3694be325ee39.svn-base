package com.banhuitong.async;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.banhuitong.http.RequestService;
import com.banhuitong.util.Constants;
import com.banhuitong.util.HttpUtils;
import com.banhuitong.util.Urls;

public class GetUnreadMsgTask extends BaseTask {
	
	private static final String TAG = "GetUnreadMsgTask" ;  
	private Handler handler;
	private String cookie = "";
	
	public GetUnreadMsgTask(Handler handler, Context ctx, String cookie) {
		super();
		this.handler = handler;
		if(cookie!=null && cookie.length()>0){
			this.cookie = cookie;
		}
	}

	@Override  
    public void run() {
//		String path = Constants.serverUrlp2p + "account/msg/push-info";
		String path = Urls.URL_27;
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("login-name-or-mobile", username);
		map.put("pwd", password);
	   	List<BasicNameValuePair> params = HttpUtils.setParams(map);

	   	Log.i(TAG, path);
	    Log.d("---- cookie-----",cookie);  
	   	
	    Map<String, String> map2 = new HashMap<String, String>();
	   	map2.put("Accept-Encoding", "gzip, deflate");
	   	map2.put("Accept", "application/json");
	   	
	   	if(cookie!=null && cookie.length()>0){
	   		map2.put("Cookie", cookie);
	   	}
	   		
	   	Map<String, Object> rst = new HashMap<String, Object>();
		try {
			String jsonStr = RequestService.getInstance().getRequest(params, path, map2);
			JSONObject jsonObject = new JSONObject(jsonStr);
			JSONObject jsonObject2 = jsonObject.optJSONObject("item");
			
			if(jsonObject2!=null){
				rst.put("amId", jsonObject2.optInt("amId",0));
				rst.put("title", jsonObject2.optString("title",""));
				rst.put("brief", jsonObject2.optString("brief",""));
			}
		} catch (Exception e) {
			e.printStackTrace();
			Message msg = new Message();
			msg.what = Constants.NETWORK_ERROR;
			handler.sendMessage(msg);
			return;
		}
		
		Message msg = new Message();
		msg.obj = rst;
		msg.what = Constants.GET_UNREAD_MESSAGE_SUCCESS;
		handler.sendMessage(msg);
    }  
}
