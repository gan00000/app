package com.efun.platform.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 单向箭头
 * 
 * @author Jesse
 * 
 */
@SuppressLint("DrawAllocation")
public class ArrowView extends RelativeLayout {
	private BackgroundView background;

	public ArrowView(Context context) {
		super(context);
		init();
	}

	public ArrowView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		background = new BackgroundView(getContext());
		background.setBackgroundColor(Color.TRANSPARENT);
		addView(background, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
	}

	/**
	 * 设置背景颜色
	 * 
	 * @param color
	 */
	public void setColor(int color) {
		this.color = color;
		background.invalidate();
	}

	/**
	 * 设置斜边比例
	 * 
	 * @param proportion
	 */
	public void setBevel(int proportion) {
		mProportion = proportion;
		background.invalidate();
	}

	private int mProportion = 1;
	private int color = Color.WHITE;

	private class BackgroundView extends TextView {
		public BackgroundView(Context context) {
			super(context);
		}

		@Override
		protected void onDraw(Canvas canvas) {
			int bottom = getBottom();
			int right = getRight();
			int[] point1 = new int[] { 0, 0 };
			int[] point2 = new int[] { right - bottom / 2 / mProportion, 0 };
			int[] point3 = new int[] { right, bottom / 2 };
			int[] point4 = new int[] { right - bottom / 2 / mProportion, bottom };
			int[] point5 = new int[] { 0, bottom };
			Path mPath = new Path();
			mPath.moveTo(point1[0], point1[1]);
			mPath.lineTo(point2[0], point2[1]);
			mPath.lineTo(point3[0], point3[1]);
			mPath.lineTo(point4[0], point4[1]);
			mPath.lineTo(point5[0], point5[1]);
			mPath.close();
			Paint mPaint = new Paint();
			mPaint.setColor(color);
			canvas.drawPath(mPath, mPaint);
			super.onDraw(canvas);
		}
	}
}
