package com.banhuitong.activity;

import org.apache.http.cookie.Cookie;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.banhuitong.http.RequestService;
import com.banhuitong.util.Constants;
import com.banhuitong.util.HttpUtils;
import com.banhuitong.util.ViewUtils;

@SuppressLint("NewApi")
public class ShowWebView2Activity extends BaseActivity {

	private WebView wvMain;
	private String url = "";
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

		if (!isRestart) {
			loading.setVisibility(View.VISIBLE);
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
		url = lastIntent.getStringExtra("url");

		wvMain = (WebView) findViewById(R.id.wv_main);

		wvMain.removeJavascriptInterface("searchBoxJavaBridge_");

		WebSettings webSettings = wvMain.getSettings();
		webSettings.setUseWideViewPort(true);// 设置此属性，可任意比例缩放
		webSettings.setLoadWithOverviewMode(true);
		webSettings.setJavaScriptEnabled(true);
		webSettings.setDefaultTextEncodingName("utf-8");
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
	}

	private void setListener() {

		wvMain.setWebViewClient(new WebViewClient() {

			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				if (url.contains("login.html")) {
					ShowWebView2Activity.this.setResult(
							Constants.RESULT_TO_LOGIN, lastIntent);
					ShowWebView2Activity.this.finish();
				} else if (url.contains("index.html")) {
					ShowWebView2Activity.this.finish();
				} else if (url.contains("jxpay-info.html")) {
					if(url.indexOf("?")==-1){
						ShowWebView2Activity.this.setResult(Constants.RESULT_INVEST_SUCCESS, lastIntent);
						ShowWebView2Activity.this.finish();
					}else{
						view.loadUrl(url);
					}
				}else {
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
				loading.setVisibility(View.GONE);
				wvMain.removeJavascriptInterface("accessibility");
				wvMain.removeJavascriptInterface("accessibilityTraversal");
			}

			@Override
			public void onLoadResource(WebView view, String url) {
				super.onLoadResource(view, url);
			}

			// public void onReceivedSslError(WebView view, SslErrorHandler
			// handler, SslError error) {
			// // handler.cancel(); //默认的处理方式，WebView变成空白页
			// handler.proceed(); // 接受证书
			// }
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (wvMain.canGoBack()) {
				wvMain.goBack();// 返回上一页面
				return true;
			} else {
				ShowWebView2Activity.this.setResult(
						Constants.RESULT_SHOW_MSG_SUCCESS, lastIntent);
				ShowWebView2Activity.this.finish();
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	private void toInvest() {
		Cookie sessionCookie = RequestService.extCookie;
		String cookieString = "";
		if (sessionCookie != null) {
			cookieString = sessionCookie.getName() + "="
					+ sessionCookie.getValue() + "; domain="
					+ sessionCookie.getDomain() + "; path="
					+ sessionCookie.getPath();
			Log.d("---- cookie-----", cookieString);
		}
		HttpUtils.synCookies(this.getApplicationContext(), url, cookieString,
				isLogin());
		wvMain.loadUrl(url);
	}

}
