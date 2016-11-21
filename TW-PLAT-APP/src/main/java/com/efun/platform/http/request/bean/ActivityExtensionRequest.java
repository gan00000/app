package com.efun.platform.http.request.bean;


/**
 * 好康 - 领取免费点数任务列表 
 * 20150617修整
 * @author itxuxxey
 *
 */
public class ActivityExtensionRequest extends BaseRequestBean{
	private String uid;
	private String device;
	private String area;
	private String version;
	private String sign;
	private String timestamp;
	private String language;//20150617添加
	private String packageVersion;
	
	public ActivityExtensionRequest() {
		super();
	}
	public ActivityExtensionRequest(String device, String area,String version) {
		super();
		this.device = device;
		this.area = area;
		this.version = version;
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
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getPackageVersion() {
		return packageVersion;
	}
	public void setPackageVersion(String packageVersion) {
		this.packageVersion = packageVersion;
	}
	
}
