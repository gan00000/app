package com.efun.platform.widget;

import com.efun.platform.AndroidScape.E_dimens;
import com.efun.platform.AndroidScape.E_drawable;
import com.efun.platform.module.base.impl.OnEfunItemClickListener;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class RatingBarView extends LinearLayout {
	private ImageView[] starIVs;
	private int selectImagRes = -1;
	private int unSelectImagRes = -1;
	private int widthDimensRes = -1;
	private int marginDimensRes = -1;

	public RatingBarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public RatingBarView(Context context) {
		super(context);
		init();
	}

	private void init() {
		setOrientation(LinearLayout.HORIZONTAL);
		starIVs = new ImageView[5];
	}

	// 創建星星評分條
	private LayoutParams params = null;

	/**
	 * 創建可點擊，可選擇星級條
	 * 
	 * @param mOnEfunItemClickListener
	 */
	public void createdStarBar(
			final OnEfunItemClickListener mOnEfunItemClickListener) {
		removeAllViews();
		int margin, width;
		if (marginDimensRes != -1) {
			margin = getResources().getDimensionPixelSize(marginDimensRes);
		} else {
			margin = getResources().getDimensionPixelSize(E_dimens.e_size_5);
		}
		if (widthDimensRes != -1) {
			width = getResources().getDimensionPixelSize(widthDimensRes);
		} else {
			width = getResources().getDimensionPixelSize(E_dimens.e_size_80);
		}
		for (int i = 0; i < 5; i++) {
			final int position = i;
			ImageView starIV = new ImageView(getContext());
			params = new LayoutParams(width, width);
			params.setMargins(margin, 0, margin, 0);
			if (selectImagRes != -1) {
				starIV.setBackgroundResource(selectImagRes);
			} else {
				starIV.setBackgroundResource(E_drawable.efun_pd_ratingbar_select);
			}
			this.addView(starIV, params);
			starIV.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					if (mOnEfunItemClickListener != null) {
						mOnEfunItemClickListener.onItemClick(position);
						showStar(position);
					}
				}
			});
			starIVs[i] = starIV;
		}
	}

	/**
	 * 創建固定值的星級條
	 * 
	 * @param startCounts
	 */
	public void createdStarBarWidthGrey(int startCounts) {
		removeAllViews();
		int margin, width;
		if (marginDimensRes != -1) {
			margin = getResources().getDimensionPixelSize(marginDimensRes);
		} else {
			margin = getResources().getDimensionPixelSize(E_dimens.e_size_5);
		}
		if (widthDimensRes != -1) {
			width = getResources().getDimensionPixelSize(widthDimensRes);
		} else {
			width = getResources().getDimensionPixelSize(E_dimens.e_size_80);
		}
		for (int i = 0; i < 5; i++) {
			ImageView starIV = new ImageView(getContext());
			params = new LayoutParams(width, width);
			params.setMargins(margin, 0, margin, 0);
			if (i < startCounts) {
				if (selectImagRes != -1) {
					starIV.setBackgroundResource(selectImagRes);
				} else {
					starIV.setBackgroundResource(E_drawable.efun_pd_ratingbar_select);
				}
			} else {
				if (unSelectImagRes != -1) {
					starIV.setBackgroundResource(unSelectImagRes);
				} else {
					starIV.setBackgroundResource(E_drawable.efun_pd_ratingbar_unselect);
				}
			}
			this.addView(starIV, params);
			starIVs[i] = starIV;
		}
	}

	private void showStar(int position) {
		for (int i = 0; i < starIVs.length; i++) {
			if (unSelectImagRes != -1) {
				starIVs[i].setBackgroundResource(unSelectImagRes);
			} else {
				starIVs[i]
						.setBackgroundResource(E_drawable.efun_pd_ratingbar_unselect);
			}
		}
		for (int i = 0; i < position + 1; i++) {
			if (selectImagRes != -1) {
				starIVs[i].setBackgroundResource(selectImagRes);
			} else {
				starIVs[i]
						.setBackgroundResource(E_drawable.efun_pd_ratingbar_select);
			}
			// starIVs[i].setBackgroundResource(E_drawable.efun_pd_ratingbar_select);
		}
	}

	public void setStarIVSelectImage(int selectImagRes) {
		this.selectImagRes = selectImagRes;
	}

	public void setStarIVUnSelectImage(int unSelectImagRes) {
		this.unSelectImagRes = unSelectImagRes;
	}

	public void setStarWidth(int widthDimensRes) {
		this.widthDimensRes = widthDimensRes;
	}

	public void setStartMargin(int marginDimensRes) {
		this.marginDimensRes = marginDimensRes;
	}
}
