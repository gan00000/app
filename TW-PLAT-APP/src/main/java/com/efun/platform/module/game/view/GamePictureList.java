package com.efun.platform.module.game.view;

import java.util.HashMap;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.efun.platform.image.ImageManager;
import com.efun.platform.image.core.DisplayImageOptions;
import com.efun.platform.widget.ListContainer;
/**
 * 游戏截图控件
 * @author Jesse
 *
 */
public class GamePictureList extends ListContainer{
	private final String KEY_Image = "KEY_Image";
	private DisplayImageOptions mDisplayImageOptions;
	
	public GamePictureList(Context context) {
		super(context);
		setDisPlayImageOptions(context);
		
	}
	public GamePictureList(Context context, AttributeSet attrs) {
		super(context, attrs);
		setDisPlayImageOptions(context);
	}
	
	private void setDisPlayImageOptions(Context context) {
			mDisplayImageOptions = new DisplayImageOptions.Builder()
			.cacheOnDisk(true)
			.considerExifParams(true)
			.decodingOptions(ImageManager.getOptions(context)).build();
	}

	
	@Override
	public void decorateItemView(View itemView, int position) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveItemViews(View itemView, HashMap<String, View> itemMap) {
		itemMap.put(KEY_Image, itemView);
	}

	@Override
	public void setValuesInItem(HashMap<String, View> itemMap, int index,Object object) {
		Log.e("efun", "picture:"+index);
		String url = (String) object;
//		GameShotImageView icon = (GameShotImageView) itemMap.get(KEY_Image);
		GameShotImageViewVideo icon = (GameShotImageViewVideo) itemMap.get(KEY_Image);
//		icon.setScreenOrientation(screenOrientation);
		if(screenOrientation){
			icon.getmImageView().setImageBitmap(ImageManager.createBitmap(getContext(), ImageManager.IMAGE_RECTANGLE_H));
		}else{
			icon.getmImageView().setImageBitmap(ImageManager.createBitmap(getContext(), ImageManager.IMAGE_RECTANGLE_V));
		}
		if(!TextUtils.isEmpty(url)){
			ImageManager.displayImage(url,icon.getmImageView(),mDisplayImageOptions);
		}
		if(isVideo && index == 0){
			icon.getSurLayout().setVisibility(View.VISIBLE);
		}
	}
	@Override
	public View createItemViewIfNoRes() {
//		return new GameShotImageView(getContext());
		return new GameShotImageViewVideo(getContext(),screenOrientation);
	}

}
