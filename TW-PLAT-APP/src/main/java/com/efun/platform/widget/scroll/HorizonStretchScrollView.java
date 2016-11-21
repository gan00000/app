package com.efun.platform.widget.scroll;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;

/**
 * 水平彈性ScrollView
 * 
 * @author Jesse
 * 
 */
public class HorizonStretchScrollView extends HorizontalScrollView {
	private static final int MSG_REST_POSITION = 0x01;
	private static final int MAX_SCROLL_WIDTH = 400;
	private static final float SCROLL_RATIO = 0.4f;
	private View mChildRootView;
	private float mTouchX;
	private boolean mTouchStop = false;
	private int mScrollX = 0;
	private int mScrollDx = 0;
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (MSG_REST_POSITION == msg.what) {
				if (mScrollX != 0 && mTouchStop) {
					mScrollX -= mScrollDx;
					if ((mScrollDx < 0 && mScrollX > 0)
							|| (mScrollDx > 0 && mScrollX < 0)) {
						mScrollX = 0;
					}
					mChildRootView.scrollTo(mScrollX, 0);
					sendEmptyMessageDelayed(MSG_REST_POSITION, 20);
				}
			}
		}
	};

	public HorizonStretchScrollView(Context context) {
		super(context);
		init();
	}

	public HorizonStretchScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public HorizonStretchScrollView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	@SuppressLint("NewApi")
	private void init() {
		// set scroll mode
		if(Build.VERSION.SDK_INT > 8){
    		setOverScrollMode(OVER_SCROLL_NEVER);
    	}
	}

	@Override
	protected void onFinishInflate() {
		if (getChildCount() > 0) {
			// when finished inflating from layout xml, get the first child view
			mChildRootView = getChildAt(0);
		}
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			mTouchX = ev.getX();
		}
		return super.onInterceptTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (null != mChildRootView) {
			doTouchEvent(ev);
		}
		return super.onTouchEvent(ev);
	}

	private void doTouchEvent(MotionEvent ev) {
		int action = ev.getAction();

		switch (action) {
		case MotionEvent.ACTION_UP:
			mScrollX = mChildRootView.getScrollX();
			if (mScrollX != 0) {
				mTouchStop = true;
				mScrollDx = (int) (mScrollX / 10.0f);
				mHandler.sendEmptyMessage(MSG_REST_POSITION);
			}
			break;

		case MotionEvent.ACTION_MOVE:
			float nowX = ev.getX();
			int deltaX = (int) (mTouchX - nowX);
			mTouchX = nowX;
			if (isNeedMove()) {
				int offset = mChildRootView.getScrollX();
				if (offset < MAX_SCROLL_WIDTH && offset > -MAX_SCROLL_WIDTH) {
					mChildRootView.scrollBy((int) (deltaX * SCROLL_RATIO), 0);
					mTouchStop = false;
				}
			}
			break;

		default:
			break;
		}
	}

	private boolean isNeedMove() {
		int viewWidth = mChildRootView.getMeasuredWidth();
		int scrollWidth = getWidth();
		int offset = viewWidth - scrollWidth;
		int scrollX = getScrollX();
		return scrollX == 0 || scrollX == offset;
	}
}