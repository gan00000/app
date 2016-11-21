package com.efun.platform.module.summary.view;

import java.util.HashMap;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.efun.core.tools.EfunResourceUtil;
import com.efun.platform.AndroidScape.E_id;
import com.efun.platform.image.ImageManager;
import com.efun.platform.image.core.DisplayImageOptions;
import com.efun.platform.module.summary.bean.SummaryItemBean;
import com.efun.platform.module.summary.utils.SummaryUtil;
import com.efun.platform.utils.TimeFormatUtil;
import com.efun.platform.widget.BevelTextView;
import com.efun.platform.widget.ListContainer;

/**
 * 資訊列表
 * @author itxuxxey
 * 
 */
public class SummaryContainer extends ListContainer {
	/**
	 * KEY 存放 {@link BevelTextView}斜角控件
	 */
	private final String KEY_Category = "KEY_Category";
	/**
	 * KEY 存放 标题控件
	 */
	private final String KEY_Title = "KEY_Title";
	/**
	 * KEY 存放 事件控件
	 */
	private final String KEY_CreateDate = "KEY_CreateDate";
	private final String KEY_HotTYpe = "KEY_HotTYpe";
	private final String KEY_Content = "KEY_Content";
	private final String KEY_Icon = "KEY_Icon";
	private DisplayImageOptions options;

	public SummaryContainer(Context context) {
		super(context);
		options = new DisplayImageOptions.Builder()
        .showImageForEmptyUri(EfunResourceUtil.findDrawableIdByName(context, "efun_pd_default_square_icon"))
        .showImageOnFail(EfunResourceUtil.findDrawableIdByName(context, "efun_pd_default_square_icon"))
        .showImageOnLoading(EfunResourceUtil.findDrawableIdByName(context, "efun_pd_default_square_icon"))
        .cacheInMemory(true)
        .cacheOnDisk(true).build();
	}

	public SummaryContainer(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void saveItemViews(View itemView, HashMap<String, View> itemMap) {
		itemMap.put(KEY_Category, itemView.findViewById(E_id.item_category));
		itemMap.put(KEY_Title, itemView.findViewById(E_id.item_title));
		itemMap.put(KEY_CreateDate, itemView.findViewById(E_id.item_time));
		itemMap.put(KEY_HotTYpe, itemView.findViewById(E_id.item_hot_type));
		itemMap.put(KEY_Content, itemView.findViewById(E_id.item_content));
		itemMap.put(KEY_Icon, itemView.findViewById(E_id.item_icon));
	}

	@Override
	public void setValuesInItem(HashMap<String, View> itemMap,int position,Object data) {
		SummaryItemBean bean =  (SummaryItemBean) data;
//		BevelTextView mBevelTextView = (BevelTextView) itemMap.get(KEY_Category);
		TextView mBevelTextView = (TextView) itemMap.get(KEY_Category);
		TextView mTitle = (TextView) itemMap.get(KEY_Title);
		TextView mCreateDate = (TextView) itemMap.get(KEY_CreateDate);
		TextView mHotType = (TextView) itemMap.get(KEY_HotTYpe);
		TextView mContent = (TextView) itemMap.get(KEY_Content);
		ImageView mIcon = (ImageView) itemMap.get(KEY_Icon);
//		mBevelTextView.setBevel(2);
		SummaryUtil.displaySummaryHeader(mBevelTextView, bean.getType());
		if(bean.getHot() != 0){
			mHotType.setVisibility(View.VISIBLE);
			mHotType.setText(bean.getHotTag());
		}else{
			mHotType.setVisibility(View.GONE);
		}
		mContent.setText(bean.getSubTitle());
		mTitle.setText(bean.getTitle());
		ImageManager.displayImage(bean.getSmallpic(), mIcon, options);
		mCreateDate.setText(TimeFormatUtil.LongFormatDate4(bean.getCrtime()));
	}

	@Override
	public void decorateItemView(View itemView, int position) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public View createItemViewIfNoRes() {
		// TODO Auto-generated method stub
		return null;
	}

}
