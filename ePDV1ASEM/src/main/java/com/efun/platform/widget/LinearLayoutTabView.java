package com.efun.platform.widget;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

public class LinearLayoutTabView extends LinearLayout{
	public static final int KEYBORAD_HIDE = 0;
	public static final int KEYBORAD_SHOW = 1;
	private static final int SOFTKEYPAD_MIN_HEIGHT = 50;
	private Handler uiHandler = new Handler();
	private KeyBordStateListener mKeyBordStateListener;

	public LinearLayoutTabView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public LinearLayoutTabView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onSizeChanged(int w, final int h, int oldw, final int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		Log.e("yang", "======onSizeChanged========w:"+w+"///h:"+h+"//oldw:"+oldw+"//oldh:"+oldh);
			uiHandler.post(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					if(oldh - h > SOFTKEYPAD_MIN_HEIGHT){
						mKeyBordStateListener.stateChange(KEYBORAD_SHOW);
					}else{
						if(mKeyBordStateListener != null){
							mKeyBordStateListener.stateChange(KEYBORAD_HIDE);
						}
					}
				}
			});
		
	}
	
	public void setKeyBordStateListener(KeyBordStateListener mKeyBordStateListener) {
		this.mKeyBordStateListener = mKeyBordStateListener;
	}

	public interface KeyBordStateListener{
		public void stateChange(int state);
	}
}
