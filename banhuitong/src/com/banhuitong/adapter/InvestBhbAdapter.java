package com.banhuitong.adapter;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.banhuitong.activity.R;
import com.banhuitong.util.ViewUtils;

public class InvestBhbAdapter extends BaseAdapter {
	
	private static final String TAG = "InvestBhbAdapter" ;  
	
	private LayoutInflater inflater;
	private List<Map<String,Object>> invests;
	private int tab;

	public InvestBhbAdapter(Context context, List<Map<String,Object>> invests) {
	    this.inflater = LayoutInflater.from(context);
	    this.invests = invests;
	}
	
	public int getTab() {
		return tab;
	}

	public void setTab(int tab) {
		this.tab = tab;
	}

	public List<Map<String,Object>> getInvests() {
		return invests;
	}

	public void setInvests(List<Map<String,Object>> invests) {
		this.invests = invests;
	}

	@Override
	public int getCount() {
		return (invests==null)?0:invests.size();  
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
        	if(tab==1){
        		convertView = inflater.inflate(R.layout.invest_item_bhb1,null);
        	}else if(tab==2){
        		convertView = inflater.inflate(R.layout.invest_item_bhb2,null);
        	}else if(tab==3){
        		convertView = inflater.inflate(R.layout.invest_item_bhb3,null);
        	}
            holder = new ViewHolder();
        }else{
        	holder = (ViewHolder) convertView.getTag();
        }
        
         holder.tvItemNo = (TextView) convertView.findViewById(R.id.tv_item_no);
	     holder.tvItemShowName = (TextView) convertView.findViewById(R.id.tv_item_show_name);
	     holder.tvMoneyRate = (TextView) convertView.findViewById(R.id.tv_money_rate);
	     holder.tvType = (TextView) convertView.findViewById(R.id.tv_type);
	     holder.tvDate1 = (TextView) convertView.findViewById(R.id.tv_date1);
	     holder.tvDate2 = (TextView) convertView.findViewById(R.id.tv_date2);
	     holder.tvDate1name = (TextView) convertView.findViewById(R.id.tv_date1name);
	     holder.tvDate2name = (TextView) convertView.findViewById(R.id.tv_date2name);
	     holder.btnStatus = (TextView) convertView.findViewById(R.id.btn_status);
	     holder.tvInvestType = (TextView) convertView.findViewById(R.id.tv_invest_type);
	     
	     holder.tvItemNo.setText(invests.get(position).get("itemNo").toString());
	     holder.tvItemShowName.setText(invests.get(position).get("itemShowName").toString());
	     
	     if(NumberUtils.toLong(invests.get(position).get("type").toString(),0)==3){
	    	 holder.tvInvestType.setText("普通资金");
	     }else if(NumberUtils.toLong(invests.get(position).get("type").toString(),0)==4){
	    	 holder.tvInvestType.setText("第三方担保资金");
	     }
	    
	     if(tab==1){
	    	 holder.tvAmt = (TextView) convertView.findViewById(R.id.tv_amt);
	    	 
//	    	 if(((BigDecimal) invests.get(position).get("amt")).compareTo(BigDecimal.valueOf(10000))>=0){
//		    	 holder.tvAmt.setText(ViewUtils.mformat.format(((BigDecimal) invests.get(position).get("amt")).divide(BigDecimal.valueOf(10000))) + "万元");
//		     }else{
		    	 holder.tvAmt.setText(ViewUtils.mformat.format(invests.get(position).get("amt")) + "元");
//		     }
	    	 
	    	 if(((BigDecimal) invests.get(position).get("dueRate")).compareTo(BigDecimal.valueOf(24))>0){
	    		 holder.tvMoneyRate.setText( "大于24%");
		     }else{
		    	 holder.tvMoneyRate.setText(ViewUtils.mformat.format(invests.get(position).get("dueRate")) + "%");
		     }
	    	 
	    	 holder.tvDate1.setText(ViewUtils.formatter.format(NumberUtils.toLong(invests.get(position).get("tenderTime").toString(),0)));
	    	 holder.tvDate2.setText(ViewUtils.formatter.format(NumberUtils.toLong(invests.get(position).get("endTime").toString(),0)));
	    	 
	     }else if(tab==2){
	    	 
	    	 holder.tvAmt = (TextView) convertView.findViewById(R.id.tv_amt);
	    	 holder.tvCurAmt = (TextView) convertView.findViewById(R.id.tv_cur_amt);
	    	 
//	    	 if(((BigDecimal) invests.get(position).get("amt")).compareTo(BigDecimal.valueOf(10000))>=0){
//		    	 holder.tvAmt.setText(ViewUtils.mformat.format(((BigDecimal) invests.get(position).get("amt")).divide(BigDecimal.valueOf(10000))) + "万元");
//		     }else{
		    	 holder.tvAmt.setText(ViewUtils.mformat.format(invests.get(position).get("amt")) + "元");
//		     }
	    	 
			 Date today = DateUtils.truncate(new Date(),Calendar.DAY_OF_MONTH);
			 long dayDiff = (today.getTime() - (Long)invests.get(position).get("expectedBorrowTime")) / (1000 * 24 * 60 * 60);
			 
			 if(dayDiff>30){
				 if(((BigDecimal) invests.get(position).get("curRate")).compareTo(BigDecimal.valueOf(24))>0){
		    		 holder.tvMoneyRate.setText("大于24%");
			     }else if(((BigDecimal) invests.get(position).get("curRate")).compareTo(BigDecimal.valueOf(0))<0){
			    	 holder.tvMoneyRate.setText("0%");
			     }else{
			    	 holder.tvMoneyRate.setText(ViewUtils.mformat.format(invests.get(position).get("curRate")) + "%");
			     }
				 
				 holder.tvCurAmt.setText(ViewUtils.mformat.format(invests.get(position).get("curAmt")) + "元");
			 }else{
				 holder.tvMoneyRate.setText("--%");
				 holder.tvCurAmt.setText("--");
			 }
			 
			 holder.tvDate1.setText(ViewUtils.formatter.format(NumberUtils.toLong(invests.get(position).get("tenderTime").toString(),0)));
	    	 holder.tvDate2.setText(ViewUtils.formatter.format(NumberUtils.toLong(invests.get(position).get("endTime").toString(),0)));
	    	 
	     }else if(tab==3){
	    	 
	    	 holder.tvDate2 = (TextView) convertView.findViewById(R.id.tv_date2);
	    	 holder.tvHolddays = (TextView) convertView.findViewById(R.id.tv_holddays);
	    	 holder.tvRealBonus = (TextView) convertView.findViewById(R.id.tv_real_bonus);
	    	 
	    	 if(((BigDecimal) invests.get(position).get("realRate")).compareTo(BigDecimal.valueOf(24))>0){
	    		 holder.tvMoneyRate.setText("大于24%");
		     }else{
		    	 holder.tvMoneyRate.setText(ViewUtils.mformat.format(invests.get(position).get("realRate")) + "%");
		     }
	    	 
	    	 holder.tvRealBonus.setText(ViewUtils.mformat.format(invests.get(position).get("realBonusAmt")) + "元");
	    	 holder.tvHolddays.setText(invests.get(position).get("holdDays").toString());
	    	 holder.tvDate2.setText(ViewUtils.formatter.format(NumberUtils.toLong(invests.get(position).get("unHoldTime").toString(),0)));
	     }
	    
	     if(NumberUtils.toInt(invests.get(position).get("sType").toString(),0)==2){
	    	holder.tvType.setText("转");
	    	holder.tvType.setBackgroundResource(R.drawable.round_corner_light_blue);
	     }else{
    		 holder.tvType.setText("宝");
    		 holder.tvType.setBackgroundResource(R.drawable.round_corner_blue);
	     }
	     
	     convertView.setTag(holder);//绑定ViewHolder对象 
	     
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
	    public TextView tvInvestType;
	    public TextView tvCurAmt;
	    public TextView tvHolddays;
	    public TextView tvRealBonus;
	}
	
}
