package com.banhuitong.activity;

import java.io.File;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.banhuitong.async.CheckVersionTask;
import com.banhuitong.async.DefaultThreadPool;
import com.banhuitong.inf.MyDialogInterface;
import com.banhuitong.item.ApkInfoItem;
import com.banhuitong.util.Constants;
import com.banhuitong.util.DownLoadManager;
import com.banhuitong.util.ViewUtils;
import com.banhuitong.view.AutoScrollTextView;

public class IndexActivity extends BaseFragmentActivity {
	
	public static final String TAG = "IndexActivity";  
	
	boolean isSencondBackDown;
	private ScrollView mScrollView;
	private LinearLayout llGong,llBao,llZhuan,llBorrow,llInfo,llStrategy;
	private AutoScrollTextView autoScrollTextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index);
		init();
		setListener();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
		if(!mApp.isStartup){
			DefaultThreadPool.getInstance().execute(new CheckVersionTask(handler));
			mApp.isStartup = true;
		}
		
		sdSaveIcLauncher();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	private void init() {
		mScrollView = (ScrollView) findViewById(R.id.scroll_view);
		
		llGong = (LinearLayout) findViewById(R.id.ll_gong);
		llBao = (LinearLayout) findViewById(R.id.ll_bao);
		llZhuan = (LinearLayout) findViewById(R.id.ll_zhuan);
		llBorrow = (LinearLayout) findViewById(R.id.ll_borrow);
		llInfo = (LinearLayout) findViewById(R.id.ll_info);
		llStrategy = (LinearLayout) findViewById(R.id.ll_strategy);
		
		int screenWidth = MyApplication.screen_w;
		int screenHeight = MyApplication.screen_h;
		
		llGong.setMinimumWidth(screenWidth/2);
		llBao.setMinimumWidth(screenWidth/2);
		llZhuan.setMinimumWidth(screenWidth/2);
		llBorrow.setMinimumWidth(screenWidth/2);
		llInfo.setMinimumWidth(screenWidth/2);
		llStrategy.setMinimumWidth(screenWidth/2);
		
		llGong.setMinimumHeight((int) (screenHeight/2.5));
		llBao.setMinimumHeight((int) (screenHeight/2.5));
		llZhuan.setMinimumHeight((int) (screenHeight/2.5));
		llBorrow.setMinimumHeight((int) (screenHeight/2.5));
		llInfo.setMinimumHeight((int) (screenHeight/2.5));
		llStrategy.setMinimumHeight((int) (screenHeight/2.5));
		
//		autoScrollTextView = (AutoScrollTextView)findViewById(R.id.TextViewNotice);
//        autoScrollTextView.init(getWindowManager());
//        autoScrollTextView.startScroll();
	}
	
	private void setListener() {
		
		llGong.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!isLogin()){
        			Intent intent = new Intent(IndexActivity.this, LoginActivity.class);
    				startActivity(intent);
    				((MainActivity3)getParent()).pager.setCurrentItem(0);
    				return;
        		}
				((MainActivity3)getParent()).pager.setCurrentItem(1);
				((MainActivity3)getParent()).loadCurActivity(1, 1, true);
			}
		});
		
		llBao.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				((MainActivity3)getParent()).pager.setCurrentItem(1);
//				((MainActivity3)getParent()).loadCurActivity(1, 2, true);
				showShare();
			}
		});
		
		llZhuan.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!isLogin()){
        			Intent intent = new Intent(IndexActivity.this, LoginActivity.class);
    				startActivity(intent);
    				((MainActivity3)getParent()).pager.setCurrentItem(0);
    				return;
        		}
				((MainActivity3)getParent()).pager.setCurrentItem(1);
				((MainActivity3)getParent()).loadCurActivity(1, 3, true);
			}
		});
		
		llInfo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(IndexActivity.this, IndexAffairsActivity.class);
				startActivity(intent);
			}
		});
		
		llStrategy.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(IndexActivity.this, IndexStrategyActivity.class);
				startActivity(intent);
			}
		});
		
		llBorrow.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(IndexActivity.this, BorrowActivity.class);
				startActivity(intent);
			}
		});
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return false;
	}
	
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			
			switch (msg.what) {
				case Constants.UPDATE_NONEED:
					Toast.makeText(getApplicationContext(), "不需要更新",
							Toast.LENGTH_SHORT).show();
				case Constants.UPDATE_CLIENT:
					
					try {
						localVersion = mApp.getVersionName();
						ApkInfoItem apk = (ApkInfoItem) msg.obj;
						
						if(apk.getVersion()!=null && !apk.getVersion().equals(localVersion)){
							Log.i(TAG, "版本号不相同 ");
							apkPath = apk.getApkPath();
							
							//对话框通知用户升级程序
							showUpdateDialog();
						}	
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				case Constants.GET_UPDATEINFO_ERROR:
					//服务器超时
					Toast.makeText(getApplicationContext(), "获取服务器更新信息失败", 1).show();
					break;
				case Constants.DOWN_ERROR:
					//下载apk失败
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
		ViewUtils.showDialog("", "", "提示", "发现新版本，确认更新？", IndexActivity.this, ViewUtils.Button_type_all, new MyDialogInterface() {
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

	/*
　　* 从服务器中下载APK
　　*/
	protected void downLoadApk() {
		final ProgressDialog pd;    //进度条对话框
		pd = new  ProgressDialog(this);
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.setCanceledOnTouchOutside(false);
		pd.setMessage("正在下载更新");
		pd.show();
		DefaultThreadPool.getInstance().execute(new Thread(){
			@Override
			public void run() {
				try {
					File file = DownLoadManager.getFileFromServer(apkPath, pd, IndexActivity.this);
					installApk(file);
					pd.dismiss(); //结束掉进度条对话框
				} catch (Exception e) {
					Message msg = new Message();
					msg.what = Constants.DOWN_ERROR;
					handler.sendMessage(msg);
					e.printStackTrace();
				}
			}});
	}
	
	//安装apk
	protected void installApk(File file) {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
		intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
		startActivity(intent);
		android.os.Process.killProcess(android.os.Process.myPid());
	}
}
