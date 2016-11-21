package com.efun.platform.module.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

//import com.efun.facebook.share.self.FacebookSelfPluginImpl.FeedCallback;
import com.efun.platform.AndroidScape.E_id;
import com.efun.platform.AndroidScape.E_layout;
import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.AndroidScape.E_style;
import com.efun.platform.module.BaseDataBean;
import com.efun.platform.module.PopWindow;
import com.efun.platform.module.PopWindow.OnPopListener;
import com.efun.platform.module.base.impl.OnEfunItemClickListener;
import com.efun.platform.module.dimensionalcode.activity.DimensionalCodeActivity;
import com.efun.platform.module.settting.activity.SettingActivity;
import com.efun.platform.module.summary.bean.SummaryItemBean;
import com.efun.platform.module.welfare.bean.ActItemBean;
import com.efun.platform.utils.Const.BeanType;
import com.efun.platform.utils.Const.Share;
import com.efun.platform.widget.Coordinates;

/**
 * 悬浮框
 * 
 * @author itxuxxey
 * 
 */
public class PopUtils {
	/**
	 * 创建PopWindow
	 * 
	 * @param layoutId
	 * @return
	 */
	public static PopWindow createPopWindow(Context context) {
		return new PopWindow(context);
	}

	/**
	 * 创建PopWindow Content View
	 * 
	 * @param context
	 * @param layoutId
	 * @param parentView
	 * @param mOnPopListener
	 * @return
	 */
	public static PopWindow createPopContent(Context context,
			String popWinowCategory, int layoutId, View parentView,
			OnPopListener mOnPopListener) {
		return createPopContent(context, popWinowCategory, layoutId,
				parentView, 0, mOnPopListener);
	}

	/**
	 * 创建PopWindow Content View
	 * 
	 * @param context
	 * @param layoutId
	 * @param parentView
	 * @param anim
	 * @param mOnPopListener
	 * @return
	 */
	public static PopWindow createPopContent(Context context,
			String popWinowCategory, int layoutId, View parentView, int anim,
			OnPopListener mOnPopListener) {
		PopWindow mPopWindow = createPopWindow(context);
		mPopWindow.createPopWindow(context, popWinowCategory, layoutId,
				parentView, anim, mOnPopListener);
		return mPopWindow;
	}

	/**
	 * 创建二维码扫描，设置PopWindow
	 * 
	 * @param context
	 * @param parentView
	 * @return
	 */
	public static PopWindow createCodeSet(final Context context, View parentView) {
		final PopWindow mPopWindow = createPopContent(context,
				PopWindow.POP_WINDOW_CODE, E_layout.efun_pd_pop, parentView,
				new OnPopListener() {
					@Override
					public void init(final PopWindow pop,final View parent, View container) {
						Coordinates coord = (Coordinates) container
								.findViewById(E_id.coordinates);
						coord.setBevel(4.2);
						LinearLayout itemOne = (LinearLayout) container
								.findViewById(E_id.contaier_linear_1);
						LinearLayout itemTwo = (LinearLayout) container
								.findViewById(E_id.contaier_linear_2);
						itemOne.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								//实现二维码扫描
//								TrackingUtils.track(context,TrackingUtils.ACTION_APP, TrackingUtils.NAME_APP_SCAN);
									Intent it = new Intent(context,DimensionalCodeActivity.class);
									context.startActivity(it);
									pop.dismiss();
							}
						});
						itemTwo.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								//实现设置
//								TrackingUtils.track(TrackingUtils.ACTION_APP, TrackingUtils.NAME_APP_SETTING);
									Intent it  =  new Intent(context, SettingActivity.class);
									context.startActivity(it);
									pop.dismiss();
							}
						});
					}
				});
		return mPopWindow;
	}

	/**
	 * 创建分享PopWindow
	 * 
	 * @param context
	 * @param parentView
	 * @return
	 */
	public static PopWindow createShare(final Context context, View parentView,final BaseDataBean mBean) {
		final PopWindow mPopWindow = createPopContent(context,
				PopWindow.POP_WINDOW_SHARE, E_layout.efun_pd_pop_share,
				parentView, E_style.Pop_Share, new OnPopListener() {
					@Override
					public void init(final PopWindow pop,final View parent, View container) {
						ImageView itemOne = (ImageView) container
								.findViewById(E_id.icon_1);
//						ImageView itemTwo = (ImageView) container
//								.findViewById(E_id.icon_2);
//						ImageView itemThree = (ImageView) container
//								.findViewById(E_id.icon_3);						
						ImageView itemFour = (ImageView) container
								.findViewById(E_id.icon_4);						
//						ImageView itemFive = (ImageView) container
//								.findViewById(E_id.icon_5);						
						if(mBean != null){
							itemOne.setOnClickListener(new OnClickListener() {
								@Override
								public void onClick(View v) {
									//facebook分享
										
										String appName = AppUtils.getAppInfoBeanByPackageName(context, context.getApplicationContext().getPackageName()).getAppLabel();
										switch (mBean.getBeanType()) {
										case BeanType.BEAN_ACTITEMBEAN:
											ShareUtils.shareFacebook(context, ((ActItemBean)mBean).getActivityUrl(), ((ActItemBean)mBean).getImg(), appName, context.getString(E_string.efun_pd_share_activity_content), ((ActItemBean)mBean).getActivityName());
											break;
										case BeanType.BEAN_SUMMARYITEMBEAN:
											ShareUtils.shareFacebook(context, ((SummaryItemBean)mBean).getIphoneUrl(), "", appName, context.getString(E_string.efun_pd_share_summary_content), ((SummaryItemBean)mBean).getTitle());
											break;
										case BeanType.BEAN_SETTINGBEAN:
											ShareUtils.shareFacebook(context, Share.SHARE_APPSHARE_URL, "", appName, context.getString(E_string.efun_pd_share_app_content), appName);
											break;
										}			
										pop.dismiss();
								}
							});

//							itemTwo.setOnClickListener(new OnClickListener() {
//								@Override
//								public void onClick(View v) {
//									//line分享
//										switch (mBean.getBeanType()) {
//										case BeanType.BEAN_ACTITEMBEAN:
//											ShareUtils.shareLine(context, context.getString(E_string.efun_pd_share_activity_content)+" : "+((ActItemBean)mBean).getActivityUrl());										
//											break;
//
//										case BeanType.BEAN_SUMMARYITEMBEAN:
//											ShareUtils.shareLine(context, context.getString(E_string.efun_pd_share_summary_content)+" : "+((SummaryItemBean)mBean).getIphoneUrl());										
//											break;
//										case BeanType.BEAN_SETTINGBEAN:
//											ShareUtils.shareLine(context, context.getString(E_string.efun_pd_share_app_content)+" : "+Share.SHARE_APPSHARE_URL);
//											break;
//										}
//										pop.dismiss();
//								}
//							});
//							itemThree.setOnClickListener(new OnClickListener() {
//							@Override
//							public void onClick(View v) {
//								//google+分享
//									switch (mBean.getBeanType()) {
//									case BeanType.BEAN_ACTITEMBEAN:
//										ShareUtils.shareGoogleJia((Activity) context,context.getString(E_string.efun_pd_share_activity_content),((ActItemBean)mBean).getActivityUrl());
//										break;
//
//									case BeanType.BEAN_SUMMARYITEMBEAN:
//										ShareUtils.shareGoogleJia((Activity) context,context.getString(E_string.efun_pd_share_summary_content),((SummaryItemBean)mBean).getIphoneUrl());
//										break;
//									case BeanType.BEAN_SETTINGBEAN:
//										ShareUtils.shareGoogleJia((Activity) context,context.getString(E_string.efun_pd_share_app_content),Share.SHARE_APPSHARE_URL);
//										break;
//									}										
//									pop.dismiss();
//								}
//							});
							itemFour.setOnClickListener(new OnClickListener() {
								@Override
								public void onClick(View v) {
									//twitter分享
										switch (mBean.getBeanType()) {
										case BeanType.BEAN_ACTITEMBEAN:
											ShareUtils.shareTwitterWithWord(context, context.getString(E_string.efun_pd_share_activity_content)+" : "+((ActItemBean)mBean).getActivityUrl());
											break;

										case BeanType.BEAN_SUMMARYITEMBEAN:
											ShareUtils.shareTwitterWithWord(context, context.getString(E_string.efun_pd_share_summary_content)+" : "+((SummaryItemBean)mBean).getIphoneUrl());
											break;
										case BeanType.BEAN_SETTINGBEAN:
											ShareUtils.shareTwitterWithWord(context, context.getString(E_string.efun_pd_share_app_content)+" : "+Share.SHARE_APPSHARE_URL);
											break;
										}										
										pop.dismiss();
								}
							});
							
//							itemFive.setOnClickListener(new OnClickListener() {
//								
//								@Override
//								public void onClick(View v) {
//									//email分享
//									switch (mBean.getBeanType()) {
//									case BeanType.BEAN_ACTITEMBEAN:
//										ShareUtils.shareEmail(context, context.getString(E_string.efun_pd_share_activity_content), ((ActItemBean)mBean).getActivityName()+" : "+((ActItemBean)mBean).getActivityUrl());
//										break;
//									case BeanType.BEAN_SUMMARYITEMBEAN:
//										ShareUtils.shareEmail(context, context.getString(E_string.efun_pd_share_summary_content), ((SummaryItemBean)mBean).getTitle()+" : "+((SummaryItemBean)mBean).getIphoneUrl());
//										break;
//									case BeanType.BEAN_SETTINGBEAN:
//										ShareUtils.shareEmail(context, context.getString(E_string.efun_pd_share_app_content), context.getString(E_string.efun_pd_share_app_content)+Share.SHARE_APPSHARE_URL);
//										break;
//									}
//									pop.dismiss();
//								}
//							});
						}			
					}
				});
		return mPopWindow;
	}
	
//	/**
//	 * 创建分享PopWindow
//	 * 
//	 * @param context
//	 * @param parentView
//	 * @return
//	 */
//	public static PopWindow createChoseAwards(Context context,final ArrayList<ActExtensionGiftBean> arrayOfGift, View parentView,final OnEfunItemClickListener onEfunItemClickListener) {
//		final PopWindow mPopWindow = createPopContent(context,
//				PopWindow.POP_WINDOW_CHOSE_AWARDS, E_layout.efun_pd_pop_chose_awards,
//				parentView, E_style.Pop_Share, new OnPopListener() {
//					@Override
//					public void init(final PopWindow pop,final View parent, View container) {
//						GiftContainer mAwardsContainer = (GiftContainer) container.findViewById(E_id.awardsContainer);
//						mAwardsContainer.setItemLayout(E_layout.efun_pd_welfare_act_extension_gift_list_item);
//						mAwardsContainer.loadedData(arrayOfGift);
//						mAwardsContainer.addHeaderOrFooter(ListContainer.HEADER_VIEW_FLAG);
//						mAwardsContainer.setOnEfunItemClickListener(onEfunItemClickListener);
//					}
//				});
//		return mPopWindow;
//	}
	/**
	 * 创建性别选择PopWindow
	 * @param context
	 * @param parentView
	 * @return
	 */
	public static PopWindow createPerInfo(Context context, View parentView,final String[] title,final OnEfunItemClickListener onEfunItemClickListener){
		final PopWindow mPopWindow = createPopContent(context,
				PopWindow.POP_WINDOW_CHOSE_SEX, E_layout.efun_pd_pop_per_info,
				parentView, E_style.Pop_Share, new OnPopListener() {
					@Override
					public void init(final PopWindow pop,final View parent, View container) {
						TextView itemOne = (TextView) container.findViewById(E_id.text_1);
						TextView itemTwo = (TextView) container.findViewById(E_id.text_2);
						itemOne.setText(title[0]);
						itemTwo.setText(title[1]);
						itemOne.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								if(onEfunItemClickListener!=null){
									onEfunItemClickListener.onItemClick(0);
									pop.dismiss();
								}
							}
						});
						itemTwo.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								if(onEfunItemClickListener!=null){
									onEfunItemClickListener.onItemClick(1);
									pop.dismiss();
								}
							}
						});
					}
				});
		return mPopWindow;
	}
	/**
	 * 创建下載选择PopWindow
	 * @param context
	 * @param parentView
	 * @return
	 */
	public static PopWindow createDownLoadAreaChose(Context context, View parentView,final String[] title,final OnEfunItemClickListener onEfunItemClickListener){
		final PopWindow mPopWindow = createPopContent(context,
				PopWindow.POP_WINDOW_CHOSE_DOWNLOAD_AREA, E_layout.efun_pd_pop_download_area_chose,
				parentView, E_style.Pop_Share, new OnPopListener() {
			@Override
			public void init(final PopWindow pop,final View parent, View container) {
				TextView itemOne = (TextView) container.findViewById(E_id.text_1);
				TextView itemTwo = (TextView) container.findViewById(E_id.text_2);
				itemOne.setText(title[0]);
				itemTwo.setText(title[1]);
				itemOne.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if(onEfunItemClickListener!=null){
							onEfunItemClickListener.onItemClick(0);
							pop.dismiss();
						}
					}
				});
				itemTwo.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if(onEfunItemClickListener!=null){
							onEfunItemClickListener.onItemClick(1);
							pop.dismiss();
						}
					}
				});
			}
		});
		return mPopWindow;
	}
//	/**
//	 * 创建啟動游戲选择PopWindow
//	 * @param context
//	 * @param parentView
//	 * @return
//	 */
//	public static PopWindow createStartAPPChose(Context context, View parentView,final String[] title,final OnEfunItemClickListener onEfunItemClickListener){
//		final PopWindow mPopWindow = createPopContent(context,
//				PopWindow.POP_WINDOW_CHOSE_DOWNLOAD_AREA, E_layout.efun_pd_pop_start_app_chose,
//				parentView, E_style.Pop_Share, new OnPopListener() {
//			@Override
//			public void init(final PopWindow pop,final View parent, View container) {
//				TextView itemOne = (TextView) container.findViewById(E_id.text_1);
//				TextView itemTwo = (TextView) container.findViewById(E_id.text_2);
//				itemOne.setText(title[0]);
//				itemTwo.setText(title[1]);
//				itemOne.setOnClickListener(new OnClickListener() {
//					@Override
//					public void onClick(View v) {
//						if(onEfunItemClickListener!=null){
//							onEfunItemClickListener.onItemClick(0);
//							pop.dismiss();
//						}
//					}
//				});
//				itemTwo.setOnClickListener(new OnClickListener() {
//					@Override
//					public void onClick(View v) {
//						if(onEfunItemClickListener!=null){
//							onEfunItemClickListener.onItemClick(1);
//							pop.dismiss();
//						}
//					}
//				});
//			}
//		});
//		return mPopWindow;
//	}
}
