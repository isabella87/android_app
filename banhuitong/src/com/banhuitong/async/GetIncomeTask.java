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
import com.banhuitong.item.AccIncomes;
import com.banhuitong.util.Constants;
import com.banhuitong.util.HttpUtils;
import com.banhuitong.util.Urls;

public class GetIncomeTask implements Runnable {
	private static final String TAG = "GetIncomeTask" ;  
	private Handler handler;
	private int pn;
	private int type;
	
	public GetIncomeTask(Handler handler, int pn, int type) {
		super();
		this.handler = handler;
		this.pn = pn;
		this.type = type;
	}

	@Override  
    public void run() {
//		String path = Constants.serverUrlp2p + "account/income";
		String path = Urls.URL_18;
		
		Map<String, String> map = new HashMap<String, String>();
	   	map.put("pn", Long.toString(pn));
	   	map.put("type", Long.toString(type));
	   	List<BasicNameValuePair> params = HttpUtils.setParams(map);
	   	
		Map<String, String> map2 = new HashMap<String, String>();
	   	map2.put("Accept-Encoding", "gzip, deflate");
	   	map2.put("Accept", "application/json");

	   	AccIncomes accIncomes = new AccIncomes();
		
		try {
			String jsonStr = RequestService.getInstance().getRequest(params, path, map2);
			JSONObject jsonObject = new JSONObject(jsonStr);
			
			JSONArray incomes = jsonObject.getJSONArray("list");
			for(int i = 0; i < incomes.length() ; i++){ 
                JSONObject jsonCas = (JSONObject)incomes.get(i);
				
				Map<String,Object> accIncome = new HashMap<String,Object>();
				accIncome.put("arId", jsonCas.getString("arId"));
				accIncome.put("datepoint", jsonCas.optLong("datepoint",0));
				accIncome.put("arType", jsonCas.getLong("arType"));
				accIncome.put("creditAmt", BigDecimal.valueOf(jsonCas.optDouble("creditAmt",0)));
				accIncome.put("debitAmt", BigDecimal.valueOf(jsonCas.optDouble("debitAmt",0)));
				accIncome.put("unfrzAmt", BigDecimal.valueOf(jsonCas.optDouble("unfrzAmt",0)));
				accIncome.put("frzAmt", BigDecimal.valueOf(jsonCas.optDouble("frzAmt",0)));
				accIncome.put("remark", (jsonCas.getString("remark")));
				accIncome.put("itemShowName", (jsonCas.getString("itemShowName")));
				
				accIncomes.getIncomes().add(accIncome);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Message msg = new Message();  
	        msg.what = Constants.NETWORK_ERROR;
	        handler.sendMessage(msg); 
	        return;
		}
		
        Message msg = new Message();  
        msg.obj = accIncomes;
        msg.what = Constants.GET_ACC_INCOME;
        handler.sendMessage(msg); 
    }  
}
