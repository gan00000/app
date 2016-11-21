package com.efun.platform.module.cs.view;

import android.content.Context;
import android.util.AttributeSet;

import com.efun.platform.widget.AutoImageView;
/**
 * 客服Banner
 * @author Jesse
 *
 */
public class CsBannerImageView extends AutoImageView {
	public CsBannerImageView(Context context) {
		super(context);
	}

	public CsBannerImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CsBannerImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public double picHeight() {
		return 0.5;
	}

	@Override
	public boolean screenOrientation() {
		return true;
	}
}
