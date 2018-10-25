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

public class JxBindCardTask extends BaseTask {
	private static final String TAG = "JxBindCardTask" ;  
	private Handler handler;
	private Context ctx;
	private String mobileCode, bankcard;
	
	public JxBindCardTask(Handler handler, String mobileCode,
			String bankcard, Context ctx) {
		super();
		this.handler = handler;
		this.ctx = ctx;
		this.mobileCode = mobileCode;
		this.bankcard = bankcard;
	}

	@Override  
    public void run() {
//		String path = Constants.serverUrlp2p + "trans/bind-card";
		String path = Urls.URL_31;
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("login-name-or-mobile", username);
		map.put("pwd", password);
		map.put("sig-card", bankcard);
		map.put("mobile-code", mobileCode);
	   	List<BasicNameValuePair> params = HttpUtils.setParams(map);
	   	
		Map<String, String> map2 = new HashMap<String, String>();
	   	map2.put("Accept-Encoding", "gzip, deflate");
	   	map2.put("Accept", "application/json");

		String jsonStr = "";
		try {

			jsonStr = RequestService.getInstance().postRequest(params, path,
					map2);
		} catch (Exception e) {
			e.printStackTrace();
			Message msg = new Message();  
	        msg.what = Constants.NETWORK_ERROR;
	        handler.sendMessage(msg); 
	        return;
		}
		
		Map<String, Object> rst = new HashMap<String, Object>();
		if(jsonStr.startsWith(RequestService.SIGN_ERROR)){
			jsonStr = jsonStr.substring(jsonStr.indexOf("*")+1);
			rst.put("errorMsg", jsonStr);
			
			Message msg = new Message();
			msg.obj = rst;
			msg.what = Constants.JX_BIND_CARD_FAILED;
			handler.sendMessage(msg);
		}else{
			Message msg = new Message();
			msg.obj = rst;
			msg.what = Constants.JX_BIND_CARD_SUCCESS;
			handler.sendMessage(msg);
		}
    }  
}