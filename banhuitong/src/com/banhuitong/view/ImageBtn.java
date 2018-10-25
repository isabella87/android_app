package com.banhuitong.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.banhuitong.activity.R;

public class ImageBtn extends LinearLayout {

	private ImageView imageView;
	private TextView  textView;
	
	public ImageBtn(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public ImageBtn(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.image_btn, this);
		imageView=(ImageView) findViewById(R.id.imageView1);
		textView=(TextView)findViewById(R.id.textView1);
	}
	
	/** 
     * 设置图片资源 
     */  
    public void setImageResource(int resId) {  
        imageView.setImageResource(resId);  
    }  
  
    /** 
     * 设置显示的文字 
     */  
    public void setTextViewText(String text) {  
        textView.setText(text);  
    }  

}
