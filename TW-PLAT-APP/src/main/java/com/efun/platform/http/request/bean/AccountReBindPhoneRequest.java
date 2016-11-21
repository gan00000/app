package com.efun.platform.http.request.bean;

/**
 * 修改手機绑定
 * 
 * @author itxuxxey
 * 
 */
public class AccountReBindPhoneRequest extends BaseRequestBean {
	
	private String phone;
	private String uid;
	private String vcode;
	private String sign;
	private String timestamp;
	private String platform;
	private String fromType;
	private String version;
	private String packageVersion;
	private String language;
	private String areaCode;
	private String newPhone;

	public AccountReBindPhoneRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AccountReBindPhoneRequest(String phone, String uid, String vcode,
			String sign, String timestamp, String platform, String fromType,
			String version, String packageVersion, String language,
			String areaCode,String newPhone) {
		super();
		this.phone = phone;
		this.uid = uid;
		this.vcode = vcode;
		this.sign = sign;
		this.timestamp = timestamp;
		this.platform = platform;
		this.fromType = fromType;
		this.version = version;
		this.packageVersion = packageVersion;
		this.language = language;
		this.areaCode = areaCode;
		this.newPhone = newPhone;
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

	public String getVcode() {
		return vcode;
	}

	public void setVcode(String vcode) {
		this.vcode = vcode;
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

	public String getFromType() {
		return fromType;
	}

	public void setFromType(String fromType) {
		this.fromType = fromType;
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

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getNewPhone() {
		return newPhone;
	}

	public void setNewPhone(String newPhone) {
		this.newPhone = newPhone;
	}

}
