package com.banhuitong.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.banhuitong.util.ViewUtils;

public class InvestListActivity extends BaseActivity {

	private ImageView imgBack;
	private RelativeLayout rlGong;
	private RelativeLayout rlBao;
	private RelativeLayout rlZhuan;
	private Bitmap mBitmap,mBitmap2;
	private DragView dragView;
	private boolean isLongClick = false;
	private RelativeLayout rlMove;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.invest_list);
		init();
		setListener();
	}

	@Override
	protected void onStart() {
		dragView.mMotionY = 0;
		super.onStart();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	private void init() {
		loading = ViewUtils.initRotateAnimation(this);

		imgBack = (ImageView) findViewById(R.id.back);
		rlGong = (RelativeLayout) findViewById(R.id.layer_gong);
		rlBao = (RelativeLayout) findViewById(R.id.layer_bao);
		rlZhuan = (RelativeLayout) findViewById(R.id.layer_zhuan);
		rlMove = (RelativeLayout) findViewById(R.id.main);

		rlZhuan.setId(1);
		mBitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.icon_zhuan);
		mBitmap2 = BitmapFactory.decodeResource(getResources(),
				R.drawable.arrow);
		dragView = new DragView(this);
		
		TextView tvTitle = (TextView) findViewById(R.id.tv_title);
		tvTitle.setText("出借管理");
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
//		int[] location = new int[2];  
//		rlGong.getLocationOnScreen(location);  
//        int x = location[0];  
//        int y = location[1];  
//        int height = rlGong.getHeight();
//        
//		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
//				MyApplication.screen_w, MyApplication.screen_h - y - height);
//		lp.addRule(RelativeLayout.BELOW, 1);
//		rlMove.removeView(dragView);
//		rlMove.addView(dragView, lp);
		
		super.onWindowFocusChanged(hasFocus);
	}

	private void setListener() {
		imgBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				InvestListActivity.this.finish();
			}
		});

		rlGong.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(InvestListActivity.this,
						AccInvestGongActivity.class);
				startActivity(intent);
			}
		});

		rlBao.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(InvestListActivity.this,
						AccInvestBaoActivity.class);
				startActivity(intent);
			}
		});

		rlZhuan.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(InvestListActivity.this,
						AccInvestZhuanActivity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			InvestListActivity.this.finish();
			return true;
		}
		return false;
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			loading.setVisibility(View.GONE);

			switch (msg.what) {
			default:
			}
		}
	};
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		return super.onTouchEvent(ev);
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		return super.dispatchTouchEvent(ev);
//		return true;
//		return false;
	}
	
	private class DragView extends View implements OnLongClickListener,OnClickListener {
		private int mMotionX = 0;
		private int mMotionY = 0, mActionDownY = 0;
		private Paint paint, paint2;

		public DragView(Context context) {
			super(context);
			setOnLongClickListener(this);
			setOnClickListener(this);
			
			paint = new Paint();
			paint.setColor(Color.rgb(0xFF, 0xFF, 0xFF));
			
			paint2 = new Paint();
			paint2.setColor(Color.rgb(0x00, 0x00, 0x00));
			paint2.setTextSize(MyApplication.screen_w / 20);
		}

		@Override
		public void draw(Canvas canvas) {
			int marginLeft = 18;
			int marginRight = 20;
			int marginTop = 10;
			
			Rect rect1 = new Rect();
			rect1.left = marginLeft;
			rect1.top = mMotionY + marginTop;
			rect1.right = marginLeft + 60;
			rect1.bottom = mMotionY + marginTop + 60;
			canvas.drawRect(0,mMotionY,MyApplication.screen_w, mMotionY + 80, paint);
			canvas.drawBitmap(mBitmap, null, rect1, null);
			
			Rect rect2 = new Rect();
			rect2.left = MyApplication.screen_w - marginRight - 20;
			rect2.top = mMotionY + marginTop + 10;
			rect2.right = MyApplication.screen_w - marginRight;
			rect2.bottom = mMotionY + marginTop + 50;
			canvas.drawBitmap(mBitmap2, null, rect2, null);
		
			canvas.drawText("债权转让", marginLeft + 70, mMotionY + marginTop + 40, paint2);
			super.draw(canvas);
		}

		@Override
		public boolean onLongClick(View v) {
			isLongClick = true;
			return true;
		}
		
		public boolean dispatchTouchEvent(MotionEvent event) {
	        return super.dispatchTouchEvent(event);
	    }


		@Override
		public boolean onTouchEvent(MotionEvent ev) {
			switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				mActionDownY = (int) ev.getY();
			case MotionEvent.ACTION_UP:
				isLongClick = false;

			case MotionEvent.ACTION_MOVE:
				if(isLongClick){
//					mMotionX = (int) ev.getX();
//					mMotionY = (int) ev.getY();
//					mActionDownY = (int) ev.getY();
//					invalidate();
				}
			default:
				break;
			}
			return super.onTouchEvent(ev);
		}

		@Override
		public void onClick(View v) {
			if(mActionDownY >= mMotionY && mActionDownY <= mMotionY + 80){
				Intent intent = new Intent(InvestListActivity.this,
						AccInvestZhuanActivity.class);
				startActivity(intent);
			}
		}
	}

}
