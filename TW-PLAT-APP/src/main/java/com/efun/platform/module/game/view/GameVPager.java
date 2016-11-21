package com.efun.platform.module.game.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
/**
 * 游戏详情ViewPager
 * @author Jesse
 *
 */
public class GameVPager extends ViewPager{
	public GameVPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public GameVPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		requestDisallowInterceptTouchEvent(true);
		return super.onInterceptTouchEvent(arg0);
	}
}
