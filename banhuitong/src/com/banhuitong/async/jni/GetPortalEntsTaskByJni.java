package com.banhuitong.async.jni;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;

import com.banhuitong.http.RequestService;
import com.banhuitong.item.PortalPrjEnts;
import com.banhuitong.util.Constants;
import com.banhuitong.util.HttpUtils;

public class GetPortalEntsTaskByJni implements Runnable {
	private static final String TAG = "GetPortalEntsTaskByJni" ;  
	private Handler handler;
	private int pn;
	
	public GetPortalEntsTaskByJni(Handler handler, int pn) {
		super();
		this.handler = handler;
		this.pn = pn;
	}

	@Override  
    public void run() {	
		/*Map<String, String> map = new HashMap<String, String>();
	   	map.put("pn", Long.toString(pn));
	   	List<BasicNameValuePair> params = HttpUtils.setParams(map);
	   	String param = URLEncodedUtils.format(params, "UTF-8");
	   	
		String path = Constants.domainPath + "project/all";
		String cookieString = "";
		if(RequestService.extCookie!=null){
			cookieString = RequestService.extCookie.getName() + "=" + RequestService.extCookie.getValue();
		}
		
		String req = "GET /" + path + " HTTP/1.1\r\nHost:" + Constants.serverHost 
				+ "\r\nAccept:application/json\r\nAccept-Encoding:gzip, deflate\r\nCookie: " + cookieString +"\r\nConnection: Close\r\n\r\n" + param +"\r\n\r\n";

		PortalPrjEnts portalPrjEnts = new PortalPrjEnts();
		
		try {
			String jsonStr = RequestService.getInstance().doRequest(Constants.serverHost, req, Constants.serverPort);
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
        handler.sendMessage(msg); */
    }  
}
