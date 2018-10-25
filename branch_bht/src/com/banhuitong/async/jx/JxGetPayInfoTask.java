package com.banhuitong.async.jx;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.message.BasicNameValuePair;

import android.os.Handler;
import android.os.Message;

import com.banhuitong.async.BaseTask;
import com.banhuitong.http.RequestService;
import com.banhuitong.util.Constants;
import com.banhuitong.util.HttpUtils;
import com.banhuitong.util.Urls;

public class JxGetPayInfoTask extends BaseTask {
	private static final String TAG = "GetJxPayInfoTask" ;  
	private Handler handler;
	
	public JxGetPayInfoTask(Handler handler) {
		super();
		this.handler = handler;
	}

	@Override  
    public void run() {
//		String path = Constants.serverUrlp2p + "trans/user-info";
		String path = Urls.URL_34;
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("login-name-or-mobile", username);
		map.put("pwd", password);
	   	List<BasicNameValuePair> params = HttpUtils.setParams(map);
	   	
		Map<String, String> map2 = new HashMap<String, String>();
	   	map2.put("Accept-Encoding", "gzip, deflate");
	   	map2.put("Accept", "application/json");

	   	Map<String,String> info = new HashMap<String,String>();
		
		try {
			String jsonStr = RequestService.getInstance().getRequest(params, path, map2);
			HttpUtils.parseJson(jsonStr, info, null);
			
		} catch (Exception e) {
			e.printStackTrace();
			Message msg = new Message();  
	        msg.what = Constants.NETWORK_ERROR;
	        handler.sendMessage(msg); 
	        return;
		}
		
        Message msg = new Message();  
        msg.obj = info;
        msg.what = Constants.GET_JXPAY_INFO_SUCCESS;
        handler.sendMessage(msg); 
    }  
}
