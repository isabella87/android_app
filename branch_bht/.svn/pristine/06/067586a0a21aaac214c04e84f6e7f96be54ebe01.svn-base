package com.banhuitong.activity;

import java.io.IOException;
import java.util.Map;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.banhuitong.http.RequestService;
import com.banhuitong.inf.ScrollViewListener;
import com.banhuitong.util.Constants;
import com.banhuitong.util.Urls;
import com.banhuitong.util.ViewUtils;
import com.banhuitong.view.MyScrollView;

public class MoreActivity extends BaseActivity implements ScrollViewListener {
	
	private RelativeLayout rlShare;
	private RelativeLayout rlAbout;
	private RelativeLayout rlRegVideo;
	private RelativeLayout rlAboutus;
	private TextView tvShare;
	private int downX;	
	private int upX;
	private ImageView imgRCode;
	private Bitmap rcodeBitmap;
	private boolean booSignedRcode = true;
	private MyScrollView srvMain;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.more);
		init();
		setListener();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		if(!isRestart){
		}
		
		if(isLogin()){
			try {
//				rcodeBitmap = RequestService.getInstance().loadImage(Constants.serverUrlp2p + "reg/rq-code-pic?disp-name=" + (booSignedRcode?"true":"false"));
				rcodeBitmap = RequestService.getInstance().loadImage(Urls.URL_11 + "?disp-name=" + (booSignedRcode?"true":"false"));
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
			
			if(rcodeBitmap!=null){
				imgRCode.setImageBitmap(rcodeBitmap);
				
				String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/banhuitong/";
				String file = "banhuitong_rcode.png";
				ViewUtils.saveBitmap(rcodeBitmap, path, file);
			}			
		}
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	private void init() {
		loading = ViewUtils.initRotateAnimation(this);
		
		rlShare = (RelativeLayout) findViewById(R.id.layer_share);
		rlAbout = (RelativeLayout) findViewById(R.id.layer_about);
		rlRegVideo = (RelativeLayout) findViewById(R.id.layer_reg_video);
		rlAboutus = (RelativeLayout) findViewById(R.id.layer_aboutus);
		tvShare = (TextView) findViewById(R.id.tv_share);
		imgRCode = (ImageView) findViewById(R.id.image_rcode);
		srvMain = (MyScrollView) findViewById(R.id.srv_main);
	}
	
	private void setListener() {
		
		srvMain.setScrollViewListener(this);  
		
		imgRCode.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					if(isLogin()){
						getRcode();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		rlShare.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showShare();
			}
		});
		
		rlAbout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MoreActivity.this, AboutActivity.class);
				startActivity(intent); 
			}
		});
		
		rlRegVideo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MoreActivity.this, ShowVideoActivity.class);
				intent.putExtra("url", "http://mp.weixin.qq.com/mp/video?__biz=MzAwNjAwMDEwOA==&mid=400056852&sn=208dd7ef5adeb247b136b6059ac6fc43&vid=q1300f6gfnn&idx=1&scene=18&pass_ticket=WIt9Z4MMubPEewBKfd7YWx%2FuM1bhFXKzTQspSzw3HQgG3qHJn%2BvL3TKVEvKLmH%2FY");
				intent.putExtra("title", "注册流程视频演示");
				startActivity(intent); 
			}
		});
		
		rlAboutus.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MoreActivity.this, AboutusActivity.class);
				startActivity(intent); 
			}
		});
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			
		}
		return false;
	}
	
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			switch (msg.what) {
			case Constants.GET_CRYPT_MOBILE_SUCCESS:
				Map<String,String> map = (Map<String, String>) msg.obj;
//				String rcode = map.get("rcode");
//				String mobile = map.get("mobile");			
				break;
			case Constants.NETWORK_ERROR:
				Toast.makeText(getApplicationContext(), "网络异常！", 1).show();
				break;
			default:
			}
		}
	};
	
	@Override  
    public boolean dispatchTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			downX = (int) ev.getX();
			break;
		case MotionEvent.ACTION_UP:
			upX = (int) ev.getX();
			break;
		case MotionEvent.ACTION_MOVE:
			
			break;
		default:
			break;
		}
		return super.dispatchTouchEvent(ev);
	}
	
	private void getRcode() throws IOException {
	    booSignedRcode = !booSignedRcode;
//	    rcodeBitmap = RequestService.getInstance().loadImage(Constants.serverUrlp2p + "reg/rq-code-pic?disp-name=" + (booSignedRcode?"true":"false"));
	    rcodeBitmap = RequestService.getInstance().loadImage(Urls.URL_11 + "?disp-name=" + (booSignedRcode?"true":"false"));
	    if(rcodeBitmap!=null){
			imgRCode.setImageBitmap(rcodeBitmap);
	    }
	}
	
	@Override  
    public void onScrollChanged(MyScrollView scrollView, int x, int y,  
            int oldx, int oldy) {  
        if (scrollView == srvMain) {  
            
        } 
    }  
}
