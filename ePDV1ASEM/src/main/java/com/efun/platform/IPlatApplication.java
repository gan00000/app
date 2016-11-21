package com.efun.platform;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.appsflyer.AppsFlyerLib;
import com.efun.ads.event.AppCrash;
import com.efun.core.tools.EfunLogUtil;
import com.efun.core.tools.SignatureUtil;
import com.efun.platform.image.cache.disc.naming.Md5FileNameGenerator;
import com.efun.platform.image.core.ImageLoader;
import com.efun.platform.image.core.ImageLoaderConfiguration;
import com.efun.platform.image.core.assist.QueueProcessingType;
import com.efun.platform.module.account.bean.PhoneAreaBean;
import com.efun.platform.module.account.bean.User;
import com.efun.platform.module.base.impl.OnEfunListener;
import com.efun.platform.module.bean.ConfigInfoBean;
import com.efun.platform.module.bean.PlayerAreaBean;
import com.efun.platform.module.utils.ConfigInfoUtils;
import com.efun.platform.module.utils.UrlUtils;
import com.efun.platform.module.utils.UserUtils;
import com.efun.platform.status.IPlatStatus;
import com.efun.platform.utils.Const.HttpParam;
import com.facebook.AppEventsLogger;
import com.google.ads.conversiontracking.AdWordsConversionReporter;

public class IPlatApplication extends Application {
	private static IPlatApplication mApplication;
	private User user;
	private String pfAName;//账号
	private String pfPCode;//密码
	private String pfLType;//登录类型 
	private boolean hasHaoKangPush = false;//是否有好康的推送
	private boolean hasRechargePush = false;//是否有储值的推送
	private String startGameName;//開啟的游戲
	private String startGameCode;//開啟的游戲gameCode
	private ArrayList<ConfigInfoBean> configInfos;//平台配置信息
	private ArrayList<PhoneAreaBean> mPhoneAreas;//手机区号
	private ArrayList<PlayerAreaBean> mPlayerAreas;//玩家地区
	private String sumPoint;//平台点
	private boolean isUserHasChange = false;//用户数据是否已改变
	private String tabFlag;
//	private boolean findWebNeedFresh = true;
//	private boolean interactionWebNeedFresh = true;


	private static final String APPFLYER_APPID = "WNZWxVLpoCwQXs9K9fDMcS";

	@Override
	public void onCreate() {
		super.onCreate();
		mApplication = this;

		//FB广告
		Log.d("efun","-->FB广告");
		AppEventsLogger.activateApp(this, this.getString(AndroidScape.E_string.efunFBApplicationId));
		//AppsFlyer
		Log.d("efun","-->AppsFlyer广告");
		AppsFlyerLib.setAppsFlyerKey(APPFLYER_APPID);
		AppsFlyerLib.setAppUserId("My-Unique-ID");
		AppsFlyerLib.setCurrencyCode("USD");
		AppsFlyerLib.sendTracking(this);

		// Efun平台_激活_Android -- adwords广告
		// Google Android first open conversion tracking snippet
		// Add this code to the onCreate() method of your application activity
		Log.d("efun","-->adwords广告");
		AdWordsConversionReporter.reportWithConversionId(this.getApplicationContext(),"881127905", "tEdFCNeK6mgQ4eOTpAM", "0.00", false);

		init();
		Log.d("efun","key hash:" + SignatureUtil.getHashKey(this,this.getPackageName()));
	}
	
	private void init(){		
		UrlUtils.initUrl(this);
		ConfigInfoUtils.initPlatformConfigs(this);
		advertiser = "";//efunapp$$efunapp_twap   1130后弃用：efunapp$$efunapp_twap_tw_2_apk
		partner = "efun";
		initImageLoader();
		UserUtils.initUser(this);
		//崩溃日志
		AppCrash.init(this, HttpParam.PLATFORM_CODE);
	}
	/**
	 * 初始化图片加载器
	 */
	private void initImageLoader() {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				this).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.diskCacheSize(50 * 1024 * 1024)
				// 50 Mb
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs() // Remove for release app
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}
	
	public static IPlatApplication get(){
		return mApplication;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public String getPfAName() {
		return pfAName;
	}

	public void setPfAName(String pfAName) {
		this.pfAName = pfAName;
	}

	public String getPfPCode() {
		return pfPCode;
	}

	public void setPfPCode(String pfPCode) {
		this.pfPCode = pfPCode;
	}

	public String getPfLType() {
		return pfLType;
	}

	public void setPfLType(String pfLType) {
		this.pfLType = pfLType;
	}

	public boolean isHasHaoKangPush() {
		return hasHaoKangPush;
	}

	public void setHasHaoKangPush(boolean hasHaoKangPush) {
		this.hasHaoKangPush = hasHaoKangPush;
	}

	public boolean isHasRechargePush() {
		return hasRechargePush;
	}

	public void setHasRechargePush(boolean hasRechargePush) {
		this.hasRechargePush = hasRechargePush;
	}

	public String getStartGameName() {
		return startGameName;
	}

	public void setStartGameName(String startGameName) {
		this.startGameName = startGameName;
	}

	public String getStartGameCode() {
		return startGameCode;
	}

	public String getTabFlag() {
		return tabFlag;
	}

	public void setTabFlag(String tabFlag) {
		this.tabFlag = tabFlag;
	}

	public void setStartGameCode(String startGameCode) {
		this.startGameCode = startGameCode;
	}

	private HashMap<String, String> iPlatUrlMaps = new HashMap<String, String>();
	public String getIPlatUrlByKey(String key) {
		String url = iPlatUrlMaps.get(key)==null?"":iPlatUrlMaps.get(key);
		return UrlUtils.checkUrl(this, key, url);
	}
	public void setIPlatUrlMaps(HashMap<String, String> iPlatUrlMaps) {
		this.iPlatUrlMaps = iPlatUrlMaps;
	}

	private OnEfunListener mOnEfunListener;
	public OnEfunListener getOnEfunListener() {
		return mOnEfunListener;
	}
	public void setOnEfunListener(OnEfunListener onEfunListener) {
		this.mOnEfunListener = onEfunListener;
	}
	
	
	private String advertiser;
	private String partner;
	public String getAdvertiser() {
		return advertiser;
	}
	public void setAdvertiser(String advertiser) {
		this.advertiser = advertiser;
	}
	public String getPartner() {
		return partner;
	}
	public void setPartner(String partner) {
		this.partner = partner;
	}
	
	/**
	 * 状态
	 */
	private IPlatStatus status;
	public IPlatStatus getIPlatStatus() {
		return status;
	}
	public void setIPlatStatus(IPlatStatus status) {
		this.status = status;
	}
	
	private boolean newStatusOfGiftSelf;
	public boolean isNewStatusOfGiftSelf() {
		return newStatusOfGiftSelf;
	}

	public void setNewStatusOfGiftSelf(boolean newStatusOfGiftSelf) {
		this.newStatusOfGiftSelf = newStatusOfGiftSelf;
	}
	
	private boolean newGiftStatus;
	public boolean isNewGiftStatus() {
		return newGiftStatus;
	}

	public void setNewGiftStatus(boolean newGiftStatus) {
		this.newGiftStatus = newGiftStatus;
	}

	public ArrayList<ConfigInfoBean> getConfigInfos() {
		return configInfos;
	}

	public void setConfigInfos(ArrayList<ConfigInfoBean> configInfos) {
		this.configInfos = configInfos;
	}

	public ArrayList<PhoneAreaBean> getmPhoneAreas() {
		return mPhoneAreas;
	}

	public void setmPhoneAreas(ArrayList<PhoneAreaBean> mPhoneAreas) {
		this.mPhoneAreas = mPhoneAreas;
	}

	public ArrayList<PlayerAreaBean> getmPlayerAreas() {
		return mPlayerAreas;
	}

	public void setmPlayerAreas(ArrayList<PlayerAreaBean> mPlayerAreas) {
		this.mPlayerAreas = mPlayerAreas;
	}

	public String getSumPoint() {
		return sumPoint;
	}

	public void setSumPoint(String sumPoint) {
		this.sumPoint = sumPoint;
	}

	public boolean isUserHasChange() {
		return isUserHasChange;
	}

	public void setUserHasChange(boolean isUserHasChange) {
		this.isUserHasChange = isUserHasChange;
	}

//	public boolean isFindWebNeedFresh() {
//		return findWebNeedFresh;
//	}
//
//	public void setFindWebNeedFresh(boolean findWebNeedFresh) {
//		this.findWebNeedFresh = findWebNeedFresh;
//	}
//
//	public boolean isInteractionWebNeedFresh() {
//		return interactionWebNeedFresh;
//	}
//
//	public void setInteractionWebNeedFresh(boolean interactionWebNeedFresh) {
//		this.interactionWebNeedFresh = interactionWebNeedFresh;
//	}
//	
		
}
