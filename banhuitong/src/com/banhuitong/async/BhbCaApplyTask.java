package com.banhuitong.async;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.message.BasicNameValuePair;

import android.os.Handler;
import android.os.Message;

import com.banhuitong.http.RequestService;
import com.banhuitong.util.Constants;
import com.banhuitong.util.HttpUtils;

public class BhbCaApplyTask implements Runnable {
	
	private static final String TAG = "BhbCaApplyTask" ;  
	private Handler handler;
	private String tiid;
	private String mobileCode;
	private String pwd;
	private String assignDays;
	private String assignAmount;
	
	public BhbCaApplyTask(Handler handler, String tiid, String mobileCode, String pwd, 
				String assignDays, String assignAmount) {
		super();
		this.handler = handler;
		this.tiid = tiid;
		this.mobileCode = mobileCode;
		this.pwd = pwd;
		this.assignDays = assignDays;
		this.assignAmount = assignAmount;
	}

	@Override  
    public void run() {  
//		String path = Constants.serverUrlp2p + "account/create-bhb-credit-assign/" + tiid;
		String path = "";
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("mobileCode", mobileCode);
		map.put("pwd", pwd);
		map.put("assignDays", assignDays);
		map.put("assignAmount", new BigDecimal(assignAmount).setScale(0, RoundingMode.DOWN).toString());
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
			msg.what = Constants.CA_APPLY_FAILED;
			handler.sendMessage(msg);
		}else{
			Message msg = new Message();
			msg.obj = rst;
			msg.what = Constants.CA_APPLY_SUCCESS;
			handler.sendMessage(msg);
		}
    }  
}
