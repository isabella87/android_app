package com.banhuitong.async.jx;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.banhuitong.async.BaseTask;
import com.banhuitong.http.RequestService;
import com.banhuitong.util.Constants;
import com.banhuitong.util.HttpUtils;
import com.banhuitong.util.Urls;

public class JxGetBalanceTask extends BaseTask {
	private static final String TAG = "GetBalanceTask" ;  
	private Handler handler;
	private Context ctx;
	
	public JxGetBalanceTask(Handler handler, Context ctx) {
		super();
		this.handler = handler;
		this.ctx = ctx;
	}

	@Override  
    public void run() {
//		String path = Constants.serverUrlp2p + "trans/account-balance";
		String path = Urls.URL_32;
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("login-name-or-mobile", username);
		map.put("pwd", password);
	   	List<BasicNameValuePair> params = HttpUtils.setParams(map);
	   	
		Map<String, String> map2 = new HashMap<String, String>();
	   	map2.put("Accept-Encoding", "gzip, deflate");
	   	map2.put("Accept", "application/json");

	   	Map<String,String> info = new HashMap<String,String>();
		String jsonStr = "";
		try {
			jsonStr = RequestService.getInstance().getRequest(params, path, map2);
			
			if(jsonStr==null || "null".equals(jsonStr) || "".equals(jsonStr)){
				info.put("available", "0");
			}else{
				HttpUtils.parseJson(jsonStr, info, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Message msg = new Message();  
	        msg.what = Constants.NETWORK_ERROR;
	        handler.sendMessage(msg); 
	        return;
		}
		
        Message msg = new Message();  
        msg.obj = info;
        msg.what = Constants.GET_BALANCE_SUCCESS;
        handler.sendMessage(msg); 
    }  
}
