package com.banhuitong.listener;

import java.math.BigDecimal;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;

public class TempListener implements SensorEventListener {
	@Override
	public final void onSensorChanged(SensorEvent event) {
		float temperatureValue = event.values[0];
		BigDecimal bd = new BigDecimal(temperatureValue);
		double temperature = bd.setScale(2, BigDecimal.ROUND_HALF_UP)
				.doubleValue();
		Log.i("sensor", "当前温度：" + temperature);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// Log.i("Sensor", "onAccuracyChanged");
	}
}
