package com.efun.platform.module.summary.view;

import java.util.HashMap;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.efun.platform.AndroidScape.E_id;
import com.efun.platform.module.summary.bean.SummaryItemBean;
import com.efun.platform.module.summary.utils.SummaryUtil;
import com.efun.platform.utils.TimeFormatUtil;
import com.efun.platform.widget.BevelTextView;
import com.efun.platform.widget.ListContainer;

/**
 * 資訊列表
 * @author Jesse
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

	public SummaryContainer(Context context) {
		super(context);
	}

	public SummaryContainer(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void saveItemViews(View itemView, HashMap<String, View> itemMap) {
		itemMap.put(KEY_Category, itemView.findViewById(E_id.item_category));
		itemMap.put(KEY_Title, itemView.findViewById(E_id.item_title));
		itemMap.put(KEY_CreateDate, itemView.findViewById(E_id.item_time));
	}

	@Override
	public void setValuesInItem(HashMap<String, View> itemMap,int position,Object data) {
		SummaryItemBean bean =  (SummaryItemBean) data;
//		BevelTextView mBevelTextView = (BevelTextView) itemMap.get(KEY_Category);
		TextView mBevelTextView = (TextView) itemMap.get(KEY_Category);
		TextView mTitle = (TextView) itemMap.get(KEY_Title);
		TextView mCreateDate = (TextView) itemMap.get(KEY_CreateDate);
//		mBevelTextView.setBevel(2);
		SummaryUtil.displaySummaryHeader(mBevelTextView, bean.getType());
		mTitle.setText(bean.getTitle());
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
