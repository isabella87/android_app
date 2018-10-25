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

public class GetPrjBhbDetailTask implements Runnable {
	private static final String TAG = "GetPrjBhbDetailTask" ;  
	private Handler handler;
	private int tiId;
	
	public GetPrjBhbDetailTask(Handler handler, int tiId) {
		super();
		this.handler = handler;
		this.tiId = tiId;
	}

	@Override  
    public void run() {
//		String path = Constants.serverUrlp2p + "account/bhb-apply-detail/" + tiId;
		String path = "";
		
		Map<String, String> map = new HashMap<String, String>();
	   	List<BasicNameValuePair> params = HttpUtils.setParams(map);
	   	
		Map<String, String> map2 = new HashMap<String, String>();
	   	map2.put("Accept-Encoding", "gzip, deflate");
	   	map2.put("Accept", "application/json");

	   	Map<String,Object> prjBhbDetail = new HashMap<String,Object>();
		try {
			String jsonStr = RequestService.getInstance().getRequest(params, path, map2);
			JSONObject jsonObject = new JSONObject(jsonStr);
			
			prjBhbDetail.put("itemNo", jsonObject.optString("itemNo",""));
			prjBhbDetail.put("itemShowName", jsonObject.optString("itemShowName",""));
//			prjBhbDetail.put("daysRemaining", jsonObject.optLong("remainingDays",0));
			prjBhbDetail.put("curRate", BigDecimal.valueOf(jsonObject.optDouble("curRate",0)));
			prjBhbDetail.put("curAmt", BigDecimal.valueOf(jsonObject.optDouble("curAmt",0)));
			prjBhbDetail.put("amt", BigDecimal.valueOf(jsonObject.optDouble("amt",0)));
			prjBhbDetail.put("unPaiedBonusAmt", BigDecimal.valueOf(jsonObject.optDouble("unPaiedBonusAmt",0)));
			prjBhbDetail.put("expectedBorrowTime", jsonObject.optLong("expectedBorrowTime",0));
			prjBhbDetail.put("borrowDays", jsonObject.optLong("borrowDays",0));
			
		} catch (Exception e) {
			e.printStackTrace();
			Message msg = new Message();  
	        msg.what = Constants.NETWORK_ERROR;
	        handler.sendMessage(msg); 
	        return;
		}
		
        Message msg = new Message();  
        msg.obj = prjBhbDetail;
        msg.what = Constants.GET_PRJ_BHB_DETAIL;
        handler.sendMessage(msg); 
    }  
}
