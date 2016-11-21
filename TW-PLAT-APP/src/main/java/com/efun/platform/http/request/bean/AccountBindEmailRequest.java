package com.efun.platform.http.request.bean;

/**
 * 郵箱綁定
 * @author itxuxxey
 *
 */
public class AccountBindEmailRequest extends BaseRequestBean {
	private String uid;
	private String platForm;
	private String email;
	private String sign;
	private String timestamp;
	private String fromType;
	private String version;
	private String packageVersion;
	private String language;

	public AccountBindEmailRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AccountBindEmailRequest(String uid, String platForm, String email,
			String sign, String timestamp, String fromType, String version,
			String packageVersion, String language) {
		super();
		this.uid = uid;
		this.platForm = platForm;
		this.email = email;
		this.sign = sign;
		this.timestamp = timestamp;
		this.fromType = fromType;
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

	public String getPlatform() {
		return platForm;
	}

	public void setPlatform(String platForm) {
		this.platForm = platForm;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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


}
