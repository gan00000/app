package com.efun.platform.module.welfare.view;

import java.util.HashMap;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.efun.platform.AndroidScape.E_color;
import com.efun.platform.AndroidScape.E_id;
import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.module.welfare.bean.ActExtensionGiftBean;
import com.efun.platform.widget.ListContainer;
/**
 * 奖励类型
 * @author Jesse
 *
 */
public class GiftContainer extends ListContainer{
	private final String KEY_Title = "KEY_Title";

	public GiftContainer(Context context) {
		super(context);
	}

	public GiftContainer(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void saveItemViews(View itemView, HashMap<String, View> itemMap) {
		itemMap.put(KEY_Title, itemView.findViewById(E_id.item_text));
	}

	@Override
	public void setValuesInItem(HashMap<String, View> itemMap,int position,Object data) {
		TextView mTitle = (TextView) itemMap.get(KEY_Title);
		ActExtensionGiftBean bean = (ActExtensionGiftBean) data;
		if(bean!=null){
			mTitle.setText(bean.getGoodsName());
		}
	}

	@Override
	public void decorateItemView(View itemView, int position) {
		if(position==HEADER_VIEW_FLAG){
			TextView mTitle =(TextView) itemView.findViewById(E_id.item_text);
			mTitle.setText(E_string.efun_pd_awards_type_title);
			mTitle.setTextColor(Color.BLACK);
			mTitle.getPaint().setFakeBoldText(true);
			mTitle.setBackgroundColor(getContext().getResources().getColor(E_color.e_00aaeb));
			setEnabled(false);
		}
	}

	@Override
	public View createItemViewIfNoRes() {
		// TODO Auto-generated method stub
		return null;
	}

}
