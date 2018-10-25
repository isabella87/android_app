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
import com.banhuitong.item.PortalPrjEnts;
import com.banhuitong.util.Constants;
import com.banhuitong.util.HttpUtils;
import com.banhuitong.util.Urls;

public class GetPortalEntsTask implements Runnable {
	private static final String TAG = "GetPortalEntsTask" ;  
	private Handler handler;
	private int pn;
	
	public GetPortalEntsTask(Handler handler, int pn) {
		super();
		this.handler = handler;
		this.pn = pn;
	}

	@Override  
    public void run() {
//		String path = Constants.serverUrlp2p + "project/all";
		String path = Urls.URL_24;
		
		Map<String, String> map = new HashMap<String, String>();
	   	map.put("pn", Long.toString(pn));
	   	List<BasicNameValuePair> params = HttpUtils.setParams(map);
	   	
		Map<String, String> map2 = new HashMap<String, String>();
	   	map2.put("Accept-Encoding", "gzip, deflate");
	   	map2.put("Accept", "application/json");

		PortalPrjEnts portalPrjEnts = new PortalPrjEnts();
		
		try {
			String jsonStr = RequestService.getInstance().getRequest(params, path, map2);
			JSONObject jsonObject = new JSONObject(jsonStr);
			
			JSONArray ents = jsonObject.getJSONArray("items");
			for(int i = 0; i < ents.length() ; i++){ 
                JSONObject jsonEnt = (JSONObject)ents.get(i);
                
                Map<String,Object> prjEnt = new HashMap<String,Object>();
                prjEnt.put("itemNo", jsonEnt.getString("itemNo"));
				prjEnt.put("itemShowName", jsonEnt.getString("itemShowName"));
				prjEnt.put("moneyRate", BigDecimal.valueOf(jsonEnt.getDouble("moneyRate")));
				prjEnt.put("amt", BigDecimal.valueOf(jsonEnt.getDouble("amt")));
				prjEnt.put("borrowDays", jsonEnt.getLong("borrowDays"));
				prjEnt.put("flags", jsonEnt.getLong("flags"));
				prjEnt.put("type", jsonEnt.getLong("type"));
				prjEnt.put("financingEndTime", new Date(jsonEnt.getLong("financingEndTime")));
				prjEnt.put("investedMoney", BigDecimal.valueOf(jsonEnt.getDouble("investedMoney")));
				prjEnt.put("status", jsonEnt.getLong("status"));
				prjEnt.put("pId", jsonEnt.getLong("pId"));
				prjEnt.put("hash", jsonEnt.getString("hash"));
				prjEnt.put("waterMark", jsonEnt.optString("waterMark", ""));
				
				portalPrjEnts.getEnts().add(prjEnt);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Message msg = new Message();  
	        msg.what = Constants.NETWORK_ERROR;
	        handler.sendMessage(msg); 
	        return;
		}
		
        Message msg = new Message();  
        msg.obj = portalPrjEnts;
        msg.what = Constants.GET_PORTAL_ENTS;
        handler.sendMessage(msg); 
    }  
}
