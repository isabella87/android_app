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

public class SendMobileCodeTask implements Runnable {
	
	private static final String TAG = "SendMobileCodeTask" ;  
	private Handler handler;
	private String mobile;
	private String captchaCode;
	
	public SendMobileCodeTask(Handler handler, String mobile, String captchaCode) {
		super();
		this.handler = handler;
		this.mobile = mobile;
		this.captchaCode = captchaCode;
	}

	@Override  
    public void run() {  
//		String path = Constants.serverUrlAcc + "reg/send-user-mobile-code";
		String path = Urls.URL_7;
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("mobile", mobile);
		map.put("captcha-code", captchaCode);
	   	List<BasicNameValuePair> params = HttpUtils.setParams(map);
	   	
	   	Map<String, String> map2 = new HashMap<String, String>();
	   	map2.put("Accept-Encoding", "gzip, deflate");
	   	map2.put("Accept", "application/json");
	   	map2.put("ContentType", "application/x-www-form-urlencoded; charset=UTF-8");

	   	String rstStr;
		try {
			String jsonStr = RequestService.getInstance().putRequest(params, path, map2);
			rstStr = jsonStr;
		} catch (Exception e) {
			e.printStackTrace();
			Message msg = new Message();
			msg.what = Constants.NETWORK_ERROR;
			handler.sendMessage(msg);
			return;
		}
		
		Map<String, Object> rst = new HashMap<String, Object>();
		if(rstStr.startsWith(RequestService.SIGN_ERROR)){
			rstStr = rstStr.substring(rstStr.indexOf("*")+1);
			rst.put("errorMsg", rstStr);
			
			Message msg = new Message();
			msg.obj = rst;
			msg.what = Constants.SEND_MOBILE_CODE_FAILED;
			handler.sendMessage(msg);
		}else{
			Message msg = new Message();
			msg.obj = rst;
			msg.what = Constants.SEND_MOBILE_CODE_SUCCESS;
			handler.sendMessage(msg);
		}
    }  
}