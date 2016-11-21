package com.efun.platform.module.utils;

import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.efun.ads.bean.AdsHttpParams;
import com.efun.ads.call.EfunAdsPlatform;
import com.efun.google.EfunGoogleProxy;
import com.efun.platform.IPlatApplication;
import com.efun.platform.utils.Const.HttpParam;
import com.efun.platform.utils.StoreUtil;

public class TrackingUtils {
//	private static final String EVENT = "EfunGameApp";//独立APP
	
	public static final String ACTION_EXTENSION = "Free_Point_Detail";//原来是:领取点卡奖励   20150717修改//免费点数详情
	public static final String ACTION_GIFT = "Gift_Center";//礼包中心
	public static final String ACTION_GIFT_DETAIL = "Gift_Detail";//礼包详情
	
	public static final String ACTION_GIFT_SELF_CENTER = "Person_Gift_Serial";//我的序列号中心
	public static final String ACTION_PHONE_AREA = "Phone_area";//手机号码地区
	
	public static final String NAME_BANNER_SUMMARY="Banner";//首页Banner
	
	public static final String NAME_GIFT_SELF_CENTER="Person_Gift_Serial";//我的序列号中心
	public static final String NAME_GIFT_SELF_CENTER_COPY = "Button_Copy";//复制
	
	//----------
	//20160623-firebase，整理：
	public static final String ACTION_TAB="Tab_Button";//底部按钮-Tab
	
	public static final String NAME_TAB_SUMMARY="Information";//资讯
	public static final String NAME_TAB_GAME="Game";//游戏
	public static final String NAME_TAB_WELFARE="Welfare";//好康
	public static final String NAME_TAB_SELF="Person";//我
	public static final String NAME_TAB_CS="Cs";//客服
	
//	public static final String ACTION_BANNER="Banner";//Banner//去掉，改为Information
	public static final String ACTION_SUMMARY="Information";
	
		public static final String NAME_SUMMARY_BANNER="Banner";//Banner
		public static final String NAME_SUMMARY_RECOMMEND_GAME="Recommend_Game";//推荐游戏
		public static final String NAME_SUMMARY_VIDEO="Video";//视频
	
	public static final String ACTION_GAME="Game";//游戏-Game
	
	public static final String NAME_GAME_DOWNLOAD="DownLoad";//下载
	public static final String NAME_GAME_UPDATE="Update";//更新
	public static final String NAME_GAME_START="Start";//启动
		public static final String NAME_GAME_ACHIEVE_SYS="Achieve_Sys";//成就系统栏
		public static final String NAME_GAME_DETAIL="Game_Detail";//打开游戏详情页面
		public static final String NAME_GAME_DETAIL_EVALUATE="Game_Detail_Evaluate";//游戏详情页面中的评价按钮
	public static final String NAME_GAME_DOWNLOAD_TW_PLAYER="TW_Player_DownLoad";//台湾玩家下载
	public static final String NAME_GAME_DOWNLOAD_HK_PLAYER="HK_Player_DownLoad";//香港玩家下载
	public static final String NAME_GAME_UPDATE_TW_PLAYER="TW_Player_Update";//台湾玩家更新
	public static final String NAME_GAME_UPDATE_HK_PLAYER="HK_Player_Update";//香港玩家更新
	public static final String NAME_GAME_START_TW_PLAYER="TW_Player_Start";//台湾玩家启动
	public static final String NAME_GAME_START_HK_PLAYER="HK_Player_Start";//香港玩家启动
	
	public static final String ACTION_WELFARE = "Welfare";//好康-Welfare

	public static final String NAME_WELFARE_EXTENSION_GET_REWARD="Receive_Award";//领取奖励
	public static final String NAME_WELFARE_EXTENSION_GAME_DOWNLOAD="Game_Download";//游戏下载
	
	public static final String NAME_WELFARE_EXTENSION="Receive_Free_Point";//领取免费点数
	public static final String NAME_WELFARE_GIFT="Recommend_Gift";//推荐礼包
	public static final String NAME_WELFARE_BIND_PHONE="Bind_Phone";//绑定手机
	
	public static final String ACTION_CS = "Cs";//客服
	
	public static final String NAME_CS_ASK ="Ask";//我要提问
	public static final String NAME_CS_QUESTION ="QA";//常见问题
	public static final String NAME_CS_REPLY ="Reply";//客服回复
	
	public static final String ACTION_MYSELF = "Person";//我
	
	public static final String NAME_MYSELF_UPDATEINFO = "Update_Person_Info";//更新个人资料
	public static final String NAME_MYSELF_SETTING = "Setting";//设置
	public static final String NAME_MYSELF_MEMBERINSTRO = "Member_desc";//会员说明
	public static final String NAME_MYSELF_BIND_EMAIL = "Bind_Email";//绑定邮箱
	public static final String NAME_MYSELF_BIND_PHONE = "Bind_Phone";//绑定手机
	public static final String NAME_MYSELF_RECHARGE = "Stored";//储值
	public static final String NAME_MYSELF_KNOCK_EGG = "Egg_Drop";//砸蛋
	public static final String NAME_MYSELF_GIFT = "Gift_Center";//礼包中心
	public static final String NAME_MYSELF_SERIAL = "Gift_Serial";//我的序列号中心
	public static final String NAME_MYSELF_VIP = "Efun_VIP";//E会员俱乐部
	public static final String NAME_MYSELF_HEAD = "Person_Icon";//头像
	public static final String NAME_MYSELF_SYS_INFO = "System_Info_Center";//信件中心
		public static final String NAME_MYSELF_FUNCTION = "Function_";//拓展功能模块
	
	public static final String ACTION_ACCOUNT = "Account";//账户-Account
	
	public static final String NAME_ACCOUNT_BIND_PHONE ="Bind_Phone";//绑定手机
	public static final String NAME_ACCOUNT_LOGIN_EFUN ="Login_Efun";//Efun登录
	public static final String NAME_ACCOUNT_LOGIN_GOOGLE ="Login_Google";//google+登录
	public static final String NAME_ACCOUNT_LOGIN_BAHA ="Login_Baha";//巴哈登录
	public static final String NAME_ACCOUNT_LOGIN_FACEBOOK ="Login_Facebook";//FB登录
	
	public static final String ACTION_APP = "APP";//APP
	
	public static final String NAME_APP_INSTALLED="Install";//安裝
	public static final String NAME_APP_START="Start";//启动
	public static final String NAME_APP_START_UNIQUE="Start_Single";//启动_排重
	public static final String NAME_APP_SCAN="Scanning";//扫描
	
	public static final String ACTION_SETTING = "Setting";//设置
	
	public static final String NAME_SETTING_PUSH_OPEN = "Push_Setting_Open";//推送设置_开
	public static final String NAME_SETTING_PUSH_CLOSE = "Push_Setting_Close";//推送设置_关
	public static final String NAME_SETTING_HELP = "Help_And_FeedBack";//帮助与反馈
	public static final String NAME_SETTING_SCORE_APP = "Rating_Efun_Platform";//为Efun游戏平台评分
	public static final String NAME_SETTING_CHECK_UPDATE = "Check_Update";//检测更新
	public static final String NAME_SETTING_SHARE = "Share_To_Friend";//分享给好友
	
	public static final String ACTION_UPDATE_PERSON_INFO = "Update_PersonInfo";//更新个人资料
	
	public static final String NAME_UPDATE_PERSON_INFO_CHANGE_PWD = "Update_PassWord";//更新密码
	public static final String NAME_UPDATE_PERSON_INFO_REBIND_EMAIL = "Update_Email";//更新信箱地址
	public static final String NAME_UPDATE_PERSON_INFO_REBIND_PHONE = "Update_Phone";//更新手机号码
	public static final String NAME_UPDATE_PERSON_INFO_CHANGE_ACCOUNT = "ChangeAccount";//切换账号
		public static final String NAME_UPDATE_PERSON_INFO_EDIT = "Person_Edit";//编辑
	
	
	
	
	
	
	
	
//	public static void init(Context context){
//		EfunGoogleAnalytics.getDefaultTracker(context, context.getString(E_string.efun_pd_tracking_id));
//	}
	
	//初始化广播接口(推送设置)
//	public static void initService(Context context){
////		EfunFireBase.getInstance().setNotificationIcon(context, EfunResourceUtil.findDrawableIdByName(context, "efun_pd_app_name"), "efuntwapp");
//		EfunGoogleProxy.setMessageIcon(context, EfunResourceUtil.findDrawableIdByName(context, "efun_pd_app_name"));
//		EfunGoogleProxy.setMessageDispather(context, MyFireBaseMessageDispatcher.class);
//		EfunGoogleProxy.initPush(context);
//	}
	
//	//注销广播接口
//	public static void destoryService(Context context){
//		EfunFireBase.getInstance().unNotificationBySubscribeToTopic("efuntwapp");
//	}
	
	
	
	/**
	 * 谷歌分析追踪
	 * @param action
	 * @param name
	 */
	public static void track(Context context,String action,String name){
//		EfunGoogleAnalytics.trackEvent(EVENT, action, name);
//		EfunFireBase.getInstance().setLogEvent(context, action+"_"+name, null);
		EfunGoogleProxy.EfunFirebaseAnalytics.logEvent(context, action+"_"+name);
	}
	
	public static void track(Context context,String action,String name,Bundle bundle){
//		EfunGoogleAnalytics.trackEvent(EVENT, action, name);
//		EfunFireBase.getInstance().setLogEvent(context, action+"_"+name, bundle);
		EfunGoogleProxy.EfunFirebaseAnalytics.logEvent(context, action+"_"+name, bundle);
	}
	
	
//	/**
//	 * 一段时间追踪一次
//	 * @param context
//	 * @param keys
//	 * @param etagValues
//	 * @param action
//	 * @param name
//	 * @param betweenTime
//	 * @param clazz
//	 */
//	public static void trackSingle(Context context,String[] keys,String[] etagValues,String action,String name,long betweenTime,Class<? extends Object> clazz){
//		String oldTime = Store.getSimpleInfoByClazz(context, keys[0], clazz);
//		if(!TextUtils.isEmpty(oldTime)){			
//			if(System.currentTimeMillis() - Long.parseLong(oldTime) > betweenTime){
//				Store.saveSimpleInfoByClazz(context, keys, etagValues, clazz);
//				TrackingUtils.track(action, name);
//			}
//		}else{
//			Store.saveSimpleInfoByClazz(context, keys, etagValues, clazz);
//			TrackingUtils.track(action, name);
//		}	
//	}
	/**
	 * 一段时间追踪一下启动数（排重作用）
	 * @param context
	 * @param action
	 * @param name
	 * @param betweenTime
	 * @param clazz
	 */
	public static void trackSingle(Context context,String action,String name,long betweenTime,Class<? extends Object> clazz){	
		String etagValues = System.currentTimeMillis()+"";
		String oldTime = StoreUtil.getValue(context, StoreUtil.Track_file_name, StoreUtil.Track_key_start_app);
		if(!TextUtils.isEmpty(oldTime)){			
			if(System.currentTimeMillis() - Long.parseLong(oldTime) > betweenTime){
				StoreUtil.saveValue(context, StoreUtil.Track_file_name, StoreUtil.Track_key_start_app, etagValues);
				TrackingUtils.track(context,action, name);
			}
		}else{
			StoreUtil.saveValue(context, StoreUtil.Track_file_name, StoreUtil.Track_key_start_app, etagValues);
			TrackingUtils.track(context,action, name);
		}
	}
	
//	public static void destory(){
//		EfunGoogleAnalytics.stopSession();
//	}
	
	public static void initAds(Activity activity){
		AdsHttpParams params = new AdsHttpParams();
		params.setPreferredUrl(IPlatApplication.get().getIPlatUrlByKey(UrlUtils.ADS_PRE_KEY));
		params.setSpareUrl(IPlatApplication.get().getIPlatUrlByKey(UrlUtils.ADS_SPA_KEY));
		params.setAdvertiser(IPlatApplication.get().getAdvertiser());
		params.setPartner(IPlatApplication.get().getPartner());
		params.setGameCode(HttpParam.PLATFORM_CODE);//gameCode
		params.setAppKey(HttpParam.PLATFORM_APP_KEY);//秘钥
		params.setAppPlatform(HttpParam.PLATFORM_APP_PLATFORM);//平台标识
		EfunAdsPlatform.initEfunAdsS2S(activity, params, true);
	}
	
//	class MyMessageDispatcher implements MessageDispatcher{
//
//		@Override
//		public boolean onMessage(Context context, String message,
//				Map<String, String> data) {
//			// TODO Auto-generated method stub
//			Log.e("yang", "===========onMessage============");
//			return false;
//		}
//
//		@Override
//		public void onNotShowMessage(Context context, String message,
//				Map<String, String> data) {
//			// TODO Auto-generated method stub
//			Log.e("yang", "===========onNotShowMessage============");
//		}
//
//		@Override
//		public boolean onClickNotification(Context context, String message,
//				Map<String, String> data) {
//			// TODO Auto-generated method stub
//			Log.e("yang", "===========onClickNotification============");
//			Log.e("yang","message:"+message);
//			Log.e("yang","data:"+data.toString());
//			return false;
//		}
//		
//	}
	
}
