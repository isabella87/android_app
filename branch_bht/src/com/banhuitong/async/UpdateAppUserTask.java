package com.banhuitong.async;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.message.BasicNameValuePair;

import android.os.Handler;

import com.banhuitong.http.RequestService;
import com.banhuitong.util.HttpUtils;
import com.banhuitong.util.Urls;

public class UpdateAppUserTask implements Runnable {
	
	private static final String TAG = "UpdateAppUserTask" ;  
	private Handler handler;
	private String cid;
	
	public UpdateAppUserTask(Handler handler, String cid) {
		super();
		this.handler = handler;
		this.cid = cid;
	}

	@Override  
    public void run() {  
//		String path = Constants.serverUrlp2p + "app/update-app-user";
		String path = Urls.URL_30;
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("cid", cid);
	   	List<BasicNameValuePair> params = HttpUtils.setParams(map);
	   	
	   	Map<String, String> map2 = new HashMap<String, String>();
	   	map2.put("Accept-Encoding", "gzip, deflate");
	   	map2.put("Accept", "application/json");
	   	map2.put("ContentType", "application/x-www-form-urlencoded; charset=UTF-8");

	   	String rst;
		try {
			String jsonStr = RequestService.getInstance().postRequest(params, path, map2);
		} catch (Exception e) {
			e.printStackTrace();
//			Message msg = new Message();
//			msg.what = Constants.NETWORK_ERROR;
//			handler.sendMessage(msg);
			return;
		}
		
//		Message msg = new Message();
//		handler.sendMessage(msg);
    }  
}
