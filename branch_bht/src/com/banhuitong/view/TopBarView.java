package com.banhuitong.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.banhuitong.activity.R;

public class TopBarView extends RelativeLayout{

	public TopBarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.top_bar_view, this, true);  
	}
}
