package com.efun.platform.module.summary.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.efun.platform.AndroidScape.E_drawable;
import com.efun.platform.AndroidScape.E_id;
import com.efun.platform.AndroidScape.E_layout;
import com.efun.platform.IPlatApplication;
import com.efun.platform.image.core.ImageLoader;
import com.efun.platform.image.core.imageaware.ImageAware;
import com.efun.platform.image.core.imageaware.ImageViewAware;
import com.efun.platform.module.base.impl.OnEfunItemAttrsClickListener;
import com.efun.platform.module.summary.bean.SummaryItemBean;
import com.efun.platform.utils.Const.ClickButtonType;
import com.efun.platform.utils.Const.Web;
import com.efun.platform.utils.TimeFormatUtil;

/**
 * 资讯列表适配器
 * @author itxuxxey
 * 
 */
public class VideoAdapter extends BaseAdapter {
	/**
	 * {@link LayoutInflater}
	 */
	private LayoutInflater mLayoutInflater;
	private ArrayList<SummaryItemBean> mArray;
	private OnEfunItemAttrsClickListener onClick;
//	private Map<String, String> mMap;
	public VideoAdapter(Context context) {
		this.mLayoutInflater = LayoutInflater.from(context);
		this.mArray = new ArrayList<SummaryItemBean>();
//		this.mMap = new HashMap<String, String>();
	}
	
	public void setOnClickLinstener(OnEfunItemAttrsClickListener onClick){
		this.onClick = onClick;
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
	/**
	 * 
	 */
	public void refreshByOne(int position,SummaryItemBean bean){
		mArray.set(position, bean);
		notifyDataSetChanged();
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
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mLayoutInflater.inflate(E_layout.efun_pd_summary_video_list_item, null);
			holder.mVideo = (ImageView) convertView.findViewById(E_id.video_play);
			holder.mVideoZan = (ImageView) convertView.findViewById(E_id.video_zan);
			holder.mVideoShare = (ImageView) convertView.findViewById(E_id.video_share);
			holder.mVideoTimeLong = (TextView) convertView.findViewById(E_id.video_time);
			holder.mVideoAllTitle = (TextView) convertView.findViewById(E_id.video_all_title);
			holder.mVideoLittleTitle = (TextView) convertView.findViewById(E_id.video_little_title);
			holder.mVideoUpdateTime = (TextView) convertView.findViewById(E_id.video_update_time);
			holder.mVideoZanCount = (TextView) convertView.findViewById(E_id.video_zan_count);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
//		holder.mVideo.setBackgroundResource(E_drawable.efun_pd_default_rectangle_h_icon);
		holder.mVideo.setImageResource(E_drawable.efun_pd_default_rectangle_h_icon);
		final SummaryItemBean bean = mArray.get(position);
//		if(mMap.get(position+"") == null){
//			Log.i("efun", "position:"+position);
//			mMap.put(position+"", bean.getVideoPic());
//		}
//		if(mMap.get(position+"").equals(bean.getVideoPic())){
//			ImageAware imageAware = new ImageViewAware(holder.mVideo, false);
			ImageLoader.getInstance().displayImage(bean.getVideoPic(), holder.mVideo);
//		}else{
//			ImageLoader.getInstance().displayImage(null, holder.mVideo);
//			holder.mVideo.setBackgroundResource(E_drawable.efun_pd_default_rectangle_h_icon);
//		}
		holder.mVideoTimeLong.setText(bean.getVideoTime());
		holder.mVideoAllTitle.setText(bean.getTitle());
		holder.mVideoLittleTitle.setText(bean.getSubTitle());
		holder.mVideoUpdateTime.setText(TimeFormatUtil.LongFormatDate4(bean.getCrtime()));
		if(bean.getLikes() != 0){
			holder.mVideoZanCount.setVisibility(View.VISIBLE);
			holder.mVideoZanCount.setText(bean.getLikes()+"");
		}
		
		if(bean.getIsLiked() == 1){
			holder.mVideoZan.setBackgroundResource(E_drawable.efun_pd_summary_video_red_heart);
		}else{
			holder.mVideoZan.setBackgroundResource(E_drawable.efun_pd_summary_video_grey_heart);
		}
		
		holder.mVideoZan.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View paramView) {
				// TODO Auto-generated method stub
				onClick.onItemClick(position, ClickButtonType.ZAN, bean);
			}
		});
		holder.mVideoShare.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View paramView) {
				// TODO Auto-generated method stub
				onClick.onItemClick(position, ClickButtonType.SHARE, bean);
			}
		});
		return convertView;
	}
	
	public static class ViewHolder {
		public TextView mVideoTimeLong;
		public TextView mVideoAllTitle;
		public TextView mVideoLittleTitle;
		public TextView mVideoUpdateTime;
		public TextView mVideoZanCount;
		public ImageView mVideo;
		public ImageView mVideoZan;
		public ImageView mVideoShare;
	}
	
}


