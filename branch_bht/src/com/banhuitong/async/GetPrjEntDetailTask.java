package com.banhuitong.async;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;

import com.banhuitong.http.RequestService;
import com.banhuitong.util.Constants;
import com.banhuitong.util.HttpUtils;
import com.banhuitong.util.Urls;

public class GetPrjEntDetailTask implements Runnable {
	private static final String TAG = "GetPrjEntDetailTask" ;  
	private Handler handler;
	private int tiId;
	
	public GetPrjEntDetailTask(Handler handler, int tiId) {
		super();
		this.handler = handler;
		this.tiId = tiId;
	}

	@Override  
    public void run() {
//		String path = Constants.serverUrlp2p + "account/prj-ent-detail/" + tiId;
		String path = Urls.URL_25 + tiId;
		
		Map<String, String> map = new HashMap<String, String>();
	   	List<BasicNameValuePair> params = HttpUtils.setParams(map);
	   	
		Map<String, String> map2 = new HashMap<String, String>();
	   	map2.put("Accept-Encoding", "gzip, deflate");
	   	map2.put("Accept", "application/json");

	   	Map<String,Object> prjEntDetail = new HashMap<String,Object>();
		try {
			String jsonStr = RequestService.getInstance().getRequest(params, path, map2);
			JSONObject jsonObject = new JSONObject(jsonStr);
			
			prjEntDetail.put("itemNo", jsonObject.optString("entItemNo",""));
			prjEntDetail.put("itemShowName", jsonObject.optString("itemShowName",""));
			prjEntDetail.put("daysRemaining", jsonObject.optLong("daysRemaining",0));
			prjEntDetail.put("moneyRate", BigDecimal.valueOf(jsonObject.optDouble("moneyRate",0)));
			prjEntDetail.put("unpaidAmt", BigDecimal.valueOf(jsonObject.optDouble("unpaidAmt",0)));
			prjEntDetail.put("capital", BigDecimal.valueOf(jsonObject.optDouble("capital",0)));
			
		} catch (Exception e) {
			e.printStackTrace();
			Message msg = new Message();  
	        msg.what = Constants.NETWORK_ERROR;
	        handler.sendMessage(msg); 
	        return;
		}
		
        Message msg = new Message();  
        msg.obj = prjEntDetail;
        msg.what = Constants.GET_PRJ_ENT_DETAIL;
        handler.sendMessage(msg); 
    }  
}
