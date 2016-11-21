package com.efun.platform.http.request.bean;

/**
 * 檢測有沒有新信件
 * @author itxuxxey
 *
 */
public class CheckNewSysEmailRequest extends BaseRequestBean{
	private String uid;
	private String platform;
	private String sign;
	private String timestamp;
	private String fromType;
	private String language;
	private String version;
	private String packageVersion;

	public CheckNewSysEmailRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CheckNewSysEmailRequest(String uid, String platform, String sign,
			String timestamp, String fromType, String language, String version,
			String packageVersion) {
		super();
		this.uid = uid;
		this.platform = platform;
		this.sign = sign;
		this.timestamp = timestamp;
		this.fromType = fromType;
		this.language = language;
		this.version = version;
		this.packageVersion = packageVersion;
	}
	
	
}
