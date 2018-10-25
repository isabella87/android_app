package com.banhuitong.activity;

import org.apache.http.cookie.Cookie;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
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
import com.banhuitong.http.RequestService;
import com.banhuitong.util.ActivityUtils;
import com.banhuitong.util.Constants;
import com.banhuitong.util.HttpUtils;
import com.banhuitong.util.ViewUtils;

@SuppressLint("NewApi")
public class ChinaPayActivity extends BaseActivity {
	
	private WebView wvMain;
	private Handler handler = new Handler();
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
		lastIntent = getIntent();
		
		wvMain = (WebView) findViewById(R.id.wv_ent_detail);
		wvMain.removeJavascriptInterface("searchBoxJavaBridge_");
		
		WebSettings webSettings = wvMain .getSettings();       
		webSettings.setUseWideViewPort(true);//设置此属性，可任意比例缩放
		webSettings.setLoadWithOverviewMode(true);
		webSettings.setJavaScriptEnabled(true);  
		webSettings.setBuiltInZoomControls(true);
		
		class JsObject {  
			 @JavascriptInterface
		        public void init(int f){  
		            handler.post(new Runnable() {  
		                public void run() {  
		                	ChinaPayActivity.this.setResult(Constants.RESULT_INVEST_SUCCESS, lastIntent);
		                	ChinaPayActivity.this.finish();
		                }  
		            });  
		        }  
			 
			 @JavascriptInterface
		        public void jumpError(String pMsg){  
				    msg = pMsg;
				    
		            handler.post(new Runnable() {  
		                public void run() {  
		                	ViewUtils.showDialog("", "", "提示", msg, ChinaPayActivity.this, ViewUtils.Button_type_sure, null);
		                	wvMain.goBack();
		                }  
		            });  
		        }  
	     }  
		wvMain.addJavascriptInterface(new JsObject(), "myObject");  
	    
		webSettings.setDefaultTextEncodingName("utf-8");
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
	}
	
	private void setListener() {
		
		wvMain.setWebViewClient(new WebViewClient() {
			
			public boolean shouldOverrideUrlLoading(WebView view, String url) {       
				if(url.contains("login.html")){
					ChinaPayActivity.this.setResult(Constants.RESULT_TO_LOGIN, lastIntent);
					ChinaPayActivity.this.finish();
				}else if(url.contains("back.html")){
					ChinaPayActivity.this.finish();
				}else{
					view.loadUrl(url);
				}
	            return true;       
	         }     
			
			@Override
			public void onPageFinished(WebView view, String url) { 
				super.onPageFinished(view, url); 
			} 
		   
		   @Override
		   public void onPageStarted(WebView view, String url, Bitmap favicon) { 
			   super.onPageStarted(view, url, favicon); 
			   wvMain.removeJavascriptInterface("accessibility");
			   wvMain.removeJavascriptInterface("accessibilityTraversal");
		   } 
		   
		   @Override
		   public void onLoadResource(WebView view, String url) { 
			   super.onLoadResource(view, url); 
		   } 
			
//			public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
////				handler.cancel(); //默认的处理方式，WebView变成空白页
//				handler.proceed(); // 接受证书
//			}
        });
	}
	
	 @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {
            if(wvMain.canGoBack())
            {
            	wvMain.goBack();//返回上一页面
                return true;
            }
            else
            {
            	ChinaPayActivity.this.finish();
            	return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

	private void toInvest(){
		Cookie sessionCookie = RequestService.extCookie;
		String url = Constants.mobileUrl + "app/chinapay-info.html";
		String cookieString = "";
		if (sessionCookie != null) {  
	       cookieString = sessionCookie.getName() + "=" + sessionCookie.getValue() 
	        		+ "; domain=" + sessionCookie.getDomain() 
	        		+ "; path=" + sessionCookie.getPath();  
	        Log.d("---- cookie-----",cookieString);  
	     }  
		HttpUtils.synCookies(this.getApplicationContext(), url, cookieString, isLogin());
		wvMain.loadUrl(url);
	}
	
}
