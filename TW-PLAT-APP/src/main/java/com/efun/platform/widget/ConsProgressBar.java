package com.efun.platform.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.efun.platform.AndroidScape.E_color;
/**
 * 圆角边框进度条
 * @author  黄杨怀
 *
 */
public class ConsProgressBar extends View {
	private int maxProgress = 100;
	private int progress = 20;
	private int progressStrokeWidth = 2;	
	private Context mContext;	
	Paint paint;

	public ConsProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO 自动生成的构造函数存根
		mContext = context;
		paint = new Paint();
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO 自动生成的方法存根
		super.onDraw(canvas);
		int width = this.getWidth();
		int height = this.getHeight();
				
		width = width - progressStrokeWidth;

		paint.setAntiAlias(true); // 设置画笔为抗锯齿
		paint.setColor(mContext.getResources().getColor(E_color.e_ff999a));
		paint.setStrokeWidth(progressStrokeWidth); // 线宽
		paint.setStyle(Style.STROKE);

		RectF outRecF = new RectF(); // RectF对象
		outRecF.left = progressStrokeWidth; // 左边
		outRecF.top = progressStrokeWidth; // 上边
		outRecF.right = width; // 右边
		outRecF.bottom =height-progressStrokeWidth; // 下边
		canvas.drawRoundRect(outRecF, height/2, height/2, paint); // 绘制圆角矩形
		
		paint.setColor(mContext.getResources().getColor(E_color.e_b6cdff));
		paint.setStyle(Style.FILL);
		
		RectF insideRecF = new RectF(); // RectF对象
		insideRecF.left = progressStrokeWidth * 2; // 左边
		insideRecF.top = progressStrokeWidth * 2; // 上边
		if(progress*width/maxProgress < height){
			insideRecF.right = height;
		}else{			
			insideRecF.right = progress*width/maxProgress - progressStrokeWidth; // 右边		
		}
		
		insideRecF.bottom =height - progressStrokeWidth * 2; // 下边
		canvas.drawRoundRect(insideRecF, (height - progressStrokeWidth)/2, (height - progressStrokeWidth)/2, paint); // 绘制圆角矩形
		}

	public int getMaxProgress() {
		return maxProgress;
	}

	public void setMaxProgress(int maxProgress) {
		if(maxProgress!=0){
			this.maxProgress = maxProgress;
		}
	}

	public void setProgress(int progress) {
		this.progress = progress;
		this.invalidate();
	}

}
