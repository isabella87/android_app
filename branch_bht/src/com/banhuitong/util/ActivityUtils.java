package com.banhuitong.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.banhuitong.cache.CacheObject;

import android.content.Context;
import android.content.Intent;

public class ActivityUtils {
	
	public static void startIntent(Context context, Class c) {
		Intent intent = new Intent(context, c);
		context.startActivity(intent);
	}
	
	/**
	 * @Title: getAssignRate
	 * @Description: 计算转让后年化收益
	 * @param credit_amount
	 *            债权本金
	 * @param assign_amount
	 *            转让标价
	 * @param notReceiveLilv
	 *            所有未结利息
	 * @param mathContext
	 * @param day
	 *            剩余借款期限
	 *
	 * @param @throws Exception
	 * @return BigDecimal 返回类型
	 * @throws
	 */
	public static BigDecimal getAssignRate(BigDecimal credit_amount,
			BigDecimal assign_amount, BigDecimal notReceiveLilv,
			MathContext mathContext, long day) throws Exception {
		// 转让后年化收益(每天都在变)= (债权本金(动态获取,因为有可能还本金) - 转让标价 +
		// 所有未结利息(动态获取,因为有可能还未结利息)) ÷ 剩余借款期限(每天都在递减)×365（天）÷ 债权本金
		// ×100%，保留两位小数，小数第三位使用四舍五入。
		
		// 转让后年化收益(每天都在变)= (债权本金(动态获取,因为有可能还本金) - 转让标价 +
		// 所有未结利息(动态获取,因为有可能还未结利息)) ÷ 剩余借款期限(每天都在递减)×365（天）÷ ( 转让标价)
		// ×100%，保留两位小数，小数第三位使用四舍五入。

		final BigDecimal DAYS_OF_YEAR = BigDecimal.valueOf(365L);

		BigDecimal moneyTemp1 = credit_amount.subtract(assign_amount);
		BigDecimal moneyTemp2 = moneyTemp1.add(notReceiveLilv);
		try{
			if (moneyTemp2.compareTo(BigDecimal.ZERO) >= 0) {
				BigDecimal assignRateB = moneyTemp2.multiply(DAYS_OF_YEAR)
						.multiply(BigDecimal.valueOf(100)).divide(
								assign_amount.multiply(BigDecimal.valueOf(day)),
								mathContext);
				return assignRateB;
			}
		}catch(ArithmeticException e){
			return BigDecimal.ZERO;
		}
		return BigDecimal.ZERO;
	}
	
	/**
	 * @Title: getAssignAmtByAssignRate
	 * @Description: 通过年化收益计算转让标价
	 * @param credit_amount
	 *            债权本金
	 * @param assign_rate
	 *            年化收益
	 * @param notReceiveLilv
	 *            所有未结利息
	 * @param mathContext
	 * @param day
	 *            剩余借款期限
	 *
	 * @param @throws Exception
	 * @return BigDecimal 返回类型
	 * @throws
	 */
	public static BigDecimal getAssignAmtByAssignRate(BigDecimal captial,
			BigDecimal assignRate, BigDecimal bonus,  long day)  {
		
		return (captial.add(bonus)).multiply(BigDecimal.valueOf(36500)).divide(
				assignRate.multiply(BigDecimal.valueOf(day)).add(BigDecimal.valueOf(36500)),
				3, RoundingMode.DOWN);
	}
	
	public static String formatMobile(String p){
		if(p==null)return "";
		if(p.length()<11)return "";
		return p.substring(0, 3) + "****" + p.substring(7, 11);
	}
	
	public static String getLastUpdateTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	}
	
	public static BigDecimal getTotalCollecting(Date selDate) {
		BigDecimal totalCollecting = BigDecimal.ZERO;

		if (selDate == null) {
			Calendar selectedDay = Calendar.getInstance();
			selectedDay.set(2099, 12, 31, 23, 59, 59);
			selDate = selectedDay.getTime();
		} else {
			selDate = DateUtils.setHours(selDate, 23);
			selDate = DateUtils.setMinutes(selDate, 59);
			selDate = DateUtils.setSeconds(selDate, 59);
		}

		JSONObject survey = CacheObject.getInstance().getSurvey();
		if (survey != null) {
			JSONArray collects = new JSONArray();
			try {
				collects = survey.getJSONArray("preReceiveAmtList");
				for (int i = 0; i < collects.length(); i++) {
					JSONObject collect = (JSONObject) collects.get(i);
					Date dt = new Date(collect.optLong("dueTime"));

					if (!dt.after(selDate)) {
						BigDecimal amt = new BigDecimal(collect.optDouble(
								"amt", 0));
						totalCollecting = totalCollecting.add(amt);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return totalCollecting;
	}

	public static BigDecimal getTotalCollectingSelectedDay(Date selDate) {
		BigDecimal totalCollecting = BigDecimal.ZERO;

		if (selDate == null) {
			Calendar selectedDay = Calendar.getInstance();
			selDate = selectedDay.getTime();
		}

		JSONObject survey = CacheObject.getInstance().getSurvey();
		if (survey != null) {
			JSONArray collects = new JSONArray();
			try {
				collects = survey.getJSONArray("preReceiveAmtList");
				for (int i = 0; i < collects.length(); i++) {
					JSONObject collect = (JSONObject) collects.get(i);

					Date dt = new Date(collect.optLong("dueTime"));
					if (DateUtils.isSameDay(selDate, dt)) {
						BigDecimal amt = new BigDecimal(collect.optDouble(
								"amt", 0));
						totalCollecting = totalCollecting.add(amt);
					}

				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return totalCollecting;
	}

	public static BigDecimal getTotalCollectingThisMonth(Date selDate) {
		BigDecimal totalCollecting = BigDecimal.ZERO;
		Calendar selectedDay = Calendar.getInstance();

		if (selDate == null) {
			selectedDay.set(2099, 12, 31);
		} else {
			selectedDay.setTime(selDate);
			selectedDay.set(Calendar.HOUR_OF_DAY, 23);
			selectedDay.set(Calendar.MINUTE, 59);
			selectedDay.set(Calendar.SECOND, 59);
		}

		JSONObject survey = CacheObject.getInstance().getSurvey();
		if (survey != null) {
			JSONArray collects = new JSONArray();
			try {
				collects = survey.getJSONArray("preReceiveAmtList");
				for (int i = 0; i < collects.length(); i++) {
					JSONObject collect = (JSONObject) collects.get(i);

					Date dt = new Date(collect.optLong("dueTime"));
					Calendar cal = Calendar.getInstance();
					cal.setTime(dt);

					if (!dt.after(selectedDay.getTime())
							&& cal.get(Calendar.YEAR) == selectedDay
									.get(Calendar.YEAR)
							&& cal.get(Calendar.MONTH) == selectedDay
									.get(Calendar.MONTH)) {
						BigDecimal amt = new BigDecimal(collect.optDouble(
								"amt", 0));
						totalCollecting = totalCollecting.add(amt);
					}

				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return totalCollecting;
	}
}
