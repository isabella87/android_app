package com.banhuitong.async.jx;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;

import com.banhuitong.async.BaseTask;
import com.banhuitong.http.RequestService;
import com.banhuitong.item.AccIncomes;
import com.banhuitong.util.Constants;
import com.banhuitong.util.HttpUtils;
import com.banhuitong.util.Urls;

public class JxGetIncomeTask extends BaseTask {
	private static final String TAG = "JxGetIncomeTask" ;  
	private Handler handler;
	private int type;
	private String lastNxReld, lastNxTrnn, lastDatepoint;
	private int pn = 1;
	
	
	public JxGetIncomeTask(Handler handler, int type, String lastNxReld, String lastNxTrnn, String lastDatepoint, int pn) {
		super();
		this.handler = handler;
		this.lastNxReld = lastNxReld;
		this.lastNxTrnn = lastNxTrnn;
		this.lastDatepoint = lastDatepoint;
		this.type = type;
		this.pn = pn;
	}

	@Override  
    public void run() {
		String path = Urls.URL_38;
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("tran-type-flag", Long.toString(type));
		map.put("last-nx-reld", lastNxReld);
	   	map.put("last-nx-trnn", lastNxTrnn);
	   	map.put("last-datepoint", lastDatepoint);
	   	map.put("login-name-or-mobile", username);
		map.put("pwd", password);
		map.put("pn", Integer.toString(pn));
	   	List<BasicNameValuePair> params = HttpUtils.setParams(map);
	   	
		Map<String, String> map2 = new HashMap<String, String>();
	   	map2.put("Accept-Encoding", "gzip, deflate");
	   	map2.put("Accept", "application/json");

	   	AccIncomes accIncomes = new AccIncomes();
		try {
			String jsonStr = RequestService.getInstance().getRequest(params, path, map2);
			
			JSONObject obj = new JSONObject(jsonStr);
			JSONArray jsonArray = obj.getJSONArray("items");
			
			for(int i = 0; i < jsonArray.length() ; i++){ 
                JSONObject jsonObj = (JSONObject)jsonArray.get(i);
				
				Map<String,Object> accIncome = new HashMap<String,Object>();
				accIncome.put("datepoint", jsonObj.optLong("datepoint",0));
				accIncome.put("amount",BigDecimal.valueOf(type==1
						?(jsonObj.optDouble("creditAmt",0)>jsonObj.optDouble("unfrzAmt",0)
								?jsonObj.optDouble("creditAmt",0):jsonObj.optDouble("unfrzAmt",0))
								:(jsonObj.optDouble("debitAmt",0)>jsonObj.optDouble("frzAmt",0)
								?jsonObj.optDouble("debitAmt",0):(jsonObj.optDouble("frzAmt",0)))));
				accIncome.put("desc", (jsonObj.getString("remark")+"   "+jsonObj.getString("itemShowName")+"["+jsonObj.getString("itemNo")+"]"));
				accIncome.put("last-nx-reld", "-999");
				accIncome.put("last-nx-trnn","-999");
				
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
