package com.banhuitong.activity;

import org.apache.http.cookie.Cookie;

import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.banhuitong.activity.R;
import com.banhuitong.http.RequestService;
import com.banhuitong.util.ActivityUtils;
import com.banhuitong.util.Constants;
import com.banhuitong.util.HttpUtils;

public class WithdrawActivity extends BaseActivity {
	
	WebView wvWithdraw;
	private Handler handler = new Handler();
	private Intent lastIntent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_webview2);
		init();
		setListener();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		if(!isRestart){
			toWithdraw();
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
		wvWithdraw = (WebView) findViewById(R.id.wv_main);
		lastIntent = getIntent();
		
		WebSettings webSettings = wvWithdraw .getSettings();       
		webSettings.setUseWideViewPort(true);//设置此属性，可任意比例缩放
		webSettings.setLoadWithOverviewMode(true);
		webSettings.setJavaScriptEnabled(true);  
		
		class JsObject {  
			 @JavascriptInterface
		        public void init(int f){  
		            handler.post(new Runnable() {  
		                public void run() {  
		                	WithdrawActivity.this.setResult(Constants.RESULT_WITHDRAW_SUCCESS, lastIntent);
		                	WithdrawActivity.this.finish();
		                }  
		            });  
		        }  
	     }  
		wvWithdraw.addJavascriptInterface(new JsObject(), "myObject");  
	    
		webSettings.setDefaultTextEncodingName("utf-8");
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
	}
	
	private void setListener() {
		wvWithdraw.setWebViewClient(new WebViewClient() {
			
			public boolean shouldOverrideUrlLoading(WebView view, String url) {       
				if(url.contains("login.html")){
					WithdrawActivity.this.setResult(Constants.RESULT_TO_LOGIN, lastIntent);
					WithdrawActivity.this.finish();
				}else if(url.contains("back.html")){
					WithdrawActivity.this.finish();
				}else{
					view.loadUrl(url);
				}
	            return true;       
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
            if(wvWithdraw.canGoBack())
            {
            	wvWithdraw.goBack();//返回上一页面
                return true;
            }
            else
            {
            	WithdrawActivity.this.finish();
            	return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

	private void toWithdraw(){
		Cookie sessionCookie = RequestService.extCookie;
		String url = Constants.mobileUrl + "app/to-withdraw.html";
		String cookieString = "";
		if (sessionCookie != null) {  
	       cookieString = sessionCookie.getName() + "=" + sessionCookie.getValue() 
	        		+ "; domain=" + sessionCookie.getDomain() 
	        		+ "; path=" + sessionCookie.getPath();  
	        Log.d("---- cookie-----",cookieString);  
	     }  
		HttpUtils.synCookies(this.getApplicationContext(), url, cookieString, isLogin());
		wvWithdraw.loadUrl(url);
	}

}
