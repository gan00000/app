package com.efun.platform.module.summary.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.efun.core.tools.EfunResourceUtil;
import com.efun.platform.AndroidScape.E_id;
import com.efun.platform.AndroidScape.E_layout;
import com.efun.platform.image.ImageManager;
import com.efun.platform.image.core.DisplayImageOptions;
import com.efun.platform.module.summary.bean.SummaryItemBean;
import com.efun.platform.module.summary.utils.SummaryUtil;
import com.efun.platform.utils.TimeFormatUtil;

/**
 * 资讯列表适配器
 * @author itxuxxey
 * 
 */
public class SummaryAdapter extends BaseAdapter {
	/**
	 * {@link LayoutInflater}
	 */
	private LayoutInflater mLayoutInflater;
	private ArrayList<SummaryItemBean> mArray;
	private DisplayImageOptions options;
	public SummaryAdapter(Context context) {
		this.mLayoutInflater = LayoutInflater.from(context);
		this.mArray = new ArrayList<SummaryItemBean>();
		options = new DisplayImageOptions.Builder()
        .showImageForEmptyUri(EfunResourceUtil.findDrawableIdByName(context, "efun_pd_default_square_icon"))
        .showImageOnFail(EfunResourceUtil.findDrawableIdByName(context, "efun_pd_default_square_icon"))
        .showImageOnLoading(EfunResourceUtil.findDrawableIdByName(context, "efun_pd_default_square_icon"))
        .cacheInMemory(true)
        .cacheOnDisk(true).build();
	}

	/**
	 * 翻页
	 * @param array
	 */
	public void append(ArrayList<SummaryItemBean> array){
		mArray.addAll(array);
		notifyDataSetChanged();
	}
	/**
	 * 刷新
	 * @param array
	 */
	public void refresh(ArrayList<SummaryItemBean> array){
		mArray.clear();
		append(array);
	}
	@Override
	public int getCount() {
		return mArray.size();
	}

	@Override
	public Object getItem(int position) {
		return mArray.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mLayoutInflater.inflate(E_layout.efun_pd_summary_list_item_1, null);
			holder.mCategory = (TextView) convertView.findViewById(E_id.item_category);
			holder.mTitle = (TextView) convertView.findViewById(E_id.item_title);
			holder.mTime = (TextView) convertView.findViewById(E_id.item_time);
			holder.mHotType = (TextView) convertView.findViewById(E_id.item_hot_type);
			holder.mContent = (TextView) convertView.findViewById(E_id.item_content);
			holder.mIcon = (ImageView) convertView.findViewById(E_id.item_icon);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		SummaryItemBean bean = mArray.get(position);
		SummaryUtil.displaySummaryHeader(holder.mCategory, bean.getType());
		if(bean.getHot() != 0){
			holder.mHotType.setVisibility(View.VISIBLE);
			holder.mHotType.setText(bean.getHotTag());
		}else{
			holder.mHotType.setVisibility(View.GONE);
		}
		holder.mContent.setText(bean.getSubTitle());
		holder.mTitle.setText(bean.getTitle());
		holder.mTime.setText(TimeFormatUtil.LongFormatDate4(bean.getCrtime()));
		ImageManager.displayImage(bean.getSmallpic(), holder.mIcon, options);
		return convertView;
	}
	public static class ViewHolder {
		public TextView mCategory;//类型
		public TextView mHotType;//是否是热点
		public TextView mTitle;
		public TextView mContent;//内容
		public TextView mTime;
		public ImageView mIcon;
	}
}
