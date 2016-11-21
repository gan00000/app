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
	private static final String EVENT = "剑侠情缘助手";//EfunGameApp
	
	public static final String ACTION_TAB="底部按钮-Tab";//Tab_Button
	public static final String ACTION_BANNER="Banner";//Banner
	public static final String ACTION_ACCOUNT = "账户-Account";//Account
//	public static final String ACTION_WELFARE_ACTIVITY = "好康-活动-Activity";
	public static final String ACTION_APP = "APP";//APP
	//20150717增加
	public static final String ACTION_MYSELF = "我";//Person
	public static final String ACTION_SETTING = "设置";//Setting
	//------------------
	public static final String NAME_TAB_SUMMARY="首页";//Information
	public static final String NAME_TAB_GAME="资讯";//Game
	public static final String NAME_TAB_WELFARE="福利";//Welfare
	public static final String NAME_TAB_SELF="我";//Person
	public static final String NAME_TAB_CS="交流";//Cs
	
	public static final String NAME_BANNER_SUMMARY="首页Banner";//Banner
	
	public static final String NAME_APP_INSTALLED="安裝";//Install
	public static final String NAME_APP_START="启动";//Start
	public static final String NAME_APP_START_UNIQUE="启动_排重";//Start_Single
	public static final String NAME_APP_SCAN="扫描";//Scanning
	//20150717增加
	public static final String NAME_ACCOUNT_LOGIN_EFUN ="Efun登录";//Login_Efun
	public static final String NAME_ACCOUNT_LOGIN_GOOGLE ="google+登录";//Login_Google
	public static final String NAME_ACCOUNT_LOGIN_BAHA ="巴哈登录";//Login_Baha
	public static final String NAME_ACCOUNT_LOGIN_FACEBOOK ="FB登录";//Login_Facebook
	public static final String NAME_ACCOUNT_LOGIN_TWITTER ="Twitter登录";//Login_Facebook
	
	//20150717增加
	public static final String NAME_MYSELF_SETTING = "设置";//Setting
	//-----------
	//20150717增加
	public static final String NAME_SETTING_PUSH_OPEN = "推送设置_开";//Push_Setting_Open
	public static final String NAME_SETTING_PUSH_CLOSE = "推送设置_关";//Push_Setting_Close
	public static final String NAME_SETTING_HELP = "帮助与反馈";//Help_And_FeedBack
	public static final String NAME_SETTING_SCORE_APP = "为剑侠情缘助手评分";//Rating_Efun_Platform
	public static final String NAME_SETTING_CHECK_UPDATE = "检测更新";//Check_Update
	public static final String NAME_SETTING_SHARE = "分享给好友";//Share_To_Friend
	//---------
	//20150717增加
	public static final String NAME_UPDATE_PERSON_INFO_HEAD = "头像";//Person_Icon
	public static final String NAME_UPDATE_PERSON_INFO_NICK = "妮称";//Person_NickName
	public static final String NAME_UPDATE_PERSON_INFO_CHANGE_PWD = "更新密码";//Update_PassWord
	public static final String NAME_UPDATE_PERSON_INFO_CHANGE_ACCOUNT = "退出账号";//Change_Account
	
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
	
//	public static void destory(){
//		EfunGoogleAnalytics.stopSession();
//	}
	
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
