package com.banhuitong.view.gesture;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.banhuitong.util.Constants;
import com.banhuitong.view.gesture.GestureLockView.Mode;

public class GestureLockViewGroup extends RelativeLayout {

	private static final String TAG = "GestureLockViewGroup";
	/**
	 * 保存所有的GestureLockView
	 */
	private GestureLockView[] mGestureLockViews;
	/**
	 * 每个边上的GestureLockView的个数
	 */
	private int mCount = 3;
	/**
	 * 存储答案
	 */
	private int[] mAnswer = {};
	/**
	 * 保存用户选中的GestureLockView的id
	 */
	private List<Integer> mChoose = new ArrayList<Integer>();

	private Paint mPaint;
	/**
	 * 每个GestureLockView中间的间距 设置为：mGestureLockViewWidth * 25%
	 */
	private int mMarginBetweenLockView = 30;
	/**
	 * GestureLockView的边长 4 * mWidth / ( 5 * mCount + 1 )
	 */
	private int mGestureLockViewWidth;

	/**
	 * GestureLockView无手指触摸的状态下内圆的颜色
	 */
	private int mNoFingerInnerCircleColor = 0xFF939090;
	/**
	 * GestureLockView无手指触摸的状态下外圆的颜色
	 */
	private int mNoFingerOuterCircleColor = 0xFFE0DBDB;
	/**
	 * GestureLockView手指触摸的状态下内圆和外圆的颜色
	 */
	private int mFingerOnColor = Constants.COLOR_YELLOW;
	/**
	 * GestureLockView手指抬起的状态下内圆和外圆的颜色
	 */
	private int mFingerUpColor = Constants.COLOR_YELLOW;
	private int mFingerUpColorWrong = Constants.COLOR_RED;

	/**
	 * 宽度
	 */
	private int mWidth;
	/**
	 * 高度
	 */
	private int mHeight;

	private Path mPath;
	/**
	 * 指引线的开始位置x
	 */
	private int mLastPathX;
	/**
	 * 指引线的开始位置y
	 */
	private int mLastPathY;
	/**
	 * 指引下的结束位置
	 */
	private Point mTmpTarget = new Point();

	/**
	 * 最大尝试次数
	 */
	private int mTryTimes = 4;
	/**
	 * 回调接口
	 */
	private OnGestureLockViewListener mOnGestureLockViewListener;

	public GestureLockViewGroup(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public GestureLockViewGroup(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);

		// 初始化画笔
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setStyle(Paint.Style.STROKE);
		// mPaint.setStrokeWidth(20);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setStrokeJoin(Paint.Join.ROUND);
		// mPaint.setColor(Color.parseColor("#aaffffff"));
		mPath = new Path();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		mWidth = MeasureSpec.getSize(widthMeasureSpec);
		mHeight = MeasureSpec.getSize(heightMeasureSpec);

		mHeight = mWidth = mWidth < mHeight ? mWidth : mHeight;

		// setMeasuredDimension(mWidth, mHeight);

		// 初始化mGestureLockViews
		if (mGestureLockViews == null) {
			mGestureLockViews = new GestureLockView[mCount * mCount];
			// 计算每个GestureLockView的宽度
			mGestureLockViewWidth = (int) (mWidth/8);
			// 计算每个GestureLockView的间距
			mMarginBetweenLockView = (int) (mGestureLockViewWidth*(8-mCount)/(mCount+1));
			// 设置画笔的宽度为GestureLockView的内圆直径稍微小点（不喜欢的话，随便设）
			mPaint.setStrokeWidth(mGestureLockViewWidth * 0.29f);

			for (int i = 0; i < mGestureLockViews.length; i++) {
				// 初始化每个GestureLockView
				mGestureLockViews[i] = new GestureLockView(getContext(),
						mNoFingerInnerCircleColor, mNoFingerOuterCircleColor,
						mFingerOnColor, mFingerUpColor, mFingerUpColorWrong);
				mGestureLockViews[i].setId(i + 1);
				// 设置参数，主要是定位GestureLockView间的位置
				RelativeLayout.LayoutParams lockerParams = new RelativeLayout.LayoutParams(
						mGestureLockViewWidth, mGestureLockViewWidth);

				// 不是每行的第一个，则设置位置为前一个的右边
				if (i % mCount != 0) {
					lockerParams.addRule(RelativeLayout.RIGHT_OF,
							mGestureLockViews[i - 1].getId());
				}
				// 从第二行开始，设置为上一行同一位置View的下面
				if (i > mCount - 1) {
					lockerParams.addRule(RelativeLayout.BELOW,
							mGestureLockViews[i - mCount].getId());
				}
				// 设置右下左上的边距
				int rightMargin = mMarginBetweenLockView;
				int bottomMargin = mMarginBetweenLockView;
				int leftMagin = 0;
				int topMargin = 0;
				/**
				 * 每个View都有右外边距和底外边距 第一行的有上外边距 第一列的有左外边距
				 */
				if (i >= 0 && i < mCount)// 第一行
				{
					topMargin = mMarginBetweenLockView;
				}
				if (i % mCount == 0)// 第一列
				{
					leftMagin = mMarginBetweenLockView;
				}

				lockerParams.setMargins(leftMagin, topMargin, rightMargin,
						bottomMargin);
				mGestureLockViews[i].setMode(Mode.STATUS_NO_FINGER);
				addView(mGestureLockViews[i], lockerParams);
			}
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		int x = (int) event.getX();
		int y = (int) event.getY();

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			// 重置
			reset();
			break;
		case MotionEvent.ACTION_MOVE:
			mPaint.setColor(mFingerOnColor);
			mPaint.setAlpha(50);
			GestureLockView child = getChildIdByPos(x, y);
			if (child != null) {
				int cId = child.getId();
				if (!mChoose.contains(cId)) {
					mChoose.add(cId);
					child.setMode(Mode.STATUS_FINGER_ON);

					// 设置指引线的起点
					mLastPathX = child.getLeft() / 2 + child.getRight() / 2;
					mLastPathY = child.getTop() / 2 + child.getBottom() / 2;

					if (mChoose.size() == 1)// 当前添加为第一个
					{
						mPath.moveTo(mLastPathX, mLastPathY);
					} else
					// 非第一个，将两者使用线连上
					{
						mPath.lineTo(mLastPathX, mLastPathY);
					}

				}
			}
			// 指引线的终点
			mTmpTarget.x = x;
			mTmpTarget.y = y;
			break;
		case MotionEvent.ACTION_UP:

			mPaint.setColor(mFingerUpColor);
			mPaint.setAlpha(100);
			this.mTryTimes--;

			// 回调是否成功
			if (mOnGestureLockViewListener != null && mChoose.size() > 0) {
				mOnGestureLockViewListener.onActionUp(mChoose, mAnswer);
//				if (this.mTryTimes == 0) {
//					mOnGestureLockViewListener.onUnmatchedExceedBoundary();
//				}
			}

			// 将终点设置位置为起点，即取消指引线
			mTmpTarget.x = mLastPathX;
			mTmpTarget.y = mLastPathY;

			// 计算每个元素中箭头需要旋转的角度
			for (int i = 0; i + 1 < mChoose.size(); i++) {
				int childId = mChoose.get(i);
				int nextChildId = mChoose.get(i + 1);

				GestureLockView startChild = (GestureLockView) findViewById(childId);
				GestureLockView nextChild = (GestureLockView) findViewById(nextChildId);

				int dx = nextChild.getLeft() - startChild.getLeft();
				int dy = nextChild.getTop() - startChild.getTop();
				// 计算角度
				int angle = (int) Math.toDegrees(Math.atan2(dy, dx)) + 90;
				startChild.setArrowDegree(angle);
			}
			break;
		}
		invalidate();
		return true;
	}

	public void changeItemMode(boolean isCorrect) {
		for (GestureLockView gestureLockView : mGestureLockViews) {
			if (mChoose.contains(gestureLockView.getId())) {
				if(isCorrect){
					gestureLockView.setMode(Mode.STATUS_FINGER_UP);
				}else{
					gestureLockView.setMode(Mode.STATUS_FINGER_UP_WRONG);
				}
			}
		}
	}

	/**
	 * 
	 * 做一些必要的重置
	 */
	private void reset() {
		mChoose.clear();
		mPath.reset();
		for (GestureLockView gestureLockView : mGestureLockViews) {
			gestureLockView.setMode(Mode.STATUS_NO_FINGER);
			gestureLockView.setArrowDegree(-1);
		}
	}
	
	public void doubleInput() {
		mPath.reset();
		for (GestureLockView gestureLockView : mGestureLockViews) {
			gestureLockView.setMode(Mode.STATUS_NO_FINGER);
			gestureLockView.setArrowDegree(-1);
		}
	}

	/**
	 * 检查用户绘制的手势是否正确
	 * 
	 * @return
	 */
	public boolean checkAnswer(List<Integer> mChoose, int[] mAnswer) {
		if (mAnswer.length != mChoose.size())
			return false;

		for (int i = 0; i < mAnswer.length; i++) {
			if (mAnswer[i] != mChoose.get(i))
				return false;
		}
		return true;
	}

	/**
	 * 检查当前左边是否在child中
	 * 
	 * @param child
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean checkPositionInChild(View child, int x, int y) {

		// 设置了内边距，即x,y必须落入下GestureLockView的内部中间的小区域中，可以通过调整padding使得x,y落入范围不变大，或者不设置padding
		int padding = (int) (mGestureLockViewWidth * 0.15);

		if (x >= child.getLeft() + padding && x <= child.getRight() - padding
				&& y >= child.getTop() + padding
				&& y <= child.getBottom() - padding) {
			return true;
		}
		return false;
	}

	/**
	 * 通过x,y获得落入的GestureLockView
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	private GestureLockView getChildIdByPos(int x, int y) {
		for (GestureLockView gestureLockView : mGestureLockViews) {
			if (checkPositionInChild(gestureLockView, x, y)) {
				return gestureLockView;
			}
		}

		return null;

	}

	/**
	 * 设置回调接口
	 * 
	 * @param listener
	 */
	public void setOnGestureLockViewListener(OnGestureLockViewListener listener) {
		this.mOnGestureLockViewListener = listener;
	}

	/**
	 * 对外公布设置答案的方法
	 * 
	 * @param answer
	 */
	public void setAnswer(int[] answer) {
		this.mAnswer = answer;
	}

	public int[] getAnswer() {
		return this.mAnswer;
	}

	/**
	 * 设置最大实验次数
	 * 
	 * @param boundary
	 */
	public void setUnMatchExceedBoundary(int boundary) {
		this.mTryTimes = boundary;
	}

	@Override
	public void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		// 绘制GestureLockView间的连线
		if (mPath != null) {
			canvas.drawPath(mPath, mPaint);
		}
		// 绘制指引线
		if (mChoose.size() > 0) {
			if (mLastPathX != 0 && mLastPathY != 0)
				canvas.drawLine(mLastPathX, mLastPathY, mTmpTarget.x,
						mTmpTarget.y, mPaint);
		}

	}

	public interface OnGestureLockViewListener {

		public void onActionUp(List<Integer> choose, int[] answer);

		public void onUnmatchedExceedBoundary();

	}
}
