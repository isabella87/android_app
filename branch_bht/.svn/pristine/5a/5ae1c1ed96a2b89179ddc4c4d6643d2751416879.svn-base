package com.banhuitong.adapter;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.banhuitong.activity.R;
import com.banhuitong.util.ActivityUtils;
import com.banhuitong.util.ViewUtils;

public class CaAdapter extends BaseAdapter {
	
	private static final String TAG = "CaAdapter" ;  
	
	private LayoutInflater inflater;
	private List<Map<String,Object>> cas;

	public CaAdapter(Context context, List<Map<String,Object>> cas) {
	    this.inflater = LayoutInflater.from(context);
	    this.cas = cas;
	}

	public List<Map<String,Object>> getCas() {
		return cas;
	}

	public void setCas(List<Map<String,Object>> cas) {
		this.cas = cas;
	}

	@Override
	public int getCount() {
		return (cas==null)?0:cas.size();  
	}

	@Override
	public Object getItem(int position) {
		return cas.get(position);  
	}

	@Override
	public long getItemId(int position) {
		 return position;  
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
//		Log.i(TAG, "position: " + position);
		
		ViewHolder holder;
        if (convertView == null) {
             convertView = inflater.inflate(R.layout.ca_item,null);
             holder = new ViewHolder();
        }else{
        	holder = (ViewHolder) convertView.getTag();
        }
        
         holder.tvItemNo = (TextView) convertView.findViewById(R.id.tv_item_no);
	     holder.tvItemShowName = (TextView) convertView.findViewById(R.id.tv_item_show_name);
	     holder.tvAssignRate = (TextView) convertView.findViewById(R.id.tv_assign_rate);
	     holder.tvAssignAmount = (TextView) convertView.findViewById(R.id.tv_credit_amt);
	     holder.tvType = (TextView) convertView.findViewById(R.id.tv_type);
	     holder.tvCountDown = (TextView) convertView.findViewById(R.id.tv_count_down);
	     holder.btnStatus = (TextView) convertView.findViewById(R.id.btn_status);
	     
	     holder.tvItemNo.setText(cas.get(position).get("itemNo").toString());
	     holder.tvItemShowName.setText(cas.get(position).get("itemShowName").toString());
	     holder.tvAssignAmount.setText(ViewUtils.mformat.format(cas.get(position).get("assignAmount")) + "元");
	     
		 if("1".equals(cas.get(position).get("type").toString())){
			 holder.tvType.setText("工");
		     holder.tvType.setBackgroundResource(R.drawable.round_corner_blue);
		     
		    BigDecimal assignRate = BigDecimal.ZERO;
			try {
				BigDecimal creditAmount = new BigDecimal(cas.get(position).get("creditAmount").toString());
				BigDecimal assignAmount = new BigDecimal(cas.get(position).get("assignAmount").toString());
				BigDecimal unpaidAmt = new BigDecimal(cas.get(position).get("unpaidAmt").toString());
				long daysRemaining = NumberUtils.toLong(cas.get(position).get("daysRemaining").toString(),0);
				
				assignRate = ActivityUtils.getAssignRate(creditAmount, assignAmount, unpaidAmt.subtract(creditAmount), new MathContext(16, RoundingMode.HALF_DOWN), daysRemaining);
			} catch (Exception e) {
				e.printStackTrace();
			}
		     
		     holder.tvAssignRate.setText(ViewUtils.mformat.format(assignRate) + "%");
		 }else if("6".equals(cas.get(position).get("type").toString())){
			 holder.tvType.setText("宝");
		     holder.tvType.setBackgroundResource(R.drawable.round_corner_dark_red);
		     
		     if(((BigDecimal) cas.get(position).get("curRate")).compareTo(BigDecimal.valueOf(24))>0){
	    		 holder.tvAssignRate.setText( "大于24%");
		     }else{
		    	 holder.tvAssignRate.setText(ViewUtils.mformat.format(cas.get(position).get("curRate")) + "%");
		     }
		 }else if("7".equals(cas.get(position).get("type").toString())){
			 holder.tvType.setText("供");
			 holder.tvType.setBackgroundResource(R.drawable.round_corner_blue);
			 
			 BigDecimal assignRate = BigDecimal.ZERO;
				try {
					BigDecimal creditAmount = new BigDecimal(cas.get(position).get("creditAmount").toString());
					BigDecimal assignAmount = new BigDecimal(cas.get(position).get("assignAmount").toString());
					BigDecimal unpaidAmt = new BigDecimal(cas.get(position).get("unpaidAmt").toString());
					long daysRemaining = NumberUtils.toLong(cas.get(position).get("daysRemaining").toString(),0);
					
					assignRate = ActivityUtils.getAssignRate(creditAmount, assignAmount, unpaidAmt.subtract(creditAmount), new MathContext(16, RoundingMode.HALF_DOWN), daysRemaining);
				} catch (Exception e) {
					e.printStackTrace();
				}
			     
			     holder.tvAssignRate.setText(ViewUtils.mformat.format(assignRate) + "%");
		 }else if("8".equals(cas.get(position).get("type").toString())){
			 holder.tvType.setText("分");
			 holder.tvType.setBackgroundResource(R.drawable.round_corner_blue);
			 
			 BigDecimal assignRate = BigDecimal.ZERO;
				try {
					BigDecimal creditAmount = new BigDecimal(cas.get(position).get("creditAmount").toString());
					BigDecimal assignAmount = new BigDecimal(cas.get(position).get("assignAmount").toString());
					BigDecimal unpaidAmt = new BigDecimal(cas.get(position).get("unpaidAmt").toString());
					long daysRemaining = NumberUtils.toLong(cas.get(position).get("daysRemaining").toString(),0);
					
					assignRate = ActivityUtils.getAssignRate(creditAmount, assignAmount, unpaidAmt.subtract(creditAmount), new MathContext(16, RoundingMode.HALF_DOWN), daysRemaining);
				} catch (Exception e) {
					e.printStackTrace();
				}
			     
			     holder.tvAssignRate.setText(ViewUtils.mformat.format(assignRate) + "%");
		 }else if("9".equals(cas.get(position).get("type").toString())){
			 holder.tvType.setText("个");
			 holder.tvType.setBackgroundResource(R.drawable.round_corner_blue);
			 
			 BigDecimal assignRate = BigDecimal.ZERO;
				try {
					BigDecimal creditAmount = new BigDecimal(cas.get(position).get("creditAmount").toString());
					BigDecimal assignAmount = new BigDecimal(cas.get(position).get("assignAmount").toString());
					BigDecimal unpaidAmt = new BigDecimal(cas.get(position).get("unpaidAmt").toString());
					long daysRemaining = NumberUtils.toLong(cas.get(position).get("daysRemaining").toString(),0);
					
					assignRate = ActivityUtils.getAssignRate(creditAmount, assignAmount, unpaidAmt.subtract(creditAmount), new MathContext(16, RoundingMode.HALF_DOWN), daysRemaining);
				} catch (Exception e) {
					e.printStackTrace();
				}
			     
			     holder.tvAssignRate.setText(ViewUtils.mformat.format(assignRate) + "%");
		 }else if("10".equals(cas.get(position).get("type").toString())){
			 holder.tvType.setText("企");
			 holder.tvType.setBackgroundResource(R.drawable.round_corner_blue);
			 
			 BigDecimal assignRate = BigDecimal.ZERO;
				try {
					BigDecimal creditAmount = new BigDecimal(cas.get(position).get("creditAmount").toString());
					BigDecimal assignAmount = new BigDecimal(cas.get(position).get("assignAmount").toString());
					BigDecimal unpaidAmt = new BigDecimal(cas.get(position).get("unpaidAmt").toString());
					long daysRemaining = NumberUtils.toLong(cas.get(position).get("daysRemaining").toString(),0);
					
					assignRate = ActivityUtils.getAssignRate(creditAmount, assignAmount, unpaidAmt.subtract(creditAmount), new MathContext(16, RoundingMode.HALF_DOWN), daysRemaining);
				} catch (Exception e) {
					e.printStackTrace();
				}
			     
			     holder.tvAssignRate.setText(ViewUtils.mformat.format(assignRate) + "%");
		 }
		     
		 holder.btnStatus.setText("认购");
	 	 holder.btnStatus.setBackgroundResource(R.drawable.round_corner_dark_red);
	 	 
	 	 holder.tvCountDown.setTextColor(Color.rgb(255, 0, 0));
		 Date today = new Date();
		 long dayDiff = (((Long)cas.get(position).get("createTime")) + ((Long)cas.get(position).get("assignDays")) * 1000 * 24 * 60 * 60 - today.getTime()) / (1000 * 24 * 60 * 60);
		 if(dayDiff<0){
			 holder.tvCountDown.setText("已截止");
		 }else{
			 long daysRemaining = dayDiff>0?dayDiff:1;
			 holder.tvCountDown.setText(daysRemaining + "天");
			 holder.tvCountDown.setTextColor(Color.rgb(0, 139, 69));
		 }
	     
	     convertView.setTag(holder);//绑定ViewHolder对象 
	     
        return convertView;
	}
		
	public class ViewHolder {
	    public TextView tvItemNo;
	    public TextView tvItemShowName;
	    public TextView tvAssignRate;
	    public TextView tvAssignAmount;
	    public TextView tvCountDown;
	    public TextView btnStatus;
	    public TextView tvType;
	}
	
}
