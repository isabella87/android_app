package com.banhuitong.view;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import com.banhuitong.activity.MyApplication;
import com.banhuitong.util.Constants;
import com.banhuitong.util.ViewUtils;

public class ColumnChartView extends View {

	private List<Map<String, Object>> collects = new ArrayList<Map<String, Object>>();
	private Map<String, BigDecimal> collectsMonthly = new HashMap<String, BigDecimal>();
	private int size = 0;
	private int SINGLE_WIDTH;
	private final int MARGIN_LEFT = 60;
	private int BASE_LINE_Y;
	private Object[] key_arr = {};
	private BigDecimal maxMonthRepay = BigDecimal.ZERO;
	private float ratio = 0;

	public List<Map<String, Object>> getCollects() {
		return collects;
	}

	public void setCollects(List<Map<String, Object>> collects) {
		if (collects == null)
			return;

		this.collects = collects;

		for (int i = 0; i < collects.size(); i++) {
			Date dt = new Date((Long) collects.get(i).get("dueTime"));
			Calendar cal = Calendar.getInstance();
			cal.setTime(dt);

			String year = String.valueOf(cal.get(Calendar.YEAR));
			String month = cal.get(Calendar.MONTH) + 1 >= 10 ? String
					.valueOf(cal.get(Calendar.MONTH) + 1) : "0"
					+ String.valueOf(cal.get(Calendar.MONTH) + 1);
			String key = year + "/" + month;

			if (collectsMonthly.containsKey(key)) {
				collectsMonthly.put(
						key,
						collectsMonthly.get(key).add(
								new BigDecimal((Double) (collects.get(i)
										.get("amt")))));
			} else {
				collectsMonthly.put(key,
						new BigDecimal((Double) (collects.get(i).get("amt"))));
			}
		}

		size = collectsMonthly.size();

		key_arr = collectsMonthly.keySet().toArray();
		Arrays.sort(key_arr);

		for (BigDecimal val : collectsMonthly.values()) {
			if (val.compareTo(maxMonthRepay) == 1) {
				maxMonthRepay = val;
			}
		}
	}

	public ColumnChartView(Context context) {
		super(context);
	}

	public ColumnChartView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public ColumnChartView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		BASE_LINE_Y = this.getMeasuredHeight() - MyApplication.screen_w / 25;
		SINGLE_WIDTH = MyApplication.screen_w/3;
		ratio = maxMonthRepay.floatValue() / (this.getMeasuredHeight()*0.7f);
		
		setMeasuredDimension(MARGIN_LEFT + SINGLE_WIDTH * size,
				getMeasuredHeight());
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (collects.size() == 0) {
			this.setVisibility(View.GONE);
			return;
		} else {
			this.setVisibility(View.VISIBLE);
		}

		// 柱状体
		Paint paint = new Paint();
		paint.setColor(Constants.COLOR_FIREBRICK);
		paint.setStrokeWidth(80);

		// 日期
		Paint paint2 = new Paint();
		paint2.setColor(Constants.COLOR_DARK_GREY);
		paint2.setTextSize(MyApplication.screen_w / 25);

		// X轴
		Paint paint3 = new Paint();
		paint3.setColor(Constants.COLOR_GREY);
		paint3.setStrokeWidth(3);

		// 金额
		Paint paint4 = new Paint();
		paint4.setColor(Constants.COLOR_LIGHT_SEA_GREEN);
		paint4.setTextSize(MyApplication.screen_w / 25);
		paint4.setTypeface(Typeface.DEFAULT_BOLD);

		for (int i = 0; i < size; i++) {
			float stopY = (float) (BASE_LINE_Y - collectsMonthly.get(key_arr[i]).floatValue() / ratio);
			
			canvas.drawLine(MARGIN_LEFT + i * SINGLE_WIDTH, stopY, MARGIN_LEFT + i * SINGLE_WIDTH, BASE_LINE_Y, paint);
			canvas.drawText((String) key_arr[i], MARGIN_LEFT + i * SINGLE_WIDTH - 40, this.getMeasuredHeight(), paint2);
			canvas.drawText(ViewUtils.mformat.format(collectsMonthly.get(key_arr[i])), MARGIN_LEFT + i * SINGLE_WIDTH - 40, stopY - 5, paint4);
		}
		canvas.drawLine(0, BASE_LINE_Y, MARGIN_LEFT + SINGLE_WIDTH * size, BASE_LINE_Y, paint3);
		canvas.drawText("单位(元)", 0, MyApplication.screen_w / 25, paint4);
	}
}
