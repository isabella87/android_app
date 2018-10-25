package com.banhuitong.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.banhuitong.adapter.CaAdapter;
import com.banhuitong.adapter.EntAdapter;
import com.banhuitong.async.DefaultThreadPool;
import com.banhuitong.async.GetPortalBhbsTask;
import com.banhuitong.async.GetPortalCasTask;
import com.banhuitong.async.GetPortalEntsTask;
import com.banhuitong.inf.OnRefreshListener;
import com.banhuitong.item.PortalPrjBhbs;
import com.banhuitong.item.PortalPrjCas;
import com.banhuitong.item.PortalPrjEnts;
import com.banhuitong.util.Constants;
import com.banhuitong.util.ViewUtils;
import com.banhuitong.view.MyListView;

public class MainActivity2 extends BaseActivity {

	public static final String TAG = "MainActivity2";

	private EntAdapter mAdapter;
	private CaAdapter caAdapter;
	private MyListView lv;
	private List<Map<String, Object>> ents;
	private List<Map<String, Object>> bhbs;
	private List<Map<String, Object>> cas;
	private RadioGroup rg;
	public RadioButton mRadio1, mRadio2, mRadio3;
	private int tab = 1;
	private int downX;
	private int upX;

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main2);
		init();
		setListener();
	}

	@Override
	protected void onStart() {
		super.onStart();

		switch (tab) {
		case 1:
			mRadio1.performClick();
			break;
		case 2:
			mRadio2.performClick();
			break;
		case 3:
			mRadio3.performClick();
			break;
		default:
		}
	}

	@Override
	protected void onDestroy() {
		((ViewGroup)loading.getParent()).removeView(loading);
		super.onDestroy();
	}

	private void init() {
		loading = ViewUtils.initRotateAnimation(this);

		Intent intent = getIntent();
		tab = intent.getIntExtra("tab", 1);

		lv = (MyListView) findViewById(R.id.lv_ent);
		rg = (RadioGroup) findViewById(R.id.tabs_rg_main);
		mRadio1 = (RadioButton) findViewById(R.id.tab_rb_ent);
		mRadio2 = (RadioButton) findViewById(R.id.tab_rb_bhb);
		mRadio3 = (RadioButton) findViewById(R.id.tab_rb_credit_assign);
		
		mRadio2.setVisibility(View.GONE);

		mAdapter = new EntAdapter(MainActivity2.this, ents);
		caAdapter = new CaAdapter(MainActivity2.this, cas);

		rg.clearCheck();
	}

	private void setListener() {

		lv.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
			}

			@Override
			public void onLoadfresh(int type, int page) {
				loading.setVisibility(View.VISIBLE);
				lv.page = 1;
				switch (tab) {
				case 1:
					ents = null;
					DefaultThreadPool.getInstance().execute(new GetPortalEntsTask(handler,
							lv.page));
					break;
				case 2:
					bhbs = null;
					DefaultThreadPool.getInstance().execute( new GetPortalBhbsTask(handler,
							lv.page));
					break;
				case 3:
					cas = null;
					DefaultThreadPool.getInstance().execute(new GetPortalCasTask(handler,
							lv.page));
					break;
				default:
				}
			}

			@Override
			public void onLoadMoring(boolean finish) {
				loading.setVisibility(View.VISIBLE);
				lv.page++;
				switch (tab) {
				case 1:
					DefaultThreadPool.getInstance().execute(new GetPortalEntsTask(handler,
							lv.page));
					break;
				case 2:
					DefaultThreadPool.getInstance().execute(new GetPortalBhbsTask(handler,
							lv.page));
					break;
				case 3:
					DefaultThreadPool.getInstance().execute(new GetPortalCasTask(handler,
							lv.page));
					break;
				default:
				}
			}
		});

		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				loading.setVisibility(View.VISIBLE);

				if (checkedId == mRadio1.getId()) {
					lv.setAdapter(mAdapter);
					ents = new ArrayList<Map<String, Object>>();
					lv.page = 1;
					tab = 1;

					DefaultThreadPool.getInstance().execute(new GetPortalEntsTask(handler,
							lv.page));
				}  else if (checkedId == mRadio3.getId()) {
					lv.setAdapter(caAdapter);
					cas = new ArrayList<Map<String, Object>>();
					lv.page = 1;
					tab = 3;

					DefaultThreadPool.getInstance().execute(new GetPortalCasTask(handler,
							lv.page));
				} else {
					loading.setVisibility(View.GONE);
					lv.setAdapter(null);
				}

				for (int i = 0; i < rg.getChildCount(); i++) {
					RadioButton rb = (RadioButton) rg.getChildAt(i);
					rb.setTextColor(Color.rgb(178, 34, 34));
					rb.setBackgroundResource(R.drawable.main_tab);

					if (rb.getId() == checkedId) {
						rb.setTextColor(Color.rgb(255, 255, 255));
						rb.setBackgroundColor(Color.rgb(178, 34, 34));
					}
				}
			}
		});

		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				if(position<1) return;

				switch (tab) {
				case 1:
					if(ents!=null){
						Map<String, Object> item = ents.get(position - 1);

						Intent intent = new Intent(MainActivity2.this,
								EntDetailActivity.class);
						intent.putExtra("pId", (Long) item.get("pId"));
						intent.putExtra("hash", item.get("hash").toString());
						getParent().startActivityForResult(intent, 0);
					}
					break;
				case 3:
					if(cas!=null){
						Map<String, Object> item3 = cas.get(position - 1);

						Intent intent3 = new Intent(MainActivity2.this,
								CaDetailActivity.class);
						intent3.putExtra("pId", (Long) item3.get("pId"));
						intent3.putExtra("hash", item3.get("hash").toString());
						getParent().startActivityForResult(intent3, 0);
					}
					break;
				default:
				}
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
			loading.setVisibility(View.GONE);

			switch (msg.what) {
			case Constants.GET_PORTAL_ENTS:
				PortalPrjEnts portalPrjEnts = (PortalPrjEnts) msg.obj;

				if (ents != null) {
					ents.addAll(portalPrjEnts.getEnts());
				} else {
					ents = portalPrjEnts.getEnts();
				}

				mAdapter.setEnts(ents);
				mAdapter.notifyDataSetChanged();
				break;
			case Constants.GET_PORTAL_CAS:
				PortalPrjCas portalPrjCas = (PortalPrjCas) msg.obj;

				if (cas != null) {
					cas.addAll(portalPrjCas.getCas());
				} else {
					cas = portalPrjCas.getCas();
				}

				caAdapter.setCas(cas);
				caAdapter.notifyDataSetChanged();
				break;
			// case Constants.COUNTDOWN_PORTAL_ENTS:
			// for(int i = 0; i < lv.getChildCount(); i++){
			// View view = lv.getChildAt(i);
			// TextView tvCountdown =
			// (TextView)view.findViewById(R.id.tv_count_down);
			// tvCountdown.setText(new Date().getTime() + "");
			// }
			// break;
			case Constants.NETWORK_ERROR:
				Toast.makeText(getApplicationContext(), "网络异常！", 1).show();
				break;
			default:
			}
			lv.onRefreshFinish();
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
}
