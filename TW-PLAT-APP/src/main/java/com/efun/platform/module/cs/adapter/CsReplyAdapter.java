package com.efun.platform.module.cs.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.efun.platform.AndroidScape.E_color;
import com.efun.platform.AndroidScape.E_drawable;
import com.efun.platform.AndroidScape.E_id;
import com.efun.platform.AndroidScape.E_layout;
import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.module.cs.bean.CsReplyQuestionBean;

/**
 * 客服，玩家回复适配器
 * 
 * @author Jesse
 * 
 */
public class CsReplyAdapter extends BaseAdapter {
	/**
	 * {@link LayoutInflater}
	 */
	private Context mContext;
	private LayoutInflater mLayoutInflater;
	private ArrayList<CsReplyQuestionBean> mArray;
	private final String FINISH = "9";
	private int selectionPostion = 0;

	private int[] dots = { E_drawable.efun_pd_icon_dot_0,
			E_drawable.efun_pd_icon_dot_1, E_drawable.efun_pd_icon_dot_2,
			E_drawable.efun_pd_icon_dot_3, E_drawable.efun_pd_icon_dot_4 };

	public CsReplyAdapter(Context context) {
		this.mLayoutInflater = LayoutInflater.from(context);
		this.mArray = new ArrayList<CsReplyQuestionBean>();
		this.mContext = context;
	}

	/**
	 * 翻页
	 * 
	 * @param array
	 */
	public void append(ArrayList<CsReplyQuestionBean> array) {
		mArray.addAll(array);
		notifyDataSetChanged();
	}

	/**
	 * 刷新
	 * 
	 * @param array
	 */
	public void refresh(ArrayList<CsReplyQuestionBean> array) {
		mArray.clear();
		append(array);
	}

	@Override
	public int getCount() {
		return mArray.size();
	}

	public ArrayList<CsReplyQuestionBean> getmArray() {
		return mArray;
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
		GroupHolder mGroupHolder = null;
		if (convertView == null) {
			mGroupHolder = new GroupHolder();
			convertView = mLayoutInflater.inflate(
					E_layout.efun_pd_cs_reply_list_item_header, null);
			mGroupHolder.mTitle = (TextView) convertView
					.findViewById(E_id.item_title);
			mGroupHolder.mCategory = (TextView) convertView
					.findViewById(E_id.item_category);
			mGroupHolder.mButton = (ImageView) convertView
					.findViewById(E_id.item_button);
			mGroupHolder.mText = (TextView) convertView
					.findViewById(E_id.item_text);
			mGroupHolder.mContent = (TextView) convertView
					.findViewById(E_id.item_content);
			mGroupHolder.mTime = (TextView) convertView
					.findViewById(E_id.item_time);
			mGroupHolder.mContainer = (LinearLayout) convertView
					.findViewById(E_id.contaier_linear_1);
			mGroupHolder.mChildren = (LinearLayout) convertView
					.findViewById(E_id.contaier_linear_2);
			mGroupHolder.mLine = convertView.findViewById(E_id.item_line_1);
			mGroupHolder.head = convertView.findViewById(E_id.head);
			mGroupHolder.mDot = (ImageView) convertView
					.findViewById(E_id.item_dot);
			convertView.setTag(mGroupHolder);
		} else {
			mGroupHolder = (GroupHolder) convertView.getTag();
		}
		mGroupHolder.mTitle.setVisibility(View.VISIBLE);
		mGroupHolder.mText.setVisibility(View.GONE);
		mGroupHolder.mContent.setVisibility(View.GONE);
		mGroupHolder.mTime.setVisibility(View.VISIBLE);
		mGroupHolder.mChildren.setBackgroundColor(Color.WHITE);
		mGroupHolder.mTitle.setText(mArray.get(position).getQuestionTitle());
		mGroupHolder.mContent.setText(mArray.get(position).getQuestionTitle());
		mGroupHolder.mTime.setText(mArray.get(position).getCreateTime());

		String replyStatus = mArray.get(position).getReplyStatus();
		String hasNew = mArray.get(position).getHasNew();
		if (!TextUtils.isEmpty(replyStatus)) {
			if (replyStatus.equals(FINISH)) {
				mGroupHolder.mCategory.setText(E_string.efun_pd_reply_grade);
				mGroupHolder.mCategory.setTextColor(mContext.getResources().getColor(E_color.e_8e8e8e));
				mGroupHolder.mButton.setBackgroundResource(E_drawable.efun_pd_right);
			} else {
				mGroupHolder.mCategory.setText(E_string.efun_pd_reply_ungrade);
				mGroupHolder.mCategory.setTextColor(Color.BLACK);
				mGroupHolder.mButton.setBackgroundResource(E_drawable.efun_pd_doubt);
			}
		}
		if (hasNew.equals("1")) {
			mGroupHolder.mButton.setBackgroundResource(E_drawable.efun_pd_hasnew);
			mGroupHolder.mCategory.setText(E_string.efun_pd_reply_new);
		}
		mGroupHolder.head.setBackgroundColor(setHeaderColor(position));
		if (position == mArray.size() - 1) {
			mGroupHolder.mLine.setVisibility(View.VISIBLE);
		}

		int flag = position % 5;
		mGroupHolder.mDot.setImageResource(dots[flag]);

		if (selectionPostion == position) {
			mGroupHolder.mDot.setSelected(true);
		} else {
			mGroupHolder.mDot.setSelected(false);
		}

		return convertView;
	}

	/**
	 * 头部
	 */
	private static class GroupHolder {
		public TextView mTitle, mCategory;// 默认状态
		public ImageView mButton, mDot;
		public TextView mContent, mTime;
		public TextView mText;
		public LinearLayout mContainer, mChildren;
		public View mLine, head;
	}

	private int setHeaderColor(int position) {
		int flag = 0;
		if (mArray.size() <= 5) {
			flag = position;
		} else {
			flag = position % 5;
		}
		int color = 0;
		switch (flag) {
		case 0:
			color = mContext.getResources().getColor(E_color.e_FEF881);
			break;
		case 1:
			color = mContext.getResources().getColor(E_color.e_FFCB5B);
			break;
		case 2:
			color = mContext.getResources().getColor(E_color.e_88E2AF);
			break;
		case 3:
			color = mContext.getResources().getColor(E_color.e_43B3FE);
			break;
		case 4:
			color = mContext.getResources().getColor(E_color.e_93EBFE);
			break;
		}
		return color;
	}

	public void setSelectionPostion(int selectionPostion) {
		this.selectionPostion = selectionPostion;
	}

}
