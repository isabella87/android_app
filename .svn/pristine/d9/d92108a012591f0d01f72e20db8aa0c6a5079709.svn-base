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
import com.banhuitong.item.AccInvestZhuan;
import com.banhuitong.util.Constants;
import com.banhuitong.util.HttpUtils;
import com.banhuitong.util.Urls;

public class GetInvestZhuanTask implements Runnable {
	private static final String TAG = "GetInvestZhuanTask" ;  
	private Handler handler;
	private int pn;
	private int status;
	
	public GetInvestZhuanTask(Handler handler, int pn, int status) {
		super();
		this.handler = handler;
		this.pn = pn;
		this.status = status;
	}

	@Override  
    public void run() {
//		String path = Constants.serverUrlp2p + "account/credit-assign";
		String path = Urls.URL_22;
		
		Map<String, String> map = new HashMap<String, String>();
	   	map.put("pn", Long.toString(pn));
		map.put("status", Long.toString(status));
	   	List<BasicNameValuePair> params = HttpUtils.setParams(map);
	   	
		Map<String, String> map2 = new HashMap<String, String>();
	   	map2.put("Accept-Encoding", "gzip, deflate");
	   	map2.put("Accept", "application/json");

	   	AccInvestZhuan accInvestZhuan = new AccInvestZhuan();
		
		try {
			String jsonStr = RequestService.getInstance().getRequest(params, path, map2);
			JSONObject jsonObject = new JSONObject(jsonStr);
			
			JSONArray invests = jsonObject.getJSONArray("items");
			for(int i = 0; i < invests.length() ; i++){ 
                JSONObject jsonInvest = (JSONObject)invests.get(i);
                
                Map<String,Object> invest = new HashMap<String,Object>();
                invest.put("itemNo", jsonInvest.optString("itemNo",""));
                invest.put("itemShowName", jsonInvest.optString("itemShowName",""));
                invest.put("repayCapitalDate", jsonInvest.optLong("repayCapitalDate",0));
                invest.put("investTime", jsonInvest.optLong("investTime",0));
                invest.put("applyTime", jsonInvest.optLong("applyTime",0));
                invest.put("assignRate", BigDecimal.valueOf(jsonInvest.optDouble("assignRate",0)));
                invest.put("curRate", BigDecimal.valueOf(jsonInvest.optDouble("curRate",0)));
                invest.put("assignDaysLeft", jsonInvest.optLong("assignDaysLeft",0));
                invest.put("unpaidAmt", BigDecimal.valueOf(jsonInvest.optDouble("unpaidAmt",0)));
                invest.put("assignAmt", BigDecimal.valueOf(jsonInvest.optDouble("assignAmt",0)));
                invest.put("transactionDate", jsonInvest.optLong("transactionDate",0));
                invest.put("fee", BigDecimal.valueOf(jsonInvest.optDouble("fee",0)));
                invest.put("transactionAmt", BigDecimal.valueOf(jsonInvest.optDouble("transactionAmt",0)));
                invest.put("sType", jsonInvest.optInt("sType",-999));
                invest.put("lastSType", jsonInvest.optInt("lastSType",-999));
                invest.put("pType", jsonInvest.optInt("pType",-999));
                invest.put("amt", BigDecimal.valueOf(jsonInvest.optDouble("amt",0)));
                invest.put("moneyRate", BigDecimal.valueOf(jsonInvest.optDouble("moneyRate",0)));
                invest.put("tiId", jsonInvest.optLong("tiId",0));
                invest.put("pId", jsonInvest.optLong("pId",0));
                invest.put("oriType", jsonInvest.optInt("oriType",-999));
                invest.put("flags", jsonInvest.optInt("flags",-999));
                
				accInvestZhuan.getInvests().add(invest);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			Message msg = new Message();  
	        msg.what = Constants.NETWORK_ERROR;
	        handler.sendMessage(msg); 
	        return;
		}
		
        Message msg = new Message();  
        msg.obj = accInvestZhuan;
        msg.what = Constants.GET_INVEST_CA;
        handler.sendMessage(msg); 
    }  
}
