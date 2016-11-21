package com.efun.platform.module.utils;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.efun.core.tools.EfunResourceUtil;

/**
 * @Description:自定义对话框
 */
public class CustomProgressDialog extends RelativeLayout {

	private AnimationDrawable mAnimation;
	private Context mContext;
	private ImageView mImageView;
//	private String mLoadingTip;
	private TextView mLoadingTv;
//	private int count = 0;
//	private String oldLoadingTip;
//	private int mResid;

	public CustomProgressDialog(Context context) {
		super(context);
		this.mContext = context;
		initView();
		initData();
	}
	
	public CustomProgressDialog(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		this.mContext = context;
		initView();
		initData();
	}

	public CustomProgressDialog(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.mContext = context;
		initView();
		initData();
	}

	private void initData() {

		mImageView.setBackgroundResource(EfunResourceUtil.findAnimIdByName(getContext(), "frame"));
		// 通过ImageView对象拿到背景显示的AnimationDrawable
		mAnimation = (AnimationDrawable) mImageView.getBackground();
		// 为了防止在onCreate方法中只显示第一帧的解决方案之一
		mImageView.post(new Runnable() {
			@Override
			public void run() {
				mAnimation.start();

			}
		});
		mLoadingTv.setText("加载中...");

	}

	public void setContent(String str) {
		mLoadingTv.setText(str);
	}

	private void initView() {
//		setContentView(EfunResourceUtil.findLayoutIdByName(mContext, "efun_pd_common_progress_dialog"));
		View view = LayoutInflater.from(mContext).inflate(EfunResourceUtil.findLayoutIdByName(mContext, "efun_pd_common_progress_dialog"), null);
		addView(view,
				new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
		mLoadingTv = (TextView) view.findViewById(EfunResourceUtil.findViewIdByName(mContext, "loadingTv"));
		mImageView = (ImageView) view.findViewById(EfunResourceUtil.findViewIdByName(mContext, "loadingIv"));
//		view.setVisibility(View.GONE);
	}

	/*@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		mAnimation.start(); 
		super.onWindowFocusChanged(hasFocus);
	}*/
}
