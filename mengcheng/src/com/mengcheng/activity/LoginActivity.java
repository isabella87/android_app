
package com.mengcheng.activity;

import java.io.File;

import android.app.ProgressDialog;
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
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.mengcheng.activity.R;
import com.handmark.pulltorefresh.library.PullToRefreshWebView;
import com.mengcheng.async.CheckVersionTask;
import com.mengcheng.async.DefaultThreadPool;
import com.mengcheng.fragment.FooterFragment;
import com.mengcheng.inf.MyDialogInterface;
import com.mengcheng.item.ApkInfoItem;
import com.mengcheng.util.Constants;
import com.mengcheng.util.DownLoadManager;
import com.mengcheng.util.ViewUtils;

public class LoginActivity extends BaseActivity {
	
	private WebView wvEntDetails;
	private Intent lastIntent;
	public static final String TAG = "IndexActivity";
	public Boolean backControl = false;
	public Boolean backControls = false;
	public Boolean closeApp = false;
	public PullToRefreshWebView mPullRefreshWebView;
	private FooterFragment vFooter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		vFooter = (FooterFragment) getFragmentManager().findFragmentById(
				R.id.id_fragment_footer);
		
		mPullRefreshWebView = (PullToRefreshWebView) findViewById(R.id.login_ban);
		wvEntDetails = mPullRefreshWebView.getRefreshableView();
		
		init();
		setListener();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
		CookieSyncManager.createInstance(this);
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
	
	/* (non-Javadoc)
	 * @see com.banhuitong.activity.BaseActivity#onRestart()
	 */
	@Override
	protected void onRestart() {
		super.onRestart();
	}
	
	/* (non-Javadoc)
	 * @see com.banhuitong.activity.BaseActivity#onPause()
	 */
	@Override
	protected void onPause() {
		super.onPause();
		 CookieSyncManager.getInstance().stopSync();
	}
	
	/* (non-Javadoc)
	 * @see com.banhuitong.activity.BaseActivity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();
		
	 	CookieSyncManager.getInstance().startSync();
	}

	private void init() {
		loading = ViewUtils.initRotateAnimation(this);
		
		lastIntent = getIntent();
		
		WebSettings webSettings = wvEntDetails .getSettings();       
		webSettings.setUseWideViewPort(true);//设置此属性，可任意比例缩放
		webSettings.setLoadWithOverviewMode(true);
		webSettings.setJavaScriptEnabled(true);  
		
		class JsObject {  
			 @JavascriptInterface
		        public void logins(final String username,final String password){  
				 handler.post(new Runnable() {  
		                public void run() {  
//		                	Editor ed = MyApplication.sp.edit();
//		            		ed.putString(Constants.USER_NAME, username);
//		            		ed.putString(Constants.USER_PASS, password);
//		            		ed.commit();
		                	LoginActivity.this.setResult(Constants.RESULT_INVEST_SUCCESS, lastIntent);
		                	loading.setVisibility(View.VISIBLE);
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
		        public void showShares(final String rcode){  
				 handler.post(new Runnable() {  
		                public void run() { 
		                	loading.setVisibility(View.VISIBLE);
		                	share(rcode);
		                }  
		            });  
		        } 
			 @JavascriptInterface
			 public void logouts(){
				 handler.post(new Runnable() {  
		                public void run() { 
//		                	CookieManager.getInstance().removeSessionCookie(); //打开app清除cookie 实现在登录。
//		                	Editor ed = MyApplication.sp.edit();
//		                	ed.putString(Constants.SAVED_COOKIE, "");
//		            	 	ed.putString(Constants.CONTROL_COOKIE, "0");
//		            	 	ed.putString(Constants.USER_NAME, "");
//		            		ed.putString(Constants.USER_PASS, "");
//		            		ed.commit();
//		            		HttpUtils.synCookies(LoginActivity.this.getApplicationContext(), Constants.mobileUrl + "login.html", "", isLogin());
//		                		//loading.setVisibility(View.VISIBLE);
//							//	DefaultThreadPool.getInstance().execute(new LogoutTask(handler));
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
				//底部取消
				vFooter.imgMyAccount
				.setBackgroundResource(R.drawable.footer_myaccount);
				vFooter.imgIndex.setBackgroundResource(R.drawable.footer_home);
				vFooter.imgMain.setBackgroundResource(R.drawable.footer_product);
				vFooter.imgMore.setBackgroundResource(R.drawable.footer_more);
				//底部变色
				if(url.contains("index.html")){
					vFooter.imgIndex
					.setBackgroundResource(R.drawable.footer_home_highlight);
				}else if(url.contains("/worker")||url.contains("/service")){
					vFooter.imgMain
					.setBackgroundResource(R.drawable.footer_product_highlight);
				}else if(url.contains("login")||url.contains("m/account")||url.contains("m/web")){
					vFooter.imgMyAccount
					.setBackgroundResource(R.drawable.footer_myaccount_highlight);
				}else{
					vFooter.imgMore
					.setBackgroundResource(R.drawable.footer_more_highlight);
				}
				//跳转返回
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
				//   首页退出程序判断 
				if(url.contains("index.html")){
					closeApp = true;
				}else{
					closeApp = false;
				}
				if(url.contains("weixin.qq")){
					return false;  
				}else if(url.contains(Constants.url)){

				}else{
					return false;  
				}
				return false;       
	         }     
			
			@Override
			public void onPageFinished(WebView view, String url) { 
				CookieSyncManager.getInstance().sync();  
//				try {
//					if(url.contains("account/accountment.html")&&!"1".equals(MyApplication.sp.getString(Constants.CONTROL_COOKIE, ""))){
//					CookieManager cookieManager = CookieManager.getInstance();
//					String[] cookies = cookieManager.getCookie(url).split("; ");
//					String cookieStr = "";
//					for(int i=0; i<cookies.length;i++){
//						if(cookies[i].contains("__auth__=")){
//							cookieStr = cookieManager.getCookie(url).split("; ")[i]+
//									 "; domain=" + Constants.url
//									+ "; path=/" ;
//							break;
//						}
//					}
//					 Editor ed = MyApplication.sp.edit();
//					 ed.putString(Constants.SAVED_COOKIE, cookieStr);
//					 if(!"".equals(cookieStr)){
//					 ed.putString(Constants.CONTROL_COOKIE, "1");
//					 }
//					 ed.commit();
//					}
//					if(url.contains("login.html")){
//						 Editor ed = MyApplication.sp.edit();
//						 ed.putString(Constants.SAVED_COOKIE, "");
//						 ed.putString(Constants.CONTROL_COOKIE, "0");
//						 ed.putString(Constants.USER_NAME, "");
//		            	 ed.putString(Constants.USER_PASS, "");
//						 ed.commit();
//					}
//				} catch (Exception e) {
//					e.printStackTrace(); 
//				}
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
            		String url = Constants.mobileUrl + "account/jxpay-info.html";
            		 
            		newpath(url);
            		//HttpUtils.synCookies(LoginActivity.this.getApplicationContext(), url, cookieString, isLogin());
            		//wvEntDetails.loadUrl(url);
            	}else if(backControls){
            		String url = Constants.mobileUrl + "account/withdraw.html";
            		newpath(url);
            		//HttpUtils.synCookies(LoginActivity.this.getApplicationContext(), url, cookieString, isLogin());
            	//	wvEntDetails.loadUrl(url);
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
	 
	 /**
	  * 跳转页面
	  * @param url
	  */
	 public void newpath(final String url){
		 if(url.contains("index.html")){
				closeApp = true;
			}else{
				closeApp = false;
			}
		 CookieSyncManager.getInstance().sync();
		// HttpUtils.synCookies(LoginActivity.this.getApplicationContext(), url, cookieString, isLogin());
		wvEntDetails.loadUrl(url);
	 }
	 
	private void toInvest(){
		String url = Constants.mobileUrl + "account/accountment.html";	
		newpath(url);
	}
	
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			loading.setVisibility(View.GONE);
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
				Toast.makeText(getApplicationContext(), "正在加载中", 1)   //获取服务器更新信息失败
						.show();
				break;
			case Constants.DOWN_ERROR:
				// 下载apk失败
				Toast.makeText(getApplicationContext(), "下载新版本失败", 1).show();
				break;
			case Constants.NETWORK_ERROR:
				Toast.makeText(getApplicationContext(), "网络异常！", 1).show();
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
		String[] lv = localVersion.split("\\.");
		String[] v = version.split("\\.");
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