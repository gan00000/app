package com.efun.platform.module.game.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.efun.core.tools.EfunScreenUtil;
import com.efun.platform.AndroidScape.E_color;
import com.efun.platform.AndroidScape.E_dimens;
import com.efun.platform.AndroidScape.E_drawable;
import com.efun.platform.widget.AutoImageView;
/**
 * 单条游戏截图
 * @author Jesse
 *
 */
public class GameShotImageViewVideo extends RelativeLayout {
	private AutoImageView mImageView;
	private boolean screenOrientation;
	private Context mContext;
	private int screenWidth;
	private double picWidth,picHeight;
	private RelativeLayout surLayout;
	public GameShotImageViewVideo(Context context,boolean screenOrientation) {
		super(context);
		this.mContext = context;
		this.screenOrientation = screenOrientation;
		init();
	}

	public GameShotImageViewVideo(Context context, AttributeSet attrs,boolean screenOrientation) {
		super(context, attrs);
		this.mContext = context;
		this.screenOrientation = screenOrientation;
		init();
	}

	public GameShotImageViewVideo(Context context, AttributeSet attrs, int defStyle,boolean screenOrientation) {
		super(context, attrs, defStyle);
		this.mContext = context;
		this.screenOrientation = screenOrientation;
		init();
	}

	private void init(){
		mImageView = new AutoImageView(mContext) {
			
			@Override
			public boolean screenOrientation() {
				// TODO Auto-generated method stub
				return screenOrientation;
			}
			
			@Override
			public double picHeight() {
				// TODO Auto-generated method stub
				return 0.75;
			}
		};
		screenWidth = EfunScreenUtil.getInStance((Activity)getContext()).getPxWidth();
		picHeight = screenWidth * 0.75;
		
		if(screenOrientation){
			picWidth = screenWidth;
		}else{
			picWidth = picHeight * 0.75;
		}
		setLayoutParams(new LayoutParams((int)picWidth, (int)picHeight));
		LayoutParams params = null;
		if(screenOrientation){
			params = new LayoutParams((int)picWidth, (int)picHeight);
		}else{
			params = new LayoutParams((int)picWidth, (int)picHeight);
			params.setMargins((int) mContext.getResources().getDimension(E_dimens.e_size_5), (int) mContext.getResources().getDimension(E_dimens.e_size_5), (int) mContext.getResources().getDimension(E_dimens.e_size_5), (int) mContext.getResources().getDimension(E_dimens.e_size_5));
		}
		addView(mImageView,params);
		surLayout = new RelativeLayout(mContext);
		surLayout.setBackgroundColor(E_color.e_50000000);
		addView(surLayout,params);
		ImageView img = new ImageView(mContext);
		int iconWidth = (int) mContext.getResources().getDimension(E_dimens.e_icon_size_120);
		img.setBackgroundResource(E_drawable.efun_pd_common_video_play_icon);
		LayoutParams param = new LayoutParams(iconWidth, iconWidth);
		param.addRule(RelativeLayout.CENTER_IN_PARENT);
		surLayout.addView(img, param);
		surLayout.setVisibility(View.GONE);
	}
//	
//	@Override
//	public double picHeight() {
//		return 0.75;
//	}
//
//	@Override
//	public boolean screenOrientation() {
//		return screenOrientation;
//	}
	
	

	public void setScreenOrientation(boolean screenOrientation) {
		this.screenOrientation = screenOrientation;
	}

	public AutoImageView getmImageView() {
		return mImageView;
	}

	public RelativeLayout getSurLayout() {
		return surLayout;
	}
}
