package com.efun.platform.module.summary.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.efun.platform.AndroidScape.E_id;
import com.efun.platform.AndroidScape.E_layout;
import com.efun.platform.module.summary.bean.SummaryItemBean;
import com.efun.platform.module.summary.utils.SummaryUtil;
import com.efun.platform.utils.TimeFormatUtil;

/**
 * 资讯列表适配器
 * @author Jesse
 * 
 */
public class SummaryAdapter extends BaseAdapter {
	/**
	 * {@link LayoutInflater}
	 */
	private LayoutInflater mLayoutInflater;
	private ArrayList<SummaryItemBean> mArray;
	public SummaryAdapter(Context context) {
		this.mLayoutInflater = LayoutInflater.from(context);
		this.mArray = new ArrayList<SummaryItemBean>();
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
			convertView = mLayoutInflater.inflate(E_layout.efun_pd_summary_list_item_2, null);
			holder.mCategory = (TextView) convertView.findViewById(E_id.item_category);
			holder.mTitle = (TextView) convertView.findViewById(E_id.item_title);
			holder.mTime = (TextView) convertView.findViewById(E_id.item_time);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		SummaryItemBean bean = mArray.get(position);
		SummaryUtil.displaySummaryHeader(holder.mCategory, bean.getType());
		holder.mTitle.setText(bean.getTitle());
		holder.mTime.setText(TimeFormatUtil.LongFormatDate4(bean.getCrtime()));
		return convertView;
	}
	public static class ViewHolder {
		public TextView mCategory;
		public TextView mTitle;
		public TextView mTime;
	}
}
