package com.efun.platform.widget;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.efun.platform.AndroidScape.E_id;
/**
 * 排列圆点容器
 * @author itxuxxey
 *
 */
public class ZoomPointContainer extends LinearLayout {
	public static final int DELAYED_TIME = 4000;
	/**
	 * 当前下标
	 */
	private int curIndex;
	/**
	 * 圆点总数目
	 */
	private int count;
	/**
	 * 点布局
	 */
	private int itemLayout;
	/**
	 * {@link ZoomPoint} 控件集合
	 */
	private ZoomPoint[] mZoomPoint;
	/**
	 * {@link LayoutInflater}
	 */
	private LayoutInflater mLayoutInflater;

	public ZoomPointContainer(Context context) {
		super(context);
		init();
	}

	public ZoomPointContainer(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	private void init() {
		mLayoutInflater = LayoutInflater.from(getContext());
	}

	/**
	 * 设置个数
	 * 
	 * @param count
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * 设置点布局
	 * 
	 * @param layoutId
	 */
	public void setItemLayout(int layoutId) {
		this.itemLayout = layoutId;
	}
	/**
	 * 创建{@link ZoomPoint}
	 * @return
	 */
	private View getItemView() {
		View view = mLayoutInflater.inflate(itemLayout, this, false);
		return view;
	}
	/**
	 * 创建界面视图
	 * @param index
	 */
	public void onInvalidate(int index) {
		removeAllViews();
		if (this.count < 0 || this.itemLayout == 0) {
			throw new NullPointerException("count = 0 或者 itemLayout 找不到");
		}
		View itemView = null;
		mZoomPoint = new ZoomPoint[this.count];
		for (int i = 0; i < this.count; i++) {
			itemView = getItemView();
			addView(itemView);
			mZoomPoint[i] = (ZoomPoint) itemView.findViewById(E_id.zoom_point);
		}
		invalidate();
		setZoomPiontStart(index);
	}

	/**
	 * 设置初始状态
	 * 
	 * @param index
	 */
	private void setZoomPiontStart(int index) {
		for (int i = 0; i < mZoomPoint.length; i++) {
			if (i != index) {
				mZoomPoint[i].startZoomInCenter();
			}
		}
	}

	/**
	 * 切换状态
	 * 
	 * @param index
	 */
	private void changeZoomPointSelected(int index) {
		for (int i = 0; i < mZoomPoint.length; i++) {
			if (i != index) {
				if (mZoomPoint[i].getZoomNormalStatus())
					mZoomPoint[i].startZoomInCenter();
			} else {
				mZoomPoint[i].startZoomOutCenter();
			}
		}
	}

	/**
	 * {@link ZoomPoint} 控件集合
	 * 
	 * @return
	 */
	public ZoomPoint[] getZoomPoint() {
		return mZoomPoint;
	}
	
	public void setZoomPointBackgroundRes(int resid){
		for (int i = 0; i < mZoomPoint.length; i++) {
			mZoomPoint[i].setBackgroundResource(resid);
		}
	}

	/**
	 * 绑定ViewPager的滑动
	 * @param viewPager
	 */
	public void setViewPager(ViewPager viewPager,OnPageChangeListener onPageChangeListener){
		viewPager.setOnPageChangeListener(onPageChangeListener);
	}
	/**
	 * 绑定ViewPager的滑动
	 * @param viewPager
	 */
	public void setViewPager(final ViewPager viewPager) {
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			private Handler oldHandler, newHandler;
			
			private Runnable oldRunnable, newRunnable;

			@Override
			public void onPageSelected(final int index) {
				curIndex = index;
				newRunnable = new Runnable() {
					@Override
					public void run() {
						if(curIndex==mZoomPoint.length-1){
							curIndex=-1;
						}
						viewPager.setCurrentItem(++curIndex);
						oldHandler = null;
					}
				};
				if (oldHandler != null && oldRunnable != null) {
					oldHandler.removeCallbacks(oldRunnable);
				}
				newHandler = new Handler();
				newHandler.postDelayed(newRunnable, DELAYED_TIME);
				oldRunnable = newRunnable;
				oldHandler = newHandler;
				changeZoomPointSelected(curIndex% viewPager.getAdapter().getCount());
			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}
	/**
	 * 获取当前下表
	 * @return
	 */
	public int getCurIndex() {
		return curIndex;
	}
}
