package com.banhuitong.async.jni;

import java.math.BigDecimal;
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
import com.banhuitong.item.AccIncomes;
import com.banhuitong.util.Constants;
import com.banhuitong.util.HttpUtils;

public class GetIncomeTaskByJni implements Runnable {
	private static final String TAG = "GetIncomeTaskByJni" ;  
	private Handler handler;
	private int pn;
	private int type;
	
	public GetIncomeTaskByJni(Handler handler, int pn, int type) {
		super();
		this.handler = handler;
		this.pn = pn;
		this.type = type;
	}

	@Override  
    public void run() {
		/*Map<String, String> map = new HashMap<String, String>();
	   	map.put("pn", Long.toString(pn));
	   	map.put("type", Long.toString(type));
	   	List<BasicNameValuePair> params = HttpUtils.setParams(map);
	   	String param = URLEncodedUtils.format(params, "UTF-8");
	   	
		String path = Constants.domainPath + "account/income";
		String cookieString = "";
		if(RequestService.extCookie!=null){
			cookieString = RequestService.extCookie.getName() + "=" + RequestService.extCookie.getValue();
		}
		
		String req = "GET /" + path + " HTTP/1.1\r\nHost:" + Constants.serverHost 
				+ "\r\nAccept:application/json\r\nAccept-Encoding:gzip, deflate\r\nCookie: " + cookieString +"\r\nConnection: Close\r\n\r\n" + param +"\r\n\r\n";
//		req = "GET /midsrv/account/income HTTP/1.1\r\nHost:192.168.11.11\r\nAccept:application/json\r\nAccept-Encoding:gzip, deflate\r\nCookie: __auth__=f3f04e89841f48613731a85498174e4b43089914b5eb1b16d591d90c7dc2b79b03d8728514d1ef27\r\nConnection: Close\r\n\r\n type=3&pn=1\r\n\r\n";
		
	   	AccIncomes accIncomes = new AccIncomes();
		
		try {
			String jsonStr = RequestService.getInstance().doRequest(Constants.serverHost, req, Constants.serverPort);
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
        handler.sendMessage(msg); */
    }  
}
