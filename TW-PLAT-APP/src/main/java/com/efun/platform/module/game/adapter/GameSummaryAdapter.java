package com.efun.platform.module.game.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.efun.platform.AndroidScape.E_color;
import com.efun.platform.AndroidScape.E_id;
import com.efun.platform.AndroidScape.E_layout;
import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.module.game.bean.GameItemBean;
import com.efun.platform.module.game.bean.GameNewsBean;
import com.efun.platform.module.utils.IntentUtils;
import com.efun.platform.utils.Const.Summary;
import com.efun.platform.utils.TimeFormatUtil;

/**
 * 游戏资讯列表
 * 
 * @author Jesse
 * 
 */
public class GameSummaryAdapter extends BaseAdapter {
	private LayoutInflater mLayoutInflater;
	private Context mContext;
	private ArrayList<GameNewsBean> mArray;
	private int mNewsSize;
	private int mStrategysSize;
	private GameItemBean mGameItemBean;

	public GameSummaryAdapter(Context context) {
		this.mContext = context;
		this.mLayoutInflater = LayoutInflater.from(context);
		this.mArray = new ArrayList<GameNewsBean>();
		this.mNewsSize = 0;
		this.mStrategysSize = 0;
	}

	/**
	 * 翻页
	 * 
	 * @param array
	 */
	public void append(ArrayList<GameNewsBean> array) {
		mArray.addAll(array);
		notifyDataSetChanged();
	}

	/**
	 * 刷新
	 * 
	 * @param array
	 */
	public void refresh(ArrayList<GameNewsBean> array) {
		mArray.clear();
		append(array);
	}

	public void setNewsSize(int newSize) {
		this.mNewsSize = newSize;
	}

	public void setStrategysSize(int mStrategysSize) {
		this.mStrategysSize = mStrategysSize;
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
	
	public void setGameBean(GameItemBean mGameItemBean){
		this.mGameItemBean = mGameItemBean;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		String title = mArray.get(position).getTitle();
		String time = TimeFormatUtil.LongFormatDate4(mArray.get(position)
				.getCrtime());
		if ((position == 0)
				|| (mNewsSize <= 10 && position == mNewsSize)
				|| (mStrategysSize <= 10 && position == mNewsSize
						+ mStrategysSize)) {
			holder = new ViewHolder();
			convertView = mLayoutInflater.inflate(
					E_layout.efun_pd_game_detail_tab_summary_header, null);
			holder.mCategory = (TextView) convertView
					.findViewById(E_id.item_category);
			holder.itemLayout = (LinearLayout) convertView
					.findViewById(E_id.item_layout);
			holder.mMore = (TextView) convertView.findViewById(E_id.more_white);
			if (position == 0) {
				// holder.mIcon.setBackgroundResource(E_drawable.efun_pd_news_icon);
				holder.itemLayout.setBackgroundResource(E_color.e_4ca8ff);
				holder.mCategory.setText(E_string.efun_pd_news);
				holder.mMore.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						IntentUtils.go2SummaryList(mContext, Summary.SUMMARY_GO_NEWS, mGameItemBean);
					}
				});
			} else if (position == mNewsSize) {
				// holder.mIcon.setBackgroundResource(E_drawable.efun_pd_strategy_icon);
				holder.itemLayout.setBackgroundResource(E_color.e_ff9a4f);
				holder.mCategory.setText(E_string.efun_pd_strategy);
				holder.mMore.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						IntentUtils.go2SummaryList(mContext, Summary.SUMMARY_GO_STRATEGY, mGameItemBean);
					}
				});
			} else if (position == mNewsSize + mStrategysSize) {
				// holder.mIcon.setBackgroundResource(E_drawable.efun_pd_strategy_icon);
				holder.itemLayout.setBackgroundResource(E_color.e_ff6274);
				holder.mCategory.setText(E_string.efun_pd_game_baha);
				holder.mMore.setVisibility(View.INVISIBLE);
//				holder.mMore.setOnClickListener(new OnClickListener() {
//
//					@Override
//					public void onClick(View v) {
//						// TODO Auto-generated method stub
//
//					}
//				});
			}

			holder.mContent = (TextView) convertView
					.findViewById(E_id.item_content);
			holder.mTime = (TextView) convertView.findViewById(E_id.item_time);
			holder.mLine1 = convertView.findViewById(E_id.item_line_1);
			holder.mContent.setText(title);
			holder.mTime.setText(time);
		} else {
			holder = new ViewHolder();
			convertView = mLayoutInflater.inflate(
					E_layout.efun_pd_game_detail_tab_summary_item, null);
			holder.mContent = (TextView) convertView
					.findViewById(E_id.item_content);
			holder.mTime = (TextView) convertView.findViewById(E_id.item_time);
			holder.mLine1 = convertView.findViewById(E_id.item_line_1);
			// holder.mLine2 = convertView.findViewById(E_id.item_line_2);
			holder.mContent.setText(title);
			holder.mTime.setText(time);

			// if ((position + 1) % 10 == 0) {
			// holder.mLine1.setVisibility(View.GONE);
			// holder.mLine2.setVisibility(View.VISIBLE);
			// }
		}

		return convertView;
	}

	public static class ViewHolder {
		public TextView mTitle, mContent, mCategory, mText, mTime, mMore;
		public LinearLayout itemLayout;
		public View mLine1, mLine2;
	}
}
