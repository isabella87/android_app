package com.banhuitong.activity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SlidingDrawer;
import android.widget.TextView;

import com.banhuitong.adapter.CollectAdapter;
import com.banhuitong.cache.CacheObject;
import com.banhuitong.inf.OnRefreshListener;
import com.banhuitong.util.ActivityUtils;
import com.banhuitong.util.ViewUtils;
import com.banhuitong.view.ColumnChartView;
import com.banhuitong.view.MyListView;
import com.squareup.timessquare.CalendarPickerView;
import com.squareup.timessquare.CalendarPickerView.OnDateSelectedListener;

@SuppressLint("NewApi")
public class RepayPlanActivity extends BaseActivity {

	private ImageView imgBack;
	private TextView tvCollectingSelectedDay;
	private TextView tvCollectingTotal;
	private TextView tvCollectingThisMonth;
	private CalendarPickerView calendar;
	private LinearLayout llMain;
	private LinearLayout llMain2;
	private TextView tvShowList;
	private LinearLayout llShowList;
	private MyListView lv;
	private List<Map<String, Object>> _collects = new ArrayList<Map<String, Object>>();
	private CollectAdapter cAdpter;
	public static BigDecimal totalCollecting = BigDecimal.ZERO;
	private Date selectedDay = new Date();
	private int tab = 1;
	private SlidingDrawer mDrawer;
	private Boolean booDrawerOpened = false;
	private ImageButton imbg;
	private ColumnChartView vColChart;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.repay_plan);
		initList(null);
		init();
		setListener();
		initCalendar();
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	private void init() {
		tvCollectingSelectedDay = (TextView) findViewById(R.id.tv_collecting_today);
		tvCollectingTotal = (TextView) findViewById(R.id.tv_collecting_total);
		tvCollectingThisMonth = (TextView) findViewById(R.id.tv_collecting_this_month);
		imgBack = (ImageView) findViewById(R.id.back);
		calendar = (CalendarPickerView) findViewById(R.id.calendar_view);
		llMain = (LinearLayout) findViewById(R.id.ll_main);
		llMain2 = (LinearLayout) findViewById(R.id.ll_main2);
		tvShowList = (TextView) findViewById(R.id.tv_show_list);
		llShowList = (LinearLayout) findViewById(R.id.ll_show_list);
		lv = (MyListView) findViewById(R.id.lv_collecting);
		mDrawer = (SlidingDrawer) findViewById(R.id.slidingdrawer);
		imbg=(ImageButton)findViewById(R.id.handle);
		vColChart = (ColumnChartView)findViewById(R.id.v_col_chart);
	
		
		imbg.setTranslationX(mApp.screen_w/2 - 50);
		imbg.getBackground().setAlpha(0);

		cAdpter = new CollectAdapter(RepayPlanActivity.this, _collects);
		lv.setAdapter(cAdpter);

		TextView tvTitle = (TextView) findViewById(R.id.tv_title);
		tvTitle.setText("还款日历");

		tvCollectingSelectedDay.setText(ViewUtils.mformat
				.format(ActivityUtils.getTotalCollectingSelectedDay(selectedDay)) + "元");
		tvCollectingTotal.setText(ViewUtils.mformat.format(totalCollecting)
				+ "元");
		tvCollectingThisMonth.setText(ViewUtils.mformat
				.format(ActivityUtils.getTotalCollectingThisMonth(selectedDay)) + "元");
		
		vColChart.setCollects(_collects);
	}

	@SuppressWarnings("deprecation")
	private void setListener() {
		imgBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				RepayPlanActivity.this.finish();
			}
		});

		lv.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				lv.onRefreshFinish();
			}

			@Override
			public void onLoadfresh(int type, int page) {
				lv.onRefreshFinish();
			}

			@Override
			public void onLoadMoring(boolean finish) {
				lv.onRefreshFinish();
			}
		});

		llShowList.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (tab == 1) {
					AnimationSet animationSet = new AnimationSet(true);
					TranslateAnimation translateAnimation = new TranslateAnimation(
							Animation.RELATIVE_TO_SELF, 0f,
							Animation.RELATIVE_TO_SELF, -1f,
							Animation.RELATIVE_TO_SELF, 0f,
							Animation.RELATIVE_TO_SELF, 0f);
					translateAnimation.setDuration(500);
					animationSet.addAnimation(translateAnimation);
					llMain.startAnimation(animationSet);

					llMain.setVisibility(View.GONE);
					llMain2.setVisibility(View.VISIBLE);
					tvShowList.setText(getResources().getString(
							R.string.btn_repay_show_calendar));
					tab = 2;
				} else if (tab == 2) {
					AnimationSet animationSet = new AnimationSet(true);
					TranslateAnimation translateAnimation = new TranslateAnimation(
							Animation.RELATIVE_TO_SELF, -1f,
							Animation.RELATIVE_TO_SELF, 0f,
							Animation.RELATIVE_TO_SELF, 0f,
							Animation.RELATIVE_TO_SELF, 0f);
					translateAnimation.setDuration(500);
					animationSet.addAnimation(translateAnimation);
					llMain.startAnimation(animationSet);

					llMain.setVisibility(View.VISIBLE);
					// llMain2.setVisibility(View.GONE);
					tvShowList.setText(getResources().getString(
							R.string.btn_repay_show_all));
					tab = 1;
				}
			}
		});

		mDrawer.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener() {
			@Override
			public void onDrawerOpened() {
				booDrawerOpened = true;
				imbg.setImageResource(R.drawable.arrow_down);
			}

		});

		mDrawer.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener() {

			@Override
			public void onDrawerClosed() {
				booDrawerOpened = false;
				imbg.setImageResource(R.drawable.arrow_up);
			}

		});

		mDrawer.setOnDrawerScrollListener(new SlidingDrawer.OnDrawerScrollListener() {

			@Override
			public void onScrollEnded() {

			}

			@Override
			public void onScrollStarted() {

			}

		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			RepayPlanActivity.this.finish();
			return true;
		}
		return false;
	}

	private void initCalendar() {
		Date min = new Date();
		Date max = DateUtils.addDays(min, 1);

		Collection<Date> c = new ArrayList<Date>();

		JSONObject survey = CacheObject.getInstance().getSurvey();
		if (survey != null) {
			JSONArray collects = new JSONArray();
			try {
				collects = survey.getJSONArray("preReceiveAmtList");
				for (int i = 0; i < collects.length(); i++) {
					JSONObject collect = (JSONObject) collects.get(i);

					Date dt = new Date(collect.optLong("dueTime"));
					c.add(dt);

					if (dt.after(max)) {
						max = dt;
					}

					if (dt.before(min)) {
						min = dt;
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		calendar.init(min, DateUtils.addDays(max, 1));
		calendar.highlightDates(c);
		calendar.setOnDateSelectedListener(new OnDateSelectedListener() {

			@Override
			public void onDateSelected(Date date) {
				selectedDay = date;
				tvCollectingSelectedDay.setText(ViewUtils.mformat
						.format(ActivityUtils.getTotalCollectingSelectedDay(selectedDay))
						+ "元");
				tvCollectingTotal.setText(ViewUtils.mformat
						.format(totalCollecting = ActivityUtils
								.getTotalCollecting(selectedDay))
						+ "元");
				tvCollectingThisMonth.setText(ViewUtils.mformat
						.format(ActivityUtils.getTotalCollectingThisMonth(selectedDay)) + "元");
				initList(selectedDay);
				cAdpter.notifyDataSetChanged();
			}

			@Override
			public void onDateUnselected(Date date) {
				// TODO Auto-generated method stub
			}

		});
	}

	private void initList(Date selDate) {

		if (selDate == null) {
			Calendar selectedDay = Calendar.getInstance();
			selectedDay.set(2099, 12, 31, 23, 59, 59);
			selDate = selectedDay.getTime();
		} else {
			selDate = DateUtils.setHours(selDate, 23);
			selDate = DateUtils.setMinutes(selDate, 59);
			selDate = DateUtils.setSeconds(selDate, 59);
		}

		_collects.clear();

		JSONObject survey = CacheObject.getInstance().getSurvey();
		if (survey != null) {
			JSONArray collects = new JSONArray();
			try {
				collects = survey.getJSONArray("preReceiveAmtList");

				for (int i = 0; i < collects.length(); i++) {
					JSONObject collect = (JSONObject) collects.get(i);

					Date dt = new Date(collect.optLong("dueTime"));
					if (!dt.after(selDate)) {
						Map<String, Object> c = new HashMap<String, Object>();
						c.put("dueTime", collect.optLong("dueTime", 0));
						c.put("amt", collect.optDouble("amt", 0));
						c.put("tranType", collect.optInt("tranType", 0));
						c.put("itemShowName",
								collect.optString("itemShowName", ""));
						_collects.add(c);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
}
