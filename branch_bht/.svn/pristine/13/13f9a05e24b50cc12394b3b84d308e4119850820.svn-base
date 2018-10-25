package com.banhuitong.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;

import com.banhuitong.fragment.FooterFragment;
import com.banhuitong.inf.ReceiverCallback;
import com.banhuitong.receiver.SystemReceiver;
import com.banhuitong.service.PushService;
import com.banhuitong.service.UpdateBadgeService;
import com.banhuitong.util.Constants;
import com.banhuitong.util.ViewUtils;

@SuppressLint("NewApi")
public class MainActivity3 extends BaseFragmentActivity {

	public static final String TAG = "MainActivity3";
	public static final int OPT_MONEY_SHORT = 401;
	public static final int OPT_TO_JX = 402;

	@SuppressWarnings("deprecation")
	public LocalActivityManager manager = null;
	public ViewPager pager = null;
	boolean isSencondBackDown;
	public View loading;
	public ArrayList<View> list = new ArrayList<View>();
	private String[] mlistTag = { Constants.MAIN_PAGE_A, Constants.MAIN_PAGE_B,
			Constants.MAIN_PAGE_C, Constants.MAIN_PAGE_D }; // activity标识
	private View vTopbar;
	private FooterFragment vFooter;
	public boolean refreshA = false;
	public boolean refreshB = false;
	public boolean refreshC = false;
	public boolean refreshD = false;
	private Intent lastIntent;

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main3);

		manager = new LocalActivityManager(this, true);
		manager.dispatchCreate(savedInstanceState);

		vTopbar = (View) findViewById(R.id.topbar);
		vFooter = (FooterFragment) getFragmentManager().findFragmentById(
				R.id.id_fragment_footer);

		loading = ViewUtils.initRotateAnimation(this);
		initPagerViewer();
		setListener();

		systemReceiver = new SystemReceiver(new ReceiverCallback() {
			public void callback(String action) {
				if (action.equals(Constants.ACTION_LOG_IN)) {
					loadCurActivity(2, -999, true);
					if (isLogin()) {
						if (!MainActivity3.this.mApp.isServiceWork(
								MainActivity3.this.mApp.getApplicationContext(),
								"com.banhuitong.service.UpdateBadgeService")) {
							Intent i = new Intent(MainActivity3.this,
									UpdateBadgeService.class);
							startService(i);
						}

						if (!MainActivity3.this.mApp.isServiceWork(
								MainActivity3.this.mApp.getApplicationContext(),
								"com.banhuitong.service.PushService")) {
							Intent i2 = new Intent(MainActivity3.this,
									PushService.class);
							startService(i2);
						}
					}
				}
			}
		});

		lastIntent = getIntent();
		String moveToMyAcc = lastIntent.getStringExtra("moveToMyAcc");
		if ("Y".equals(moveToMyAcc)) {
			this.refreshC = true;
			this.pager.setCurrentItem(2);
			this.refreshC = false;
			Activity curActivity = manager.getActivity(mlistTag[2]);
			if (curActivity != null && curActivity instanceof MyAccountActivity) {
				((MyAccountActivity) curActivity).showMessage();
			}
		}

		// PushManager pm = PushManager.getInstance();
		// pm.initialize(this.getApplicationContext());
		// String cid = pm.getClientid(this.getApplicationContext());
		// Log.i(TAG, "cid = " + cid);
		// DefaultThreadPool.getInstance().execute(new
		// UpdateAppUserTask(null,cid));
	}

	@Override
	protected void onStart() {
		super.onStart();
		loading.setVisibility(View.GONE);

		if (isLogin()) {
			if (!this.mApp.isServiceWork(this.mApp.getApplicationContext(),
					"com.banhuitong.service.UpdateBadgeService")) {
				Intent i = new Intent(MainActivity3.this,
						UpdateBadgeService.class);
				startService(i);
			}

			if (!this.mApp.isServiceWork(this.mApp.getApplicationContext(),
					"com.banhuitong.service.PushService")) {
				Intent i2 = new Intent(MainActivity3.this, PushService.class);
				startService(i2);
			}
		} else {
			if (this.mApp.isServiceWork(this.mApp.getApplicationContext(),
					"com.banhuitong.service.UpdateBadgeService")) {
				Intent i = new Intent(MainActivity3.this,
						UpdateBadgeService.class);
				stopService(i);
			}

			if (this.mApp.isServiceWork(this.mApp.getApplicationContext(),
					"com.banhuitong.service.PushService")) {
				Intent i2 = new Intent(MainActivity3.this, PushService.class);
				stopService(i2);
			}
		}
	}

	private void initPagerViewer() {
		pager = (ViewPager) findViewById(R.id.viewpager);

		Intent intent = new Intent(MainActivity3.this, IndexActivity2.class);
		list.add(getView(Constants.MAIN_PAGE_A, intent));
		Intent intent2 = new Intent(MainActivity3.this, MainActivity2.class);
		list.add(getView(Constants.MAIN_PAGE_B, intent2));
		Intent intent3 = new Intent(MainActivity3.this, MyAccountActivity.class);
		list.add(getView(Constants.MAIN_PAGE_C, intent3));
		Intent intent4 = new Intent(MainActivity3.this, MoreActivity.class);
		list.add(getView(Constants.MAIN_PAGE_D, intent4));

		pager.setAdapter(new MyPagerAdapter(list));
		pager.setCurrentItem(0);
		pager.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	private void setListener() {
		vTopbar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pager.setCurrentItem(0);
			}
		});
	}

	private View getView(String id, Intent intent) {
		return manager.startActivity(id, intent).getDecorView();
	}

	public class MyPagerAdapter extends PagerAdapter {
		List<View> list = new ArrayList<View>();

		public MyPagerAdapter(ArrayList<View> list) {
			this.list = list;
		}

		@Override
		public float getPageWidth(int position) {
			// if (position == 3) {
			// return 0.8f;
			// }
			return 1f;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			ViewPager pViewPager = ((ViewPager) container);
			pViewPager.removeView(list.get(position));
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			ViewPager pViewPager = ((ViewPager) arg0);
			pViewPager.addView(list.get(arg1));
			return list.get(arg1);
		}

	}

	public class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageSelected(int index) {

			if (index == 2 || index == 1) {
				if (!isLogin()) {
					Intent intent = new Intent(MainActivity3.this,
							LoginActivity.class);
					startActivity(intent);
					pager.setCurrentItem(0);
					return;
				} else {
					loadCurActivity(2, -999, refreshC);
				}
			} else if (index == 3) {
				loadCurActivity(3, -999, refreshD);
			}

			vFooter.imgMyAccount
					.setBackgroundResource(R.drawable.footer_myaccount);
			vFooter.imgIndex.setBackgroundResource(R.drawable.footer_home);
			vFooter.imgMain.setBackgroundResource(R.drawable.footer_product);
			vFooter.imgMore.setBackgroundResource(R.drawable.footer_more);

			switch (index) {
			case 0:
				vFooter.imgIndex
						.setBackgroundResource(R.drawable.footer_home_highlight);
				break;
			case 1:
				vFooter.imgMain
						.setBackgroundResource(R.drawable.footer_product_highlight);
				break;
			case 2:
				vFooter.imgMyAccount
						.setBackgroundResource(R.drawable.footer_myaccount_highlight);
				break;
			case 3:
				vFooter.imgMore
						.setBackgroundResource(R.drawable.footer_more_highlight);
				break;
			default:

			}
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case Constants.RESULT_BACK_INDEX:
			pager.setCurrentItem(0);
			break;
		case Constants.RESULT_TO_LOGIN:
			Intent intent = new Intent(MainActivity3.this, LoginActivity.class);
			startActivity(intent);
			pager.setCurrentItem(0);
			break;
		case Constants.RESULT_INVEST_SUCCESS:
			this.refreshC = true;
			this.pager.setCurrentItem(2);
			this.refreshC = false;
			break;
		case Constants.RESULT_SHOW_MSG_SUCCESS:
			Activity curActivity = manager.getActivity(mlistTag[2]);
			((MyAccountActivity) curActivity).checkNewMessage();
			break;
		case Constants.RESULT_WITHDRAW_SUCCESS:
			loadCurActivity(2, -999, true);
			break;
		case Constants.RESULT_RECHARGE_SUCCESS:
			loadCurActivity(2, -999, true);
			break;
		case Constants.RESULT_RECHARGE_FAILED:
			loadCurActivity(2, -999, true);
			break;
		case Constants.RESULT_MONEY_SHORT:
			loadCurActivity(2, OPT_MONEY_SHORT, true);
			break;
		case Constants.RESULT_TO_JX:
			loadCurActivity(2, OPT_TO_JX, true);
			break;
		default:
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// 调用双击退出函数
			Timer tExit = null;
			if (isSencondBackDown == false) {
				isSencondBackDown = true;// 准备退出

				Toast toast = Toast.makeText(getApplicationContext(),
						"再按一次退出程序", 0);
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

	public void loadCurActivity(int index, int opt, boolean refresh) {
		Activity curActivity = manager.getActivity(mlistTag[index]);
		switch (index) {
		case 0:

			break;
		case 1:
			if (curActivity != null && curActivity instanceof MainActivity2) {
				switch (opt) {
				case 1:
					((MainActivity2) curActivity).mRadio1.performClick();
					break;
				case 2:
					((MainActivity2) curActivity).mRadio2.performClick();
					break;
				case 3:
					((MainActivity2) curActivity).mRadio3.performClick();
					break;
				default:
				}
			}
			break;
		case 2:
			if (curActivity != null && curActivity instanceof MyAccountActivity) {
				if (refresh) {
					((MyAccountActivity) curActivity).refreshInfo();
				}
				((MyAccountActivity) curActivity).refreshLayout();

				switch (opt) {
				case OPT_MONEY_SHORT:
					((MyAccountActivity) curActivity).recharge();
					break;
				case OPT_TO_JX:
					((MyAccountActivity) curActivity).getJxInfo(null);
					break;
				default:
					break;
				}
			}
			break;
		case 3:
			if (curActivity != null && curActivity instanceof MoreActivity) {
				((BaseActivity) curActivity).onStart();
				// ((BaseActivity) curActivity).showShare();
			}
			break;
		default:

		}
	}
}
