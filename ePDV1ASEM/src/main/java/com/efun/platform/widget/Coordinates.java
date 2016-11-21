package com.efun.platform.widget;

import com.efun.platform.AndroidScape.E_color;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 向上箭头
 * 
 * @author itxuxxey
 * 
 */
@SuppressLint("DrawAllocation")
public class Coordinates extends TextView {
	public Coordinates(Context context) {
		super(context);
		init();
	}

	public Coordinates(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		setBackgroundColor(Color.TRANSPARENT);
		color = getResources().getColor(E_color.e_e3ebfe);
	}

	private double mProportion = 1;
	private int color;

	@Override
	protected void onDraw(Canvas canvas) {
		int bottom = getBottom();
		int right = getRight();
		/**
		 * 三角形坐标点
		 */
		int[] pointStart = new int[] { (int) (right - (bottom) * mProportion),
				0 };
		int[] pointCenter = new int[] {
				(int) (right - (bottom) * mProportion + bottom / Math.sqrt(3)),
				bottom };
		int[] pointEnd = new int[] {
				(int) (right - (bottom) * mProportion - bottom / Math.sqrt(3)),
				bottom };
		/**
		 * 绘制三角形
		 */
		Path mPath = new Path();
		mPath.moveTo(pointStart[0], pointStart[1]);
		mPath.lineTo(pointCenter[0], pointCenter[1]);
		mPath.lineTo(pointEnd[0], pointEnd[1]);
		mPath.lineTo(pointStart[0], pointStart[1]);
		mPath.close();
		Paint mPaint = new Paint();
		/**
		 * 三角形内部颜色
		 */
		mPaint.setColor(color);
		canvas.drawPath(mPath, mPaint);
		super.onDraw(canvas);

	}

	/**
	 * 设置背景颜色
	 * 
	 * @param color
	 */
	public void setColor(int color) {
		this.color = color;
		invalidate();
	}

	/**
	 * 设置斜边比例
	 * 
	 * @param proportion
	 */
	public void setBevel(double proportion) {
		if (proportion < 1) {
			throw new IllegalArgumentException("proportion 小于 1");
		}
		mProportion = proportion;
		invalidate();
	}

}
