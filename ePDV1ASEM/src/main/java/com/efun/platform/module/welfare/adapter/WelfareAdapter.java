package com.efun.platform.module.welfare.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.efun.platform.AndroidScape.E_id;
import com.efun.platform.AndroidScape.E_layout;
import com.efun.platform.image.ImageManager;
import com.efun.platform.module.welfare.bean.WelfareItemBean;
/**
 * 福利列表适配器
 * @author harvery
 *
 */
public class WelfareAdapter extends BaseAdapter {
	private LayoutInflater mLayoutInflater;
	private ArrayList<WelfareItemBean> mArray;
	public WelfareAdapter(Context context) {
		this.mLayoutInflater = LayoutInflater.from(context);
		this.mArray = new ArrayList<WelfareItemBean>();
	}
	/**
	 * 翻页
	 * @param array
	 */
	public void append(ArrayList<WelfareItemBean> array){
		mArray.addAll(array);
		notifyDataSetChanged();
	}
	/**
	 * 刷新
	 * @param array
	 */
	public void refresh(ArrayList<WelfareItemBean> array){
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mLayoutInflater.inflate(E_layout.efun_pd_welfare_item_jx, null);
			holder.mIcon = (ImageView) convertView.findViewById(E_id.welfare_game_icon);
			holder.mDoingBtn = (ImageView) convertView.findViewById(E_id.item_icon2);
			holder.mTitle = (TextView)convertView.findViewById(E_id.welfare_game_title);
			holder.mGameContent = (TextView)convertView.findViewById(E_id.item_content);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		ImageManager.displayImage(mArray.get(position).getIcon(), holder.mIcon,ImageManager.IMAGE_SQUARE);
		holder.mTitle.setText(mArray.get(position).getTitle());
		holder.mGameContent.setText(mArray.get(position).getContent());
		return convertView;
	}

	public static class ViewHolder {
		public TextView mTitle,mGameContent;
		public ImageView mIcon,mDoingBtn;
	}

}
