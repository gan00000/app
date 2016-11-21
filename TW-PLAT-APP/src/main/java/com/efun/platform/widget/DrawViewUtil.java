package com.efun.platform.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;
import com.efun.core.tools.EfunResourceUtil;
import com.efun.platform.AndroidScape.E_color;

public class DrawViewUtil extends TextView {
	private float rightwidth,mwidth,mheight;
	private Context context;
	private int curColor = -1;
	public DrawViewUtil(Context context) {
		super(context);
		this.context = context;
	}

	public DrawViewUtil(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context = context;
		// TODO Auto-generated constructor stub
	}

	public DrawViewUtil(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		// TODO Auto-generated constructor stub
	}

	public float getRightwidth() {
		return rightwidth;
	}
	public void setRightwidth(float rightwidth) {
		this.rightwidth = rightwidth;
	}
	public float getMwidth() {
		return mwidth;
	}
	public void setMwidth(float mwidth) {
		this.mwidth = mwidth;
	}
	public float getMheight() {
		return mheight;
	}
	public void setMheight(float mheight) {
		this.mheight = mheight;
	}
	public void setCurColor(int curColor){
		this.curColor = curColor;
	}

	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		Paint paint = new Paint();
		//设置颜色
		if(curColor != -1){
			paint.setColor(this.getResources().getColor(curColor));
		}else{
			paint.setColor(this.getResources().getColor(E_color.e_42bd41));
		}
        //让画出的图形是实心的  
        paint.setStyle(Paint.Style.FILL);  
        //设置画出的线的 粗细程度  
        paint.setStrokeWidth(1);
        canvas.drawRect(0, 0, rightwidth, mheight, paint);
        paint.setColor(this.getResources().getColor(E_color.e_e2e6e9));
        canvas.drawRect(rightwidth, 0, mwidth, mheight, paint);
		super.draw(canvas);
	}
}
