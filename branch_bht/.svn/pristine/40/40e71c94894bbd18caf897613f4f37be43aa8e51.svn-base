package com.banhuitong.async;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.banhuitong.cache.CacheService;
import com.banhuitong.enumerate.InComeDetailType;
import com.banhuitong.http.RequestService;
import com.banhuitong.util.Constants;
import com.banhuitong.util.HttpUtils;
import com.banhuitong.util.Urls;

public class GetIncomeTotalTask implements Runnable {
	private static final String TAG = "GetIncomeTotalTask" ;  
	private Handler handler;
	private int type;
	private Context ctx;
	
	public GetIncomeTotalTask(Handler handler, int type, Context ctx) {
		super();
		this.handler = handler;
		this.type = type;
		this.ctx = ctx;
	}

	@Override  
    public void run() {
//		String path = Constants.serverUrlp2p + "account/income-total-amt";
		String path = Urls.URL_19;
		
		Map<String, String> map = new HashMap<String, String>();
	   	map.put("type", Long.toString(type));
	   	List<BasicNameValuePair> params = HttpUtils.setParams(map);
	   	
		Map<String, String> map2 = new HashMap<String, String>();
	   	map2.put("Accept-Encoding", "gzip, deflate");
	   	map2.put("Accept", "application/json");

	   	String incomeTotal = "";
	   	String key = "";
	   	String jsonStr = "";
	   	
	    if(type==InComeDetailType.RECHARGE.value()){
	    	key = "totalIncome_recharge";
        }else if(type==InComeDetailType.WITHDRAW.value()){
        	key = "totalIncome_withdraw";
        }else if(type==InComeDetailType.TENDER.value()){
        	key = "totalIncome_tender";
        }else if(type==InComeDetailType.REPAY.value()){
        	key = "totalIncome_repay";
        }else if(type==InComeDetailType.REWARD.value()){
        	key = "totalIncome_reward";
        }else if(type==InComeDetailType.FEE.value()){
        	key = "totalIncome_fee";
        }
	    
		try {
			//缓存json
			Map<String,String> cacheAcc = CacheService.getInstance(ctx).query(key);
			if(cacheAcc.get("value")==null){
				jsonStr = RequestService.getInstance().getRequest(params, path, map2);
				CacheService.getInstance(ctx).insert(key, jsonStr);
			}else if(new Date().getTime() - NumberUtils.toLong(cacheAcc.get("datepoint"),0) > 30*1000){
				jsonStr = RequestService.getInstance().getRequest(params, path, map2);
				CacheService.getInstance(ctx).update(key, jsonStr);
			}else{
				jsonStr = cacheAcc.get("value");
			}
			
			jsonStr = RequestService.getInstance().getRequest(params, path, map2);
			JSONObject jsonObject = new JSONObject(jsonStr);
			
			incomeTotal = jsonObject.getString("totalAmt");
		} catch (Exception e) {
			e.printStackTrace();
			Message msg = new Message();  
	        msg.what = Constants.NETWORK_ERROR;
	        handler.sendMessage(msg); 
	        return;
		}
		
        Message msg = new Message();  
        msg.obj = incomeTotal;
        
        if(type==InComeDetailType.RECHARGE.value()){
        	msg.what = Constants.GET_ACC_INCOME_TOTAL_OF_RECHARGE;
        }else if(type==InComeDetailType.WITHDRAW.value()){
        	msg.what = Constants.GET_ACC_INCOME_TOTAL_OF_WITHDRAW;
        }else if(type==InComeDetailType.TENDER.value()){
        	msg.what = Constants.GET_ACC_INCOME_TOTAL_OF_INVEST;
        }else if(type==InComeDetailType.REPAY.value()){
        	msg.what = Constants.GET_ACC_INCOME_TOTAL_OF_REPAY;
        }else if(type==InComeDetailType.REWARD.value()){
        	msg.what = Constants.GET_ACC_INCOME_TOTAL_OF_BONUS;
        }else if(type==InComeDetailType.FEE.value()){
        	msg.what = Constants.GET_ACC_INCOME_TOTAL_OF_EXPENDITURE;
        }
        handler.sendMessage(msg); 
    }  
}
