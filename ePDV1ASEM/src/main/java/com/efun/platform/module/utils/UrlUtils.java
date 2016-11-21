package com.efun.platform.module.utils;

import java.util.HashMap;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.efun.core.task.EfunCommandCallBack;
import com.efun.core.task.command.abstracts.EfunCommand;
import com.efun.core.tools.EfunLogUtil;
import com.efun.core.url.EfunDynamicUrl;
import com.efun.platform.AndroidScape.E_string;
import com.efun.platform.IPlatApplication;

public class UrlUtils {
	public static final String PLATFORM_PRE_KEY = "efunPlatformPreferredUrl";
	public static final String PLATFORM_PRE_WEB_KEY = "efunPlatformWebPreferredUrl";
	public static final String GAME_PRE_KEY = "efunGamePreferredUrl";
	public static final String GAME_SPA_KEY = "efunGameSpareUrl";
	public static final String FB_PRE_KEY = "efunFbPreferredUrl";
	public static final String FB_SPA_KEY = "efunFbSpareUrl";
	public static final String ADS_PRE_KEY = "efunAdsPreferredUrl";
	public static final String ADS_SPA_KEY = "efunAdsSpareUrl";
	public static final String LOGIN_PRE_KEY = "efunLoginPreferredUrl";
	public static final String LOGIN_SPA_KEY = "efunLoginSpareUrl";
	public static final String IMG_UPLOAD_PRE_KEY = "efunImgUploadPreferredUrl";
	public static final String IMG_UPLOAD_SPA_KEY = "efunImgUploadSpareUrl";
	public static final String PAY_PRE_KEY = "efunPayPreferredUrl";
	public static final String PAY_SPA_KEY = "efunPaySpareUrl";
	public static final String ACTIVITY_PRE_KEY = "efunPFActivitySpareUrl";
	
	public static final String PUSH_URL_KEY = "efunPushServerUrl";
	public static final String PUSH_PORT_KEY = "efunPushServerPort";
	
	public static void initUrl(final Context context){
		final String[] urlKey = new String[]{
				PLATFORM_PRE_KEY,
				PLATFORM_PRE_WEB_KEY,
				GAME_PRE_KEY,
				GAME_SPA_KEY,
				FB_PRE_KEY,
				FB_SPA_KEY,
				ADS_PRE_KEY,
				ADS_SPA_KEY,
				LOGIN_PRE_KEY,
				LOGIN_SPA_KEY,
				IMG_UPLOAD_PRE_KEY,
				IMG_UPLOAD_SPA_KEY,
				PAY_PRE_KEY,
				PAY_SPA_KEY,
				ACTIVITY_PRE_KEY,
				PUSH_URL_KEY,
				PUSH_PORT_KEY};
		final String[] defaultValues = new String[]{
				context.getString(E_string.efun_pd_url_base),
				context.getString(E_string.efun_pd_url_web_base),
				context.getString(E_string.efun_pd_url_game_base),
				context.getString(E_string.efun_pd_url_game_base_spa),
				context.getString(E_string.efun_pd_url_fb_base),
				context.getString(E_string.efun_pd_url_fb_base_spa),
				context.getString(E_string.efun_pd_url_ads_base),
				context.getString(E_string.efun_pd_url_ads_base_spa),
				context.getString(E_string.efun_pd_url_login_base),
				context.getString(E_string.efun_pd_url_login_base_spa),
				context.getString(E_string.efun_pd_url_img_upload_base),
				context.getString(E_string.efun_pd_url_img_upload_base_spa),
				context.getString(E_string.efun_pd_url_pay_base),
				context.getString(E_string.efun_pd_url_pay_base_spa),
				context.getString(E_string.efun_pd_url_activity_base),
				context.getString(E_string.efun_pd_url_push_base),
				context.getString(E_string.efun_pd_url_push_port_base)};
		EfunDynamicUrl.initPlatformDynamicUrl(context,
				context.getString(E_string.efun_pd_sdk_download_efunVersionCode),
				context.getString(E_string.efun_pd_sdk_downloadtw_efunVersionCode),
				context.getString(E_string.efun_pd_sdk_download_efunDomainInventory),
				context.getString(E_string.efun_pd_sdk_downloadtw_efunDomainInventory),
				new EfunCommandCallBack() {
					@Override
					public void cmdCallBack(EfunCommand arg0) {
						String[] urls = EfunDynamicUrl.getPlatformDynamicUrls(context, urlKey, defaultValues);
						HashMap<String, String> urlMaps = new HashMap<String, String>();
						for (int i = 0; i < defaultValues.length; i++) {
							Log.d("platform","UrlKey:"+urlKey[i] +"===== UrlValue:"+urls[i]);
							urlMaps.put(urlKey[i], urls[i]);
						}
						IPlatApplication.get().setIPlatUrlMaps(urlMaps);
					}
				});
	}
	
	public static String checkUrl(Context context,String key,String url){
		if(TextUtils.isEmpty(key)){
			throw new NullPointerException("UrlUtils:checkUrl 的 key 是null");
		}
		if(TextUtils.isEmpty(url)){
			if(key.equals(PLATFORM_PRE_KEY)){
				EfunLogUtil.logE("efunPlatformPreferredUrl is null form server");
				return context.getString(E_string.efun_pd_url_base);
			}else if(key.equals(PLATFORM_PRE_WEB_KEY)){
				EfunLogUtil.logE("efunPlatformWebPreferredUrl is null form server");
				return context.getString(E_string.efun_pd_url_web_base);
			}else if(key.equals(GAME_PRE_KEY)){
				EfunLogUtil.logE("efunGamePreferredUrl is null form server");
				return context.getString(E_string.efun_pd_url_game_base);
			}else if(key.equals(GAME_SPA_KEY)){
				EfunLogUtil.logE("efunGameSpareUrl is null form server");
				return context.getString(E_string.efun_pd_url_game_base_spa);
			}else if(key.equals(FB_PRE_KEY)){
				EfunLogUtil.logE("efunFbPreferredUrl is null form server");
				return context.getString(E_string.efun_pd_url_fb_base);
			}else if(key.equals(FB_SPA_KEY)){
				EfunLogUtil.logE("efunFbSpareUrl is null form server");
				return context.getString(E_string.efun_pd_url_fb_base_spa);
			}else if(key.equals(ADS_PRE_KEY)){
				EfunLogUtil.logE("efunAdsPreferredUrl is null form server");
				return context.getString(E_string.efun_pd_url_ads_base);
			}else if(key.equals(ADS_SPA_KEY)){
				EfunLogUtil.logE("efunAdsSpareUrl is null form server");
				return context.getString(E_string.efun_pd_url_ads_base_spa);
			}else if(key.equals(LOGIN_PRE_KEY)){
				EfunLogUtil.logE("efunLoginPreferredUrl is null form server");
				return context.getString(E_string.efun_pd_url_login_base);
			}else if(key.equals(LOGIN_SPA_KEY)){
				EfunLogUtil.logE("efunLoginSpareUrl is null form server");
				return context.getString(E_string.efun_pd_url_login_base_spa);
			}else if(key.equals(IMG_UPLOAD_PRE_KEY)){
				EfunLogUtil.logE("efunImgUploadPreferredUrl is null form server");
				return context.getString(E_string.efun_pd_url_img_upload_base);
			}else if(key.equals(IMG_UPLOAD_SPA_KEY)){
				EfunLogUtil.logE("efunImgUploadSpareUrl is null form server");
				return context.getString(E_string.efun_pd_url_img_upload_base_spa);
			}else if(key.equals(PAY_PRE_KEY)){
				EfunLogUtil.logE("efunPayPreferredUrl is null form server");
				return context.getString(E_string.efun_pd_url_pay_base);
			}else if(key.equals(PAY_SPA_KEY)){
				EfunLogUtil.logE("efunPaySpareUrl is null form server");
				return context.getString(E_string.efun_pd_url_pay_base_spa);
			}else if(key.equals(ACTIVITY_PRE_KEY)){
				EfunLogUtil.logE("efunPFActivitySpareUrl is null form server");
				return context.getString(E_string.efun_pd_url_activity_base);
			}else if(key.equals(PUSH_URL_KEY)){
				EfunLogUtil.logE("efunPushServerUrl is null form server");
				return context.getString(E_string.efun_pd_url_push_base);
			}else if(key.equals(PUSH_PORT_KEY)){
				EfunLogUtil.logE("efunPushServerPort is null form server");
				return context.getString(E_string.efun_pd_url_push_port_base);
			}else{
				return "";
			}
		}
		return url;
	}

	

}
