package com.banhuitong.adapter;

import java.util.Calendar;
import java.util.Date;
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
import com.banhuitong.enumerate.BonusType;
import com.banhuitong.util.ViewUtils;

public class CollectAdapter extends BaseAdapter {
	
	private static final String TAG = "CollectAdapter" ;  
	
	private LayoutInflater inflater;
	private List<Map<String,Object>> collects;

	public CollectAdapter(Context context, List<Map<String,Object>> collects) {
	    this.inflater = LayoutInflater.from(context);
	    this.collects = collects;
	}

	public List<Map<String,Object>> getCollects() {
		return collects;
	}

	public void setCollects(List<Map<String,Object>> collects) {
		this.collects = collects;
	}

	@Override
	public int getCount() {
		return (collects==null)?0:collects.size();  
	}

	@Override
	public Object getItem(int position) {
		return collects.get(position);  
	}

	@Override
	public long getItemId(int position) {
		 return position;  
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		Log.i(TAG, "position: " + position);
		
		final ViewHolder holder;
        if (convertView == null) {
             convertView = inflater.inflate(R.layout.collect_item,null);
             holder = new ViewHolder();
        }else{
        	holder = (ViewHolder) convertView.getTag();
        }
        
	     holder.tvDueTime = (TextView) convertView.findViewById(R.id.tv_due_time);
	     holder.tvAmt = (TextView) convertView.findViewById(R.id.tv_amt);
	     holder.tvTranType = (TextView) convertView.findViewById(R.id.tv_tran_type);
	     holder.tvItemShowName = (TextView) convertView.findViewById(R.id.tv_item_show_name);
	     
	     holder.tvItemShowName.setText(collects.get(position).get("itemShowName").toString());
	     holder.tvTranType.setText(BonusType.name(NumberUtils.toInt(collects.get(position).get("tranType").toString(),0)));
	     holder.tvAmt.setText(ViewUtils.mformat.format(NumberUtils.toDouble(collects.get(position).get("amt").toString(),0)) + "元");
	     
	     boolean omitDueTime = false;
	     if(position>0){
	    	 Date dt = new Date(NumberUtils.toLong(collects.get(position).get("dueTime").toString(),0));
	         Calendar cal = Calendar.getInstance();
	         cal.setTime(dt);
	         
	         Date dtPre = new Date(NumberUtils.toLong(collects.get(position-1).get("dueTime").toString(),0));
	         Calendar calPre = Calendar.getInstance();
	         calPre.setTime(dtPre);
	         
	         if(cal.get(Calendar.YEAR)==calPre.get(Calendar.YEAR)
	                && cal.get(Calendar.MONTH)==calPre.get(Calendar.MONTH)
	                && cal.get(Calendar.DATE)==calPre.get(Calendar.DATE)){
	        	 omitDueTime = true;
	         }
	     }
         
	     if(omitDueTime){
	    	 holder.tvDueTime.setText("");
	     }else{
	    	 holder.tvDueTime.setText(ViewUtils.formatter.format(NumberUtils.toLong(collects.get(position).get("dueTime").toString(),0)));
	     }
	    
	     convertView.setTag(holder);//绑定ViewHolder对象 
         return convertView;
	}
		
	public class ViewHolder {
	    public TextView tvDueTime;
	    public TextView tvItemShowName;
	    public TextView tvAmt;
	    public TextView tvTranType;
	}
	
}
