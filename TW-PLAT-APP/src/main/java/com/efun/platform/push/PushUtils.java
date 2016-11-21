package com.efun.platform.push;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;

import com.efun.core.tools.EfunLogUtil;
import com.efun.platform.IPlatApplication;
import com.efun.platform.module.utils.UrlUtils;
import com.efun.platform.utils.Const.HttpParam;
import com.efun.pushnotification.task.EfunPushManager;

public class PushUtils {

	
	public static void initPushWhenStart(Context context){
//		String preUrl = IPlatApplication.get().getIPlatUrlByKey(UrlUtils.GAME_PRE_KEY);
//		String pushConfigUrl = IPlatApplication.get().getIPlatUrlByKey(UrlUtils.PUSH_URL_KEY);
//		String pushConfigPort= IPlatApplication.get().getIPlatUrlByKey(UrlUtils.PUSH_PORT_KEY);
//		EfunLogUtil.logD("preUrl:"+preUrl+"-pushConfigPort:"+pushConfigPort);
//		EfunPushManager.getInstance().setGameCode(context, HttpParam.PLATFORM_CODE)
////		.setPreUrl(context, preUrl)
//		.setPreUrl(context, "http://pushdispatch.efuntw.com/")
////		.setPushConfig(context,pushConfigUrl, pushConfigPort)
//		.setAppPlatform(context, HttpParam.PLATFORM_APP_PLATFORM)
//		.setMessageDispather(context, PushReceiver.class)
//		.startPush(context);
			String preUrl = IPlatApplication.get().getIPlatUrlByKey(UrlUtils.GAME_PRE_KEY);
			String pushConfigUrl = IPlatApplication.get().getIPlatUrlByKey(UrlUtils.PUSH_URL_KEY);
			String pushConfigPort= IPlatApplication.get().getIPlatUrlByKey(UrlUtils.PUSH_PORT_KEY);
			EfunLogUtil.logD("preUrl:"+preUrl+"-pushConfigPort:"+pushConfigPort);
			EfunPushManager.getInstance().setGameCode(context, HttpParam.PLATFORM_CODE)
			.setPreUrl(context, preUrl)
			.setPushConfig(context,pushConfigUrl, pushConfigPort)
			.setAppPlatform(context, HttpParam.PLATFORM_APP_PLATFORM)
			.setMessageDispather(context,PushReceiver.class)
			.startPush(context);
	}
	
	public static class PushType{
		/**
		 * 资讯内容
		 */
		public static final int PUSH_TYPE_No1 = 1;
		/**
		 * Game 列表
		 */
		public static final int PUSH_TYPE_No2 = 2;
		/**
		 * Game 内容页面
		 */
		public static final int PUSH_TYPE_No3 = 3;
		/**
		 * 活动内容页
		 */
		public static final int PUSH_TYPE_No4 = 4;
		/**
		 * 礼包列表
		 */
		public static final int PUSH_TYPE_No5 = 5;
		/**
		 * 礼包内容页
		 */
		public static final int PUSH_TYPE_No6 = 6;
		/**
		 * 好康
		 */
		public static final int PUSH_TYPE_No7 = 7;
		/**
		 * 储值
		 */
		public static final int PUSH_TYPE_No8 = 8;
		
		/**
		 * 资讯内容
		 */
		public static final String PUSH_TYPE_SUMMARY = "PUSH_1001";
		/**
		 * Game 列表
		 */
		public static final String PUSH_TYPE_GAMELIST = "PUSH_1002";
		/**
		 * Game 内容页面 - 对应值填gameCode
		 */
		public static final String PUSH_TYPE_GAMEDETAIL = "PUSH_1003";
//		/**
//		 * 活动内容页
//		 */
//		public static final String PUSH_TYPE_ACTIVITY_DETAIL = "PUSH_1004";
		/**
		 * 礼包列表
		 */
		public static final String PUSH_TYPE_GIFTLIST = "PUSH_1005";
		/**
		 * 礼包内容页-对应值填 礼包ID
		 */
		public static final String PUSH_TYPE_GIFT_DETAIL = "PUSH_1006";
		/**
		 * 好康
		 */
		public static final String PUSH_TYPE_WELFARE = "PUSH_1007";
		/**
		 * 储值
		 */
		public static final String PUSH_TYPE_RECHARGE = "PUSH_1008";
//		/**
//		 * 游戏gameCode - 与打开游戏内容页面相配套使用
//		 */
//		public static final String PUSH_TYPE_GAMECODE = "PUSH_GAMECODE";
//		/**
//		 * 礼包ID - 与礼包内容页面配套使用
//		 */
//		public static final String PUSH_TYPE_GOODS_ID = "PUSH_GOODS_ID";
	}
	


	/**
	 * 解析推送JSON
	 * @param message
	 * @return
	 */
//	{
//    "app": {
//        "pushParams": {
//            "pushGameCode": "zj2"
//			  "pushGoodsId":"dfdsfsdfdsfsdfds"
//        },
//        "pushType": "0",
//        "pushUrl": "http://xxx"
//    },
//    "content": "",
//    "gameCode": "tlbb",
//    "packageName": "",
//    "title": "test",
//	  "messageId": 3333
//}
	public static PushInfoBean json2Object(String message){
		if(!TextUtils.isEmpty(message)){
			try {
				JSONObject parentObject = new JSONObject(message);
				PushInfoBean info = new PushInfoBean();
//				
//				if(parentObject.has("extendJson")){
//				JSONObject jsonObject = parentObject.optJSONObject("extendJson");
//				if(jsonObject.has("pushGameCode")){
//					info.setPushGameCode(jsonObject.optString("pushGameCode"));
//				}
//				if(jsonObject.has("language")){
//					info.setLanguage(jsonObject.optString("language"));
//				}
//				if(jsonObject.has("cmd")){
//					info.setCmd(jsonObject.optString("cmd"));
//				}
//				if(jsonObject.has("title")){
//					info.setTitle(jsonObject.optString("title"));
//				}
//				if(jsonObject.has("gamecode")){
//					info.setGamecode(jsonObject.optString("gamecode"));
//				}
//				if(jsonObject.has("url")){
//					info.setUrl(jsonObject.optString("url"));
//				}
//				if(jsonObject.has("content")){
//					info.setContent(jsonObject.optString("content"));
//				}
//				if(jsonObject.has("istest")){
//					info.setIstest(jsonObject.optBoolean("istest"));
//				}
//				if(jsonObject.has("pushGoodsId")){
//					info.setPushGoodsId(jsonObject.optString("pushGoodsId"));
//				}
//				if(jsonObject.has("pushType")){
//					info.setPushType(jsonObject.optInt("pushType"));
//				}
//				if(parentObject.has("code")){
//					info.setCode(parentObject.optString("code"));
//				}
//				if(parentObject.has("message")){
//					info.setMessage(parentObject.optString("message"));
//				}
//			}
//			
//			return info;
				
				if(parentObject.has("app")){
					JSONObject jsonObject = parentObject.optJSONObject("app");
					if(jsonObject.has("pushType")){
						info.setPushType(Integer.parseInt(jsonObject.optString("pushType","0")));
					}
					if(jsonObject.has("pushUrl")){
						info.setPushUrl(jsonObject.optString("pushUrl"));
					}
					if(jsonObject.has("pushParams")){
						JSONObject paramObject = jsonObject.optJSONObject("pushParams");
						PushParamsBean params = new PushParamsBean();
						if(paramObject.has("pushGameCode")){
							params.setPushGameCode(paramObject.optString("pushGameCode"));
						}
						if(paramObject.has("pushGoodsId")){
							params.setPushGoodsId(paramObject.optString("pushGoodsId"));
						}
						info.setPushParams(params);
					}
				}
				if(parentObject.has("title")){
					info.setTitle(parentObject.optString("title"));
				}
				if(parentObject.has("content")){
					info.setContent(parentObject.optString("content"));
				}
				if(parentObject.has("messageId")){
					info.setMessageId(parentObject.optLong("messageId"));
				}
				
				return info;
			} catch (JSONException e) {
				return null;
			}
		}
		return null;
	}
}
