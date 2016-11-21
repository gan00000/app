package com.efun.platform.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.AbsSeekBar;

public class CurProgressBar extends AbsSeekBar {
	public CurProgressBar(Context context) {
		super(context);
		init(context);
	}
	
	public CurProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	private void init(Context context){
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
}
