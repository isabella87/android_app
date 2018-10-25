package com.banhuitong.adapter;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.banhuitong.activity.MyApplication;
import com.banhuitong.activity.R;
import com.banhuitong.view.RoundProgressView;

public class BhbAdapter extends BaseAdapter {
	
	private DecimalFormat mformat = new DecimalFormat("#.##"); 
	private static final String TAG = "BhbAdapter" ;  
	
	private LayoutInflater inflater;
	private List<Map<String,Object>> bhbs;

	public BhbAdapter(Context context, List<Map<String,Object>> bhbs) {
	    this.inflater = LayoutInflater.from(context);
	    this.bhbs = bhbs;
	}

	public List<Map<String,Object>> getBhbs() {
		return bhbs;
	}

	public void setBhbs(List<Map<String,Object>> bhbs) {
		this.bhbs = bhbs;
	}

	@Override
	public int getCount() {
		return (bhbs==null)?0:bhbs.size();  
	}

	@Override
	public Object getItem(int position) {
		return bhbs.get(position);  
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
             convertView = inflater.inflate(R.layout.bhb_item,null);
             holder = new ViewHolder();
        }else{
        	holder = (ViewHolder) convertView.getTag();
        }
        
         holder.tvItemNo = (TextView) convertView.findViewById(R.id.tv_item_no);
	     holder.tvItemShowName = (TextView) convertView.findViewById(R.id.tv_item_show_name);
	     holder.tvTargetRate = (TextView) convertView.findViewById(R.id.tv_target_rate);
	     holder.tvBorrowDays = (TextView) convertView.findViewById(R.id.tv_borrow_days);
	     holder.tvType = (TextView) convertView.findViewById(R.id.tv_type);
	     holder.tvCountDown = (TextView) convertView.findViewById(R.id.tv_count_down);
	     holder.vProgress = (RoundProgressView) convertView.findViewById(R.id.v_round_progress);
	     holder.btnStatus = (TextView) convertView.findViewById(R.id.btn_status);
	     holder.tvStatus = (TextView) convertView.findViewById(R.id.tv_status);
	     
	     LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(MyApplication.screen_w/8, MyApplication.screen_h/14);
	     holder.vProgress.setLayoutParams(layoutParams);   
	     
	     holder.tvItemNo.setText(bhbs.get(position).get("itemNo").toString());
	     holder.tvItemShowName.setText(bhbs.get(position).get("itemShowName").toString());
	     
	     holder.tvTargetRate.setText(mformat.format(bhbs.get(position).get("targetRate")) + "%");
	     holder.tvBorrowDays.setText(bhbs.get(position).get("borrowDays") + "天");
	     
	     holder.tvType.setText("宝");
	     holder.tvType.setBackgroundResource(R.drawable.round_corner_blue);
	    
	     int progress = (((BigDecimal)bhbs.get(position).get("priorInvestedAmt")).add((BigDecimal)(bhbs.get(position).get("riskInvestedAmt"))))
	    		 .movePointRight(2)
	    		 .divide((((BigDecimal)bhbs.get(position).get("priorAmt")).add(((BigDecimal)bhbs.get(position).get("riskAmt")))), new MathContext(16,RoundingMode.HALF_DOWN)).intValue();
	     holder.vProgress.setProgress(progress);
	     
	     long status = (Long)bhbs.get(position).get("status");
	     
	     switch ((int)status) {
		 	case 1:
		 		holder.btnStatus.setText("了解详情");
		 		holder.btnStatus.setBackgroundResource(R.drawable.round_corner_dark_green);
		 		holder.tvStatus.setText("预告");
		 		holder.tvStatus.setBackgroundResource(R.drawable.round_corner_dark_green);
		 		break;
		 	case 2:
		 		holder.btnStatus.setText("我要加入");
		 		holder.btnStatus.setBackgroundResource(R.drawable.round_corner_dark_red);
		 		holder.tvStatus.setText("开放");
		 		holder.tvStatus.setBackgroundResource(R.drawable.round_corner_dark_red);
		 		break;
		 	case 3:
		 		holder.btnStatus.setText("了解详情");
		 		holder.btnStatus.setBackgroundResource(R.drawable.round_corner_light_blue);
		 		holder.tvStatus.setText("存续");
		 		holder.tvStatus.setBackgroundResource(R.drawable.round_corner_light_blue);
		 		break;
		 	case 4:
		 		holder.btnStatus.setText("了解详情");
		 		holder.btnStatus.setBackgroundResource(R.drawable.round_corner_grey);
		 		holder.tvStatus.setText("完结");
		 		holder.tvStatus.setBackgroundResource(R.drawable.round_corner_grey);
		 		break;
		 	default:
		 		break;
	 	 }
	     
	     holder.tvCountDown.setTextColor(Color.rgb(255, 0, 0));
	     if(status==2){
	    	 Date outTime = (Date)bhbs.get(position).get("outTime");
	    	 long financingDays = (Long)bhbs.get(position).get("financingDays");
			 Date today = new Date();
			 long dayDiff = (outTime.getTime() + financingDays * 24 * 60 * 60 * 1000 - today.getTime()) / (1000 * 24 * 60 * 60);
			 if(dayDiff<0){
				 holder.tvCountDown.setText("已截止");
			 }else{
				 long daysRemaining = dayDiff>0?dayDiff:1;
				 holder.tvCountDown.setText(daysRemaining + "天");
				 holder.tvCountDown.setTextColor(Color.rgb(0, 139, 69));
			 }
	     }else{
	    	 holder.tvCountDown.setText("");
	     }
	     
	     convertView.setTag(holder);//绑定ViewHolder对象 
	     
        return convertView;
	}
		
	public class ViewHolder {
	    public TextView tvItemNo;
	    public TextView tvItemShowName;
	    public TextView tvTargetRate;
	    public TextView tvBorrowDays;
	    public TextView tvType;
	    public TextView tvCountDown;
	    public RoundProgressView vProgress;
	    public TextView btnStatus;
	    public TextView tvStatus;
	}
	
}
