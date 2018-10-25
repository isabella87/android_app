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
import com.banhuitong.item.AccInvestBao;
import com.banhuitong.util.Constants;
import com.banhuitong.util.HttpUtils;

public class GetInvestBaoTask implements Runnable {
	private static final String TAG = "GetInvestBaoTask" ;  
	private Handler handler;
	private int pn;
	private int status;
	
	public GetInvestBaoTask(Handler handler, int pn, int status) {
		super();
		this.handler = handler;
		this.pn = pn;
		this.status = status;
	}

	@Override  
    public void run() {
//		String path = Constants.serverUrlp2p + "account/bhb";
		String path = "";
		
		Map<String, String> map = new HashMap<String, String>();
	   	map.put("pn", Long.toString(pn));
		map.put("status", Long.toString(status));
	   	List<BasicNameValuePair> params = HttpUtils.setParams(map);
	   	
		Map<String, String> map2 = new HashMap<String, String>();
	   	map2.put("Accept-Encoding", "gzip, deflate");
	   	map2.put("Accept", "application/json");

	   	AccInvestBao accInvestBao = new AccInvestBao();
		
		try {
			String jsonStr = RequestService.getInstance().getRequest(params, path, map2);
			JSONObject jsonObject = new JSONObject(jsonStr);
			
			JSONArray invests = jsonObject.getJSONArray("items");
			for(int i = 0; i < invests.length() ; i++){ 
                JSONObject jsonInvest = (JSONObject)invests.get(i);
                
                Map<String,Object> invest = new HashMap<String,Object>();
                invest.put("itemNo", jsonInvest.optString("itemNo",""));
                invest.put("itemShowName", jsonInvest.optString("itemShowName",""));
                invest.put("dueRate", BigDecimal.valueOf(jsonInvest.optDouble("dueRate",0)));
                invest.put("curRate", BigDecimal.valueOf(jsonInvest.optDouble("curRate",0)));
                invest.put("amt", BigDecimal.valueOf(jsonInvest.optDouble("amt",0)));
                invest.put("curAmt", BigDecimal.valueOf(jsonInvest.optDouble("curAmt",0)));
                invest.put("borrowDays", jsonInvest.optLong("borrowDays",0));
                invest.put("tenderTime", jsonInvest.optLong("tenderTime",0));
                invest.put("endTime", jsonInvest.optLong("endTime",0));
                invest.put("expectedBorrowTime",jsonInvest.optLong("expectedBorrowTime",0));
                invest.put("status",jsonInvest.optLong("status",-999));
                invest.put("pId", jsonInvest.optLong("pId",0));
                invest.put("hash", jsonInvest.optString("hash",""));
                invest.put("sType", jsonInvest.optLong("sType",0));
                invest.put("type", jsonInvest.optLong("type",0));
                invest.put("unHoldTime", jsonInvest.optLong("unHoldTime",0));
                invest.put("holdDays", jsonInvest.optLong("holdDays",0));
                invest.put("realRate", BigDecimal.valueOf(jsonInvest.optDouble("realRate",0)));
                invest.put("realBonusAmt", BigDecimal.valueOf(jsonInvest.optDouble("realBonusAmt",0)));
				
				accInvestBao.getInvests().add(invest);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			Message msg = new Message();  
	        msg.what = Constants.NETWORK_ERROR;
	        handler.sendMessage(msg); 
	        return;
		}
		
        Message msg = new Message();  
        msg.obj = accInvestBao;
        msg.what = Constants.GET_INVEST_BHB;
        handler.sendMessage(msg); 
    }  
}
