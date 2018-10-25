package com.mengcheng.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;

import com.mengcheng.activity.R;
import com.mengcheng.activity.MyApplication;
import com.mengcheng.util.ViewUtils;

public class PortionView extends View { 
	
    private Bitmap showPic = null; 
    private int index = 0;

    public PortionView(Context context) { 
        super(context); 
        // TODO Auto-generated constructor stub  
    } 
 
    @Override 
    protected void onDraw(Canvas canvas) { 
        // TODO Auto-generated method stub  
        super.onDraw(canvas); 
        
        int screenHeight = MyApplication.screen_h;
		int screenWidth = MyApplication.screen_w;
        
		if(index==0){
        	showPic = ViewUtils.readBitMap(null, R.drawable.guide1, getResources());
        }else if(index==1){
        	showPic = ViewUtils.readBitMap(null, R.drawable.guide2, getResources());
        }else if(index==2){
        	showPic = ViewUtils.readBitMap(null, R.drawable.guide3, getResources());
        }else if(index==3){
        	showPic = ViewUtils.readBitMap(null, R.drawable.launch, getResources());
        }
    
        int rawHeight = showPic.getHeight();
		int rawWidth = showPic.getWidth();
		
		float ratio = (float)screenWidth/screenHeight;
		float ratio2 = (float)rawWidth/rawHeight;
				
		if(ratio<=ratio2){
			int x = rawWidth - (int)(rawHeight*ratio);
			
			Rect src = new Rect();// 图片
			Rect dst = new Rect();// 屏幕
			src.left = x/2;
			src.top = 0;
			src.right = rawWidth - x/2;
			src.bottom = rawHeight;
			dst.left = 0;
			dst.top = 0;
			dst.right = screenWidth;
			dst.bottom = screenHeight;
			canvas.drawBitmap(showPic, src, dst, null); 
			
//			canvas.drawBitmap(showPic, -x/2, 0, null); 
		}else{
			int y = rawHeight - (int)(rawWidth/ratio);
			
			Rect src = new Rect();// 图片
			Rect dst = new Rect();// 屏幕
			src.left = 0;
			src.top = y/2;
			src.right = rawWidth;
			src.bottom = rawHeight - y/2;
			dst.left = 0;
			dst.top = 0;
			dst.right = screenWidth;
			dst.bottom = screenHeight;
			canvas.drawBitmap(showPic, src, dst, null); 
			
//			canvas.drawBitmap(showPic, 0, (int)(rawHeight - rawWidth/ratio)/2, null); 
		}     
		
		showPic.recycle();
		showPic = null;
		System.gc();
    } 
    
//    public void setBitmapShow(Bitmap b) {
//        showPic = b; 
//    } 
    
    public void setIndex(int index) {
        this.index = index; 
    } 
}  
