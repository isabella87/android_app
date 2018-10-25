package com.banhuitong.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.banhuitong.adapter.EntAdapter;
import com.banhuitong.async.CheckVersionTask;
import com.banhuitong.async.CountdownTask;
import com.banhuitong.async.GetPortalEntsTask;
import com.banhuitong.async.LoginTask;
import com.banhuitong.async.LogoutTask;
import com.banhuitong.inf.OnRefreshListener;
import com.banhuitong.item.ApkInfoItem;
import com.banhuitong.item.PortalPrjEnts;
import com.banhuitong.item.PrjEntItem;
import com.banhuitong.util.Constants;
import com.banhuitong.util.DownLoadManager;
import com.banhuitong.util.ViewUtils;
import com.banhuitong.view.MyImgScroll;
import com.banhuitong.view.MyListView;

@SuppressLint("NewApi")
public class MainActivity extends BaseActivity {
	
	public static final String TAG = "MainActivity";  

	private EntAdapter mAdapter;
	private MyListView lv;
	private List<Map<String,Object>> ents;
	private RadioGroup rg;
	public RadioButton mRadio1, mRadio2, mRadio3;
	boolean isSencondBackDown;
	private LinearLayout mViewPoints;
	private ImageView[] imageViews;
	private ImageView imageView;
	private MyImgScroll vp;
	private LinearLayout main;
	private ImageView imgMenu;
	private TextView tvLogin;
	private TextView tvLogout;
	private Dialog dialog;
	
    @Override
	protected void onStop() {
		super.onStop();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		loading = ViewUtils.initRotateAnimation(this);
		init();
		setListener();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
		CheckVersionTask cv = new CheckVersionTask(handler);
		new Thread(cv).start();
		
		mRadio2.performClick();
		mRadio1.performClick();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	private void init() {
		lv = (MyListView) findViewById(R.id.lv_ent);
		rg = (RadioGroup) findViewById(R.id.tabs_rg_main);
		mRadio1 = (RadioButton) findViewById(R.id.tab_rb_ent);
		mRadio2 = (RadioButton) findViewById(R.id.tab_rb_bhb);
		mRadio3 = (RadioButton) findViewById(R.id.tab_rb_credit_assign);
		vp = (MyImgScroll) findViewById(R.id.vp);
		main = (LinearLayout) findViewById(R.id.main);
		imgMenu = (ImageView) findViewById(R.id.menu);
		tvLogin = (TextView) findViewById(R.id.txtLogin);
		tvLogout = (TextView) findViewById(R.id.txtLogout);
		
		mAdapter = new EntAdapter(MainActivity.this, ents);
   	  	lv.setAdapter(mAdapter);
   	  	
	   	imageViews = new ImageView[vp.mImageViews.length];
		mViewPoints = (LinearLayout) findViewById(R.id.viewGroup);
		for (int i = 0; i < vp.mImageViews.length; i++) {
	        imageView = new ImageView(this);
	        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
	                15, 15);
	        layoutParams.setMargins(5, 0, 5, 0);
	        imageView.setLayoutParams(layoutParams);
	        
	        imageViews[i] = imageView;
	        if (i == 0) {
	            imageViews[i].setBackgroundDrawable(new BitmapDrawable(ViewUtils.readBitMap(MainActivity.this, R.drawable.pin_selected, getResources())));
	        } else {
	            imageViews[i].setBackgroundDrawable(new BitmapDrawable(ViewUtils.readBitMap(MainActivity.this, R.drawable.pin_normal, getResources())));
	        }
	
	        mViewPoints.addView(imageViews[i]);
	   }
	}
	
	private void setListener() {
		
		lv.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
			}

			@Override
			public void onLoadfresh(int type, int page) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onLoadMoring(boolean finish) {
				loading.setVisibility(View.VISIBLE);
				lv.page++;
				GetPortalEntsTask cv = new GetPortalEntsTask(handler,lv.page);
				new Thread(cv).start();
			}
		});
		
		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				loading.setVisibility(View.VISIBLE);
				
				if(checkedId==mRadio1.getId()){
					lv.setAdapter(mAdapter);
					ents = new ArrayList<Map<String,Object>>();
					lv.page = 1;

					GetPortalEntsTask cv = new GetPortalEntsTask(handler,lv.page);
					new Thread(cv).start();
				}else{
					lv.setAdapter(null);
					loading.setVisibility(View.GONE);
				}
				
				for (int i = 0; i < rg.getChildCount(); i++) {
					RadioButton rb = (RadioButton) rg.getChildAt(i);
					rb.setTextColor(Color.rgb(255,0,0));
					rb.setBackgroundDrawable(new BitmapDrawable(ViewUtils.readBitMap(MainActivity.this, R.drawable.main_tab, getResources())));
					
					if(rb.getId()==checkedId){
						rb.setTextColor(Color.rgb(255, 255, 255));
						rb.setBackgroundColor(Color.rgb(255,0,0));
					}
				}
			}
		});
		
		vp.setOnPageChangeListener(new NavigationPageChangeListener());
		
		imgMenu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(main.getX()==0){
					main.setX(-400);
				}else{
					main.setX(0);
				}
			}
		});
		
		tvLogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				LoginTask loginTask = new LoginTask(handler, "gaoyj", "000000");
				new Thread(loginTask).start();
			}
		});
		
		tvLogout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				LogoutTask logoutTask = new LogoutTask(handler);
				new Thread(logoutTask).start();
			}
		});
		
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			
			}
		});
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {

			// 调用双击退出函数
			Timer tExit = null;
			if (isSencondBackDown == false) {
				isSencondBackDown = true;// 准备退出

				Toast toast = Toast.makeText(getApplicationContext(), "再按一次退出程序", 0);
				toast.show();
				
				tExit = new Timer();
				tExit.schedule(new TimerTask() {
					@Override
					public void run() {
						isSencondBackDown = false; // 取消退出
					}
				}, 2000);// 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
				return false;

			} else {
				exitApp();
				return true;
			}
		} else {
			isSencondBackDown = false;
		}
		return false;
	}
	
	private void exitApp() {
		ActivityManager activityMgr = (ActivityManager) this
				.getSystemService(ACTIVITY_SERVICE);
		activityMgr.killBackgroundProcesses(getPackageName());
		System.exit(0);
	}
	
	private String getVersionName() throws Exception {
		PackageManager packageManager = getPackageManager();
		PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(),0);
		return packInfo.versionName;
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
						localVersion = getVersionName();
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
				case Constants.GET_PORTAL_ENTS:
					 PortalPrjEnts portalPrjEnts = (PortalPrjEnts) msg.obj;
					 
					 if(ents!=null){
						 ents.addAll(portalPrjEnts.getEnts());
					 }else{
						 ents = portalPrjEnts.getEnts();
					 }
		        	  
		        	  mAdapter.setEnts(ents);
		        	  mAdapter.notifyDataSetChanged();
		        	  
		        	CountdownTask countdownTask = new CountdownTask(handler);
		      		new Thread(countdownTask).start();
					break;
				case Constants.COUNTDOWN_PORTAL_ENTS:
					for(int i = 0; i < lv.getChildCount(); i++){  
			            View view = lv.getChildAt(i);  
			            TextView tvCountdown = (TextView)view.findViewById(R.id.tv_count_down);  
			            tvCountdown.setText(new Date().getTime() + "");  
			        } 
					break;
				case Constants.NETWORK_ERROR:
					Toast.makeText(getApplicationContext(), "网络异常！", 1).show();
					break;
				case Constants.LOGIN_SUCCESS:
					Toast.makeText(getApplicationContext(), "登录成功。", 1).show();
					Map<String,Object> map = (Map<String, Object>) msg.obj;
					mApp.setSavedPassword(map.get(Constants.USER_PASS).toString());
					mApp.setSavedUsername(map.get(Constants.USER_NAME).toString());
					login();
					break;
				case Constants.LOGOUT_SUCCESS:
					Toast.makeText(getApplicationContext(), "登出成功。", 1).show();
					break;
				default:
			}
		}
	};
	
	protected void showUpdateDialog() {
		AlertDialog.Builder builer = new Builder(this);
		builer.setTitle("版本升级");
		builer.setMessage("版本升级");
		builer.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				Log.i(TAG, "下载apk,更新");
				downLoadApk();
			}
		});
		builer.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		AlertDialog dialog = builer.create();
		dialog.show();
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
		new Thread(){
			@Override
			public void run() {
				try {
					File file = DownLoadManager.getFileFromServer(apkPath, pd, MainActivity.this);
					installApk(file);
					pd.dismiss(); //结束掉进度条对话框
				} catch (Exception e) {
					Message msg = new Message();
					msg.what = Constants.DOWN_ERROR;
					handler.sendMessage(msg);
					e.printStackTrace();
				}
			}}.start();
	}
	
	//安装apk
	protected void installApk(File file) {
		Intent intent = new Intent();
		//执行动作
		intent.setAction(Intent.ACTION_VIEW);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
		//执行的数据类型
		intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
		startActivity(intent);
		android.os.Process.killProcess(android.os.Process.myPid());
	}
	
	private class NavigationPageChangeListener implements OnPageChangeListener {
		 
        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
 
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }
 
        @Override
        public void onPageSelected(int position) {
        	position = position%vp.mImageViews.length==0 ? vp.mImageViews.length : position%vp.mImageViews.length;
        	
            for (int i = 0; i < imageViews.length; i++) {
                imageViews[position % vp.mImageViews.length].setBackgroundDrawable(new BitmapDrawable(ViewUtils.readBitMap(MainActivity.this, R.drawable.pin_selected, getResources())));
                if (position != i) {
                    imageViews[i].setBackgroundDrawable(new BitmapDrawable(ViewUtils.readBitMap(MainActivity.this, R.drawable.pin_normal, getResources())));
                }
            }
        }
    }
	
}
