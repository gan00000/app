package com.efun.platform.http.request.bean;
/**
 * 發送驗證碼到郵箱
 * @author itxuxxey
 *
 */
public class SendVcodeToEmailRequest extends BaseRequestBean{
	
	private String uid;//用戶id
	private String platform;//平臺標識，tw
	private String email;//接收驗證碼的郵箱
	private String language;//語言
	private String version;//設備類型android
	private String packageVersion;//應用版本
	private String sign;//簽名
	private String timestamp;//時間戳
	private String fromType;//app
	
	public SendVcodeToEmailRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SendVcodeToEmailRequest(String uid, String platform, String email,
			String language, String version, String packageVersion,
			String sign, String timestamp, String fromType) {
		super();
		this.uid = uid;
		this.platform = platform;
		this.email = email;
		this.language = language;
		this.version = version;
		this.packageVersion = packageVersion;
		this.sign = sign;
		this.timestamp = timestamp;
		this.fromType = fromType;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
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
	
}
