package com.efun.platform.http.request.bean;

import android.content.Context;
/**
 * 资讯 - 首页
 * @author itxuxxey
 *
 */
public class SummaryHomeRequest extends BaseRequestBean{
	private String version;
	private String platform;
	private String picSize;
	private String partner;
	private String gameCode;
	private String packageVersion;
	private String queryType;
	
	public SummaryHomeRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SummaryHomeRequest(Context context ,String version, String platform, String picSize,
			String partner) {
		super();
		this.version = version;
		this.platform = platform;
		this.picSize = picSize;
		this.partner = partner;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getPicSize() {
		return picSize;
	}
	public void setPicSize(String picSize) {
		this.picSize = picSize;
	}
	public String getPartner() {
		return partner;
	}
	public void setPartner(String partner) {
		this.partner = partner;
	}
	public String getGameCode() {
		return gameCode;
	}
	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}
	public String getPackageVersion() {
		return packageVersion;
	}
	public void setPackageVersion(String packageVersion) {
		this.packageVersion = packageVersion;
	}
	public String getQueryType() {
		return queryType;
	}
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
	
}
