package com.banhuitong.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.banhuitong.util.Constants;
import com.banhuitong.util.ViewUtils;
import com.banhuitong.view.TopBarView;

public class ShowVideoActivity extends BaseActivity implements OnPreparedListener, OnErrorListener {
	
	private ImageView imgBack;
	private Intent lastIntent;
	private String url = "";
	private VideoView videoView;
	private MediaController mc;
	private TopBarView vTopbar;
	private ImageView imgPlay;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_video);
		init();
		setListener();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}

	private void init() {
		loading = ViewUtils.initRotateAnimation(this);
		
		Intent lastIntent = getIntent();
		String opt = lastIntent.getStringExtra("opt");
		
		TextView tvTitle = (TextView)findViewById(R.id.tv_title);
		
		imgBack = (ImageView) findViewById(R.id.back);
		imgPlay = (ImageView) findViewById(R.id.img_play);
		videoView = (VideoView)this.findViewById(R.id.video_main);  
		vTopbar = (TopBarView)this.findViewById(R.id.topbar);  
		
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(MyApplication.screen_w/4, MyApplication.screen_h/7);
		imgPlay.setLayoutParams(layoutParams);
		
		LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)imgPlay.getLayoutParams();
		params.setMargins((MyApplication.screen_w-layoutParams.width)/2, layoutParams.height/2, 0, 0);
		
        mc = new MediaController(this);
//		mc.setAnchorView(videoView);
//		mc.setKeepScreenOn(true);
		videoView.setMediaController(mc);
		
		if("REG_VIDEO".equals(opt)){
			tvTitle.setText("注册流程视频演示");
			url = Constants.mobileUrl + "web/video/register.mp4";
		}
		
		Uri uri = Uri.parse(url);
        videoView.setVideoURI(uri);  
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	    super.onConfigurationChanged(newConfig);
	    
	    if(this.getResources().getConfiguration().orientation ==Configuration.ORIENTATION_PORTRAIT){
	    	
	    	WindowManager.LayoutParams attrs = getWindow().getAttributes();     
	    	attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);     
	    	getWindow().setAttributes(attrs);      
	    	getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
	    	
	    	RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
    				RelativeLayout.LayoutParams.WRAP_CONTENT,
    				RelativeLayout.LayoutParams.WRAP_CONTENT);
	    	layoutParams.addRule(RelativeLayout.BELOW, vTopbar.getId());
    		videoView.setLayoutParams(layoutParams);
    		vTopbar.setVisibility(View.VISIBLE);
    		
    		int w = imgPlay.getWidth();
    		int h = imgPlay.getHeight();
    		
    		LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)imgPlay.getLayoutParams();
    		params.setMargins((MyApplication.screen_w-w)/2, h/2, 0, 0);
    		
        }else if(this.getResources().getConfiguration().orientation ==Configuration.ORIENTATION_LANDSCAPE){
        	
        	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        	RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
    				RelativeLayout.LayoutParams.MATCH_PARENT,
    				RelativeLayout.LayoutParams.MATCH_PARENT);
    		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
    		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
    		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
    		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
    		videoView.setLayoutParams(layoutParams);
    		vTopbar.setVisibility(View.GONE);

    		int w = imgPlay.getWidth();
    		int h = imgPlay.getHeight();
    		
    		LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)imgPlay.getLayoutParams();
    		params.setMargins((MyApplication.screen_h-w)/2, (MyApplication.screen_w-h)/2, 0, 0);
        }
	}
	
	private void setListener() {
		imgBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ShowVideoActivity.this.finish();
			}
		});
		
		videoView.setOnCompletionListener(new OnCompletionListener(){
		     @Override
		     public void onCompletion(MediaPlayer mp){
		     }
	    });
		
		videoView.setOnTouchListener(new View.OnTouchListener() {
	        @Override
	        public boolean onTouch(View v, MotionEvent event) {
	        	if(imgPlay.getVisibility()==View.VISIBLE){
	        		imgPlay.setVisibility(View.GONE);
	        		videoView.start();
	        	}
	            return false;
	        }
		});
	}
	
	 @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if(keyCode==KeyEvent.KEYCODE_BACK) {
            	ShowVideoActivity.this.finish();
            	return true;
        }
        return super.onKeyDown(keyCode, event);
    }

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
	}

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		finish();
		return true;
	}
}
