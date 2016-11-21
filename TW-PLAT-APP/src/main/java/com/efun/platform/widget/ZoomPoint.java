package com.efun.platform.widget;

import com.nineoldandroids.animation.ObjectAnimator;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 伸缩点
 * 
 * @author Jesse
 * 
 */
public class ZoomPoint extends ImageView {
	/**
	 * 500毫秒
	 */
	public final int ZOOM_DURATION_500 = 500;
	/**
	 * 1000毫秒
	 */
	public final int ZOOM_DURATION_1000 = 1000;
	/**
	 * 缩放比例 0.5f
	 */
	private final float MIN_SCALE = 0.5f;
	/**
	 * 缩放比例 1.0f
	 */
	private final float MAX_SCALE = 1.0f;
	/**
	 * 默认X轴缩放比例
	 */
	private float scaleX = 0.0f;
	/**
	 * 默认Y轴缩放比例
	 */
	private float scaleY = 0.0f;
	/**
	 * 记录放大点的下标
	 */
	private int status = 0;
	/**
	 * 动画时间
	 */
	private int duration;

	public ZoomPoint(Context context) {
		super(context);
		duration = ZOOM_DURATION_500;
	}

	public ZoomPoint(Context context, AttributeSet attrs) {
		super(context, attrs);
		duration = ZOOM_DURATION_500;
	}

	/**
	 * 缩小
	 */
	public void startZoomInCenter() {
		startZoomAnimation(true);
		status--;
	}

	/**
	 * 放大
	 */
	public void startZoomOutCenter() {
		startZoomAnimation(false);
		status++;
	}
	/**
	 * 启动缩放动画
	 * @param isIn true缩小，false放大
	 */
	private void startZoomAnimation(boolean isIn) {
		if (isIn) {
			if ((scaleX == 0.0f && scaleY == 0.0f)
					|| (scaleX == MAX_SCALE && scaleY == MAX_SCALE)) {
				scaleX = MIN_SCALE;
				scaleY = MIN_SCALE;
			}
		} else {
			if (scaleX == 0.0f && scaleY == 0.0f
					|| (scaleX == MIN_SCALE && scaleY == MIN_SCALE)) {
				scaleX = MAX_SCALE;
				scaleY = MAX_SCALE;
			}
		}
		ObjectAnimator.ofFloat(this, "scaleX", scaleX).setDuration(duration)
				.start();
		ObjectAnimator.ofFloat(this, "scaleY", scaleY).setDuration(duration)
				.start();
	}

	/**
	 * 设置动画时间
	 * 
	 * @param duration
	 */
	public void setZoomDuration(int duration) {
		this.duration = duration;
	}

	/**
	 * 判断是否是普通状态
	 * 
	 * @return
	 */
	public boolean getZoomNormalStatus() {
		if (status == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 恢复
	 */
	public void setZoomRestore() {
		if (status > 0) {
			startZoomInCenter();
		} else if (status < 0) {
			startZoomOutCenter();
		}
	}
	/**
	 * 设置X轴缩放比例
	 * @param scaleX
	 */
	public void setZoomScaleX(float scaleX) {
		this.scaleX = scaleX;
	}
	/**
	 * 设置Y轴缩放比例
	 * @param scaleY
	 */
	public void setZoomScaleY(float scaleY) {
		this.scaleY = scaleY;
	}

}
