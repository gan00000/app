package com.efun.platform.module.logo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.efun.platform.AndroidScape.E_drawable;
import com.efun.platform.module.logo.view.ViewPagerImage;
/**
 * Logo适配器
 * @author Jesse
 *
 */
public class LogoAdapter extends PagerAdapter{
	private Context mContext;
	private ViewPagerImage[] logoImages;
	public LogoAdapter(Context context) {
		this.mContext = context;
		logoImages = new ViewPagerImage[imageRes.length];
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	@Override
	public int getCount() {
		return imageRes.length;
	}

	@Override
	public Object instantiateItem(ViewGroup view, int position) {
		ViewPagerImage imageView = new ViewPagerImage(mContext);
		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		imageView.setImageResource(imageRes[position]);
		view.addView(imageView, 0);
		logoImages[position] = imageView; 		
		return imageView;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view.equals(object);
	}

	@Override
	public void restoreState(Parcelable state, ClassLoader loader) {
	}

	@Override
	public Parcelable saveState() {
		return null;
	}
	
	public void destroyImage(){
		for (int i = 0; i < logoImages.length; i++) {
			if(logoImages[i]!=null){
				BitmapDrawable mDrawable =  (BitmapDrawable) logoImages[i].getDrawable();
				Bitmap mBitmap = mDrawable.getBitmap();
				mBitmap.recycle();
				logoImages[i].destroy();
			}
		}
		System.gc();
	}
	
	private int[] imageRes = new int[]{
			E_drawable.efun_pd_logo_1,
			E_drawable.efun_pd_logo_2,
			E_drawable.efun_pd_logo_3};
}
