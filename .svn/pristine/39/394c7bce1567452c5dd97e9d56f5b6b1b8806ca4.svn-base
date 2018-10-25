package com.banhuitong.async;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.message.BasicNameValuePair;

import android.os.Handler;
import android.os.Message;

import com.banhuitong.http.RequestService;
import com.banhuitong.util.Constants;
import com.banhuitong.util.HttpUtils;
import com.banhuitong.util.Urls;

public class CaCancelTask implements Runnable {
	
	private static final String TAG = "CaCancelTask" ;  
	private Handler handler;
	private long pid;
	
	public CaCancelTask(Handler handler, long pid) {
		super();
		this.handler = handler;
		this.pid = pid;
	}

	@Override  
    public void run() {  
//		String path = Constants.serverUrlp2p + "account/cancel-credit-assign/" + pid;
		String path = Urls.URL_13 + pid;
		
		Map<String, String> map = new HashMap<String, String>();
	   	List<BasicNameValuePair> params = HttpUtils.setParams(map);
	   	
	   	Map<String, String> map2 = new HashMap<String, String>();
	   	map2.put("Accept-Encoding", "gzip, deflate");
	   	map2.put("Accept", "application/json");
	   	map2.put("ContentType", "application/x-www-form-urlencoded; charset=UTF-8");

	   	String rstStr;
		try {
			String jsonStr = RequestService.getInstance().postRequest(params, path, map2);
		   	rstStr = jsonStr;
		} catch (Exception e) {
			e.printStackTrace();
			Message msg = new Message();
			msg.what = Constants.NETWORK_ERROR;
			handler.sendMessage(msg);
			return;
		}
		
		Message msg = new Message();
		if("true".equals(rstStr)){
			msg.what = Constants.CA_CANCEL_SUCCESS;
		}else{
			msg.what = Constants.CA_CANCEL_FAILED;
		}
		handler.sendMessage(msg);
    }  
}
