package com.efun.platform.http.request.bean;

import android.content.Context;

import com.efun.core.res.EfunResConfiguration;
import com.efun.core.tools.EfunLocalUtil;
import com.efun.core.tools.EfunStringUtil;
import com.efun.platform.IPlatApplication;
import com.efun.platform.module.utils.AppUtils;
import com.efun.platform.utils.Const;
/**
 * 第三方登陆
 * @author harvey
 *
 */
public class AccountThirdLoginRequest extends BaseRequestBean{
//	private String thirdId;//第三方登陆id 没有可为null
//	private String loginType;//登陆类型 只可填以下参数 efun, fb, evart, gamer, google,twitter
	private String apps;
	private String platform;//填tw
	private String device;//填phone
	private String timestamp;//时间戳
	private String mac;//设备mac地址
	private String imei;//Andriod设备填写imei，ios设备填写 idfa
	private String androidid;//Andriod设备填写androidid ,ios设备填写cfuuid
	private String advertiser;//Andriod填写广告商,ios设备不填写(或者填写 “”)
	private String systemVersion;//系統版本號
	private String deviceType;//設備類型
	private String partner;//合作商
	private String language;//语言标识
	private String region;//region 地区标示 用于区分用户是哪一个地区的
	private String version;//本应用的版本号 0116加上

	private String loginid;
	private String thirdPlate;
	private String gameCode;
	private String crossdomain = "false";


	public AccountThirdLoginRequest(Context context) {
		super();
		this.timestamp = System.currentTimeMillis()+"";
		this.mac = EfunLocalUtil.getLocalMacAddress(context);
		this.imei = EfunLocalUtil.getLocalImeiAddress(context);
		this.androidid = EfunLocalUtil.getLocalAndroidId(context);
		this.advertiser = IPlatApplication.get().getAdvertiser();
		this.systemVersion = EfunLocalUtil.getOsVersion();
//		this.deviceType = EfunLocalUtil.getDeviceType();


		this.language = Const.HttpParam.LANGUAGE;
		this.region = Const.HttpParam.REGION;
		this.partner = IPlatApplication.get().getPartner();
		this.version = AppUtils.getAppVersionCode(context)+"";

		this.deviceType = "android";
		this.gameCode = EfunResConfiguration.getGameCode(context);

	}

	public String getApps() {
		return apps;
	}

	public void setApps(String apps) {
		this.apps = apps;
	}

	public String getThirdId() {
		return loginid;
	}
	public void setThirdId(String thirdId) {
		this.loginid = thirdId;
	}
	public String getLoginType() {
		return thirdPlate;
	}
	public void setLoginType(String loginType) {
		this.thirdPlate = loginType;
	}
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getLanguage() {
		return language;
	}
}
