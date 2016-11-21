package com.efun.platform.widget;

import java.util.ArrayList;
import java.util.HashMap;

import com.efun.platform.module.base.impl.OnEfunItemClickListener;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

/**
 * 列表
 * @author itxuxxey
 *
 */
public abstract class ListContainer extends LinearLayout {
	/**
	 * 代表插入头部
	 */
	public final static int HEADER_VIEW_FLAG = -1;
	/**
	 *  代表插入尾部
	 */
	public final static int FOOT_VIEW_FLAG = -2;
	/**
	 * 容器
	 */
	private int itemLayout;
	/**
	 *保存ItemView的Array
	 */
	private ArrayList<HashMap<String, View>> itemArray;
	/**
	 * 布局加载工具
	 */
	private LayoutInflater mLayoutInflater;
	
	/**
	 * 点击事件监听 {@link OnEfunItemClickListener}
	 */
	private OnEfunItemClickListener mOnEfunItemClickListener;
	
	public boolean screenOrientation;
	
	public boolean isVideo;
	/**
	 * 设置点击事件
	 * @param onEfunItemClickListener
	 */
	public void setOnEfunItemClickListener(
			OnEfunItemClickListener onEfunItemClickListener) {
		this.mOnEfunItemClickListener = onEfunItemClickListener;
	}
	
	public ListContainer(Context context) {
		super(context);
		init();
	}

	public ListContainer(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	/**
	 * 初始化对象
	 */
	private void init() {
		mLayoutInflater = LayoutInflater.from(getContext());
		itemArray = new ArrayList<HashMap<String, View>>();
	}

	/**
	 * 设置点布局
	 * @param layoutId
	 */
	public void setItemLayout(int layoutId) {
		this.itemLayout = layoutId;
	}
	/**
	 * 创建Item View
	 * @return
	 */
	private View createItemView() {
		View view = null;
		if(itemLayout==0){
			view = createItemViewIfNoRes();
		}else{
			view = mLayoutInflater.inflate(itemLayout, this, false);
		}
		return view;
	}
	/**
	 * 创建子控件
	 * @param count
	 */
	private void createChildrenView(int count) {
		removeAllViews();
		itemArray.clear();
		HashMap<String, View> itemMap = null;
		View itemView = null;
		for (int i = 0; i < count; i++) {
			final int index = i;
			itemView = createItemView();
			itemView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(mOnEfunItemClickListener!=null){
						mOnEfunItemClickListener.onItemClick(index);
					}
				}
			});
			decorateItemView(itemView,index);
			addView(itemView);
			itemMap = new HashMap<String, View>();
			saveItemViews(itemView,itemMap);
			itemArray.add(itemMap);
		}
	}
	/**
	 * 修饰Item View
	 * @param itemView
	 * @param position
	 */
	public abstract void decorateItemView(View itemView,int position);
	/**
	 * 保存Item View 的每个控件
	 * @param itemMap
	 */
	public abstract void saveItemViews(View itemView,HashMap<String, View> itemMap);
	/**
	 * 设置Item View 的每个控件的内容值
	 * @param itemMap
	 */
	public abstract void setValuesInItem(HashMap<String, View> itemMap,int index,Object object);

	/**
	 * 创建布局
	 * @return
	 */
	public abstract View createItemViewIfNoRes();
	/**
	 * 设置内容值
	 */
	private void setValues(ArrayList data) {
		HashMap<String, View> itemMap = null;
		for (int i = 0; i < itemArray.size(); i++) {
			itemMap = itemArray.get(i);
			setValuesInItem(itemMap,i,data.get(i));
		}
	}
	/**
	 * 加载数据
	 * @param count
	 */
	public void loadedData(ArrayList<? extends Object> data) {
		createChildrenView(data.size());
		setValues(data);
	}
	public void loadedData(String[] data){
		ArrayList<String> list = new ArrayList<String>(data.length);
		for (int i = 0; i < data.length; i++) {
			list.add(data[i]);
		}
		loadedData(list);
	}
	public void setScreenOrientation(boolean orientation) {
		this.screenOrientation = orientation;
	}
	
	public void setVideoStatue(boolean isVideo){
		this.isVideo = isVideo;
	}
	/**
	 * 插入动作
	 * @param flag 动作：插入头部；插入尾部
	 */
	public void addHeaderOrFooter(int flag){
		if(flag!=HEADER_VIEW_FLAG && flag!=FOOT_VIEW_FLAG){
			throw new IllegalArgumentException("flag 不是 HEADER_VIEW_FLAG 或者 FOOT_VIEW_FLAG");
		}
		View itemView = createItemView();
		addAction(flag, itemView);
	}
	/**
	 * 插入动作
	 * @param flag 动作：插入头部；插入尾部
	 * @param itemView
	 */
	public void addHeaderOrFooter(int flag,View itemView){
		if(flag!=HEADER_VIEW_FLAG && flag!=FOOT_VIEW_FLAG){
			throw new IllegalArgumentException("flag 不是 HEADER_VIEW_FLAG 或者 FOOT_VIEW_FLAG");
		}
		if(itemView==null){
			throw new NullPointerException("flag 不是 HEADER_VIEW_FLAG 或者 FOOT_VIEW_FLAG");
		}
		addAction(flag, itemView);
	}
	/**
	 * 插入
	 * @param flag 动作：插入头部；插入尾部
	 * @param itemView
	 */
	private void addAction(int flag,View itemView){
		decorateItemView(itemView,flag);
		if(flag==HEADER_VIEW_FLAG){
			addView(itemView,0);
		}else{
			addView(itemView,getChildCount()+1);
		}
	}
	
}