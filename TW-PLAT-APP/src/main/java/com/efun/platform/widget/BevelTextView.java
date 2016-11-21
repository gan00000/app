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
 * 斜边TextView
 * @author Jesse
 * 
 */
public class BevelTextView extends TextView {
	private int mProportion = 1;
//	private int triangleBgColor = -1;

	public BevelTextView(Context context) {
		super(context);
	}

	public BevelTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		int bottom = getBottom();
		int right = getRight();
		/**
		 * 三角形坐标点
		 */
		int[] pointStart = new int[] { right - (bottom) / mProportion, 0 };
		int[] pointCenter = new int[] { right, 0 };
		int[] pointEnd = new int[] { right, (bottom) };
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
//		if(triangleBgColor != -1){
//			mPaint.setColor(triangleBgColor);
//		}else{
//			mPaint.setColor(Color.WHITE);
//		}
		mPaint.setColor(E_color.e_417fd2);
		canvas.drawPath(mPath, mPaint);
		super.onDraw(canvas);

	}

	/**
	 * 设置背景颜色
	 * 
	 * @param color
	 */
	public void setColor(int color) {
		setBackgroundResource(color);
	}
	
//	public void setTriangleColorBg(int color){
//		triangleBgColor = color;
//	}

	/**
	 * 设置斜边比例
	 * 
	 * @param proportion
	 */
	public void setBevel(int proportion) {
		if (proportion < 1) {
			throw new IllegalArgumentException("proportion 小于 1");
		}
		mProportion = proportion;
		invalidate();
	}

}
