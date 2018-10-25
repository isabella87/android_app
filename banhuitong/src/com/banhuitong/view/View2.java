package com.banhuitong.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import com.banhuitong.activity.R;

@SuppressLint("DrawAllocation")
public class View2 extends View{
	
	public boolean toDrawCircle = true;

	public View2(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		Paint paint = new Paint();
		paint.setColor(Color.rgb(0xB2, 0x22, 0x22));
		
		Paint paint2 = new Paint();
		paint2.setColor(Color.rgb(0xFF, 0xFF, 0xFF));
		
		canvas.drawRect(20,0,30,this.getHeight(), paint);
		
		if(toDrawCircle){
			canvas.drawCircle(24, 27, 15, paint);
			canvas.drawCircle(24, 27, 5, paint2);
		}
	}
	
	public void refresh(){
		postInvalidate();
	}
}
