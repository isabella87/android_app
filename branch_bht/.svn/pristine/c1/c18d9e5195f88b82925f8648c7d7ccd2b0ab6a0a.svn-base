package com.banhuitong.adapter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.banhuitong.activity.R;
import com.banhuitong.util.ViewUtils;

public class InvestCaAdapter extends BaseAdapter {

	private static final String TAG = "InvestCaAdapter";

	private LayoutInflater inflater;
	private List<Map<String, Object>> invests;
	private int tab;

	public InvestCaAdapter(Context context, List<Map<String, Object>> invests) {
		this.inflater = LayoutInflater.from(context);
		this.invests = invests;
	}

	public int getTab() {
		return tab;
	}

	public void setTab(int tab) {
		this.tab = tab;
	}

	public List<Map<String, Object>> getInvests() {
		return invests;
	}

	public void setInvests(List<Map<String, Object>> invests) {
		this.invests = invests;
	}

	@Override
	public int getCount() {
		return (invests == null) ? 0 : invests.size();
	}

	@Override
	public Object getItem(int position) {
		return invests.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Log.i(TAG, "position: " + position);

		ViewHolder holder;
		if (convertView == null) {
			if (tab == 1) {
				convertView = inflater.inflate(R.layout.invest_item_ca1, null);
			} else if (tab == 2) {
				convertView = inflater.inflate(R.layout.invest_item_ca2, null);
			} else if (tab == 3) {
				convertView = inflater.inflate(R.layout.invest_item_ca3, null);
			}
			holder = new ViewHolder();
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.tvItemNo = (TextView) convertView.findViewById(R.id.tv_item_no);
		holder.tvItemShowName = (TextView) convertView
				.findViewById(R.id.tv_item_show_name);
		holder.tvMoneyRate = (TextView) convertView
				.findViewById(R.id.tv_money_rate);
		holder.tvType = (TextView) convertView.findViewById(R.id.tv_type);
		holder.tvDate2 = (TextView) convertView.findViewById(R.id.tv_date2);
		holder.btnStatus = (TextView) convertView.findViewById(R.id.btn_status);

		holder.tvItemNo.setText(invests.get(position).get("itemNo").toString());
		holder.tvItemShowName.setText(invests.get(position).get("itemShowName")
				.toString());

		int sType = (Integer) invests.get(position).get("sType");
		int lastSType = (Integer) invests.get(position).get("lastSType");
		int pType = (Integer) invests.get(position).get("pType");
		int oriType = (Integer) invests.get(position).get("oriType");

		if (tab == 1) {
			holder.tvDate1 = (TextView) convertView.findViewById(R.id.tv_date1);
			holder.tvAmt = (TextView) convertView.findViewById(R.id.tv_amt);

			if (((BigDecimal) invests.get(position).get("amt"))
					.compareTo(BigDecimal.valueOf(10000)) >= 0) {
				holder.tvAmt.setText(ViewUtils.mformat
						.format(((BigDecimal) invests.get(position).get("amt"))
								.divide(BigDecimal.valueOf(10000)))
						+ "万元");
			} else {
				holder.tvAmt.setText(ViewUtils.mformat.format(invests.get(
						position).get("amt"))
						+ "元");
			}

			if (pType == 3 || pType == 4) {
				if (((BigDecimal) invests.get(position).get("curRate"))
						.compareTo(BigDecimal.valueOf(24)) > 0) {
					holder.tvMoneyRate.setText("当前大于24%");
				} else {
					holder.tvMoneyRate.setText("当前"
							+ ViewUtils.mformat.format(invests.get(position)
									.get("curRate")) + "%");
				}
			} else if (pType == 1) {
				holder.tvMoneyRate.setText(ViewUtils.mformat.format(invests
						.get(position).get("moneyRate")) + "%");
			}

			holder.tvDate1.setText(ViewUtils.formatter.format(NumberUtils
					.toLong(invests.get(position).get("investTime").toString(),
							0)));
			holder.tvDate2.setText(ViewUtils.formatter.format(NumberUtils
					.toLong(invests.get(position).get("repayCapitalDate")
							.toString(), 0)));

		} else if (tab == 2) {

			holder.tvLeftAssignDays = (TextView) convertView
					.findViewById(R.id.tv_assign_days_left);
			holder.tvAssignAmt = (TextView) convertView
					.findViewById(R.id.tv_assign_amt);
			holder.tvUnpaid = (TextView) convertView
					.findViewById(R.id.tv_unpaid);

			if (pType == 3 || pType == 4) {
				holder.tvMoneyRate.setText("当前"
						+ ViewUtils.mformat.format(invests.get(position).get(
								"curRate")) + "%");
			} else if (pType == 1) {
				holder.tvMoneyRate.setText("折算"
						+ ViewUtils.mformat.format(invests.get(position).get(
								"assignRate")) + "%");
			}

			holder.tvDate2.setText(ViewUtils.formatter.format((Long) invests
					.get(position).get("applyTime")));
			holder.tvLeftAssignDays.setText(invests.get(position)
					.get("assignDaysLeft").toString()
					+ "天");

			if (((BigDecimal) invests.get(position).get("assignAmt"))
					.compareTo(BigDecimal.valueOf(10000)) >= 0) {
				holder.tvAssignAmt
						.setText(ViewUtils.mformat.format(((BigDecimal) invests
								.get(position).get("assignAmt"))
								.divide(BigDecimal.valueOf(10000)))
								+ "万元");
			} else {
				holder.tvAssignAmt.setText(ViewUtils.mformat.format(invests
						.get(position).get("assignAmt")) + "元");
			}

			if (((BigDecimal) invests.get(position).get("unpaidAmt"))
					.compareTo(BigDecimal.valueOf(10000)) >= 0) {
				holder.tvUnpaid
						.setText(ViewUtils.mformat.format(((BigDecimal) invests
								.get(position).get("unpaidAmt"))
								.divide(BigDecimal.valueOf(10000)))
								+ "万元");
			} else {
				holder.tvUnpaid.setText(ViewUtils.mformat.format(invests.get(
						position).get("unpaidAmt"))
						+ "元");
			}

		} else if (tab == 3) {

			holder.tvAssignAmt = (TextView) convertView
					.findViewById(R.id.tv_assign_amt);
			holder.tvFee = (TextView) convertView.findViewById(R.id.tv_fee);
			holder.tvRealGain = (TextView) convertView
					.findViewById(R.id.tv_real_gain);

			if (pType == 3 || pType == 4) {
				holder.tvMoneyRate.setText(ViewUtils.mformat.format(invests
						.get(position).get("curRate")) + "%");
			} else if (pType == 1) {
				holder.tvMoneyRate.setText(ViewUtils.mformat.format(invests
						.get(position).get("assignRate")) + "%");
			}

			holder.tvDate2.setText(ViewUtils.formatter.format((Long) invests
					.get(position).get("transactionDate")));

			if (((BigDecimal) invests.get(position).get("assignAmt"))
					.compareTo(BigDecimal.valueOf(10000)) >= 0) {
				holder.tvAssignAmt
						.setText(ViewUtils.mformat.format(((BigDecimal) invests
								.get(position).get("assignAmt"))
								.divide(BigDecimal.valueOf(10000)))
								+ "万元");
			} else {
				holder.tvAssignAmt.setText(ViewUtils.mformat.format(invests
						.get(position).get("assignAmt")) + "元");
			}

			if (((BigDecimal) invests.get(position).get("fee"))
					.compareTo(BigDecimal.valueOf(10000)) >= 0) {
				holder.tvFee.setText(ViewUtils.mformat
						.format(((BigDecimal) invests.get(position).get("fee"))
								.divide(BigDecimal.valueOf(10000)))
						+ "万元");
			} else {
				holder.tvFee.setText(ViewUtils.mformat.format(invests.get(
						position).get("fee"))
						+ "元");
			}

			if (((BigDecimal) invests.get(position).get("transactionAmt"))
					.subtract((BigDecimal) invests.get(position).get("fee"))
					.compareTo(BigDecimal.valueOf(10000)) >= 0) {
				holder.tvRealGain.setText(ViewUtils.mformat
						.format(((BigDecimal) invests.get(position).get(
								"transactionAmt")).subtract(
								(BigDecimal) invests.get(position).get("fee"))
								.divide(BigDecimal.valueOf(10000)))
						+ "万元");
			} else {
				holder.tvRealGain.setText(ViewUtils.mformat
						.format(((BigDecimal) invests.get(position).get(
								"transactionAmt"))
								.subtract((BigDecimal) invests.get(position)
										.get("fee")))
						+ "元");
			}
		}

		if (sType == 2 || lastSType == 2) {
			holder.tvType.setText("转");
			holder.tvType
					.setBackgroundResource(R.drawable.round_corner_light_blue);
		} else if (pType == 3 || pType == 4) {
			holder.tvType.setText("宝");
			holder.tvType
					.setBackgroundResource(R.drawable.round_corner_dark_red);
		} else if ((Integer) invests.get(position).get("flags") == 32) {
			holder.tvType.setText("员");
			holder.tvType.setBackgroundResource(R.drawable.round_corner_orange);
		} else if (oriType == 1) {
			holder.tvType.setText("工");
			holder.tvType.setBackgroundResource(R.drawable.round_corner_blue);
		} else if (oriType == 7) {
			holder.tvType.setText("供");
			holder.tvType.setBackgroundResource(R.drawable.round_corner_blue);
		} else if (oriType == 8) {
			holder.tvType.setText("分");
			holder.tvType.setBackgroundResource(R.drawable.round_corner_blue);
		} else if (oriType == 9) {
			holder.tvType.setText("个");
			holder.tvType.setBackgroundResource(R.drawable.round_corner_blue);
		} else if (oriType == 10) {
			holder.tvType.setText("企");
			holder.tvType.setBackgroundResource(R.drawable.round_corner_blue);
		}

		convertView.setTag(holder);// 绑定ViewHolder对象
		return convertView;
	}

	public class ViewHolder {
		public TextView tvItemNo;
		public TextView tvItemShowName;
		public TextView tvAmt;
		public TextView tvMoneyRate;
		public TextView tvType;
		public TextView tvDate1;
		public TextView tvDate1name;
		public TextView tvDate2;
		public TextView tvDate2name;
		public TextView btnStatus;
		public TextView tvLeftAssignDays;
		public TextView tvUnpaid;
		public TextView tvAssignAmt;
		public TextView tvFee;
		public TextView tvRealGain;
	}

}
