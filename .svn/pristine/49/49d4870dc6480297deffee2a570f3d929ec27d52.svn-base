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
import com.banhuitong.item.AccInvestGong;
import com.banhuitong.util.Constants;
import com.banhuitong.util.HttpUtils;
import com.banhuitong.util.Urls;

public class GetInvestGongTask implements Runnable {
	private static final String TAG = "GetInvestGongTask" ;  
	private Handler handler;
	private int pn;
	private int status;
	
	public GetInvestGongTask(Handler handler, int pn, int status) {
		super();
		this.handler = handler;
		this.pn = pn;
		this.status = status;
	}

	@Override  
    public void run() {
//		String path = Constants.serverUrlp2p + "account/investment";
		String path = Urls.URL_21;
		
		Map<String, String> map = new HashMap<String, String>();
	   	map.put("pn", Long.toString(pn));
		map.put("status", Long.toString(status));
	   	List<BasicNameValuePair> params = HttpUtils.setParams(map);
	   	
		Map<String, String> map2 = new HashMap<String, String>();
	   	map2.put("Accept-Encoding", "gzip, deflate");
	   	map2.put("Accept", "application/json");

	   	AccInvestGong accInvestGong = new AccInvestGong();
		
		try {
			String jsonStr = RequestService.getInstance().getRequest(params, path, map2);
			JSONObject jsonObject = new JSONObject(jsonStr);
			
			JSONArray invests = jsonObject.getJSONArray("items");
			for(int i = 0; i < invests.length() ; i++){ 
                JSONObject jsonInvest = (JSONObject)invests.get(i);
                
                Map<String,Object> invest = new HashMap<String,Object>();
                invest.put("itemNo", jsonInvest.optString("itemNo",""));
                invest.put("itemShowName", jsonInvest.optString("itemShowName",""));
                invest.put("moneyRate", BigDecimal.valueOf(jsonInvest.optDouble("moneyRate",0)));
                invest.put("amt", BigDecimal.valueOf(jsonInvest.optDouble("amt",0)));
                invest.put("borrowDays", jsonInvest.optLong("borrowDays",0));
                invest.put("repayCapitalDate", jsonInvest.optLong("repayCapitalDate",0));
                invest.put("investTime", jsonInvest.optLong("investTime",0));
                invest.put("cancelDate", jsonInvest.optLong("cancelDate",0));
                invest.put("status",jsonInvest.optLong("status",-999));
                invest.put("pId", jsonInvest.optLong("pId",0));
                invest.put("tiId", jsonInvest.optLong("tiId",0));
                invest.put("hash", jsonInvest.optString("hash",""));
                invest.put("sType", jsonInvest.optLong("sType",0));
                invest.put("oriType", jsonInvest.optInt("oriType",-999));
                invest.put("flags", jsonInvest.optInt("flags",-999));
				
				accInvestGong.getInvests().add(invest);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Message msg = new Message();  
	        msg.what = Constants.NETWORK_ERROR;
	        handler.sendMessage(msg); 
	        return;
		}
		
        Message msg = new Message();  
        msg.obj = accInvestGong;
        msg.what = Constants.GET_INVEST_ENTS;
        handler.sendMessage(msg); 
    }  
}
