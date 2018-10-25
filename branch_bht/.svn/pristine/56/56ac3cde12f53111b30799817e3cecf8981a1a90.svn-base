package com.banhuitong.async;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;

import com.banhuitong.http.RequestService;
import com.banhuitong.util.Constants;
import com.banhuitong.util.HttpUtils;
import com.banhuitong.util.Urls;

public class GetInvestGongDetailTask implements Runnable {
	private static final String TAG = "GetInvestGongDetailTask" ;  
	private Handler handler;
	private long pId, tiId, ttId;
	
	public GetInvestGongDetailTask(Handler handler, long pId, long tiId, long ttId) {
		super();
		this.handler = handler;
		this.pId = pId;
		this.tiId = tiId;
		this.ttId = ttId;
	}

	@Override  
    public void run() {
		String path = Urls.URL_20 + pId + "/";
		
		Map<String, String> map = new HashMap<String, String>();
	   	
	   	if(tiId>0){
	   		path += tiId;
	   	}else if(ttId>0){
	   		path += ttId;
	   	}
		
	   	List<BasicNameValuePair> params = HttpUtils.setParams(map);
	   	
		Map<String, String> map2 = new HashMap<String, String>();
	   	map2.put("Accept-Encoding", "gzip, deflate");
	   	map2.put("Accept", "application/json");

	   	Map<String, Object> rst = new HashMap<String, Object>();
	   	List<Map<String, Object>> bonusList = new ArrayList<Map<String, Object>>();
	   	rst.put("bonusList", bonusList);
		
		try {
			String jsonStr = RequestService.getInstance().getRequest(params, path, map2);
			JSONObject jsonObject = new JSONObject(jsonStr);
			
			rst.put("assignTime", jsonObject.optLong("assignTime",0));
			
			JSONArray repays = jsonObject.getJSONArray("bonusList");
			for(int i = 0; i < repays.length() ; i++){ 
                JSONObject jsonInvest = (JSONObject)repays.get(i);
                
                Map<String,Object> invest = new HashMap<String,Object>();
                invest.put("tranNo", jsonInvest.optLong("tranNo",0));
                invest.put("totalTranNo", jsonInvest.optLong("totalTranNo",0));
                invest.put("tranType",jsonInvest.optLong("tranType",-999));
                invest.put("status",jsonInvest.optLong("status",-999));
                invest.put("dueTime", jsonInvest.optLong("dueTime",0));
                invest.put("amt", BigDecimal.valueOf(jsonInvest.optDouble("amt",0)));
                invest.put("paidTime", jsonInvest.optLong("paidTime",0));
                invest.put("paidAmt", BigDecimal.valueOf(jsonInvest.optDouble("paidAmt",0)));
                
                bonusList.add(invest);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Message msg = new Message();  
	        msg.what = Constants.NETWORK_ERROR;
	        handler.sendMessage(msg); 
	        return;
		}
		
        Message msg = new Message();  
        msg.obj = rst;
        msg.what = Constants.GET_INVEST_ENT_DETAIL;
        handler.sendMessage(msg); 
    }  
}
