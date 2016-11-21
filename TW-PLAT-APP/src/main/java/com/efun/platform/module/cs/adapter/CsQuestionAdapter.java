package com.efun.platform.module.cs.adapter;

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
import com.efun.platform.module.cs.bean.CsQuestionItemBean;
import com.efun.platform.widget.ArrowView;
/**
 * 常见问题列表Adapter
 * @author Jesse
 *
 */
public class CsQuestionAdapter extends BaseAdapter {
	private LayoutInflater mLayoutInflater;
	private ArrayList<CsQuestionItemBean> mArray;
	public CsQuestionAdapter(Context context) {
		this.mLayoutInflater = LayoutInflater.from(context);
		this.mArray = new ArrayList<CsQuestionItemBean>();
	}

	/**
	 * 翻页
	 * @param array
	 */
	public void append(ArrayList<CsQuestionItemBean> array){
		mArray.addAll(array);
		notifyDataSetChanged();
	}
	/**
	 * 刷新
	 * @param array
	 */
	public void refresh(ArrayList<CsQuestionItemBean> array){
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
		if(convertView==null){
			holder = new ViewHolder();
			convertView = mLayoutInflater.inflate(E_layout.efun_pd_cs_question_list_item, null);
			holder.mContent = (TextView) convertView.findViewById(E_id.item_content);
			holder.mTime = (TextView) convertView.findViewById(E_id.item_time);
			holder.mLine1 = convertView.findViewById(E_id.item_line_1);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		CsQuestionItemBean bean = mArray.get(position);
		holder.mContent.setText(bean.getQuestionsTitle());
//		holder.mTime.setText(bean.getCreateTime());
		return convertView;
	}

	public static class ViewHolder {
		public TextView mTitle, mContent, mCategory, mText, mTime;
		public ImageView mIcon;
		public ArrowView mArrow;
		public View mLine1;
	}
}