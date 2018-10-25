package com.banhuitong.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.banhuitong.activity.R;
import com.banhuitong.inf.OnRefreshListener;
import com.banhuitong.util.ActivityUtils;

@SuppressLint("NewApi") public class MyListView extends ListView implements OnScrollListener {
	
	public static final String TAG = "MyListView";  
	public int page = 1;
	
	private boolean isScroll2Bottom = false;	// 是否滚动到底部
	private boolean isScroll2Top = false;
	private OnRefreshListener mOnRefreshListener;
	private int downY;		// 按下时y轴的偏移量
	private int upY;	
	private View headerView;		// 头布局
	private int headerViewHeight;	// 头布局的高度
	private int firstVisibleItemPosition;
	private Animation upAnim;		// 向上旋转的动画
	private Animation downAnim;		// 向下旋转的动画
	private ImageView ivArrow;		// 头布局的箭头
	private TextView tvState;		// 头布局刷新状态
	private TextView tvLastUpdateTime;	// 头布局的最后刷新时间
	private DisplayMode currentState = DisplayMode.Pull_Down;
	
	public enum DisplayMode {
		Pull_Down, // 下拉刷新的状态
		Release_Refresh, // 松开刷新的状态
		Refreshing	// 正在刷新中的状态
	}
	
	public MyListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
		initHeader();
	}
	
	private void init(Context context) {
		// 设置滚动监听事件
		setOnScrollListener(this);
	}
	
	private void initHeader() {
		headerView = LayoutInflater.from(getContext()).inflate(R.layout.activity_listview_refresh_header, null);
		ivArrow = (ImageView) headerView.findViewById(R.id.iv_listview_header_down_arrow);
		tvState = (TextView) headerView.findViewById(R.id.tv_listview_header_state);
		tvLastUpdateTime = (TextView) headerView.findViewById(R.id.tv_listview_header_last_update_time);
		
		ivArrow.setMinimumWidth(50);
		tvLastUpdateTime.setText("最后刷新时间: " + ActivityUtils.getLastUpdateTime());
		
		measureView(headerView);
		headerViewHeight = headerView.getMeasuredHeight();
		
		headerView.setPadding(0, -headerViewHeight, 0, 0);
		
		this.addHeaderView(headerView);
		initAnimation();
	}
	
	private void refreshHeaderViewState() {
		if(currentState == DisplayMode.Pull_Down) {	// 当前进入下拉状态
			ivArrow.startAnimation(downAnim);
			tvState.setText("下拉刷新");
		} else if(currentState == DisplayMode.Release_Refresh) { //当前进入松开刷新状态
			ivArrow.startAnimation(upAnim);
			tvState.setText("松开刷新");
		} else if(currentState == DisplayMode.Refreshing) {  //当前进入正在刷新中
			ivArrow.clearAnimation();
			tvState.setText("正在刷新中..");
		}
	}
	
	/**
	 * 当刷新任务执行完毕时, 回调此方法, 去刷新界面
	 */
	public void onRefreshFinish() {
		headerView.setPadding(0, -headerViewHeight, 0, 0);
		ivArrow.setVisibility(View.VISIBLE);
		tvLastUpdateTime.setText("最后刷新时间: " + ActivityUtils.getLastUpdateTime());
		currentState = DisplayMode.Pull_Down;
		refreshHeaderViewState();
	}
	
	private void initAnimation() {
		upAnim = new RotateAnimation(
				0, -180, 
				Animation.RELATIVE_TO_SELF, 0.5f, 
				Animation.RELATIVE_TO_SELF, 0.5f);
		upAnim.setDuration(500);
		upAnim.setFillAfter(true);
		
		downAnim = new RotateAnimation(
				-180, -360, 
				Animation.RELATIVE_TO_SELF, 0.5f, 
				Animation.RELATIVE_TO_SELF, 0.5f);
		downAnim.setDuration(500);
		downAnim.setFillAfter(true);
	}
	
	private void measureView(View child) {
        ViewGroup.LayoutParams lp = child.getLayoutParams();
        if (lp == null) {
        	lp = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }

//        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0, lp.width);
//        
//        int lpHeight = lp.height;
//        int childHeightSpec;
//        if (lpHeight > 0) {
//            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);
//        } else {
//            childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
//        }
//        child.measure(childWidthSpec, childHeightSpec);
        
        child.measure(lp.width, lp.height);
    }

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,int visibleItemCount, int totalItemCount) {
		
		firstVisibleItemPosition = firstVisibleItem;
		
		float listHeight = view.getHeight();

		if(view.getChildCount()>0){
			
			float firstY = view.getChildAt(0).getY();
			float lastY = view.getChildAt(getChildCount()-1).getY();
			int lastHeight = view.getChildAt(getChildCount()-1).getHeight();
			float bottomY = lastY + lastHeight;
			
			if(firstY==0){
				isScroll2Top = true;
			}else{
				isScroll2Top = false;
			}
			
			if(listHeight == bottomY) {
				isScroll2Bottom = true;
			} else {
				isScroll2Bottom = false;
			}
		}else{
			isScroll2Top = false;
			isScroll2Bottom = false;
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {

		if(scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
			
			if(isScroll2Bottom) {		// 滚动到底部
				if(mOnRefreshListener != null ) {
					mOnRefreshListener.onLoadMoring(true);
				}
			}
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			downY = (int) ev.getY();
			break;
		case MotionEvent.ACTION_UP:
			upY = (int) ev.getY();
			
			if(currentState == DisplayMode.Pull_Down) {		// 松开时, 当前显示的状态为下拉状态, 执行隐藏headerView的操作
				headerView.setPadding(0, -headerViewHeight, 0, 0);
			} else if(currentState == DisplayMode.Release_Refresh) {	// 松开时, 当前显示的状态为松开刷新状态, 执行刷新的操作
				headerView.setPadding(0, 0, 0, 0);
				currentState = DisplayMode.Refreshing;
				refreshHeaderViewState();
				if(mOnRefreshListener != null) {
					if(isScroll2Top && !isScroll2Bottom){
						mOnRefreshListener.onLoadfresh(0,0);
					}
				}
			}
			
			break;
		case MotionEvent.ACTION_MOVE:
			int moveY = (int) ev.getY();	// 移动中的y轴的偏移量
			int diffY = moveY - downY;
			
			if(diffY<5){
				return  super.onTouchEvent(ev);
			}
			
			int paddingTop = -headerViewHeight + (diffY / 2);
			
			if(firstVisibleItemPosition == 0 && paddingTop > -headerViewHeight) {
				
				if(paddingTop > 0
						&& currentState == DisplayMode.Pull_Down) {
					currentState = DisplayMode.Release_Refresh;
					refreshHeaderViewState();
					tvLastUpdateTime.setText("最后刷新时间: " + ActivityUtils.getLastUpdateTime());
				} else if(paddingTop <= 0
						&& currentState == DisplayMode.Release_Refresh) {
					currentState = DisplayMode.Pull_Down;
					refreshHeaderViewState();
					tvLastUpdateTime.setText("最后刷新时间: " + ActivityUtils.getLastUpdateTime());
				}
				headerView.setPadding(0, paddingTop, 0, 0);
			}
			
			break;
		default:
			break;
		}
		return super.onTouchEvent(ev);
	}
	
	/**
	 * 设置刷新的监听事件
	 * @param listener
	 */
	public void setOnRefreshListener(OnRefreshListener listener) {
		this.mOnRefreshListener = listener;
	}
	
}
