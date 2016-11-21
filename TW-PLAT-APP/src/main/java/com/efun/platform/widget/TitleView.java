package com.efun.platform.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.efun.platform.AndroidScape.E_id;
import com.efun.platform.AndroidScape.E_layout;
import com.efun.platform.module.BaseDataBean;
import com.efun.platform.module.PopWindow;
import com.efun.platform.module.base.impl.OnEfunItemClickListener;
import com.efun.platform.module.base.impl.OnTitleButtonClickListener;
import com.efun.platform.module.utils.PopUtils;
import com.efun.platform.module.utils.ViewUtils;
/**
 * 标题栏
 * @author Jesse
 *
 */
public class TitleView extends LinearLayout{
	public TitleView(Context context) {
		super(context);
		init();
	}
	public TitleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	private void init(){
		View v = ViewUtils.createView(getContext(), E_layout.efun_pd_title);
		addView(v,new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		initViews(v);
		initListeners();
	}
	
	/**
	 * 左侧，中间，右侧图片按钮
	 */
	private ImageView leftBtnView, rightBtnView, centerBtnView,rightPointView;
	/**
	 * 标题栏容器
	 */
	private RelativeLayout mBackgroundLayout;
	/**
	 * 中间,右边文字按钮
	 */
	private TextView centerTextView,rightTextView;
	/**
	 * 左右按钮监听接口{@link OnTitleButtonClickListener}
	 */
	private OnTitleButtonClickListener mOnTitleButtonClickListener;
	/**
	 * 二维码扫描，分享的{@link PopWindow}
	 */
	private PopWindow mCodePopWindow, mSharePopWindow;
	/**
	 * 是否显示{@link PopWindow}
	 */
	private boolean mEnableCode, mEnableShare;
	
	private View mBottomLine;
	public void initViews(View v) {
		mBackgroundLayout = (RelativeLayout) v.findViewById(E_id.contaier_relative_1);
		leftBtnView = (ImageView) v.findViewById(E_id.left_btn);
		centerBtnView = (ImageView) v.findViewById(E_id.center_btn);
		centerTextView = (TextView) v.findViewById(E_id.center_text);
		rightBtnView = (ImageView) v.findViewById(E_id.right_btn);
		rightTextView = (TextView) v.findViewById(E_id.right_text);
		mBottomLine = v.findViewById(E_id.title_bottom_line);
		rightPointView=(ImageView) v.findViewById(E_id.icon_1);
	}


	public void initListeners() {
		leftBtnView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mOnTitleButtonClickListener != null) {
					mOnTitleButtonClickListener.onClickLeft();
				}
			}
		});
		rightBtnView.setOnClickListener(new rightListener());
		rightTextView.setOnClickListener(new rightListener());
	}
	
	private class rightListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			if (mOnTitleButtonClickListener != null) {
				mOnTitleButtonClickListener.onClickRight();
				if (mEnableCode) {
					mCodePopWindow.showPopWindow(PopWindow.POP_WINDOW_CODE);
				}
				if (mEnableShare) {
					mSharePopWindow.showPopWindow(PopWindow.POP_WINDOW_SHARE);
				}

			}
		}
	}

	/**
	 * 设置标题栏监听
	 * 
	 * @param onTitleButtonClickListener
	 *            {@link OnTitleButtonClickListener}
	 */
	public void setOnTitleButtonClickListener(
			OnTitleButtonClickListener onTitleButtonClickListener) {
		this.mOnTitleButtonClickListener = onTitleButtonClickListener;
	}

	/**
	 * 切换图片和文本显示
	 * 
	 * @param title
	 *            中间文本按钮的内容
	 */
	public void setTitleCenterText(String title) {
		centerTextView.setVisibility(View.VISIBLE);
		centerTextView.setText(title);
		centerBtnView.setVisibility(View.GONE);
	}

	/**
	 * 设置左边图片按钮的资源
	 * 
	 * @param selecter
	 */
	public void setTitleLeftRes(int selecter) {
		leftBtnView.setBackgroundResource(selecter);
	}

	/**
	 * 设置右边图片按钮的资源
	 * 
	 * @param selecter
	 */
	public void setTitleRightRes(int selecter) {
		rightBtnView.setBackgroundResource(selecter);
		rightBtnView.setVisibility(View.VISIBLE);
		rightTextView.setVisibility(View.GONE);
	}
	
	/**
	 * 设置右边文字按钮背景
	 * @param selecter
	 */
	public void setTitleRightTVRes(int selecter) {
		rightTextView.setBackgroundResource(selecter);
		rightTextView.setVisibility(View.VISIBLE);
		rightBtnView.setVisibility(View.GONE);
	}
	
	/**
	 * 设置右边文字内容
	 * 
	 * @param selecter
	 */
	public void setTitleRightTextRes(int selecter) {
		rightBtnView.setVisibility(View.GONE);
		rightTextView.setVisibility(View.VISIBLE);
		rightTextView.setText(selecter);
	}
	
	/**
	 * 设置右边文字颜色
	 * @param selecter
	 */
	public void setTitleRightTextColor(int color) {
		rightBtnView.setVisibility(View.GONE);
		rightTextView.setVisibility(View.VISIBLE);
		rightTextView.setTextColor(getResources().getColor(color));
	}
	/**
	 * 设置右边文字大小
	 * @param selecter
	 */
	public void setTitleRightTextSize(int size) {
		rightBtnView.setVisibility(View.GONE);
		rightTextView.setVisibility(View.VISIBLE);
		rightTextView.setTextSize(getResources().getDimension(size));
	}

	/**
	 * 设置左边图片按钮的状态 {@value View#GONE} {@value View#VISIBLE}
	 * {@value View#INVISIBLE}
	 * 
	 * @param selecter
	 */
	public void setTitleLeftStatus(int status) {
		leftBtnView.setVisibility(status);
	}
	
	

	/**
	 * 设置右侧图片按钮的状态 {@value View#GONE} {@value View#VISIBLE}
	 * {@value View#INVISIBLE}
	 * 
	 * @param selecter
	 */
	public void setTitleRightStatus(int status) {
		rightBtnView.setVisibility(status);
	}
	
	/**
	 * 设置中间按钮的状态 {@value View#GONE} {@value View#VISIBLE}
	 * {@value View#INVISIBLE}
	 * 
	 * @param selecter
	 */
	public void setTitleCenterStatus(int status) {
		centerTextView.setVisibility(status);
		centerBtnView.setVisibility(status);
	}

	/**
	 * 设置中间按钮的状态
	 * 
	 * @param resId
	 *            资源ID
	 * @param isPic
	 *            是否是图片资源，true为图片资源
	 */
	public void setTitleCenterRes(int resId, boolean isPic) {
		if (isPic) {
			centerBtnView.setBackgroundResource(resId);
			centerTextView.setVisibility(View.GONE);
			centerBtnView.setVisibility(View.VISIBLE);
		} else {
			centerTextView.setVisibility(View.VISIBLE);
			centerTextView.setText(resId);
			centerBtnView.setVisibility(View.GONE);
		}

	}
	

	/**
	 * 设置中间按钮的字体颜色
	 * 
	 * @param resId
	 *            资源ID
	 */
	public void setTitleCenterTextColor(int resId) {
			centerTextView.setVisibility(View.VISIBLE);
			centerTextView.setTextColor(resId);
			centerBtnView.setVisibility(View.GONE);
	}
	/**
	 * 设置标题栏背景颜色
	 * @param res
	 */
	public void setTitleBarBackgroundRes(int res){
		mBackgroundLayout.setBackgroundResource(res);
	}
	
	/**
	 * 设置标题栏背景颜色
	 * @param color
	 */
	public void setTitleBarBackgroundColor(int color){
		mBackgroundLayout.setBackgroundColor(color);
	}
	/**
	 * 设置底部線條的状态 {@value View#GONE} {@value View#VISIBLE}
	 * {@value View#INVISIBLE}
	 * 
	 * @param selecter
	 */
	public void setTitleBottomLineStatus(int status) {
		mBottomLine.setVisibility(status);
	}
	
	/**
	 * 设置红点的状态 {@value View#GONE} {@value View#VISIBLE}
	 * {@value View#INVISIBLE}
	 * 
	 * @param selecter
	 */
	public void setTitleRightPointStatus(int status) {
		rightPointView.setVisibility(status);
	}
	/**
	 * 显示悬浮按钮
	 * 
	 * @param popWindowCategory
	 *            {@link PopWindow}的类型 {@value PopWindow#POP_WINDOW_CODE	二维码和设置}
	 *            {@value PopWindow#POP_WINDOW_SHARE	分享}
	 */
	public void setPopWindowEnable(String popWindowCategory,BaseDataBean mBean) {
		if (!TextUtils.isEmpty(popWindowCategory)) {
			if (popWindowCategory.equals(PopWindow.POP_WINDOW_CODE)) {
				mEnableCode = true;
				if (mCodePopWindow == null) {//创建二维码和设置
					mCodePopWindow = PopUtils.createCodeSet(getContext(),rightBtnView);
				}
			}

			if (popWindowCategory.equals(PopWindow.POP_WINDOW_SHARE)) {
				mEnableShare = true;
				if (mSharePopWindow == null) {// 创建facebook分享，google+分享，line分享
					mSharePopWindow = PopUtils.createShare(getContext(),rightBtnView, mBean);
				}
			}
		}

	}
}
