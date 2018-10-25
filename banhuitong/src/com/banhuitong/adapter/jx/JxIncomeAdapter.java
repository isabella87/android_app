package com.banhuitong.adapter.jx;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.banhuitong.activity.R;
import com.banhuitong.activity.WelcomeActivity;
import com.banhuitong.util.ViewUtils;

public class JxIncomeAdapter extends BaseAdapter {
	
	private static final String TAG = "IncomeAdapter" ;  
	
	private LayoutInflater inflater;
	private List<Map<String,Object>> incomes;
	private Drawable spinner1, spinner2;

	public JxIncomeAdapter(Context context, List<Map<String,Object>> incomes) {
	    this.inflater = LayoutInflater.from(context);
	    this.incomes = incomes;
	    
	    Bitmap rawBitmap = ViewUtils.readBitMap(context, R.drawable.spinner1, context.getResources());		
		spinner1 = new BitmapDrawable(rawBitmap);
		
		rawBitmap = ViewUtils.readBitMap(context, R.drawable.spinner2, context.getResources());		
		spinner2 = new BitmapDrawable(rawBitmap);	
	}

	public List<Map<String,Object>> getIncomes() {
		return incomes;
	}

	public void setIncomes(List<Map<String,Object>> incomes) {
		this.incomes = incomes;
	}

	@Override
	public int getCount() {
		return (incomes==null)?0:incomes.size();  
	}

	@Override
	public Object getItem(int position) {
		return incomes.get(position);  
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
             convertView = inflater.inflate(R.layout.income_item,null);
             holder = new ViewHolder();
        }else{
        	holder = (ViewHolder) convertView.getTag();
        }
        
	     holder.tvDatepoint = (TextView) convertView.findViewById(R.id.tv_datepoint);
	     holder.tvAmt = (TextView) convertView.findViewById(R.id.tv_amt);
	     holder.imgArrow = (ImageView) convertView.findViewById(R.id.img_arrow);
	     holder.tvRemark = (TextView) convertView.findViewById(R.id.tv_remark);
	     holder.tvItemShowName = (TextView) convertView.findViewById(R.id.tv_item_show_name);
	     holder.l2 = (View) convertView.findViewById(R.id.l2);
	     holder.rlIncome = (View) convertView.findViewById(R.id.rl_income);
	     
	     holder.tvItemShowName.setText(incomes.get(position).get("desc").toString());
	     holder.tvDatepoint.setText(ViewUtils.formatter.format(NumberUtils.toLong(incomes.get(position).get("datepoint").toString(),0)));
	 	 holder.tvAmt.setText(ViewUtils.mformat.format(incomes.get(position).get("amount")) + "元");
	     
	     convertView.setTag(holder);//绑定ViewHolder对象 
	     
	     holder.imgArrow.setImageDrawable(spinner2);
	     holder.rlIncome.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(holder.l2.getVisibility()==View.VISIBLE){
						holder.l2.setVisibility(View.GONE);
						holder.imgArrow.setImageDrawable(spinner2);
					}else{
						holder.l2.setVisibility(View.VISIBLE);
						holder.imgArrow.setImageDrawable(spinner1);
					}
				}
			});
	     
	    int colorPos=position%2;
        if(colorPos==1){
        	convertView.setBackgroundResource(R.color.grey); 
        }else{
        	convertView.setBackgroundResource(R.color.white); 
        }
        
        return convertView;
	}
		
	public class ViewHolder {
	    public TextView tvDatepoint;
	    public TextView tvItemShowName;
	    public TextView tvRemark;
	    public TextView tvAmt;
	    public ImageView imgArrow;
	    public View l2;
	    public View rlIncome;
	}
	
}
