package com.banhuitong.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.banhuitong.async.DefaultThreadPool;
import com.banhuitong.async.GetCryptMobileTask;
import com.banhuitong.http.RequestService;
import com.banhuitong.receiver.SystemReceiver;
import com.banhuitong.util.Constants;
import com.banhuitong.util.DownLoadManager;
import com.banhuitong.view.HeaderView;
import com.iflytek.sunflower.FlowerCollector;

public abstract class BaseFragmentActivity extends FragmentActivity {

	protected String localVersion;
	protected String apkPath="";
	protected MyApplication mApp;
	protected LayoutInflater mInflater;
	protected BroadcastReceiver systemReceiver;
	protected HeaderView topbar;
	protected boolean isRestart = false;
	protected View loading;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mApp = (MyApplication) getApplication();
		mInflater = (LayoutInflater) mApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		systemReceiver = new SystemReceiver(null);
		
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		MyApplication.screen_w = metric.widthPixels;  // 屏幕宽度（像素）
		MyApplication.screen_h = metric.heightPixels;  // 屏幕高度（像素）
	}

	@Override
	protected void onStart() {
		super.onStart();
		mApp.setSavedUsername(MyApplication.sp.getString(Constants.USER_NAME, ""));
		mApp.setSavedPassword(MyApplication.sp.getString(Constants.USER_PASS, ""));
		
		IntentFilter filter = new IntentFilter();  
        filter.addAction(Constants.ACTION_LOG_OUT);  
        filter.addAction(Constants.ACTION_LOG_IN); 
        registerReceiver(systemReceiver, filter);  
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		isRestart = true;
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		FlowerCollector.onResume(this);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		FlowerCollector.onPause(this);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(systemReceiver);
	}
	
    @Override  
    public Resources getResources() {  
        Resources res = super.getResources();    
        Configuration config=new Configuration();    
        config.setToDefaults();    
        res.updateConfiguration(config,res.getDisplayMetrics() );  
        return res;  
    }  

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return super.onKeyDown(keyCode, event);
	}
	
	public void login() {
		Editor ed = MyApplication.sp.edit();
		ed.putString(Constants.USER_NAME, mApp.getSavedUsername());
		ed.putString(Constants.USER_PASS, mApp.getSavedPassword());
		ed.commit();
	}
	
	public void logout() {
		Editor ed = MyApplication.sp.edit();
		ed.putString(Constants.USER_PASS, "");
		ed.putString(Constants.USER_NAME, "");
		ed.commit();
		RequestService.extCookie = null;
	}
	
	public boolean isLogin(){
		return (!"".equals(MyApplication.sp.getString(Constants.USER_NAME, "")))&&(!"".equals(MyApplication.sp.getString(Constants.USER_PASS, "")));
	}
	
	public void sdSaveIcLauncher(){	
		try {
			InputStream is = getClass().getResourceAsStream("/res/drawable/ic_launcher2.png");
			FileOutputStream fos = new FileOutputStream(new File(Environment.getExternalStorageDirectory().getPath() + "/ic_launcher2.png"));
			DownLoadManager.writeFile(is, fos);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected void showShare() {
		DefaultThreadPool.getInstance().execute(new GetCryptMobileTask(baseHandler));
	}
	
	private void showShare(String rcode) {
		 ShareSDK.initSDK(this);
		 OnekeyShare oks = new OnekeyShare();
		 //关闭sso授权
		 oks.disableSSOWhenAuthorize(); 

		// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
		 //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
		 // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		 oks.setTitle(getString( R.string.share_name));
		 // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		 oks.setTitleUrl(Constants.mobileUrl + "share.html?rcode=" + rcode);
		 // text是分享文本，所有平台都需要这个字段
		 oks.setText("建筑业金融信息服务专家");
		 // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		 oks.setImagePath(Environment.getExternalStorageDirectory().getAbsolutePath() + "/ic_launcher2.png");//确保SDcard下面存在此张图片
		 // url仅在微信（包括好友和朋友圈）中使用
		 oks.setUrl(Constants.mobileUrl + "share.html?rcode=" + rcode);
		 // comment是我对这条分享的评论，仅在人人网和QQ空间使用
		 oks.setComment("我是测试评论文本");
		 // site是分享此内容的网站名称，仅在QQ空间使用
		 oks.setSite(getString(R.string.app_name));
		 // siteUrl是分享此内容的网站地址，仅在QQ空间使用
		 oks.setSiteUrl(Constants.mobileUrl + "share.html");
		 oks.setSilent(true);

		// 启动分享GUI
		 oks.show(this);
	}
	
	Handler baseHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			switch (msg.what) {
			case Constants.GET_CRYPT_MOBILE_SUCCESS:
				Map<String,String> map = (Map<String, String>) msg.obj;
				String rcode = map.get("rcode");
				String mobile = map.get("mobile");
				showShare(rcode);
				break;
			case Constants.NETWORK_ERROR:
				Toast.makeText(getApplicationContext(), "网络异常！", 1).show();
				break;
			default:
			}
		}
	};
	
}
