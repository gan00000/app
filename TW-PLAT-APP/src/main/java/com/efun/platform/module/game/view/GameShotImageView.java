package com.efun.platform.module.game.view;

import android.content.Context;
import android.util.AttributeSet;

import com.efun.platform.widget.AutoImageView;
/**
 * 单条游戏截图
 * @author Jesse
 *
 */
public class GameShotImageView extends AutoImageView {
	private boolean screenOrientation;
	public GameShotImageView(Context context) {
		super(context);
	}

	public GameShotImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public GameShotImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public double picHeight() {
		return 0.75;
	}

	@Override
	public boolean screenOrientation() {
		return screenOrientation;
	}

	public void setScreenOrientation(boolean screenOrientation) {
		this.screenOrientation = screenOrientation;
	}

	
	
}
