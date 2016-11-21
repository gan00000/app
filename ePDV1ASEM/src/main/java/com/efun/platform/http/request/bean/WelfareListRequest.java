package com.efun.platform.http.request.bean;

import com.efun.platform.utils.Const.HttpParam;

/**
 * 福利列表
 * 
 * @author harvey
 *
 */
public class WelfareListRequest extends BaseRequestBean{

	private String fromType;
	/**
	 * 平台标志
	 */
	private String platform;
	private String language;
	private String gameCode;
	private String version;
	private String packageVersion;
	
	
//	/**
//	 * 当前页面
//	 */
//	private String currentPage;
//	/**
//	 * 每页数目
//	 */
//	private String pageSize;
	
	public WelfareListRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getFromType() {
		return fromType;
	}

	public void setFromType(String fromType) {
		this.fromType = fromType;
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

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getGameCode() {
		return gameCode;
	}

	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getPackageVersion() {
		return packageVersion;
	}

	public void setPackageVersion(String packageVersion) {
		this.packageVersion = packageVersion;
	}
	
}
