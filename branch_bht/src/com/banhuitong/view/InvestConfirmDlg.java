package com.banhuitong.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.banhuitong.activity.R;

public class InvestConfirmDlg  extends Dialog {

	public InvestConfirmDlg(Context context) {
		super(context);
		init();
	}
	
	private void init(){
		View mView = LayoutInflater.from(getContext()).inflate(R.layout.loding, null);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.setContentView(mView);
	}

}
