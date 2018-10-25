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

public class FindbackPwdTask implements Runnable {
	
	private static final String TAG = "FindbackPwdTask" ;  
	private Handler handler;
	private String usernameOrMobile;
	private String activeCode;
	
	public FindbackPwdTask(Handler handler, String usernameOrMobile, String activeCode) {
		super();
		this.handler = handler;
		this.activeCode = activeCode;
		this.usernameOrMobile = usernameOrMobile;
	}

	@Override  
    public void run() {  
//		String path = Constants.serverUrlAcc + "account/reset-pwd";
		String path = Urls.URL_10;
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("login-name-or-mobile", usernameOrMobile);
		map.put("active-code", activeCode);

	   	List<BasicNameValuePair> params = HttpUtils.setParams(map);

	   	String rstStr;
		try {
			String jsonStr = RequestService.getInstance().postRequest(params, path, new HashMap<String, String>());
		   	rstStr = jsonStr;
		   	
		} catch (Exception e) {
			e.printStackTrace();
			Message msg = new Message();
			msg.what = Constants.NETWORK_ERROR;
			handler.sendMessage(msg);
			return;
		}
		
		Map<String, Object> rst = new HashMap<String, Object>();
		
		if(rstStr!=null && rstStr.contains("true")){
			Message msg = new Message();
			msg.obj = rst;
			msg.what = Constants.MOBILE_CODE_VALID;
			handler.sendMessage(msg);
		}else{
			if(rstStr.contains("*")){
				rstStr = rstStr.substring(rstStr.indexOf("*")+1);
			}
			rst.put("errorMsg", rstStr);
			
			Message msg = new Message();
			msg.obj = rst;
			msg.what = Constants.MOBILE_CODE_INVALID;
			handler.sendMessage(msg);
		}
    }  
}
