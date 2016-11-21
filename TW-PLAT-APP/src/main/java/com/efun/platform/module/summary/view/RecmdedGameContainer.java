package com.efun.platform.module.summary.view;

import java.util.ArrayList;

import com.efun.platform.AndroidScape.E_dimens;
import com.efun.platform.AndroidScape.E_id;
import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.image.ImageManager;
import com.efun.platform.module.base.impl.OnEfunItemClickListener;
import com.efun.platform.module.summary.bean.EventGameBean;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 推荐游戏
 * 
 * @author Jesse
 * 
 */
public class RecmdedGameContainer extends LinearLayout {
	/**
	 * Item 布局
	 */
	private int itemLayout;
	/**
	 * Item 个数
	 */
	private final int CHILD_COUNT = 3;
	/**
	 * {@link LayoutInflater}
	 */
	private LayoutInflater mLayoutInflater;

	public RecmdedGameContainer(Context context) {
		super(context);
	}

	public RecmdedGameContainer(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	private View[] views ;
	/**
	 * 创建界面视图
	 */
	public void onInvalidate() {
		mLayoutInflater = LayoutInflater.from(getContext());
		if (itemLayout == 0) {
			throw new IllegalArgumentException("itemLayout not find");
		}
		views = new View[CHILD_COUNT];
		for (int i = 0; i < CHILD_COUNT; i++) {
			final int mIndex = i;
			views[i] = getItemView();
			views[i].setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (mOnEfunItemClickListener != null) {
						mOnEfunItemClickListener.onItemClick(mIndex);
					}
				}
			});
		}
		LayoutParams params = new LayoutParams(0,LayoutParams.WRAP_CONTENT,1.0f);
		params.gravity = Gravity.CENTER_HORIZONTAL;
		addView(views[0], params);

		params = new LayoutParams(0,LayoutParams.WRAP_CONTENT,1.0f);
		params.gravity = Gravity.CENTER_HORIZONTAL;
//		params.addRule(RelativeLayout.CENTER_HORIZONTAL);
		addView(views[1], params);

		params = new LayoutParams(0,LayoutParams.WRAP_CONTENT,1.0f);
		params.gravity = Gravity.CENTER_HORIZONTAL;
//		params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		addView(views[2], params);
		super.invalidate();
	}

	/**
	 * 设置子布局
	 * 
	 * @return
	 */
	private View getItemView() {
		View view = mLayoutInflater.inflate(itemLayout, this, false);
		return view;
	}

	/**
	 * 设置数据
	 * 
	 * @param view
	 * @param bean
	 */
	public void setValues(ArrayList<EventGameBean> array) {
		for (int i = 0; i < array.size(); i++) {
			EventGameBean bean = array.get(i);
			ImageView iconView = (ImageView) views[i].findViewById(E_id.item_icon);
			TextView titleView = (TextView) views[i].findViewById(E_id.item_title);
			TextView contentView = (TextView) views[i].findViewById(E_id.item_content);
			
			ImageManager.displayImage(bean.getSmallpic(), iconView, ImageManager.IMAGE_ROUND, E_dimens.e_corners_radius_132);
			titleView.setText(bean.getGameName());
			if(bean.getActGameCode().equals("1")||bean.getActGameCode().equals("3")){
				contentView.setText(E_string.efun_pd_download_for_point);
				contentView.setVisibility(View.VISIBLE);
//				contentView.setVisibility(View.GONE);
			}else{
				contentView.setVisibility(View.GONE);
			}
		}
		
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
	 * 点击事件监听
	 */
	private OnEfunItemClickListener mOnEfunItemClickListener;
	/**
	 * 设置点击事件
	 * @param onEfunItemClickListener
	 */
	public void setOnEfunItemClickListener(
			OnEfunItemClickListener onEfunItemClickListener) {
		this.mOnEfunItemClickListener = onEfunItemClickListener;
	}
}
