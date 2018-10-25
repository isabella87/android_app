package com.banhuitong.activity;

import org.apache.http.cookie.Cookie;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.banhuitong.http.RequestService;
import com.banhuitong.util.Constants;
import com.banhuitong.util.HttpUtils;
import com.banhuitong.util.ViewUtils;

public class BhbDetailActivity extends BaseActivity {
	
	private WebView wvEntDetails;
	private Handler handler = new Handler();
	private long pId;
	private String hash;
	boolean isLogin;
	private Intent lastIntent;
	private String msg = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ent_detail);
		init();
		setListener();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		if(!isRestart){
			toInvest();
		}
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}

	private void init() {
		loading = ViewUtils.initRotateAnimation(this);
		
		lastIntent = getIntent();
		pId = lastIntent.getLongExtra("pId", 0);
		hash = lastIntent.getStringExtra("hash");
		
		wvEntDetails = (WebView) findViewById(R.id.wv_ent_detail);
//		topbar = (HeaderView) findViewById(R.id.topbar);
		
		WebSettings webSettings = wvEntDetails .getSettings();       
		webSettings.setUseWideViewPort(true);//设置此属性，可任意比例缩放
		webSettings.setLoadWithOverviewMode(true);
		webSettings.setJavaScriptEnabled(true);  
		
		class JsObject {  
			 @JavascriptInterface
		        public void init(int f){
				 	handler.post(new Runnable() {  
		                public void run() {  
		                	BhbDetailActivity.this.setResult(Constants.RESULT_INVEST_SUCCESS, lastIntent);
		                	BhbDetailActivity.this.finish();
		                }  
		            });  
		        }  
			 
			 @JavascriptInterface
		        public void jumpError(String pMsg){  
				    msg = pMsg;
				    
				    handler.post(new Runnable() {  
		                public void run() {  
		                	if(msg.contains("余额不足")){
		                		ViewUtils.showDialog("", "", "提示", getResources().getString(R.string.msg_balance_not_enough), BhbDetailActivity.this, ViewUtils.Button_type_sure, null);
		                	}else{
		                		ViewUtils.showDialog("", "", "提示", msg, BhbDetailActivity.this, ViewUtils.Button_type_sure, null);
		                	}
		                	
		                	wvEntDetails.goBack();
		                }  
		            });  
		        }  
	     }  
	    wvEntDetails.addJavascriptInterface(new JsObject(), "myObject");  
	    
		webSettings.setDefaultTextEncodingName("utf-8");
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
	}
	
	private void setListener() {
		wvEntDetails.setWebViewClient(new WebViewClient() {
			
			public boolean shouldOverrideUrlLoading(WebView view, String url) {       
				if(url.contains("login.html")){
					BhbDetailActivity.this.setResult(Constants.RESULT_TO_LOGIN, lastIntent);
					BhbDetailActivity.this.finish();
				}else if(url.contains("back.html")){
					BhbDetailActivity.this.finish();
				}else{
					view.loadUrl(url);
				}
	            return true;       
	         }     
			
			@Override
			public void onPageFinished(WebView view, String url) { 
				loading.setVisibility(View.GONE);
				super.onPageFinished(view, url); 
			} 
		   
		   @Override
		   public void onPageStarted(WebView view, String url, Bitmap favicon) { 
			   loading.setVisibility(View.VISIBLE);
			   super.onPageStarted(view, url, favicon); 
		   } 
		   
		   @Override
		   public void onLoadResource(WebView view, String url) { 
			   super.onLoadResource(view, url); 
		   } 
			
			public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//				handler.cancel(); //默认的处理方式，WebView变成空白页
				handler.proceed(); // 接受证书
			}
        });
	}
	
	 @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {
            if(wvEntDetails.canGoBack())
            {
            	wvEntDetails.goBack();//返回上一页面
                return true;
            }
            else
            {
            	BhbDetailActivity.this.finish();
            	return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

	private void toInvest(){
		Cookie sessionCookie = RequestService.extCookie;
		int x = (int) Math.random();
		String url = Constants.mobileUrl + "app/bhb-view.html?id=" + pId + "&hash=" + hash + "&random=" + x;

		String cookieString = "";
		if (sessionCookie != null) {  
	       cookieString = sessionCookie.getName() + "=" + sessionCookie.getValue() 
	        		+ "; domain=" + sessionCookie.getDomain() 
	        		+ "; path=" + sessionCookie.getPath();  
	        Log.d("---- cookie-----",cookieString);  
	     }  
		HttpUtils.synCookies(this.getApplicationContext(), url, cookieString, isLogin());
		wvEntDetails.loadUrl(url);
	}
	
}