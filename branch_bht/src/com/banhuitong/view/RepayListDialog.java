package com.banhuitong.view;

import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.banhuitong.activity.AccInvestGongActivity;
import com.banhuitong.activity.R;
import com.banhuitong.adapter.InvestEntRepayListAdapter;

public class RepayListDialog {
	Context context;
	AlertDialog ad;
	TextView titleView;
	MyListView lv_repays;
	TextView tvClose;
	InvestEntRepayListAdapter mAdapter;

	public RepayListDialog(Context context, List<Map<String, Object>> bonusList, long assignTime, int tab) {	
		
		this.context = context;
		ad = new AlertDialog.Builder(context).create();
		ad.show();

		// 关键在下面的两行,使用window.setContentView,替换整个对话框窗口的布局
		Window window = ad.getWindow();
		window.setContentView(R.layout.repay_list);
		titleView = (TextView) window.findViewById(R.id.title);
		lv_repays = (MyListView) window.findViewById(R.id.lv_repay);
		tvClose = (TextView) window.findViewById(R.id.tv_close);
		
		mAdapter = new InvestEntRepayListAdapter(context, bonusList, assignTime, tab);
		lv_repays.setAdapter(mAdapter);
		mAdapter.setBonusList(bonusList);
    	mAdapter.notifyDataSetChanged();
		
		setListener();
	}
	
	private void setListener() {
		
		tvClose.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ad.dismiss();
			}
		});
	}
}
