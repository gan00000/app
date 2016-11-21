package com.efun.platform.module.game.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.efun.core.tools.EfunStringUtil;
import com.efun.platform.AndroidScape.E_array;
import com.efun.platform.AndroidScape.E_drawable;
import com.efun.platform.AndroidScape.E_id;
import com.efun.platform.AndroidScape.E_layout;
import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.image.ImageManager;
import com.efun.platform.module.PopWindow;
import com.efun.platform.module.base.impl.OnEfunItemClickListener;
import com.efun.platform.module.game.bean.GameItemBean;
import com.efun.platform.module.utils.AppUtils;
import com.efun.platform.module.utils.IntentUtils;
import com.efun.platform.module.utils.PopUtils;
import com.efun.platform.module.utils.TrackingGoogleUtils;
import com.efun.platform.module.utils.TrackingUtils;
import com.efun.platform.utils.Const.AppStatus;
import com.efun.platform.utils.Const.HttpParam;
import com.efun.platform.utils.Const.Web;
import com.efun.platform.utils.StatLogInfoUtils;
/**
 * 游戏列表适配器
 * @author Jesse
 *
 */
public class GameAdapter extends BaseAdapter {
	private LayoutInflater mLayoutInflater;
	private ArrayList<GameItemBean> mArray;
	private Context mContext;
	private PopWindow mDownloadChoice,mStartAppChoice;
	private int pressIndex;
	public GameAdapter(Context context) {
		this.mLayoutInflater = LayoutInflater.from(context);
		this.mContext = context;
		this.mArray = new ArrayList<GameItemBean>();
	}
	/**
	 * 翻页
	 * @param array
	 */
	public void append(ArrayList<GameItemBean> array){
		mArray.addAll(array);
		notifyDataSetChanged();
	}
	/**
	 * 刷新
	 * @param array
	 */
	public void refresh(ArrayList<GameItemBean> array){
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
			convertView = mLayoutInflater.inflate(E_layout.efun_pd_game_list_item, null);
			holder.mIcon = (ImageView) convertView.findViewById(E_id.item_icon);
			holder.mTitle = (TextView) convertView.findViewById(E_id.item_title);
			holder.mContent = (TextView) convertView.findViewById(E_id.item_content);
			holder.mCategory = (TextView) convertView.findViewById(E_id.item_category);
			holder.mCount = (TextView) convertView.findViewById(E_id.item_count);
			holder.mButton = (TextView) convertView.findViewById(E_id.item_button);
			holder.mFBLayout = (LinearLayout) convertView.findViewById(E_id.item_fb);
//			holder.mText = (TextView) convertView.findViewById(E_id.item_text);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		ImageManager.displayImage(mArray.get(position).getSmallpic(), holder.mIcon,ImageManager.IMAGE_SQUARE);
		holder.mTitle.setText(mArray.get(position).getGameName());
		holder.mContent.setText(mArray.get(position).getGameDesc());
		holder.mCategory.setText(mArray.get(position).getGameType());
		holder.mCount.setText(mArray.get(position).getLike()+ mContext.getString(E_string.efun_pd_game_item_zan));
		
		//FB粉丝页
		holder.mFBLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				IntentUtils.go2WebForVideo(mContext, mArray.get(position).get);
				if(!EfunStringUtil.isEmpty(mArray.get(position).getFbUrl())){
					IntentUtils.go2Web(mContext, Web.WEB_GO_GAME_FB, mArray.get(position));
				}
			}
		});
		
		//选择下载
		mDownloadChoice = PopUtils.createDownLoadAreaChose(mContext, holder.mButton, mContext.getResources().getStringArray(E_array.efun_pd_pop_chose_area_download), new OnEfunItemClickListener() {
			
			@Override
			public void onItemClick(int Index) {
				// TODO Auto-generated method stub
				Log.i("efun", "position1:"+pressIndex);
				if(Index == 0){
					if(mArray.get(pressIndex).getStatus() == AppStatus.UPDATE){
						TrackingUtils.track(mContext,TrackingUtils.ACTION_GAME, TrackingUtils.NAME_GAME_UPDATE_TW_PLAYER+"_"+mArray.get(pressIndex).getGameCode());
						TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_GAME, mArray.get(pressIndex).getGameName()+TrackingGoogleUtils.NAME_GAME_UPDATE_TW_PLAYER);
						StatLogInfoUtils.setStaticLogInfo(mContext, mArray.get(pressIndex).getGameCode(), HttpParam.GAMELIST, HttpParam.UPDATECLICK, HttpParam.GAME_TW);
					}else{
						TrackingUtils.track(mContext,TrackingUtils.ACTION_GAME, TrackingUtils.NAME_GAME_DOWNLOAD_TW_PLAYER+"_"+mArray.get(pressIndex).getGameCode());
						TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_GAME, mArray.get(pressIndex).getGameName()+TrackingGoogleUtils.NAME_GAME_DOWNLOAD_TW_PLAYER);
						StatLogInfoUtils.setStaticLogInfo(mContext, mArray.get(pressIndex).getGameCode(), HttpParam.GAMELIST, HttpParam.DOWNLOADCLICK, HttpParam.GAME_TW);
					}
					AppUtils.download(mContext, mArray.get(pressIndex).getAndroidDownload());
				}else{
					if(mArray.get(pressIndex).getStatus() == AppStatus.UPDATE){
						TrackingUtils.track(mContext,TrackingUtils.ACTION_GAME, TrackingUtils.NAME_GAME_UPDATE_HK_PLAYER + "_"+mArray.get(pressIndex).getGameCode());
						TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_GAME, mArray.get(pressIndex).getGameName()+TrackingGoogleUtils.NAME_GAME_UPDATE_HK_PLAYER);
						StatLogInfoUtils.setStaticLogInfo(mContext, mArray.get(pressIndex).getGameCode(), HttpParam.GAMELIST, HttpParam.UPDATECLICK, HttpParam.GAME_HK);
					}else{
						TrackingUtils.track(mContext,TrackingUtils.ACTION_GAME,TrackingUtils.NAME_GAME_DOWNLOAD_HK_PLAYER+"_"+mArray.get(pressIndex).getGameCode());
						TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_GAME, mArray.get(pressIndex).getGameName()+TrackingGoogleUtils.NAME_GAME_DOWNLOAD_HK_PLAYER);
						StatLogInfoUtils.setStaticLogInfo(mContext, mArray.get(pressIndex).getGameCode(), HttpParam.GAMELIST, HttpParam.DOWNLOADCLICK, HttpParam.GAME_HK);
					}
					AppUtils.download(mContext, mArray.get(pressIndex).getHkAndroidDownloadURL());
				}
			}
		});
		mStartAppChoice = PopUtils.createStartAPPChose(mContext, holder.mButton, mContext.getResources().getStringArray(E_array.efun_pd_pop_chose_area_download), new OnEfunItemClickListener() {
			
			@Override
			public void onItemClick(int Index) {
				// TODO Auto-generated method stub
				if(Index == 0){
					TrackingUtils.track(mContext,TrackingUtils.ACTION_GAME, TrackingUtils.NAME_GAME_START_TW_PLAYER+"_"+mArray.get(pressIndex).getGameCode());
					TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_GAME, mArray.get(pressIndex).getGameName()+TrackingGoogleUtils.NAME_GAME_START_TW_PLAYER);
					StatLogInfoUtils.setStaticLogInfo(mContext, mArray.get(pressIndex).getGameCode(), HttpParam.GAMELIST, HttpParam.STARTAPP, HttpParam.GAME_TW);
					AppUtils.startApp(mContext, mArray.get(pressIndex).getPackageName());
				}else{
					TrackingUtils.track(mContext,TrackingUtils.ACTION_GAME, TrackingUtils.NAME_GAME_START_HK_PLAYER+"_"+mArray.get(pressIndex).getGameCode());
					TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_GAME, mArray.get(pressIndex).getGameName()+TrackingGoogleUtils.NAME_GAME_START_HK_PLAYER);
					StatLogInfoUtils.setStaticLogInfo(mContext, mArray.get(pressIndex).getGameCode(), HttpParam.GAMELIST, HttpParam.STARTAPP, HttpParam.GAME_HK);
					AppUtils.startApp(mContext, mArray.get(pressIndex).getHkPackageName());
				}
			}
		});
		
		if(EfunStringUtil.isEmpty(mArray.get(position).getHkAndroidDownloadURL())){//只有一个地区包的游戏
			if(AppUtils.isAppInstalled(mContext, mArray.get(position).getPackageName())){
				if(AppUtils.isAppUpdate(mContext, mArray.get(position).getPackageName(), mArray.get(position).getAndroidVersion())){
//					holder.mButton.setBackgroundResource(E_drawable.efun_pd_game_update_selector);
					holder.mButton.setText(E_string.efun_pd_game_update);
					holder.mButton.setBackgroundResource(E_drawable.efun_pd_game_update_icon_bg);
					holder.mButton.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							if(!EfunStringUtil.isEmpty(mArray.get(position).getHkAndroidDownloadURL())){
								Log.i("efun", "position3:"+position);
								pressIndex = position;
								mDownloadChoice.showPopWindow(PopWindow.POP_WINDOW_CHOSE_DOWNLOAD_AREA);
							}else{
								TrackingUtils.track(mContext,TrackingUtils.ACTION_GAME, TrackingUtils.NAME_GAME_UPDATE+"_"+mArray.get(position).getGameCode());
								TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_GAME, mArray.get(position).getGameName()+TrackingGoogleUtils.NAME_GAME_UPDATE);
								StatLogInfoUtils.setStaticLogInfo(mContext, mArray.get(position).getGameCode(), HttpParam.GAMELIST, HttpParam.UPDATECLICK, HttpParam.GAME_TW);
								AppUtils.download(mContext, mArray.get(position).getAndroidDownload());
							}
						}
					});
					mArray.get(position).setStatus(AppStatus.UPDATE);
				}else{
//					holder.mButton.setBackgroundResource(E_drawable.efun_pd_game_start_selector);
					holder.mButton.setText(E_string.efun_pd_game_start);
					holder.mButton.setBackgroundResource(E_drawable.efun_pd_game_start_icon_bg);
					holder.mButton.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							TrackingUtils.track(mContext,TrackingUtils.ACTION_GAME, TrackingUtils.NAME_GAME_START+"_"+mArray.get(position).getGameCode());
							TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_GAME, mArray.get(position).getGameName()+TrackingGoogleUtils.NAME_GAME_START);
							StatLogInfoUtils.setStaticLogInfo(mContext, mArray.get(position).getGameCode(), HttpParam.GAMELIST, HttpParam.STARTAPP, HttpParam.GAME_TW);
							AppUtils.startApp(mContext, mArray.get(position).getPackageName());
						}
					});
					mArray.get(position).setStatus(AppStatus.START);
				}
			}else{
//				holder.mButton.setBackgroundResource(E_drawable.efun_pd_game_download_selector);
				holder.mButton.setText(E_string.efun_pd_game_download);
				holder.mButton.setBackgroundResource(E_drawable.efun_pd_game_download_icon_bg);
				holder.mButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if(!EfunStringUtil.isEmpty(mArray.get(position).getHkAndroidDownloadURL())){
							pressIndex = position;
							mDownloadChoice.showPopWindow(PopWindow.POP_WINDOW_CHOSE_DOWNLOAD_AREA);
						}else{
							TrackingUtils.track(mContext,TrackingUtils.ACTION_GAME, TrackingUtils.NAME_GAME_DOWNLOAD+"_"+mArray.get(position).getGameCode());
							TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_GAME, mArray.get(position).getGameName()+TrackingGoogleUtils.NAME_GAME_DOWNLOAD);
							StatLogInfoUtils.setStaticLogInfo(mContext, mArray.get(position).getGameCode(), HttpParam.GAMELIST, HttpParam.DOWNLOADCLICK, HttpParam.GAME_TW);
							AppUtils.download(mContext, mArray.get(position).getAndroidDownload());
						}
						//测试所用
//						AppUtils.startApp(mContext, "com.efun.tc");
					}
				});
				mArray.get(position).setStatus(AppStatus.DOWNLOAD);
			}
		}else{//有两个地区包的游戏
			if(AppUtils.isAppInstalled(mContext, mArray.get(position).getPackageName()) || AppUtils.isAppInstalled(mContext, mArray.get(position).getHkPackageName())){//设备里有一个地区包或者有两个地区的包
				if((AppUtils.isAppInstalled(mContext, mArray.get(position).getPackageName()) && 
						AppUtils.isAppUpdate(mContext,mArray.get(position).getPackageName(),mArray.get(position).getAndroidVersion())) || 
						(AppUtils.isAppInstalled(mContext,mArray.get(position).getHkPackageName()) && 
								AppUtils.isAppUpdate(mContext, mArray.get(position).getHkPackageName(),mArray.get(position).getHkAndroidVersion()))){
//					holder.mButton.setBackgroundResource(E_drawable.efun_pd_game_update_selector);
					holder.mButton.setText(E_string.efun_pd_game_update);
					holder.mButton.setBackgroundResource(E_drawable.efun_pd_game_update_icon_bg);
					holder.mButton.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							if(AppUtils.isAppInstalled(mContext, mArray.get(position).getPackageName()) && !AppUtils.isAppInstalled(mContext, mArray.get(position).getHkPackageName())){
								TrackingUtils.track(mContext,TrackingUtils.ACTION_GAME, TrackingUtils.NAME_GAME_UPDATE_TW_PLAYER+"_"+mArray.get(position).getGameCode());
								TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_GAME, mArray.get(position).getGameName()+TrackingGoogleUtils.NAME_GAME_UPDATE_TW_PLAYER);
								StatLogInfoUtils.setStaticLogInfo(mContext, mArray.get(position).getGameCode(), HttpParam.GAMELIST, HttpParam.UPDATECLICK, HttpParam.GAME_TW);
								AppUtils.download(mContext, mArray.get(position).getAndroidDownload());
							}else if(!AppUtils.isAppInstalled(mContext, mArray.get(position).getPackageName()) && AppUtils.isAppInstalled(mContext, mArray.get(position).getHkPackageName())){
								TrackingUtils.track(mContext,TrackingUtils.ACTION_GAME, TrackingUtils.NAME_GAME_UPDATE_HK_PLAYER+"_"+mArray.get(position).getGameCode());
								TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_GAME, mArray.get(position).getGameName()+TrackingGoogleUtils.NAME_GAME_UPDATE_HK_PLAYER);
								StatLogInfoUtils.setStaticLogInfo(mContext, mArray.get(position).getGameCode(), HttpParam.GAMELIST, HttpParam.UPDATECLICK, HttpParam.GAME_HK);
								AppUtils.download(mContext, mArray.get(position).getHkAndroidDownloadURL());
							}else{
								pressIndex = position;
								mDownloadChoice.showPopWindow(PopWindow.POP_WINDOW_CHOSE_DOWNLOAD_AREA);
							}
//							if(!EfunStringUtil.isEmpty(mArray.get(index).getHkAndroidDownloadURL())){
//								mDownloadChoice.showPopWindow(PopWindow.POP_WINDOW_CHOSE_DOWNLOAD_AREA);
//							}else{
//								AppUtils.download(mContext, mArray.get(index).getAndroidDownload());
//							}
						}
					});
					mArray.get(position).setStatus(AppStatus.UPDATE);
				}else{
//					holder.mButton.setBackgroundResource(E_drawable.efun_pd_game_start_selector);
					holder.mButton.setText(E_string.efun_pd_game_start);
					holder.mButton.setBackgroundResource(E_drawable.efun_pd_game_start_icon_bg);
					holder.mButton.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							if(AppUtils.isAppInstalled(mContext, mArray.get(position).getPackageName()) && !AppUtils.isAppInstalled(mContext, mArray.get(position).getHkPackageName())){
								TrackingUtils.track(mContext,TrackingUtils.ACTION_GAME, TrackingUtils.NAME_GAME_START_TW_PLAYER+"_"+mArray.get(position).getGameCode());
								TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_GAME, mArray.get(position).getGameName()+TrackingGoogleUtils.NAME_GAME_START_TW_PLAYER);
								StatLogInfoUtils.setStaticLogInfo(mContext, mArray.get(position).getGameCode(), HttpParam.GAMELIST, HttpParam.STARTAPP, HttpParam.GAME_TW);
								AppUtils.startApp(mContext, mArray.get(position).getPackageName());
							}else if(!AppUtils.isAppInstalled(mContext, mArray.get(position).getPackageName()) && AppUtils.isAppInstalled(mContext, mArray.get(position).getHkPackageName())){
								TrackingUtils.track(mContext,TrackingUtils.ACTION_GAME, TrackingUtils.NAME_GAME_START_HK_PLAYER+"_"+mArray.get(position).getGameCode());
								TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_GAME, mArray.get(position).getGameName()+TrackingGoogleUtils.NAME_GAME_START_HK_PLAYER);
								StatLogInfoUtils.setStaticLogInfo(mContext, mArray.get(position).getGameCode(), HttpParam.GAMELIST, HttpParam.STARTAPP, HttpParam.GAME_HK);
								AppUtils.startApp(mContext, mArray.get(position).getHkPackageName());
							}else{
								pressIndex = position;
								mStartAppChoice.showPopWindow(PopWindow.POP_WINDOW_CHOSE_START_APP);
							}
						}
					});
					mArray.get(position).setStatus(AppStatus.START);
				}
			}else{
//				holder.mButton.setBackgroundResource(E_drawable.efun_pd_game_download_selector);
				holder.mButton.setText(E_string.efun_pd_game_download);
				holder.mButton.setBackgroundResource(E_drawable.efun_pd_game_download_icon_bg);
				holder.mButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if(!EfunStringUtil.isEmpty(mArray.get(position).getHkAndroidDownloadURL())){
							Log.i("efun", "position2:"+position);
							pressIndex = position;
							mDownloadChoice.showPopWindow(PopWindow.POP_WINDOW_CHOSE_DOWNLOAD_AREA);
						}else{
							TrackingUtils.track(mContext,TrackingUtils.ACTION_GAME, TrackingUtils.NAME_GAME_DOWNLOAD_TW_PLAYER+"_"+mArray.get(position).getGameCode());
							TrackingGoogleUtils.track(TrackingGoogleUtils.ACTION_GAME, mArray.get(position).getGameName()+TrackingGoogleUtils.NAME_GAME_DOWNLOAD_TW_PLAYER);
							StatLogInfoUtils.setStaticLogInfo(mContext, mArray.get(position).getGameCode(), HttpParam.GAMELIST, HttpParam.DOWNLOADCLICK, HttpParam.GAME_TW);
							AppUtils.download(mContext, mArray.get(position).getAndroidDownload());
						}
					}
				});
				mArray.get(position).setStatus(AppStatus.DOWNLOAD);
			}
		}
		
		return convertView;
	}

	public static class ViewHolder {
		public TextView mTitle, mContent, mCategory, mCount,mButton;
		public ImageView mIcon;
		public LinearLayout mFBLayout;
	}

}
