package com.banhuitong.async;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.banhuitong.activity.R;
import com.banhuitong.http.RequestService;

public class ImageLoadTask extends AsyncTask<Object, Void, Bitmap> {
	ImageView mHolder;
	public String url;
	private Bitmap drawable;
	String sdcradImgUrl;
    Resources res;
	public ImageLoadTask(Context context) {
	}

	@Override
	protected Bitmap doInBackground(Object... params) {
		try {

			mHolder = (ImageView) params[0];
			url = (String) params[1];
			res=(Resources) params[2];
			drawable = RequestService.getInstance().loadImage(url);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return drawable;
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		if (result == null) {
			mHolder.setImageBitmap(BitmapFactory.decodeResource(res,R.drawable.abc_ab_solid_dark_holo));
		}else{
			mHolder.setImageBitmap(result);
		}
		
	}

}
