package com.banhuitong.activity;

import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.banhuitong.activity.jx.JxBindCardActivity;
import com.banhuitong.activity.jx.JxIncomeListActivity;
import com.banhuitong.activity.jx.JxOpenAccountActivity;
import com.banhuitong.activity.jx.JxRechargeActivity;
import com.banhuitong.activity.jx.JxSetTradePwdActivity;
import com.banhuitong.activity.jx.JxWithdrawActivity;
import com.banhuitong.async.DefaultThreadPool;
import com.banhuitong.async.GetAccSurveyTask;
import com.banhuitong.async.GetAccTask;
import com.banhuitong.async.GetPersonalInfoTask;
import com.banhuitong.async.GetUnreadMsgCountTask;
import com.banhuitong.async.LoginTask;
import com.banhuitong.async.jx.JxGetBalanceTask;
import com.banhuitong.inf.JxHandler;
import com.banhuitong.inf.ReceiverCallback;
import com.banhuitong.receiver.SystemReceiver;
import com.banhuitong.service.PushService;
import com.banhuitong.service.UpdateBadgeService;
import com.banhuitong.util.ActivityUtils;
import com.banhuitong.util.BadgeUtil;
import com.banhuitong.util.Constants;
import com.banhuitong.util.ViewUtils;

@SuppressLint("NewApi")
public class MyAccountActivity extends BaseActivity {

	private TextView tvWithdraw;
	private TextView tvRecharge;
	private TextView tvBalance;
	private TextView tvCollecting;
	private TextView tvFreezing;
	private TextView tvUsername;
	private LinearLayout llTouziguanli;
	private LinearLayout llZijinmingxi;
	private LinearLayout llHuankuanrili;
	private LinearLayout llYinlianzhanghu;
	private LinearLayout llCollecting;
	private LinearLayout llFreezing;
	private LinearLayout llHeader;
	private ImageView imgMessage;
	private ImageView imgSettings;

	private View headerView; // 头布局
	private int headerViewHeight; // 头布局的高度
	private Animation upAnim; // 向上旋转的动画
	private Animation downAnim; // 向下旋转的动画
	private ImageView ivArrow; // 头布局的箭头
	private TextView tvState; // 头布局刷新状态
	private TextView tvLastUpdateTime; // 头布局的最后刷新时间
	private int downY; // 按下时y轴的偏移量
	private int upY;
	private int downX;
	private int upX;
	private DisplayMode currentState = DisplayMode.Pull_Down;
	private Animation scaleAnimation, alphaAnimation;

	public enum DisplayMode {
		Pull_Down, // 下拉刷新的状态
		Release_Refresh, // 松开刷新的状态
		Refreshing // 正在刷新中的状态
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_account);
		init();
		setListener();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onStart() {
		super.onStart();
		loading.setVisibility(View.VISIBLE);

		DefaultThreadPool.getInstance().execute(
				new GetAccSurveyTask(handler, this.getApplicationContext()));
		DefaultThreadPool.getInstance().execute(
				new GetAccTask(handler, this.getApplicationContext()));
		DefaultThreadPool.getInstance().execute(
				new GetPersonalInfoTask(handler));
		DefaultThreadPool.getInstance().execute(
				new GetUnreadMsgCountTask(handler, getApplicationContext(),
						null));
		DefaultThreadPool.getInstance().execute(
				new JxGetBalanceTask(handler, getApplicationContext()));
		
		handler.postDelayed(new JxGetBalanceTask(handler, getApplicationContext()), 7000);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		loading.setVisibility(View.GONE);
		super.onPause();
	}

	private void init() {
		loading = ViewUtils.initRotateAnimation(this);

		tvWithdraw = (TextView) findViewById(R.id.tv_withdraw);
		tvRecharge = (TextView) findViewById(R.id.tv_recharge);
		tvBalance = (TextView) findViewById(R.id.tv_balance);
		tvCollecting = (TextView) findViewById(R.id.tv_collecting);
		tvFreezing = (TextView) findViewById(R.id.tv_freezing);
		tvUsername = (TextView) findViewById(R.id.tv_username);
		llTouziguanli = (LinearLayout) findViewById(R.id.check_1_1);
		llZijinmingxi = (LinearLayout) findViewById(R.id.check_2_1);
		llHuankuanrili = (LinearLayout) findViewById(R.id.check_1_2);
		llYinlianzhanghu = (LinearLayout) findViewById(R.id.check_2_2);
		llCollecting = (LinearLayout) findViewById(R.id.ll_collecting);
		llFreezing = (LinearLayout) findViewById(R.id.ll_freezing);
		llHeader = (LinearLayout) findViewById(R.id.ll_header);
		imgMessage = (ImageView) findViewById(R.id.img_message);
		imgSettings = (ImageView) findViewById(R.id.img_settings);

		llCollecting.setMinimumWidth(MyApplication.screen_w / 2);
		llFreezing.setMinimumWidth(MyApplication.screen_w / 2);
		llTouziguanli.setMinimumWidth(MyApplication.screen_w / 2);
		llZijinmingxi.setMinimumWidth(MyApplication.screen_w / 2);
		llHuankuanrili.setMinimumWidth(MyApplication.screen_w / 2);
		llYinlianzhanghu.setMinimumWidth(MyApplication.screen_w / 2);

		llCollecting.setMinimumHeight(MyApplication.screen_h / 10);
		llFreezing.setMinimumHeight(MyApplication.screen_h / 10);
		llTouziguanli.setMinimumHeight(MyApplication.screen_h / 7);
		llZijinmingxi.setMinimumHeight(MyApplication.screen_h / 7);
		llHuankuanrili.setMinimumHeight(MyApplication.screen_h / 7);
		llYinlianzhanghu.setMinimumHeight(MyApplication.screen_h / 7);

		initHeader();

		scaleAnimation = new ScaleAnimation(1.0f, 1.0f, 0.0f, 1.0f);
		scaleAnimation.setDuration(1000);
		alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
		alphaAnimation.setDuration(2000);

		systemReceiver = new SystemReceiver(new ReceiverCallback() {
			public void callback(String action) {
				if (action.equals(Constants.ACTION_LOG_OUT)) {
					Toast.makeText(getApplicationContext(), "登出成功。", 1).show();
					logout();
					if (!isLogin()) {
						if (MyAccountActivity.this.mApp.isServiceWork(
								MyAccountActivity.this.mApp
										.getApplicationContext(),
								"com.banhuitong.service.UpdateBadgeService")) {
							Intent i = new Intent(MyAccountActivity.this,
									UpdateBadgeService.class);
							stopService(i);
						}

						if (MyAccountActivity.this.mApp.isServiceWork(
								MyAccountActivity.this.mApp
										.getApplicationContext(),
								"com.banhuitong.service.PushService")) {
							Intent i2 = new Intent(MyAccountActivity.this,
									PushService.class);
							stopService(i2);
						}
					}
				}
			}
		});
	}

	private void initHeader() {
		headerView = LayoutInflater.from(this.getApplicationContext()).inflate(
				R.layout.activity_listview_refresh_header, null);
		ivArrow = (ImageView) headerView
				.findViewById(R.id.iv_listview_header_down_arrow);
		tvState = (TextView) headerView
				.findViewById(R.id.tv_listview_header_state);
		tvLastUpdateTime = (TextView) headerView
				.findViewById(R.id.tv_listview_header_last_update_time);

		ivArrow.setMinimumWidth(50);
		tvLastUpdateTime
				.setText("最后刷新时间: " + ActivityUtils.getLastUpdateTime());

		measureView(headerView);
		headerViewHeight = headerView.getMeasuredHeight();

		headerView.setPadding(0, -headerViewHeight, 0, 0);

		llHeader.addView(headerView);
		initAnimation();
	}

	private void refreshHeaderViewState() {
		if (currentState == DisplayMode.Pull_Down) { // 当前进入下拉状态
			ivArrow.startAnimation(downAnim);
			tvState.setText("下拉刷新");
		} else if (currentState == DisplayMode.Release_Refresh) { // 当前进入松开刷新状态
			ivArrow.startAnimation(upAnim);
			tvState.setText("松开刷新");
		} else if (currentState == DisplayMode.Refreshing) { // 当前进入正在刷新中
			ivArrow.clearAnimation();
			tvState.setText("正在刷新中..");
		}
	}

	public void refreshInfo() {
		loading.setVisibility(View.VISIBLE);
		if (isLogin()) {
			LoginTask loginTask = new LoginTask(handler,
					MyApplication.sp.getString(Constants.USER_NAME, ""),
					MyApplication.sp.getString(Constants.USER_PASS, ""));
			DefaultThreadPool.getInstance().execute(loginTask);
		}
	}

	private void measureView(View child) {
		ViewGroup.LayoutParams lp = child.getLayoutParams();
		if (lp == null) {
			lp = new ViewGroup.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}

		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0, lp.width);

		int lpHeight = lp.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
					MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}

	private void initAnimation() {
		upAnim = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		upAnim.setDuration(500);
		upAnim.setFillAfter(true);

		downAnim = new RotateAnimation(-180, -360, Animation.RELATIVE_TO_SELF,
				0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		downAnim.setDuration(500);
		downAnim.setFillAfter(true);
	}

	private void setListener() {

		imgSettings.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MyAccountActivity.this,
						SettingsActivity.class);
				startActivity(intent);
			}
		});

		imgMessage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showMessage();
			}
		});

		tvWithdraw.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				withdraw();
			}
		});

		tvRecharge.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				recharge();
			}
		});

		tvUsername.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MyAccountActivity.this,
						PersonalInfoActivity.class);
				getParent().startActivityForResult(intent, 0);
			}
		});

		llZijinmingxi.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MyAccountActivity.this,
						JxIncomeListActivity.class);
				startActivity(intent);
			}
		});

		llHuankuanrili.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				((MainActivity3) getParent()).loading
						.setVisibility(View.VISIBLE);
				Intent intent = new Intent();
				intent.setClass(MyAccountActivity.this, RepayPlanActivity.class);
				startActivity(intent);
			}
		});

		llTouziguanli.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MyAccountActivity.this,
						InvestListActivity.class);
				startActivity(intent);
			}
		});

		llYinlianzhanghu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				loading.setVisibility(View.VISIBLE);
				getJxInfo(null);
			}
		});
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			downY = (int) ev.getY();
			downX = (int) ev.getX();
			break;
		case MotionEvent.ACTION_UP:
			upY = (int) ev.getY();
			upX = (int) ev.getX();

			headerView.setPadding(0, -headerViewHeight, 0, 0);
			currentState = DisplayMode.Pull_Down;
			refreshHeaderViewState();

			if (upY - downY > 300) {
				refreshInfo();
			}

			break;
		case MotionEvent.ACTION_MOVE:
			int moveY = (int) ev.getY(); // 移动中的y轴的偏移量
			int diffY = moveY - downY;

			if (diffY < 5) {
				return super.onTouchEvent(ev);
			}

			int paddingTop = -headerViewHeight + (diffY / 2);
			if (paddingTop > -headerViewHeight) {

				if (paddingTop > 0 && currentState == DisplayMode.Pull_Down) { // 完全显示,
																				// 进入到刷新状态
					currentState = DisplayMode.Release_Refresh; // 把当前的状态改为松开刷新
					refreshHeaderViewState();
					tvLastUpdateTime.setText("最后刷新时间: "
							+ ActivityUtils.getLastUpdateTime());
				}
				headerView.setPadding(0, paddingTop, 0, 0);
			}

			break;
		default:
			break;
		}
		return super.dispatchTouchEvent(ev);
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			loading.setVisibility(View.GONE);

			switch (msg.what) {
			case Constants.GET_ACC_SURVEY:
				Map<String, String> accSurvey = (Map<String, String>) msg.obj;

				tvCollecting
						.setText(ViewUtils.mformat2
								.format(RepayPlanActivity.totalCollecting = ActivityUtils
										.getTotalCollecting(null)));
				tvFreezing.setText(ViewUtils.mformat2.format(NumberUtils
						.toDouble(accSurvey.get("freezing").toString(), 0)));
				break;
			case Constants.GET_ACC:
				Map<String, String> acc = (Map<String, String>) msg.obj;
				tvUsername.setText(acc.get("loginName").toString());
				break;
			case Constants.GET_BALANCE_SUCCESS:
				Map<String, String> bal = (Map<String, String>) msg.obj;
				tvBalance.setText(ViewUtils.mformat2.format(NumberUtils
						.toDouble(bal.get("available").toString(), 0)));
				break;
			case Constants.LOGIN_SUCCESS:
				DefaultThreadPool.getInstance().execute(
						new GetAccSurveyTask(handler, MyAccountActivity.this
								.getApplicationContext()));
				DefaultThreadPool.getInstance().execute(
						new GetAccTask(handler, MyAccountActivity.this
								.getApplicationContext()));
				DefaultThreadPool.getInstance().execute(
						new GetPersonalInfoTask(handler));
				DefaultThreadPool.getInstance().execute(
						new GetUnreadMsgCountTask(handler,
								getApplicationContext(), null));
				DefaultThreadPool.getInstance().execute(
						new JxGetBalanceTask(handler, getApplicationContext()));
				break;
			case Constants.GET_UNREAD_MESSAGE_COUNT_SUCCESS:
				int unreadCount = (Integer) ((Map<String, Object>) msg.obj)
						.get("unreadCount");
				if (unreadCount > 0) {
					imgMessage.setBackgroundResource(R.drawable.message_new);
				} else {
					imgMessage.setBackgroundResource(R.drawable.message);
				}

				if (unreadCount != BadgeUtil.badgeCount) {
					BadgeUtil.setBadgeCount(getApplicationContext(),
							unreadCount);
				}
				break;
			case Constants.GET_JXPAY_INFO_SUCCESS:
				gotoJx(null);
				break;
			case Constants.NETWORK_ERROR:
				Toast.makeText(getApplicationContext(), "网络异常！", 1).show();
				break;
			default:
				break;
			}
		}
	};

	public void checkNewMessage() {
		DefaultThreadPool.getInstance().execute(
				new GetUnreadMsgCountTask(handler, getApplicationContext(),
						null));
	}

	public void showMessage() {
		Intent intent = new Intent(MyAccountActivity.this,
				ShowWebViewActivity.class);
		intent.putExtra("url", Constants.mobileUrl + "app/message-list.html");
		intent.putExtra("title", "消息中心");
		getParent().startActivityForResult(intent, 0);
	}

	public void recharge() {
		Intent intent = new Intent();
		intent.setClass(MyAccountActivity.this, JxRechargeActivity.class);
		getParent().startActivityForResult(intent, 0);
	}

	public void withdraw() {
		Intent intent = new Intent();
		intent.setClass(MyAccountActivity.this, JxWithdrawActivity.class);
		getParent().startActivityForResult(intent, 0);
	}

	public void refreshLayout() {
		tvBalance.startAnimation(scaleAnimation);
		tvCollecting.startAnimation(scaleAnimation);
		tvFreezing.startAnimation(scaleAnimation);
		tvUsername.startAnimation(alphaAnimation);
	}
}
