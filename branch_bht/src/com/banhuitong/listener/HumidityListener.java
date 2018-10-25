package com.banhuitong.listener;

import java.math.BigDecimal;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;

public class HumidityListener implements SensorEventListener {
	@Override
	public final void onSensorChanged(SensorEvent event) {
		float humidityValue = event.values[0];
		BigDecimal bd = new BigDecimal(humidityValue);
		double humidity = bd.setScale(2, BigDecimal.ROUND_HALF_UP)
				.doubleValue();
		Log.i("sensor", "当前湿度：" + humidity);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// Log.i("Sensor", "onAccuracyChanged");
	}
}
