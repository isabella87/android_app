package com.banhuitong.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.banhuitong.activity.MyApplication;

@SuppressLint("DrawAllocation")
public class View1 extends View{

	public View1(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
//		canvas.drawColor(Color.rgb(0xDC, 0xDC, 0xDC));
		
		Paint paint = new Paint();
		paint.setColor(Color.rgb(0x00, 0xCD, 0xCD));
		
		Paint paint2 = new Paint();
		paint2.setColor(Color.rgb(0x00, 0x00, 0x00));
		paint2.setTextSize(MyApplication.screen_w/22);
		
		canvas.drawRect(0,10,10 + MyApplication.screen_w/40, 10 + MyApplication.screen_w/25, paint);
		canvas.drawText("还款日", 15 + MyApplication.screen_w/40, 8 + MyApplication.screen_w/25, paint2);
	}
}
