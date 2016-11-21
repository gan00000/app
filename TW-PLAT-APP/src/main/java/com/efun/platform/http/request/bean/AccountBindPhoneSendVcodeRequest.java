package com.efun.platform.http.request.bean;

/**
 * 发送验证码
 * @author Jesse
 *
 */
public class AccountBindPhoneSendVcodeRequest extends BaseRequestBean{
	private String phone;
	private String uid;
	private String fromType;
	private String sign;
	private String timestamp;
	private String platform;
	private String areaCode;
	private String version;
	private String packageVersion;
	private String language;
	public AccountBindPhoneSendVcodeRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AccountBindPhoneSendVcodeRequest(String phone,
			String fromType, String platform,
			String areaCode, String version, String packageVersion,
			String language) {
		super();
		this.phone = phone;
		this.fromType = fromType;
		this.platform = platform;
		this.areaCode = areaCode;
		this.version = version;
		this.packageVersion = packageVersion;
		this.language = language;
	}


	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getFromType() {
		return fromType;
	}
	public void setFromType(String fromType) {
		this.fromType = fromType;
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
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
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
