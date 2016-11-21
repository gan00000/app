package com.efun.platform.http.request.bean;

import android.content.Context;

import com.efun.platform.module.utils.AppUtils;
/**
 * 设置 - 检查版本更新
 * @author itxuxxey
 *
 */
public class SettingCheckRequest extends BaseRequestBean{
	private String curVersion;
	private String gameCode;
	
	public SettingCheckRequest(Context context, String gameCode) {
		super();
		this.curVersion = AppUtils.getAppVersionCode(context)+"";
		this.gameCode = gameCode;
	}
	public String getCurVersion() {
		return curVersion;
	}
	public void setCurVersion(String curVersion) {
		this.curVersion = curVersion;
	}
	public String getGameCode() {
		return gameCode;
	}
	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}
	
}
