package com.efun.platform.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.efun.core.tools.EfunScreenUtil;

/**
 * 类似 {@link PagerTabStrip} 控件
 * @author itxuxxey
 * 
 */
public class PagerTab extends LinearLayout {
	/**
	 * 布局容器
	 */
	private LinearLayout mContainer;
	/**
	 * Item 底部 线条
	 */
	private ImageView mLine;
	/**
	 * 标题控件数组
	 */
	private TextView[] mTitles;
	/**
	 * 线宽
	 */
	private int lineWidth = 0;
	/**
	 * 当前指向的下标
	 */
	private int currIndex = 0;
	/**
	 * Tab Id
	 */
	private int tabId;
	/**
	 * Tab获取焦点时字体颜色
	 */
	private int focusTabColor;
	// private int offset;
	// private int bmW;
	/**
	 * {@link LayoutParams}
	 */
	private LayoutParams params;
	
	public PagerTab(Context context) {
		super(context);
		init();
	}

	public PagerTab(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		setBackgroundColor(Color.WHITE);
		params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT, 1.0f);
		setOrientation(LinearLayout.VERTICAL);
		mContainer = new LinearLayout(getContext());
		mContainer.setOrientation(LinearLayout.HORIZONTAL);
		addView(mContainer, params);
		
	}

	/**
	 * 设置标题
	 * @param resId
	 */
	public void setTitles(int resId) {
		String[] res = getResources().getStringArray(resId);
		setTitles(res);
	}
	/**
	 * 设置Title
	 * @param titles
	 */
	public void setTitles(String[] titles) {
		if (mTitles == null) {
			mTitles = new TextView[titles.length];
			params = new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT, 1.0f);
			TextView itemText = null;
			for (int i = 0; i < titles.length; i++) {
				itemText = createTextView();
				mTitles[i] = itemText;
				mContainer.addView(itemText, params);
			}
		} else if (mTitles.length != titles.length) {
			throw new IllegalArgumentException("PagerTitleStrip 标题数目越界");
		}
		for (int i = 0; i < mTitles.length; i++) {
			mTitles[i].setText(titles[i]);
		}
	}
	/**
	 * 创建一个Tab 格式的TextView
	 * @return
	 */
	private TextView createTextView() {
		return (TextView) LayoutInflater.from(getContext()).inflate(tabId, null);
	}

	/**
	 * 设置底部线条
	 * 
	 * @param resId
	 */
	public void setLine(int resId) {
		lineWidth = EfunScreenUtil.getInStance((Activity) getContext()).getPxWidth() / mTitles.length;
		if(mLine==null){
			mLine = new ImageView(getContext());
			mLine.setScaleType(ScaleType.MATRIX);
			LayoutParams params = new LayoutParams(lineWidth, LayoutParams.WRAP_CONTENT);
			addView(mLine, params);
		}
		mLine.setBackgroundResource(resId);
	}

	/**
	 * 设置Tab 布局
	 * 
	 * @param resId
	 */
	public void setTab(int resId) {
		tabId = resId;
	}

	/**
	 * 绑定PagerAdapter
	 * 
	 * @param vPager
	 */
	public void setPagerAdapter(final ViewPager vPager) {
		vPager.setOnPageChangeListener(new MyOnPageChangeListener());
		for (int i = 0; i < mTitles.length; i++) {
			final int index = i;
			mTitles[i].setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (vPager != null) {
						vPager.setCurrentItem(index);
					}
				}
			});
		}
	}

	/**
	 * 设置选中时的字体颜色
	 * 
	 * @param color
	 */
	public void setTabSelectedColor(int color) {
		focusTabColor = color;
	}

	/**
	 * 设置默认选项
	 * 
	 * @param current
	 */
	public void setSelectedItem(ViewPager vPager, int current) {
		if (focusTabColor != 0) {
			mTitles[currIndex].setTextColor(getResources().getColor(
					focusTabColor));
		}
		vPager.setCurrentItem(current);
	}
	/**
	 * 活动监听 ；将Tab颜色和Line与{@link OnPageChangeListener} 绑定一起
	 * @author itxuxxey
	 *
	 */
	private class MyOnPageChangeListener implements OnPageChangeListener {
		// int lineWidth = offset * 2 + bmW;
		public void onPageScrollStateChanged(int arg0) {
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		public void onPageSelected(int position) {
			Animation animation = new TranslateAnimation(lineWidth * currIndex,
					lineWidth * position, 0, 0);
			mTitles[currIndex].setTextColor(Color.BLACK);
			currIndex = position;
			if (focusTabColor != 0) {
				mTitles[currIndex].setTextColor(getResources().getColor(
						focusTabColor));
			}
			animation.setFillAfter(true);
			animation.setDuration(300);
			mLine.startAnimation(animation);
		}

	}
}
