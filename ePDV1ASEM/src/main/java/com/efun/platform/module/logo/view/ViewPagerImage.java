package com.efun.platform.module.logo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

public class ViewPagerImage extends ImageView{
	private Bitmap mBitmap;
	public ViewPagerImage(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setImageBitmap(Bitmap bm) {
		mBitmap = bm;
		super.setImageBitmap(bm);
	}
	
	public void destroy(){
		if(mBitmap!=null && !mBitmap.isRecycled()){
			mBitmap.recycle();
		}
	}
}
