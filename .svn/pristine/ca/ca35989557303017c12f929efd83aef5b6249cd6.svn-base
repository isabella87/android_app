package com.banhuitong.activity;

import org.apache.http.cookie.Cookie;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import com.banhuitong.http.RequestService;
import com.banhuitong.util.Constants;
import com.banhuitong.util.HttpUtils;

public class RepayNoticeActivity extends BaseActivity {
	
	private ImageView imgBack;
	private WebView wvMain;
	boolean isLogin;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_webview);
		init();
		setListener();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		isLogin = isLogin();
		show();
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}

	@SuppressLint("SetJavaScriptEnabled")
	private void init() {
		
		wvMain = (WebView) findViewById(R.id.wv_main);
		imgBack = (ImageView) findViewById(R.id.back);
		
		TextView tvTitle = (TextView)findViewById(R.id.tv_title);
		tvTitle.setText("还款公告");
		
		WebSettings webSettings = wvMain .getSettings();       
		webSettings.setUseWideViewPort(true);//设置此属性，可任意比例缩放
		webSettings.setLoadWithOverviewMode(true);
		webSettings.setJavaScriptEnabled(true);  
		webSettings.setDefaultTextEncodingName("utf-8");
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
	}
	
	private void setListener() {
		
		imgBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				RepayNoticeActivity.this.finish();
			}
		});
		
		wvMain.setWebViewClient(new WebViewClient() {
			
			public boolean shouldOverrideUrlLoading(WebView view, String url) {       
				view.loadUrl(url);
	            return true;       
	         }     
			
			@Override
			public void onPageFinished(WebView view, String url) { 
				super.onPageFinished(view, url); 
			} 
		   
		   @Override
		   public void onPageStarted(WebView view, String url, Bitmap favicon) { 
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
            if(wvMain.canGoBack())
            {
            	wvMain.goBack();//返回上一页面
                return true;
            }
            else
            {
            	RepayNoticeActivity.this.finish();
            	return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

	private void show(){
		Cookie sessionCookie = RequestService.extCookie;
		String url = Constants.mobileUrl + "app/info-list.html?type=1&p=1";
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
