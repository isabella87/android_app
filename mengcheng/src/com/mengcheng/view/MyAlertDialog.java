package com.mengcheng.view;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.mengcheng.activity.R;

public class MyAlertDialog {
	 	Context context;
	    AlertDialog ad;
	    TextView titleView;
	    TextView messageView;
	    LinearLayout buttonLayout;
	    
	    public MyAlertDialog(Context context) {
	        // TODO Auto-generated constructor stub
	        this.context=context;
	        ad=new AlertDialog.Builder(context).create();
	        ad.show();
	        
	        //关键在下面的两行,使用window.setContentView,替换整个对话框窗口的布局
	        Window window = ad.getWindow();
	        window.setContentView(R.layout.alert_dialog);
	        titleView=(TextView)window.findViewById(R.id.title);
	        messageView=(TextView)window.findViewById(R.id.message);
	        buttonLayout=(LinearLayout)window.findViewById(R.id.buttonLayout);
	    }
	    
	    public void setTitle(int resId){
	        titleView.setText(resId);
	    }
	    
	    public void setTitle(String title) {
	        titleView.setText(title);
	    }
	    
	    public void setMessage(int resId) {
	        messageView.setText(resId);
	    }  
	    
	    public void setMessage(String message){
	        messageView.setText(message);
	    }
	    
	    /**
	     * 设置按钮
	     * @param text
	     * @param listener
	     */
	    public void setPositiveButton(String text,final View.OnClickListener listener){
	        Button button=new Button(context);
	        LinearLayout.LayoutParams params=new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	        button.setLayoutParams(params);
	        button.setBackgroundResource(R.color.light_blue);
	        button.setText(text);
	        button.setTextColor(Color.WHITE);
	        button.setTextSize(20);
	        button.setPadding(15, 3, 15, 3);
	        button.setOnClickListener(listener);
	        buttonLayout.addView(button);
	    }  
	    
	    /**
	     * 设置按钮
	     * @param text
	     * @param listener
	     */
	    public void setNegativeButton(String text,final View.OnClickListener listener){
	        Button button=new Button(context);
	        LinearLayout.LayoutParams params=new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	        button.setLayoutParams(params);
	        button.setBackgroundResource(R.color.pink);
	        button.setText(text);
	        button.setTextColor(Color.WHITE);
	        button.setTextSize(20);
	        button.setPadding(15, 3, 15, 3);
	        button.setOnClickListener(listener);
	        if(buttonLayout.getChildCount()>0)
	        {
	            params.setMargins(20, 0, 0, 0);
	            button.setLayoutParams(params);
	            buttonLayout.addView(button);
	        }else{
	            button.setLayoutParams(params);
	            buttonLayout.addView(button);
	        }  
	    }
	    
	    /**
	     * 关闭对话框
	     */
	    public void dismiss() {
	        ad.dismiss();
	    } 
}
