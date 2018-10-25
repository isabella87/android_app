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

public class InvestEntAdapter extends BaseAdapter {
	
	private static final String TAG = "InvestEntAdapter" ;  
	
	private LayoutInflater inflater;
	private List<Map<String,Object>> invests;
	private int tab;

	public InvestEntAdapter(Context context, List<Map<String,Object>> invests) {
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
             convertView = inflater.inflate(R.layout.invest_item_ent,null);
             holder = new ViewHolder();
        }else{
        	holder = (ViewHolder) convertView.getTag();
        }
        
         holder.tvItemNo = (TextView) convertView.findViewById(R.id.tv_item_no);
	     holder.tvItemShowName = (TextView) convertView.findViewById(R.id.tv_item_show_name);
	     holder.tvAmt = (TextView) convertView.findViewById(R.id.tv_amt);
	     holder.tvMoneyRate = (TextView) convertView.findViewById(R.id.tv_money_rate);
	     holder.tvBorrowDays = (TextView) convertView.findViewById(R.id.tv_borrow_days);
	     holder.tvType = (TextView) convertView.findViewById(R.id.tv_type);
	     holder.tvDate2 = (TextView) convertView.findViewById(R.id.tv_date2);
	     holder.tvDate2name = (TextView) convertView.findViewById(R.id.tv_date2name);
	     holder.tvInvestTime = (TextView) convertView.findViewById(R.id.tv_date1);
	     holder.btnStatus = (TextView) convertView.findViewById(R.id.btn_status);
	     
	     holder.tvItemNo.setText(invests.get(position).get("itemNo").toString());
	     holder.tvItemShowName.setText(invests.get(position).get("itemShowName").toString());
	     
	     if(((BigDecimal) invests.get(position).get("amt")).compareTo(BigDecimal.valueOf(10000))>=0){
	    	 holder.tvAmt.setText(ViewUtils.mformat.format(((BigDecimal) invests.get(position).get("amt")).divide(BigDecimal.valueOf(10000))) + "万元");
	     }else{
	    	 holder.tvAmt.setText(ViewUtils.mformat.format(invests.get(position).get("amt")) + "元");
	     }
	    
	     holder.tvMoneyRate.setText(ViewUtils.mformat.format(invests.get(position).get("moneyRate")) + "%");
	     holder.tvBorrowDays.setText(invests.get(position).get("borrowDays") + "天");
	     
	     holder.tvInvestTime.setText(ViewUtils.formatter.format(NumberUtils.toLong(invests.get(position).get("investTime").toString(),0)));
	     
	     if(tab==4){
	    	 holder.tvDate2.setText(ViewUtils.formatter.format(NumberUtils.toLong(invests.get(position).get("cancelDate").toString(),0)));
	    	 holder.tvDate2name.setText("流标日期");
	     }else{
	    	 holder.tvDate2.setText(ViewUtils.formatter.format(NumberUtils.toLong(invests.get(position).get("repayCapitalDate").toString(),0)));
	    	 holder.tvDate2name.setText("还本日期");
	     }
	     
	     if(NumberUtils.toInt(invests.get(position).get("sType").toString(),0)==2){
	    	holder.tvType.setText("转");
	    	holder.tvType.setBackgroundResource(R.drawable.round_corner_light_blue);
	     }else{
	    	 if((Integer)invests.get(position).get("flags")==32){
	    		 holder.tvType.setText("员");
	    		 holder.tvType.setBackgroundResource(R.drawable.round_corner_orange);
	    	 }else if((Integer)invests.get(position).get("oriType")==1 ){
	    		 holder.tvType.setText("工");
	    		 holder.tvType.setBackgroundResource(R.drawable.round_corner_blue);
	    	 }else if((Integer)invests.get(position).get("oriType")==7 ){
	    		 holder.tvType.setText("供");
	    		 holder.tvType.setBackgroundResource(R.drawable.round_corner_blue);
	    	 }else if((Integer)invests.get(position).get("oriType")==8 ){
	    		 holder.tvType.setText("分");
	    		 holder.tvType.setBackgroundResource(R.drawable.round_corner_blue);
	    	 }else if((Integer)invests.get(position).get("oriType")==9 ){
	    		 holder.tvType.setText("个");
	    		 holder.tvType.setBackgroundResource(R.drawable.round_corner_blue);
	    	 }
	     }
	     
	     convertView.setTag(holder);//绑定ViewHolder对象 
	     
        return convertView;
	}
		
	public class ViewHolder {
	    public TextView tvItemNo;
	    public TextView tvItemShowName;
	    public TextView tvAmt;
	    public TextView tvMoneyRate;
	    public TextView tvBorrowDays;
	    public TextView tvType;
	    public TextView tvDate2;
	    public TextView tvDate2name;
	    public TextView tvInvestTime;
	    public TextView btnStatus;
	}
	
}
