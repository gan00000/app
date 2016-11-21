package com.efun.platform.module.summary.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.efun.platform.AndroidScape.E_drawable;
import com.efun.platform.image.ImageManager;
import com.efun.platform.image.core.DisplayImageOptions;
import com.efun.platform.module.base.impl.OnEfunItemClickListener;
import com.efun.platform.module.summary.bean.BannerItemBean;

/**
 * Banner Adapter
 * 
 * @author itxuxxey
 * 
 */
public class BannerAdapter extends PagerAdapter {
	private OnEfunItemClickListener mOnEfunItemClickListener;
	private ArrayList<BannerItemBean> mImages;
	private Context mContext;
	private DisplayImageOptions mDisplayImageOptions;
	public BannerAdapter(Context context) {
		this.mContext = context;
		BitmapFactory.Options options = new BitmapFactory.Options();  
		options.inSampleSize = 2;
		mDisplayImageOptions = new DisplayImageOptions.Builder()
			.cacheOnDisk(true)
			.considerExifParams(true)
			.showImageForEmptyUri(ImageManager.getRectangle_BD_H(mContext))
			.showImageOnFail(ImageManager.getRectangle_BD_H(mContext))
			.showImageOnLoading(ImageManager.getRectangle_BD_H(mContext))
			.decodingOptions(options).build();
	}

	public void refresh(ArrayList<BannerItemBean> images){
		this.mImages = images;
		notifyDataSetChanged();
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	@Override
	public int getCount() {
		return mImages.size();
	}

	@Override
	public Object instantiateItem(ViewGroup view, int position) {
		ImageView imageView = new ImageView(mContext);
		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		ImageManager.displayImage(mImages.get(position).getPic(), imageView,mDisplayImageOptions);
		final int index = position;
		if(mOnEfunItemClickListener!=null){
			imageView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					mOnEfunItemClickListener.onItemClick(index);
				}
			});
		view.addView(imageView, 0);
		}
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

	public void setOnEfunItemClickListener(
			OnEfunItemClickListener mOnEfunItemClickListener) {
		this.mOnEfunItemClickListener = mOnEfunItemClickListener;
	}
	

}
