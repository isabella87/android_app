
package com.banhuitong.activity;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.cookie.Cookie;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.banhuitong.async.CheckVersionTask;
import com.banhuitong.async.DefaultThreadPool;
import com.banhuitong.async.LoginTask;
import com.banhuitong.async.LogoutTask;
import com.banhuitong.http.RequestService;
import com.banhuitong.inf.MyDialogInterface;
import com.banhuitong.item.ApkInfoItem;
import com.banhuitong.util.Constants;
import com.banhuitong.util.DataCleanManager;
import com.banhuitong.util.DownLoadManager;
import com.banhuitong.util.HttpUtils;
import com.banhuitong.util.Urls;
import com.banhuitong.util.ViewUtils;

public class LoginActivity extends BaseActivity {
	
	private WebView wvEntDetails;
	private Intent lastIntent;
	public static final String TAG = "IndexActivity";
	public Boolean backControl = false;
	public Boolean backControls = false;
	public Boolean closeApp = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		init();
		setListener();
	}
	
	@Override
	protected void onStart() {
		super.onStart();

		if (!mApp.isStartup) {
			DefaultThreadPool.getInstance().execute(
					new CheckVersionTask(handler));
			mApp.isStartup = true;
		}
		if(!isRestart){
			toInvest();
		}	
		sdSaveIcLauncher();
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
		wvEntDetails = (WebView) findViewById(R.id.login_ban);
		
		WebSettings webSettings = wvEntDetails .getSettings();       
		webSettings.setUseWideViewPort(true);//设置此属性，可任意比例缩放
		webSettings.setLoadWithOverviewMode(true);
		webSettings.setJavaScriptEnabled(true);  
		
		class JsObject {  
			@JavascriptInterface
	        public void gesture(){  
				Intent intent = new Intent(LoginActivity.this,
						SettingsActivity.class);
				startActivity(intent);
	        } 
			
			 @JavascriptInterface
		        public void logins(final String username,final String password){  
				 handler.post(new Runnable() {  
		                public void run() {  
		                	LoginActivity.this.setResult(Constants.RESULT_INVEST_SUCCESS, lastIntent);
							//LoginActivity.this.finish();
		                	//System.out.println("+++"+username+"+++"+password);
		                	loading.setVisibility(View.VISIBLE);
							DefaultThreadPool.getInstance().execute(
									new LoginTask(handler, username, password));
		                }  
		            });  
		        } 
			 @JavascriptInterface
		        public String getVersionName(){  
					try {
						PackageManager packageManager = getPackageManager();
						PackageInfo packInfo;
						packInfo = packageManager.getPackageInfo(getPackageName(),0);
						return packInfo.versionName;
					} catch (NameNotFoundException e) {
						e.printStackTrace();
					}
					return null;
		        } 
			 @JavascriptInterface
		        public void showShares(){  
				 handler.post(new Runnable() {  
		                public void run() { 
		                	loading.setVisibility(View.VISIBLE);
		                	showShare();
		                }  
		            });  
		        } 
			 @JavascriptInterface
			 public void logouts(){
				 handler.post(new Runnable() {  
		                public void run() { 
		                	
		                		loading.setVisibility(View.VISIBLE);
								DefaultThreadPool.getInstance().execute(new LogoutTask(handler));
		                }
		            });
			 }
			 
	     }  
	    wvEntDetails.addJavascriptInterface(new JsObject(), "myLogin");  
	    
		webSettings.setDefaultTextEncodingName("utf-8");
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
	}
	
	private void setListener() {
		wvEntDetails.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {  
				if(url.contains("app/mobile/settingsN")||url.contains("pinset/jump2CP")){
					backControl = true;
				}else{
					backControl = false;
				}
				if(url.contains("app/mobile/withdrawN")||url.contains("withhold/jump2CP")){
					backControls = true;
				}else{
					backControls = false;
				}
				if(url.contains("index.html")){
					closeApp = true;
				}else{
					closeApp = false;
				}
				if(url.contains("weixin.qq")){
					return false;  
				}else if(url.contains(Constants.url)){
				Cookie sessionCookie = RequestService.extCookie;
				String cookieString = "";
				if (sessionCookie != null) {  
					cookieString = sessionCookie.getName() + "=" + sessionCookie.getValue() 
				       	+ "; domain=" + sessionCookie.getDomain() 
				       	+ "; path=" + sessionCookie.getPath();  
					Log.d("---- cookie-----",cookieString);  
				}
				HttpUtils.synCookies(LoginActivity.this.getApplicationContext(), url, cookieString, isLogin());
				view.loadUrl(url);
				}else{
					return false;  
					//view.loadUrl(url);
				}
				return false;       
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
            	if(backControl){
            		Cookie sessionCookie = RequestService.extCookie;
            		String url = Constants.mobileUrl + "account/jxpay-info.html";
            		String cookieString = "";
            		if (sessionCookie != null) {  
            	       cookieString = sessionCookie.getName() + "=" + sessionCookie.getValue() 
            	        		+ "; domain=" + sessionCookie.getDomain() 
            	        		+ "; path=" + sessionCookie.getPath();  
            	        Log.d("---- cookie-----",cookieString);  
            	     }  
            		HttpUtils.synCookies(LoginActivity.this.getApplicationContext(), url, cookieString, isLogin());
            		wvEntDetails.loadUrl(url);
            	}else if(backControls){
            		Cookie sessionCookie = RequestService.extCookie;
            		String url = Constants.mobileUrl + "account/withdraw.html";
            		String cookieString = "";
            		if (sessionCookie != null) {  
            	       cookieString = sessionCookie.getName() + "=" + sessionCookie.getValue() 
            	        		+ "; domain=" + sessionCookie.getDomain() 
            	        		+ "; path=" + sessionCookie.getPath();  
            	        Log.d("---- cookie-----",cookieString);  
            	     }  
            		HttpUtils.synCookies(LoginActivity.this.getApplicationContext(), url, cookieString, isLogin());
            		wvEntDetails.loadUrl(url);
            	}else if(closeApp){
            		android.os.Process.killProcess(android.os.Process.myPid()); 
            	}else{
            		wvEntDetails.goBack();//返回上一页面
            	}
                return true;
                }
            else
            {
            	LoginActivity.this.finish();
            	return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
		
	 
	 
	private void toInvest(){
		if(isLogin()){
		Cookie sessionCookie = RequestService.extCookie;
		String url = Constants.mobileUrl + "account/accountment.html";
		String cookieString = "";
		if (sessionCookie != null) {  
	       cookieString = sessionCookie.getName() + "=" + sessionCookie.getValue() 
	        		+ "; domain=" + sessionCookie.getDomain() 
	        		+ "; path=" + sessionCookie.getPath();  
	        Log.d("---- cookie-----",cookieString);  
	     }  
		HttpUtils.synCookies(LoginActivity.this.getApplicationContext(), url, cookieString, isLogin());
		wvEntDetails.loadUrl(url);
		}else{
		String url = Constants.mobileUrl + "login.html";
		HttpUtils.synCookies(this.getApplicationContext(), url, "", false);
		wvEntDetails.loadUrl(url);
		}
	}
	
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			loading.setVisibility(View.GONE);
			Intent intent = new Intent();
			switch (msg.what) {
			case Constants.UPDATE_NONEED:
				Toast.makeText(getApplicationContext(), "不需要更新",
						Toast.LENGTH_SHORT).show();
			case Constants.UPDATE_CLIENT:

				try {
					localVersion = mApp.getVersionName();
					ApkInfoItem apk = (ApkInfoItem) msg.obj;

					if (apk.getVersion() != null
							&& equalsVersion(localVersion,apk.getVersion())) {
						Log.i(TAG, "版本号不相同 ");
						apkPath = apk.getApkPath();

						// 对话框通知用户升级程序
						showUpdateDialog();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case Constants.GET_UPDATEINFO_ERROR:
				// 服务器超时
				Toast.makeText(getApplicationContext(), "获取服务器更新信息失败", 1)
						.show();
				break;
			case Constants.DOWN_ERROR:
				// 下载apk失败
				Toast.makeText(getApplicationContext(), "下载新版本失败", 1).show();
				break;
			case Constants.NETWORK_ERROR:
				Toast.makeText(getApplicationContext(), "网络异常！", 1).show();
				break;
			case Constants.LOGIN_SUCCESS:
				Toast.makeText(getApplicationContext(), "登录成功。", 1).show();
				Map<String, Object> map = (Map<String, Object>) msg.obj;
				mApp.setSavedPassword(map.get(Constants.USER_PASS).toString());
				mApp.setSavedUsername(map.get(Constants.USER_NAME).toString());
				login();

				
				intent.setAction(Constants.ACTION_LOG_IN);
				sendBroadcast(intent);
				
				
				
				Cookie sessionCookie = RequestService.extCookie;
				String url = Constants.mobileUrl + "account/accountment.html";
				String cookieString = "";
				if (sessionCookie != null) {  
			       cookieString = sessionCookie.getName() + "=" + sessionCookie.getValue() 
			        		+ "; domain=" + sessionCookie.getDomain() 
			        		+ "; path=" + sessionCookie.getPath();  
			        Log.d("---- cookie-----",cookieString);  
			     }  
				HttpUtils.synCookies(LoginActivity.this.getApplicationContext(), url, cookieString, isLogin());
				wvEntDetails.loadUrl(url);
				break;
			case Constants.LOGIN_FAILED:
				break;
			case Constants.LOGOUT_SUCCESS:
                intent.setAction(Constants.ACTION_LOG_OUT);  
                sendBroadcast(intent);  
                
                SystemClock.sleep(2000);
                loading.setVisibility(View.GONE);
                logout();
                toInvest();
				break;
			default:
			}
		}
	};
	
	protected void showUpdateDialog() {
		ViewUtils.showDialog("", "", "提示", "发现新版本，确认更新？", LoginActivity.this,
				ViewUtils.Button_type_all, new MyDialogInterface() {
					@Override
					public void onButtonSure() {
						Log.i(TAG, "下载apk,更新");
						downLoadApk();
					}

					@Override
					public void onButtonCancel() {
					}
				});
	}
	/**
	 * 
	 *版本比较
	 * @param localVersion
	 * @param version
	 * @return
	 * @throws Exception
	 */
	protected boolean equalsVersion(String localVersion, String version) throws Exception {
		String[] lv = localVersion.split(".");
		String[] v = version.split(".");
		int lv0 = Integer.parseInt(lv[0]);
		int lv1 = Integer.parseInt(lv[1]);
		int lv2 = Integer.parseInt(lv[2]);
		int v0 = Integer.parseInt(v[0]);
		int v1 = Integer.parseInt(v[1]);
		int v2 = Integer.parseInt(v[2]);
		if(v0>lv0){
			return true;
		}else if(v1>lv1&&v0==lv0){
			return true;
		}else if(v2>lv2&&v0==lv0&&v1==lv1){
			return true;
		}
		return false;
	}
	/*
	 * 　　* 从服务器中下载APK 　　
	 */
	protected void downLoadApk() {
		final ProgressDialog pd; // 进度条对话框
		pd = new ProgressDialog(this);
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.setCanceledOnTouchOutside(false);
		pd.setMessage("正在下载更新");
		pd.show();
		DefaultThreadPool.getInstance().execute(new Thread() {
			@Override
			public void run() {
				try {
					File file = DownLoadManager.getFileFromServer(apkPath, pd,
							LoginActivity.this);
					installApk(file);
					pd.dismiss(); // 结束掉进度条对话框
				} catch (Exception e) {
					Message msg = new Message();
					msg.what = Constants.DOWN_ERROR;
					handler.sendMessage(msg);
					e.printStackTrace();
				}
			}
		});
	}

	// 安装apk
	protected void installApk(File file) {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setDataAndType(Uri.fromFile(file),
				"application/vnd.android.package-archive");
		startActivity(intent);
		android.os.Process.killProcess(android.os.Process.myPid());
	}

}
