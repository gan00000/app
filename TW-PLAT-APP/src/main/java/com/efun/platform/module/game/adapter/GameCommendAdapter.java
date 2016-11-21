package com.efun.platform.module.game.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.efun.platform.AndroidScape.E_dimens;
import com.efun.platform.AndroidScape.E_drawable;
import com.efun.platform.AndroidScape.E_id;
import com.efun.platform.AndroidScape.E_layout;
import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.image.ImageManager;
import com.efun.platform.module.game.bean.GameCommendItemBean;
import com.efun.platform.module.game.bean.GameVoteItemBean;
import com.efun.platform.utils.TimeFormatUtil;
import com.efun.platform.widget.RatingBarView;
/**
 * 游戏评论列表适配器
 * @author Jesse
 *
 */
public class GameCommendAdapter extends BaseAdapter {

	private LayoutInflater mLayoutInflater;
	private ArrayList<GameVoteItemBean> mArray;
//	private ArrayList<GameCommendItemBean> mArray;
	public GameCommendAdapter(Context context) {
		this.mLayoutInflater = LayoutInflater.from(context);
		this.mArray = new ArrayList<GameVoteItemBean>();
	}
	/**
	 * 翻页
	 * @param array
	 */
	public void insert(GameVoteItemBean bean){
		mArray.add(0,bean);
		notifyDataSetChanged();
	}
	/**
	 * 翻页
	 * @param array
	 */
	public void append(ArrayList<GameVoteItemBean> array){
		mArray.addAll(array);
		notifyDataSetChanged();
	}
	/**
	 * 刷新
	 * @param array
	 */
	public void refresh(ArrayList<GameVoteItemBean> array){
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
		final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mLayoutInflater.inflate(E_layout.efun_pd_game_commend_list_item, null);
			holder.mIcon = (ImageView) convertView.findViewById(E_id.item_icon);
			holder.mTitle = (TextView) convertView.findViewById(E_id.item_title);
			holder.mContent = (TextView) convertView.findViewById(E_id.item_content);
			holder.mTime = (TextView) convertView.findViewById(E_id.item_time);
			holder.mBarView = (RatingBarView) convertView.findViewById(E_id.game_commend_ratingbar);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		ImageManager.displayImage(mArray.get(position).getIcon(), holder.mIcon, ImageManager.IMAGE_ROUND_USER,E_dimens.e_size_8);
		if(TextUtils.isEmpty(mArray.get(position).getNickname())){			
			holder.mTitle.setText(E_string.efun_pd_anonymous);			
		}else{
			holder.mTitle.setText(mArray.get(position).getNickname());			
		}
		holder.mTime.setText(TimeFormatUtil.LongFormatDate(mArray.get(position).getCreatedTime()));
		String content = mArray.get(position).getReview();
		if(!TextUtils.isEmpty(content)){
			content = content.trim();
		}
		holder.mContent.setText(content);
		
		holder.mBarView.setStarIVSelectImage(E_drawable.efun_pd_star_yellow_select);
		holder.mBarView.setStarIVUnSelectImage(E_drawable.efun_pd_star_grey_unselect);
		holder.mBarView.setStarWidth(E_dimens.e_size_20);
		holder.mBarView.createdStarBarWidthGrey(mArray.get(position).getStar());
		return convertView;
	}

	public static class ViewHolder {
		public TextView mTitle, mContent, mTime;
		public ImageView mIcon;
		private RatingBarView mBarView;
	}
}
