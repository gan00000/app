package com.efun.platform.module.utils;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.efun.ads.bean.AdsHttpParams;
import com.efun.ads.call.EfunAdsPlatform;
import com.efun.google.EfunGoogleProxy;
import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.IPlatApplication;
import com.efun.platform.utils.Const.HttpParam;
import com.efun.platform.utils.StoreUtil;

public class TrackingGoogleUtils {
	private static final String EVENT = "独立APP";//EfunGameApp
	
	public static final String ACTION_TAB="底部按钮-Tab";//Tab_Button
	public static final String ACTION_BANNER="Banner";//Banner
	public static final String ACTION_GAME="游戏-Game";//Game
	public static final String ACTION_WELFARE = "好康-Welfare";//Welfare
	public static final String ACTION_ACCOUNT = "账户-Account";//Account
//	public static final String ACTION_WELFARE_ACTIVITY = "好康-活动-Activity";
	public static final String ACTION_APP = "APP";//APP
	public static final String ACTION_EXTENSION = "免费点数详情";//原来是:领取点卡奖励   20150717修改//Free_Point_Detail
	public static final String ACTION_GIFT = "礼包中心";//Gift_Center
	public static final String ACTION_GIFT_DETAIL = "礼包详情";//Gift_Detail
	public static final String ACTION_CS = "客服";//Cs
	//20150717增加
	public static final String ACTION_MYSELF = "我";//Person
	public static final String ACTION_GIFT_SELF_CENTER = "我的序列号中心";//Person_Gift_Serial
	public static final String ACTION_SETTING = "设置";//Setting
	public static final String ACTION_UPDATE_PERSON_INFO = "更新个人资料";//Update_Person_Info
	public static final String ACTION_PHONE_AREA = "手机号码地区";//Phone_area
	//------------------
	public static final String NAME_TAB_SUMMARY="资讯";//Information
	public static final String NAME_TAB_GAME="游戏";//Game
	public static final String NAME_TAB_WELFARE="好康";//Welfare
	public static final String NAME_TAB_SELF="我";//Person
	public static final String NAME_TAB_CS="客服";//Cs
	
	public static final String NAME_BANNER_SUMMARY="首页Banner";//Banner
	
	public static final String NAME_GAME_DOWNLOAD="下载";//DownLoad
	public static final String NAME_GAME_UPDATE="更新";//Update
	public static final String NAME_GAME_START="启动";//Start
	//20150717增加
	public static final String NAME_GAME_DOWNLOAD_TW_PLAYER="台湾玩家下载";//TW_Player_DownLoad
	public static final String NAME_GAME_DOWNLOAD_HK_PLAYER="香港玩家下载";//HK_Player_DownLoad
	public static final String NAME_GAME_UPDATE_TW_PLAYER="台湾玩家更新";//TW_Player_Update
	public static final String NAME_GAME_UPDATE_HK_PLAYER="香港玩家更新";//HK_Player_Update
	public static final String NAME_GAME_START_TW_PLAYER="台湾玩家启动";//TW_Player_Start
	public static final String NAME_GAME_START_HK_PLAYER="香港玩家启动";//HK_Player_Start
	//---------------
	
	public static final String NAME_APP_INSTALLED="安裝";//Install
	public static final String NAME_APP_START="启动";//Start
	public static final String NAME_APP_START_UNIQUE="启动_排重";//Start_Single
	public static final String NAME_APP_SCAN="扫描";//Scanning
//	public static final String NAME_APP_SETTING="设置";
	
	public static final String NAME_GIFT_SELF_CENTER="我的序列号中心";//Person_Gift_Serial
	
	
	
	public static final String NAME_WELFARE_EXTENSION_GET_REWARD="领取奖励";//Receive_Award
	public static final String NAME_WELFARE_EXTENSION_GAME_DOWNLOAD="游戏下载";//Game_Download
	
	public static final String NAME_WELFARE_EXTENSION="领取免费点数";//Receive_Free_Point
	//public static final String NAME_WELFARE_ACTIVITY="活动";//20150717修改
	//public static final String NAME_WELFARE_GIFT="礼包中心";//20150717修改
	//public static final String NAME_WELFARE_KNOCK_EGG="砸蛋";//20150717修改
	//20150717增加
	public static final String NAME_WELFARE_GIFT="推荐礼包";//Recommend_Gift
	public static final String NAME_WELFARE_BIND_PHONE="绑定手机";//Bind_Phone
	//---------
	
	public static final String NAME_ACCOUNT_BIND_PHONE ="绑定手机";//Bind_Phone
	//20150717增加
	public static final String NAME_ACCOUNT_LOGIN_EFUN ="Efun登录";//Login_Efun
	public static final String NAME_ACCOUNT_LOGIN_GOOGLE ="google+登录";//Login_Google
	public static final String NAME_ACCOUNT_LOGIN_BAHA ="巴哈登录";//Login_Baha
	public static final String NAME_ACCOUNT_LOGIN_FACEBOOK ="FB登录";//Login_Facebook
		public static final String NAME_ACCOUNT_UPDATE_PHONE_DIAL ="拨打号码更新手机号码";//Update_Phone_Dial
		public static final String NAME_ACCOUNT_UPDATE_PHONE_CODE ="验证码更新手机号码";//Update_Phone_Code
		public static final String NAME_ACCOUNT_BIND_PHONE_DIAL ="拨打号码绑定手机";//Bind_Phone_Dial
		public static final String NAME_ACCOUNT_BIND_PHONE_CODE ="验证码绑定手机";//Bind_Phone_Code
	//-----------
	
	public static final String NAME_CS_ASK ="我要提问";//Ask
	public static final String NAME_CS_QUESTION ="常见问题";//QA
	public static final String NAME_CS_REPLY ="客服回复";//Reply
	
	//20150717增加
	public static final String NAME_MYSELF_UPDATEINFO = "更新个人资料";//Update_Person_Info
	public static final String NAME_MYSELF_SETTING = "设置";//Setting
	public static final String NAME_MYSELF_MEMBERINSTRO = "会员说明";//Member_desc
	public static final String NAME_MYSELF_BIND_EMAIL = "绑定邮箱";//Bind_Email
	public static final String NAME_MYSELF_BIND_PHONE = "绑定手机";//Bind_Phone
	public static final String NAME_MYSELF_RECHARGE = "储值";//Stored
	public static final String NAME_MYSELF_KNOCK_EGG = "砸蛋";//Egg_Drop
	public static final String NAME_MYSELF_GIFT = "礼包中心";//Gift_Center
	public static final String NAME_MYSELF_SERIAL = "我的序列号中心";//Gift_Serial
	public static final String NAME_MYSELF_VIP = "E会员俱乐部";//Efun_VIP
		public static final String NAME_MYSELF_SING = "签到";//Sign_Button
		public static final String NAME_MYSELF_SING_GIFT = "签到礼包-";//SignInGift
	//-----------
	//20150717增加
	public static final String NAME_GIFT_SELF_CENTER_COPY = "复制";//Button_Copy
	//---------
	//-----------
	//20150717增加
	public static final String NAME_SETTING_PUSH_OPEN = "推送设置_开";//Push_Setting_Open
	public static final String NAME_SETTING_PUSH_CLOSE = "推送设置_关";//Push_Setting_Close
	public static final String NAME_SETTING_HELP = "帮助与反馈";//Help_And_FeedBack
	public static final String NAME_SETTING_SCORE_APP = "为Efun游戏平台评分";//Rating_Efun_Platform
	public static final String NAME_SETTING_CHECK_UPDATE = "检测更新";//Check_Update
	public static final String NAME_SETTING_SHARE = "分享给好友";//Share_To_Friend
	//---------
	//20150717增加
	public static final String NAME_UPDATE_PERSON_INFO_HEAD = "头像";//Person_Icon
	public static final String NAME_UPDATE_PERSON_INFO_NICK = "妮称";//Person_NickName
	public static final String NAME_UPDATE_PERSON_INFO_CHANGE_PWD = "更新密码";//Update_PassWord
	public static final String NAME_UPDATE_PERSON_INFO_SEX = "性别";//Person_Sex
	public static final String NAME_UPDATE_PERSON_INFO_REBIND_EMAIL_OR_PHONE = "更新信箱地址或手机号码";//Update_Email_Or_Phone
	public static final String NAME_UPDATE_PERSON_INFO_CHANGE_ACCOUNT = "切换账号";//Change_Account
	//----------
	//20160623-firebase
	
	
	
	
	
	
	
	public static void init(Context context){
//		EfunGoogleAnalytics.getDefaultTracker(context, context.getString(E_string.efun_pd_tracking_id));
		EfunGoogleProxy.EfunGoogleAnalytics.initDefaultTracker(context, context.getString(E_string.efun_pd_tracking_id));
	}
	/**
	 * 谷歌分析追踪
	 * @param action
	 * @param name
	 */
	public static void track(String action,String name){
//		EfunGoogleAnalytics.trackEvent(EVENT, action, name);
		EfunGoogleProxy.EfunGoogleAnalytics.trackEvent(EVENT, action, name);
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
//				TrackingGoogleUtils.track(action, name);
//			}
//		}else{
//			Store.saveSimpleInfoByClazz(context, keys, etagValues, clazz);
//			TrackingGoogleUtils.track(action, name);
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
		String oldTime = StoreUtil.getValue(context, StoreUtil.Track_file_name, StoreUtil.Track_key_start_app_google);
		if(!TextUtils.isEmpty(oldTime)){			
			if(System.currentTimeMillis() - Long.parseLong(oldTime) > betweenTime){
				StoreUtil.saveValue(context, StoreUtil.Track_file_name, StoreUtil.Track_key_start_app_google, etagValues);
				TrackingGoogleUtils.track(action, name);
			}
		}else{
			StoreUtil.saveValue(context, StoreUtil.Track_file_name, StoreUtil.Track_key_start_app_google, etagValues);
			TrackingGoogleUtils.track(action, name);
		}
	}
	
	public static void destory(){
		EfunGoogleAnalytics.stopSession();
	}
	
//	
//	public static void initAds(Activity activity){
//		AdsHttpParams params = new AdsHttpParams();
//		params.setPreferredUrl(IPlatApplication.get().getIPlatUrlByKey(UrlUtils.ADS_PRE_KEY));
//		params.setSpareUrl(IPlatApplication.get().getIPlatUrlByKey(UrlUtils.ADS_SPA_KEY));
//		params.setAdvertiser(IPlatApplication.get().getAdvertiser());
//		params.setPartner(IPlatApplication.get().getPartner());
//		params.setGameCode(HttpParam.PLATFORM_CODE);//gameCode
//		params.setAppKey(HttpParam.PLATFORM_APP_KEY);//秘钥
//		params.setAppPlatform(HttpParam.PLATFORM_APP_PLATFORM);//平台标识
//		EfunAdsPlatform.initEfunAdsS2S(activity, params, true);
//	}
	
}
