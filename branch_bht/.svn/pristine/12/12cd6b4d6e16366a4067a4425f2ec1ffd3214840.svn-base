package com.banhuitong.async;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;

import com.banhuitong.http.RequestService;
import com.banhuitong.item.PortalPrjBhbs;
import com.banhuitong.util.Constants;
import com.banhuitong.util.HttpUtils;

public class GetPortalBhbsTask implements Runnable {
	private static final String TAG = "GetPortalBhbsTask" ;  
	private Handler handler;
	private int pn;
	
	public GetPortalBhbsTask(Handler handler, int pn) {
		super();
		this.handler = handler;
		this.pn = pn;
	}

	@Override  
    public void run() {
//		String path = Constants.serverUrlp2p + "bhb/all";
		String path = "";
		
		Map<String, String> map = new HashMap<String, String>();
	   	map.put("pn", Long.toString(pn));
	   	List<BasicNameValuePair> params = HttpUtils.setParams(map);
	   	
		Map<String, String> map2 = new HashMap<String, String>();
	   	map2.put("Accept-Encoding", "gzip, deflate");
	   	map2.put("Accept", "application/json");

		PortalPrjBhbs portalPrjBhbs = new PortalPrjBhbs();
		
		try {
			String jsonStr = RequestService.getInstance().getRequest(params, path, map2);
			JSONObject jsonObject = new JSONObject(jsonStr);
			
			JSONArray bhbs = jsonObject.getJSONArray("items");
			for(int i = 0; i < bhbs.length() ; i++){ 
                JSONObject jsonBhb = (JSONObject)bhbs.get(i);
                
                Map<String,Object> prjBhb = new HashMap<String,Object>();
                prjBhb.put("itemNo", jsonBhb.optString("itemNo"));
                prjBhb.put("itemShowName", jsonBhb.optString("itemShowName"));
                prjBhb.put("targetRate", BigDecimal.valueOf(jsonBhb.optDouble("targetRate")));
                prjBhb.put("priorAmt", BigDecimal.valueOf(jsonBhb.optDouble("priorAmt")));
                prjBhb.put("riskAmt", BigDecimal.valueOf(jsonBhb.optDouble("riskAmt")));
                prjBhb.put("priorInvestedAmt", BigDecimal.valueOf(jsonBhb.optDouble("priorInvestedAmt")));
                prjBhb.put("riskInvestedAmt", BigDecimal.valueOf(jsonBhb.optDouble("riskInvestedAmt")));
                prjBhb.put("outTime", new Date(jsonBhb.optLong("outTime")));
                prjBhb.put("financingDays", jsonBhb.optLong("financingDays"));
                prjBhb.put("borrowDays", jsonBhb.optLong("borrowDays"));
                prjBhb.put("status", jsonBhb.optLong("status"));
                prjBhb.put("pId", jsonBhb.optLong("pId"));
                prjBhb.put("hash", jsonBhb.optString("hash"));
				
				portalPrjBhbs.getBhbs().add(prjBhb);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Message msg = new Message();  
	        msg.what = Constants.NETWORK_ERROR;
	        handler.sendMessage(msg); 
	        return;
		}
		
        Message msg = new Message();  
        msg.obj = portalPrjBhbs;
        msg.what = Constants.GET_PORTAL_BHBS;
        handler.sendMessage(msg); 
    }  
}
