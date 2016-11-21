package com.efun.platform.module;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

import com.efun.platform.AndroidScape.E_color;

/**
 * Pop Window 继承 {@link PopupWindow}
 * 
 * @author Jesse
 * 
 */
@SuppressLint("ViewConstructor")
public class PopWindow extends PopupWindow {
	public static final String POP_WINDOW_CODE = "POP_WINDOW_CODE";
	public static final String POP_WINDOW_SHARE = "POP_WINDOW_SHARE";
	public static final String POP_WINDOW_CHOSE_AWARDS = "POP_WINDOW_CHOSE_AWARDS";
	
	public static final String POP_WINDOW_CHOSE_PIC = "POP_WINDOW_CHOSE_PIC";
	public static final String POP_WINDOW_CHOSE_SEX = "POP_WINDOW_CHOSE_SEX";
	public static final String POP_WINDOW_CHOSE_DOWNLOAD_AREA = "POP_WINDOW_CHOSE_DOWNLOAD_AREA";
	public static final String POP_WINDOW_CHOSE_START_APP = "POP_WINDOW_CHOSE_START_APP";
	
	private View mContainer;
	private View mParent;
	private Context mContext;
	
	public PopWindow(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	
	public void createPopWindow(Context context, String popWindowCategory,
			int layoutId, View parent, int anim, OnPopListener onPopListener) {
		mContext = context;
		mParent = parent;
		mContainer = ((Activity) context).getLayoutInflater().inflate(layoutId,
				null);
		mContainer.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		// 设置SelectPicPopupWindow的View
		this.setContentView(mContainer);
		this.setWidth(LayoutParams.MATCH_PARENT);
		this.setHeight(LayoutParams.MATCH_PARENT);
		// 设置SelectPicPopupWindow弹出窗体可点击
		this.setFocusable(true);
		this.setOutsideTouchable(true);
		if (anim != 0) {
			this.setAnimationStyle(anim);
		}
		
		// 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
		this.setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(E_color.e_50000000)));
		// 刷新状态
				this.update();
		if (onPopListener != null) {
			onPopListener.init(this,mParent, mContainer);
		}
	}

	/**
	 * 创建窗口
	 * 
	 * @param context
	 *            上下文
	 * @param popWindowCategory
	 *            窗口类型 {@value PopWindow#POP_WINDOW_CODE}
	 *            {@value PopWindow#POP_WINDOW_SHARE}
	 * @param layoutId
	 *            窗口中的布局
	 * @param parent
	 *            显示窗口事件的View
	 * @param onPopListener
	 *            {@link OnPopListener}
	 */
	public void createPopWindow(Context context, String popWindowCategory,
			int layoutId, View parent, OnPopListener onPopListener) {
		createPopWindow(context, popWindowCategory, layoutId, parent, 0,
				onPopListener);
	}

	/**
	 * 显示窗口
	 * 
	 * @param popWindowCategory
	 */
	public void showPopWindow(String popWindowCategory) {
		if (popWindowCategory.equals(POP_WINDOW_CODE)) {
			if (!this.isShowing()) {
				this.showAsDropDown(mParent, 0, 0);
			} else {
				this.dismiss();
			}
		} else if (popWindowCategory.equals(POP_WINDOW_SHARE) ||
				popWindowCategory.equals(POP_WINDOW_CHOSE_SEX) ||
				popWindowCategory.equals(POP_WINDOW_CHOSE_PIC)  ) {
			if (!this.isShowing()) {
				this.showAtLocation(mParent, Gravity.BOTTOM, 0, 0);
			} else {
				this.dismiss();
			}
		}else if(popWindowCategory.equals(POP_WINDOW_CHOSE_AWARDS)){
			if (!this.isShowing()) {
				this.showAtLocation(mParent, Gravity.BOTTOM, 0, 0);
			} else {
				this.dismiss();
			}
		}else if(popWindowCategory.equals(POP_WINDOW_CHOSE_DOWNLOAD_AREA)){
			if (!this.isShowing()) {
				this.showAtLocation(mParent, Gravity.CENTER, 0, 0);
			} else {
				this.dismiss();
			}
		}else if(popWindowCategory.equals(POP_WINDOW_CHOSE_START_APP)){
			if (!this.isShowing()) {
				this.showAtLocation(mParent, Gravity.CENTER, 0, 0);
			} else {
				this.dismiss();
			}
		}

	}

	/**
	 * 窗口布局中的点击事件监听
	 * 
	 * @author Jesse
	 */
	public interface OnPopListener {
		/**
		 * 初始化监听
		 * 
		 * @param parent
		 *            显示窗口事件的View
		 * @param container
		 *            窗口中的布局
		 */
		public void init(PopWindow pop,View parent, View container);
	}
}
