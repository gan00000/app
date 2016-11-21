package com.efun.platform.http.request.bean;

import com.efun.core.tools.EfunLocalUtil;
import com.efun.platform.utils.Const.HttpParam;

import android.content.Context;

public class ActivityExtensionDownloadRequest extends BaseRequestBean{
	private String gameCode ;
	private String activityCode;
	private String userid;
	private String plateForm;
	private String mac;
	private String imei;
	private String androidid;
	private String mainGameCode;
	private String sign;
	private String timestamp;
	public ActivityExtensionDownloadRequest(Context context,String gameCode,String userid) {
		super();
		this.gameCode = gameCode;
		this.activityCode = gameCode+"promotion";
		this.userid = userid;
		this.plateForm = "pf";
		this.mac = EfunLocalUtil.getLocalMacAddress(context);
		this.imei = EfunLocalUtil.getLocalImeiAddress(context);
		this.androidid = EfunLocalUtil.getLocalAndroidId(context);
		this.mainGameCode = HttpParam.PLATFORM_CODE;
	}
	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getGameCode() {
		return gameCode;
	}

	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}

	public String getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPlateForm() {
		return plateForm;
	}

	public void setPlateForm(String plateForm) {
		this.plateForm = plateForm;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getAndroidid() {
		return androidid;
	}

	public void setAndroidid(String androidid) {
		this.androidid = androidid;
	}

	public String getMainGameCode() {
		return mainGameCode;
	}

	public void setMainGameCode(String mainGameCode) {
		this.mainGameCode = mainGameCode;
	}
	
	
}
