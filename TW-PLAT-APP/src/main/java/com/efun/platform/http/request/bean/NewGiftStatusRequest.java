package com.efun.platform.http.request.bean;

import com.efun.platform.utils.Const.HttpParam;

/**
 * 新礼包检查
 * @author itxuxxey
 *
 */
public class NewGiftStatusRequest extends BaseRequestBean {
	private String fromType;
	private String platform;
	private String version;
	private String packageVersion;
	private String language;
	
	public NewGiftStatusRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NewGiftStatusRequest(String fromType, String platform,
			String version, String packageVersion, String language) {
		super();
		this.fromType = fromType;
		this.platform = platform;
		this.version = version;
		this.packageVersion = packageVersion;
		this.language = language;
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

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
	
}
