package com.efun.platform.http.request.bean;
/**
 * 綁定郵箱
 * @author itxuxxey
 *
 */
public class BindEmailByVcodeRequest extends BaseRequestBean{
	
	private String uid;//用戶id
	private String platform;//平臺標識，tw
	private String email;//原郵箱
	private String validEmail;//驗證郵箱
	private String vcode;//驗證碼
	private String sign;//簽名
	private String timestamp;//時間戳
	private String fromType;//app
	private String language;//語言
	private String version;//設備類型android
	private String packageVersion;//應用版本
	private String busiCode;//业务标识bindEmail
	
	public BindEmailByVcodeRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BindEmailByVcodeRequest(String uid, String platform, String email,
			String validEmail, String vcode, String sign, String timestamp,
			String fromType, String language, String version,
			String packageVersion,String busiCode) {
		super();
		this.uid = uid;
		this.platform = platform;
		this.email = email;
		this.validEmail = validEmail;
		this.vcode = vcode;
		this.sign = sign;
		this.timestamp = timestamp;
		this.fromType = fromType;
		this.language = language;
		this.version = version;
		this.packageVersion = packageVersion;
		this.busiCode = busiCode;
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

	public String getValidEmail() {
		return validEmail;
	}

	public void setValidEmail(String validEmail) {
		this.validEmail = validEmail;
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

	public String getFromType() {
		return fromType;
	}

	public void setFromType(String fromType) {
		this.fromType = fromType;
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

	public String getBusiCode() {
		return busiCode;
	}

	public void setBusiCode(String busiCode) {
		this.busiCode = busiCode;
	}

}
