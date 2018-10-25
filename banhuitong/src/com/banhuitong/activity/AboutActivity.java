package com.banhuitong.activity;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.banhuitong.activity.test.TestActivity;
import com.banhuitong.inf.ScrollViewListener;
import com.banhuitong.listener.ShakeListener;
import com.banhuitong.listener.ShakeListener.OnShakeListener;
import com.banhuitong.view.MyScrollView;
import com.banhuitong.view.TopBarView;

@SuppressLint("NewApi")
public class AboutActivity extends BaseActivity implements ScrollViewListener {

	private ImageView imgBack;
	private TextView tvVersion;
	private TextView tvLocationIcon;
	private TextView tvTest;
	private MyScrollView srvMain;
	private TopBarView vTop;
	private SensorManager mSensorManager;
	private Sensor temperature;
	private Sensor humidity;

	private SensorEventListener tempListener;
	private SensorEventListener humidityListener;
	private ShakeListener mShakeListener;  
	private Vibrator mVibrator;  

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		init();
		setListener();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mShakeListener != null) {  
            mShakeListener.stop();  
        }  
		
		if (mVibrator != null) {  
			mVibrator.cancel();  
        }  
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		 List<Sensor> deviceSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
		 for (Sensor sensor : deviceSensors) {
			 Log.i("sensor", "------------------");
			 Log.i("sensor", sensor.getName());
			 Log.i("sensor", sensor.getVendor());
			 Log.i("sensor", Integer.toString(sensor.getType()));
		 }

//		temperature = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
//		humidity = mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
//
//		this.tempListener = new TempListener();
//		this.humidityListener = new HumidityListener();
//
//		mSensorManager.registerListener(tempListener, temperature, SensorManager.SENSOR_DELAY_NORMAL);
//		mSensorManager.registerListener(humidityListener, humidity, SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	protected void onPause() {
		super.onPause();
//		mSensorManager.unregisterListener(this.tempListener);
//		mSensorManager.unregisterListener(this.humidityListener);
	}

	private void init() {
		mVibrator = (Vibrator)getApplication().getSystemService(VIBRATOR_SERVICE);  

		imgBack = (ImageView) findViewById(R.id.back);
		tvVersion = (TextView) findViewById(R.id.tv_version);
		tvLocationIcon = (TextView) findViewById(R.id.tv_location_icon);
		tvTest = (TextView) findViewById(R.id.tv_test);
		srvMain = (MyScrollView) findViewById(R.id.srv_main);
		vTop = (TopBarView) findViewById(R.id.topbar);

		try {
			String version = mApp.getVersionName();
			tvVersion.setText(version);
		} catch (Exception e) {
			e.printStackTrace();
		}

		TextView tvTitle = (TextView) findViewById(R.id.tv_title);
		tvTitle.setText("关于");
	}

	private void setListener() {
		
		 mShakeListener = new ShakeListener(this);  
	        mShakeListener.setOnShakeListener(new OnShakeListener() {  
	            public void onShake() {  
	            	mVibrator.vibrate( new long[]{500,200,500,200}, -1);
	                startAnim();
	            }  
	        });  
	        
		srvMain.setScrollViewListener(this);

		imgBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AboutActivity.this.finish();
			}
		});

		tvLocationIcon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(AboutActivity.this,
						MapActivity.class);
				startActivity(intent);
			}
		});
		
		tvTest.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(AboutActivity.this,
						TestActivity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			AboutActivity.this.finish();
			return true;
		}
		return false;
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
		}
	};

	@Override
	public void onScrollChanged(MyScrollView scrollView, int x, int y,
			int oldx, int oldy) {
		if (scrollView == srvMain) {
			vTop.setAlpha(1 - y / 100f);
		}
	}
	
	public void startAnim () {
        AnimationSet animdn = new AnimationSet(true);  
        TranslateAnimation mytranslateanimdn0 = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,+0.5f);  
        mytranslateanimdn0.setDuration(1000);  
        TranslateAnimation mytranslateanimdn1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,-0.5f);  
        mytranslateanimdn1.setDuration(1000);  
        mytranslateanimdn1.setStartOffset(1000);  
        animdn.addAnimation(mytranslateanimdn0);  
        animdn.addAnimation(mytranslateanimdn1);  
        srvMain.startAnimation(animdn);
    }  
}
