package com.efun.platform.http.request.bean;
/**
 * 手機號碼地區
 * @author itxuxxey
 *
 */
public class PhoneAreaTypeRequest extends BaseRequestBean{
	private String fromType;//app
	private String platform;//平臺標識，tw
	private String type;
	private String version;//設備類型android
	private String packageVersion;//應用版本
	private String language;//語言
	
	public PhoneAreaTypeRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PhoneAreaTypeRequest(String fromType, String platform, String type,
			String version, String packageVersion, String language) {
		super();
		this.fromType = fromType;
		this.platform = platform;
		this.type = type;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
