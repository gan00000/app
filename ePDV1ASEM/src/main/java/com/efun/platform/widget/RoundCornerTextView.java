package com.efun.platform.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * @author mashaojie
 */
@SuppressLint("DrawAllocation")
public class RoundCornerTextView extends TextView {
	private Paint paint;
	private final int DEF_RADIUS = 25;
	private int cornerRadius;

	public RoundCornerTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		cornerRadius = DEF_RADIUS;
		paint = new Paint();
		paint.setColor(Color.BLUE);
		paint.setAntiAlias(true);
		paint.setStrokeJoin(Paint.Join.ROUND);
		paint.setStrokeCap(Paint.Cap.ROUND);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		int mWidth = getWidth();
		int mHeight = getHeight();
		if (mHeight < cornerRadius * 2) {
			cornerRadius = mHeight / 2;
		} else if (cornerRadius < 0) {
			cornerRadius = 0;
		}
		Path p = new Path();
		p.moveTo(cornerRadius, 0);
		p.lineTo(mWidth, 0);
		p.lineTo(mWidth, mHeight);
		p.lineTo(cornerRadius * 2, mHeight);
		RectF oval1 = new RectF(cornerRadius, mHeight - cornerRadius * 2,
				cornerRadius * 3, mHeight);
		p.arcTo(oval1, 90, 90);
		p.lineTo(cornerRadius, cornerRadius);
		RectF oval2 = new RectF(-cornerRadius, 0, cornerRadius,
				cornerRadius * 2);
		p.moveTo(cornerRadius, 0);
		p.arcTo(oval2, 270, 90);
		p.close();
		canvas.drawPath(p, paint);
		super.onDraw(canvas);
	}

	/**
	 * 设置颜色
	 * 
	 * @param color
	 */
	public void setColor(int color) {
		if (paint != null) {
			paint.setColor(color);
		}
	}

	/**
	 * 设置圆角弧度
	 * 
	 * @param cornerRadius
	 */
	public void setCornerRadius(int cornerRadius) {
		this.cornerRadius = cornerRadius;
	}
}
