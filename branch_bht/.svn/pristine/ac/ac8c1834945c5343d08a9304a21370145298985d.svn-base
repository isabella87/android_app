package com.banhuitong.async;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;

import com.banhuitong.http.RequestService;
import com.banhuitong.item.PortalPrjCas;
import com.banhuitong.util.Constants;
import com.banhuitong.util.HttpUtils;
import com.banhuitong.util.Urls;

public class GetPortalCasTask implements Runnable {
	private static final String TAG = "GetPortalCasTask" ;  
	private Handler handler;
	private int pn;
	
	public GetPortalCasTask(Handler handler, int pn) {
		super();
		this.handler = handler;
		this.pn = pn;
	}

	@Override  
    public void run() {
//		String path = Constants.serverUrlp2p + "creditassign/all";
		String path = Urls.URL_23;
		
		Map<String, String> map = new HashMap<String, String>();
	   	map.put("pn", Long.toString(pn));
	   	List<BasicNameValuePair> params = HttpUtils.setParams(map);
	   	
		Map<String, String> map2 = new HashMap<String, String>();
	   	map2.put("Accept-Encoding", "gzip, deflate");
	   	map2.put("Accept", "application/json");

		PortalPrjCas portalPrjCas = new PortalPrjCas();
		
		try {
			String jsonStr = RequestService.getInstance().getRequest(params, path, map2);
			JSONObject jsonObject = new JSONObject(jsonStr);
			
			JSONArray cas = jsonObject.getJSONArray("items");
			for(int i = 0; i < cas.length() ; i++){ 
                JSONObject jsonCas = (JSONObject)cas.get(i);
				
				Map<String,Object> prjCa = new HashMap<String,Object>();
				prjCa.put("itemNo", jsonCas.getString("itemNo"));
				prjCa.put("itemShowName", jsonCas.getString("itemShowName"));
				prjCa.put("assignAmount", BigDecimal.valueOf(jsonCas.optDouble("assignAmmount",0)));
				prjCa.put("creditAmount", BigDecimal.valueOf(jsonCas.optDouble("creditAmount",0)));
				prjCa.put("contractEndDate", jsonCas.getLong("contractEndDate"));
				prjCa.put("unpaidAmt", BigDecimal.valueOf(jsonCas.getDouble("unpaidAmt")));
				prjCa.put("pId", jsonCas.getLong("pId"));
				prjCa.put("hash", (jsonCas.getString("hash")));
				prjCa.put("type", (jsonCas.getLong("type")));
				prjCa.put("daysRemaining", jsonCas.getInt("daysRemaining"));
				prjCa.put("curRate", BigDecimal.valueOf(jsonCas.optDouble("curRate",0)));
				prjCa.put("pId", jsonCas.getLong("pId"));
				prjCa.put("hash", jsonCas.getString("hash"));
				prjCa.put("createTime", jsonCas.optLong("createTime",0));
				prjCa.put("assignDays", jsonCas.optLong("assignDays",0));
				
				portalPrjCas.getCas().add(prjCa);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Message msg = new Message();  
	        msg.what = Constants.NETWORK_ERROR;
	        handler.sendMessage(msg); 
	        return;
		}
		
        Message msg = new Message();  
        msg.obj = portalPrjCas;
        msg.what = Constants.GET_PORTAL_CAS;
        handler.sendMessage(msg); 
    }  
}
