package com.banhuitong.adapter;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.banhuitong.activity.MyApplication;
import com.banhuitong.activity.R;
import com.banhuitong.enumerate.EntStatusType;
import com.banhuitong.util.ViewUtils;
import com.banhuitong.view.RoundProgressView;

@SuppressLint("NewApi")
public class EntAdapter extends BaseAdapter {

	private static final String TAG = "EntAdapter";

	private LayoutInflater inflater;
	private List<Map<String, Object>> ents;

	public EntAdapter(Context context, List<Map<String, Object>> ents) {
		this.inflater = LayoutInflater.from(context);
		this.ents = ents;
	}

	public List<Map<String, Object>> getEnts() {
		return ents;
	}

	public void setEnts(List<Map<String, Object>> ents) {
		this.ents = ents;
	}

	@Override
	public int getCount() {
		return (ents == null) ? 0 : ents.size();
	}

	@Override
	public Object getItem(int position) {
		return ents.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		// Log.i(TAG, "position: " + position);

		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.ent_item, null);
			holder = new ViewHolder();
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.tvItemNo = (TextView) convertView.findViewById(R.id.tv_item_no);
		holder.tvItemShowName = (TextView) convertView
				.findViewById(R.id.tv_item_show_name);
		holder.tvAmt = (TextView) convertView.findViewById(R.id.tv_amt);
		holder.tvMoneyRate = (TextView) convertView
				.findViewById(R.id.tv_money_rate);
		holder.tvBorrowDays = (TextView) convertView
				.findViewById(R.id.tv_borrow_days);
		holder.tvType = (TextView) convertView.findViewById(R.id.tv_type);
		holder.tvCountDown = (TextView) convertView
				.findViewById(R.id.tv_count_down);
		holder.vProgress = (RoundProgressView) convertView
				.findViewById(R.id.v_round_progress);
		holder.btnStatus = (TextView) convertView.findViewById(R.id.btn_status);
		holder.imgSeal = (ImageView) convertView.findViewById(R.id.img_seal);

		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				MyApplication.screen_w / 8, MyApplication.screen_h / 14);
		holder.vProgress.setLayoutParams(layoutParams);

		holder.tvItemNo.setText(ents.get(position).get("itemNo").toString());
		holder.tvItemShowName.setText(ents.get(position).get("itemShowName")
				.toString());

		if (((BigDecimal) ents.get(position).get("amt")).compareTo(BigDecimal
				.valueOf(10000)) >= 0) {
			holder.tvAmt
					.setText(ViewUtils.mformat.format(((BigDecimal) ents.get(
							position).get("amt")).divide(BigDecimal
							.valueOf(10000))) + "万元");
		} else {
			holder.tvAmt.setText(ViewUtils.mformat.format((BigDecimal) ents
					.get(position).get("amt")) + "元");
		}

		holder.tvMoneyRate.setText(ViewUtils.mformat.format(((BigDecimal) ents
				.get(position).get("moneyRate"))) + "%");
		holder.tvBorrowDays.setText((Long) ents.get(position).get("borrowDays")
				+ "天");
		
		if ((Long) ents.get(position).get("flags") == 1) {
			holder.tvType.setText("新");
			holder.tvType.setBackgroundResource(R.drawable.round_corner_green);
			holder.imgSeal.setBackgroundResource(R.drawable.seal);
		} else if ((Long) ents.get(position).get("flags") == 32) {
			holder.tvType.setText("员");
			holder.tvType.setBackgroundResource(R.drawable.round_corner_orange);
			holder.imgSeal.setBackground(null);
		} else {
			if ((Long) ents.get(position).get("type") == 1) {
				holder.tvType.setText("工");
				holder.tvType
						.setBackgroundResource(R.drawable.round_corner_blue);
			}else if((Long) ents.get(position).get("type") == 7){
				holder.tvType.setText("供");
				holder.tvType
						.setBackgroundResource(R.drawable.round_corner_blue);
				
			}else if((Long) ents.get(position).get("type") == 8){
				holder.tvType.setText("分");
				holder.tvType
						.setBackgroundResource(R.drawable.round_corner_blue);
				
			}else if((Long) ents.get(position).get("type") == 9){
				holder.tvType.setText("个");
				holder.tvType
						.setBackgroundResource(R.drawable.round_corner_blue);
			}else if((Long) ents.get(position).get("type") == 10){
				holder.tvType.setText("企");
				holder.tvType
						.setBackgroundResource(R.drawable.round_corner_blue);
			}
			if ((Long) ents.get(position).get("flags") == 16) {
				holder.imgSeal.setBackgroundResource(R.drawable.seal_prior);
			} else {
				holder.imgSeal.setBackground(null);
				
				if(ents.get(position).get("waterMark").toString().equals("guoqing_biao")){
					holder.imgSeal.setBackgroundResource(R.drawable.seal_guoqing);
				}
			}
		}
		
		int progress = ((BigDecimal) ents.get(position).get("investedMoney"))
				.movePointRight(2)
				.divide((BigDecimal) ents.get(position).get("amt"),
						new MathContext(16, RoundingMode.HALF_DOWN)).intValue();
		holder.vProgress.setProgress(progress);

		long status = (Long) ents.get(position).get("status");

		if (status >= 1 && status <= 30) {
			holder.btnStatus.setText("即将发布");
			holder.btnStatus
					.setBackgroundResource(R.drawable.round_corner_light_blue);
		} else if (status == 40) {
			holder.btnStatus.setText("我要出借");
			holder.btnStatus
					.setBackgroundResource(R.drawable.round_corner_dark_red);
		} else if (status == 50 || status == 60 || status == 70) {
			holder.btnStatus.setText("满标");
			holder.btnStatus
					.setBackgroundResource(R.drawable.round_corner_grey);
		} else if (status == 80 || status == 90) {
			holder.btnStatus.setText("还款中");
			holder.btnStatus
					.setBackgroundResource(R.drawable.round_corner_grey);
		} else if (status == 999) {
			holder.btnStatus.setText("已结清");
			holder.btnStatus
					.setBackgroundResource(R.drawable.round_corner_grey);
		} else if (status == -1) {
			holder.btnStatus.setText("流标");
			holder.btnStatus
					.setBackgroundResource(R.drawable.round_corner_grey);
		}

		holder.tvCountDown.setTextColor(Color.rgb(255, 0, 0));
		if (status == EntStatusType.RAISING.value()) {
			Date financingEndTime = (Date) ents.get(position).get(
					"financingEndTime");
			Date today = new Date();
			long dayDiff = (financingEndTime.getTime() - today.getTime())
					/ (1000 * 24 * 60 * 60);
			if (dayDiff < 0) {
				holder.tvCountDown.setText("已截止");
			} else {
				long daysRemaining = dayDiff > 0 ? dayDiff : 1;
				holder.tvCountDown.setText(daysRemaining + "天");
				holder.tvCountDown.setTextColor(Color.rgb(0, 139, 69));
			}
		} else {
			holder.tvCountDown.setText("");
		}

		convertView.setTag(holder);// 绑定ViewHolder对象

		return convertView;
	}

	public class ViewHolder {
		public TextView tvItemNo;
		public TextView tvItemShowName;
		public TextView tvAmt;
		public TextView tvMoneyRate;
		public TextView tvBorrowDays;
		public TextView tvType;
		public TextView tvCountDown;
		public RoundProgressView vProgress;
		public TextView btnStatus;
		public ImageView imgSeal;
	}

}
