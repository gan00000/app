package com.efun.platform.module.welfare.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.efun.platform.AndroidScape.E_color;
import com.efun.platform.AndroidScape.E_dimens;
/**
 * 彩色圆框进度条
 * @author 黄杨怀
 */
public class RoundProgress extends View{
	/**
	 * 默认最大数值
	 */
	private int maxProgress = 100;
	/**
	 * 默认当前数值
	 */
	private int progress = 20;//初始化
	/**
	 * 色宽 单位PX
	 */
	private float progressStrokeWidth = 8;
	/**
	 * 渐变颜色数组
	 */
	private int[] gradientColors = null; 
	/**
	 * 内圆宽高和半径
	 */
	private float OvalWidth,OvalHeight,OvalR;
	/**
	 * {@link Context}
	 */
	private Context mContext;
	/**
	 * 外圆区域
	 */
	private RectF ovalOut;
	/**
	 * 色圆区域
	 */
	private RectF oval;
	private Paint paint;
	public RoundProgress(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO 自动生成的构造函数存根
		mContext = context;
		progressStrokeWidth = context.getResources().getDimension(E_dimens.e_size_8);
		oval = new RectF();
		ovalOut = new RectF();
		paint = new Paint();
	}
 
	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO 自动生成的方法存根
		int width = this.getWidth();
		int height = this.getHeight();
 
		if(width!=height){
			int min=Math.min(width, height);
			width=min;
			height=min;
		}
		
		paint.setAntiAlias(true); // 设置画笔为抗锯齿
		paint.setColor(Color.WHITE);
		paint.setStrokeWidth(progressStrokeWidth); //线宽
		paint.setStyle(Style.FILL);
		
		
		ovalOut.left = progressStrokeWidth / 2; // 左上角x
		ovalOut.top = progressStrokeWidth / 2; // 左上角y
		ovalOut.right = width - progressStrokeWidth / 2; // 左下角x
		ovalOut.bottom = height - progressStrokeWidth / 2; // 右下角y	
		canvas.drawArc(ovalOut, -90, 360, false, paint);		
 
		paint.setColor(mContext.getResources().getColor(E_color.e_cbcbcb)); // 设置画笔颜色
		paint.setStrokeWidth(progressStrokeWidth); //线宽
		paint.setStyle(Style.STROKE);
 
		oval.left = progressStrokeWidth / 2+2*progressStrokeWidth; // 左上角x
		oval.top = progressStrokeWidth / 2+2*progressStrokeWidth; // 左上角y
		oval.right = width - progressStrokeWidth / 2-2*progressStrokeWidth; // 左下角x
		oval.bottom = height - progressStrokeWidth / 2-2*progressStrokeWidth; // 右下角y	
		//圆的坐标，半径
		OvalWidth = width/2; 
		OvalHeight = height/2;
		OvalR = (oval.bottom -  progressStrokeWidth / 2-2*progressStrokeWidth)/2;
		
		canvas.drawArc(oval, -90, 360, false, paint); // 绘制白色圆圈，即进度条背景
		RadialGradient gradient = null;
		if(gradientColors != null){
			gradient = new RadialGradient(width/2+progressStrokeWidth, progressStrokeWidth, height+2*progressStrokeWidth, gradientColors, null,Shader.TileMode.MIRROR);
		}else{
			gradient = new RadialGradient(width/2+progressStrokeWidth, progressStrokeWidth, height+2*progressStrokeWidth, new int[] {    
					Color.WHITE,
					Color.BLACK, 
					Color.BLACK}, null,    
			        Shader.TileMode.MIRROR);
		}		 
		paint.setShader(gradient);
		
		canvas.drawArc(oval, -90, ((float) progress / maxProgress) * 360, false, paint); // 绘制进度圆弧，这里是蓝色		
		paint.setShader(null);
		paint.setColor(mContext.getResources().getColor(E_color.e_cbcbcb));
		paint.setStrokeWidth(1);
		canvas.drawColor(Color.TRANSPARENT);
		paint.setStyle(Style.STROKE);
		if((((float) progress / maxProgress) * 360) > 0 && (((float) progress / maxProgress) * 360) <= 90){			
			NortheastOrientation(canvas,1.5f);			
		}else if((((float) progress / maxProgress) * 360) > 90 && (((float) progress / maxProgress) * 360) <= 180){			
			SoutheastOrientation(canvas,1.5f);
		}else if((((float) progress / maxProgress) * 360) > 180 && (((float) progress / maxProgress) * 360) <= 270){			
			SouthwestOrientation(canvas,1.5f);
		}else if((((float) progress / maxProgress) * 360) > 270 && (((float) progress / maxProgress) * 360) <= 360){			
			NorthwestOrientation(canvas,1.5f);
		}
		
		paint.setColor(Color.WHITE);
		paint.setStyle(Style.FILL);
		if((((float) progress / maxProgress) * 360) > 0 && (((float) progress / maxProgress) * 360) <= 90){			
			double paramDouble = ((float) progress / maxProgress) * 360;
			canvas.drawCircle((float)(OvalWidth+OvalR*Math.sin(paramDouble*Math.PI/180)), (float)(OvalHeight-OvalR*Math.cos(paramDouble*Math.PI/180)), 1.5f*progressStrokeWidth-1, paint);			
		}else if((((float) progress / maxProgress) * 360) > 90 && (((float) progress / maxProgress) * 360) <= 180){			
			double paramDouble = ((float) progress / maxProgress) * 360-90;
			canvas.drawCircle((float)(OvalWidth+OvalR*Math.cos(paramDouble*Math.PI/180)), (float)(OvalHeight+OvalR*Math.sin(paramDouble*Math.PI/180)), 1.5f*progressStrokeWidth-1, paint);
		}else if((((float) progress / maxProgress) * 360) > 180 && (((float) progress / maxProgress) * 360) <= 270){			
			double paramDouble = ((float) progress / maxProgress) * 360-180;
			canvas.drawCircle((float)(OvalWidth-OvalR*Math.sin(paramDouble*Math.PI/180)), (float)(OvalHeight+OvalR*Math.cos(paramDouble*Math.PI/180)), 1.5f*progressStrokeWidth-1, paint);
		}else if((((float) progress / maxProgress) * 360) > 270 && (((float) progress / maxProgress) * 360) <= 360){			
			double paramDouble = ((float) progress / maxProgress) * 360-270;
			canvas.drawCircle((float)(OvalWidth-OvalR*Math.cos(paramDouble*Math.PI/180)), (float)(OvalHeight-OvalR*Math.sin(paramDouble*Math.PI/180)), 1.5f*progressStrokeWidth-1, paint);
		}		
		
		paint.setColor(mContext.getResources().getColor(E_color.e_17c6fd));
		paint.setStyle(Style.FILL);
		if((((float) progress / maxProgress) * 360) > 0 && (((float) progress / maxProgress) * 360) <= 90){			
			NortheastOrientation(canvas,1f);			
		}else if((((float) progress / maxProgress) * 360) > 90 && (((float) progress / maxProgress) * 360) <= 180){			
			SoutheastOrientation(canvas,1f);
		}else if((((float) progress / maxProgress) * 360) > 180 && (((float) progress / maxProgress) * 360) <= 270){			
			SouthwestOrientation(canvas,1f);
		}else if((((float) progress / maxProgress) * 360) > 270 && (((float) progress / maxProgress) * 360) <= 360){			
			NorthwestOrientation(canvas,1f);
		}		
		
		paint.setColor(mContext.getResources().getColor(E_color.e_e88c39));
		paint.setStyle(Style.FILL);
		paint.setStrokeWidth(1);
		paint.setAntiAlias(true);
		String text = progress+"";
		int textHeight = height / 4;
		paint.setTextSize(textHeight);
		int textWidth = (int) paint.measureText(text, 0, text.length());
		canvas.drawText(text, width / 2 - textWidth / 2, height / 2 +textHeight/2-progressStrokeWidth, paint);
		
		super.onDraw(canvas);
	}

	/**
	 * 西北方向角
	 * @param canvas
	 * @param ScaleValue
	 */
	private void NorthwestOrientation(Canvas canvas,float ScaleValue) {
		double paramDouble = ((float) progress / maxProgress) * 360-270;
		canvas.drawCircle((float)(OvalWidth-OvalR*Math.cos(paramDouble*Math.PI/180)), (float)(OvalHeight-OvalR*Math.sin(paramDouble*Math.PI/180)), ScaleValue*progressStrokeWidth, paint);
	}

	/**
	 * 西南方向角
	 * @param canvas
	 * @param ScaleValue
	 */
	private void SouthwestOrientation(Canvas canvas,float ScaleValue) {
		double paramDouble = ((float) progress / maxProgress) * 360-180;
		canvas.drawCircle((float)(OvalWidth-OvalR*Math.sin(paramDouble*Math.PI/180)), (float)(OvalHeight+OvalR*Math.cos(paramDouble*Math.PI/180)), ScaleValue*progressStrokeWidth, paint);
	}
	
	/**
	 * 东南方向角
	 * @param canvas
	 * @param ScaleValue
	 */
	private void SoutheastOrientation(Canvas canvas,float ScaleValue) {
		double paramDouble = ((float) progress / maxProgress) * 360-90;
		canvas.drawCircle((float)(OvalWidth+OvalR*Math.cos(paramDouble*Math.PI/180)), (float)(OvalHeight+OvalR*Math.sin(paramDouble*Math.PI/180)), ScaleValue*progressStrokeWidth, paint);
	}

	/**
	 * 东北方向角
	 * @param canvas
	 * @param ScaleValue
	 */
	private void NortheastOrientation(Canvas canvas,float ScaleValue) {
		double paramDouble = ((float) progress / maxProgress) * 360;
		canvas.drawCircle((float)(OvalWidth+OvalR*Math.sin(paramDouble*Math.PI/180)), (float)(OvalHeight-OvalR*Math.cos(paramDouble*Math.PI/180)), ScaleValue*progressStrokeWidth, paint);
	}
 
 
	/**
	 * 设置最大数值
	 * @param maxProgress
	 */
	public void setMaxProgress(int maxProgress) {
		this.maxProgress = maxProgress;
	}	
 
	/**
	 * 设置色圆宽度  单位PX
	 * @param progressStrokeWidth
	 */
	public void setProgressStrokeWidth(int progressStrokeWidth) {
		this.progressStrokeWidth = progressStrokeWidth;
	}	

	/**
	 * 设置渐变颜色数组
	 * @param gradientColors
	 */
	public void setGradientColors(int[] gradientColors) {
		this.gradientColors = transColors(gradientColors);
		
	}
	/**
	 * 设置当前数值
	 * @param maxProgress
	 */
	public void setProgress(int progress) {
		this.progress = progress;
	}
	/**
	 * 重绘
	 */
	public void onInvalidate(){
		this.invalidate();
	}
	
	private int[] transColors(int[] gradientColors){
		int[] colors = new int[gradientColors.length];
		for (int i = 0; i < colors.length; i++) {
			colors[i] = getResources().getColor(gradientColors[i]);
		}
		return colors;
	}
}
