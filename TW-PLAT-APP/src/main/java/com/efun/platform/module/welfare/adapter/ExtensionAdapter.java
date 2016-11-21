package com.efun.platform.module.welfare.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.efun.core.tools.EfunStringUtil;
import com.efun.platform.AndroidScape.E_color;
import com.efun.platform.AndroidScape.E_drawable;
import com.efun.platform.AndroidScape.E_id;
import com.efun.platform.AndroidScape.E_layout;
import com.efun.platform.IPlatApplication;
import com.efun.platform.image.ImageManager;
import com.efun.platform.module.account.activity.BindPhoneActivityNew;
import com.efun.platform.module.account.bean.User;
import com.efun.platform.module.base.impl.OnUpdateUserListener;
import com.efun.platform.module.utils.IntentUtils;
import com.efun.platform.module.utils.TrackingGoogleUtils;
import com.efun.platform.module.utils.TrackingUtils;
import com.efun.platform.module.welfare.bean.ActExtensionBean;
import com.efun.platform.module.welfare.bean.GiftItemBean;
import com.efun.platform.utils.TimeFormatUtil;
import com.efun.platform.widget.RoundCornerTextView;
/**
 * 好康内容适配器
 * @author itxuxxey
 *
 */
public class ExtensionAdapter extends BaseAdapter {
	private LayoutInflater mLayoutInflater;
	private ArrayList<ActExtensionBean> mExtensions;
	private ArrayList<GiftItemBean> mRecommendGifts;
//	private String DataType = "mExtensions";
	private int count;
	
	private Context mContext;

	public ExtensionAdapter(Context context) {
		this.mContext = context;
		this.mLayoutInflater = LayoutInflater.from(context);
		this.mExtensions = new ArrayList<ActExtensionBean>();
		this.mRecommendGifts = new ArrayList<GiftItemBean>();
	}
	

	@Override
	public int getCount() {
		return mExtensions.size()+mRecommendGifts.size();
	}

	@Override
	public Object getItem(int position) {
		Log.i("yang", "adposition:"+position);
		if(mExtensions.size() != 0 && mRecommendGifts.size() != 0){
			if(position < mExtensions.size()){
				return mExtensions.get(position);
			}else{
				return mRecommendGifts.get(position-mExtensions.size());
			}
		}else if(mExtensions.size() != 0 && mRecommendGifts.size() == 0){
			return mExtensions.get(position);
		}else if(mExtensions.size() == 0 && mRecommendGifts.size() != 0){
			return mRecommendGifts.get(position);
		}else{
			return null;
		}
//		return mExtensions.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void addExtensions(ArrayList<ActExtensionBean> array){
		mExtensions.clear();
		if(array != null){
			mExtensions.addAll(array);
		}
//		DataType = "mExtensions";
		notifyDataSetChanged();
	}
	
	public void addRecommondGifts(ArrayList<GiftItemBean> array){
		mRecommendGifts.clear();
		if(array != null){
			mRecommendGifts.addAll(array);
		}
//		DataType = "mRecommendGifts";
		notifyDataSetChanged();
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Log.i("yang", "getViewposition:"+position);
		final ViewHolder holder;
		final GiftViewHolder giftHolder;
		if(position < mExtensions.size()){
				holder = new ViewHolder();
				convertView = mLayoutInflater.inflate(E_layout.efun_pd_welfare_item_new, null);
				holder.mIcon = (ImageView) convertView.findViewById(E_id.welfare_game_icon);
				holder.mFinishIco = (ImageView) convertView.findViewById(E_id.welfare_task_finish_icon);
				holder.mDoingBtn = (ImageView) convertView.findViewById(E_id.item_icon2);
				holder.mTitle = (TextView)convertView.findViewById(E_id.welfare_game_title);
				holder.mLastGift = (TextView)convertView.findViewById(E_id.welfare_task_gift_remain);
				holder.mGameContent = (TextView)convertView.findViewById(E_id.item_content);
				holder.mBindPhone = (TextView)convertView.findViewById(E_id.item_bind_phone);
				holder.mFootLayout = convertView.findViewById(E_id.foot_kong_view);
//				convertView.setTag(holder);
			if(mExtensions != null && IPlatApplication.get().getUser() != null){
				if(!EfunStringUtil.isEmpty(IPlatApplication.get().getUser().getPhone())){
					//点数卡已被领取完
					if(mExtensions.get(position).getGiftsLastCount() == 0){
						holder.mFinishIco.setVisibility(View.VISIBLE);
						if(mExtensions.get(position).getArrayOfTask() != null && mExtensions.get(position).getArrayOfTask().size() != 0){
							//还未执行过任务
							if(mExtensions.get(position).getArrayOfTask().get(0).getCurrentState().equals("0")){
								holder.mDoingBtn.setVisibility(View.GONE);
								holder.mBindPhone.setVisibility(View.GONE);
							}
							//还在执行任务中
							if(mExtensions.get(position).getArrayOfTask().get(0).getCurrentState().equals("2")){
								holder.mDoingBtn.setBackgroundResource(E_drawable.efun_pd_welfare_item_continue_finish_btn);
							}
						}
						
					}else{//点数卡暂未被领取完
						holder.mFinishIco.setVisibility(View.GONE);
						//玩家处于执行状态
						if(mExtensions.get(position).getArrayOfTask() != null && mExtensions.get(position).getArrayOfTask().size() != 0){
							if(mExtensions.get(position).getArrayOfTask().get(0).getCurrentState().equals("2")){
								holder.mDoingBtn.setBackgroundResource(E_drawable.efun_pd_welfare_item_continue_normal_btn);
							}
						}
					}
					//用户已完成任务
					if(mExtensions.get(position).getCurrentState().equals("2")){
						holder.mDoingBtn.setBackgroundResource(E_drawable.efun_pd_welfare_item_task_finish);
					}
				}else{
					holder.mDoingBtn.setVisibility(View.GONE);
					holder.mBindPhone.setVisibility(View.VISIBLE);
					//点数卡已被领取完
					if(mExtensions.get(position).getGiftsLastCount() == 0){
						holder.mFinishIco.setVisibility(View.VISIBLE);
					}else{
						holder.mFinishIco.setVisibility(View.GONE);
					}
				}
				holder.mBindPhone.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View paramView) {
						// TODO Auto-generated method stub
						IntentUtils.goWithBeanForResult(mContext,BindPhoneActivityNew.class, IPlatApplication.get().getUser(),
								new OnUpdateUserListener() {
									@Override
									public void onUpdate(User userInfo) {
										TrackingUtils.track(mContext,TrackingUtils.ACTION_WELFARE, TrackingUtils.NAME_WELFARE_BIND_PHONE);
										TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_WELFARE, TrackingGoogleUtils.NAME_WELFARE_BIND_PHONE);
										holder.mBindPhone.setVisibility(View.GONE);
										holder.mDoingBtn.setVisibility(View.VISIBLE);
										IPlatApplication.get().setUser(userInfo);
									}
								});
					}
				});
				holder.mDoingBtn.setClickable(false);
				holder.mTitle.setText(mExtensions.get(position).getGameBean().getGameName());
				holder.mGameContent.setText(mExtensions.get(position).getOutLine());//一句話描述
				holder.mLastGift.setText(mExtensions.get(position).getRewarddescription());//禮包描述
				ImageManager.displayImage(mExtensions.get(position).getGameBean().getSmallpic(), holder.mIcon,ImageManager.IMAGE_SQUARE);
			}
			if(position == (mExtensions.size() - 1)){
				holder.mFootLayout.setVisibility(View.VISIBLE);
			}
		}else if(position < getCount()){
				giftHolder = new GiftViewHolder();
				convertView = mLayoutInflater.inflate(E_layout.efun_pd_welfare_extension_gift_list_item, null);
				giftHolder.mRoundCornerTextView= (RoundCornerTextView) convertView.findViewById(E_id.item_text);
				giftHolder.mIcon = (ImageView) convertView.findViewById(E_id.item_icon);
				giftHolder.mTitle = (TextView) convertView.findViewById(E_id.item_title);
				giftHolder.mCount = (TextView) convertView.findViewById(E_id.item_count);
				giftHolder.mTime = (TextView) convertView.findViewById(E_id.item_time);
				giftHolder.mGiftRemain = (TextView) convertView.findViewById(E_id.item_gift_remain);
				giftHolder.mCurProgressBar=  (ProgressBar) convertView.findViewById(E_id.curProgressBar);
				giftHolder.mLine= convertView.findViewById(E_id.gift_fenge_line);
				
				if(position-mExtensions.size() == 0){
					giftHolder.mLine.setVisibility(View.VISIBLE);
				}
//				convertView.setTag(giftHolder);
			if(mRecommendGifts != null && mRecommendGifts.size() > 0){
				ImageManager.displayImage(mRecommendGifts.get(position-mExtensions.size()).getIcon(), giftHolder.mIcon,ImageManager.IMAGE_SQUARE);			
				giftHolder.mCount.setText((int)((1 - Double.valueOf(mRecommendGifts.get(position-mExtensions.size()).getUsePercent()))*100)+"%");
				giftHolder.mTime.setText(TimeFormatUtil.StringFormatDate2(mRecommendGifts.get(position-mExtensions.size()).getActiveEndTime()));
				count = mRecommendGifts.get(position-mExtensions.size()).getTotal() - mRecommendGifts.get(position-mExtensions.size()).getHasUse();
				giftHolder.mGiftRemain.setText(count+"");
				giftHolder.mCurProgressBar.setProgress((int) ((1 - Double.valueOf(mRecommendGifts.get(position-mExtensions.size()).getUsePercent()))*100));			
			
				giftHolder.mTitle.setText(mRecommendGifts.get(position-mExtensions.size()).getGameName());
//				giftHolder.mRoundCornerTextView.setText(mRecommendGifts.get(position-mExtensions.size()).getGoodsTypeName());
				giftHolder.mRoundCornerTextView.setText(mRecommendGifts.get(position-mExtensions.size()).getTag());
				giftHolder.mRoundCornerTextView.setColor(mContext.getResources().getColor(E_color.e_ff5ad3));
//				if(mRecommendGifts.get(position-mExtensions.size()).getGoodsType().equals("fresher")){
//					giftHolder.mRoundCornerTextView.setColor(mContext.getResources().getColor(E_color.e_ff5ad3));
//					giftHolder.mRoundCornerTextView.setText(mContext.getResources().getString(E_string.efun_pd_gifts_type_fresher));
//				}else if(mRecommendGifts.get(position-mExtensions.size()).getGoodsType().equals("timelimit")){
//					giftHolder.mRoundCornerTextView.setColor(mContext.getResources().getColor(E_color.e_00bf8e));
//					giftHolder.mRoundCornerTextView.setText(mContext.getResources().getString(E_string.efun_pd_gifts_type_timelimit));
//				}else if(mRecommendGifts.get(position-mExtensions.size()).getGoodsType().equals("unique")){
//					giftHolder.mRoundCornerTextView.setColor(mContext.getResources().getColor(E_color.e_fe5a66));
//					giftHolder.mRoundCornerTextView.setText(mContext.getResources().getString(E_string.efun_pd_gifts_type_unique));
//				}
			}
		}
		
		
		return convertView;
	}

	public static class ViewHolder {
		public TextView mTitle,mLastGift,mGameContent,mBindPhone;
		public ImageView mIcon,mFinishIco,mDoingBtn;
		public View mFootLayout;
	}
	
	public static class GiftViewHolder{
		public TextView mTitle, mCount, mTime,mGiftRemain;
		public RoundCornerTextView mRoundCornerTextView;
		public ImageView mIcon;
		public ProgressBar mCurProgressBar;
		public View mLine;
	}

}
