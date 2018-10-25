package com.banhuitong.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.banhuitong.activity.MyApplication;
import com.banhuitong.activity.R;

public class HeaderView extends RelativeLayout{

	public HeaderView(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.header_view, this, true);  
		TextView tvTitle = (TextView) this.findViewById(R.id.tv_title);
		float textSize = MyApplication.screen_w/30>18 ? 18 : MyApplication.screen_w/30;
		tvTitle.setTextSize(textSize);
	}
}
