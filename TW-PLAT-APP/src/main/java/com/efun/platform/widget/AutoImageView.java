package com.efun.platform.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.efun.core.tools.EfunScreenUtil;

public abstract class AutoImageView extends ImageView{
	private int screenWidth;
	private double picWidth,picHeight;
	public AutoImageView(Context context) {
		super(context);
		init();
	}

	public AutoImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public AutoImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	private void init(){
		screenWidth = EfunScreenUtil.getInStance((Activity)getContext()).getPxWidth();
		picHeight = screenWidth * picHeight();
	}

	@Override
	public void setImageBitmap(Bitmap bitmap) {
		Bitmap bmp = null;
		try {
			if(screenOrientation()){
				picWidth = screenWidth;
			}else{
				picWidth = picHeight * picWidth();
			}
			bmp = ThumbnailUtils.extractThumbnail(bitmap, (int)picWidth, (int)picHeight, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
			System.gc();
		} catch (Exception e) {
			// TODO: handle exception
		}
		super.setImageBitmap(bmp);
		
	}
	
	//相对于屏幕宽度
	public abstract double picHeight();
	//相对图片高度
	public double picWidth(){
		return 0.75;
	}
	
	public abstract boolean screenOrientation();
	
}
