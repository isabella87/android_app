package com.banhuitong.adapter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.banhuitong.activity.R;
import com.banhuitong.enumerate.BonusType;
import com.banhuitong.util.ViewUtils;

public class InvestEntRepayListAdapter extends BaseAdapter {

	private static final String TAG = "InvestEntRepayListAdapter";

	private LayoutInflater inflater;
	List<Map<String, Object>> bonusList;
	long assignTime;
	int tab;

	public InvestEntRepayListAdapter(Context context,
			List<Map<String, Object>> bonusList, long assignTime, int tab) {
		this.inflater = LayoutInflater.from(context);
		this.bonusList = bonusList;
		this.assignTime = assignTime;
		this.tab = tab;
	}

	public List<Map<String, Object>> getBonusList() {
		return bonusList;
	}

	public void setBonusList(List<Map<String, Object>> bonusList) {
		this.bonusList = bonusList;
	}

	@Override
	public int getCount() {
		return (bonusList == null) ? 0 : bonusList.size();
	}

	@Override
	public Object getItem(int position) {
		return bonusList.get(position);
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
			convertView = inflater.inflate(R.layout.repay_item, null);
			holder = new ViewHolder();
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.tvTranNo = (TextView) convertView.findViewById(R.id.tv_tran_no);
		holder.tvTranType = (TextView) convertView
				.findViewById(R.id.tv_tran_type);
		holder.tvStatus = (TextView) convertView.findViewById(R.id.tv_status);
		holder.tvDueTime = (TextView) convertView
				.findViewById(R.id.tv_due_time);
		holder.tvAmt = (TextView) convertView.findViewById(R.id.tv_amt);
		holder.tvPaidTime = (TextView) convertView
				.findViewById(R.id.tv_paid_time);
		holder.tvPaidAmt = (TextView) convertView
				.findViewById(R.id.tv_paid_amt);

		holder.tvTranNo.setText(bonusList.get(position).get("tranNo") + "/"
				+ bonusList.get(position).get("totalTranNo"));

		if (((Long) bonusList.get(position).get("tranType")) == BonusType.INTEREST
				.value()) {
			holder.tvTranType.setText("利息");
		} else if (((Long) bonusList.get(position).get("tranType")) == BonusType.PRINCIPAL
				.value()) {
			holder.tvTranType.setText("本金");
		}

		if (((Long) bonusList.get(position).get("status")) == 0) {
			if(tab==3){
				holder.tvStatus.setText("提前还本");
				holder.tvStatus.setTextColor(Color.GRAY);
				holder.tvTranNo.setTextColor(Color.GRAY);
				holder.tvTranType.setTextColor(Color.GRAY);
				holder.tvDueTime.setTextColor(Color.GRAY);
				holder.tvAmt.setTextColor(Color.GRAY);
				holder.tvAmt.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG );
				holder.tvPaidTime.setTextColor(Color.GRAY);
				holder.tvPaidAmt.setTextColor(Color.GRAY);
				holder.tvPaidAmt.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG );
			}else{
				holder.tvStatus.setText("待还");
			}
		} else if (((Long) bonusList.get(position).get("status")) == 1) {
			if(NumberUtils.toLong(
					bonusList.get(position).get("paidTime").toString(), 0)<assignTime){
				holder.tvStatus.setText("已还");
				holder.tvStatus.setTextColor(Color.GRAY);
				holder.tvTranNo.setTextColor(Color.GRAY);
				holder.tvTranType.setTextColor(Color.GRAY);
				holder.tvDueTime.setTextColor(Color.GRAY);
				holder.tvAmt.setTextColor(Color.GRAY);
				holder.tvAmt.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG );
				holder.tvPaidTime.setTextColor(Color.GRAY);
				holder.tvPaidAmt.setTextColor(Color.GRAY);
				holder.tvPaidAmt.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG );
			}else{
				holder.tvStatus.setText("已还");
			}		
		}

		if(NumberUtils.toLong(bonusList.get(position).get("dueTime").toString(), 0)==0){
			holder.tvDueTime.setText("");
		}else{
			holder.tvDueTime.setText(ViewUtils.formatter.format(NumberUtils.toLong(bonusList.get(position).get("dueTime").toString(), 0)));
		}
			
		if (((BigDecimal) bonusList.get(position).get("amt")).compareTo(BigDecimal.valueOf(10000)) >= 0) {
			holder.tvAmt.setText(ViewUtils.mformat.format(((BigDecimal) bonusList.get(position).get("amt")).divide(BigDecimal.valueOf(10000)))+ "万元");
		} else {
			holder.tvAmt.setText(ViewUtils.mformat.format(bonusList.get(position).get("amt"))+ "元");
		}

		if(NumberUtils.toLong(bonusList.get(position).get("paidTime").toString(), 0)==0){
			holder.tvPaidTime.setText("");
		}else{
			holder.tvPaidTime.setText(ViewUtils.formatter.format(NumberUtils.toLong(bonusList.get(position).get("paidTime").toString(), 0)));
		}
		
		if (((BigDecimal) bonusList.get(position).get("paidAmt")).compareTo(BigDecimal.valueOf(0))==1){
			
			if (((BigDecimal) bonusList.get(position).get("paidAmt")).compareTo(BigDecimal.valueOf(10000)) >= 0) {
				holder.tvPaidAmt.setText(ViewUtils.mformat.format(((BigDecimal) bonusList.get(position).get("paidAmt")).divide(BigDecimal.valueOf(10000)))+ "万元");
			} else {
				holder.tvPaidAmt.setText(ViewUtils.mformat.format(bonusList.get(position).get("paidAmt"))+ "元");
			}
		}else{
			holder.tvPaidAmt.setText("");
		}
		
		convertView.setTag(holder);// 绑定ViewHolder对象

		return convertView;
	}

	public class ViewHolder {
		public TextView tvTranNo;
		public TextView tvTranType;
		public TextView tvStatus;
		public TextView tvDueTime;
		public TextView tvAmt;
		public TextView tvPaidTime;
		public TextView tvPaidAmt;
	}

}
