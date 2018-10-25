package com.banhuitong.view;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.banhuitong.activity.R;

public class SystemAlertDialog {
	 	Context context;
	    Dialog ad;
	    TextView titleView;
	    TextView messageView;
	    LinearLayout buttonLayout;
	    
	    public SystemAlertDialog(Context context) {
	        // TODO Auto-generated constructor stub
	        this.context=context;
			Builder builder=new Builder(context);

			ad=builder.create();
			ad.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
			ad.show();
	        
			Window window = ad.getWindow();
			window.setContentView(R.layout.system_dialog);
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
	        button.setBackgroundResource(R.drawable.red_round);
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
	        button.setBackgroundResource(R.drawable.red_round);
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
