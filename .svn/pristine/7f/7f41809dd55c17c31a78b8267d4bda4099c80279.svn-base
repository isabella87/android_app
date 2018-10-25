package com.banhuitong.activity;

import java.io.File;
import java.lang.ref.WeakReference;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
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
import com.banhuitong.view.MyImgScroll;

public class IndexActivity2 extends BaseFragmentActivity implements
		OnPageChangeListener {

	public static final String TAG = "IndexActivity";

	boolean isSencondBackDown;
	private LinearLayout llGong, llBao, llZhuan, llBorrow, llInfo, llStrategy;
	private AutoScrollTextView autoScrollTextView;
	private MyImgScroll vp;
	private ImageView[] dots;
	private int currentIndex;
	private ImageHandler imageHandler = new ImageHandler(
			new WeakReference<IndexActivity2>(this));

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index2);
		init();
		setListener();
		initDots();
	}

	@Override
	protected void onStart() {
		super.onStart();

		if (!mApp.isStartup) {
			DefaultThreadPool.getInstance().execute(
					new CheckVersionTask(handler));
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

		llGong = (LinearLayout) findViewById(R.id.ll_gong);
		llBao = (LinearLayout) findViewById(R.id.ll_bao);
		llZhuan = (LinearLayout) findViewById(R.id.ll_zhuan);
		llBorrow = (LinearLayout) findViewById(R.id.ll_borrow);
		llInfo = (LinearLayout) findViewById(R.id.ll_info);
		llStrategy = (LinearLayout) findViewById(R.id.ll_strategy);
		vp = (MyImgScroll) findViewById(R.id.vp);
		final ScrollView sv = (ScrollView) findViewById(R.id.scroll_view);
		LayoutParams lp = sv.getLayoutParams();
		lp.height = 700;
		sv.setLayoutParams(lp);

		vp.setActivity(this);

		// vp.setOnPageChangeListener(this);

		// autoScrollTextView =
		// (AutoScrollTextView)findViewById(R.id.TextViewNotice);
		// autoScrollTextView.init(getWindowManager());
		// autoScrollTextView.startScroll();

		vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			// 配合Adapter的currentItem字段进行设置。
			@Override
			public void onPageSelected(int arg0) {
				setCurrentDot(arg0 % vp.mImageViews.length);
				imageHandler.sendMessage(Message.obtain(imageHandler,
						ImageHandler.MSG_PAGE_CHANGED, arg0, 0));
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			// 覆写该方法实现轮播效果的暂停和恢复
			@Override
			public void onPageScrollStateChanged(int arg0) {
				switch (arg0) {
				case ViewPager.SCROLL_STATE_DRAGGING:
					imageHandler.sendEmptyMessage(ImageHandler.MSG_KEEP_SILENT);
					break;
				case ViewPager.SCROLL_STATE_IDLE:
					imageHandler.sendEmptyMessageDelayed(
							ImageHandler.MSG_UPDATE_IMAGE,
							ImageHandler.MSG_DELAY);
					break;
				default:
					break;
				}
			}
		});
		// 开始轮播效果
		imageHandler.sendEmptyMessageDelayed(ImageHandler.MSG_UPDATE_IMAGE,
				ImageHandler.MSG_DELAY);
	}

	private void setListener() {

		llGong.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!isLogin()) {
					Intent intent = new Intent(IndexActivity2.this,
							LoginActivity.class);
					startActivity(intent);
					((MainActivity3) getParent()).pager.setCurrentItem(0);
					return;
				}
				((MainActivity3) getParent()).pager.setCurrentItem(1);
				((MainActivity3) getParent()).loadCurActivity(1, 1, true);
			}
		});

		llBao.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// ((MainActivity3)getParent()).pager.setCurrentItem(1);
				// ((MainActivity3)getParent()).loadCurActivity(1, 2, true);
				showShare();
			}
		});

		llZhuan.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!isLogin()) {
					Intent intent = new Intent(IndexActivity2.this,
							LoginActivity.class);
					startActivity(intent);
					((MainActivity3) getParent()).pager.setCurrentItem(0);
					return;
				}
				((MainActivity3) getParent()).pager.setCurrentItem(1);
				((MainActivity3) getParent()).loadCurActivity(1, 3, true);
			}
		});

		llInfo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(IndexActivity2.this,
						IndexAffairsActivity.class);
				startActivity(intent);
			}
		});

		llStrategy.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(IndexActivity2.this,
						RepayNoticeActivity.class);
				startActivity(intent);
			}
		});

		llBorrow.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(IndexActivity2.this,
						BorrowActivity.class);
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

					if (apk.getVersion() != null
							&& !apk.getVersion().equals(localVersion)) {
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
			default:
			}
		}
	};

	protected void showUpdateDialog() {
		ViewUtils.showDialog("", "", "提示", "发现新版本，确认更新？", IndexActivity2.this,
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
							IndexActivity2.this);
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

	private void initDots() {
		LinearLayout ll = (LinearLayout) findViewById(R.id.ll);

		dots = new ImageView[vp.mImageViews.length];

		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				25, 25);

		for (int i = 0; i < vp.mImageViews.length; i++) {
			ImageView dot = new ImageView(this.getApplicationContext());
			dot.setPadding(7, 7, 7, 7);
			dot.setImageResource(R.drawable.dot2);
			dot.setLayoutParams(layoutParams);
			ll.addView(dot);

			dots[i] = dot;
			dots[i].setEnabled(false);
		}

		currentIndex = 0;
		dots[currentIndex].setEnabled(true);
	}

	private void setCurrentDot(int position) {
		if (position < 0 || position > vp.mImageViews.length - 1
				|| currentIndex == position) {
			return;
		}

		dots[position].setEnabled(true);
		dots[currentIndex].setEnabled(false);

		currentIndex = position;
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int arg0) {
		setCurrentDot(arg0 % vp.mImageViews.length);
		imageHandler.sendMessage(Message.obtain(imageHandler,
				ImageHandler.MSG_PAGE_CHANGED, arg0, 0));
	}

	private static class ImageHandler extends Handler {

		/**
		 * 请求更新显示的View。
		 */
		protected static final int MSG_UPDATE_IMAGE = 1;
		/**
		 * 请求暂停轮播。
		 */
		protected static final int MSG_KEEP_SILENT = 2;
		/**
		 * 请求恢复轮播。
		 */
		protected static final int MSG_BREAK_SILENT = 3;
		/**
		 * 记录最新的页号，当用户手动滑动时需要记录新页号，否则会使轮播的页面出错。
		 * 例如当前如果在第一页，本来准备播放的是第二页，而这时候用户滑动到了末页，
		 * 则应该播放的是第一页，如果继续按照原来的第二页播放，则逻辑上有问题。
		 */
		protected static final int MSG_PAGE_CHANGED = 4;

		// 轮播间隔时间
		protected static final long MSG_DELAY = 3000;

		// 使用弱引用避免Handler泄露.这里的泛型参数可以不是Activity，也可以是Fragment等
		private WeakReference<IndexActivity2> weakReference;
		private int currentItem = 0;

		protected ImageHandler(WeakReference<IndexActivity2> wk) {
			weakReference = wk;
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			IndexActivity2 activity = weakReference.get();
			if (activity == null) {
				// Activity已经回收，无需再处理UI了
				return;
			}
			// 检查消息队列并移除未发送的消息，这主要是避免在复杂环境下消息出现重复等问题。
			if (activity.imageHandler.hasMessages(MSG_UPDATE_IMAGE)) {
				activity.imageHandler.removeMessages(MSG_UPDATE_IMAGE);
			}
			switch (msg.what) {
			case MSG_UPDATE_IMAGE:
				currentItem++;
				activity.vp.setCurrentItem(currentItem);
				// 准备下次播放
				activity.imageHandler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE,
						MSG_DELAY);
				break;
			case MSG_KEEP_SILENT:
				// 只要不发送消息就暂停了
				break;
			case MSG_BREAK_SILENT:
				activity.imageHandler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE,
						MSG_DELAY);
				break;
			case MSG_PAGE_CHANGED:
				// 记录当前的页号，避免播放的时候页面显示不正确。
				currentItem = msg.arg1;
				break;
			default:
				break;
			}
		}
	}

}
